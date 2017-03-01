package com.example.adrian.vistaweb;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class vistawebview extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vistawebview);
        Bundle bundle=getIntent().getExtras();
        boolean chechar=bundle.getBoolean("pie_pagina");
        WebView webView=(WebView)findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);

        if(chechar){
            webView.loadUrl("http://movil.gs/movil/content/ekt/secciones/cuentanos/cuentanos.html?source=Inicio-General");

        }else
        {
            webView.loadUrl("http://movil.gs/login.html");
        }


        webView.setWebViewClient(new cliente());

    }

}

class cliente extends WebViewClient
{
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }
}
