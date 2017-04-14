package com.pawel.database_php;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;



public class Texts extends Activity {
    public String title_of_song;
    public static final String TITLE_OF_SONG = "TITLE_OF_SONG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texts);



            Bundle extras = getIntent().getExtras();

            if(extras!=null){
                title_of_song= extras.getString(TITLE_OF_SONG);
            };
        String pdf_url = "http://docs.google.com/gview?embedded=true&url=http://pawelnbd.ayz.pl/pdf/wymarzona.pdf";
        WebView webView = (WebView) findViewById(R.id.webwiew);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.loadUrl(pdf_url);


    }
}
