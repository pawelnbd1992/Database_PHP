package com.pawel.database_php;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Texts extends Activity {
    public String url_of_song;
    public static final String URL_OF_SONG = "URL_OF_SONG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texts);



            Bundle extras = getIntent().getExtras();

            if(extras!=null){
                url_of_song= extras.getString(URL_OF_SONG);
            };
        String pdf_url = "http://docs.google.com/gview?embedded=true&url="+url_of_song;
        WebView webView = (WebView) findViewById(R.id.webwiew);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.loadUrl(pdf_url);



    }


}
