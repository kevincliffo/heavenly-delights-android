package com.kevin.heavenlydelights;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.kevin.heavenlydelights.Model.Data;
import com.kevin.heavenlydelights.Model.Pages;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.kevin.heavenlydelights.MySingleton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


public class SummaryActivity extends AppCompatActivity {

    private String mDateString;
    private String mTimeString;
    private TextView mDateText;
    private TextView mTimeText;
    private TextView mSugarFreeText;
    private TextView mFlavourText;
    private TextView mToppingText;
    private TextView mCostText;
    private TextView mThemeText;
    private TextView mWeightText;
    private Button mChangeButton;
    private Button mOrderButton;
    private EditText etEmail;
    private EditText etName;
    private EditText etPhoneNo;
    private Intent mIntent;
    private String ADD_ORDER_API_URL = "http://10.0.2.2:8012/heavenlydelights/api/add-order.php";
    private int totalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        setDateAndTime();
        mIntent = getIntent();
    }

    private void setLayout() {
        int cost = 0;

        //flavours ops

        mFlavourText = (TextView) findViewById(R.id.flavourLabel);
        int flavourPos = Data.baseIndex;
        mFlavourText.setText("Base Flavour: " +
                Pages.cakePage.getObjects()[flavourPos].getDisplayName());
        mFlavourText.setVisibility(View.VISIBLE);

        //toppings ops

        mToppingText = (TextView) findViewById(R.id.toppingLabel);
        int toppingPos = Data.topIndex;
        mToppingText.setText("Topping: " +
                Pages.creamPage.getObjects()[toppingPos].getDisplayName());
        mToppingText.setVisibility(View.VISIBLE);

        //weight ops

        mWeightText = (TextView) findViewById(R.id.weightLabel);
        int weightPos = Data.sizeIndex;
        mWeightText.setText(Pages.weights.getObjects()[weightPos].getDisplayName());
        cost = Pages.weights.getObjects()[weightPos].getPrice();

        //theme ops

        mThemeText = (TextView) findViewById(R.id.themeLabel);
        String theme = Data.cakeTheme;
        if (!theme.isEmpty()){
            mThemeText.setText("Personal Theme: " + theme + " (add Ksh. 400)");
            cost+=400;
        }
        else{
            mThemeText.setVisibility(View.GONE);
        }
        //sugarfree settings
        mSugarFreeText = (TextView) findViewById(R.id.sugarFreeLabel);
        int sugarIndex = Data.sugarIndex;
        mSugarFreeText.setText("Sugarfree: " +
                Pages.sugarPage.getObjects()[sugarIndex].getDisplayName());
        if (sugarIndex == 0){
            cost+=100;
        }

        totalCost = cost;

        mCostText = (TextView) findViewById(R.id.finalCostLabel);
        mCostText.setText("Total Cost: Ksh. " + cost);

        etEmail = findViewById(R.id.email);
        etName = findViewById(R.id.name);
        etPhoneNo = findViewById(R.id.mobileno);

        mChangeButton = (Button) findViewById(R.id.changeDateButton);
        mChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateAndTime();
            }
        });

        mOrderButton = findViewById(R.id.finalButton);
        mOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add order details to db
                addOrderToDatabase();
            }
        });
    }

    private void addOrderToDatabase(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_ORDER_API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            //if (json.getString("success")) {

                            Toast.makeText(getBaseContext(), json.getString("message"), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SummaryActivity.this, HomeActivity.class);

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

                params.put("CustomerName", etName.getText().toString());
                params.put("CustomerEmail",etEmail.getText().toString());
                params.put("CustomerMobileNo",etPhoneNo.getText().toString());
                params.put("Item", "CAKE");
                params.put("BaseFlavour",mFlavourText.getText().toString());
                params.put("Topping",mToppingText.getText().toString());
                params.put("Theme",mThemeText.getText().toString());
                params.put("SugarFree",mSugarFreeText.getText().toString());
                params.put("TotalCost", String.valueOf(totalCost));
                params.put("OrderTime",mTimeString);
                params.put("OrderDate", mDateString);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void setDateAndTime() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog datePickerDialog, int year, int
                    month, int day) {
                GregorianCalendar date = new GregorianCalendar(year, month, day);
                SimpleDateFormat formatter = new SimpleDateFormat("d MMMM y");
                mDateString = formatter.format(date.getTime());
                //date operation
                mDateText = (TextView) findViewById(R.id.dateLabel);
                mDateText.setText("Delivery Date: " + mDateString);
                mDateText.setVisibility(View.VISIBLE);
                setTime();
            }
        };
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                listener,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setThemeDark(true);
        dpd.show(getFragmentManager(), "Select You Delivery Date");
    }

    private void setTime() {
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout radialPickerLayout, int hour, int minute) {
                String dep;
                if (hour>13){
                    hour-=12;
                    dep = " PM";
                }
                else dep = " AM";
                String minString = String.valueOf(minute);
                if (minString.length() < 2){
                    minString = " " + minString;
                }
                mTimeString = "" + hour + " : " + minString + dep;
                //time operations
                mTimeText = (TextView) findViewById(R.id.timeLabel);
                mTimeText.setText("Delivery Time: " + mTimeString);
                mTimeText.setVisibility(View.VISIBLE);
                setLayout();
            }
        };
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                listener,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        timePickerDialog.setThemeDark(true);
        timePickerDialog.show(getFragmentManager(), "Select Delivery Time");
    }
}
