package com.magicbinder.webservice;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.magicbinder.MagicBinderApplication;
import com.magicbinder.data.ColorSQLiteAdapter;
import com.magicbinder.entity.Card;
import com.magicbinder.entity.Color;

public class WebService {
    protected static final String URL_IMG = "http://api.mtgdb.info/content/hi_res_card_images/%s.jpg";
    protected static final String TAG = "JsonToListCards";
    protected static final String URL_GET_TYPES = "http://api.mtgdb.info/cards/types";
    private Context context;
    private ProgressDialog progressDialog;
    private ColorSQLiteAdapter colorAdapter;
    private ArrayList<Card> listCards;
    private ArrayList<String> types;

    /**
     * Constructor 
     * @param context
     */
    public WebService(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
    }
    /**
     * Get and transform and url webservice into a list of cards
     * @param url String URL from webservice.
     * @return ArrayList<Card>
     */
    public ArrayList<Card> SearchCards(String url) {
        listCards = new ArrayList<Card>();
        colorAdapter = new ColorSQLiteAdapter(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        showProgressDialog();
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                try {
                    Card card = new Card();
                    JSONObject json;
                    ArrayList<Color> listColors = new ArrayList<Color>();
                    Color color = new Color();
                    for (int i = 0; i < response.length(); i++) {
                        //init listColors for each response
                        listColors.clear();
                        //init jsonObject
                        json = (JSONObject) response.get(i);
                        //set card values
                        card.setId(json.getInt("id"));
                        card.setName(json.getString("name"));
                        card.setName(String.format(URL_IMG, json.getString("id")));
                        card.setConvertedManaCost(json.getInt("convertedManaCost"));
                        card.setTypeCard(json.getString("type"));
                        card.setRarity(json.getString("rarity"));
                        card.setCardSetId(json.getString("cardSetId"));
                        String[] colors = (String[]) json.get("colors");
                        colorAdapter.open();
                        for (int j = 0; j < colors.length; j++) {
                            listColors.add(colorAdapter.getByLabel(colors[j]));
                        }
                        colorAdapter.close();
                        card.setColors(listColors);
                        listCards.add(card);}
                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hideProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideProgressDialog();
            }
        });
        // Adding request to request queue
        MagicBinderApplication.getInstance().getRequestQueue().add(req);
        return listCards;
    }
    /**
     * Get all types from API
     * @return String[] all types
     */
    public ArrayList<String> getAllTypes(){
        types.clear();
        JsonArrayRequest req = new JsonArrayRequest(URL_GET_TYPES,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                
                try {
                    String res;
                    for (int i = 0; i < response.length(); i++){
                        res=response.get(i).toString();
                        if(res!="")
                            types.add(res);
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // Adding request to request queue
        MagicBinderApplication.getInstance().getRequestQueue().add(req);
        return types;
    }
    /**
     * Show Progress Dialog
     */
    private void showProgressDialog() {
        if (!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    /**
     * Hide Progress Dialog
     */
    private void hideProgressDialog() {
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
