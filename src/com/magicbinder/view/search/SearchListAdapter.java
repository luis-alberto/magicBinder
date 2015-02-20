package com.magicbinder.view.search;

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
import com.magicbinder.entity.Card;
import com.magicbinder.view.card.CardDetailActivity;

/**
 * SearchListAdapter base adapter for search list.
 * @author Luis
 *
 */
public class SearchListAdapter extends BaseAdapter{
	/**
	 * Tag first putextra message.
	 */
	private static final String MESSAGE1 ="message1";
	/**
	 * Tag second putextra message.
	 */
	private static final String POSITION = "position";
	/**
	 * Context of activity
	 */
    private Activity context;
    /**
     * List of cards.
     */
    private ArrayList<Card> listCards;

    /**
     * Construtor of SearchListAdapter.
     * @param context of activity.
     * @param listCards .
     */
    public SearchListAdapter(Activity context, ArrayList<Card> listCards) {
        this.context = context;
        this.listCards = listCards;
    }

    /**
     * Count numbers of cards in list card.
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if(listCards!=null){
            return listCards.size();
        }
        return 0;
    }

    /**
     * Get card in list of card position.
     */
    @Override
    public Card getItem(int position) {
        // TODO Auto-generated method stub
        if(listCards!=null){
            return listCards.get(position);
        }
        return null;
    }

    /**
     * Get id of card in list of card position.
     */
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        if(listCards!=null){
            return listCards.get(position).getId();
        }
        return 0;
    }

    /**
     * Creating view.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;

        if(convertView==null){
            grid = new View(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            grid=inflater.inflate(R.layout.grid_card, parent, false);
        }else{
            grid = (View)convertView;
        }
        //settin image.
        NetworkImageView imagePart = (NetworkImageView) grid.findViewById(R.id.imagepart);
        imagePart.setDefaultImageResId(R.drawable.cardback);
        imagePart.setImageUrl(this.getItem(position).getImage(), MagicBinderApplication.getInstance().getImageLoader());
        //build of image click.
        imagePart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CardDetailActivity.class);
                intent.putParcelableArrayListExtra(MESSAGE1, listCards);
                intent.putExtra(POSITION, position);
                context.startActivity(intent);
            }

          });
        TextView textView = (TextView)grid.findViewById(R.id.textpart);
        textView.setText(String.valueOf(position));

        return grid;
    }

}
