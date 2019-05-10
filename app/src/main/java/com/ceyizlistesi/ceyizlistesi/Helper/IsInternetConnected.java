package com.ceyizlistesi.ceyizlistesi.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

public class IsInternetConnected extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private InternetCheckListener listener;

    public IsInternetConnected(Context context, InternetCheckListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }



        if (isAvailable) {

            try{
                HttpURLConnection connection = (HttpURLConnection) (new URL("http://www.google.com").openConnection());

                connection.setRequestProperty("User-Agent", "Test");
                connection.setRequestProperty("Connection", "close");
                connection.setConnectTimeout(1000); //choose your own timeframe
                connection.setReadTimeout(2000); //choose your own timeframe

                connection.connect();

                int responseCode = connection.getResponseCode();


                if (responseCode == 200) { //Connection OK
                    return true;
                } else {

                    return  false;
                }
            }catch (Exception e){

                return  false; //connectivity exists, but no internet.
            }
        } else {

            return  false; //no connectivity
        }
    }

    @Override
    protected void onPostExecute(Boolean isConnected) {
        super.onPostExecute(isConnected);
        listener.onInternetConnect(isConnected);
    }
}