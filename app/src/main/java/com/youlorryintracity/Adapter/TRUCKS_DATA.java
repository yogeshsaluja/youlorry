package com.youlorryintracity.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.youlorryintracity.Home;
import com.youlorryintracity.R;

import java.util.ArrayList;

/**
 * Created by hp on 11/21/2017.
 */

public class TRUCKS_DATA extends RecyclerView.Adapter<TRUCKS_DATA.ViewHolder> {
    ArrayList<String> names,time;
    ArrayList<Integer> imageslist,secimages;
    int[] secondimages = {R.drawable.ic_delivery_truck2, R.drawable.ic_delivery_truck_new2, R.drawable.ic_crane2, R.drawable.ic_cranenew2,R.drawable.ic_delivery_truck2};
String[] trucknamelist={"truck","trailer","openbody","cointainer","closed truck"};
    int[] images = {R.drawable.ic_delivery_truck, R.drawable.ic_delivery_truck_new, R.drawable.ic_crane, R.drawable.ic_cranenew,R.drawable.ic_delivery_truck};
     Context context;
    int pos=0;
        Boolean Selected=false;

    public TRUCKS_DATA(Home home) {
       this.context=home;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.truck_data_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {




        holder.name.setText(trucknamelist[position]);
        holder.image.setImageResource(images[position]);
         holder.image.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View view) {
        pos=position;
        notifyDataSetChanged();

    }
});

        if(position == pos ){

            holder.image.setBackground(context.getResources().getDrawable(R.drawable.circleselected));
          holder.image.setImageResource(secondimages[position]);
            holder.cardView.setElevation(30);
            //((Home)context).BookinLayout(names.get(position),time.get(position));
        }else {
            holder.image.setBackground(context.getResources().getDrawable(R.drawable.circleswhite));

        }
    }

    @Override
    public int getItemCount() {
        return secondimages.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,times;
        LinearLayout cardView;
        ImageView image;
        DotProgressBar dotProgressBar;
        public ViewHolder(View itemView) {
            super(itemView);
            dotProgressBar=(DotProgressBar)itemView.findViewById(R.id.progress_bar);
            name=(TextView)itemView.findViewById(R.id.name);
            cardView=(LinearLayout)itemView.findViewById(R.id.cardview);
            times=(TextView)itemView.findViewById(R.id.time);
            image=(ImageView)itemView.findViewById(R.id.image);
         }
    }
}
