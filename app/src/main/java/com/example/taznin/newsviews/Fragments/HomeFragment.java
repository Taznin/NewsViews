package com.example.taznin.newsviews.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taznin.newsviews.Adapter.HeadLineAdapter;
import com.example.taznin.newsviews.Adapter.SourceAdapter;
import com.example.taznin.newsviews.Constant;
import com.example.taznin.newsviews.Interfaces.HeadLineService;
import com.example.taznin.newsviews.Interfaces.SourceService;
import com.example.taznin.newsviews.Manager.ApiClient;
import com.example.taznin.newsviews.Manager.InternetConnectivityCheck;
import com.example.taznin.newsviews.Model.Article;
import com.example.taznin.newsviews.Model.News;
import com.example.taznin.newsviews.Model.NewsPaper;
import com.example.taznin.newsviews.Model.Source;
import com.example.taznin.newsviews.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private HeadLineAdapter adapter;
    private SourceAdapter adapterSource;
    public static  final String apike="391e231937244940bd83c03eaa075a2d";
    public static  final String country="us";
    private List<Article> articles= new ArrayList<>();
    private List<Source> sources= new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewSource;
    ProgressDialog progressDoalog;
    private SwipeRefreshLayout swipeRefreshLayoutOne;
    private SwipeRefreshLayout swipeRefreshLayoutTwo;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.frag_home_news,container,false);
        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
        swipeRefreshLayoutOne=(SwipeRefreshLayout)view.findViewById(R.id.swipeOne) ;
        swipeRefreshLayoutTwo=(SwipeRefreshLayout)view.findViewById(R.id.swipeTwo) ;
        //swipeRefreshLayoutOne.setOnRefreshListener(getActivity());
        recyclerView =view.findViewById(R.id.listView1);
        recyclerViewSource =view.findViewById(R.id.listView2);
        if(InternetConnectivityCheck.isConnectedToInternet(getActivity())){
            swipeRefreshLayoutOne.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    headLineLoad();
                }
            });
            swipeRefreshLayoutTwo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    sourceLoad();
                }
            });

            headLineLoad();
            sourceLoad();
        }else {
            progressDoalog.dismiss();
            Toast.makeText(getActivity(),"No internet connection",Toast.LENGTH_SHORT).show();
        }

        return view;
    }
    void headLineLoad(){
        swipeRefreshLayoutOne.setRefreshing(true);
        HeadLineService service = ApiClient.getRetrofitInstance().create(HeadLineService.class);
        Call<News> call = service.getAllArticales(Constant.NEWS_API_COUNTRY, Constant.NEWS_API_KEY);


        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                progressDoalog.dismiss();
                if(response.isSuccessful() && response.body().getArticles()!=null){
                    if(!articles.isEmpty()){
                        articles.clear();
                    }
                    articles=response.body().getArticles();
                    headlineGenerate(articles);
                    swipeRefreshLayoutOne.setRefreshing(false);
                }else{
                    swipeRefreshLayoutOne.setRefreshing(false);
                    Toast.makeText(getActivity(), "no response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                progressDoalog.dismiss();

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    void sourceLoad(){
        swipeRefreshLayoutTwo.setRefreshing(true);
        SourceService service = ApiClient.getRetrofitInstance().create(SourceService.class);
        Call<NewsPaper> call = service.getSources(Constant.NEWS_API_KEY);

        call.enqueue(new Callback<NewsPaper>() {
            @Override
            public void onResponse(Call<NewsPaper> call, Response<NewsPaper> response) {
                progressDoalog.dismiss();
                if(response.isSuccessful() && response.body().getSources()!=null){
                    if(!sources.isEmpty()){
                        sources.clear();
                    }
                    sources=response.body().getSources();
                    sourceGenerate(sources);
                    swipeRefreshLayoutTwo.setRefreshing(false);
                }else{
                    swipeRefreshLayoutTwo.setRefreshing(false);
                    Toast.makeText(getActivity(), "no response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NewsPaper> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void headlineGenerate(List<Article> articleList) {

        adapter = new HeadLineAdapter(getActivity(),articleList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }
    private void sourceGenerate(List<Source> articleList) {

        adapterSource = new SourceAdapter(getActivity(),articleList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewSource.setLayoutManager(layoutManager);
        recyclerViewSource.setAdapter(adapterSource);
    }


}
