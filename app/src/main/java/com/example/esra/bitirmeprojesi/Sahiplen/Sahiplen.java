package com.example.esra.bitirmeprojesi.Sahiplen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.example.esra.bitirmeprojesi.Kayıplar.KayıpHayvanlar;
import com.example.esra.bitirmeprojesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Sahiplen extends Fragment implements SahiplendirmeAdapter.MyListener{

    private SahiplendirmeAdapter sahiplendirmeAdapter;
    private RecyclerView srecyclerView;
    private ArrayList<Sahiplendirme> sahiplendirmeArrayList;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    private ProgressBar progressBar;

    public static Sahiplen newInstance(){
        return new Sahiplen();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_sahiplen,container,false);
        setHasOptionsMenu(true);
        progressBar=rootView.findViewById(R.id.sahiplen_progressBar);
        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        sahiplendirmeAdapter=new SahiplendirmeAdapter((AppCompatActivity) getActivity(),this);
        sahiplendirmeArrayList=sahiplendirmeAdapter.getSahiplendirmeArrayList();
        srecyclerView=rootView.findViewById(R.id.recyclerView_sahiplendirme);
        srecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        if (isAdded()){
            srecyclerView.setAdapter(sahiplendirmeAdapter);
        }

        getSahiplendirmeArrayList(sahiplendirmeArrayList);

        return rootView;
    }

    //firebaseden verileri çekmek için nu fonksiyonu oluşturduk

    private ArrayList<Sahiplendirme> getSahiplendirmeArrayList(final ArrayList<Sahiplendirme> sahiplendirmeArrayList){

        DatabaseReference Reference=firebaseDatabase.getReference("Sahiplendirme İlanları");
        Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sahiplendirmeArrayList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap=(HashMap<String, String>) ds.getValue();
                    String scinsiyet=hashMap.get("Cinsiyet");
                    String sİsmi=hashMap.get("Hayvan İsmi");
                    String stürü=hashMap.get("Hayvan Türü");
                    String syaşı=hashMap.get("Hayvan Yaşı");
                    String saçıklama=hashMap.get("İlan Açıklaması");
                    String siletisim=hashMap.get("İletişim Bilgileri");
                    String sİl=hashMap.get("İl");
                    String sİlanTarihi=hashMap.get("İlan Tarihi");
                    String sİlçe=hashMap.get("İlçe");
                    String silanFoto=hashMap.get("Fotoğraf");
                    String ilanid=hashMap.get("İlan İD");
                    String ilanSahibiİsim=hashMap.get("İlan Sahibi İsim Soyisim");
                    String irki=hashMap.get("Hayvan Irkı");

                    sahiplendirmeArrayList.add(new Sahiplendirme(ilanid,sİsmi,ilanSahibiİsim,stürü,irki,scinsiyet,syaşı,sİl,sİlçe,sİlanTarihi,siletisim,saçıklama,silanFoto));
                    sahiplendirmeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


        return sahiplendirmeArrayList;
    }    @Override
    public void MyListener(Sahiplendirme sahiplendirme) {

        Intent intent=PopUpSahiplendirmeActivity.newIntent(getActivity(),sahiplendirme);
        startActivity(intent);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.arama_sahiplendirme, menu);
        MenuItem searchItemS=menu.findItem(R.id.ara_sahiplen);
        SearchView searchViewS = null;
        if (searchItemS!=null){
            searchViewS=(SearchView) searchItemS.getActionView();
        }
        searchViewS.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                /* kayıpHayvanlarAdapter.getFilter().filter(s);*/
                s=s.toLowerCase();
                ArrayList<Sahiplendirme> list=new ArrayList<>();
                for (Sahiplendirme sahiplendirme:sahiplendirmeArrayList){
                    String isimS=sahiplendirme.getSahiplendirmeSehir().toLowerCase();
                    if (isimS.contains(s))
                        list.add(sahiplendirme);
                }
                sahiplendirmeAdapter.searchSahiplendirme(list);
                return false;
            }
        });

    }
}
