package com.example.lisa.customadapter;

public class Feed {
    private String name;
    private String age;
    private String job;
    private String search;
    private String location;
    private String qm;
    private String pricing;
    private String others;
//    private String picure1;


    public Feed (String name, String age, String job, String search, String location, String qm,
          String pricing, String others /*,String picure1*/) {
        this.name = name;
        this.age = age;
        this.job = job;
        this.search = search;
        this.location = location;
        this.qm = qm;
        this.pricing = pricing;
        this.others = others;
        //this.picure1 = picure1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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

    public String getQm() {
        return qm;
    }

    public void setQm(String qm) {
        this.qm = qm;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

//    public String getPicure1() {
//        return picure1;
//    }
//
//    public void setPicure1(String picure1) {
//        this.picure1 = picure1;
//    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
