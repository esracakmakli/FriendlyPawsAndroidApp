package com.example.esra.bitirmeprojesi.Sahiplen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.esra.bitirmeprojesi.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class SahiplendirmeViewHolder extends RecyclerView.ViewHolder {

    ImageView sImageview;
    CardView sCardView;
    TextView sİsim;
    TextView sŞehir;
    TextView sİlanTarihi;
    ProgressBar progressBar;

    public SahiplendirmeViewHolder(@NonNull View itemView) {
        super(itemView);
        sImageview=itemView.findViewById(R.id.imageView_sahiplendirme);
        sCardView=itemView.findViewById(R.id.cardView_sahiplendirme);
        sİsim=itemView.findViewById(R.id.isim_sahiplendirme);
        sŞehir=itemView.findViewById(R.id.yer_sahiplendirme);
        sİlanTarihi=itemView.findViewById(R.id.tarih_sahiplendirme);
        progressBar=itemView.findViewById(R.id.rw_sahiplendirme_progressbar);

    }
    public void getSahiplenIlan(final Context context, Sahiplendirme sahiplendirme){
        itemView.setTag(sahiplendirme);
        sİsim.setText(sahiplendirme.getSahiplendirmeİsim());
        sŞehir.setText(sahiplendirme.getSahiplendirmeSehir());
        sİlanTarihi.setText(sahiplendirme.getSahiplendirmeTarih());


        Picasso.get().load(sahiplendirme.getSahiplendirmeFoto())
                .fit().centerCrop().into(sImageview, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }
}
