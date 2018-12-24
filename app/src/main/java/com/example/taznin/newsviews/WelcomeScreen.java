package com.example.taznin.newsviews;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WelcomeScreen extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private LinearLayout dotLayout;
    private slideAdapter slideAdapter;
    private TextView[] mDots;
    private ImageView[] dots;
    private Button btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        if(new PreferenceManager(this).checkPref()){

            loadHome();
        }


        setContentView(R.layout.activity_welcome_screen);
        viewPager=(ViewPager) findViewById(R.id.layout_slideView);
        dotLayout =(LinearLayout) findViewById(R.id.layout_dots);


        btnNext=(Button) findViewById(R.id.btn_slideNext);
        btnNext.setVisibility(View.INVISIBLE);

        btnNext.setOnClickListener(this);

        if(Build.VERSION.SDK_INT>=19){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        slideAdapter= new slideAdapter(this);
        viewPager.setAdapter(slideAdapter);
        //addDots(0);
        createDots(0);
        viewPager.addOnPageChangeListener(viewListener);
    }

   
    public void createDots(int cPos)
    {
        if(dotLayout!=null){
            dotLayout.removeAllViews();
        }
        dots=new ImageView[3] ;
        for(int i=0;i<dots.length;i++){
            dots[i]= new ImageView(this);
            if(i==cPos){
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.dot_slide));
            }
            else{
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.dot_slide));
            }
            LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);
            dotLayout.addView(dots[i],params);
        }
    }
    ViewPager.OnPageChangeListener viewListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            // addDots(i);

            if(i==2){
                btnNext.setText("START");
                btnNext.setVisibility(View.VISIBLE);

            }else{
                btnNext.setText("NEXT");
                btnNext.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    @Override
    public void onClick(View v) {
        loadHome();
        new PreferenceManager(this).writePref();
    }

    private void loadHome(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
    private void loadNextSlide(){
        int nextSlide= viewPager.getCurrentItem();
        if(nextSlide<3){
            viewPager.setCurrentItem(nextSlide);
        }else{
            loadHome();
            new PreferenceManager(this).writePref();
        }
    }
}
