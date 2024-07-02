package com.githab.grinevskayaab.entity;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private Long id;
    private String name;
    private Integer year = null;
    private List<Song> songs = null;

    public Album(Long id, String name, Integer year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public Album(Long id, String name, Integer year, List<Song> songs) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.songs = songs;
    }

    public Album(Long id, List<Song> songs) {
        this.id = id;
        this.songs = songs;
    }

    public Album(Long id) {
        this.id = id;
    }

    public Album(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "Album" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", songs=\n" + songs + '\n';
    }
}
