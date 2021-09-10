package com.example.popularshows;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<myviewholder> {

    ArrayList<dataclass> list;
    Context context;

    public  Adapter(ArrayList<dataclass> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View oneitem = layoutInflater.inflate(R.layout.singleitemlayout, parent, false);
        return new myviewholder(oneitem);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        String fullimageurl = "https://image.tmdb.org/t/p/w500/"+list.get(position).image;

        Glide.with(context).load(fullimageurl).into(holder.imageView);
        holder.titletext.setText(list.get(position).name);
        holder.overviewtext.setText("Overview : "+list.get(position).overview);
        holder.ratingtext.setText("Rating : "+list.get(position).vote_average);
        holder.ratingcounttext.setText("Total Rated : "+list.get(position).vote_count);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class myviewholder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView titletext, overviewtext, ratingtext, ratingcounttext;

    public myviewholder(@NonNull View itemView) {
        super(itemView);
        this.imageView = itemView.findViewById(R.id.imageview);
        this.titletext = itemView.findViewById(R.id.titletext);
        this.overviewtext = itemView.findViewById(R.id.overviewtext);
        this.ratingtext = itemView.findViewById(R.id.ratingtext);
        this.ratingcounttext = itemView.findViewById(R.id.ratingcounttext);
    }

}