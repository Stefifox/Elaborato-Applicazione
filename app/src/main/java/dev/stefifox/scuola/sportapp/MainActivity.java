package dev.stefifox.scuola.sportapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import dev.stefifox.scuola.sportapp.core.*;

public class MainActivity extends AppCompatActivity {

    public static String serverUrl = "http://192.168.86.24:5500";
    public static ArrayList<Sport> sport = new ArrayList<>();
    public static ArrayList<Squadra> squadre = new ArrayList<>();
    public static ArrayList<Squadra> preferite = new ArrayList<>();

    private String token = "n";
    private int id;
    private String nome;
    private String cognome;
    private String username;
    private String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView userShow = findViewById(R.id.username);
        final TextView mailShow = findViewById(R.id.mail_text);
        final ImageView profile = findViewById(R.id.profilebutton);
        final ImageView sportView = findViewById(R.id.sportview);
        final TabLayout respLayout = findViewById(R.id.results);

        final LinearLayout teamView = findViewById(R.id.teamsView);

        //Carico tutti i dati che ho in memoria
        token = loadToken();
        id = loadId();
        nome = loadName();
        cognome = loadCognome();
        username = loadUsername();
        mail = loadMail();

        if(sport.isEmpty()){
            getSports();
        }else{
            for(int i = 0; i<sport.size(); i++) {
                Sport s = sport.get(i);
                respLayout.addTab(respLayout.newTab().setText(s.getNome()));
            }
            respLayout.addTab(respLayout.newTab().setText("Tutte"));
        }
        if(squadre.isEmpty()){
            getSquadre();
        }

