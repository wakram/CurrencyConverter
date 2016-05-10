package com.example.wahid.currencyconverter;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Wahid on 3/29/2016.
 */
public class DataCollectService extends IntentService {
    private final String sourceUrl= "http://hrhafiz.com/converter/";
    public DataCollectService() {
        super("test");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String test = "";
        try{
            URL url = new URL(sourceUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            int byteData = inputStreamReader.read();
            while (byteData != -1){
                char currentData = (char) byteData;
                byteData = inputStreamReader.read();
                test += currentData;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("test", test);
        getData(test);
    }

    public void getData(String str){
        double [] a = new double[2];
        int i = 0;
        Matcher m = Pattern.compile("(?!=\\d\\.\\d\\.)([\\d.]+)").matcher(str);
        while (m.find())
        {
            a[i] = Double.parseDouble(m.group(1));
            i++;
        }
        if(isNetworkAvailable()){
            SharedPreferences sharedPreferences = getSharedPreferences("WebData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("USD", String.valueOf(a[0]));
            editor.putString("BDT", String.valueOf(a[1]));
            editor.commit();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
