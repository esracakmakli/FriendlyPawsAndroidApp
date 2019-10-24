package com.example.esra.bitirmeprojesi.Sahiplen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esra.bitirmeprojesi.Activity.İlanSahibiActivity;
import com.example.esra.bitirmeprojesi.Activity.İlanSahibiSahiplendirmeActivity;
import com.example.esra.bitirmeprojesi.Yorumlar.Yorumlar;
import com.example.esra.bitirmeprojesi.R;
import com.example.esra.bitirmeprojesi.Yorumlar.SahiplendirmeYorumlarAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class PopUpSahiplendirmeFragment extends Fragment implements View.OnClickListener{
    //layout tanımlamaları
    TextView İsim,Aciklama,Sehir,Cinsiyet,Tür,Yass,İletisimm,İlanSahibi,ilanTarihi,ilce,cinsi;
    ImageView simageView;
    ProgressBar progressBar;
    private Sahiplendirme sahiplendirme;
    //yorumlar için
    RecyclerView recyclerView;
    SahiplendirmeYorumlarAdapter sahiplendirmeYorumlarAdapter;
    private ArrayList<Yorumlar> sahiplendirmeGelenYorumlar;
    EditText sahiplendirmeYorumText;
    ImageButton shplndrmYorumGonder;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseAuth auth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_pop_up_sahiplendirme,container,false);

        progressBar=rootView.findViewById(R.id.fragment_pop_up_sahiplendirme_progressbar);
        İsim=rootView.findViewById(R.id.pop_up_sahiplendirme_isim);
        Aciklama=rootView.findViewById(R.id.pop_up_sahiplendirme_aciklama);
        Sehir=rootView.findViewById(R.id.pop_up_sahiplendirme_sehir);
        Cinsiyet=rootView.findViewById(R.id.pop_up_sahiplendirme_cinsiyet);
        Tür=rootView.findViewById(R.id.pop_up_sahiplendirme_tür);
        cinsi=rootView.findViewById(R.id.pop_up_sahiplendirme_cins);
        Yass=rootView.findViewById(R.id.pop_up_sahiplendirme_yas);
        İletisimm=rootView.findViewById(R.id.pop_up_sahiplendirme_iletisim);
        İlanSahibi=rootView.findViewById(R.id.pop_up_sahiplendirme_ilansahibi);
        simageView=rootView.findViewById(R.id.pop_up_sahiplendirme_imageView);
        ilanTarihi=rootView.findViewById(R.id.pop_up_sahiplendirme_ilantarihi);
        ilce=rootView.findViewById(R.id.pop_up_sahiplendirme_ilçe);
        //yorumlar için
        recyclerView=rootView.findViewById(R.id.pop_up_sahiplendirmeYorumlarRV);
        sahiplendirmeYorumlarAdapter=new SahiplendirmeYorumlarAdapter((AppCompatActivity) getActivity(),databaseReference);
        sahiplendirmeGelenYorumlar=sahiplendirmeYorumlarAdapter.getYorumlarArrayList();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sahiplendirmeYorumlarAdapter);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("Sahiplendirme İlanları");
        sahiplendirmeYorumText=rootView.findViewById(R.id.pop_up_sahiplendirmeYorumYaz);
        shplndrmYorumGonder=rootView.findViewById(R.id.pop_up_sahiplendirme_yorum_gonder_buton);


        İsim.setText(sahiplendirme.getSahiplendirmeİsim());
        Aciklama.setText(sahiplendirme.getSahiplendirmeAciklama());
        Sehir.setText(sahiplendirme.getSahiplendirmeSehir());
        Cinsiyet.setText(sahiplendirme.getSahiplendirmeCinsiyet());
        Tür.setText(sahiplendirme.getSahiplendirmeTür());
        cinsi.setText(sahiplendirme.getIrk());
        Yass.setText(sahiplendirme.getSahiplendirmeYas());
        İletisimm.setText(sahiplendirme.getSahiplendirmeIletisim());
        ilanTarihi.setText(sahiplendirme.getSahiplendirmeTarih());
        ilce.setText(sahiplendirme.getSahiplendirmeİlçe());
        İlanSahibi.setText(sahiplendirme.getSahiplendirmeİlanSahibiİsimSoyisim());

        Picasso.get().load(sahiplendirme.getSahiplendirmeFoto()).fit().centerCrop().into(simageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        İlanSahibi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ilanidS=sahiplendirme.getsİlanİD();
                Intent intent=new Intent(getContext(),İlanSahibiSahiplendirmeActivity.class);
                intent.putExtra("ilan id sahiplendirme",ilanidS);
                startActivity(intent);
            }
        });


        //yorum gönder butonu
        shplndrmYorumGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String gönderilecekYorum=sahiplendirmeYorumText.getText().toString();
                UUID uuid1=UUID.randomUUID();
                final String uuidString=uuid1.toString();
                final SimpleDateFormat bicim=new SimpleDateFormat("dd.MM.yyyy hh:mm aa"); //mevcut tarih ve saati am/pm moduna göre çek
                final Date tarihSaat=new Date();

                final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Sahiplendirme İlanları");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds:dataSnapshot.getChildren()){
                            HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                            String ilanid=String.valueOf(hashMap.get("İlan İD"));
                            if (sahiplendirme.getsİlanİD().matches(ilanid)){
                                final String ilansahibiid = String.valueOf(hashMap.get("İlan Sahibi İD"));
                                DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Profiller");
                                ref.child(ilansahibiid).child("Verilen İlanlar").child("Sahiplendirme").child(sahiplendirme.getsİlanİD()).child("Yorumlar")
                                        .child(uuidString).child("Yorum İD").setValue(uuidString);
                                ref.child(ilansahibiid).child("Verilen İlanlar").child("Sahiplendirme").child(sahiplendirme.getsİlanİD()).child("Yorumlar")
                                        .child(uuidString).child("Yorum").setValue(gönderilecekYorum);
                                ref.child(ilansahibiid).child("Verilen İlanlar").child("Sahiplendirme").child(sahiplendirme.getsİlanİD()).child("Yorumlar")
                                        .child(uuidString).child("Yorum Tarihi").setValue(bicim.format(tarihSaat));
                                ref.child(ilansahibiid).child("Verilen İlanlar").child("Sahiplendirme").child(sahiplendirme.getsİlanİD()).child("Yorumlar")
                                        .child(uuidString).child("Yorum Sahibi İD").setValue(auth.getCurrentUser().getUid());
                                ref.child(ilansahibiid).child("Verilen İlanlar").child("Sahiplendirme").child(sahiplendirme.getsİlanİD()).child("Yorumlar")
                                        .child(uuidString).child("Yorum Zamanı").setValue(ServerValue.TIMESTAMP);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(),"Hata!",Toast.LENGTH_SHORT).show();
                    }
                });

                final DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Profiller");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds:dataSnapshot.getChildren()) {
                            HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                            String id=String.valueOf(hashMap.get("üyeUid"));
                            if (auth.getCurrentUser().getUid().matches(id)){

                                final  String kullanıcı = String.valueOf(hashMap.get("üyeİsimSoyisim"));
                                final  String foto = String.valueOf(hashMap.get("üyeFoto"));

                                databaseReference.child(sahiplendirme.getsİlanİD()).child("Yorumlar").child(uuidString).child("Yorum Sahibi").setValue(kullanıcı);
                                databaseReference.child(sahiplendirme.getsİlanİD()).child("Yorumlar").child(uuidString).child("Yorum İD").setValue(uuidString);
                                databaseReference.child(sahiplendirme.getsİlanİD()).child("Yorumlar").child(uuidString).child("Yorum Sahibi ProfilFoto").setValue(foto);
                                databaseReference.child(sahiplendirme.getsİlanİD()).child("Yorumlar").child(uuidString).child("Yorum").setValue(gönderilecekYorum);
                                databaseReference.child(sahiplendirme.getsİlanİD()).child("Yorumlar").child(uuidString).child("Yorum Sahibi İD").setValue(auth.getCurrentUser().getUid());
                                databaseReference.child(sahiplendirme.getsİlanİD()).child("Yorumlar").child(uuidString).child("Yorum Tarihi").setValue(bicim.format(tarihSaat));
                                databaseReference.child(sahiplendirme.getsİlanİD()).child("Yorumlar").child(uuidString).child("Yorum Zamanı").setValue(ServerValue.TIMESTAMP);
                                final DatabaseReference refe=FirebaseDatabase.getInstance().getReference("Sahiplendirme İlanları");
                                refe.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot ds:dataSnapshot.getChildren()) {
                                            HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                                            String ilanİD=String.valueOf(hashMap.get("İlan İD"));
                                            if (sahiplendirme.getsİlanİD().matches(ilanİD)){
                                                final String ilansahibiid = String.valueOf(hashMap.get("İlan Sahibi İD"));
                                                DatabaseReference r=database.getReference("Profiller");
                                                r.child(ilansahibiid).child("Verilen İlanlar").child("Sahiplendirme").child(sahiplendirme.getsİlanİD()).child("Yorumlar")
                                                        .child(uuidString).child("Yorum Sahibi PP").setValue(foto);
                                                r.child(ilansahibiid).child("Verilen İlanlar").child("Sahiplendirme").child(sahiplendirme.getsİlanİD()).child("Yorumlar")
                                                        .child(uuidString).child("Yorum Sahibi").setValue(kullanıcı);

                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                sahiplendirmeYorumText.setText("");
               getYorumlarArrayList(sahiplendirmeGelenYorumlar);
            }
        });
        getYorumlarArrayList(sahiplendirmeGelenYorumlar);

        return rootView;
    }

    //gelen yorumları firebaseden çekme
    private ArrayList<Yorumlar> getYorumlarArrayList(final ArrayList<Yorumlar> sahiplendirmeGelenYorumlar){
        DatabaseReference newreference=FirebaseDatabase.getInstance().getReference("Sahiplendirme İlanları").child(sahiplendirme.getsİlanİD()).child("Yorumlar");
        newreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sahiplendirmeGelenYorumlar.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap=(HashMap<String, String>) ds.getValue();
                    String yorumYapan=String.valueOf(hashMap.get("Yorum Sahibi"));
                    String gelenYorum=String.valueOf(hashMap.get("Yorum"));
                    String yorumTarihi=String.valueOf(hashMap.get("Yorum Tarihi"));
                    String yorumSahibiPP=hashMap.get("Yorum Sahibi ProfilFoto");
                    String yorumid=String.valueOf(hashMap.get("Yorum İD"));
                    sahiplendirmeGelenYorumlar.add(new Yorumlar(yorumYapan,gelenYorum,yorumTarihi,yorumSahibiPP,yorumid));
                    sahiplendirmeYorumlarAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return sahiplendirmeGelenYorumlar;
    }

    public  static PopUpSahiplendirmeFragment newInstance(){
        return new PopUpSahiplendirmeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sahiplendirme=getActivity().getIntent().getParcelableExtra("INFO");

    }


    @Override
    public void onClick(View v) {

    }

}
