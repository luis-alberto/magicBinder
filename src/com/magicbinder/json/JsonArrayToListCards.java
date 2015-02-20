package com.magicbinder.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.magicbinder.data.ColorSQLiteAdapter;
import com.magicbinder.entity.Card;
import com.magicbinder.entity.Color;

/**
 * Converted JsonArray to List of cards.
 * @author Luis
 *
 */
public class JsonArrayToListCards {
	/**
	 * URL image.
	 */
    protected static final String URL_IMG = "http://mtgimage.com/multiverseid/%s.jpg";
    /**
     * Context of activity.
     */
    private Context context;
    /**
     * ColorSqLiteAdapter.
     */
    private ColorSQLiteAdapter colorAdapter;

    /**
     * Constructor of JsonArrayToListCards.
     * @param context of the activity.
     */
    public JsonArrayToListCards(Context context) {
        this.context = context;
    }

    /**
     * Convert String Json in List of cards.
     * @param json in string format.
     * @return Arraylist of Cards.
     */
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
            e.printStackTrace();
        }
        return listCards;
    }

}
