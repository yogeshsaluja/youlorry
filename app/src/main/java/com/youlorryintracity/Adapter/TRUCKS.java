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

public class TRUCKS extends RecyclerView.Adapter<TRUCKS.ViewHolder> {
    ArrayList<String> names, time;
    ArrayList<Integer> imageslist, secimages;
    int[] secondimages = {R.drawable.ic_delivery_truck2, R.drawable.ic_delivery_truck_new2, R.drawable.ic_crane2, R.drawable.ic_cranenew2, R.drawable.ic_delivery_truck2};

    int[] images = {R.drawable.ic_delivery_truck, R.drawable.ic_delivery_truck_new, R.drawable.ic_crane, R.drawable.ic_cranenew, R.drawable.ic_delivery_truck};
    Context context;
    int pos = 0;
    Boolean Selected = false;

    public TRUCKS(Home home, ArrayList<Integer> integers, ArrayList<Integer> secondicon, ArrayList<String> nameslist) {
        this.context = home;
        this.imageslist = integers;
        this.secimages = secondicon;
        this.names = nameslist;
       // this.time = timelist;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.truck_images, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

//        holder.dotProgressBar.setVisibility(View.VISIBLE);
//        holder.times.setVisibility(View.GONE);
      /*  if (!time.get(position).equals("")) {
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.dotProgressBar.setVisibility(View.GONE);
                    holder.times.setVisibility(View.VISIBLE);
                    holder.times.setText(time.get(position));

                }
            }, 1000);
        }*/
      if (position==0){
          holder.LeftLine.setVisibility(View.INVISIBLE);
      }else if (position==2){
          holder.RightLine.setVisibility(View.INVISIBLE);
      }
        holder.dotProgressBar.setVisibility(View.GONE);
       // holder.times.setVisibility(View.VISIBLE);
       // holder.times.setText(time.get(position));

        holder.name.setText(names.get(position));
        holder.image.setImageResource(images[position]);
        if (position == pos) {
            holder.image.setBackground(context.getResources().getDrawable(R.drawable.circleselected));
            holder.name.setTextColor(context.getResources().getColor(R.color.primary));
            holder.image.setImageResource(secondimages[position]);
            holder.cardView.setElevation(30);
            holder.image.setElevation(10);
          ((Home) context).BookinLayout(names.get(position).replaceAll("\\s",""));
        } else {
            holder.image.setElevation(0);
            holder.image.setBackground(context.getResources().getDrawable(R.drawable.circleswhite));
            holder.name.setTextColor(context.getResources().getColor(R.color.black));

        }
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, times;
        LinearLayout cardView;
        ImageView image,LeftLine,RightLine;
        DotProgressBar dotProgressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            dotProgressBar = itemView.findViewById(R.id.progress_bar);
            name = itemView.findViewById(R.id.name);
            cardView = itemView.findViewById(R.id.cardview);
            LeftLine=(ImageView)itemView.findViewById(R.id.left_line);
            RightLine =(ImageView)itemView.findViewById(R.id.right_line);

            times = itemView.findViewById(R.id.time);
            image =
                    itemView.findViewById(R.id.image);
            image.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    pos = getLayoutPosition();
                    notifyDataSetChanged();

                }
            });
        }
    }
}
