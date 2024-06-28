package com.githab.grinevskayaab;

public class Song {
    private Long id;
    private Integer year = null;
    private Long albumId = null;
    private final String name;


    public Song(Long id, String name, Long album_id, Integer year) {
        this.id = id;
        this.name = name;
        this.albumId = album_id;
        this.year = year;
    }

    public Song(String name, Long album_id, Integer year) {
        this.name = name;
        this.albumId = album_id;
        this.year = year;
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

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }


    public Long getId() {
        return id;
    }

    public Integer getYear() {
        return year;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", year=" + year +
                ", album_id=" + albumId +
                ", name='" + name + "'\n";
    }
}
