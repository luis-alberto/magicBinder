package com.magicbinder.view.card;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.toolbox.NetworkImageView;
import com.magicbinder.MagicBinderApplication;
import com.magicbinder.R;
import com.magicbinder.data.BinderSQLiteAdapter;
import com.magicbinder.data.Binder_CardSQLiteAdapter;
import com.magicbinder.data.CardSQLiteAdapter;
import com.magicbinder.data.QualitySQLiteAdapter;
import com.magicbinder.entity.Binder;
import com.magicbinder.entity.Binder_Card;
import com.magicbinder.entity.Card;
import com.magicbinder.entity.Quality;
import com.magicbinder.harmony.view.HarmonyFragment;

/**
 * CardDetailFragment.
 * @author Luis
 *
 */
public class CardDetailFragment extends HarmonyFragment{
    //fiels and components
    private Card card;
    private NetworkImageView iconView;
    private Spinner spinnerBinder;
    private Spinner spinnerQuantity;
    private Spinner spinnerQuality;
    private Button buttonAdd;
    private View view;

    /**
     * Contructor of CardDetailFragment.
     * @param card
     */
    public CardDetailFragment( Card card){
        this.card = card;
    }
    /**
     * Creating view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(
                R.layout.fragment_card_detail, container, false);
        buildImageCard();
        buildBinderSpinner();
        buildQualitySpinner();
        buildQuantitySpinner();
        buildAddButton();
        return view;
    }
    /**
     * Create and bind image card.
     */
    private void buildImageCard(){
        iconView = (NetworkImageView) view.findViewById(R.id.imageCard);
        iconView.setDefaultImageResId(R.drawable.cardback);
        iconView.setImageUrl(card.getImage(), MagicBinderApplication.getInstance().getImageLoader());
    }
    /**
     * Create and bind binder spinner.
     */
    private void buildBinderSpinner() {
        //get all binders from bdd.
        BinderSQLiteAdapter binderSqlAdapter = 
                new BinderSQLiteAdapter(getActivity());
        binderSqlAdapter.open();
        ArrayList<Binder> binderList = binderSqlAdapter.getAll();
        binderSqlAdapter.close();
        //init and bind binder spinner
        spinnerBinder = (Spinner)view.findViewById(R.id.binder);
        ArrayAdapter<Binder> dataAdapter = new ArrayAdapter<Binder>(getActivity(),
                android.R.layout.simple_spinner_item, binderList);
        spinnerBinder.setAdapter(dataAdapter);
    }
    /**
     * Create and bind quality spinner.
     */
    private void buildQualitySpinner() {
        //get all quality from bdd.
        QualitySQLiteAdapter quelitySqlAdapter = 
                new QualitySQLiteAdapter(getActivity());
        quelitySqlAdapter.open();
        ArrayList<Quality> qualityList = quelitySqlAdapter.getAll();
        quelitySqlAdapter.close();
        //init and bind binder spinner
        spinnerQuality = (Spinner)view.findViewById(R.id.quality);
        ArrayAdapter<Quality> dataAdapter = new ArrayAdapter<Quality>(getActivity(),
                android.R.layout.simple_spinner_item, qualityList);
        spinnerQuality.setAdapter(dataAdapter);
    }
    /**
     * Create and bind quantity spinner.
     */
    private void buildQuantitySpinner() {
        //init and bind quality spinner
        spinnerQuantity = (Spinner)view.findViewById(R.id.quantity);
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.quantity_items, android.R.layout.simple_spinner_item);
        spinnerQuantity.setAdapter(dataAdapter);
    }
    /**
     * Create and bind add button.
     */
    private void buildAddButton() {
        //init and bind quality spinner
        buttonAdd = (Button)view.findViewById(R.id.add);
        buttonAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CardSQLiteAdapter cardSqlAdapter = new CardSQLiteAdapter(getActivity());
                Binder_CardSQLiteAdapter binderCardSqlAdapter= new Binder_CardSQLiteAdapter(getActivity());
                //get card information.
                cardSqlAdapter.open();
                int idCard = card.getId();
                Card cardBdd = cardSqlAdapter.getByID(idCard);
                if(cardBdd.getId()==0){
                    cardSqlAdapter.insert(card);
                }
                cardSqlAdapter.close();
                //get binder,quality and quantity information.
                Binder binder = (Binder) spinnerBinder.getSelectedItem();
                Quality quality = (Quality) spinnerQuality.getSelectedItem();
                String str = spinnerQuantity.getSelectedItem().toString();
                int quantity = Integer.parseInt(str);
                //insert card into bdd if no exist.
                binderCardSqlAdapter.open();
                Binder_Card binderCard = binderCardSqlAdapter
                        .getBinderCardByBinderCardQuality(binder,card,quality);
                if (binderCard == null){
                    binderCard = new Binder_Card();
                    binderCard.setBinder(binder);
                    binderCard.setCard(card);
                    binderCard.setQuality(quality);
                    binderCard.setQuantity(quantity);
                    binderCardSqlAdapter.insert(binderCard);
                }else{
                    binderCard.setQuantity(binderCard.getQuantity()+quantity);
                    binderCardSqlAdapter.update(binderCard);
                }
                binderCardSqlAdapter.close();
                getActivity().finish();
            }
        });
    }

}
