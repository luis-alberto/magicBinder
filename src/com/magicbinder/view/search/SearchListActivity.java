package com.magicbinder.view.search;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.magicbinder.R;
import com.magicbinder.entity.Card;
import com.magicbinder.harmony.view.HarmonyFragmentActivity;
import com.magicbinder.json.JsonArrayToListCards;

public class SearchListActivity extends HarmonyFragmentActivity{
    private final static String MESSAGE = "message";
    private String searchResult;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        intent = getIntent();
        searchResult = intent.getStringExtra(MESSAGE);
        JsonArrayToListCards json = new JsonArrayToListCards(this);
        ArrayList<Card> listCards = json.getListCard(searchResult);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new SearchListAdapter(this,listCards));
    }

}
