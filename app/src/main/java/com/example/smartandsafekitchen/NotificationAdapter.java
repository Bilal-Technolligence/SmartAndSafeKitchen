package com.example.smartandsafekitchen;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    //    private String[] title,description,dateTime;
    ArrayList<NotificationAttributes> list;
    public NotificationAdapter(ArrayList<NotificationAttributes> list)
    {
        this.list=list;
//        this.title=title;
//        this.description=description;
//        this.dateTime=dateTime;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        String tittle=title[position];
//        String dscription=description[position];
//        String datetime=dateTime[position];
        holder.title.setText(list.get(position).getTittle());
        holder.desc.setText(list.get( position ).getDescription());
        holder.datetime.setText(list.get( position ).getDatetime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        public TextView title,desc,datetime;
        LinearLayout linearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.notification_title);
            desc = (TextView) itemView.findViewById(R.id.notification_description);
            datetime=(TextView) itemView.findViewById( R.id.notification_datetime );
            linearLayout=(LinearLayout) itemView.findViewById(R.id.linearLayout);
            linearLayout.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(),1,0,"Delete this notification");
        }
    }

}

