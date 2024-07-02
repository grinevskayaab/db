package com.githab.grinevskayaab.repository;

import com.githab.grinevskayaab.entity.Album;
import com.githab.grinevskayaab.entity.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumRepository {
    Connection connection;


    public AlbumRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Album> findAll() {
        String request = "select * from albums";
        try (PreparedStatement statement = connection.prepareStatement(request)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Album> result = new ArrayList<>();
                while (resultSet.next()) {
                   Album album = createObjAlbum(resultSet);

                    result.add(album);
                }
                return result;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Album findById(Long id) throws SQLException {
        String request = "select * from albums where id=?";
        PreparedStatement statement = connection.prepareStatement(request);
        statement.setLong(1, id);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return createObjAlbum(resultSet);
            } else {
                System.out.println("Альбома с такой песне нет в БД");
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Album createObjAlbum(ResultSet resultSet) throws SQLException {
        Album album = new Album(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getObject("year") == null ? null : resultSet.getInt("year")
        );

        List<Song> songs = findSongByAlbumId(album);
        album.setSongs(songs);
        return album;
    }

    private List<Song> findSongByAlbumId(Album album) throws SQLException {
        String request = "select * from songs where album_id=?";
        PreparedStatement statement = connection.prepareStatement(request);
        statement.setLong(1, album.getId());

        try (ResultSet resultSet = statement.executeQuery()) {
            List<Song> result = new ArrayList<>();
            while (resultSet.next()) {
                Song song = new Song(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getObject("year") == null ? null : resultSet.getInt("year"),
                        album
                );

                result.add(song);
            }
            return result;
        }
    }

    public Album createAlbum(Album album) throws SQLException {
        String request = "insert into albums (name,year) values (?,?)";

        PreparedStatement statement = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, album.getName());

        if (album.getYear() == null) {
            statement.setNull(2, Types.INTEGER);
        } else {
            statement.setInt(2, album.getYear());
        }


        int result = statement.executeUpdate();
        if (result == 0) {
            throw new SQLException("Ошибка при создании альбома");
        }

        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        album.setId(resultSet.getLong(1));

        return album;
    }


    public void updateAlbum(Album album) throws SQLException {
        String request = "Update albums set name = ?, year = ? where id = ?";

        PreparedStatement statement = connection.prepareStatement(request);
        statement.setString(1, album.getName());

        if (album.getYear() == null) {
            statement.setNull(2, Types.INTEGER);
        } else {
            statement.setInt(2, album.getYear());
        }

        statement.setLong(3, album.getId());

        boolean autoCommit = connection.getAutoCommit();
        try {
            connection.setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }


    public void deleteAlbum(Long album_id) throws SQLException {
        String request = "delete from albums where id = ?";
        PreparedStatement statement = connection.prepareStatement(request);
        statement.setLong(1, album_id);

        if (statement.executeUpdate() > 0)
            System.out.println("Удалена запись " + album_id);
        else
            System.out.println("Не удалена запись" + album_id);
    }

}
