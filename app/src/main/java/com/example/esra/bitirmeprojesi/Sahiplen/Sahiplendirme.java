package com.example.esra.bitirmeprojesi.Sahiplen;

import android.os.Parcel;
import android.os.Parcelable;

public class Sahiplendirme implements Parcelable {

    private String sİlanİD;
    private String sahiplendirmeİsim;
    private String sahiplendirmeTür;
    private String sahiplendirmeCinsiyet;
    private String sahiplendirmeYas;
    private String sahiplendirmeSehir;
    private String sahiplendirmeİlçe;
    private String sahiplendirmeTarih;
    private String sahiplendirmeIletisim;
    private String sahiplendirmeAciklama;
    private String sahiplendirmeFoto;
    private String sahiplendirmeİlanSahibiİsimSoyisim;
    private String irk;

    public Sahiplendirme() {
    }

    public Sahiplendirme(String sİlanİD, String sahiplendirmeİsim,String sahiplendirmeİlanSahibiİsimSoyisim, String sahiplendirmeTür,String irk, String sahiplendirmeCinsiyet,
                         String sahiplendirmeYas, String sahiplendirmeSehir, String sahiplendirmeİlçe, String sahiplendirmeTarih, String sahiplendirmeIletisim,
                         String sahiplendirmeAciklama, String sahiplendirmeFoto) {
        this.sİlanİD = sİlanİD;
        this.sahiplendirmeİsim = sahiplendirmeİsim;
        this.sahiplendirmeTür = sahiplendirmeTür;
        this.irk=irk;
        this.sahiplendirmeCinsiyet = sahiplendirmeCinsiyet;
        this.sahiplendirmeYas = sahiplendirmeYas;
        this.sahiplendirmeSehir = sahiplendirmeSehir;
        this.sahiplendirmeİlçe = sahiplendirmeİlçe;
        this.sahiplendirmeTarih = sahiplendirmeTarih;
        this.sahiplendirmeIletisim = sahiplendirmeIletisim;
        this.sahiplendirmeAciklama = sahiplendirmeAciklama;
        this.sahiplendirmeFoto = sahiplendirmeFoto;
        this.sahiplendirmeİlanSahibiİsimSoyisim=sahiplendirmeİlanSahibiİsimSoyisim;
    }

    public String getIrk() {
        return irk;
    }

    public void setIrk(String irk) {
        this.irk = irk;
    }

    public String getSahiplendirmeİlanSahibiİsimSoyisim() {
        return sahiplendirmeİlanSahibiİsimSoyisim;
    }

    public void setSahiplendirmeİlanSahibiİsimSoyisim(String sahiplendirmeİlanSahibiİsimSoyisim) {
        this.sahiplendirmeİlanSahibiİsimSoyisim = sahiplendirmeİlanSahibiİsimSoyisim;
    }

    public String getsİlanİD() {
        return sİlanİD;
    }

    public void setsİlanİD(String sİlanİD) {
        this.sİlanİD = sİlanİD;
    }

    public String getSahiplendirmeİsim() {
        return sahiplendirmeİsim;
    }

    public void setSahiplendirmeİsim(String sahiplendirmeİsim) {
        this.sahiplendirmeİsim = sahiplendirmeİsim;
    }

    public String getSahiplendirmeTür() {
        return sahiplendirmeTür;
    }

    public void setSahiplendirmeTür(String sahiplendirmeTür) {
        this.sahiplendirmeTür = sahiplendirmeTür;
    }

    public String getSahiplendirmeCinsiyet() {
        return sahiplendirmeCinsiyet;
    }

    public void setSahiplendirmeCinsiyet(String sahiplendirmeCinsiyet) {
        this.sahiplendirmeCinsiyet = sahiplendirmeCinsiyet;
    }

    public String getSahiplendirmeYas() {
        return sahiplendirmeYas;
    }

    public void setSahiplendirmeYas(String sahiplendirmeYas) {
        this.sahiplendirmeYas = sahiplendirmeYas;
    }

    public String getSahiplendirmeSehir() {
        return sahiplendirmeSehir;
    }

    public void setSahiplendirmeSehir(String sahiplendirmeSehir) {
        this.sahiplendirmeSehir = sahiplendirmeSehir;
    }

    public String getSahiplendirmeİlçe() {
        return sahiplendirmeİlçe;
    }

    public void setSahiplendirmeİlçe(String sahiplendirmeİlçe) {
        this.sahiplendirmeİlçe = sahiplendirmeİlçe;
    }

    public String getSahiplendirmeTarih() {
        return sahiplendirmeTarih;
    }

    public void setSahiplendirmeTarih(String sahiplendirmeTarih) {
        this.sahiplendirmeTarih = sahiplendirmeTarih;
    }

    public String getSahiplendirmeIletisim() {
        return sahiplendirmeIletisim;
    }

    public void setSahiplendirmeIletisim(String sahiplendirmeIletisim) {
        this.sahiplendirmeIletisim = sahiplendirmeIletisim;
    }

    public String getSahiplendirmeAciklama() {
        return sahiplendirmeAciklama;
    }

    public void setSahiplendirmeAciklama(String sahiplendirmeAciklama) {
        this.sahiplendirmeAciklama = sahiplendirmeAciklama;
    }

    public String getSahiplendirmeFoto() {
        return sahiplendirmeFoto;
    }

    public void setSahiplendirmeFoto(String sahiplendirmeFoto) {
        this.sahiplendirmeFoto = sahiplendirmeFoto;
    }

    protected Sahiplendirme(Parcel in) {
        sahiplendirmeFoto = in.readString();
        sahiplendirmeİsim = in.readString();
        sahiplendirmeSehir = in.readString();
        sahiplendirmeCinsiyet = in.readString();
        sahiplendirmeTür = in.readString();
        irk=in.readString();
        sahiplendirmeYas = in.readString();
        sahiplendirmeAciklama = in.readString();
        sahiplendirmeIletisim = in.readString();
        sahiplendirmeİlçe=in.readString();
        sahiplendirmeTarih=in.readString();
        sİlanİD=in.readString();
        sahiplendirmeİlanSahibiİsimSoyisim=in.readString();
    }

    public static final Creator<Sahiplendirme> CREATOR = new Creator<Sahiplendirme>() {
        @Override
        public Sahiplendirme createFromParcel(Parcel in) {
            return new Sahiplendirme(in);
        }

        @Override
        public Sahiplendirme[] newArray(int size) {
            return new Sahiplendirme[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sahiplendirmeFoto);
        dest.writeString(sahiplendirmeİsim);
        dest.writeString(sahiplendirmeSehir);
        dest.writeString(sahiplendirmeCinsiyet);
        dest.writeString(sahiplendirmeTür);
        dest.writeString(irk);
        dest.writeString(sahiplendirmeYas);
        dest.writeString(sahiplendirmeAciklama);
        dest.writeString(sahiplendirmeIletisim);
        dest.writeString(sahiplendirmeİlçe);
        dest.writeString(sahiplendirmeTarih);
        dest.writeString(sİlanİD);
        dest.writeString(sahiplendirmeİlanSahibiİsimSoyisim);
    }
}
