package com.example.android.movieproject.AdapterMain;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.movieproject.Model.ChildResult;
import com.example.android.movieproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Yosefricaro on 02/12/2016.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private List<ChildResult> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<ChildResult> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView, itemList);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolders holder, final int position) {
        Picasso.with(context)
                .load(itemList.get(position).getPoster_path())
                .into(holder.moviePoster);
    }
    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
