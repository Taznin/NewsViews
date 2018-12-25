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

public class WelcomeScreen extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotLayout;
    private slideAdapter slideAdapter;
    private TextView[] mDots;
    private ImageView[] dots;
    private Button btnNext,btnPrev;
    int cPage;
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


        btnPrev=(Button) findViewById(R.id.btn_slidePrev);
        btnNext=(Button) findViewById(R.id.btn_slideNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cPage==2){
                    loadHome();
                    new PreferenceManager(getApplicationContext()).writePref();
                }else{
                    viewPager.setCurrentItem(cPage+1);
                }


            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(cPage-1);
            }
        });

        if(Build.VERSION.SDK_INT>=19){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        slideAdapter= new slideAdapter(this);
        viewPager.setAdapter(slideAdapter);

        createDots(0);
        viewPager.addOnPageChangeListener(viewListener);
    }


    public void createDots(int cPos)
    {
        if(dotLayout!=null){
            dotLayout.removeAllViews();
        }
        mDots=new TextView[3] ;
        for(int i=0;i<mDots.length;i++){
            mDots[i]= new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.trasparentWhite));

            dotLayout.addView(mDots[i]);
        }
        if(mDots.length>0){
            mDots[cPos].setTextColor(getResources().getColor(R.color.White));
        }
    }
    ViewPager.OnPageChangeListener viewListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            createDots(i);
            cPage=i;
            if(i==0){
                btnNext.setEnabled(true);
                btnPrev.setEnabled(false);
                btnPrev.setVisibility(View.VISIBLE);

                btnNext.setText("Next");
                btnPrev.setText("");
            }else if(i==mDots.length-1){
                btnNext.setEnabled(true);
                btnPrev.setEnabled(true);
                btnPrev.setVisibility(View.INVISIBLE);

                btnNext.setText("Start");
                btnPrev.setText("Back");
            }else{
                btnNext.setEnabled(true);
                btnPrev.setEnabled(true);
                btnPrev.setVisibility(View.VISIBLE);

                btnNext.setText("Next");
                btnPrev.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };



    private void loadHome(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

}
