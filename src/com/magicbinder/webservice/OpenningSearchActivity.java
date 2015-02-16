package com.magicbinder.webservice;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.magicbinder.Test1;
import com.magicbinder.view.search.SearchListActivity;

public class OpenningSearchActivity extends AsyncTask<String,Void,String> {
    private final static String SEARCHING = "Searching....";
    private final static String MESSAGE = "message";
    private ProgressDialog progressDialog;
    Activity context;

    public OpenningSearchActivity(Activity context) {
        this.context = context;
    }
    
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(SEARCHING);
        progressDialog.show();
    }
    @Override
    protected String doInBackground(String... urls) {
        StringBuilder sb = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urls[0]);

            // Open connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setChunkedStreamingMode(0);

            // Test connection to server
//            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                if (inputStream != null){
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    
                    String line = null;

                    // Read Server Response
                    while((line = br.readLine()) != null)
                    {
                        // Append server response in string
                        sb.append(line + "");
                    }

                    // Append Server Response To Content String 
                    Log.d("string web service", sb.toString());
                }
//            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if (urlConnection!=null){
                urlConnection.disconnect();
            }
        }
        return sb.toString();
    }
    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        JSONArray json = null;
        try {
            json = new JSONArray(result);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        progressDialog.dismiss();
        Toast.makeText(context, String.valueOf(json.length()),Toast.LENGTH_SHORT).show();
        
        
        Intent intent = new Intent(context, SearchListActivity.class);
        intent.putExtra(MESSAGE, result);
        context.startActivityForResult(intent, 0);
        
        
    }
}
