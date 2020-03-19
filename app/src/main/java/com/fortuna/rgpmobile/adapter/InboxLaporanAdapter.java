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
import com.fortuna.rgpmobile.pesan.InboxLaporanData;

import java.util.List;
import java.util.Random;

public class InboxLaporanAdapter extends RecyclerView.Adapter<InboxLaporanViewHolder> {

    private List<InboxLaporanData> mInboxLaporan;
    private Context mContext;

    public InboxLaporanAdapter(Context mContext, List<InboxLaporanData> mInboxLaporan) {
        this.mInboxLaporan = mInboxLaporan;
        this.mContext = mContext;
    }

    @Override
    public InboxLaporanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_inbox_laporan_item,
                parent, false);
        return new InboxLaporanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final InboxLaporanViewHolder holder, int position) {
        holder.mIcon.setText(mInboxLaporan.get(position).getID_RELAWAN().substring(0, 1));
        holder.mIDRelawan.setText(mInboxLaporan.get(position).getID_RELAWAN());
        holder.mJudul.setText(mInboxLaporan.get(position).getJUDUL());
        holder.mDesc.setText(mInboxLaporan.get(position).getDESKRIPSI());
        holder.mDateCreate.setText(mInboxLaporan.get(position).getDATE_CREATE());
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
        return mInboxLaporan.size();
    }
}

class InboxLaporanViewHolder extends RecyclerView.ViewHolder {

    TextView mIcon;
    TextView mIDRelawan;
    TextView mJudul;
    TextView mDesc;
    TextView mDateCreate;
    RelativeLayout mLayout;

    InboxLaporanViewHolder(View itemView) {
        super(itemView);

        mIcon = itemView.findViewById(R.id.tvIcon);
        mIDRelawan = itemView.findViewById(R.id.tvEmailSender);
        mJudul = itemView.findViewById(R.id.tvEmailTitle);
        mDesc = itemView.findViewById(R.id.tvEmailDetails);
        mDateCreate = itemView.findViewById(R.id.tvEmailTime);
        mLayout = itemView.findViewById(R.id.layout);
    }
}