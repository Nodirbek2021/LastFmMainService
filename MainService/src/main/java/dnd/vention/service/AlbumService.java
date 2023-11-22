package dnd.vention.service;

import dnd.vention.db.DbConnection;
import dnd.vention.exception.MainServiceException;
import dnd.vention.payload.Album;
import dnd.vention.payload.ApiResponse;
import dnd.vention.payload.Artist;
import dnd.vention.payload.Track;
import dnd.vention.payload.payloadForUrl.AlbumDto;
import dnd.vention.payload.payloadForUrl.AlbumDtoAddTracks;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dnd.vention.service.TrackService.getAlbumTracksById;
import static dnd.vention.service.TrackService.getTracksForPlaylist;

public class AlbumService {
    public static ApiResponse<Album> getAlbumById(String id) throws SQLException, ClassNotFoundException {
        int albumId = Integer.parseInt(id);
        Connection connection = DbConnection.getConnection();
        Statement connectionStatement = connection.createStatement();
        String sqlGetConnectionById = "select * from Album where id = " + albumId + ";";
        ResultSet resultSet = connectionStatement.executeQuery(sqlGetConnectionById);
        if (!resultSet.isBeforeFirst()) {
            MainServiceException mainServiceException = new MainServiceException("Album not Found");
            return new ApiResponse<>(false, mainServiceException.getMessage());
        }
        Album album = new Album();
        while (resultSet.next()) {
            album.setId(resultSet.getInt("id"));
            album.setName(resultSet.getString("name"));
            album.setListeners(resultSet.getInt("listeners"));
            album.setPlaycount(resultSet.getInt("playcount"));
            int artist_id = resultSet.getInt("artist_id");
            ApiResponse<Artist> artistById = ArtistService.getArtistById(artist_id);
            if (!artistById.isSucces()) {
                MainServiceException mainServiceException = new MainServiceException("Artist not found!");
                return new ApiResponse<>(false, mainServiceException.getMessage());
            }
            album.setArtist(artistById.getData());
            album.setBlocked(resultSet.getBoolean("isBlocked"));
            int i = Integer.parseInt(id);
            ApiResponse<Track> albumTracksById = getAlbumTracksById(i);
            Track data = albumTracksById.getData();
            if (!albumTracksById.isSucces()) data = new Track();
            ApiResponse<List<Track>> allAlbumTracks = getAllAlbumTracks(data.getId());
            if (!allAlbumTracks.isSucces()) {
                MainServiceException mainServiceException = new MainServiceException("Not album tracks found");
            }
            album.setTrackList(allAlbumTracks.getData());
        }
        return new ApiResponse<>(true, "Here it is!", album);
    }

