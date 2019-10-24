package com.example.esra.bitirmeprojesi.Kayıplar;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.esra.bitirmeprojesi.R;

import java.util.ArrayList;
import java.util.List;

public class KayıpHayvanlarAdapter extends RecyclerView.Adapter<KayıpHayvanlarViewHolder> implements View.OnClickListener {

    private ArrayList<KayıpHayvanlar> kayıplarArrayList;
    private LayoutInflater layoutInflater;
    private AppCompatActivity appCompatActivity;
    private MyListener myListener;
    TextView kayıpİsim;
    TextView kayıpYer;
    TextView kayıpTarih;


    public KayıpHayvanlarAdapter(AppCompatActivity appCompatActivity, MyListener myListener) {
        this.appCompatActivity = appCompatActivity;
        this.myListener = myListener;
        layoutInflater=appCompatActivity.getLayoutInflater();
        kayıplarArrayList=new ArrayList<>();
    }

    public ArrayList<KayıpHayvanlar> getKayıplarArrayList() {
        return kayıplarArrayList;
    }

    @NonNull
    @Override
    public KayıpHayvanlarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View ListView=layoutInflater.inflate(R.layout.recyclerview_kayiphayvanlar_tasarim,viewGroup,false);
        ListView.setOnClickListener(this);
        kayıpİsim=ListView.findViewById(R.id.isim);
        kayıpYer=ListView.findViewById(R.id.yer);
        kayıpTarih=ListView.findViewById(R.id.tarih);

        return new KayıpHayvanlarViewHolder(ListView);
    }

    @Override
    public void onBindViewHolder(@NonNull final KayıpHayvanlarViewHolder kayıpHayvanlarViewHolder, int i) {

        kayıpHayvanlarViewHolder.getkayıpİlan(appCompatActivity,kayıplarArrayList.get(i));


    }

    @Override
    public int getItemCount() {
        return kayıplarArrayList.size();
    }

    @Override
    public void onClick(View v) {

        if (v.getTag() instanceof KayıpHayvanlar){
            KayıpHayvanlar kayıpHayvanlar=(KayıpHayvanlar) v.getTag();
            myListener.MyListener(kayıpHayvanlar);
        }
    }

    public interface MyListener{
        public void MyListener(KayıpHayvanlar kayıpHayvanlar);

    }


   public  void search(ArrayList<KayıpHayvanlar> newList){
       kayıplarArrayList=new ArrayList<>();
       kayıplarArrayList.addAll(newList);
       notifyDataSetChanged();

   }

}
