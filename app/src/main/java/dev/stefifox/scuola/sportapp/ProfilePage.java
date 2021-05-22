package dev.stefifox.scuola.sportapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfilePage  extends AppCompatActivity {

    private int id;
    private String nome;
    private String cognome;
    private String username;
    private String mail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile);

        final TextView name = findViewById(R.id.name);
        final TextView sur = findViewById(R.id.surname);
        final TextView mailView = findViewById(R.id.mailprof);
        final TextView userView = findViewById(R.id.usernameprof);
        final TextView idView = findViewById(R.id.idprof);

        final Button exit = findViewById(R.id.exit);
        final Button change = findViewById(R.id.changepass);

        final EditText oldPass = findViewById(R.id.oldpass);
        final EditText newPass = findViewById(R.id.newpass);

        id = loadId();
        nome = loadName();
        cognome = loadCognome();
        username = loadUsername();
        mail = loadMail();

        name.setText(nome);
        sur.setText(cognome);
        mailView.setText(mail);
        userView.setText(username);
        idView.setText(String.valueOf(id));

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save("id", 0);
                callLogin();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = oldPass.getText().toString();
                String newPassword = newPass.getText().toString();
                String reqUrl = MainActivity.serverUrl + "/changepass";

                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                params.put("old", oldPassword);
                params.put("new", newPassword);
                JSONObject parameters = new JSONObject(params);

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, reqUrl, parameters, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ProfilePage.this, "Password cambiata", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfilePage.this, "Errore, riprova", Toast.LENGTH_SHORT).show();
                    }
                });
                MySingleton.getInstance(ProfilePage.this).addToRequestQueue(request);
            }
        });


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

    private int loadId(){
        SharedPreferences load = getSharedPreferences("id", MODE_PRIVATE);
        int id = load.getInt("id_", this.id);
        return id;
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

    private void callLogin(){
        Intent login = new Intent(ProfilePage.this, Login.class);
        startActivity(login);
        finish(); //Termino l'activity
    }


}
