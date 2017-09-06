package com.inti.ricoh.julfi.idap.ViewAdapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.inti.ricoh.julfi.idap.Objects.MessageLayer;
import com.inti.ricoh.julfi.idap.R;
import com.inti.ricoh.julfi.idap.AppController;

import java.util.List;

/**
 * Created by julfi on 30/08/2017.
 */

public class MessageCardAdapter extends RecyclerView.Adapter<MessageCardAdapter.ViewHolder> {

    private List<MessageLayer> items;
    private Context context;
    private ImageLoader loader;

    public MessageCardAdapter(List<MessageLayer> items,Context context){
        super();

        this.context = context;
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_card_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MessageLayer messageLayer = items.get(position);
        loader = AppController.getInstance().getImageLoader();

        holder.tv_name.setText(messageLayer.getName());
        holder.tv_message.setText(messageLayer.getMessage());
        holder.tv_date.setText(messageLayer.getDate());
        holder.iv_alais.setImageUrl(messageLayer.getAlais(),loader);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("card clicked and position name is -> "+messageLayer.getName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name,tv_message,tv_date;
        CardView cardView;
        NetworkImageView iv_alais;

        ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.message_card);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_message = (TextView) itemView.findViewById(R.id.tv_message);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            iv_alais = (NetworkImageView) itemView.findViewById(R.id.iv_alais);
        }
    }
}
