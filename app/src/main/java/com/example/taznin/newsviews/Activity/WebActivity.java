package com.example.taznin.newsviews.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taznin.newsviews.R;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private String url_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        if(getIntent().hasExtra("newsurl")){
            url_=getIntent().getStringExtra("newsurl");
        }else {
            Toast.makeText(this,"No url",Toast.LENGTH_LONG).show();
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder
                .setMessage("Open url in browser?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_));
                        startActivity(browserIntent);

                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                            webView=(WebView)findViewById(R.id.webPage);

                            webView.setWebViewClient(new WebViewClient());
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.loadUrl(url_);

                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.canGoBack();
        }else {
            super.onBackPressed();
        }

    }
}
