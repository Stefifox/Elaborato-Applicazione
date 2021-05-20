package dev.stefifox.scuola.sportapp;

import android.app.VoiceInteractor;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CallApi {

    private String serverUrl = "http://192.168.86.24:5500";

    public CallApi() {
        //set context variables if required
    }

    public HashMap<String, String> loginFuction(String user, String pass, Context context){
        HashMap<String, String> data = new HashMap<>();
        Map<String, String> params = new HashMap<>();

        params.put("token", "51c8b422852557a12d3778270037538c");
        params.put("mail", user);
        params.put("password", pass);

        JSONObject parameters = new JSONObject(params);
        String loginUrl = serverUrl + "/login";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, loginUrl, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success

                try {
                    JSONObject obj = new JSONObject(response.getJSONObject("user").toString());
                    data.put("id", obj.getString("id"));
                    data.put("nome", obj.getString("nome"));
                    data.put("cognome", obj.getString("cognome"));
                    data.put("mail", obj.getString("mail"));
                    data.put("username", obj.getString("username"));
                    data.put("token", obj.getString("identityToken"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                data.put("error", "1");

                //TODO: handle failure
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
        return data;
    }
}
