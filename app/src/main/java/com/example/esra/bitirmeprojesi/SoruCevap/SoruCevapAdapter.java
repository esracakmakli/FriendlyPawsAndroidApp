package com.example.esra.bitirmeprojesi.SoruCevap;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.esra.bitirmeprojesi.Kayıplar.KayıpHayvanlar;
import com.example.esra.bitirmeprojesi.Profilim.İlanHareketleriniGörüntüle.Kayıpİlanların.KayiplarİlanDüzenleActivity;
import com.example.esra.bitirmeprojesi.R;
import com.example.esra.bitirmeprojesi.Yorumlar.Yorumlar;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SoruCevapAdapter extends RecyclerView.Adapter<SoruCevapAdapter.SorularViewHolder> {
    private ArrayList<SoruCevapClass> soruCevapArrayList;
    private LayoutInflater layoutInflater;
    private AppCompatActivity appCompatActivity;
    TextView soru,soruTarihi,sorubaslık,isim;
    CircularImageView profilFoto;
    Button cevapla;

    @NonNull
    @Override
    public SorularViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=layoutInflater.inflate(R.layout.soru_cevap_rv,viewGroup,false);
        soru=itemView.findViewById(R.id.soru_cevap_soru);
        soruTarihi=itemView.findViewById(R.id.soru_cevap_tarih);
        sorubaslık=itemView.findViewById(R.id.soru_cevap_konu);
        isim=itemView.findViewById(R.id.soru_cevap_isim_soyisim);
        cevapla=itemView.findViewById(R.id.soru_cevap_cevapla);
        profilFoto=itemView.findViewById(R.id.soru_cevap_pp);

        return new SorularViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SorularViewHolder sorularViewHolder, int i) {
        sorularViewHolder.getSoru(appCompatActivity,soruCevapArrayList.get(i));
        final SoruCevapClass sorucevap=soruCevapArrayList.get(i);
        sorularViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sorugonder=sorucevap.getSoru();
                String sorubaslıkgonder=sorucevap.getSorubaslık();
                String sorutarihgonder=sorucevap.getTarih();
                String isimgonder=sorucevap.getIsimSoyisim();
                String fotogonder=sorucevap.getPp();
                String id=sorucevap.getSoruİD();
                Intent ıntent=new Intent(appCompatActivity,SoruActivity.class);
                ıntent.putExtra("sorugonder",sorugonder);ıntent.putExtra("sorubaslık",sorubaslıkgonder);ıntent.putExtra("sorutarih",sorutarihgonder);
                ıntent.putExtra("isimgonder",isimgonder);ıntent.putExtra("foto",fotogonder);ıntent.putExtra("idgonder",id);
                appCompatActivity.startActivity(ıntent);

            }
        });


    }

    public SoruCevapAdapter(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
        layoutInflater=appCompatActivity.getLayoutInflater();
        soruCevapArrayList=new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return soruCevapArrayList.size();
    }


    public ArrayList<SoruCevapClass> getSoruCevapArrayList() {
        return soruCevapArrayList;

    }

    public class SorularViewHolder extends RecyclerView.ViewHolder{
        TextView isimSoyisim,soruTarihi,soruBaslık,soru;
        CircularImageView circularImageView;
        Button button;

        public SorularViewHolder(@NonNull View itemView) {
            super(itemView);
            isimSoyisim=itemView.findViewById(R.id.soru_cevap_isim_soyisim);
            soruTarihi=itemView.findViewById(R.id.soru_cevap_tarih);
            soruBaslık=itemView.findViewById(R.id.soru_cevap_konu);
            soru=itemView.findViewById(R.id.soru_cevap_soru);
            button=itemView.findViewById(R.id.soru_cevap_cevapla);
            circularImageView=itemView.findViewById(R.id.soru_cevap_pp);
        }
        public void getSoru(Context context, SoruCevapClass soruCevap){
            itemView.setTag(soruCevap);
            soru.setText(soruCevap.getSoru());
            soruTarihi.setText(soruCevap.getTarih());
            soruBaslık.setText(soruCevap.getSorubaslık());
            isimSoyisim.setText(soruCevap.getIsimSoyisim());
            Picasso.get().load(soruCevap.getPp()).fit().centerCrop().into(circularImageView);


        }

    }
    public  void search(ArrayList<SoruCevapClass> newList){
        soruCevapArrayList=new ArrayList<>();
        soruCevapArrayList.addAll(newList);
        notifyDataSetChanged();

    }


}
