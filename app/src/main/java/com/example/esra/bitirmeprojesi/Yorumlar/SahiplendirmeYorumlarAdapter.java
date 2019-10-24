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

import com.example.esra.bitirmeprojesi.Sahiplen.Sahiplendirme;
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

public class SahiplendirmeYorumlarAdapter extends RecyclerView.Adapter<SahiplendirmeYorumlarAdapter.SahiplendirmeYorumlarViewHolder> {

    private ArrayList<Yorumlar> yorumlarArrayList;
    private LayoutInflater layoutInflater;
    private AppCompatActivity appCompatActivity;
    private Sahiplendirme sahiplendirme;
    CircularImageView circularImageView;
    TextView yorum,yorumSahibi,yorumTarihi;
    ImageView sil;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @NonNull
    @Override
    public SahiplendirmeYorumlarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=layoutInflater.inflate(R.layout.yorumlar_recyclerview,viewGroup,false);
        auth=FirebaseAuth.getInstance();
        circularImageView=itemView.findViewById(R.id.kayıplar_yorum_yorumSahibifoto);
        yorumSahibi=itemView.findViewById(R.id.kayıplar_yorum_yorumsahibi);
        yorum=itemView.findViewById(R.id.kayıplar_yorum_yapılanyorum);
        yorumTarihi=itemView.findViewById(R.id.kayıplar_yorum_yorumtarihi);
        sil=itemView.findViewById(R.id.yorumSil);
        sahiplendirme=(Sahiplendirme) appCompatActivity.getIntent().getParcelableExtra("INFO");
        database=FirebaseDatabase.getInstance();
      /*  databaseReference=database.getReference("Sahiplendirme İlanları").child(sahiplendirme.getsİlanİD()).child("Yorumlar");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    String yorumsahibiid=hashMap.get("Yorum Sahibi İD");
                    if (yorumsahibiid!=null && yorumsahibiid.matches(auth.getCurrentUser().getUid())){
                        sil.setVisibility(View.VISIBLE);
                    } else
                        sil.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        return new SahiplendirmeYorumlarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SahiplendirmeYorumlarViewHolder sahiplendirmeYorumlarViewHolder, int i) {

        final Yorumlar yorum=yorumlarArrayList.get(i);
        sahiplendirmeYorumlarViewHolder.getYorum(appCompatActivity,yorumlarArrayList.get(i));
        sahiplendirmeYorumlarViewHolder.ımageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(sahiplendirmeYorumlarViewHolder.ımageView,"Silinsin mi?",Snackbar.LENGTH_SHORT).setAction("Evet", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference.child(yorum.getYorumid()).removeValue();

                    }
                }).show();
            }
        });
    }

    public SahiplendirmeYorumlarAdapter(AppCompatActivity appCompatActivity, DatabaseReference databaseReference) {
        yorumlarArrayList=new ArrayList<>();
        layoutInflater=appCompatActivity.getLayoutInflater();
        this.appCompatActivity = appCompatActivity;
        this.databaseReference = databaseReference;
    }

    @Override
    public int getItemCount() {
        return yorumlarArrayList.size();
    }

    public ArrayList<Yorumlar> getYorumlarArrayList() {
        return yorumlarArrayList;
    }

    public class SahiplendirmeYorumlarViewHolder extends RecyclerView.ViewHolder{

        public TextView yorum,yorumTarihi,yorumSahibi;
        CircularImageView circularImageView;
        ImageView ımageView;
        ProgressBar progressBar;

        public SahiplendirmeYorumlarViewHolder(@NonNull View itemView) {
            super(itemView);

            yorum=itemView.findViewById(R.id.kayıplar_yorum_yapılanyorum);
            yorumTarihi=itemView.findViewById(R.id.kayıplar_yorum_yorumtarihi);
            yorumSahibi=itemView.findViewById(R.id.kayıplar_yorum_yorumsahibi);
            circularImageView=itemView.findViewById(R.id.kayıplar_yorum_yorumSahibifoto);
            ımageView=itemView.findViewById(R.id.yorumSil);
            progressBar=itemView.findViewById(R.id.kayıplar_yorumlar_progressbar);
        }
        public void getYorum(Context context, Yorumlar yorumlar){
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
