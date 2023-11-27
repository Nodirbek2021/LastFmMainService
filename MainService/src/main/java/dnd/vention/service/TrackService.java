package dnd.vention.service;

import dnd.vention.db.DbConnection;
import dnd.vention.exception.MainServiceException;
import dnd.vention.payload.ApiResponse;
import dnd.vention.payload.Artist;
import dnd.vention.payload.Track;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static dnd.vention.service.ArtistService.getArtistById;
import static dnd.vention.service.RankService.getRankById;

public class TrackService {
    public static Track getTracksForPlaylist(int trackId) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSetTrack = statement.executeQuery("select  * from TrackObject where id=" + trackId + ";");
        if (!resultSetTrack.isBeforeFirst()) return new Track();
        Track track = new Track();
        while (resultSetTrack.next()) {
            track.setId(resultSetTrack.getInt("id"));
            track.setName(resultSetTrack.getString("name"));
            track.setBlocked(resultSetTrack.getBoolean("is_blocked"));
            track.setPlaycount(resultSetTrack.getInt("playcount"));
            track.setListeners(resultSetTrack.getInt("listeners"));
            track.setMbid(resultSetTrack.getString("mbid"));
            track.setRank(getRankById(resultSetTrack.getInt("rank_id")));
            int artist_object_id = resultSetTrack.getInt("artist_object_id");
//            ApiResponse<Artist> artistById = getArtistById(artist_object_id);
//            if (!artistById.isSucces()) {
//                MainServiceException mainServiceException = new MainServiceException("Artist not found of this track"+trackId+"!");
//                return new ApiResponse<>(false, mainServiceException.getMessage());
//            }
//            track.setArtist(artistById.getData());
            track.setDuration(resultSetTrack.getInt("duration"));
            track.setUrl(resultSetTrack.getString("url"));
        }
        connection.close();
        return track;

    }

    public static ApiResponse<Track> getAlbumTracksById(Integer id) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSetTrack = statement.executeQuery("select  * from TrackObject where id=" + id + ";");
        if (!resultSetTrack.isBeforeFirst()) {
            MainServiceException mainServiceException = new MainServiceException("trackNotFound: " + id);
            return new ApiResponse<>(false, mainServiceException.getMessage());
        }
        Track track = new Track();
        while (resultSetTrack.next()) {
            track.setId(resultSetTrack.getInt("id"));
            track.setName(resultSetTrack.getString("name"));
            track.setBlocked(resultSetTrack.getBoolean("is_blocked"));
            track.setPlaycount(resultSetTrack.getInt("playcount"));
            track.setListeners(resultSetTrack.getInt("listeners"));
            track.setMbid(resultSetTrack.getString("mbid"));
            track.setRank(getRankById(resultSetTrack.getInt("rank_id")));
            int artist_object_id = resultSetTrack.getInt("artist_object_id");
            ApiResponse<Artist> artistById = getArtistById(artist_object_id);
            if (!artistById.isSucces()) {
                MainServiceException mainServiceException = new MainServiceException("Artist not found of this track" + id + "!");
                return new ApiResponse<>(false, mainServiceException.getMessage());
            }
            track.setArtist(artistById.getData());
            track.setDuration(resultSetTrack.getInt("duration"));
            track.setUrl(resultSetTrack.getString("url"));
        }
        connection.close();
        return new ApiResponse<>(true, "Here it is!", track);
    }


    public static ApiResponse<Track> getTrackById(Integer id) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement connectionStatement = connection.createStatement();
        ResultSet resultSetTrack = connectionStatement.executeQuery("select * from TrackObject where id =" + id + ";");
        if (!resultSetTrack.isBeforeFirst()) {
            MainServiceException mainServiceException = new MainServiceException("Track not found");
            return new ApiResponse<>(false, "Track not found in this id" + id);
        }
        Track track = new Track();
        while (resultSetTrack.next()) {
            track.setId(resultSetTrack.getInt("id"));
            track.setName(resultSetTrack.getString("name"));
            track.setBlocked(resultSetTrack.getBoolean("is_blocked"));
            track.setPlaycount(resultSetTrack.getInt("playcount"));
            track.setListeners(resultSetTrack.getInt("listeners"));
            track.setMbid(resultSetTrack.getString("mbid"));
            track.setRank(getRankById(resultSetTrack.getInt("rank_id")));
            int artist_object_id = resultSetTrack.getInt("artist_object_id");
            ApiResponse<Artist> artistById = getArtistById(artist_object_id);
            if (!artistById.isSucces()) {
                MainServiceException mainServiceException = new MainServiceException("Artist not found of this track" + id + "!");
                return new ApiResponse<>(false, mainServiceException.getMessage());
            }
            track.setArtist(artistById.getData());
            track.setDuration(resultSetTrack.getInt("duration"));
            track.setUrl(resultSetTrack.getString("url"));
        }
        return new ApiResponse<>(true,"Here it is!",track);

    }
}
