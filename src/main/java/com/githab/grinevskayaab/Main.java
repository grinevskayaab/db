package com.githab.grinevskayaab;

import com.githab.grinevskayaab.entity.Album;
import com.githab.grinevskayaab.entity.Song;
import com.githab.grinevskayaab.repository.AlbumRepository;
import com.githab.grinevskayaab.repository.SongRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseConfig app = new DatabaseConfig();
        Connection connection = app.connectDatabase();
//
//        SongRepository songRepository = new SongRepository(connection);
//        System.out.println(songRepository.findAll());
//        System.out.println(songRepository.findById(2L));
//        System.out.println(songRepository.findById(200L));



//
//        Song song = new Song("Пути");
//        System.out.println(song);
//        System.out.println(songRepository.createSong(song));
//        System.out.println(songRepository.findAll());
//
//
//        Song song1 = songRepository.findById(17L);
//        System.out.println(song1);
//        song1.setYear(2000);
//        System.out.println(song1);
//        songRepository.updateSong(song1);
//        System.out.println(songRepository.findById(17L));
//
//
//        songRepository.deleteSong(17L);
//
//        System.out.println(songRepository.getTopSongs());


//        AlbumRepository albumRepository = new AlbumRepository(connection);
//        System.out.println(albumRepository.findAll());
//        System.out.println(albumRepository.findById(1L));
//        System.out.println(albumRepository.findById(200L));
//
//        Album album = new Album("Пути");
////        System.out.println(album);
//        System.out.println(albumRepository.createAlbum(album));
//        System.out.println(albumRepository.findAll());
//
//
//        Album album1 = albumRepository.findById(5L);
//        System.out.println(album1);
//        album1.setYear(2000);
//        System.out.println(album1);
//        albumRepository.updateAlbum(album1);
//        System.out.println(albumRepository.findById(5L));
//
//        albumRepository.deleteAlbum(5L);

    }
}