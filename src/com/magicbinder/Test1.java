package com.magicbinder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.magicbinder.harmony.view.HarmonyFragmentActivity;

public class Test1 extends HarmonyFragmentActivity {
    private final static String MESSAGE = "message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        //        WebService ws = new WebService(this);
        //        ws.execute("http://mtgimage.com/multiverseid/1.jpg");
        NetworkImageView iconView = (NetworkImageView) this.findViewById(R.id.imageView1);
        iconView.setDefaultImageResId(R.drawable.cardback);
        iconView.setImageUrl("http://mtgimage.com/multiverseid/1.jpg", MagicBinderApplication.getInstance().getImageLoader());
        final Intent intent = getIntent();
        String message = intent.getStringExtra(MESSAGE);
//        String s = getIntent().getStringExtra("message");
        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
    }
}
