package com.example.fulkscord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    String[] test, descriptions;
    Context context;
    public Adapter(Context ctx, String[] s1, String[] s2) {
        context = ctx;
        test = s1;
        descriptions = s2;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.message, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView myText1, myText2;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
//            myText1 = itemView.findViewById(R.id.);
        }
    }
}

// im messing with recyclerviews rn so like you probably wont be able to build
