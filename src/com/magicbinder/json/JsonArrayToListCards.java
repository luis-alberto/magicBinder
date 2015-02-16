package com.magicbinder.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.magicbinder.data.ColorSQLiteAdapter;
import com.magicbinder.entity.Card;
import com.magicbinder.entity.Color;

public class JsonArrayToListCards {
    protected static final String URL_IMG = "http://api.mtgdb.info/content/hi_res_card_images/%s.jpg";
    private Context context;
    private ColorSQLiteAdapter colorAdapter;

    public JsonArrayToListCards(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public ArrayList<Card> getListCard(String json){
        ArrayList<Card> listCards = new ArrayList<Card>();
        try {
            JSONArray jArr = new JSONArray(json);
            
            for (int i = 0; i < jArr.length(); i++) {
                Card card = new Card();
                ArrayList<Color> listColors = new ArrayList<Color>();
                JSONObject jobj;
                jobj = (JSONObject) jArr.get(i);
                //set card values
                card.setId(jobj.getInt("id"));
                card.setName(jobj.getString("name"));
                card.setImage(String.format(URL_IMG, jobj.getString("id")));
                card.setConvertedManaCost(jobj.getInt("convertedManaCost"));
                card.setTypeCard(jobj.getString("type"));
                card.setRarity(jobj.getString("rarity"));
                card.setCardSetId(jobj.getString("cardSetId"));
                colorAdapter = new ColorSQLiteAdapter(context);
                colorAdapter.open();
                JSONArray colorsJson = jobj.getJSONArray("colors");
                for (int j = 0; j < colorsJson.length(); j++) {
                    String color = colorsJson.get(j).toString();
                    listColors.add(colorAdapter.getByLabel(color));
                }
                colorAdapter.close();
                card.setColors(listColors);
                listCards.add(card);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return listCards;
    }

}
