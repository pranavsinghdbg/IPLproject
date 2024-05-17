package com.entity;

public class Bowler {

    private String bowler;
    private int run;
    private double avgRun;

    public String getBowler() {
        return bowler;
    }

    public void setBowler(String bowler) {
        this.bowler = bowler;
    }

    public int getRun() {
        return run;
    }

    public void setRun(int run) {
        this.run = run;
    }

    public double getAvgRun() {
        return avgRun;
    }

    public void setAvgRun(double avgRun) {
        this.avgRun = avgRun;
    }

    public Bowler(String bowler, int run, double avgRun){
        this.bowler = bowler;
        this.run = run;
        this.avgRun = avgRun;
    }
}