        if(id == 0){ //Se l'id ?? 0 avvio la procedura di Login
            callLogin();
        }else{

        }

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prof = new Intent(MainActivity.this, ProfilePage.class);
                startActivity(prof);
            }
        });

        sportView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent spo = new Intent(MainActivity.this, AllSport.class);
                startActivity(spo);
            }
        });

        userShow.setText(username.toUpperCase());
        mailShow.setText(mail);

        respLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                    int pos = respLayout.getSelectedTabPosition() + 1;
                    popolaPref(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        popolaPref(1);

    }

    private void popolaPref(int pos){
        final LinearLayout result = findViewById(R.id.teamsView);
        int count = 0;

        result.removeAllViews();
        for (int i = 0; i<preferite.size(); i++){

            Squadra s = preferite.get(i);
            Sport sp = s.getSport();
            //System.out.println("Sport id: " + sp.getId() + " - " + sp.getDescizione() + "-" + s.getNome());
            if(sp.getId() == pos){
                TextView temp = new TextView(MainActivity.this);
                count ++;
                temp.setText(s.getNome());
                temp.setTextSize(20);
                temp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                result.addView(temp);
            }
        }
        if(count == 0){
            TextView temp = new TextView(MainActivity.this);
            temp.setText("Non ho trovato squadre preferite");
            temp.setTextSize(20);
            temp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            result.addView(temp);
        }
    }


    //TO-DO Get preferiti
    //Get Peferiti
    private void getPreferiti(){
        final TextView debug = findViewById(R.id.debug);
        String followURL = serverUrl + "/getFollow?id=" + id;

        JsonObjectRequest floowRequest = new JsonObjectRequest(Request.Method.GET, followURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray follow = new JSONArray(response.getJSONArray("follow").toString());
                    for(int i=0; i<follow.length(); i++){
                        JSONObject temp = follow.getJSONObject(i);
                        System.out.println("-" + temp.getString("nome"));
                        int id = temp.getInt("id")-1;
                        Squadra s = squadre.get(id);
                        System.out.println(s.getNome());
                        preferite.add(s);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(floowRequest);
    }

    private void getSports(){
        final TabLayout respLayout = findViewById(R.id.results);
        final TextView debug = findViewById(R.id.debug);
        String sportURL = serverUrl + "/sports";
        JsonObjectRequest addSportRequest = new JsonObjectRequest(Request.Method.GET, sportURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray sports = new JSONArray(response.getJSONArray("sports").toString());
                    for(int i = 0; i < sports.length(); i++) {
                        JSONObject sportObject = sports.getJSONObject(i);
                        int id = sportObject.getInt("id");
                        String nome = sportObject.getString("nome");
                        String descrizione = sportObject.getString("descizione");
                        //System.out.println(id + " - " + descrizione);
                        sport.add(new Sport(id ,nome, descrizione));
                        respLayout.addTab(respLayout.newTab().setText(nome));
                        debug.setVisibility(View.INVISIBLE);
                    }
                    respLayout.addTab(respLayout.newTab().setText("Tutte"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                debug.setVisibility(View.VISIBLE);
                debug.setText("Connettiti ad internet");
                Toast.makeText(MainActivity.this, "Errore di connessione", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(MainActivity.this).addToRequestQueue(addSportRequest);
    }

    private void getSquadre(){
        final TextView debug = findViewById(R.id.debug);
        String teamsURL = serverUrl + "/teams";

        JsonObjectRequest addTeamsRequest = new JsonObjectRequest(Request.Method.GET, teamsURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray teams = new JSONArray(response.getJSONArray("teams").toString());
                    //debug.setText(teams.toString());
                    for(int i = 0; i < teams.length(); i++) {
                        JSONObject teamsObject = teams.getJSONObject(i);
                        int idSport = teamsObject.getJSONObject("sport").getInt("id");
                        int id = teamsObject.getInt("id");
                        String nome = teamsObject.getString("nome");
                        String cNaz = teamsObject.getString("nazione");
                        Sport s = MainActivity.sport.get(idSport-1);
                        MainActivity.squadre.add(new Squadra(id, s, nome, cNaz));
                        debug.setVisibility(View.INVISIBLE);
                    }
                    getPreferiti();
                    //debug.setText(squadre.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Errore di connessione", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(MainActivity.this).addToRequestQueue(addTeamsRequest);

    }


    private int loadId(){
        SharedPreferences load = getSharedPreferences("id", MODE_PRIVATE);
        int id = load.getInt("id_", this.id);
        return id;
    }

    private String loadToken(){
        SharedPreferences load = getSharedPreferences("token", MODE_PRIVATE);
        String token = load.getString("token_", this.token);
        return token;
    }

    private String loadName(){
        SharedPreferences load = getSharedPreferences("nome", MODE_PRIVATE);
        String nome = load.getString("nome_", this.nome);
        return nome;
    }

    private String loadCognome(){
        SharedPreferences load = getSharedPreferences("cognome", MODE_PRIVATE);
        String cognome = load.getString("cognome_", this.cognome);
        return cognome;
    }

    private String loadUsername(){
        SharedPreferences load = getSharedPreferences("username", MODE_PRIVATE);
        String username = load.getString("username_", this.username);
        return username;
    }

    private String loadMail(){
        SharedPreferences load = getSharedPreferences("mail", MODE_PRIVATE);
        String mail = load.getString("mail_", this.mail);
        return mail;
    }


    private String getToken(String id){
        String getUrl = serverUrl + "/getToken?id=" + id;
        final String[] responseString = new String[1];
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    responseString[0] = response.getJSONObject("token").toString();
                } catch (JSONException e) {
                    responseString[0] = "null";
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        return responseString[0];
    }

    private void callLogin(){
            Intent login = new Intent(MainActivity.this, Login.class);
            startActivity(login);
            finish(); //Termino l'activity
    }


    private void save (String data, String value){
        SharedPreferences save = getSharedPreferences(data, MODE_PRIVATE);
        SharedPreferences.Editor saveEdit = save.edit();
        saveEdit.putString(data + "_", value);
        saveEdit.apply();
    }

    private void save (String data, int value){
        SharedPreferences save = getSharedPreferences(data, MODE_PRIVATE);
        SharedPreferences.Editor saveEdit = save.edit();
        saveEdit.putInt(data + "_", value);
        saveEdit.apply();
    }


}