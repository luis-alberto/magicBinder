package com.magicbinder.adapter;

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
import com.magicbinder.entity.Binder;
import com.magicbinder.entity.Binder_Card;
import com.magicbinder.entity.Card;

public class BinderGridViewAdapter extends BaseAdapter{

    private Context context;
    ArrayList<Binder_Card> binderCards;

    public BinderGridViewAdapter(Context context, Binder binder) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.binderCards = binder.getBinders_cards_binder();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if(binderCards!=null){
            return binderCards.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        if(binderCards!=null){
            return binderCards.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        if(binderCards!=null){
            return binderCards.get(position).getId();
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
        imagePart.setImageUrl("http://mtgimage.com/multiverseid/1.jpg", MagicBinderApplication.getInstance().getImageLoader());
        TextView textView = (TextView)grid.findViewById(R.id.textpart);
        textView.setText(String.valueOf(position));

        return grid;
    }

}
