package com.stdio.newsapi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.DataViewHolder> {

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView tvTitle;
        TextView tvDescription;

        DataViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            tvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
            tvDescription = (TextView)itemView.findViewById(R.id.tvDescription);
        }
    }

    List<DataModel> dataList;
    Context mContext;

    RVAdapter(List<DataModel> dataList, Context context){
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
        DataViewHolder pvh = new DataViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(DataViewHolder dataViewHolder, final int position) {
        dataViewHolder.tvTitle.setText(dataList.get(position).title);
        dataViewHolder.tvDescription.setText(dataList.get(position).description);
        dataViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebviewActivity.url = MainActivity.dataList.get(position).url;
                MainActivity.mContext.startActivity(new Intent(MainActivity.mContext, WebviewActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}