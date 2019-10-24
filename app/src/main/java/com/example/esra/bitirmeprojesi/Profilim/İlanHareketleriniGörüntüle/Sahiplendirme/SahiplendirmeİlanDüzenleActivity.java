package com.example.esra.bitirmeprojesi.Profilim.İlanHareketleriniGörüntüle.Sahiplendirme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esra.bitirmeprojesi.Activity.Main2Activity;
import com.example.esra.bitirmeprojesi.Profilim.İlanHareketleriniGörüntüleActivity;
import com.example.esra.bitirmeprojesi.R;
import com.example.esra.bitirmeprojesi.Sahiplen.PopUpSahiplendirmeActivity;
import com.example.esra.bitirmeprojesi.Sahiplen.Sahiplendirme;
import com.example.esra.bitirmeprojesi.Sahiplen.SahiplendirmeAdapter;
import com.example.esra.bitirmeprojesi.Sahiplen.SahiplendirmeİlanVerActivity;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class SahiplendirmeİlanDüzenleActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText hayvanİsmi,aciklama,ilanİletisim;
    private EditText yas,cinsiyett,ill,ilçe;
    private RoundedImageView foto;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prof_sahiplendirme_ilan_duzenle);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        yas=findViewById(R.id.prof_sahiplendirme_yas_text2);
        cinsiyett=findViewById(R.id.prof_sahiplendirme_icinsiyeti_text3);
        ill=findViewById(R.id.prof_sahiplendirme_il_text4);
        ilçe=findViewById(R.id.prof_sahiplendirme_ilçe_text5);
        progressBar=findViewById(R.id.prgrbsrS);
        mAuth=FirebaseAuth.getInstance();

        hayvanİsmi=findViewById(R.id.prof_sahiplendirme_isim_text);
        aciklama=findViewById(R.id.prof_sahiplendirme_açıklamatext);
        ilanİletisim=findViewById(R.id.prof_sahiplendirme_iletişim);
        foto=findViewById(R.id.prof_sahiplendirme_ilan_düzenle_foto);
        bilgileriGetirS();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void sahiplendirmeİlanDüzenle(View view) {


        if (hayvanİsmi.getText().length()>0&&aciklama.getText().length() > 0 && ilanİletisim.getText().length() > 0 && yas.getText().length()>0 && ill.getText().length()>0
        &&ilçe.getText().length()>0 &&cinsiyett.getText().length()>0) {

            String isim = hayvanİsmi.getText().toString();
            String hayvanYasi = yas.getText().toString();
            String acıklama = aciklama.getText().toString();
            String il = ill.getText().toString();
            String ilce = ilçe.getText().toString();
            String cinsiyet = cinsiyett.getText().toString();
            String iletisim = ilanİletisim.getText().toString();

            Intent intent = getIntent();
            String idS = intent.getStringExtra("idS");
            final DatabaseReference myRef=FirebaseDatabase.getInstance().getReference("Sahiplendirme İlanları");
            myRef.child(idS).child("İlan Açıklaması").setValue(acıklama);
            myRef.child(idS).child("İletişim Bilgileri").setValue(iletisim);
            myRef.child(idS).child("Hayvan İsmi").setValue(isim);


            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Profiller");

            databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Sahiplendirme").child(idS).child("İlan Açıklaması").setValue(acıklama);
            databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Sahiplendirme").child(idS).child("İletişim Bilgileri").setValue(iletisim);
            databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Sahiplendirme").child(idS).child("Hayvan İsmi").setValue(isim);
            databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Sahiplendirme").child(idS).child("Hayvan Yaşı").setValue(hayvanYasi);
            databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Sahiplendirme").child(idS).child("Cinsiyet").setValue(cinsiyet);
            databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Sahiplendirme").child(idS).child("İl").setValue(il);
            databaseReference.child(mAuth.getCurrentUser().getUid()).child("Verilen İlanlar").child("Sahiplendirme").child(idS).child("İlçe").setValue(ilce);

            myRef.child(idS).child("İl").setValue(il);
            myRef.child(idS).child("İlçe").setValue(ilce);
            myRef.child(idS).child("Cinsiyet").setValue(cinsiyet);
            myRef.child(idS).child("Hayvan Yaşı").setValue(hayvanYasi);

            Toast.makeText(SahiplendirmeİlanDüzenleActivity.this, "İlan Düzenlendi!", Toast.LENGTH_LONG).show();
            Intent ıntent = new Intent(getApplicationContext(), İlanHareketleriniGörüntüleActivity.class);
            startActivity(ıntent);

        }else
            Toast.makeText(getApplicationContext(),"Bilgiler boş geçilemez!",Toast.LENGTH_LONG).show();

    }

    public void bilgileriGetirS(){

        Intent intent = getIntent();
        String hism=intent.getStringExtra("isimS");
        String hyas=intent.getStringExtra("yasS");
        String hcinsiyet=intent.getStringExtra("cinsiyetS");
        String hsehir=intent.getStringExtra("şehirS");
        String hacıklama=intent.getStringExtra("açıklamaS");
        String hiletisim=intent.getStringExtra("iletişimS");
        String hilceS=intent.getStringExtra("ilçeS");
        String fotoğ=intent.getStringExtra("fotoS");

        hayvanİsmi.setText(hism);aciklama.setText(hacıklama);ilanİletisim.setText(hiletisim);ill.setText(hsehir);yas.setText(hyas);
        cinsiyett.setText(hcinsiyet);ilçe.setText(hilceS);

        Picasso.get().load(fotoğ).fit().centerCrop().into(foto, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }


    private static String[] bos=new String[] {"İlçeyi değiştirin"};
    private static String[] adanaİlçeler=new String[] {" Aladağ " , " Ceyhan " , " Çukurova " , " Feke " , " İmamoğlu " , " Karaisalı " , " Karataş " , " Kozan " , " Pozantı " , " Saimbeyli " , " Sarıçam " , "Seyhan " , "Tufanbeyli " , " Yumurtalık " , " Yüreğir "} ;
    private static String[] adıyamanİlçeler=new String[] { " Adıyaman " , " Besni " , " Çelikhan " , " Gerger " , " Gölbaşı " , " Kâhta " , " Samsat " , " Sincik " , " Tut " };
    private static String[] afyonkarahisarİlçeler=new String[] {" Afyonkarahisar " , " Başmakçı " , " Bayat " , " Bolvadin " , " Çay " , " Çobanlar " , " Dazkırı " , " Dinar " , " Emirdağ " , " Evciler " , " Hocalar " , "İhsaniye " , "İscehisar " , " Kızılören " , " Sandıklı " , " Sinanpaşa " , " Sultandağı " , " Şuhut "};
    private static String[] ağrıİlçeler=new String[] {" Ağrı " , " Diyadin " , " Doğubayazıt " , " Eleşkirt " , " Hamur " , " Patnos " , " Taşlıçay " , " Tutak " };
    private static String[] aksarayİlçeler=new String[] { " Ağaçören " , " Aksaray " , " Eskil " , " Gülağaç " , " Güzelyurt " , " Ortaköy " , " Sarıyahşi " };
    private static String[] amasyaİlçeler=new String[] {" Amasya " , " Göynücek " , " Gümüşhacıköy " , " Hamamözü " , " Merzifon " , " Suluova " , " Taşova " };
    private static String[] ankaraİlçeler=new String[] { " Akyurt " , " Altındağ " , " Ayaş " , " Balâ " , " Beypazarı " , " Çamlıdere " , " Çankaya " , " Çubuk " , " Elmadağ " , " Etimesgut " , " Evren " , "Gölbaşı " , "Güdül " , " Haymana " , " Kalecik " , " Kahramankazan " , " Keçiören " , " Kızılcahamam " , " Mamak " , " Nallıhan " , " Polatlı " , " Pursaklar " , " Sincan " , " Şereflikoçhisar " , "Yenimahalle " };
    private static String[] antalyaİlçeler=new String[] { " Akseki " , " Aksu " , " Alanya " , " Döşemealtı " , " Elmalı " , " Finike " , " Gazipaşa " , " Gündoğmuş " , " İbradı " , " Demre " , " Kaş " , "Kemer " , "Kepez " , " Konyaaltı " , " Korkuteli " , " Kumluca " , " Manavgat " , " Muratpaşa " , " Serik " };
    private static String[] ardahanİlçeler=new String[] {" Ardahan " , " Çıldır " , " Damal " , " Göle " , " Hanak " , " Posof "};
    private static String[] artvinİlçeler=new String[] {" Ardanuç " , " Arhavi " , " Artvin " , " Borçka " , " Hopa " , " Murgul " , " Şavşat " , " Yusufeli " };
    private static String[] aydınİlçeler=new String[] {" Bozdoğan " , " Buharkent " , " Çine " , " Didim " , " Efeler " , " Germencik " , " İncirliova " , " Karacasu " , " Karpuzlu " , " Koçarlı " , " Köşk " , "Kuşadası " , "Kuyucak " , " Nazilli " , " Söke " , " Sultanhisar " , " Yenipazar " };
    private static String[] balıkesirİlçeler=new String[] {" Altıeylül " , " Ayvalık " , " Balya " , " Bandırma " , " Bigadiç " , " Burhaniye " , " Dursunbey " , " Edremit " , " Erdek " , " Gömeç " , " Gönen " , "Havran " , "İvrindi " , " Karesi " , " Kepsut " , " Manyas " , " Marmara " , " Savaştepe " , " Sındırgı " , " Susurluk " };
    private static String[] bartınİlçeler=new String[] {" Amasra " , " Bartın " , " Kurucaşile " , " Ulus " };
    private static String[] batmanİlçeler=new String[] { " Batman " , " Beşiri " , " Gercüş " , " Hasankeyf " , " Kozluk " , " Sason " };
    private static String[] bayburtİlçeler=new String[] {" Aydıntepe " , " Bayburt (İl merkezi) " , " Demirözü "};
    private static String[] bilecikİlçeler=new String[] {" Bilecik " , " Bozüyük " , " Gölpazarı " , " İnhisar " , " Osmaneli " , " Pazaryeri " , " Söğüt " , " Yenipazar " };
    private static String[] bingolİlçeler=new String[] { " Adaklı " , " Bingöl " , " Genç " , " Karlıova " , " Kiğı " , " Solhan " , " Yayladere " , " Yedisu " };
    private static String[] bitlisİlçeler=new String[] { " Adilcevaz " , " Ahlat " , " Bitlis " , " Güroymak " , " Hizan " , " Mutki " , " Tatvan " };
    private static String[] boluİlçeler=new String[] {" Bolu " , " Dörtdivan " , " Gerede " , " Göynük " , " Kıbrıscık " , " Mengen " , " Mudurnu " , " Seben " , " Yeniçağa " };
    private static String[] burdurİlçeler=new String[] {" Ağlasun " , " Altınyayla " , " Bucak " , " Burdur " , " Çavdır " , " Çeltikçi " , " Gölhisar " , " Karamanlı " , " Kemer " , " Tefenni " , " Yeşilova " };
    private static String[] bursaİlçeler=new String[] {" Büyükorhan " , " Gemlik " , " Gürsu " , " Harmancık " , " İnegöl " , " İznik " , " Karacabey " , " Keles " , " Kestel " , " Mudanya " , " Mustafakemalpaşa " ," Nilüfer " ," Orhaneli " , " Orhangazi " , " Osmangazi " , " Yenişehir " , " Yıldırım " };
    private static String[] canakkaleİlçeler=new String[] {" Ayvacık " , " Bayramiç " , " Biga " , " Bozcaada " , " Çan " , " Çanakkale " , " Eceabat " , " Ezine " , " Gelibolu " , " Gökçeada " , " Lapseki " , "Yenice " };
    private static String[] cankırıİlçeler=new String[] {" Atkaracalar " , " Bayramören " , " Çankırı " , " Çerkeş " , " Eldivan " , " Ilgaz " , " Kızılırmak " , " Korgun " , " Kurşunlu " , " Orta " , " Şabanözü " ," Yapraklı " };
    private static String[] corumİlçeler=new String[] { " Alaca " , " Bayat " , " Boğazkale " , " Çorum " , " Dodurga " , " İskilip " , " Kargı " , " Laçin " , " Mecitözü " , " Oğuzlar " , " Ortaköy " , "Osmancık " , "Sungurlu " , " Uğurludağ " };
    private static String[] denizliİlçeler=new String[] {" Acıpayam " , " Babadağ " , " Baklan " , " Bekilli " , " Beyağaç " , " Bozkurt " , " Buldan " , " Çal " , " Çameli " , " Çardak " , " Çivril " , "Güney " , "Honaz " , " Kale " , " Merkezefendi " , " Pamukkale " , " Sarayköy " , " Serinhisar " , " Tavas "};
    private static String[] diyarbakirİlçeler=new String[] {" Bağlar " , " Bismil " , " Çermik " , " Çınar " , " Çüngüş " , " Dicle " , " Eğil " , " Ergani " , " Hani " , " Hazro " , " Kayapınar " , "Kocaköy " , " Kulp" , " Lice " , " Silvan " , " Sur " , " Yenişehir " };
    private static String[] düzceİlçeler=new String[] {" Akçakoca " , " Cumayeri " , " Çilimli " , " Düzce " , " Gölyaka " , " Gümüşova " , " Kaynaşlı " , " Yığılca " };
    private static String[] edirneİlçeler=new String[] {" Enez " , " Havsa " , " İpsala " , " Keşan " , " Lalapaşa " , " Meriç " , " Merkez " , " Süloğlu " , " Uzunköprü " };
    private static String[] elazıgİlçeler=new String[] {" Ağın " , " Alacakaya " , " Arıcak " , " Baskil " , " Elâzığ " , " Karakoçan " , " Keban " , " Kovancılar " , " Maden " , " Palu " , " Sivrice " };
    private static String[] erzincanİlçeler=new String[] {" Çayırlı " , " Erzincan " , " İliç " , " Kemah " , " Kemaliye " , " Otlukbeli " , " Refahiye " , " Tercan " , " Üzümlü " };
    private static String[] erzurumİlçeler=new String[] {" Aşkale " , " Aziziye " , " Çat " , " Hınıs " , " Horasan " , " İspir " , " Karaçoban " , " Karayazı " , " Köprüköy " , " Narman " , " Oltu " , "Olur " , "Palandöken " , " Pasinler " , " Pazaryolu " , " Şenkaya " , " Tekman " , " Tortum " , " Uzundere " , " Yakutiye " };
    private static String[] eskisehirİlçeler=new String[] {" Alpu " , " Beylikova " , " Çifteler " , " Günyüzü " , " Han " , " İnönü " , " Mahmudiye " , " Mihalgazi " , " Mihalıççık " , " Odunpazarı " , " Sarıcakaya " ," Seyitgazi " ," Sivrihisar " , " Tepebaşı " };
    private static String[] gaziantepİlçeler=new String[] { " Araban " , " İslahiye " , " Karkamış " , " Nizip " , " Nurdağı " , " Oğuzeli " , " Şahinbey " , " Şehitkâmil " , " Yavuzeli " };
    private static String[] giresunİlçeler=new String[] {" Alucra " , " Bulancak " , " Çamoluk " , " Çanakçı " , " Dereli " , " Doğankent " , " Espiye " , " Eynesil " , " Giresun " , " Görele " , " Güce " , "Keşap " , "Piraziz " , " Şebinkarahisar " , " Tirebolu " , " Yağlıdere " };
    private static String[] gümüshaneİlçeler=new String[] {" Gümüşhane " , " Kelkit " , " Köse " , " Kürtün " , " Şiran " , " Torul " };
    private static String[] hakkariİlçeler=new String[] {" Çukurca " , " Hakkâri " , " Şemdinli " , " Yüksekova "};
    private static String[] hatayİlçeler=new String[] { " Altınözü " , " Antakya " , " Arsuz " , " Belen " , " Defne " , " Dörtyol " , " Erzin " , " Hassa " , " İskenderun " , " Kırıkhan " , " Kumlu " , "Payas " , "Reyhanlı " , " Samandağ " , " Yayladağı " };
    private static String[] ıgdırİlçeler=new String[] {" Aralık " , " Iğdır " , " Karakoyunlu " , " Tuzluca " };
    private static String[] ıspartaİlçeler=new String[] { " Aksu " , " Atabey " , " Eğirdir " , " Gelendost " , " Gönen " , " Isparta " , " Keçiborlu " , " Senirkent " , " Sütçüler " , " Şarkikaraağaç " , " Uluborlu " ," Yalvaç " ," Yenişarbademli " };
    private static String[] istanbulİlçeler=new String[] { " Adalar " , " Arnavutköy " , " Ataşehir " , " Avcılar " , " Bağcılar " , " Bahçelievler " , " Bakırköy " , " Başakşehir " , " Bayrampaşa " , " Beşiktaş " , " Beykoz " ," Beylikdüzü " ," Beyoğlu " , " Büyükçekmece " , " Çatalca " , " Çekmeköy " , " Esenler " , " Esenyurt " , " Eyüp " , " Fatih " , " Gaziosmanpaşa " , " Güngören " , " Kadıköy " , " Kağıthane " , "Kartal " , " Küçükçekmece" , " Maltepe " , " Pendik " , " Sancaktepe " , " Sarıyer " , " Silivri " , " Sultanbeyli " , " Sultangazi " , " Şile " , " Şişli " , " Tuzla " , " Ümraniye " , " Üsküdar " , "Zeytinburnu " };
    private static String[] izmirİlçeler=new String[] { " Aliağa " , " Balçova " , " Bayındır " , " Bayraklı " , " Bergama " , " Beydağ " , " Bornova " , " Buca " , " Çeşme " , " Çiğli " , " Dikili " , "Foça " , "Gaziemir " , " Güzelbahçe " , " Karabağlar " , " Karaburun " , " Karşıyaka " , " Kemalpaşa " , " Kınık " , " Kiraz " , " Konak " , " Menderes " , " Menemen " , " Narlıdere " , " Ödemiş ", " Seferihisar ", " Selçuk " , " Lastik " , " Torbalı " , " Urla " };
    private static String[] kahramanmarasİlçeler=new String[] {" Afşin " , " Andırın " , " Çağlayancerit " , " Dulkadiroğlu " , " Ekinözü " , " Elbistan " , " Göksun " , " Nurhak " , " Onikişubat " , " Pazarcık " , " Türkoğlu " };
    private static String[] karabukİlçeler=new String[] {" Eflani " , " Eskipazar " , " Karabük " , " Ovacık " , " Safranbolu " , " Yenice " };
    private static String[] karamanİlçeler=new String[] { " Ayrancı " , " Başyayla " , " Ermenek " , " Karaman " , " Kazımkarabekir " , " Sarıveliler " };
    private static String[] karsİlçeler=new String[] {" Akyaka " , " Arpaçay " , " Digor " , " Kağızman " , " Kars " , " Sarıkamış " , " Selim " , " Susuz " };
    private static String[] kastamonuİlçeler=new String[] {" Abana " , " Ağlı " , " Araç " , " Azdavay " , " Bozkurt " , " Cide " , " Çatalzeytin " , " Daday " , " Devrekani " , " Doğanyurt " , " Hanönü " , "İhsangazi " , "İnebolu " , " Kastamonu " , " Küre " , " Pınarbaşı " , " Seydiler " , " Şenpazar " , " Taşköprü " , " Tosya " };
    private static String[] kayseriİlçeler=new String[] { " Akkışla " , " Bünyan " , " Develi " , " Felahiye " , " Hacılar " , " İncesu " , " Kocasinan " , " Melikgazi " , " Özvatan " , " Pınarbaşı " , " Sarıoğlan " ," Sarız " , "Talas " , " Tomarza " , " Yahyalı " , " Yeşilhisar " };
    private static String[] kırıkkaleİlçeler=new String[] {" Bahşılı " , " Balışeyh " , " Çelebi " , " Delice " , " Karakeçili " , " Keskin " , " Kırıkkale " , " Sulakyurt " , " Yahşihan " };
    private static String[] kırklareliİlçeler=new String[] {" Babaeski " , " Demirköy " , " Kırklareli " , " Kofçaz " , " Lüleburgaz " , " Pehlivanköy " , " Pınarhisar " , " Vize " };
    private static String[] kırsehirİlçeler=new String[] {" Akçakent " , " Akpınar " , " Boztepe " , " Çiçekdağı " , " Kaman " , " Kırşehir " , " Mucur " };
    private static String[] kilisİlçeler=new String[] { " Elbeyli " , " Kilis " , " Musabeyli " , " Polateli "};
    private static String[] kocaeliİlçeler=new String[] {" Başiskele " , " Çayırova " , " Darıca " , " Derince " , " Dilovası " , " Gebze " , " Gölcük " , " İzmit " , " Kandıra " , " Karamürsel " , " Kartepe " , "Körfez " };
    private static String[] konyaİlçeler=new String[] {" Ahırlı " , " Akören " , " Akşehir " , " Altınekin " , " Beyşehir " , " Bozkır " , " Cihanbeyli " , " Çeltik " , " Çumra " , " Derbent " , " Derebucak " , "Doğanhisar " , "Emirgazi " , " Ereğli " , " Güneyini " , " Hadım " , " Halkapınar " , " Hüyük " , " Ilgın " , " Kadınhanı " , " Karapınar " , " Karatay " , " Kulu " , " Meram " , " Sarayönü " ," Selçuklu " ," Seydişehir " , " Taşkent " , " Tuzlukçu " , " Yalıhüyük " , " Yunak " };
    private static String[] kütahyaİlçeler=new String[] { " Altıntaş " , " Aslanapa " , " Çavdarhisar " , " Domaniç " , " Dumlupınar " , " Emet " , " Gediz " , " Hisarcık " , " Kütahya " , " Pazarlar " , " Şaphane " ," Simav " , "Tavşanlı " };
    private static String[] malatyaİlçeler=new String[] {" Akçadağ " , " Arapgir " , " Arguvan " , " Battalgazi " , " Darende " , " Doğanşehir " , " Doğanyol " , " Hekimhan " , " Kale " , " Kuluncak " , " Pütürge " ," Yazıhan " ," Yeşilyurt " };
    private static String[] manisaİlçeler=new String[] {" Ahmetli " , " Akhisar " , " Alaşehir " , " Demirci " , " Gölmarmara " , " Gördes " , " Kırkağaç " , " Köprübaşı " , " Kula " , " Salihli " , " Sarıgöl " , "Saruhanlı " , "Selendi " , " Soma " , " Şehzadeler " , " Turgutlu " , " Yunusemre " };
    private static String[] mardinİlçeler=new String[] { " Artuklu " , " Dargeçit " , " Derik " , " Kızıltepe " , " Mazıdağı " , " Midyat " , " Nusaybin " , " Ömerli " , " Savur " , " Yeşilli " };
    private static String[] mersinİlçeler=new String[] { " Akdeniz " , " Anamur " , " Aydıncık " , " Bozyazı " , " Çamlıyayla " , " Erdemli " , " Gülnar " , " Mezitli " , " Mut " , " Silifke " , " Tarsus " , "Toroslar " , "Yenişehir " };
    private static String[] muğlaİlçeler=new String[] {" Bodrum " , " Dalaman " , " Datça " , " Fethiye " , " Kavaklıdere " , " Köyceğiz " , " Marmaris " , " Menteşe " , " Milas " , " Ortaca " , " Seydikemer " , "Ula " , "Yatağan " };
    private static String[] muşİlçeler=new String[] {" Bulanık " , " Hasköy " , " Korkut " , " Malazgirt " , " Muş " , " Varto " };
    private static String[] nevsehirİlçeler=new String[] {" Acıgöl " , " Avanos " , " Derinkuyu " , " Gülşehir " , " Hacıbektaş " , " Kozaklı " , " Nevşehir " , " Ürgüp " };
    private static String[] niğdeİlçeler=new String[] {" Altunhisar " , " Bor " , " Çamardı " , " Çiftlik " , " Niğde " , " Ulukışla " };
    private static String[] orduİlçeler=new String[] {" Akkuş " , " Altınordu " , " Aybastı " , " Çamaş " , " Çatalpınar " , " Çaybaşı " , " Fatsa " , " Gölköy " , " Gülyalı " , " Gürgentepe " , " İkizce " , "Kabadüz " , "Kabataş " , " Korgan " , " Kumru " , " Mesudiye " , " Perşembe " , " Ulubey " , " Ünye " };
    private static String[] osmaniyeİlçeler=new String[] {" Bahçe " , " Düziçi " , " Hasanbeyli " , " Kadirli " , " Osmaniye " , " Sumbas " , " Toprakkale " };
    private static String[] rizeİlçeler=new String[] {" Ardeşen " , " Çamlıhemşin " , " Çayeli " , " Derepazarı " , " Fındıklı " , " Güneysu " , " Hemşin " , " İkizdere " , " İyidere " , " Kalkandere " , " Pazar " ," Rize " };
    private static String[] sakaryaİlçeler=new String[] { " Adapazarı " , " Akyazı " , " Arifiye " , " Erenler " , " Ferizli " , " Geyve " , " Hendek " , " Karapürçek " , " Karasu " , " Kaynarca " , " Kocaali " , "Pamukova " , "Sapanca " , " Serdivan " , " Söğütlü " , " Taraklı " };
    private static String[] samsunİlçeler=new String[] {" Alaçam " , " Asarcık " , " Atakum " , " Ayvacık " , " Bafra " , " Canik " , " Çarşamba " , " Havza " , " İlkadım " , " Kavak " , " Ladik " , "Ondokuzmayıs " , "Salıpazarı " , " Tekkeköy " , " Terme " , " Vezirköprü " , " Yakakent " };
    private static String[] siirtİlçeler=new String[] { " Siirt " , " Tillo " , " Baykan " , " Eruh " , " Kurtalan " , " Pervari " , " Şirvan " };
    private static String[] sinopİlçeler=new String[] { " Ayancık " , " Boyabat " , " Dikmen " , " Durağan " , " Erfelek " , " Gerze " , " Saraydüzü " , " Sinop " , " Türkeli " };
    private static String[] sivasİlçeler=new String[] { " Akıncılar " , " Altınyayla " , " Divriği " , " Doğanşar " , " Gemerek " , " Gölova " , " Hafik " , " İmranlı " , " Kangal " , " Koyulhisar " , " Sivas " , "Suşehri " , "Şarkışla " , " Ulaş " , " Yıldızeli " , " Zara " , " Gürün " };
    private static String[] sanlıurfaİlçeler=new String[] {" Akçakale " , " Birecik " , " Bozova " , " Ceylanpınar " , " Eyyübiye " , " Halfeti " , " Haliliye " , " Harran " , " Hilvan " , " Karaköprü " , " Siverek " ," Suruç " , "Viranşehir " };
    private static String[] sırnakİlçeler=new String[] {" Beytüşşebap " , " Cizre " , " Güçlükonak " , " İdil " , " Silopi " , " Şırnak " , " Uludere " };
    private static String[] tekirdağİlçeler=new String[] {" Çerkezköy " , " Çorlu " , " Ergene " , " Hayrabolu " , " Kapaklı " , " Malkara " , " Marmara Ereğlisi " , " Muratlı " , " Saray " , " Süleymanpaşa " , " Şarköy " };
    private static String[] tokatİlçeler=new String[] {" Almus " , " Artova " , " Başçiftlik " , " Erbaa " , " Niksar " , " Pazar " , " Reşadiye " , " Sulusaray " , " Tokat " , " Turhal " , " Yeşilyurt " , "Zile " };
    private static String[] trabzonİlçeler=new String[] {" Akçaabat " , " Araklı " , " Arsin " , " Beşikdüzü " , " Çarşıbaşı " , " Çaykara " , " Dernekpazarı " , " Düzköy " , " Hayrat " , " Köprübaşı " , " Maçka " ," Of " , "Ortahisar " , " Sürmene " , " Şalpazarı " , " Tonya " , " Vakfıkebir " , " Yomra " };
    private static String[] tunceliİlçeler=new String[] {" Çemişgezek " , " Hozat " , " Mazgirt " , " Nazımiye " , " Ovacık " , " Pertek " , " Pülümür " , " Tunceli " };
    private static String[] usakİlçeler=new String[] {" Banaz " , " Eşme " , " Karahallı " , " Sivaslı " , " Ulubey " , " Uşak " };
    private static String[] vanİlçeler=new String[] {" Bahçesaray " , " Başkale " , " Çaldıran " , " Çatak " , " Edremit " , " Erciş " , " Gevaş " , " Gürpınar " , " İpekyolu " , " Muradiye " , " Özalp " , "Saray " , "Tuşba " };
    private static String[] yalovaİlçeler=new String[] { " Altınova " , " Armutlu " , " Çınarcık " , " Çiftlikköy " , " Termal " , " Yalova " };
    private static String[] yozgatİlçeler=new String[] { " Akdağmadeni " , " Aydıncık " , " Boğazlıyan " , " Çandır " , " Çayıralan " , " Çekerek " , " Kadışehri " , " Saraykent " , " Sarıkaya " , " Sorgun " , " Şefaatli " ," Yenifakılı " ," Yerköy " , " Yozgat " };
    private static String[] zonguldakİlçeler=new String[] { " Alaplı " , " Çaycuma " , " Devrek " , " Gökçebey " , " Kilimli " , " Kozlu " , " Karadeniz Ereğli " , " Zonguldak " };



}
