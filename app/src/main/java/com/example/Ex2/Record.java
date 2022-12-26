package com.example.Ex2;

import androidx.annotation.NonNull;

public class Record implements Comparable<Record> {

    private String name = "";
    private int score = 0;
    private double lat = 0.0;
    private double lon = 0.0;

    public Record() {
    }

    public String getName() {
        return name;
    }

    public Record setName(String name) {
        this.name = name != null ? name : "";
        return this;
    }

    public int getScore() {
        return score;
    }

    public Record setScore(int score) {
        this.score = score;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Record setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Record setLon(double lon) {
        this.lon = lon;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "Record{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }

    @Override
    public int compareTo(Record o) {
        return o.score - score;
    }
}
