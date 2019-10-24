package com.example.esra.bitirmeprojesi.Profilim.İlanHareketleriniGörüntüle.Kayıpİlanların;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esra.bitirmeprojesi.Kayıplar.KayıpHayvanlar;
import com.example.esra.bitirmeprojesi.Profilim.İlanHareketleriniGörüntüleActivity;
import com.example.esra.bitirmeprojesi.R;
import com.example.esra.bitirmeprojesi.Yorumlar.KayıpYorumlarAdapter;
import com.example.esra.bitirmeprojesi.Yorumlar.Yorumlar;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class İlanHarGörKayiplarPopUpFragment extends Fragment implements View.OnClickListener {


    TextView ilanHismi,ilanHyas,ilanHtür,ilanHcinsiyet,ilanHsehir,ilanHaciklama,ilanHiletisim,ilanHtarih,ilanHilansahibi,ilanHilce,ilanHcins;
    ImageView ilanHimageView;
    ProgressBar ilanHprogressBar;
    private KayıpHayvanlar profKayıpHayvanlar;
    RelativeLayout relativeLayout;

    DatabaseReference databaseReference;
    FirebaseDatabase database;
    FirebaseAuth auth;

    //yorumlar için
    private RecyclerView recyclerView;
    private KayıpYorumlarAdapter kayıpYorumlarAdapter;
    EditText yorumTextS;
    ImageButton buttonS;
    private ArrayList<Yorumlar> gelenYorumlar;
    TextView yorumSahibiTextS,yapılanYorumTextS,yorumTarihiTextS;
    CircularImageView circularImageViewS;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.ilan_har_goruntule_kayiplar_pop_up_fragment,container,false);
        setHasOptionsMenu(true);
        relativeLayout=rootView.findViewById(R.id.ilan_har_gor_kayiplar_releative_layout);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("Profiller").child(auth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Kayıp İlanların").child(profKayıpHayvanlar.getIlanİD());

        //tanımlamaların bağlanması
        ilanHismi=rootView.findViewById(R.id.ilan_har_pop_up_kayiplar_pop_up_isim);
        ilanHyas=rootView.findViewById(R.id.ilan_har_pop_up_kayiplar_pop_up_yas);
        ilanHtür=rootView.findViewById(R.id.ilan_har_pop_up_kayiplar_pop_up_tür);
        ilanHcins=rootView.findViewById(R.id.ilan_har_pop_up_kayiplar_pop_up_cins);
        ilanHcinsiyet=rootView.findViewById(R.id.ilan_har_kayiplar_pop_up_cinsiyet);
        ilanHsehir=rootView.findViewById(R.id.ilan_har_pop_up_kayiplar_pop_up_şehir);
        ilanHaciklama=rootView.findViewById(R.id.ilan_har_pop_up_kayiplar_pop_up_aciklama);
        ilanHiletisim=rootView.findViewById(R.id.ilan_har_pop_up_kayiplar_pop_up_iletisim);
        ilanHtarih=rootView.findViewById(R.id.ilan_har_pop_up_kayiplar_pop_up_tarih);
        ilanHimageView=rootView.findViewById(R.id.ilan_har_pop_up_kayiplar_imageView);
        ilanHilansahibi=rootView.findViewById(R.id.ilan_har_pop_up_kayiplar_pop_up_ilansahibi);
        ilanHilce=rootView.findViewById(R.id.ilan_har_pop_up_kayiplar_pop_up_ilçe);
        ilanHprogressBar=rootView.findViewById(R.id.ilan_har_pop_up_kayiplar_progressbar);

        //verilerin alınması KayıpHayvanlar sınıfından
        ilanHismi.setText(profKayıpHayvanlar.getHayvanİsim());
        ilanHyas.setText(profKayıpHayvanlar.getHayvanYas());
        ilanHtür.setText(profKayıpHayvanlar.getHayvanTür());
        ilanHcins.setText(profKayıpHayvanlar.getIrk());
        ilanHcinsiyet.setText(profKayıpHayvanlar.getHayvanCinsiyet());
        ilanHsehir.setText(profKayıpHayvanlar.getKaybolduğuYer());
        ilanHaciklama.setText(profKayıpHayvanlar.getIlanAciklama());
        ilanHiletisim.setText(profKayıpHayvanlar.getIletisim());
        ilanHtarih.setText(profKayıpHayvanlar.getKaybolduğuTarih());
        ilanHilce.setText(profKayıpHayvanlar.getKaybolduğuİlçe());
        ilanHilansahibi.setText(profKayıpHayvanlar.getIlanSahibiİsimSoyisim());

        Picasso.get().load(profKayıpHayvanlar.getHayvanFoto()).fit().centerCrop().into(ilanHimageView, new Callback() {
            @Override
            public void onSuccess() {
                ilanHprogressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        //yorumlar rv
        recyclerView=rootView.findViewById(R.id.ilan_har_pop_up_kayiplar_kayıplarYorumlarRW);
        yorumSahibiTextS=rootView.findViewById(R.id.kayıplar_yorum_yorumsahibi);
        yapılanYorumTextS=rootView.findViewById(R.id.kayıplar_yorum_yapılanyorum);
        yorumTarihiTextS=rootView.findViewById(R.id.kayıplar_yorum_yorumtarihi);
        circularImageViewS=rootView.findViewById(R.id.kayıplar_yorum_yorumSahibifoto);
        yorumTextS=rootView.findViewById(R.id.ilan_har_pop_up_kayiplar_kayıplarYorumYaz);
        buttonS=rootView.findViewById(R.id.ilan_har_pop_up_kayiplar_buttonyorumGonder);
        kayıpYorumlarAdapter =new KayıpYorumlarAdapter((AppCompatActivity) getActivity(),databaseReference);
        gelenYorumlar= kayıpYorumlarAdapter.getYorumlarArrayList();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(kayıpYorumlarAdapter);

        getYorumlarArrayList(gelenYorumlar);
        return rootView;

    }

    private ArrayList<Yorumlar> getYorumlarArrayList(final ArrayList<Yorumlar> gelenYorumlar){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Profiller").child(auth.getCurrentUser().getUid()).child("Verilen İlanlar")
                .child("Kayıp İlanların").child(profKayıpHayvanlar.getIlanİD()).child("Yorumlar");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gelenYorumlar.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap=(HashMap<String, String>) ds.getValue();
                    String yorumYapan=String.valueOf(hashMap.get("Yorum Sahibi"));
                    String gelenYorum=String.valueOf(hashMap.get("Yorum"));
                    String yorumTarihi=String.valueOf(hashMap.get("Yorum Tarihi"));
                    String yorumSahibiPP=hashMap.get("Yorum Sahibi PP");
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
        return gelenYorumlar;
    }


    public static İlanHarGörKayiplarPopUpFragment newConstr(){
        return new İlanHarGörKayiplarPopUpFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profKayıpHayvanlar=getActivity().getIntent().getParcelableExtra("word");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.pop_up_menu_profil_kayiplar_tasarim, menu);
    }

    public void ilanSil(){
        DatabaseReference reference=database.getReference("Kayıp Hayvan İlanları");
        reference.child(profKayıpHayvanlar.getIlanİD()).removeValue();
        databaseReference.removeValue();
        Intent intent=new Intent(getContext(),İlanHareketleriniGörüntüleActivity.class);
        startActivity(intent);
    }
    public void ilanDüzenle(){

        String id=profKayıpHayvanlar.getIlanİD();
        String hism=profKayıpHayvanlar.getHayvanİsim();
        String hyas=profKayıpHayvanlar.getHayvanYas();
        String htür=profKayıpHayvanlar.getHayvanTür();
        String hcinsiyet=profKayıpHayvanlar.getHayvanCinsiyet();
        String hsehir=profKayıpHayvanlar.getKaybolduğuYer();
        String hacıklama=profKayıpHayvanlar.getIlanAciklama();
        String hiletisim=profKayıpHayvanlar.getIletisim();
        String htarih=profKayıpHayvanlar.getKaybolduğuTarih();
        String hilce=profKayıpHayvanlar.getKaybolduğuİlçe();
        String foto=profKayıpHayvanlar.getHayvanFoto();

        Intent intent=new Intent(getContext(),KayiplarİlanDüzenleActivity.class);
        intent.putExtra("id",id);intent.putExtra("yas",hyas);intent.putExtra("ilçe",hilce);intent.putExtra("tarih",htarih);
        intent.putExtra("iletişim",hiletisim);intent.putExtra("açıklama",hacıklama);intent.putExtra("şehir",hsehir);intent.putExtra("cinsiyet",hcinsiyet);
        intent.putExtra("isim",hism);intent.putExtra("tür",htür);intent.putExtra("foto",foto);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()){
            case R.id.pop_up_menu_profil_kayiplar_düzenle:
               ilanDüzenle();
                return  true;
            case R.id.pop_up_menu_profil_kayiplar_bulundu_olarak_işaretle:
                return true;
            case  R.id.pop_up_menu_profil_kayiplar_sil:
                Snackbar.make(relativeLayout,"Silinsin mi?",Snackbar.LENGTH_SHORT).setAction("Evet", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ilanSil();
                    }
                }).show();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View v) {

    }
}
