package com.example.wahid.currencyconverter;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private double bdt;
    private double usd;
    private final static String DEFAULT = "No Data Found";

    private ImageView i1;
    private ImageView i2;
    private TextView inputTextView, resultTextView;
    private ImageButton mDelete;
    private String inputValue = "";
    private final String sourceUrl= "http://hrhafiz.com/converter/";
    public static String x = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        i1 = (ImageView) findViewById(R.id.imageView1);
        i2 = (ImageView) findViewById(R.id.imageView2);
        inputTextView = (TextView) findViewById(R.id.inputFirldID);
        resultTextView = (TextView) findViewById(R.id.resultFieldId);
        inputTextView.setText("0");
        mDelete = (ImageButton) findViewById(R.id.delButton);

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputValue = delete(inputValue);
                inputTextView.setText(inputValue);
                if(!inputTextView.getText().toString().isEmpty()) {
                    calculation();
                }else {

                    inputTextView.setText("0");
                    resultTextView.setText("0");
                }

            }
        });

        mDelete.setOnLongClickListener(new View.OnLongClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onLongClick(View v) {
                // previously invisible view
                View myView = findViewById(R.id.test);
                // get the center for the clipping circle
                int cx = (myView.getLeft() + myView.getRight()) / 2;
                int cy = (myView.getTop() + myView.getBottom()) / 2;

                // get the final radius for the clipping circle
                int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
                // make the view visible and start the animation
                anim.setDuration(1000);
                anim.start();
                inputValue = "0";
                inputTextView.setText(inputValue);
                resultTextView.setText(inputValue);
                return false;
            }
        });
        load();

        Log.i("Test Of usd", String.valueOf(usd));
        Log.i("Test Of bdt", String.valueOf(bdt));

        if(isNetworkAvailable()){
            Toast.makeText(this, "Network Available", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "No Network", Toast.LENGTH_LONG).show();
            load();
        }
    }

    public void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.oneButton:
                inputValue = inputValue + 1;
                inputTextView.setText(inputValue);
                calculation();

                break;
            case R.id.twoButton:
                inputValue = inputValue + 2;
                inputTextView.setText(inputValue);
                calculation();

                break;
            case R.id.threeButton:
                inputValue = inputValue + 3;
                inputTextView.setText(inputValue);
                calculation();

                break;
            case R.id.fourButton:
                inputValue = inputValue + 4;
                inputTextView.setText(inputValue);
                calculation();

                break;
            case R.id.fiveButton:
                inputValue = inputValue + 5;
                inputTextView.setText(inputValue);
                calculation();

                break;
            case R.id.sixButton:
                inputValue = inputValue + 6;
                inputTextView.setText(inputValue);
                calculation();

                break;
            case R.id.sevenButton:
                inputValue = inputValue + 7;
                inputTextView.setText(inputValue);
                calculation();

                break;
            case R.id.eightButton:
                inputValue = inputValue + 8;
                inputTextView.setText(inputValue);
                calculation();

                break;
            case R.id.nineButton:
                inputValue = inputValue + 9;
                inputTextView.setText(inputValue);
                calculation();

                break;
            case R.id.zeroButton:
                inputValue = inputValue + 0;
                inputTextView.setText(inputValue);
                calculation();

                break;
            case R.id.dotButton:
                if (!isDotValid(inputTextView.getText().toString())) {
                    inputValue = inputValue + ".";
                    inputTextView.setText(inputValue);
                }
                break;
            case R.id.replaceID:
//                int x = R.drawable.bd;
//                int y = R.drawable.usa;
//                Log.i("Image", (String) i1.getTag());
//                Log.i("Image", String.valueOf(y));
                if(i1.getTag().equals("bd")){
                    i1.setImageResource(R.drawable.usa);
                    i1.setTag("usa");
                    i2.setImageResource(R.drawable.bd);
                    String resultValue = inputTextView.getText().toString();
                    inputTextView.setText(resultTextView.getText().toString());
                    inputValue = resultTextView.getText().toString();
                    resultTextView.setText(resultValue);


                }else {
                    i1.setImageResource(R.drawable.bd);
                    i1.setTag("bd");
                    i2.setImageResource(R.drawable.usa);
                    String resultValue = inputTextView.getText().toString();
                    inputTextView.setText(resultTextView.getText().toString());
                    inputValue = resultTextView.getText().toString();
                    resultTextView.setText(resultValue);
                }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean isDotValid(String string) {
        if(string.indexOf(".")!=-1){
            return true;
        }
        else{
            return false;
        }
    }

    public String delete(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-1);
    }

    public void load() {
        SharedPreferences sharedPreferences = getSharedPreferences("WebData", Context.MODE_PRIVATE);
        String getUSD = sharedPreferences.getString("USD", DEFAULT);
        String getBDT = sharedPreferences.getString("BDT", DEFAULT);
        if(getBDT.equals(DEFAULT) || getUSD.equals(DEFAULT)){
            Toast.makeText(this,"NO Data Found", Toast.LENGTH_LONG).show();
        }else {
            usd = Double.parseDouble(getUSD);
            bdt = Double.parseDouble(getBDT);
        }
    }

    public void calculation(){
        try{
            if(i1.getTag().equals("bd")){
                double getValue = Double.valueOf(inputTextView.getText().toString());
                getValue = getValue * bdt;
                resultTextView.setText(String.valueOf(getValue));
            }else{
                double getValue = Double.valueOf(inputTextView.getText().toString());
                getValue = getValue * usd;
                resultTextView.setText(String.valueOf(getValue));
            }
        }catch (Exception e){
            Toast.makeText(MainActivity.this, "SomeThing Wrong Long Press Delete Button", Toast.LENGTH_SHORT);
        }
    }


}
