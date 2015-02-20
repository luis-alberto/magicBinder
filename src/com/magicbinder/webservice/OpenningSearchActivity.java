package com.magicbinder.webservice;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.magicbinder.view.search.SearchListActivity;

/**
 * OpenningSearchActivity.
 * @author Luis
 *
 */
public class OpenningSearchActivity extends AsyncTask<String,Void,String> {
	//Tags.
    private final static String SEARCHING = "Searching....";
    private final static String MESSAGE = "message";
    private final static String NO_RESULT = "Sorry, there is not results!";
    private final static String MANY_RESULT = "Sorry, so many results!";
    private final static String URL = "%s&total=true";
    //Fiels and components.
    private ProgressDialog progressDialog;
    private Activity context;

    /**
     * Contructor od OpenningSearchActivity.
     * @param context of activity.
     */
    public OpenningSearchActivity(Activity context) {
        this.context = context;
    }

    /**
     * onPreExecute traitement.
     */
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(SEARCHING);
        progressDialog.show();
    }
    /**
     * Search for API data.
     */
    @Override
    protected String doInBackground(String... urls) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbCount = new StringBuilder();
        HttpURLConnection urlConnection = null;
        InputStream inputStream;
        try {
            URL url = new URL(urls[0]);
            URL urlCount = new URL(String.format(URL, url));

            // Open connection
            urlConnection = (HttpURLConnection) urlCount.openConnection();
            urlConnection.setChunkedStreamingMode(0);

            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            if (inputStream != null){
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;

                // Read Server Response
                while((line = br.readLine()) != null){
                    // Append server response in string
                    sbCount.append(line + "");
                }
            }
            String str = sbCount.toString();
            int count = Integer.parseInt(str);
            if(count<50){
             // Open connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setChunkedStreamingMode(0);
    
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                if (inputStream != null){
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    String line = null;
    
                    // Read Server Response
                    while((line = br.readLine()) != null){
                        // Append server response in string
                        sb.append(line + "");
                    }
                }
            }else{
                return null;
            }

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
    /**
     * onPostExecute traitement.
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(result != null){
            JSONArray json = null;
            try {
                json = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int length = json.length();
            if(length>0){
                Intent intent = new Intent(context, SearchListActivity.class);
                intent.putExtra(MESSAGE, result);
                context.startActivityForResult(intent, 0);
                progressDialog.dismiss();
            }else{
                progressDialog.dismiss();
                Toast.makeText(context, NO_RESULT,Toast.LENGTH_SHORT).show();
            }
        }else{
                progressDialog.dismiss();
                Toast.makeText(context, MANY_RESULT,Toast.LENGTH_SHORT).show();
        }
    }
}
