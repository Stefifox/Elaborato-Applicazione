package dev.stefifox.scuola.sportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    public static String serverUrl = "http://192.168.86.24:5500";

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

        token = loadToken();
        id = loadId();

        if(token.equals(getToken(String.valueOf(id)))){

        }

        nome = loadName();
        cognome = loadCognome();
        username = loadUsername();
        mail = loadMail();

        userShow.setText(username);
        mailShow.setText(mail);

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

}