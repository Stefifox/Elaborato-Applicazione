package dev.stefifox.scuola.sportapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    //Dichiaro le variabili
    private String username;
    private String password;
    private boolean success = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final HashMap<String, String> userdata = new HashMap<>();

        //Dichiaro i Widget
        final Button login = findViewById(R.id.login_button);
        final EditText mailText = findViewById(R.id.mail);
        final EditText passText = findViewById(R.id.pass);

        //Dichiaro il funzionamemto dei pulsanti
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = mailText.getText().toString();
                password = passText.getText().toString();

                //Parametri della richiesta
                String loginUrl = MainActivity.serverUrl + "/login";
                Map<String, String> params = new HashMap<>();

                params.put("token", "51c8b422852557a12d3778270037538c");
                params.put("mail", username);
                params.put("password", password);
                JSONObject parameters = new JSONObject(params);

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, loginUrl, parameters, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = new JSONObject(response.getJSONObject("user").toString()); //Genero un oggetto dal risultato
                            save("id", obj.getInt("id"));
                            save("nome", obj.getString("nome"));
                            save("cognome", obj.getString("cognome"));
                            save("mail", obj.getString("mail"));
                            save("username", obj.getString("username"));
                            save("token", obj.getString("identityToken"));
                            System.out.println(obj.toString()); //Print di debug
                            callMain(true); //Avvio la pagina principale una volta ottenute le informazioni
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, "Errore di autentucazione, riprova", Toast.LENGTH_SHORT).show(); //In caso di errore
                    }
                });
                //Chiamo l'istanza per eseguire la richiesta
                MySingleton.getInstance(Login.this).addToRequestQueue(request);
            }
        });

    }

    private void callMain(boolean result){
        if(result){
            Intent main = new Intent(Login.this, MainActivity.class);
            startActivity(main); //Avvio l'activity main
            finish(); //Termino l'activity login
        }
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
