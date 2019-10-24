package com.example.esra.bitirmeprojesi.Profilim.İlanHareketleriniGörüntüle.Kayıpİlanların;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.esra.bitirmeprojesi.Kayıplar.KayıpHayvanlar;
import com.example.esra.bitirmeprojesi.Kayıplar.KayıpHayvanlarAdapter;
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

public class İlanHareketleriniGörüntüleKayiplarFragment extends Fragment implements KayıpHayvanlarAdapter.MyListener{

    private KayıpHayvanlarAdapter kayıpHayvanlarAdapter;
    private RecyclerView recyclerView;
    private ArrayList<KayıpHayvanlar> kayıplarArrayList;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    public static İlanHareketleriniGörüntüleKayiplarFragment newInstance(){
        return new İlanHareketleriniGörüntüleKayiplarFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.ilan_har_goruntule_fragment_kayiplar,container,false);
        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        kayıpHayvanlarAdapter=new KayıpHayvanlarAdapter((AppCompatActivity) getActivity(),this);
        kayıplarArrayList=kayıpHayvanlarAdapter.getKayıplarArrayList();
        recyclerView=rootview.findViewById(R.id.ilan_har_kayıp_ilanların_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        if (isAdded()){
            recyclerView.setAdapter(kayıpHayvanlarAdapter);
        }

        getKayıplarArrayList(kayıplarArrayList);
        return rootview;

    }
    private ArrayList<KayıpHayvanlar> getKayıplarArrayList(final ArrayList<KayıpHayvanlar> kayıplarArrayListP){

        DatabaseReference Reference=firebaseDatabase.getReference("Profiller").child(auth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Kayıp İlanların");
        Query query=Reference.orderByChild("İlan Zamanı");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kayıplarArrayList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap=(HashMap<String, String>) ds.getValue();
                    final String id=String.valueOf(hashMap.get("İlan İD"));
                    DatabaseReference r=firebaseDatabase.getReference("Kayıp Hayvan İlanları");
                    r.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds: dataSnapshot.getChildren()) {
                                HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                                String idd=String.valueOf(hashMap.get("İlan İD"));
                                if (idd.matches(id)){
                                    final String cinsiyet=hashMap.get("Cinsiyet");
                                    final String hİsmi=hashMap.get("Hayvan İsmi");
                                    final  String türü=hashMap.get("Hayvan Türü");
                                    final  String yaşı=hashMap.get("Hayvan Yaşı");
                                    final String açıklama=hashMap.get("İlan Açıklaması");
                                    final String iletisim=hashMap.get("İletişim Bilgileri");
                                    final String kTarih=hashMap.get("Kaybolduğu Tarih");
                                    final String kYer=hashMap.get("Kaybolduğu İl");
                                    final String ilanFoto=hashMap.get("Fotoğraf");
                                    final  String ilce=hashMap.get("Kaybolduğu İlçe");
                                    final  String ilanid=hashMap.get("İlan İD");
                                    final  String irk=String.valueOf(hashMap.get("Hayvan Irkı"));
                                    String ilanSahibi=String.valueOf(hashMap.get("İlan Sahibi İsim Soyisim"));
                                    kayıplarArrayList.add(new KayıpHayvanlar(ilanFoto,hİsmi,kYer,kTarih,cinsiyet,türü,irk,yaşı,açıklama,iletisim,ilce,ilanid,ilanSahibi));
                                    kayıpHayvanlarAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        return kayıplarArrayListP;
    }


    @Override
    public void MyListener(KayıpHayvanlar kayıpHayvanlar) {
        Intent intent=İlanHarGörKayiplarPopUpActivity.profilimKayiplaryeniIntent(getActivity(),kayıpHayvanlar);
        startActivity(intent);
    }
}
