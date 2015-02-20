package com.magicbinder.view.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.magicbinder.R;
import com.magicbinder.adapter.SetSpinnerAdapter;
import com.magicbinder.adapter.SimpleSpinnerAdapter;
import com.magicbinder.data.ColorSQLiteAdapter;
import com.magicbinder.entity.Color;
import com.magicbinder.entity.Set;
import com.magicbinder.harmony.view.HarmonyFragment;
import com.magicbinder.webservice.JsonArrayFromURL;
import com.magicbinder.webservice.OpenningSearchActivity;

public class SearchMenuFragment extends HarmonyFragment {
	//tag and converted strings.
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TYPE_URL = "http://api.mtgdb.info/cards/types";
    private static final String RARITY_URL = "http://api.mtgdb.info/cards/rarity";
    private static final String SET_URL = "http://api.mtgdb.info/sets/";
    private static final String SEARCH_URL = "http://api.mtgdb.info/search/?q=%s";
    private static final String STRING_AND = "%s and ";
    private static final String STRING_STRING = "%s %s";
    private static final String NAME_URL = "name m '%s'";
    private static final String DESCRIP_URL = "description m '%s'";
    private static final String COLOR_URL = "color m '%s'";
    private static final String MANACOST_URL = "manacost eq '%s'";
    private static final String CONVERTEDMANACOST_URL = "convertedmanacost %s '%s'";
    private static final String TYPECARD_URL = "type eq '%s'";
    private static final String SUBTYPE_URL = "subtype m '%s'";
    private static final String POWER_URL = "power %s '%s'";
    private static final String TOUGHNESS_URL = "toughness %s '%s'";
    private static final String LOYALTY_URL = "loyalty %s '%s'";
    private static final String RARITYCARD_URL = "rarity eq '%s'";
    private static final String ARTIST_URL = "artist m '%s'";
    private static final String SETCARD_URL = "setId eq '%s'";
    private static final String[] COMPARE = {"=","≠","<","≤",">","≥"};
    private static final String CALL = "call";
    private static final String TYPECARDLIST = "typeCardlist";
    private static final String RARITYLIST = "rarityList";
    private static final String SETLIST = "setList";
    private static final String CARDLIST = "cardList";

    //Components of fragment.
    private ArrayList<String> rarityList = new ArrayList<String>();
    private ArrayList<String> typeCardList = new ArrayList<String>();
    private ArrayList<Set> setIdList = new ArrayList<Set>();
    private ArrayList<String> colorList = new ArrayList<String>();
    private View view;
    private TextView searchName;
    private TextView searchDescription;
    private TextView searchManaCost;
    private TextView searchConvertedManaCost;
    private TextView searchSubType;
    private TextView searchPower;
    private TextView searchToughness;
    private TextView searchLoyalty;
    private TextView searchArtist;
    private Spinner searchType;
    private Spinner searchRarity;
    private Spinner searchSet;
    private Spinner spinnerCompareConvertedManaCost;
    private Spinner spinnerComparePower;
    private Spinner spinnerCompareToughness;
    private Spinner spinnerCompareLoyalty;
    private Spinner searchColor;
    private Button buttonReset;
    private Button buttonSearch;

    private JsonArrayFromURL jsonArrayFromURL;
    private OpenningSearchActivity openningSearchActivity;
    
    /**
     * Default constructor
     */
    public SearchMenuFragment(){
    }

