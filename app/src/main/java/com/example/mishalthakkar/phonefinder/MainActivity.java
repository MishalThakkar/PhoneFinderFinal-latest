package com.example.mishalthakkar.phonefinder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.kwabenaberko.smsbroadcastreceiverlib.SmsBroadcastReceiver;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends AppCompatActivity implements OnSuccessListener<Location>{

    Button Submit;
    TextView EditTextView;
    public static String passcode;
    private String TAG = MainActivity.class.getSimpleName();
    private FusedLocationProviderClient client;
    static SmsManager smsManager;
    private static final int SEND_SMS_PERMISSION_CODE = 111;



    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* if (getIntent().getBooleanExtra("SERVICE",false))
        {
            sendSMS();
        }*/

        client = LocationServices.getFusedLocationProviderClient(this);
        Submit = findViewById(R.id.button);
        EditTextView = findViewById(R.id.editText);
        SharedPreferences sharedPreferences = this.getSharedPreferences("PASSCODE", Context.MODE_PRIVATE);
        String pass = sharedPreferences.getString("passcode", "");
        Log.d(TAG, "onReceive: " + pass);
        EditTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTextView.setText("");
            }
        });

        if (checkPermission(android.Manifest.permission.SEND_SMS))
        {
            String msg = getIntent().getStringExtra("Message");
            smsManager = SmsManager.getDefault();
            Bundle bundle = getIntent().getBundleExtra("Bundle");
            Log.d(TAG, "onCreate: "+ getIntent().getStringExtra("Message"));

            Log.d("onCreate",getIntent().getStringExtra("msg"));
//            if(getIntent().getStringExtra("Message")!= null && !getIntent().getStringExtra("Message").isEmpty())
//            {
                smsManager.sendTextMessage("9909677576", null,"hii" , null, null);
//            }

            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        }else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_CODE);
        }







//        smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage("9909677576", null, "HI", null, null);







//        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//
//                if(location!= null){
//
//                    String retrievelocation = location.toString();
//                    latitude = retrievelocation.substring(15,24);
//                    longitude = retrievelocation.substring(25,34);
//                    textView.setText("Latitude = " + latitude + "  " + "  Lonigude = "+longitude);
//                    //textView.setText(location.toString());
//                    message = "http://maps.google.com?q="+latitude+","+longitude;
//                }
//                if(checkPermission(Manifest.permission.SEND_SMS))
//                {
//
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage("9909677576",null,message,null,null);
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            smsManager = SmsManager.getDefault();
            //smsManager.sendTextMessage("9909677576", null, "HI", null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        }

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            passcode = EditTextView.getText().toString();
            Toast.makeText(getApplicationContext(),"Submitted",Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("PASSCODE", MODE_PRIVATE);
            sharedPreferences.edit().putString("passcode", passcode).apply();
            Log.d("Test",passcode);
            SharedPreferences sharedPref = getSharedPreferences("PASSCODE", Context.MODE_PRIVATE);
            String pass = sharedPref.getString("passcode", "");
            Log.d("Test", "onReceive: " + pass);
            }
        });
    }

//     void sendSMS(){
//        smsManager = SmsManager.getDefault();
////        smsManager.sendTextMessage("9909677576", null, "HI", null, null);
//         MainActivity.this.finish();
//
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case SEND_SMS_PERMISSION_CODE: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getApplicationContext(), "SMS sent.",
//                            Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getApplicationContext(),
//                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
//                    return;
//                }
//            }
//        }
//
//
//    }

    private boolean checkPermission(String permission)
    {
        int checkPermission = ContextCompat.checkSelfPermission(this,permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode){
            case SEND_SMS_PERMISSION_CODE :
//                if(grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
        }
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    void getLocationInfo(Location location){
        Log.d(TAG, "onSuccess: " + location);
    }

    @Override
    public void onSuccess(Location location) {
        Log.d(TAG, "onSuccess: " + location);
    }
}
