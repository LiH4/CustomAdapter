package com.example.lisa.customadapter;

public class Feed {
    private String name;
    private int age;
    private String job;
    private String search;
    private String location;
    private int qm;
    private int pricing;
    private String others;
    private String picture1;


    public Feed (String name, int age, String job, String search, String location, int qm,
          int pricing, String others ,String picture1) {
        this.name = name;
        this.age = age;
        this.job = job;
        this.search = search;
        this.location = location;
        this.qm = qm;
        this.pricing = pricing;
        this.others = others;
        this.picture1 = picture1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getQm() {
        return qm;
    }

    public void setQm(int qm) {
        this.qm = qm;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getPicture1() {
        return picture1;
    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public int getPricing() {
        return pricing;
    }

    public void setPricing(int pricing) {
        this.pricing = pricing;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
