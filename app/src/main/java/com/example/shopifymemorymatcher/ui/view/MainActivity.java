package com.example.shopifymemorymatcher.ui.view;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.shopifymemorymatcher.R;
import com.example.shopifymemorymatcher.ui.shared.SessionManager;

public class MainActivity extends Activity implements View.OnClickListener {
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sessionManager = new SessionManager(this);

        if (sessionManager.isDark())
            setTheme(R.style.AppThemeDark);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(this);

        ImageButton shopifyButton = findViewById(R.id.shopify_button);
        shopifyButton.setOnClickListener(this);

        ImageButton brightButton = findViewById(R.id.bright_button);
        brightButton.setOnClickListener(this);
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
            case R.id.bright_button:
                sessionManager.switchTheme();
                switchTheme();
                break;
        }
    }

    private void switchTheme() {
        this.finish();
        this.startActivity(new Intent(this, this.getClass()));
        this.overridePendingTransition(0, 0);
    }
}
