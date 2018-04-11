package com.youlorryintracity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.youlorryintracity.ModalClasses.BookingDataBean;
import com.youlorryintracity.RideHistory;
import com.youlorryintracity.R;
import com.youlorryintracity.TrackActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by hp on 11/20/2017.
 */

public class truckListAdapter extends RecyclerView.Adapter<truckListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<BookingDataBean> list;

    public truckListAdapter(RideHistory listener, ArrayList<BookingDataBean> arrayList) {
        this.context = listener;
        this.list = arrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listdatalayout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (list.get(position).getStatus().equals("pending")) {
            Picasso.with(context).load(R.drawable.pending).into(holder.StatusImag);
            holder.Pick.setText(list.get(position).getPickup().trim());
            holder.Drop.setText(list.get(position).getDrop().trim());

          //  holder.Date.setText(list.get(position).getDate().trim());
          //  holder.Duration.setText("Duration :" + list.get(position).getDuration().trim());
           // holder.Distance.setText("Distance :" + list.get(position).getDistance().trim());
           // holder.Amount.setText(list.get(position).getAmount().trim());
        } /*else if (list.get(position).getStatus().equals("cancelled")) {
            Picasso.with(context).load(R.drawable.canceled).into(holder.StatusImag);
            holder.Pick.setText(list.get(position).getPickup().trim());
            holder.Drop.setText(list.get(position).getDrop().trim());
            holder.Date.setText(list.get(position).getDate().trim());
            holder.Amount.setText(list.get(position).getAmount().trim());
        }*/


//        holder.genre.setText(movie.getGenre());
//        holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout Card;
        ImageView StatusImag;
        private TextView Date, Vname, Dname, Pick, Drop, Status, Duration, Distance, Amount;

        private ViewHolder(View itemView) {
            super(itemView);
            Date = itemView.findViewById(R.id.date);
            Vname = itemView.findViewById(R.id.vnumber);
            Dname = itemView.findViewById(R.id.drivername);
            Pick = itemView.findViewById(R.id.pickup);
            Drop = itemView.findViewById(R.id.drop);
            Status = itemView.findViewById(R.id.status);
            Card = itemView.findViewById(R.id.cardlayout);
            Duration = itemView.findViewById(R.id.duration);
            Amount = itemView.findViewById(R.id.amount);
            Distance = itemView.findViewById(R.id.distance);
            StatusImag = itemView.findViewById(R.id.staus_image);
            Card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (list.get(getLayoutPosition()).getStatus().equals("trip_ended")) {
                        Intent intent = new Intent(context, TrackActivity.class);
                        intent.putExtra("pick", list.get(getLayoutPosition()).getPickup());
                        intent.putExtra("drop", list.get(getLayoutPosition()).getDrop());
                        intent.putExtra("drivername", list.get(getLayoutPosition()).getName());
                        intent.putExtra("distance", list.get(getLayoutPosition()).getDistance());
                        intent.putExtra("duration", list.get(getLayoutPosition()).getDuration());
                        intent.putExtra("amount", list.get(getLayoutPosition()).getAmount());
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "Ride is not Done!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
