package com.kevin.heavenlydelights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {

    EditText etEmail, etPassword, etAge, etMobileNo, etName;
    private final String REGISTRATION_API_URL = "http://10.0.2.2:8012/heavenlydelights/api/registration.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etAge = findViewById(R.id.etAge);
        etMobileNo = findViewById(R.id.etMobileNo);
        etName = findViewById(R.id.etName);

        switchTologin();
        register();
    }

    private void register()
    {
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(
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
                        if(etName.getText().toString().equals(""))
                        {
                            Toast.makeText(getBaseContext(), "Enter Name", Toast.LENGTH_LONG).show();
                            break;
                        }
                        if(etAge.getText().toString().equals(""))
                        {
                            Toast.makeText(getBaseContext(), "Enter Age", Toast.LENGTH_LONG).show();
                            break;
                        }
                        if(etMobileNo.getText().toString().equals(""))
                        {
                            Toast.makeText(getBaseContext(), "Enter Mobile No", Toast.LENGTH_LONG).show();
                            break;
                        }

                        registerUser();
                        break;
                    }
                }
            }
        );
    }

    public void registerUser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTRATION_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    //if (json.getString("success")) {

                    Toast.makeText(getBaseContext(), json.getString("message"), Toast.LENGTH_LONG);
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);

                    startActivity(intent);
                    ////////////////////
                    //}
                }
                catch (JSONException ex)
                {

                }
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
                params.put("Name", etName.getText().toString());
                params.put("Email",etEmail.getText().toString());
                params.put("Password",etPassword.getText().toString());
                params.put("MobileNo",etMobileNo.getText().toString());
                params.put("Age",etAge.getText().toString());
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

    void switchTologin()
    {
        TextView tvLoginTextView = findViewById(R.id.loginTextView);
        tvLoginTextView.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);

                    startActivity(intent);
                }
            }
        );
    }
}