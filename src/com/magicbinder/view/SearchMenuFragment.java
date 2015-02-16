package com.magicbinder.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.magicbinder.R;
import com.magicbinder.adapter.SetSpinnerAdapter;
import com.magicbinder.adapter.SimpleSpinnerAdapter;
import com.magicbinder.component.MultiSelectionSpinner;
import com.magicbinder.data.ColorSQLiteAdapter;
import com.magicbinder.entity.Color;
import com.magicbinder.entity.Set;
import com.magicbinder.harmony.view.HarmonyFragment;
import com.magicbinder.webservice.JsonArrayFromURL;
import com.magicbinder.webservice.OpenningSearchActivity;

public class SearchMenuFragment extends HarmonyFragment {
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TYPE_URL = "http://api.mtgdb.info/cards/types";
    private static final String RARITY_URL = "http://api.mtgdb.info/cards/rarity";
    private static final String SET_URL = "http://api.mtgdb.info/sets/";
    private static final String SEARCH_URL = "http://api.mtgdb.info/search/?q=color eq blue and type m 'Creature' and description m 'flying' and convertedmanacost lt 3 and name m 'cloud'";
    private static final String[] COMPARE = {"=","≠","<","≤",">","≥"};

    private ArrayList<String> rarityList = new ArrayList<String>();
    private ArrayList<String> typeCardList = new ArrayList<String>();
    private ArrayList<Set> setIdList = new ArrayList<Set>();
    private List<String> colorList = new ArrayList<String>();
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
    private MultiSelectionSpinner searchColor;
    private Button buttonReset;
    private Button buttonSearch;

    private JsonArrayFromURL jsonArrayFromURL;
    private OpenningSearchActivity openningSearchActivity;
    
    /**
     * Default constructor
     */
    public SearchMenuFragment(){
    }

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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
        (getActivity(), android.R.layout.simple_spinner_item, COMPARE);
        // Specify the layout to use when the list of choices appears
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        apiParams.add(new BasicNameValuePair("call", "typeCardlist"));

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
        apiParams.add(new BasicNameValuePair("call", "rarityList"));

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
        apiParams.add(new BasicNameValuePair("call", "rarityList"));

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
                            android.R.layout.simple_spinner_item, setIdList);
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
        ArrayList<Color> colorArray = colorSqlAdapter.getAll();
        colorSqlAdapter.close();
        //send all colors label to string list
        for (Color color : colorArray) {
            colorList.add(color.getLabel());
        }
        //init and bind color multiselection spinner
        searchColor = 
                (MultiSelectionSpinner)view.findViewById(R.id.search_colors);
        searchColor.setItems(colorList);
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
                (MultiSelectionSpinner) view.findViewById(R.id.search_colors);
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
    
    private void buildSearchButton(){
        buttonSearch = (Button) view.findViewById(R.id.search_search);
      //implement on click of reset button 
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Building post parameters, key and value pair
                List<NameValuePair> apiParams = new ArrayList<NameValuePair>(0);
                apiParams.add(new BasicNameValuePair("call", "rarityList"));

                openningSearchActivity = new OpenningSearchActivity(getActivity());
                openningSearchActivity.execute(SEARCH_URL);
            }
        });
    }
}
