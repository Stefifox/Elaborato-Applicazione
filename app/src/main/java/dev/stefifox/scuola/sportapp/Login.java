package dev.stefifox.scuola.sportapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    //Dichiaro le variabili
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

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
                
            }
        });

    }

}
