package com.example.ficharbitekapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText editmail, editpass;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editmail = findViewById(R.id.login_email);
        editpass = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_button);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarUser("http://192.168.10.47/git_bitek_fichar/Bitek_Fichar/bitek_fichar/api/api_login.php");
            }
        });

    }

    private void validarUser(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);


                    Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                    intent.putExtra("id_user", jsonObject.getString("id_user"));
                    intent.putExtra("dni", jsonObject.getString("dni"));
                    intent.putExtra("mail", jsonObject.getString("mail"));
                    intent.putExtra("name", jsonObject.getString("name"));
                    intent.putExtra("lname", jsonObject.getString("lname"));
                    intent.putExtra("type", jsonObject.getString("type"));

                    startActivity(intent);

                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo", editmail.getText().toString());
                parametros.put("contrasena", editpass.getText().toString());
                return parametros;
            }
        };

        RequestQueue resquestQueque = Volley.newRequestQueue(this);
        resquestQueque.add(stringRequest);
    }

}