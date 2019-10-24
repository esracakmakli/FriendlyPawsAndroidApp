package com.example.esra.bitirmeprojesi.Kayıplar;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.esra.bitirmeprojesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Kayiplar extends Fragment implements KayıpHayvanlarAdapter.MyListener{

   private KayıpHayvanlarAdapter kayıpHayvanlarAdapter;
   private RecyclerView recyclerView;
   private ArrayList<KayıpHayvanlar> kayıplarArrayList;
   private ProgressBar progressBar;

   FirebaseDatabase firebaseDatabase;
   FirebaseAuth auth;
   DatabaseReference databaseReference;


    public static Kayiplar newInstance() {
        return new Kayiplar();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_kayiplar, container, false);
        setHasOptionsMenu(true);
        progressBar=rootView.findViewById(R.id.kayıplar_progressBar);
        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        kayıpHayvanlarAdapter=new KayıpHayvanlarAdapter((AppCompatActivity) getActivity(),this);
        kayıplarArrayList=kayıpHayvanlarAdapter.getKayıplarArrayList();
        recyclerView=rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        if (isAdded()){
            recyclerView.setAdapter(kayıpHayvanlarAdapter);
        }

        getKayıplarArrayList(kayıplarArrayList);
        return rootView;


    }


    private ArrayList<KayıpHayvanlar> getKayıplarArrayList(final ArrayList<KayıpHayvanlar> kayıplarArrayList){

        DatabaseReference Reference=firebaseDatabase.getReference("Kayıp Hayvan İlanları");
        Query query=Reference.orderByChild("İlan Zamanı");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kayıplarArrayList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    HashMap<String,String> hashMap=(HashMap<String, String>) ds.getValue();
                    String cinsiyet=hashMap.get("Cinsiyet");
                    String hİsmi=hashMap.get("Hayvan İsmi");
                    String türü=hashMap.get("Hayvan Türü");
                    String yaşı=hashMap.get("Hayvan Yaşı");
                    String açıklama=hashMap.get("İlan Açıklaması");
                    String iletisim=hashMap.get("İletişim Bilgileri");
                    String kTarih=hashMap.get("Kaybolduğu Tarih");
                    String kYer=hashMap.get("Kaybolduğu İl");
                    String ilanFoto=hashMap.get("Fotoğraf");
                    String ilce=hashMap.get("Kaybolduğu İlçe");
                    String ilanid=hashMap.get("İlan İD");
                    String ilanSahibi=hashMap.get("İlan Sahibi İsim Soyisim");
                    String irki=hashMap.get("Hayvan Irkı");

                    kayıplarArrayList.add(new KayıpHayvanlar(ilanFoto,hİsmi,kYer,kTarih,cinsiyet,türü,irki,yaşı,açıklama,iletisim,ilce,ilanid,ilanSahibi));
                    kayıpHayvanlarAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        return kayıplarArrayList;
    }

    //hangi ilanın çağırıldığını gösterir
    @Override
    public void MyListener(KayıpHayvanlar kayıpHayvanlar) {

        //PopUpActivity i çağırabildik
        Intent intent=PopUpKayiplarActivity.newIntent(getActivity(),kayıpHayvanlar);
        startActivity(intent);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.arama, menu);
        MenuItem searchItem=menu.findItem(R.id.ara);
        SearchView searchView=null;
        if (searchItem!=null){
            searchView=(SearchView) searchItem.getActionView();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
               //* kayıpHayvanlarAdapter.getFilter().filter(s);*//*
                s=s.toLowerCase();
                ArrayList<KayıpHayvanlar> myList=new ArrayList<>();
                for (KayıpHayvanlar kayıpHayvanlar:kayıplarArrayList){
                    String isim=kayıpHayvanlar.getKaybolduğuYer().toLowerCase();
                    if (isim.contains(s))
                        myList.add(kayıpHayvanlar);
                }
                kayıpHayvanlarAdapter.search(myList);
                return false;
            }
        });

    }


}