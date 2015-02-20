package com.magicbinder.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

import android.os.AsyncTask;
import android.util.Log;

/**
 * Get JSONArray data from URL
 * @author Luis
 *
 */
public class JsonArrayFromURL extends AsyncTask<String, String, JSONArray> {
	//Tags,fiels and Components.
	private static final String UTF8 = "utf-8";
	private static final String POST = "POST";
	private static final String GET = "GET";
    List<NameValuePair> postparams = new ArrayList<NameValuePair>();
    String URL = null;
    String method = null;
    static InputStream is = null;
    static JSONArray jArry = null;
    static String json = "";

    /**
     * Constructor.
     * @param url .
     * @param method .
     * @param params .
     */
    public JsonArrayFromURL(String url, String method, List<NameValuePair> params) {
        this.URL = url;
        this.postparams = params;
        this.method = method;
    }

    /**
     * Getting and building JsonArray from API url.
     */
    @Override
    protected JSONArray doInBackground(String... params) {
        // Making HTTP request
        try {
            // Making HTTP request
            // check for request method
            if (method.equals(POST)) {
                // request method is POST
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL);
                httpPost.setEntity(new UrlEncodedFormEntity(postparams));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } else if (method == GET) {
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils
                        .format(postparams, UTF8);
                URL += "?" + paramString;
                HttpGet httpGet = new HttpGet(URL);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }

            // read input stream returned by request into a string using StringBuilder
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, UTF8), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();

            // create a JSONObject from the json string
            jArry = new JSONArray(json);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // return JSONObject (this is a class variable and null is returned if something went bad)
        return jArry;

    }
}
