package com.githab.grinevskayaab.entity;

public class Song {
    private Long id;
    private Integer year = null;
    private final String name;
    private Album album = null;


    public Song(Long id, String name, Integer year, Album album) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.album = album;
    }

    public Song(Long id, String name, Integer year) {
        this.name = name;
        this.year = year;
        this.id = id;
    }

    public Song(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Album getAlbum() {
        return album;
    }

    public Long getId() {
        return id;
    }

    public Integer getYear() {
        return year;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Song " +
                "id=" + id +
                ", year=" + year +
                ", name='" + name + '\'' +
                ", album=" + (album == null ? null : album.getId()) +
                '\n';
    }
}
