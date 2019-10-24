package com.example.esra.bitirmeprojesi.Profilim.İlanHareketleriniGörüntüle.Sahiplendirme;

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

import com.example.esra.bitirmeprojesi.R;
import com.example.esra.bitirmeprojesi.Sahiplen.PopUpSahiplendirmeActivity;
import com.example.esra.bitirmeprojesi.Sahiplen.Sahiplendirme;
import com.example.esra.bitirmeprojesi.Sahiplen.SahiplendirmeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class İlanHareketleriniGörüntüleSahiplenFragment  extends Fragment implements SahiplendirmeAdapter.MyListener {
    private SahiplendirmeAdapter sahiplendirmeAdapter;
    private RecyclerView srecyclerView;
    private ArrayList<Sahiplendirme> sahiplendirmeArrayList;
    private Sahiplendirme profSahiplendirme;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    public static İlanHareketleriniGörüntüleSahiplenFragment newInstance(){
        return new İlanHareketleriniGörüntüleSahiplenFragment();}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.ilan_har_goruntule_fragment_sahiplen,container,false);
        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        profSahiplendirme=getActivity().getIntent().getParcelableExtra("WORD");

        sahiplendirmeAdapter=new SahiplendirmeAdapter((AppCompatActivity) getActivity(),  this);
        sahiplendirmeArrayList=sahiplendirmeAdapter.getSahiplendirmeArrayList();
        srecyclerView=rootview.findViewById(R.id.ilan_har_sahiplendirme_rv);
        srecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        if (isAdded()){
            srecyclerView.setAdapter(sahiplendirmeAdapter);
        }

         getSahiplendirmeArrayList(sahiplendirmeArrayList);

        return rootview;
    }
    public ArrayList<Sahiplendirme> getSahiplendirmeArrayList(final ArrayList<Sahiplendirme> sahiplendirmeArrayListP){


        DatabaseReference Reference=firebaseDatabase.getReference("Profiller").child(auth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Sahiplendirme");
        Query query=Reference.orderByChild("İlan Zamanı");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sahiplendirmeArrayListP.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap=(HashMap<String, String>) ds.getValue();
                    final String id=String.valueOf(hashMap.get("İlan İD"));
                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Sahiplendirme İlanları");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds: dataSnapshot.getChildren()){
                                HashMap<String,String> hashMap=(HashMap<String, String>) ds.getValue();
                                String idd=String.valueOf(hashMap.get("İlan İD"));
                                if (idd.matches(id)){
                                    String cinsiyet=String.valueOf(hashMap.get("Cinsiyet"));
                                    String hİsmi=String.valueOf(hashMap.get("Hayvan İsmi"));
                                    String türü=String.valueOf(hashMap.get("Hayvan Türü"));
                                    String yaşı=String.valueOf(hashMap.get("Hayvan Yaşı"));
                                    String açıklama=String.valueOf(hashMap.get("İlan Açıklaması"));
                                    String iletisim=String.valueOf(hashMap.get("İletişim Bilgileri"));
                                    String kTarih=String.valueOf(hashMap.get("İlan Tarihi"));
                                    String kYer=String.valueOf(hashMap.get("İl"));
                                    String ilanFoto=String.valueOf(hashMap.get("Fotoğraf"));
                                    String ilce=String.valueOf(hashMap.get("İlçe"));
                                    String ilanid=String.valueOf(hashMap.get("İlan İD"));
                                    String ilanSahibi=String.valueOf(hashMap.get("İlan Sahibi İsim Soyisim"));
                                    String irk=String.valueOf(hashMap.get("Hayvan Irkı"));

                                    sahiplendirmeArrayListP.add(new Sahiplendirme(ilanid,hİsmi,ilanSahibi,türü,irk,cinsiyet,yaşı,kYer,ilce,kTarih,iletisim,açıklama,ilanFoto));
                                    sahiplendirmeAdapter.notifyDataSetChanged();

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



        return sahiplendirmeArrayListP;
    }

    public void MyListener(Sahiplendirme sahiplendirme){
        Intent intent=İlanHarGörSahiplendirmePopUpActivity.ilanHarGorSahiplendirmeyeniIntent(getActivity(),sahiplendirme);
        startActivity(intent);
    }
}
