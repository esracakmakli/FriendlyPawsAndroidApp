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

public class KayıpYorumlarAdapter extends RecyclerView.Adapter<KayıpYorumlarAdapter.YorumlarViewHolder> {

    private ArrayList<Yorumlar> yorumlarArrayList;
    private LayoutInflater layoutInflater;
    private AppCompatActivity appCompatActivity;
    private KayıpHayvanlar kayıpHayvanlar;
    CircularImageView circularImageView;
    TextView yorum,yorumSahibi,yorumTarihi;
    ImageView sil;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @NonNull
    @Override
    public YorumlarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=layoutInflater.inflate(R.layout.yorumlar_recyclerview,viewGroup,false);
        circularImageView=itemView.findViewById(R.id.kayıplar_yorum_yorumSahibifoto);
        yorumSahibi=itemView.findViewById(R.id.kayıplar_yorum_yorumsahibi);
        yorum=itemView.findViewById(R.id.kayıplar_yorum_yapılanyorum);
        yorumTarihi=itemView.findViewById(R.id.kayıplar_yorum_yorumtarihi);
        sil=itemView.findViewById(R.id.yorumSil);
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
       /* kayıpHayvanlar=appCompatActivity.getIntent().getParcelableExtra("Info");
        databaseReference=database.getReference("Kayıp Hayvan İlanları").child(kayıpHayvanlar.getIlanİD()).child("Yorumlar");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    String yorumsahibiid=hashMap.get("Yorum Sahibi İD");
                    if (yorumsahibiid!=null){
                        if (yorumsahibiid.matches(auth.getCurrentUser().getUid())){
                            sil.setVisibility(View.VISIBLE);
                        }
                    } else
                        sil.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        return new YorumlarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final YorumlarViewHolder yorumlarViewHolder, int i) {

        final Yorumlar yorum=yorumlarArrayList.get(i);
        yorumlarViewHolder.getYorum(appCompatActivity,yorumlarArrayList.get(i));
        yorumlarViewHolder.ımageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(yorumlarViewHolder.ımageView,"Silinsin mi?",Snackbar.LENGTH_SHORT).setAction("Evet", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference.child(yorum.getYorumid()).removeValue();

                    }
                }).show();
            }
        });

    }

    public KayıpYorumlarAdapter(AppCompatActivity appCompatActivity, DatabaseReference databasereference) {

        yorumlarArrayList=new ArrayList<>();
        layoutInflater=appCompatActivity.getLayoutInflater();
        this.appCompatActivity = appCompatActivity;
        this.databaseReference=databasereference;
    }

    @Override
    public int getItemCount() {
        return yorumlarArrayList.size();
    }

    public ArrayList<Yorumlar> getYorumlarArrayList() {
        return yorumlarArrayList;
    }

    public class YorumlarViewHolder extends RecyclerView.ViewHolder{
        public TextView yorum,yorumTarihi,yorumSahibi;
        CircularImageView circularImageView;
        ImageView ımageView;
        ProgressBar progressBar;
        public YorumlarViewHolder(@NonNull View itemView) {
            super(itemView);

            yorum=itemView.findViewById(R.id.kayıplar_yorum_yapılanyorum);
            yorumTarihi=itemView.findViewById(R.id.kayıplar_yorum_yorumtarihi);
            yorumSahibi=itemView.findViewById(R.id.kayıplar_yorum_yorumsahibi);
            circularImageView=itemView.findViewById(R.id.kayıplar_yorum_yorumSahibifoto);
            ımageView=itemView.findViewById(R.id.yorumSil);
            progressBar=itemView.findViewById(R.id.kayıplar_yorumlar_progressbar);
        }
        public void getYorum(Context context,Yorumlar yorumlar){
            itemView.setTag(yorumlar);
            yorum.setText(yorumlar.getYorum());
            yorumTarihi.setText(yorumlar.getYorumTarihi());
            yorumSahibi.setText(yorumlar.getYorumSahibi());
            Picasso.get().load(yorumlar.getYorumSahibiFoto())
                    .fit().centerCrop().into(circularImageView, new Callback() {
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
