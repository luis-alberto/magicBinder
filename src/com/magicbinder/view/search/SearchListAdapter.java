package com.magicbinder.view.search;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.magicbinder.MagicBinderApplication;
import com.magicbinder.R;
import com.magicbinder.entity.Card;

public class SearchListAdapter extends BaseAdapter{

    private Context context;
    ArrayList<Card> listCards;

    public SearchListAdapter(Context context, ArrayList<Card> listCards) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.listCards = listCards;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if(listCards!=null){
            return listCards.size();
        }
        return 0;
    }

    @Override
    public Card getItem(int position) {
        // TODO Auto-generated method stub
        if(listCards!=null){
            return listCards.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        if(listCards!=null){
            return listCards.get(position).getId();
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;

        if(convertView==null){
            grid = new View(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            grid=inflater.inflate(R.layout.grid_card, parent, false);
        }else{
            grid = (View)convertView;
        }

        NetworkImageView imagePart = (NetworkImageView) grid.findViewById(R.id.imagepart);
        imagePart.setDefaultImageResId(R.drawable.cardback);
        imagePart.setImageUrl(this.getItem(position).getImage(), MagicBinderApplication.getInstance().getImageLoader());
        imagePart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

              // do stuff
            }

          });
        TextView textView = (TextView)grid.findViewById(R.id.textpart);
        textView.setText(String.valueOf(position));

        return grid;
    }

}
