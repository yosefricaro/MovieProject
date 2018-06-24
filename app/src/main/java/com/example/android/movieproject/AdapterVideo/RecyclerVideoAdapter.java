package com.example.android.movieproject.AdapterVideo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.movieproject.Model.ChildVideo;
import com.example.android.movieproject.R;

import java.util.List;

/**
 * Created by Yosefricaro on 02/12/2016.
 */

public class RecyclerVideoAdapter extends RecyclerView.Adapter<RecyclerVideoHolders> {
    private List<ChildVideo> itemList;
    private Context context;

    public RecyclerVideoAdapter(Context context, List<ChildVideo> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerVideoHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.card_video, parent, false);
        RecyclerVideoHolders rcv = new RecyclerVideoHolders(layoutView, itemList);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final RecyclerVideoHolders holder, final int position) {
        holder.trailer.setText("Trailer " + (position+1));
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
