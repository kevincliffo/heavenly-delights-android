package com.kevin.heavenlydelights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button btnOrder, btnCall, btnEmail, btnVisitUs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnOrder = findViewById(R.id.btnOrder);
        btnCall = findViewById(R.id.btnCall);
        btnEmail = findViewById(R.id.btnEmail);
        btnVisitUs = findViewById(R.id.btnVisitUs);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOrderActivity();
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //emails kitcheneesta@gmail.com
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@heavenlydelights.co.ke"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Heavenly Delights Support");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client:"));
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calls the phone number
                Intent intent = new Intent (Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+254706012355"));
                startActivity(intent);
            }
        });

        btnVisitUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to address (random for now)
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q=Maryjoy Primary School - Nyali"));
                startActivity(intent);
            }
        });
    }

    private void startOrderActivity() {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }
}