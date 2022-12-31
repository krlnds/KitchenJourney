package com.karlina.kitchenjourney.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tersimpan {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("bahan")
    @Expose
    private String bahan;
    @SerializedName("cara")
    @Expose
    private String cara;

    public Tersimpan(String title, String bahan, String cara, String image) {
        this.title = title;
        this.bahan = bahan;
        this.cara = cara;
        this.image = image;
    }

    @SerializedName("image")
    @Expose
    private String image;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBahan() {
        return bahan;
    }

    public void setBahan(String bahan) {
        this.bahan = bahan;
    }

    public String getCara() {
        return cara;
    }

    public void setCara(String cara) {
        this.cara = cara;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
