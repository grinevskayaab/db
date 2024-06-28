package com.githab.grinevskayaab;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongRepository {
    Connection connection;


    public SongRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Song> findAll() {
        String request = "select * from songs";
        try (PreparedStatement statement = connection.prepareStatement(request)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Song> result = new ArrayList<>();
                while (resultSet.next()) {
                    Song song = new Song(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getObject("album_id") == null ? null : resultSet.getLong("album_id"),
                            resultSet.getObject("year") == null ? null : resultSet.getInt("year")
                    );

                    result.add(song);
                }
                return result;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Song findById(Long id) throws SQLException {
        String request = "select * from songs where id=?";
        PreparedStatement statement = connection.prepareStatement(request);
        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return new Song(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getObject("album_id") == null ? null : resultSet.getLong("album_id"),
                resultSet.getObject("year") == null ? null : resultSet.getInt("year")
        );

    }

    public Song createSong(Song song) throws SQLException {
        String request = "insert into songs (name,year,album_id) values (?,?,?)";

        PreparedStatement statement = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, song.getName());

        if (song.getYear() == null) {
            statement.setNull(2, Types.INTEGER);
        } else {
            statement.setInt(2, song.getYear());
        }

        if (song.getAlbumId() == null) {
            statement.setNull(3, Types.INTEGER);
        } else {
            statement.setLong(3, song.getAlbumId());
        }


        int result = statement.executeUpdate();
        if (result == 0) {
            throw new SQLException("Ошибка при создании песни");
        }

        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        song.setId(resultSet.getLong(1));

        return song;
    }

    public void updateSong(Song song) throws SQLException {
        String request = "Update songs set name = ?, album_id = ?, year = ? where id = ?";

        PreparedStatement statement = connection.prepareStatement(request);
        statement.setString(1, song.getName());

        if (song.getAlbumId() == null) {
            statement.setNull(2, Types.INTEGER);
        } else {
            statement.setLong(2, song.getAlbumId());
        }

        if (song.getYear() == null) {
            statement.setNull(3, Types.INTEGER);
        } else {
            statement.setInt(3, song.getYear());
        }

        statement.setLong(4, song.getId());

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


    public void deleteSong(Long song_id) throws SQLException {
        String request = "delete from songs where id = ?";
        PreparedStatement statement = connection.prepareStatement(request);
        statement.setLong(1, song_id);

        if (statement.executeUpdate() > 0)
            System.out.println("Удалена запись " + song_id);
        else
            System.out.println("Не удалена запись" + song_id);
    }

    public List<Song> getTopSongs() throws SQLException {
        String request = """
                select s.name, sum(stat.count_plays) as sum, s.id, s.album_id, s.year
                from songs_stat stat
                         join songs s on stat.song_id = s.id
                where s.album_id is null
                  and stat.date > date_trunc('month', NOW()) - '6 month'::INTERVAL
                group by s.id
                order by sum desc
                limit 3;""";

        PreparedStatement statement = connection.prepareStatement(request);
        ResultSet resultSet = statement.executeQuery();
        List<Song> result = new ArrayList<>();
        while (resultSet.next()) {
            Song song = new Song(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getObject("album_id") == null ? null : resultSet.getLong("album_id"),
                    resultSet.getObject("year") == null ? null : resultSet.getInt("year")
            );

            result.add(song);
        }
        return result;

    }
}