    /**
     * Creating view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_search_menu, 
                container, false);
        buildComparesSpinners();
        buildTypeSpinner();
        buildRaritySpinner();
        buildSetSpinner();
        buildColorSpinner();
        buildResetButton();
        buildSearchButton();

        return view;
    }
    
    /**
     * Build all compare spinners
     */
    private void buildComparesSpinners() {
        // TODO Auto-generated method stub
        spinnerComparePower = (Spinner) view.findViewById(R.id.compare_power);
        spinnerCompareToughness = (Spinner) view.findViewById(R.id.compare_toughness);
        spinnerCompareLoyalty = (Spinner) view.findViewById(R.id.compare_loyalty);
        spinnerCompareConvertedManaCost = (Spinner) view.findViewById(R.id.compare_convertedManaCost);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
        		getActivity(), android.R.layout.simple_list_item_1, COMPARE);
        // Specify the layout to use when the list of choices appears
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        // Apply the adapter to the spinner
        spinnerCompareConvertedManaCost.setAdapter(arrayAdapter);
        spinnerComparePower.setAdapter(arrayAdapter);
        spinnerCompareToughness.setAdapter(arrayAdapter);
        spinnerCompareLoyalty.setAdapter(arrayAdapter);
    }
    /**
     * Build type spinner
     */
    private void buildTypeSpinner() {
        // Building post parameters, key and value pair
        List<NameValuePair> apiParams = new ArrayList<NameValuePair>(0);
        apiParams.add(new BasicNameValuePair(CALL, TYPECARDLIST));

        jsonArrayFromURL = new JsonArrayFromURL(TYPE_URL, "GET", apiParams);

        try {
            JSONArray typeCardJSON = jsonArrayFromURL.execute().get();
            //add empty type to list of strings
            typeCardList.add("");
            // looping through All types
            for (int i = 0; i < typeCardJSON.length(); i++) {

                String name = typeCardJSON.get(i).toString();

                // add typeCard to list
                typeCardList.add(name);
            }
            Collections.sort(typeCardList);
            // bind adapter to spinner
            searchType = (Spinner) view.findViewById(R.id.search_type);
            SimpleSpinnerAdapter spinnerAdapter = 
                    new SimpleSpinnerAdapter(getActivity(), 
                            android.R.layout.simple_list_item_1, typeCardList);
            searchType.setAdapter(spinnerAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    /**
     * Build rarity spinner
     */
    private void buildRaritySpinner() {
        // Building post parameters, key and value pair
        List<NameValuePair> apiParams = new ArrayList<NameValuePair>(0);
        apiParams.add(new BasicNameValuePair(CALL,RARITYLIST));

        jsonArrayFromURL = new JsonArrayFromURL(RARITY_URL, "GET", apiParams);

        try {
            JSONArray typeCardJSON = jsonArrayFromURL.execute().get();
            //add a empty rarity to list of Strings
            rarityList.add("");
            // looping through All rarities
            for (int i = 0; i < typeCardJSON.length(); i++) {

                String name = typeCardJSON.get(i).toString();

                // add rarity to list
                rarityList.add(name);
            }
            Collections.sort(rarityList);
            // bind adapter to spinner
            searchRarity = (Spinner) view.findViewById(R.id.search_rarity);
            SimpleSpinnerAdapter spinnerAdapter = 
                    new SimpleSpinnerAdapter(getActivity(), 
                            android.R.layout.simple_list_item_1, rarityList);
            searchRarity.setAdapter(spinnerAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    /**
     * Build set spinner
     */
    private void buildSetSpinner() {
        // Building post parameters, key and value pair
        List<NameValuePair> apiParams = new ArrayList<NameValuePair>(0);
        apiParams.add(new BasicNameValuePair(CALL, SETLIST));

        jsonArrayFromURL = new JsonArrayFromURL(SET_URL, "GET", apiParams);

        try {
            JSONArray typeSetJSON = jsonArrayFromURL.execute().get();
            //add a empty set to list of sets
            setIdList.add(new Set("",""));
            JSONObject json;
            // looping through All rarities
            for (int i = 0; i < typeSetJSON.length(); i++) {
                json = (JSONObject) typeSetJSON.get(i);
                // add Set to list of sets
                setIdList.add(new Set(json.getString(TAG_ID),
                        json.getString(TAG_NAME)));
            }

            // bind adapter to spinner
            searchSet = (Spinner) view.findViewById(R.id.search_set);
            SetSpinnerAdapter spinnerAdapter = 
                    new SetSpinnerAdapter(getActivity(), 
                            android.R.layout.simple_list_item_1, setIdList);
            searchSet.setAdapter(spinnerAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    /**
     * Build multi-select color spinner
     */
    private void buildColorSpinner() {
        //get all colors from bdd
        ColorSQLiteAdapter colorSqlAdapter = 
                new ColorSQLiteAdapter(getActivity());
        colorSqlAdapter.open();
        colorList.add("");
        ArrayList<Color> colorArray = colorSqlAdapter.getAll();
        colorSqlAdapter.close();
        //send all colors label to string list
        for (Color color : colorArray) {
            colorList.add(color.getLabel());
        }
        //init and bind color spinner
        searchColor = (Spinner)view.findViewById(R.id.search_colors);
        SimpleSpinnerAdapter spinnerAdapter = 
                new SimpleSpinnerAdapter(getActivity(), 
                        android.R.layout.simple_list_item_1, colorList);
        searchColor.setAdapter(spinnerAdapter);
    }
    /**
     * Build reset button
     */
    private void buildResetButton(){
        //init all fields to reset
        buttonReset = (Button) view.findViewById(R.id.search_reset);
        searchName = (TextView) view.findViewById(R.id.search_name);
        searchDescription = (TextView) view.findViewById(R.id.search_text);
        searchColor = 
                (Spinner) view.findViewById(R.id.search_colors);
        searchManaCost = (TextView) view.findViewById(R.id.search_manaCost); 
        searchConvertedManaCost = 
                (TextView) view.findViewById(R.id.search_convertedManaCost);
        searchType = (Spinner) view.findViewById(R.id.search_type);
        searchSubType = (TextView) view.findViewById(R.id.search_subType);
        searchPower = (TextView) view.findViewById(R.id.search_power);
        searchToughness = (TextView) view.findViewById(R.id.search_toughness);
        searchLoyalty = (TextView) view.findViewById(R.id.search_loyalty);
        searchRarity = (Spinner) view.findViewById(R.id.search_rarity);
        searchArtist = (TextView) view.findViewById(R.id.search_artist);
        searchSet = (Spinner) view.findViewById(R.id.search_set);
        spinnerComparePower = (Spinner) view.findViewById(R.id.compare_power);
        spinnerCompareToughness = 
                (Spinner) view.findViewById(R.id.compare_toughness);
        spinnerCompareLoyalty = 
                (Spinner) view.findViewById(R.id.compare_loyalty);
        spinnerCompareConvertedManaCost = 
                (Spinner) view.findViewById(R.id.compare_convertedManaCost);

        //implement on click of reset button 
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset all fields
                searchName.setText("");
                searchDescription.setText("");
                searchManaCost.setText("");
                searchConvertedManaCost.setText("");
                searchSubType.setText("");
                searchPower.setText("");
                searchToughness.setText("");
                searchLoyalty.setText("");
                searchArtist.setText("");
                searchColor.setSelection(0);
                searchType.setSelection(0);
                searchRarity.setSelection(0);
                searchSet.setSelection(0);
                spinnerCompareConvertedManaCost.setSelection(0);
                spinnerCompareLoyalty.setSelection(0);
                spinnerComparePower.setSelection(0);
                spinnerCompareToughness.setSelection(0);

            }
        });
    }
    
    /**
     * Build search button.
     */
    private void buildSearchButton(){
        buttonSearch = (Button) view.findViewById(R.id.search_search);
      //implement on click of reset button 
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildSearchUrl();
                if(url!=null){
                    // Building post parameters, key and value pair
                    List<NameValuePair> apiParams = new ArrayList<NameValuePair>(0);
                    apiParams.add(new BasicNameValuePair(CALL, CARDLIST));
                    openningSearchActivity = new OpenningSearchActivity(getActivity());
                    openningSearchActivity.execute(url);
                }
            }
        });
    }
    /**
     * Build search URL.
     * @return search url to request.
     */
    private String buildSearchUrl(){
        String url="";
        if (nameRequest()!= null){
            if(!url.isEmpty())
                url = String.format(STRING_AND, url);
            url = String.format(STRING_STRING, url,nameRequest());
        }
        if (descriptionRequest()!= null){
            if(!url.isEmpty())
                url = String.format(STRING_AND, url);
            url = String.format(STRING_STRING, url,descriptionRequest());
        }
        if (colorRequest()!= null){
            if(!url.isEmpty())
                url = String.format(STRING_AND, url);
            url = String.format(STRING_STRING, url,colorRequest());
        }
        if (manaCostRequest()!= null){
            if(!url.isEmpty())
                url = String.format(STRING_AND, url);
            url = String.format(STRING_STRING, url,manaCostRequest());
        }
        if (convertedManaCostRequest()!= null){
            if(!url.isEmpty())
                url = String.format(STRING_AND, url);
            url = String.format(STRING_STRING, url,convertedManaCostRequest());
        }
        if (typeRequest()!= null){
            if(!url.isEmpty())
                url = String.format(STRING_AND, url);
            url = String.format(STRING_STRING, url,typeRequest());
        }
        if (subTypeRequest()!= null){
            if(!url.isEmpty())
                url = String.format(STRING_AND, url);
            url = String.format(STRING_STRING, url,subTypeRequest());
        }
        if (powerRequest()!= null){
            if(!url.isEmpty())
                url = String.format(STRING_AND, url);
            url = String.format(STRING_STRING, url,powerRequest());
        }
        if (toughnessRequest()!= null){
            if(!url.isEmpty())
                url = String.format(STRING_AND, url);
            url = String.format(STRING_STRING, url,toughnessRequest());
        }
        if (loyaltyRequest()!= null){
            if(!url.isEmpty())
                url = String.format(STRING_AND, url);
            url = String.format(STRING_STRING, url,loyaltyRequest());
        }
        if (rarityRequest()!= null){
            if(!url.isEmpty())
                url = String.format(STRING_AND, url);
            url = String.format(STRING_STRING, url,rarityRequest());
        }
        if (artistRequest()!= null){
            if(!url.isEmpty())
                url = String.format(STRING_AND, url);
            url = String.format(STRING_STRING, url,artistRequest());
        }
        if (setRequest()!= null){
            if(!url.isEmpty())
                url = String.format(STRING_AND, url);
            url = String.format(STRING_STRING, url,setRequest());
        }
        if (!url.isEmpty()){
            return String.format(SEARCH_URL, url);
        }
        return null;
        
    }
    /**
     * Build name piece of url search.
     * @return name url search.
     */
    private String nameRequest(){
        String name = searchName.getText().toString();
        if(!name.replace(" ", "").isEmpty()){
            return String.format(NAME_URL,name);
        }
        return null;
    }
    /**
     * Build description piece of URL search.
     * @return description url search.
     */
    private String descriptionRequest(){
        String description = searchDescription.getText().toString();
        if(!description.replace(" ", "").isEmpty()){
            return String.format(DESCRIP_URL,description);
        }
        return null;
    }

    /**
     * Build color piece of url search.
     * @return color url search.
     */
    private String colorRequest(){
        String color = searchColor.getSelectedItem().toString();
        if(!color.isEmpty()){
            return String.format(COLOR_URL,color);
        }
        return null;
    }
    
    /**
     * Build mana cost piece of url search.
     * @return mana cost url search.
     */
    private String manaCostRequest(){
        String manaCost = searchManaCost.getText().toString().replace(" ", "");
        if(!manaCost.isEmpty()){
            return String.format(MANACOST_URL,manaCost);
        }
        return null;
    }
    
    /**
     * Build converted mana cost piece of url search.
     * @return conveted mana cost url search.
     */
    private String convertedManaCostRequest(){
        String convertedManaCost = searchConvertedManaCost.getText().toString().replace(" ", "");
        String compare = compareSpinnerToString(spinnerCompareConvertedManaCost.getSelectedItemPosition());
        if(!convertedManaCost.isEmpty()){
            return String.format(CONVERTEDMANACOST_URL,compare,convertedManaCost);
        }
        return null;
    }
    
    /**
     * Build type piece of url search.
     * @return type url search.
     */
    private String typeRequest(){
        String type = searchType.getSelectedItem().toString();
        if(!type.isEmpty()){
            return String.format(TYPECARD_URL,type);
        }
        return null;
    }
    
    /**
     * Build subtype piece of url search.
     * @return subtype url search.
     */
    private String subTypeRequest(){
        String subType = searchSubType.getText().toString();
        if(!subType.replace(" ", "").isEmpty()){
            return String.format(SUBTYPE_URL,subType);
        }
        return null;
    }
    
    /**
     * Build power piece of url search.
     * @return power url search.
     */
    private String powerRequest(){
        String power = searchPower.getText().toString().replace(" ", "");
        String compare = compareSpinnerToString(spinnerComparePower.getSelectedItemPosition());
        if(!power.isEmpty()){
            return String.format(POWER_URL,compare,power);
        }
        return null;
    }
    
    /**
     * Build toughness piece of url search.
     * @return toughness url search.
     */
    private String toughnessRequest(){
        String toughness = searchToughness.getText().toString().replace(" ", "");
        String compare = compareSpinnerToString(spinnerCompareToughness.getSelectedItemPosition());
        if(!toughness.isEmpty()){
            return String.format(TOUGHNESS_URL,compare,toughness);
        }
        return null;
    }
    
    /**
     * Build loyalty piece of url search.
     * @return loyalty url search.
     */
    private String loyaltyRequest(){
        String loyalty = searchLoyalty.getText().toString().replace(" ", "");
        String compare = compareSpinnerToString(spinnerCompareLoyalty.getSelectedItemPosition());
        if(!loyalty.isEmpty()){
            return String.format(LOYALTY_URL,compare,loyalty);
        }
        return null;
    }
    /**
     * Build rarity piece of url search.
     * @return rarity url search.
     */
    private String rarityRequest(){
        String rarity = searchRarity.getSelectedItem().toString();
        if(!rarity.isEmpty()){
            return String.format(RARITYCARD_URL,rarity);
        }
        return null;
    }
    /**
     * Build artist piece of url search.
     * @return artist url search.
     */
    private String artistRequest(){
        String artist = searchArtist.getText().toString();
        if(!artist.replace(" ", "").isEmpty()){
            return String.format(ARTIST_URL,artist);
        }
        return null;
    }
    /**
     * Build set piece of url search.
     * @return set url search.
     */
    private String setRequest(){
        Set set = (Set) searchSet.getSelectedItem();
        if(!set.getId().isEmpty()){
            return String.format(SETCARD_URL,set.getId());
        }
        return null;
    }
    /**
     * Convert simbole of spinner to string.
     * @param position of spinner.
     * @return string equivalation.
     */
    private String compareSpinnerToString(int position){
        switch (position) {
        case 0:
            return this.getString(R.string.eq);
        case 1:
            return this.getString(R.string.not);
        case 2:
            return this.getString(R.string.lt);
        case 3:
            return this.getString(R.string.lte);
        case 4:
            return this.getString(R.string.gt);
        case 5:
            return this.getString(R.string.gte);
        default:
            break;
        }
        return null;
    }
}
