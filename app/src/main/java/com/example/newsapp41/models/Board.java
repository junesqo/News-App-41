package com.example.newsapp41.models;

import com.airbnb.lottie.LottieAnimationView;

public class Board {

    private String title;
    private String desc;
    private String lottie;


    public Board(String title, String desc, String lottie) {
        this.title = title;
        this.desc = desc;
        this.lottie = lottie;
    }

    public String getLottie() {
        return lottie;
    }

    public void setLottie(String lottie) {
        this.lottie = lottie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
