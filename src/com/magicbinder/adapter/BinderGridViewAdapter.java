package com.magicbinder.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.magicbinder.MagicBinderApplication;
import com.magicbinder.R;
import com.magicbinder.data.Binder_CardSQLiteAdapter;
import com.magicbinder.data.CardSQLiteAdapter;
import com.magicbinder.data.QualitySQLiteAdapter;
import com.magicbinder.entity.Binder;
import com.magicbinder.entity.Binder_Card;
import com.magicbinder.entity.Card;
import com.magicbinder.entity.Quality;
import com.magicbinder.view.binder.BinderCardDetailActivity;

/**
 * BinderGridViewAdapter
 * @author Luis
 *
 */
public class BinderGridViewAdapter extends BaseAdapter{
	/**
	 * Show quality and quantity selected.
	 */
	private static final String QUALITY_QUANTITY = "Quantity:%s  Quality:%s";
	/**
	 * Tag of first putExtra message.
	 */
	private static final String MESSAGE1 = "message1";
	/**
	 * Tag of second putExtra message.
	 */
	private static final String POSITION = "position";
	/**
	 * Activity luched.
	 */
    private Activity context;
    /**
     * Array Binder_Card for a binder.
     */
    private ArrayList<Binder_Card> binderCards;

    /**
     * Contructor of BinderGridViewAdapter.
     * @param context of activity lunched.
     * @param binder selected to show.
     */
    public BinderGridViewAdapter(Activity context, Binder binder) {
        this.context = context;
        //getting all binder_cards from binder.
        Binder_CardSQLiteAdapter binderCardSqlAdapter = new Binder_CardSQLiteAdapter(context);
        binderCardSqlAdapter.open();
        this.binderCards = binderCardSqlAdapter.getAllBinderCardsByBinder(binder);
        binderCardSqlAdapter.close();
    }
    /**
     * Count binder_cards in the binder
     * @return numbers of binder_card in the binder. 0 if no binder_card.
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if(binderCards!=null){
            return binderCards.size();
        }
        return 0;
    }

    /**
     * Get binder_card in a position of binder.
     * @param position in the binder.
     * @return binder_card in binder position. Null if no binder_card.
     */
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        if(binderCards!=null){
            return binderCards.get(position);
        }
        return null;
    }

    /**
     * Get id from item in binder position.
     * @param position in the binder
     * @return id of binder_card in binder position. 0 if no binder_card.
     */
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        if(binderCards!=null){
            return binderCards.get(position).getId();
        }
        return 0;
    }

    /**
     * Creation view of Binder.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        //binder view.
        if(convertView==null){
            grid = new View(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            grid=inflater.inflate(R.layout.grid_card, parent, false);
        }else{
            grid = (View)convertView;
        }
        //construction of components of view.
        Binder_Card binderCardTemp = binderCards.get(position);
        CardSQLiteAdapter cardSqlAdapter = new CardSQLiteAdapter(context);
        QualitySQLiteAdapter qualitySqlAdapter = new QualitySQLiteAdapter(context);
        //getting card.
        cardSqlAdapter.open();
        Card card = cardSqlAdapter.getByID(binderCardTemp.getCard().getId());
        cardSqlAdapter.close();
        //getting quality.
        qualitySqlAdapter.open();
        Quality quality = qualitySqlAdapter.getByID(binderCardTemp.getQuality().getId());
        qualitySqlAdapter.close();
        int quantity = binderCardTemp.getQuantity();
        //construction of card image.
        NetworkImageView imagePart = (NetworkImageView) grid.findViewById(R.id.imagepart);
        imagePart.setDefaultImageResId(R.drawable.cardback);
        imagePart.setImageUrl(card.getImage(), MagicBinderApplication.getInstance().getImageLoader());
        imagePart.setOnClickListener(new View.OnClickListener() {
        	/**
        	 * Onclick Image to start BinderCardDetailActivity.
        	 */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BinderCardDetailActivity.class);
                intent.putParcelableArrayListExtra(MESSAGE1, binderCards);
                intent.putExtra(POSITION, position);
                context.startActivity(intent);
            }

          });
        TextView textView = (TextView)grid.findViewById(R.id.textpart);
        textView.setText(String.format(QUALITY_QUANTITY, String.valueOf(quantity),quality.getLabel()));

        return grid;
    }

}
