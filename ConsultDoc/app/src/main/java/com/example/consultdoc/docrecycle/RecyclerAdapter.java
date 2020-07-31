package com.example.consultdoc.docrecycle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultdoc.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<RecyclerHelper> recyclerHelperArrayList;

    public RecyclerAdapter(ArrayList<RecyclerHelper> recyclerHelperArrayList) {
        this.recyclerHelperArrayList = recyclerHelperArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design_doc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RecyclerHelper recyclerHelper = recyclerHelperArrayList.get(position);
        holder.imageView.setImageResource(recyclerHelper.getImage());
        holder.title.setText(recyclerHelper.getTitle());
        holder.desc.setText(recyclerHelper.getDesc());


    }

    @Override
    public int getItemCount() {
        return recyclerHelperArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        ImageView imageView;

        TextView title, desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.doctors_recycler_image);
            title = itemView.findViewById(R.id.doctors_recycler_title);
            desc = itemView.findViewById(R.id.doctors_recycler_description);

        }
    }

}