    public static ApiResponse<List<Track>> getAllAlbumTracks(Integer album_id) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from AlbumTracks where album_id=" + album_id + ";");
        List<Track> tracksOfAlbum = new ArrayList<>();
        while (resultSet.next()) {
            int trackId = resultSet.getInt("track_id");
            ApiResponse<Track> albumTracksById = getAlbumTracksById(trackId);
            if (!albumTracksById.isSucces()) {
                MainServiceException mainServiceException = new MainServiceException("tack not found for this id: " + trackId + "");
                return new ApiResponse<>(false, mainServiceException.getMessage());
            }
            tracksOfAlbum.add(albumTracksById.getData());
        }
        connection.close();
        return new ApiResponse<List<Track>>(true, "here it is!", tracksOfAlbum);
    }

    public static ApiResponse<Album> createAlbum(AlbumDto valuesFromRequest) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        String sqlCreateAlbum = "insert into Album (name,artist_id) values(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlCreateAlbum);
        preparedStatement.setString(1, valuesFromRequest.getName());
        preparedStatement.setInt(2, valuesFromRequest.getArtist_id());
        preparedStatement.execute();
        Statement connectionStatement = connection.createStatement();
        ResultSet resultSet = connectionStatement.executeQuery("select * from Album where name='" + valuesFromRequest.getName() + "'");
        Album album = new Album();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            album.setId(resultSet.getInt("id"));
            album.setName(resultSet.getString("name"));
            album.setListeners(resultSet.getInt("listeners"));
            album.setPlaycount(resultSet.getInt("playcount"));
            int artist_id = resultSet.getInt("artist_id");
            ApiResponse<Artist> artistById = ArtistService.getArtistById(artist_id);
            if (!artistById.isSucces()) {
                MainServiceException mainServiceException = new MainServiceException("Artist not found!");
                return new ApiResponse<>(false, mainServiceException.getMessage());
            }
            album.setArtist(artistById.getData());
            album.setBlocked(resultSet.getBoolean("isBlocked"));
            ApiResponse<Track> albumTracksById = getAlbumTracksById(id);
            Track data = albumTracksById.getData();
            if (!albumTracksById.isSucces()) data = new Track();
            ApiResponse<List<Track>> allAlbumTracks = getAllAlbumTracks(data.getId());
            if (!allAlbumTracks.isSucces()) {
                MainServiceException mainServiceException = new MainServiceException("Not album tracks found");
            }
            album.setTrackList(allAlbumTracks.getData());
        }
        return new ApiResponse<Album>(true, "Here it is!", album);
    }

    public static ApiResponse<Album> updateAlbum(String id, AlbumDto valuesFromRequest) throws SQLException, ClassNotFoundException {
        ApiResponse<Album> albumById = getAlbumById(id);
        if (!albumById.isSucces()) {
            MainServiceException mainServiceException = new MainServiceException("Album not found.");
            return new ApiResponse<>(false, mainServiceException.getMessage());
        }
        if (albumById.getData().getArtist().getId() != valuesFromRequest.getArtist_id()) {
            return new ApiResponse<>(false, "You cant change Artist of this Album!");
        }
        Connection connection = DbConnection.getConnection();
        Statement connectionStatement = connection.createStatement();

        String sqlUpdatePlaylist = "UPDATE Album " +
                "SET name = '" + valuesFromRequest.getName() + "'" +
                "WHERE id=" + Integer.parseInt(id) + " ;";
        connectionStatement.execute(sqlUpdatePlaylist);
        connection.close();
        ApiResponse<Album> albumById1 = getAlbumById(id);
        return albumById1;
    }

    public static ApiResponse<?> deleteAlbum(String id) throws SQLException, ClassNotFoundException {
        Integer albumId = Integer.parseInt(id);
        ApiResponse<Album> albumById = getAlbumById(id);
        if (!albumById.isSucces()) {
            MainServiceException mainServiceException = new MainServiceException("Album Not found");
            return new ApiResponse<>(false, mainServiceException.getMessage());
        }
        Connection connection = DbConnection.getConnection();
        Statement connectionStatement = connection.createStatement();
        String sqlDeleteAlbumQuery = "delete from Album where id = " + id + ";";
        connectionStatement.execute(sqlDeleteAlbumQuery);
        connection.close();
        return new ApiResponse<>(true, "Deleted!");
    }

    public static ApiResponse<Album> addTracksToAlbum(AlbumDtoAddTracks valuesFromRequest) throws SQLException, ClassNotFoundException {
        ApiResponse<Album> albumById = getAlbumById(valuesFromRequest.getAlbumId().toString());
        if (!albumById.isSucces()) {
            MainServiceException mainServiceException = new MainServiceException("Album Not found");
            return new ApiResponse<>(false, mainServiceException.getMessage());
        }
        List<Integer> listTracks = valuesFromRequest.getListTracks();
        Connection connection = DbConnection.getConnection();
        Statement connectionStatement = connection.createStatement();
        for (int i = 0; i < listTracks.size(); i++) {
            if (listTracks.size()>10){
                return new ApiResponse<>(false, "You only can add 10 track for this Album");
            }
            ResultSet resultSet = connectionStatement.executeQuery("select * from TrackObject where id=" + i + ";");
            if (!resultSet.isBeforeFirst()) continue;
            ApiResponse<Track> trackById = TrackService.getTrackById(i);
            if (!trackById.isSucces()) continue;
            if (trackById.getData().getArtist().getId() != albumById.getData().getArtist().getId()) continue;
            String sqlAddTracksToAlbumQuery = "insert into AlbumTracks(album_id,track_id) " +
                    "values (" +
                    "" + valuesFromRequest.getAlbumId() + "," + i + ");";
            connectionStatement.execute(sqlAddTracksToAlbumQuery);
        }
        connection.close();
        ApiResponse<Album> albumById1 = getAlbumById(valuesFromRequest.getAlbumId().toString());
        return new ApiResponse<Album>(true, "Here it is!", albumById1.getData());
    }

    public static ApiResponse<?> deleteTrackFromAlbum(Integer albumId, Integer trackId) throws SQLException, ClassNotFoundException {
        ApiResponse<Album> albumById = getAlbumById(albumId.toString());
        if (!albumById.isSucces()){
            MainServiceException mainServiceException = new MainServiceException("Track not found!");
            return new ApiResponse<>(false, "Track not Found");
        }
        ApiResponse<Track> trackById = TrackService.getTrackById(trackId);
        if (!trackById.isSucces()) {
            MainServiceException mainServiceException = new MainServiceException("Track not found!");
            return new ApiResponse<>(false, "Track not Found");
        }
        Connection connection = DbConnection.getConnection();
        Statement connectionStatement = connection.createStatement();
        connectionStatement.execute("delete  from AlbumTracks where  album_id ="+albumId+" and track_id = "+trackId+" ;");
        connection.close();
        return new ApiResponse<>(true,"deleted");
    }
}
