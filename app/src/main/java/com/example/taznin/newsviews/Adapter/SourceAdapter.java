package com.example.taznin.newsviews.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taznin.newsviews.Model.Source;
import com.example.taznin.newsviews.R;

import java.util.List;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.SourceViewHolder> {

    private List<Source> dataList;

    private Context context;

    public SourceAdapter(Context context,List<Source> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    class SourceViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtTitle;
        TextView txtCountry;

        SourceViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.txt_sourceName);
            txtCountry = mView.findViewById(R.id.txt_sourceCat);

        }
    }

    @Override
    public SourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.source_list_item, parent, false);
        return new SourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SourceViewHolder holder, int position) {
        holder.txtTitle.setText(dataList.get(position).getName());
        holder.txtCountry.setText(dataList.get(position).getCategory());


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
