package com.example.esra.bitirmeprojesi.Kayıplar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.esra.bitirmeprojesi.Kayıplar.KayıpHayvanlar;
import com.example.esra.bitirmeprojesi.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class KayıpHayvanlarViewHolder  extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView kayıpİsim;
    TextView kayıpYer;
    TextView kayıpTarih;
    CardView cardView;
    ProgressBar progressBar;


    public KayıpHayvanlarViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView=itemView.findViewById(R.id.cardView);
        imageView=itemView.findViewById(R.id.imageView);
        kayıpİsim=itemView.findViewById(R.id.isim);
        kayıpYer=itemView.findViewById(R.id.yer);
        kayıpTarih=itemView.findViewById(R.id.tarih);
        progressBar=itemView.findViewById(R.id.rw_kayıphayvanlar_progressbar);



    }


    public void getkayıpİlan(final Context context, KayıpHayvanlar kayıpHayvanlar){
        itemView.setTag(kayıpHayvanlar);
        kayıpİsim.setText(kayıpHayvanlar.getHayvanİsim());
        kayıpYer.setText(kayıpHayvanlar.getKaybolduğuYer());
        kayıpTarih.setText(kayıpHayvanlar.getKaybolduğuTarih());
        Picasso.get().load(kayıpHayvanlar.getHayvanFoto())
                .fit().centerCrop().into(imageView, new Callback() {
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
