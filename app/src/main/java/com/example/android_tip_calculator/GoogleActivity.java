package com.example.android_tip_calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.app.AppCompatActivity;

public class GoogleActivity extends AppCompatActivity {

    // inputs
    private EditText searchBar;

    // buttons
    private Button searchButton;
    private Button homeButton;

    // webview
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);

        // register layout
        searchBar    = (EditText)findViewById(R.id.search_bar);
        searchButton = (Button)findViewById(R.id.search_button);
        homeButton   = (Button)findViewById(R.id.home_button);
        web          = (WebView)findViewById(R.id.web_view);

        // setup webview
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient( new WebViewClient() );

        // search bar
        searchButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get searched text
                String searchedText = searchBar.getText().toString();

                // launch webview with searched text
                web.loadUrl(searchedText);
            }
        });

        // back button
        OnBackPressedCallback back = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed(){
                // if there webview can go back go back
                if ( web.canGoBack() ) web.goBack();
            }
        };

        getOnBackPressedDispatcher().addCallback(this, back);


        // home button
        homeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }

}
