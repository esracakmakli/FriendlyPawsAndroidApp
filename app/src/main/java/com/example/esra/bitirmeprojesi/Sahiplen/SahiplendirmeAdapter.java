package com.example.esra.bitirmeprojesi.Sahiplen;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esra.bitirmeprojesi.Kayıplar.KayıpHayvanlar;
import com.example.esra.bitirmeprojesi.R;

import java.util.ArrayList;

public class SahiplendirmeAdapter extends RecyclerView.Adapter<SahiplendirmeViewHolder> implements View.OnClickListener {

    private ArrayList<Sahiplendirme> sahiplendirmeArrayList;
    private LayoutInflater slayoutInflater;
    private AppCompatActivity sappCompatActivity;
    private MyListener smyListener;

    public SahiplendirmeAdapter(AppCompatActivity sappCompatActivity, MyListener smyListener) {
        this.sappCompatActivity = sappCompatActivity;
        this.smyListener = smyListener;
        slayoutInflater=sappCompatActivity.getLayoutInflater();
        sahiplendirmeArrayList=new ArrayList<>();
    }

    public ArrayList<Sahiplendirme> getSahiplendirmeArrayList() {
        return sahiplendirmeArrayList;
    }

    @NonNull
    @Override
    public SahiplendirmeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View ListView=slayoutInflater.inflate(R.layout.recyclerview_sahiplendirme_tasarim,viewGroup,false);
        ListView.setOnClickListener(this);

        return new SahiplendirmeViewHolder(ListView);
    }

    @Override
    public void onBindViewHolder(@NonNull SahiplendirmeViewHolder sahiplendirmeViewHolder, int i) {

        sahiplendirmeViewHolder.getSahiplenIlan(sappCompatActivity,sahiplendirmeArrayList.get(i));
    }

    @Override
    public int getItemCount() {
        return sahiplendirmeArrayList.size();
    }

    @Override
    public void onClick(View v) {

        if (v.getTag() instanceof Sahiplendirme){
            Sahiplendirme sahiplendirme=(Sahiplendirme) v.getTag();
            smyListener.MyListener(sahiplendirme);
        }
    }

    public interface MyListener{
        public void MyListener(Sahiplendirme sahiplendirme);

    }

    public  void searchSahiplendirme(ArrayList<Sahiplendirme> list){
        sahiplendirmeArrayList=new ArrayList<>();
        sahiplendirmeArrayList.addAll(list);
        notifyDataSetChanged();

    }
}
