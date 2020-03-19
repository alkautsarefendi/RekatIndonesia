package com.fortuna.rgpmobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fortuna.rgpmobile.R;
import com.fortuna.rgpmobile.pesan.DetailInboxLaporanActivity;
import com.fortuna.rgpmobile.pesan.InboxBroadcastData;

import java.util.List;
import java.util.Random;

public class InboxBroadcastAdapter extends RecyclerView.Adapter<InboxBroadcastViewHolder> {

    private List<InboxBroadcastData> mInboxBroadcast;
    private Context mContext;

    public InboxBroadcastAdapter(Context mContext, List<InboxBroadcastData> mInboxBroadcast) {
        this.mInboxBroadcast = mInboxBroadcast;
        this.mContext = mContext;
    }

    @Override
    public InboxBroadcastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_inbox_broadcast_item,
                parent, false);
        return new InboxBroadcastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final InboxBroadcastViewHolder holder, int position) {
        holder.mIcon.setText(mInboxBroadcast.get(position).getID_RELAWAN().substring(0, 1));
        holder.mIDRelawan.setText(mInboxBroadcast.get(position).getID_RELAWAN());
        holder.mJudul.setText(mInboxBroadcast.get(position).getJNS_BROADCAST());
        holder.mDesc.setText(mInboxBroadcast.get(position).getDESKRIPSI());
        holder.mDateCreate.setText(mInboxBroadcast.get(position).getDATE_CREATE());
        Random mRandom = new Random();
        final int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
        ((GradientDrawable) holder.mIcon.getBackground()).setColor(color);

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(mContext, DetailInboxLaporanActivity.class);
                mIntent.putExtra("sender", holder.mIDRelawan.getText().toString());
                mIntent.putExtra("title", holder.mJudul.getText().toString());
                mIntent.putExtra("details", holder.mDesc.getText().toString());
                mIntent.putExtra("time", holder.mDateCreate.getText().toString());
                mIntent.putExtra("icon", holder.mIcon.getText().toString());
                mIntent.putExtra("colorIcon", color);
                mContext.startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mInboxBroadcast.size();
    }
}

class InboxBroadcastViewHolder extends RecyclerView.ViewHolder {

    TextView mIcon;
    TextView mIDRelawan;
    TextView mJudul;
    TextView mDesc;
    TextView mDateCreate;
    RelativeLayout mLayout;

    InboxBroadcastViewHolder(View itemView) {
        super(itemView);

        mIcon = itemView.findViewById(R.id.tvIcon);
        mIDRelawan = itemView.findViewById(R.id.tvEmailSender);
        mJudul = itemView.findViewById(R.id.tvEmailTitle);
        mDesc = itemView.findViewById(R.id.tvEmailDetails);
        mDateCreate = itemView.findViewById(R.id.tvEmailTime);
        mLayout = itemView.findViewById(R.id.layout);
    }
}