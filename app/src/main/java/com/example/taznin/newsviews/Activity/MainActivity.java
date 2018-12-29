package com.example.taznin.newsviews.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.taznin.newsviews.Constant;
import com.example.taznin.newsviews.Fragments.AboutFragment;
import com.example.taznin.newsviews.Fragments.HomeFragment;
import com.example.taznin.newsviews.Fragments.LoginFragment;
import com.example.taznin.newsviews.Fragments.NumberFragment;
import com.example.taznin.newsviews.Fragments.WelcomeFragment;
import com.example.taznin.newsviews.Manager.InternetConnectivityCheck;
import com.example.taznin.newsviews.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mToogle;
    private OkHttpClient okHttpClient;
    private Request request;
    private static final String number_BASE_URL="http://numbersapi.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawer=(DrawerLayout) findViewById(R.id.myDrawer);
        navigationView=findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);


        mToogle= new ActionBarDrawerToggle(this,mDrawer,R.string.open,R.string.close);
        mDrawer.addDrawerListener(mToogle);
        mToogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmrnt_container,new WelcomeFragment()).commit();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToogle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.mItem_home:
               // getSupportFragmentManager().beginTransaction().replace(R.id.fragmrnt_container,new ListFragment()).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmrnt_container,new HomeFragment()).commit();
                //Toast.makeText(this,"Home",Toast.LENGTH_LONG).show();
                break;
            case R.id.mItem_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmrnt_container,new AboutFragment()).commit();
                //Toast.makeText(this,"About",Toast.LENGTH_LONG).show();
                break;
            case R.id.mItem_exit:
                exitApp();
                //Toast.makeText(this,"Exit",Toast.LENGTH_LONG).show();
                break;
            case R.id.mItem_login:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmrnt_container,new LoginFragment()).commit();
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void exitApp(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu,menu);
        MenuItem item= menu.findItem(R.id.searchOption);
        android.support.v7.widget.SearchView searchView= (android.support.v7.widget.SearchView) item.getActionView();
        searchView.setQueryHint("Number or dd/mm");
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                    if(InternetConnectivityCheck.isConnectedToInternet(MainActivity.this)){
                        String url="";
                        if(s.contains("/")){
                            url= Constant.BASE_URL_Number+s+"/date";
                        }else{
                            url= Constant.BASE_URL_Number+s;
                        }

                        getResultFromAPI_number(url);
                    }else{
                        Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }



                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    void getResultFromAPI_number(String s){
        okHttpClient= new OkHttpClient();
        request=new Request.Builder()
                .url(s)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    final String res= response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // txtNUm.setText(res);
                            showNumberFact(res);
                        }
                    });
                }
            }
        });

    }
    void showNumberFact(String result){
        Bundle bundle = new Bundle();
        bundle.putString("NUMBER_KEY", result);

        NumberFragment myFragment = new NumberFragment();
        myFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmrnt_container,myFragment).commit();
    }



    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()==0){
            exitApp();
        }else{
            getSupportFragmentManager().popBackStack();
        }

    }

}
