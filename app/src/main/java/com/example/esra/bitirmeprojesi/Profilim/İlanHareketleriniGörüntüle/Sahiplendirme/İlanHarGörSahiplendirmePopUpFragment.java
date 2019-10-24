package com.example.esra.bitirmeprojesi.Profilim.İlanHareketleriniGörüntüle.Sahiplendirme;

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

import com.example.esra.bitirmeprojesi.Profilim.İlanHareketleriniGörüntüle.Kayıpİlanların.KayiplarİlanDüzenleActivity;
import com.example.esra.bitirmeprojesi.Profilim.İlanHareketleriniGörüntüleActivity;
import com.example.esra.bitirmeprojesi.R;
import com.example.esra.bitirmeprojesi.Sahiplen.Sahiplendirme;
import com.example.esra.bitirmeprojesi.Yorumlar.KayıpYorumlarAdapter;
import com.example.esra.bitirmeprojesi.Yorumlar.SahiplendirmeYorumlarAdapter;
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

public class İlanHarGörSahiplendirmePopUpFragment extends Fragment implements View.OnClickListener {

    TextView profSİsim,profSAciklama,profSSehir,profSCinsiyet,profSTür,profSYass,profSİletisimm,profSİlanSahibi,profSİlanTarihi, profSilce,profScins;
    ImageView profSimageView;
    ProgressBar profSprogressBar;
    private Sahiplendirme profSahiplendirme;
    RelativeLayout relativeLayoutS;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    FirebaseAuth auth;
    //yorumlar için
    private RecyclerView recyclerView;
    private SahiplendirmeYorumlarAdapter sahiplendirmeYorumlarAdapter;
     EditText yorumTextprofS;
     ImageButton buttonprofS;
     ArrayList<Yorumlar> gelenYorumlarS;
     TextView yorumSahibiTextprofS,yapılanYorumTextprofS,yorumTarihiTextprofS;
     CircularImageView circularImageViewprofS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.ilan_har_goruntule_sahiplendirme_pop_up_fragment,container,false);
        setHasOptionsMenu(true);
        relativeLayoutS=rootview.findViewById(R.id.ilan_har_gor_sahiplendirme_releative_layout);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        sahiplendirmeYorumlarAdapter=new SahiplendirmeYorumlarAdapter((AppCompatActivity) getActivity(),databaseReference);
        databaseReference=database.getReference("Profiller").child(auth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Sahiplendirme");

        profSİsim=rootview.findViewById(R.id.profilim_sahiplendirme_isim);
        profSAciklama=rootview.findViewById(R.id.profilim_sahiplendirme_aciklama);
        profSSehir=rootview.findViewById(R.id.profilim_sahiplendirme_sehir);
        profSCinsiyet=rootview.findViewById(R.id.profilim_sahiplendirme_cinsiyet);
        profSTür=rootview.findViewById(R.id.profilim_sahiplendirme_tür);
        profScins=rootview.findViewById(R.id.profilim_sahiplendirme_cins);
        profSYass=rootview.findViewById(R.id.profilim_sahiplendirme_yas);
        profSİletisimm=rootview.findViewById(R.id.profilim_sahiplendirme_iletisim);
        profSİlanSahibi=rootview.findViewById(R.id.profilim_sahiplendirme_ilansahibi);
        profSİlanTarihi=rootview.findViewById(R.id.profilim_sahiplendirme_ilantarihi);
        profSilce=rootview.findViewById(R.id.profilim_sahiplendirme_ilçe);
        profSimageView=rootview.findViewById(R.id.profilim_sahiplendirme_imageView);
        profSprogressBar=rootview.findViewById(R.id.profilim_sahiplendirme_progressbar);


        profSİsim.setText(profSahiplendirme.getSahiplendirmeİsim());
        profSAciklama.setText(profSahiplendirme.getSahiplendirmeAciklama());
        profSSehir.setText(profSahiplendirme.getSahiplendirmeSehir());
        profSCinsiyet.setText(profSahiplendirme.getSahiplendirmeCinsiyet());
        profSTür.setText(profSahiplendirme.getSahiplendirmeTür());
        profScins.setText(profSahiplendirme.getIrk());
        profSYass.setText(profSahiplendirme.getSahiplendirmeYas());
        profSİletisimm.setText(profSahiplendirme.getSahiplendirmeIletisim());
        profSİlanSahibi.setText(profSahiplendirme.getSahiplendirmeİlanSahibiİsimSoyisim());
        profSİlanTarihi.setText(profSahiplendirme.getSahiplendirmeTarih());
        profSilce.setText(profSahiplendirme.getSahiplendirmeİlçe());
        Picasso.get().load(profSahiplendirme.getSahiplendirmeFoto()).fit().centerCrop().into(profSimageView, new Callback() {
            @Override
            public void onSuccess() {
                profSprogressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        recyclerView=rootview.findViewById(R.id.profilim_sahiplendirme_sahiplendirmeYorumlarRV);
        yorumSahibiTextprofS=rootview.findViewById(R.id.kayıplar_yorum_yorumsahibi);
        yapılanYorumTextprofS=rootview.findViewById(R.id.kayıplar_yorum_yapılanyorum);
        yorumTarihiTextprofS=rootview.findViewById(R.id.kayıplar_yorum_yorumtarihi);
        circularImageViewprofS=rootview.findViewById(R.id.kayıplar_yorum_yorumSahibifoto);
        yorumTextprofS=rootview.findViewById(R.id.ilan_har_pop_up_kayiplar_kayıplarYorumYaz);
        buttonprofS=rootview.findViewById(R.id.ilan_har_pop_up_kayiplar_buttonyorumGonder);
        sahiplendirmeYorumlarAdapter =new SahiplendirmeYorumlarAdapter((AppCompatActivity) getActivity(),databaseReference);
        gelenYorumlarS= sahiplendirmeYorumlarAdapter.getYorumlarArrayList();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sahiplendirmeYorumlarAdapter);

        getYorumlarArrayList(gelenYorumlarS);
        return rootview;
    }

    private ArrayList<Yorumlar> getYorumlarArrayList(final ArrayList<Yorumlar> gelenYorumlar){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Profiller").child(auth.getCurrentUser().getUid()).child("Verilen İlanlar")
                .child("Sahiplendirme").child(profSahiplendirme.getsİlanİD()).child("Yorumlar");
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
                    sahiplendirmeYorumlarAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Hata!",Toast.LENGTH_LONG).show();
            }
        });
        return gelenYorumlar;
    }
    public static İlanHarGörSahiplendirmePopUpFragment newConstr(){
        return new İlanHarGörSahiplendirmePopUpFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profSahiplendirme=getActivity().getIntent().getParcelableExtra("WORD");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.pop_up_menu_profil_sahiplendir_tasarim, menu);
    }

    public void ilanSilSahiplendirme(){
        DatabaseReference reference=database.getReference("Sahiplendirme İlanları");
        reference.child(profSahiplendirme.getsİlanİD()).removeValue();
        databaseReference=database.getReference("Profiller").child(auth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Sahiplendirme");
        databaseReference.child(profSahiplendirme.getsİlanİD()).removeValue();
        Intent intent=new Intent(getContext(),İlanHareketleriniGörüntüleActivity.class);
        startActivity(intent);
    }


     public void ilanDüzenleS(){
         String idS=profSahiplendirme.getsİlanİD();
         String hismS=profSahiplendirme.getSahiplendirmeİsim();
         String hyasS=profSahiplendirme.getSahiplendirmeYas();
         String htürS=profSahiplendirme.getSahiplendirmeTür();
         String hcinsiyetS=profSahiplendirme.getSahiplendirmeCinsiyet();
         String hsehirS=profSahiplendirme.getSahiplendirmeSehir();
         String hacıklamaS=profSahiplendirme.getSahiplendirmeAciklama();
         String hiletisimS=profSahiplendirme.getSahiplendirmeIletisim();
         String htarihS=profSahiplendirme.getSahiplendirmeTarih();
         String hilceS=profSahiplendirme.getSahiplendirmeİlçe();
         String fotoS=profSahiplendirme.getSahiplendirmeFoto();

         Intent intent=new Intent(getContext(),SahiplendirmeİlanDüzenleActivity.class);
         intent.putExtra("idS",idS);intent.putExtra("yasS",hyasS);intent.putExtra("ilçeS",hilceS);intent.putExtra("tarihS",htarihS);
         intent.putExtra("iletişimS",hiletisimS);intent.putExtra("açıklamaS",hacıklamaS);intent.putExtra("şehirS",hsehirS);intent.putExtra("cinsiyetS",hcinsiyetS);
         intent.putExtra("isimS",hismS);intent.putExtra("türS",htürS);intent.putExtra("fotoS",fotoS);
         startActivity(intent);

     }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.pop_up_menu_düzenle:
                ilanDüzenleS();
                return  true;
            case  R.id.pop_up_menu_sil:
                Snackbar.make(relativeLayoutS,"Silinsin mi?",Snackbar.LENGTH_SHORT).setAction("Evet", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ilanSilSahiplendirme();
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
