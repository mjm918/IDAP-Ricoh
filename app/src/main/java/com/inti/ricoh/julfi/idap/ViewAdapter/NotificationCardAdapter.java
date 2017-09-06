package com.inti.ricoh.julfi.idap.ViewAdapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inti.ricoh.julfi.idap.Objects.NotificationLayer;
import com.inti.ricoh.julfi.idap.R;

import java.util.List;

/**
 * Created by julfi on 04/09/2017.
 */

public class NotificationCardAdapter extends RecyclerView.Adapter<NotificationCardAdapter.ViewHolder> {

    private List<NotificationLayer>items;
    private Context context;

    public NotificationCardAdapter(List<NotificationLayer>items,Context context){

        super();

        this.items = items;
        this.context = context;
    }

    @Override
    public NotificationCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card_view,parent,false);

        return new NotificationCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final NotificationLayer notificationLayer = items.get(position);

        holder.tv_title.setText(notificationLayer.getTitle());
        holder.tv_message.setText(notificationLayer.getMessage());
        holder.tv_date.setText(notificationLayer.getDate());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("notification clicked");
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_title,tv_message,tv_date;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_message = (TextView) itemView.findViewById(R.id.tv_message);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            cardView = (CardView) itemView.findViewById(R.id.notification_card);

        }
    }

}
