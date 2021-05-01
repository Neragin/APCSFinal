package com.example.fulkscord.homeScreenUsers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fulkscord.R;

import java.util.Collections;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    List<UserToDisplayData> list
            = Collections.emptyList();

    Context context;
    ClickListener listener;

    public Adapter(List<UserToDisplayData> list,
                                Context context, ClickListener listieer)
    {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public Adapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.viewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

// im messing with recyclerviews rn so like you probably wont be able to build
