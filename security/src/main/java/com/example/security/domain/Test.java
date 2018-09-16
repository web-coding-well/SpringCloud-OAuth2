package com.example.security.domain;

import java.io.Serializable;

/**
 * Created by hwj on 2018/9/16.
 */
public class Test implements Serializable{

    public Test(String s1, double d1) {
        this.s1 = s1;
        this.d1 = d1;
    }

    private String s1;
    private double d1;

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public double getD1() {
        return d1;
    }

    public void setD1(double d1) {
        this.d1 = d1;
    }
}
