package com.kevin.heavenlydelights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    private String LOGIN_REGISTRATION_API_URL = "http://10.0.2.2:8012/heavenlydelights/api/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        login();
        register();
    }

    void login()
    {
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        while(true)
                        {
                            if(etEmail.getText().toString().equals(""))
                            {
                                Toast.makeText(getBaseContext(), "Enter Email", Toast.LENGTH_LONG).show();
                                break;
                            }
                            if(etPassword.getText().toString().equals(""))
                            {
                                Toast.makeText(getBaseContext(), "Enter Password", Toast.LENGTH_LONG).show();
                                break;
                            }

                            loginUser();
                            break;
                        }
                    }
                }
        );
    }

    public void loginUser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_REGISTRATION_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    //if (json.getString("success")) {
                    String errorFound = json.getString("errorfound");
                    if(json.getString("errorfound").equals("0")) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();

                        editor.putString("UserId",json.getString("UserId"));
                        editor.putString("MobileNo",json.getString("MobileNo"));
                        editor.putString("Name",json.getString("Name"));
                        editor.putString("Email",json.getString("Email"));
                        editor.putString("UserType",json.getString("UserType"));
                        editor.putString("Age",json.getString("Age"));
                        editor.apply();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        Toast.makeText(getBaseContext(), json.getString("message"), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
                    }
                    ////////////////////
                    //}
                }
                catch (JSONException ex)
                {}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"failed to Register",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("Email", etEmail.getText().toString());
                params.put("Password",etPassword.getText().toString());
                //params.put("photo", "nothing");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
    void register()
    {
        TextView tvCreateAccount = findViewById(R.id.signUpTextView);
        tvCreateAccount.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);

                        startActivity(intent);
                    }
                }
        );
    }
}