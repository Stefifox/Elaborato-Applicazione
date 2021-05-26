package dev.stefifox.scuola.sportapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        final EditText nome = findViewById(R.id.namereg);
        final EditText cognome = findViewById(R.id.cognreg);
        final EditText email = findViewById(R.id.emailreg);
        final EditText user = findViewById(R.id.userreg);
        final EditText pass = findViewById(R.id.passreg);

        final Button button = findViewById(R.id.button_reg);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeS = nome.getText().toString();
                String cognomeS = cognome.getText().toString();
                String emailS = email.getText().toString();
                String userS = user.getText().toString();
                String passS = pass.getText().toString();

                HashMap<String, String> params = new HashMap<>();
                params.put("token", "c2e1b21e0a17d28c667cc0a774cb0152");
                params.put("nome", nomeS);
                params.put("cognome", cognomeS);
                params.put("mail", emailS);
                params.put("username", userS);
                params.put("password", passS);
                JSONObject parameters = new JSONObject(params);

                String regURL = MainActivity.serverUrl + "/registration";
                JsonObjectRequest registration = new JsonObjectRequest(Request.Method.POST, regURL, parameters, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(Registration.this, "Registrazione effettuata", Toast.LENGTH_LONG).show();
                        callLogin();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Registration.this, "Errore di registrazione", Toast.LENGTH_SHORT).show();
                    }
                });
                MySingleton.getInstance(Registration.this).addToRequestQueue(registration);
            }

        });

    }

    private void callLogin(){
        Intent login = new Intent(Registration.this, Login.class);
        startActivity(login);
        finish(); //Termino l'activity
    }

}
