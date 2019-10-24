package com.example.esra.bitirmeprojesi.SoruCevap;

import android.os.Parcel;
import android.os.Parcelable;

public class SoruCevapClass implements Parcelable {
    private String soru;
    private String sorubaslık;
    private String pp;
    private String isimSoyisim;
    private String tarih;
    private String konu;
    private String soruİD;

    public SoruCevapClass() {
    }

    public SoruCevapClass(String soruİD,String sorubaslık,String konu,String soru, String pp, String isimSoyisim, String tarih ) {
       this.soruİD=soruİD;
        this.soru = soru;
        this.pp = pp;
        this.isimSoyisim = isimSoyisim;
        this.tarih = tarih;
        this.konu = konu;
        this.sorubaslık=sorubaslık;
    }

    public String getSoruİD() {
        return soruİD;
    }

    public void setSoruİD(String soruİD) {
        this.soruİD = soruİD;
    }

    public String getSorubaslık() {
        return sorubaslık;
    }

    public void setSorubaslık(String sorubaslık) {
        this.sorubaslık = sorubaslık;
    }

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
    }

    public String getPp() {
        return pp;
    }

    public void setPp(String pp) {
        this.pp = pp;
    }

    public String getIsimSoyisim() {
        return isimSoyisim;
    }

    public void setIsimSoyisim(String isimSoyisim) {
        this.isimSoyisim = isimSoyisim;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getKonu() {
        return konu;
    }

    public void setKonu(String konu) {
        this.konu = konu;
    }

    protected SoruCevapClass(Parcel in) {
        soru=in.readString();
        konu=in.readString();
        tarih=in.readString();
        pp=in.readString();
        isimSoyisim=in.readString();
        sorubaslık=in.readString();
        soruİD=in.readString();
    }

    public static final Creator<SoruCevapClass> CREATOR = new Creator<SoruCevapClass>() {
        @Override
        public SoruCevapClass createFromParcel(Parcel in) {
            return new SoruCevapClass(in);
        }

        @Override
        public SoruCevapClass[] newArray(int size) {
            return new SoruCevapClass[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(soru);
        dest.writeString(tarih);
        dest.writeString(konu);
        dest.writeString(pp);
        dest.writeString(sorubaslık);
        dest.writeString(isimSoyisim);
        dest.writeString(soruİD);
    }
}
