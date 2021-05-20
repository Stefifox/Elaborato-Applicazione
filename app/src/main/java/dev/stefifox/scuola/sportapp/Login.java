package dev.stefifox.scuola.sportapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    //Dichiaro le variabili
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final HashMap<String, String>[] userdata = new HashMap[]{new HashMap<>()};

        //Dichiaro i Widget
        final Button login = findViewById(R.id.login_button);
        final EditText mailText = findViewById(R.id.mail);
        final EditText passText = findViewById(R.id.pass);

        final CallApi api = new CallApi();
        //Dichiaro il funzionamemto dei pulsanti
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = mailText.getText().toString();
                password = passText.getText().toString();
                userdata[0] = api.loginFuction(username, password, Login.this);

                if(!userdata[0].containsKey("error")){

                }else{

                }

            }
        });

    }

}
