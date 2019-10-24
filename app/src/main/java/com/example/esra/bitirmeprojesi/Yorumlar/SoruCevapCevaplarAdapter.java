package com.example.esra.bitirmeprojesi.Yorumlar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.esra.bitirmeprojesi.Kayıplar.KayıpHayvanlar;
import com.example.esra.bitirmeprojesi.R;
import com.example.esra.bitirmeprojesi.SoruCevap.SoruCevapClass;
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

public class SoruCevapCevaplarAdapter  extends RecyclerView.Adapter<SoruCevapCevaplarAdapter.CevaplarViewHolder>{

    private ArrayList<Yorumlar> cevaplarArrayList;
    private LayoutInflater layoutInflater;
    private AppCompatActivity appCompatActivity;
    private SoruCevapClass soruCevapClass;
    private CircularImageView circularImageView;
    private TextView cevap,cevapSahibi,cevapTarihi;
    private ImageView cevapSil;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @NonNull
    @Override
    public CevaplarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=layoutInflater.inflate(R.layout.yorumlar_recyclerview,viewGroup,false);
        circularImageView=itemView.findViewById(R.id.kayıplar_yorum_yorumSahibifoto);
        cevapSahibi=itemView.findViewById(R.id.kayıplar_yorum_yorumsahibi);
        cevap=itemView.findViewById(R.id.kayıplar_yorum_yapılanyorum);
        cevapTarihi=itemView.findViewById(R.id.kayıplar_yorum_yorumtarihi);
        cevapSil=itemView.findViewById(R.id.yorumSil);
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
       /* soruCevapClass=appCompatActivity.getIntent().getParcelableExtra("sorucevap");
        databaseReference=database.getReference("Soru&Cevap").child(soruCevapClass.getSoruİD()).child("Cevaplar");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    String cevapsahibiid=hashMap.get("Cevap Sahibi İD");
                    if (cevapsahibiid!=null){
                        if (cevapsahibiid.matches(auth.getCurrentUser().getUid())){
                            cevapSil.setVisibility(View.VISIBLE);
                        }
                    } else
                        cevapSil.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        return new CevaplarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CevaplarViewHolder cevaplarViewHolder, int i) {
        final Yorumlar cevap=cevaplarArrayList.get(i);
        cevaplarViewHolder.getCevap(appCompatActivity,cevaplarArrayList.get(i));
        cevaplarViewHolder.cevapSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(cevaplarViewHolder.cevapSil,"Silinsin mi?",Snackbar.LENGTH_SHORT).setAction("Evet", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference.child(cevap.getYorumid()).removeValue();

                    }
                }).show();
            }
        });

    }
    public SoruCevapCevaplarAdapter(AppCompatActivity appCompatActivity, DatabaseReference databasereference) {

        cevaplarArrayList=new ArrayList<>();
        layoutInflater=appCompatActivity.getLayoutInflater();
        this.appCompatActivity = appCompatActivity;
        this.databaseReference=databasereference;
    }

    @Override
    public int getItemCount() {
        return cevaplarArrayList.size() ;
    }
    public ArrayList<Yorumlar> getCevaplarArrayList() {
        return cevaplarArrayList;
    }

    public class CevaplarViewHolder extends RecyclerView.ViewHolder{
     TextView cevap,cevapTarihi,cevapSahibi;
     CircularImageView cevapFoto;
     ImageView cevapSil;
     private ProgressBar progressBar;
     public CevaplarViewHolder(@NonNull View itemView) {
         super(itemView);
         cevap=itemView.findViewById(R.id.kayıplar_yorum_yapılanyorum);
         cevapTarihi=itemView.findViewById(R.id.kayıplar_yorum_yorumtarihi);
         cevapSahibi=itemView.findViewById(R.id.kayıplar_yorum_yorumsahibi);
         cevapFoto=itemView.findViewById(R.id.kayıplar_yorum_yorumSahibifoto);
         cevapSil=itemView.findViewById(R.id.yorumSil);
         progressBar=itemView.findViewById(R.id.kayıplar_yorumlar_progressbar);
     }
     public void getCevap(Context context, Yorumlar yorumlar){
         itemView.setTag(yorumlar);
         cevap.setText(yorumlar.getYorum());
         cevapTarihi.setText(yorumlar.getYorumTarihi());
         cevapSahibi.setText(yorumlar.getYorumSahibi());
         Picasso.get().load(yorumlar.getYorumSahibiFoto())
                 .fit().centerCrop().into(cevapFoto, new Callback() {
             @Override
             public void onSuccess() {
                 progressBar.setVisibility(View.INVISIBLE);
             }

             @Override
             public void onError(Exception e) {

             }
         });

     }
 }

}
