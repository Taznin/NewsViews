package com.example.taznin.newsviews.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.taznin.newsviews.Activity.WebActivity;
import com.example.taznin.newsviews.Model.Article;
import com.example.taznin.newsviews.R;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;
public class HeadLineAdapter extends RecyclerView.Adapter<HeadLineAdapter.CustomViewHolder> {

    private List<Article> dataList;

    private Context context;

    public HeadLineAdapter(Context context, List<Article> dataList){
        this.context = context;
        this.dataList = dataList;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder  {

        public final View mView;

        private TextView txtTitle;
        private ImageView coverImage;
        private LinearLayout layout;
        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.txttitle);
            coverImage = mView.findViewById(R.id.coverImage);
            layout=(LinearLayout)mView.findViewById(R.id.layOut_headline);

        }


    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.topheadline_row_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        holder.txtTitle.setText(dataList.get(position).getTitle());
        final String newsUrl=dataList.get(position).getUrl();
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getUrlToImage())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, WebActivity.class);
                intent.putExtra("newsurl",newsUrl);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    void openLink(String url){
        final String url_= url;


    }

}