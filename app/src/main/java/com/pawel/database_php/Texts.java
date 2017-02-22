package com.pawel.database_php;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.w3c.dom.Text;

/**
 * Created by Paweł on 2017-02-18.
 */
public class Texts extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.texts);



        //WebView webView = (WebView) findViewById(R.id.webwiew);
        //webView.getSettings().setJavaScriptEnabled(true);
        String pdf = "http://www.terravita.com.pl/sites/default/files/pferta/pliki/sample1.pdf";
        //webView.loadUrl( pdf);


        WebView mWebView=new WebView(Texts.this);
        mWebView.getSettings().setJavaScriptEnabled(true);


        mWebView.loadUrl("https://docs.google.com/gview?embedded=true&url="+pdf);
        setContentView(mWebView);

    }
}
