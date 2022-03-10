package com.example.demo_razorpay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener
{
    TextView Display;
    Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Button
        pay=findViewById(R.id.btn_pay);

        //TextView
        Display=findViewById(R.id.txt_display);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startPayment();
            }
        });

        Checkout.preload(getApplicationContext());
    }

    public void startPayment()
    {
        Checkout checkout = new Checkout();

        checkout.setKeyID("rzp_test_A2BXtR6sCo8175");
        checkout.setImage(R.drawable.ic_launcher_background);

        final Activity activity = this;

        try
        {
            JSONObject options = new JSONObject();

            options.put("name", "Merchant Name");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000");//pass amount in currency subunits 500 * 100
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","9624720301");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        }
        catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s)
    {
        Display.setText("Payment Successful ID : "+s);
    }

    @Override
    public void onPaymentError(int i, String s)
    {
        Display.setText("Failed and Cause is : "+s);
    }
}