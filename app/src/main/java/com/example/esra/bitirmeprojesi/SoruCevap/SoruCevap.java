package com.example.esra.bitirmeprojesi.SoruCevap;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.esra.bitirmeprojesi.Kayıplar.KayıpHayvanlar;
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

public class SoruCevap extends Fragment {
    private Spinner spinnerkategori;
    private static String[] kategori=new String[] {"Tüm Kategoriler","Genel","Hayvan Sağlığı","Bakım","Beslenme","Eğitim&Oyun","Çiftleştirme","Hayvan Hakları"};
    private ArrayAdapter<String> veriAdaptoru;
    private SoruCevapAdapter soruCevapAdapter;
    private RecyclerView recyclerView;
    private ArrayList<SoruCevapClass> soruCevapArrayList;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    private ProgressBar progressBar;

    public static SoruCevap newInstance(){
        return new SoruCevap();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_soru_cevap,container,false);
        setHasOptionsMenu(true);
        progressBar=rootView.findViewById(R.id.soru_cevap_progressBar2);
        spinnerkategori=rootView.findViewById(R.id.spinnersorukategori);
        veriAdaptoru=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, kategori);
        veriAdaptoru.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerkategori.setAdapter(veriAdaptoru);

        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Soru&Cevap").child("Tüm Kategoriler");
        soruCevapAdapter=new SoruCevapAdapter((AppCompatActivity) getActivity());
        soruCevapArrayList=soruCevapAdapter.getSoruCevapArrayList();
        recyclerView=rootView.findViewById(R.id.soru_cevap_rv);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(soruCevapAdapter);

        spinnerkategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        getSoruCevapArrayList(soruCevapArrayList);
                        break;
                    case 1:

                        genelSoruGetir();
                        break;
                    case 2:
                       hayvanSagligiSoruGetir();
                        break;
                    case 3:
                        getSoruCevapBakim(soruCevapArrayList);
                        break;
                    case 4:
                        getSoruCevapBeslenme(soruCevapArrayList);
                        break;
                    case 5:
                        getSoruCevapEgitimOyun(soruCevapArrayList);
                        break;
                    case 6:
                        getSoruCevapCiftlestirme(soruCevapArrayList);
                        break;
                    case 7:
                        getSoruCevapHayvanHakları(soruCevapArrayList);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return rootView;
    }


    private ArrayList<SoruCevapClass> getSoruCevapArrayList(final ArrayList<SoruCevapClass> soruCevapArrayList){
        DatabaseReference databaseReference=firebaseDatabase.getReference("Soru&Cevap").child("Tüm Kategoriler");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                soruCevapArrayList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    String sorubaslık=String.valueOf(hashMap.get("Soru Başlığı"));
                    String soru=String.valueOf(hashMap.get("Soru"));
                    String tarih=String.valueOf(hashMap.get("Soru Tarihi"));
                    String isim=String.valueOf(hashMap.get("Kullanıcı"));
                    String pp=String.valueOf(hashMap.get("Kullanıcı Profil Fotoğrafı"));
                    String id=String.valueOf(hashMap.get("Soru İD"));

                    soruCevapArrayList.add(new SoruCevapClass(id,sorubaslık,"",soru,pp,isim,tarih));
                    progressBar.setVisibility(View.INVISIBLE);
                    soruCevapAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return soruCevapArrayList;
    }
   public void genelSoruGetir(){
        Query sorgu=databaseReference.orderByChild("Soru Kategorisi").equalTo(kategori[1]);
            sorgu.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    soruCevapArrayList.clear();
                    soruCevapAdapter.notifyDataSetChanged();
                    for (DataSnapshot ds: dataSnapshot.getChildren()) {
                        SoruCevapClass soruCevapClass=ds.getValue(SoruCevapClass.class);
                        soruCevapClass.setSoruİD(ds.getKey());
                        HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                        String sorubaslık=String.valueOf(hashMap.get("Soru Başlığı"));
                        String soru=String.valueOf(hashMap.get("Soru"));
                        String tarih=String.valueOf(hashMap.get("Soru Tarihi"));
                        String isim=String.valueOf(hashMap.get("Kullanıcı"));
                        String pp=String.valueOf(hashMap.get("Kullanıcı Profil Fotoğrafı"));
                        String id=String.valueOf(hashMap.get("Soru İD"));

                        soruCevapArrayList.add(new SoruCevapClass(id,sorubaslık,"",soru,pp,isim,tarih));
                    }
                    soruCevapAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }


   public void hayvanSagligiSoruGetir(){
        Query sorgu=databaseReference.orderByChild("Soru Kategorisi").equalTo(kategori[2]);
        sorgu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                soruCevapArrayList.clear();
                soruCevapAdapter.notifyDataSetChanged();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    String sorubaslık=String.valueOf(hashMap.get("Soru Başlığı"));
                    String soru=String.valueOf(hashMap.get("Soru"));
                    String tarih=String.valueOf(hashMap.get("Soru Tarihi"));
                    String isim=String.valueOf(hashMap.get("Kullanıcı"));
                    String pp=String.valueOf(hashMap.get("Kullanıcı Profil Fotoğrafı"));
                    String id=String.valueOf(hashMap.get("Soru İD"));

                    soruCevapArrayList.add(new SoruCevapClass(id,sorubaslık,"",soru,pp,isim,tarih));
                    soruCevapAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private ArrayList<SoruCevapClass> getSoruCevapBakim(final ArrayList<SoruCevapClass> soruCevapArrayList){
        DatabaseReference databaseReference=firebaseDatabase.getReference("Soru&Cevap").child("Bakım");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                soruCevapArrayList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    String sorubaslık=String.valueOf(hashMap.get("Soru Başlığı"));
                    String soru=String.valueOf(hashMap.get("Soru"));
                    String tarih=String.valueOf(hashMap.get("Soru Tarihi"));
                    String isim=String.valueOf(hashMap.get("Kullanıcı"));
                    String pp=String.valueOf(hashMap.get("Kullanıcı Profil Fotoğrafı"));
                    String id=String.valueOf(hashMap.get("Soru İD"));

                    soruCevapArrayList.add(new SoruCevapClass(id,sorubaslık,"",soru,pp,isim,tarih));
                    soruCevapAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return soruCevapArrayList;
    }
    private ArrayList<SoruCevapClass> getSoruCevapBeslenme(final ArrayList<SoruCevapClass> soruCevapArrayList){
        DatabaseReference databaseReference=firebaseDatabase.getReference("Soru&Cevap").child("Beslenme");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                soruCevapArrayList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    String sorubaslık=String.valueOf(hashMap.get("Soru Başlığı"));
                    String soru=String.valueOf(hashMap.get("Soru"));
                    String tarih=String.valueOf(hashMap.get("Soru Tarihi"));
                    String isim=String.valueOf(hashMap.get("Kullanıcı"));
                    String pp=String.valueOf(hashMap.get("Kullanıcı Profil Fotoğrafı"));
                    String id=String.valueOf(hashMap.get("Soru İD"));

                    soruCevapArrayList.add(new SoruCevapClass(id,sorubaslık,"",soru,pp,isim,tarih));
                    soruCevapAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return soruCevapArrayList;
    }
    private ArrayList<SoruCevapClass> getSoruCevapEgitimOyun(final ArrayList<SoruCevapClass> soruCevapArrayList){
        DatabaseReference databaseReference=firebaseDatabase.getReference("Soru&Cevap").child("Eğitim&Oyun");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                soruCevapArrayList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    String sorubaslık=String.valueOf(hashMap.get("Soru Başlığı"));
                    String soru=String.valueOf(hashMap.get("Soru"));
                    String tarih=String.valueOf(hashMap.get("Soru Tarihi"));
                    String isim=String.valueOf(hashMap.get("Kullanıcı"));
                    String pp=String.valueOf(hashMap.get("Kullanıcı Profil Fotoğrafı"));
                    String id=String.valueOf(hashMap.get("Soru İD"));

                    soruCevapArrayList.add(new SoruCevapClass(id,sorubaslık,"",soru,pp,isim,tarih));
                    soruCevapAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return soruCevapArrayList;
    }
    private ArrayList<SoruCevapClass> getSoruCevapCiftlestirme(final ArrayList<SoruCevapClass> soruCevapArrayList){
        DatabaseReference databaseReference=firebaseDatabase.getReference("Soru&Cevap").child("Çiftleştirme");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                soruCevapArrayList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    String sorubaslık=String.valueOf(hashMap.get("Soru Başlığı"));
                    String soru=String.valueOf(hashMap.get("Soru"));
                    String tarih=String.valueOf(hashMap.get("Soru Tarihi"));
                    String isim=String.valueOf(hashMap.get("Kullanıcı"));
                    String pp=String.valueOf(hashMap.get("Kullanıcı Profil Fotoğrafı"));
                    String id=String.valueOf(hashMap.get("Soru İD"));

                    soruCevapArrayList.add(new SoruCevapClass(id,sorubaslık,"",soru,pp,isim,tarih));
                    soruCevapAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return soruCevapArrayList;
    }
    private ArrayList<SoruCevapClass> getSoruCevapHayvanHakları(final ArrayList<SoruCevapClass> soruCevapArrayList){
        DatabaseReference databaseReference=firebaseDatabase.getReference("Soru&Cevap").child("Hayvan Hakları");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                soruCevapArrayList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    String sorubaslık=String.valueOf(hashMap.get("Soru Başlığı"));
                    String soru=String.valueOf(hashMap.get("Soru"));
                    String tarih=String.valueOf(hashMap.get("Soru Tarihi"));
                    String isim=String.valueOf(hashMap.get("Kullanıcı"));
                    String pp=String.valueOf(hashMap.get("Kullanıcı Profil Fotoğrafı"));
                    String id=String.valueOf(hashMap.get("Soru İD"));

                    soruCevapArrayList.add(new SoruCevapClass(id,sorubaslık,"",soru,pp,isim,tarih));
                    soruCevapAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return soruCevapArrayList;
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

                s=s.toLowerCase();
                ArrayList<SoruCevapClass> myList=new ArrayList<>();
                for (SoruCevapClass soruCevapClass:soruCevapArrayList){
                    String isim=soruCevapClass.getSorubaslık().toLowerCase();
                    if (isim.contains(s))
                        myList.add(soruCevapClass);
                }
                soruCevapAdapter.search(myList);
                return false;
            }
        });

    }
}
