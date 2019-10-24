package com.example.esra.bitirmeprojesi.Kayıplar;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import java.util.Date;

import com.example.esra.bitirmeprojesi.Activity.İlanSahibiActivity;
import com.example.esra.bitirmeprojesi.Profilim.İlanHareketleriniGörüntüle.Sahiplendirme.SahiplendirmeİlanDüzenleActivity;
import com.example.esra.bitirmeprojesi.Yorumlar.Yorumlar;
import com.example.esra.bitirmeprojesi.R;
import com.example.esra.bitirmeprojesi.Yorumlar.KayıpYorumlarAdapter;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PopUpKayiplarFragment extends Fragment implements View.OnClickListener{

    //layoutda tanımlamaları
    TextView ismi,yas,tür,cinsiyet,sehir,aciklama,iletisim,tarih,ilansahibi,ilce,cins;
    ImageView imageView;
    ProgressBar progressBar;
    private KayıpHayvanlar kayıpHayvanlar;
    private TextView not;

    //yorumlar için
    RecyclerView recyclerView;
    KayıpYorumlarAdapter kayıpYorumlarAdapter;
    EditText yorumText;
    ImageButton button;
    private ArrayList<Yorumlar> gelenYorumlar;
    TextView yorumSahibiText,yapılanYorumText,yorumTarihiText;
    CircularImageView circularImageView;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseAuth auth;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView=inflater.inflate(R.layout.fragment_pop_up_kayiplar,container,false);
        // OneSignal Initialization
        OneSignal.startInit(getContext())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference("Kayıp Hayvan İlanları").child(kayıpHayvanlar.getIlanİD());
        recyclerView=rootView.findViewById(R.id.kayıplarYorumlarRW);
        yorumText=rootView.findViewById(R.id.kayıplarYorumYaz);
        kayıpYorumlarAdapter =new KayıpYorumlarAdapter((AppCompatActivity) getActivity(),databaseReference);
        gelenYorumlar= kayıpYorumlarAdapter.getYorumlarArrayList();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(kayıpYorumlarAdapter);

        yorumSahibiText=rootView.findViewById(R.id.kayıplar_yorum_yorumsahibi);
        yapılanYorumText=rootView.findViewById(R.id.kayıplar_yorum_yapılanyorum);
        yorumTarihiText=rootView.findViewById(R.id.kayıplar_yorum_yorumtarihi);
        circularImageView=rootView.findViewById(R.id.kayıplar_yorum_yorumSahibifoto);
        button=rootView.findViewById(R.id.buttonyorumGonder);
        progressBar=rootView.findViewById(R.id.fragment_pop_up_progressbar);
        not=rootView.findViewById(R.id.kayıp_yorumNot);

        //tanımlamaların bağlanması
        ismi=rootView.findViewById(R.id.pop_up_isim);
        yas=rootView.findViewById(R.id.pop_up_yas);
        tür=rootView.findViewById(R.id.pop_up_tür);
        cins=rootView.findViewById(R.id.pop_up_cinsi);
        cinsiyet=rootView.findViewById(R.id.pop_up_cinsiyet);
        sehir=rootView.findViewById(R.id.pop_up_şehir);
        aciklama=rootView.findViewById(R.id.pop_up_aciklama);
        iletisim=rootView.findViewById(R.id.pop_up_iletisim);
        tarih=rootView.findViewById(R.id.pop_up_tarih);
        imageView=rootView.findViewById(R.id.pop_up_imageView);
        ilansahibi=rootView.findViewById(R.id.pop_up_ilansahibi);
        ilce=rootView.findViewById(R.id.pop_up_ilçe);

        //verilerin alınması KayıpHayvanlar sınıfından
        ismi.setText(kayıpHayvanlar.getHayvanİsim());
        yas.setText(kayıpHayvanlar.getHayvanYas());
        tür.setText(kayıpHayvanlar.getHayvanTür());
        cins.setText(kayıpHayvanlar.getIrk());
        cinsiyet.setText(kayıpHayvanlar.getHayvanCinsiyet());
        sehir.setText(kayıpHayvanlar.getKaybolduğuYer());
        aciklama.setText(kayıpHayvanlar.getIlanAciklama());
        iletisim.setText(kayıpHayvanlar.getIletisim());
        tarih.setText(kayıpHayvanlar.getKaybolduğuTarih());
        ilce.setText(kayıpHayvanlar.getKaybolduğuİlçe());
        ilansahibi.setText(kayıpHayvanlar.getIlanSahibiİsimSoyisim());

        Picasso.get().load(kayıpHayvanlar.getHayvanFoto()).fit().centerCrop().into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        }) ;

        ilansahibi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ilanid=kayıpHayvanlar.getIlanİD();
                String telno=kayıpHayvanlar.getIletisim();
                Intent intent=new Intent(getContext(),İlanSahibiActivity.class);
                intent.putExtra("ilan id kayiplar",ilanid);
                intent.putExtra("tel no kayıp ilanı",telno);
                startActivity(intent);

            }
        });
        iletisim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        //yorum gönder butonu
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String gönderilecekYorum=yorumText.getText().toString();
                if (yorumText.getText().length()>0){
                    UUID uuid1 = UUID.randomUUID();
                    final String uuidString = uuid1.toString();
                    final SimpleDateFormat bicim = new SimpleDateFormat("dd.MM.yyyy hh:mm aa"); //mevcut tarih ve saati am/pm moduna göre çek
                    final Date tarihSaat = new Date();

                   final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Kayıp Hayvan İlanları");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot ds:dataSnapshot.getChildren()) {
                                HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                                String ilanİD=String.valueOf(hashMap.get("İlan İD"));
                                if (kayıpHayvanlar.getIlanİD().matches(ilanİD)){
                                    final String ilansahibiid = String.valueOf(hashMap.get("İlan Sahibi İD"));
                                    final DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Profiller");
                                    ref.child(ilansahibiid).child("Verilen İlanlar").child("Kayıp İlanların").child(kayıpHayvanlar.getIlanİD()).child("Yorumlar")
                                            .child(uuidString).child("Yorum İD").setValue(uuidString);
                                    ref.child(ilansahibiid).child("Verilen İlanlar").child("Kayıp İlanların").child(kayıpHayvanlar.getIlanİD()).child("Yorumlar")
                                            .child(uuidString).child("Yorum").setValue(gönderilecekYorum);
                                    ref.child(ilansahibiid).child("Verilen İlanlar").child("Kayıp İlanların").child(kayıpHayvanlar.getIlanİD()).child("Yorumlar")
                                            .child(uuidString).child("Yorum Tarihi").setValue(bicim.format(tarihSaat));
                                    ref.child(ilansahibiid).child("Verilen İlanlar").child("Kayıp İlanların").child(kayıpHayvanlar.getIlanİD()).child("Yorumlar")
                                            .child(uuidString).child("Yorum Sahibi İD").setValue(auth.getCurrentUser().getUid());

                                }


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    final DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Profiller");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds:dataSnapshot.getChildren()) {
                                HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                                String id=String.valueOf(hashMap.get("üyeUid"));
                                if (auth.getCurrentUser().getUid().matches(id)) {

                                  final  String kullanıcı = String.valueOf(hashMap.get("üyeİsimSoyisim"));
                                  final  String foto = String.valueOf(hashMap.get("üyeFoto"));

                                    databaseReference.child(kayıpHayvanlar.getIlanİD()).child("Yorumlar").child(uuidString).child("Yorum Sahibi").setValue(kullanıcı);
                                    databaseReference.child(kayıpHayvanlar.getIlanİD()).child("Yorumlar").child(uuidString).child("Yorum İD").setValue(uuidString);
                                    databaseReference.child(kayıpHayvanlar.getIlanİD()).child("Yorumlar").child(uuidString).child("Yorum Sahibi ProfilFoto").setValue(foto);
                                    databaseReference.child(kayıpHayvanlar.getIlanİD()).child("Yorumlar").child(uuidString).child("Yorum").setValue(gönderilecekYorum);
                                    databaseReference.child(kayıpHayvanlar.getIlanİD()).child("Yorumlar").child(uuidString).child("Yorum Sahibi İD").setValue(auth.getCurrentUser().getUid());
                                    databaseReference.child(kayıpHayvanlar.getIlanİD()).child("Yorumlar").child(uuidString).child("Yorum Tarihi").setValue(bicim.format(tarihSaat));
                                    final DatabaseReference refe=FirebaseDatabase.getInstance().getReference("Kayıp Hayvan İlanları");
                                    refe.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot ds:dataSnapshot.getChildren()) {
                                                HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                                                String ilanİD=String.valueOf(hashMap.get("İlan İD"));
                                                if (kayıpHayvanlar.getIlanİD().matches(ilanİD)){
                                                    final String ilansahibiid = String.valueOf(hashMap.get("İlan Sahibi İD"));
                                                    DatabaseReference r=database.getReference("Profiller");
                                                    r.child(ilansahibiid).child("Verilen İlanlar").child("Kayıp İlanların").child(kayıpHayvanlar.getIlanİD()).child("Yorumlar")
                                                            .child(uuidString).child("Yorum Sahibi PP").setValue(foto);
                                                    r.child(ilansahibiid).child("Verilen İlanlar").child("Kayıp İlanların").child(kayıpHayvanlar.getIlanİD()).child("Yorumlar")
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
                            Toast.makeText(getContext(),"Hata!",Toast.LENGTH_SHORT).show();
                        }
                    });


                    yorumText.setText("");
                }else
                    Toast.makeText(getContext(),"Boş yorum gönderilemez!",Toast.LENGTH_SHORT).show();


                getYorumlarArrayList(gelenYorumlar);
            }
        });

        getYorumlarArrayList(gelenYorumlar);
        return rootView;
    }

    private ArrayList<Yorumlar> getYorumlarArrayList(final ArrayList<Yorumlar> gelenYorumlar){
        if (gelenYorumlar.isEmpty()){
            not.setVisibility(View.GONE);
            DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Kayıp Hayvan İlanları").child(kayıpHayvanlar.getIlanİD()).child("Yorumlar");
            Query query=reference.orderByChild("Yorum Zamanı");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    gelenYorumlar.clear();
                    for (DataSnapshot ds:dataSnapshot.getChildren()){
                        HashMap<String,String> hashMap=(HashMap<String, String>) ds.getValue();
                        String yorumYapan=String.valueOf(hashMap.get("Yorum Sahibi"));
                        String gelenYorum=String.valueOf(hashMap.get("Yorum"));
                        String yorumTarihi=String.valueOf(hashMap.get("Yorum Tarihi"));
                        String yorumSahibiPP=hashMap.get("Yorum Sahibi ProfilFoto");
                        String yorumid=String.valueOf(hashMap.get("Yorum İD"));
                        gelenYorumlar.add(new Yorumlar(yorumYapan,gelenYorum,yorumTarihi,yorumSahibiPP,yorumid));
                        kayıpYorumlarAdapter.notifyDataSetChanged();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(),"Hata!",Toast.LENGTH_LONG).show();
                }
            });

        }else {
            not.setText("Henüz yorum yapılmamış, ilk yorum yapan sen ol!");
        }

        return gelenYorumlar;
    }


    //constructer oluşturulması gerekli
    public static PopUpKayiplarFragment newInstance(){
        return new PopUpKayiplarFragment();
    }

    //popupactivity içindeki metodla kayiplarda çağırdığımız intenti burada alıyoruz
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kayıpHayvanlar=getActivity().getIntent().getParcelableExtra("Info");


    }


    @Override
    public void onClick(View v) {

    }
}
