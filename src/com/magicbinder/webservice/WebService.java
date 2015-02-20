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

/**
 * WebService.
 * @author Luis
 *
 */
public class WebService {
	//Tags and string.
	private static final String URL_IMG = "http://api.mtgdb.info/content/hi_res_card_images/%s.jpg";
    private static final String TAG = "JsonToListCards";
    private static final String URL_GET_TYPES = "http://api.mtgdb.info/cards/types";
    private static final String WAIT = "Please wait...";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CONVERTEDMANACOST = "convertedManaCost";
    private static final String TYPE = "type";
    private static final String RARITY = "rarity";
    private static final String CARD_SET_ID = "cardSetId";
	private static final String COLORS = "colors";
	//fiels and composants.
    private Context context;
    private ProgressDialog progressDialog;
    private ColorSQLiteAdapter colorAdapter;
    private ArrayList<Card> listCards;
    private ArrayList<String> types;

    /**
     * Constructor. 
     * @param context .
     */
    public WebService(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
    }
    /**
     * Get and transform and url webservice into a list of cards.
     * @param url String URL from webservice.
     * @return ArrayList<Card>.
     */
    public ArrayList<Card> SearchCards(String url) {
        listCards = new ArrayList<Card>();
        colorAdapter = new ColorSQLiteAdapter(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(WAIT);
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
                    for (int i = 0; i < response.length(); i++) {
                        //init listColors for each response
                        listColors.clear();
                        //init jsonObject
                        json = (JSONObject) response.get(i);
                        //set card values
                        card.setId(json.getInt(ID));
                        card.setName(json.getString(NAME));
                        card.setName(String.format(URL_IMG, json.getString(ID)));
                        card.setConvertedManaCost(json.getInt(CONVERTEDMANACOST));
                        card.setTypeCard(json.getString(TYPE));
                        card.setRarity(json.getString(RARITY));
                        card.setCardSetId(json.getString(CARD_SET_ID));
                        String[] colors = (String[]) json.get(COLORS);
                        colorAdapter.open();
                        for (int j = 0; j < colors.length; j++) {
                            listColors.add(colorAdapter.getByLabel(colors[j]));
                        }
                        colorAdapter.close();
                        card.setColors(listColors);
                        listCards.add(card);
                        }
                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hideProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, error.getMessage());
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
     * Get all types from API.
     * @return String[] all types.
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
                    Toast.makeText(context,e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG,error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // Adding request to request queue
        MagicBinderApplication.getInstance().getRequestQueue().add(req);
        return types;
    }
    /**
     * Show Progress Dialog.
     */
    private void showProgressDialog() {
        if (!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    /**
     * Hide Progress Dialog.
     */
    private void hideProgressDialog() {
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
