package com.example.esra.bitirmeprojesi.Kayıplar;

import android.os.Parcel;
import android.os.Parcelable;

public class KayıpHayvanlar implements Parcelable {

    private String hayvanFoto;
    private String hayvanİsim;
    private String kaybolduğuYer;
    private String kaybolduğuTarih;
    private String hayvanCinsiyet;
    private String hayvanTür;
    private String hayvanYas;
    private String ilanAciklama;
    private String iletisim;
    private String kaybolduğuİlçe;
    private String ilanİD;
    private String ilanSahibiİsimSoyisim;
    private String irk;

    public KayıpHayvanlar() {
    }

    public KayıpHayvanlar(String hayvanFoto, String hayvanİsim, String kaybolduğuYer, String kaybolduğuTarih, String hayvanCinsiyet, String hayvanTür,String irk, String hayvanYas,
                          String ilanAciklama, String iletisim, String kaybolduğuİlçe, String ilanİD,String ilanSahibiİsimSoyisim) {

        this.hayvanFoto = hayvanFoto;
        this.hayvanİsim = hayvanİsim;
        this.kaybolduğuYer = kaybolduğuYer;
        this.kaybolduğuTarih = kaybolduğuTarih;
        this.hayvanCinsiyet = hayvanCinsiyet;
        this.hayvanTür = hayvanTür;
        this.irk=irk;
        this.hayvanYas = hayvanYas;
        this.ilanAciklama = ilanAciklama;
        this.iletisim = iletisim;
        this.kaybolduğuİlçe=kaybolduğuİlçe;
        this.ilanİD=ilanİD;
        this.ilanSahibiİsimSoyisim=ilanSahibiİsimSoyisim;
    }

    protected KayıpHayvanlar(Parcel in) {
        hayvanFoto = in.readString();
        hayvanİsim = in.readString();
        kaybolduğuYer = in.readString();
        kaybolduğuTarih = in.readString();
        hayvanCinsiyet = in.readString();
        hayvanTür = in.readString();
        irk=in.readString();
        hayvanYas = in.readString();
        ilanAciklama = in.readString();
        iletisim = in.readString();
        kaybolduğuİlçe=in.readString();
        ilanİD=in.readString();
        ilanSahibiİsimSoyisim=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hayvanFoto);
        dest.writeString(hayvanİsim);
        dest.writeString(kaybolduğuYer);
        dest.writeString(kaybolduğuTarih);
        dest.writeString(hayvanCinsiyet);
        dest.writeString(hayvanTür);
        dest.writeString(irk);
        dest.writeString(hayvanYas);
        dest.writeString(ilanAciklama);
        dest.writeString(iletisim);
        dest.writeString(kaybolduğuİlçe);
        dest.writeString(ilanİD);
        dest.writeString(ilanSahibiİsimSoyisim);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<KayıpHayvanlar> CREATOR = new Creator<KayıpHayvanlar>() {
        @Override
        public KayıpHayvanlar createFromParcel(Parcel in) {
            return new KayıpHayvanlar(in);
        }

        @Override
        public KayıpHayvanlar[] newArray(int size) {
            return new KayıpHayvanlar[size];
        }
    };

    public String getHayvanFoto() {
        return hayvanFoto;
    }

    public void setHayvanFoto(String hayvanFoto) {
        this.hayvanFoto = hayvanFoto;
    }

    public String getHayvanİsim() {
        return hayvanİsim;
    }

    public void setHayvanİsim(String hayvanİsim) {
        this.hayvanİsim = hayvanİsim;
    }

    public String getKaybolduğuYer() {
        return kaybolduğuYer;
    }

    public void setKaybolduğuYer(String kaybolduğuYer) {
        this.kaybolduğuYer = kaybolduğuYer;
    }

    public String getKaybolduğuTarih() {
        return kaybolduğuTarih;
    }

    public void setKaybolduğuTarih(String kaybolduğuTarih) {
        this.kaybolduğuTarih = kaybolduğuTarih;
    }

    public String getHayvanCinsiyet() {
        return hayvanCinsiyet;
    }

    public void setHayvanCinsiyet(String hayvanCinsiyet) {
        this.hayvanCinsiyet = hayvanCinsiyet;
    }

    public String getHayvanTür() {
        return hayvanTür;
    }

    public void setHayvanTür(String hayvanTür) {
        this.hayvanTür = hayvanTür;
    }

    public String getHayvanYas() {
        return hayvanYas;
    }

    public void setHayvanYas(String hayvanYas) {
        this.hayvanYas = hayvanYas;
    }

    public String getIlanAciklama() {
        return ilanAciklama;
    }

    public void setIlanAciklama(String ilanAciklama) {
        this.ilanAciklama = ilanAciklama;
    }

    public String getIletisim() {
        return iletisim;
    }

    public void setIletisim(String iletisim) {
        this.iletisim = iletisim;
    }

    public String getKaybolduğuİlçe() {
        return kaybolduğuİlçe;
    }

    public void setKaybolduğuİlçe(String kaybolduğuİlçe) {
        this.kaybolduğuİlçe = kaybolduğuİlçe;
    }

    public String getIlanİD() {
        return ilanİD;
    }

    public void setIlanİD(String ilanİD) {
        this.ilanİD = ilanİD;
    }

    public String getIlanSahibiİsimSoyisim() {
        return ilanSahibiİsimSoyisim;
    }

    public void setIlanSahibiİsimSoyisim(String ilanSahibiİsimSoyisim) {
        this.ilanSahibiİsimSoyisim = ilanSahibiİsimSoyisim;
    }

    public String getIrk() {
        return irk;
    }

    public void setIrk(String irk) {
        this.irk = irk;
    }
}
