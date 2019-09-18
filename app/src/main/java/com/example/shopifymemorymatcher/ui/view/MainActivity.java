package com.example.shopifymemorymatcher.ui.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.shopifymemorymatcher.R;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(this);

        ImageButton shopifyButton = findViewById(R.id.shopify_button);
        shopifyButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_button:
                Intent activityIntent = new Intent(this,
                        MemoryMatcherActivity.class);
                startActivity(activityIntent);
                break;
            case R.id.shopify_button:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.shopify.ca/careers/" +
                                "mobile-developer-intern-android-winter-2020-83902b"));
                startActivity(browserIntent);
                break;
        }
    }
}
