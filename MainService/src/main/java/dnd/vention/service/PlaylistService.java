package dnd.vention.service;

import dnd.vention.db.DbConnection;
import dnd.vention.exception.MainServiceException;
import dnd.vention.payload.*;
import dnd.vention.payload.enums.PlaylistTypeEnum;
import dnd.vention.payload.payloadForUrl.PlaylistTracksDto;
import dnd.vention.payload.payloadForUrl.PlaylistUpdateDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static dnd.vention.service.RankService.getPlayListRankById;
import static dnd.vention.service.TrackService.getTracksForPlaylist;

public class PlaylistService {


    public static ApiResponse<Playlist> getPlayListById(Integer playListId) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();
        String sqlGetPlayListById = "select * from Playlist where id=" + playListId + ";";
        ResultSet resultSetPlayList = statement.executeQuery(sqlGetPlayListById);
        Playlist playlist = new Playlist();
        if (!resultSetPlayList.isBeforeFirst()) {
            MainServiceException mainServiceException = new MainServiceException("Wrong playlist id!");
            return new ApiResponse<>(false, mainServiceException.getMessage());
        }
        while (resultSetPlayList.next()) {
            playlist.setId(resultSetPlayList.getInt("id"));
            playlist.setPlaycount(resultSetPlayList.getInt("playcount"));
            playlist.setListeners(resultSetPlayList.getInt("Listeners"));
            playlist.setName(resultSetPlayList.getString("name"));
            PlaylistType playlistType = getPlaylistTypeById(resultSetPlayList.getInt("playlist_type_id"));
            playlist.setPlaylistType(playlistType);
            List<Track> tracksOfPlaylist = getTracksOfPlaylistMethod(resultSetPlayList.getInt("id"));
            playlist.setTrackList(tracksOfPlaylist);
            playlist.setRank(getPlayListRankById(resultSetPlayList.getInt("rank_id")));
        }
        connection.close();
        return new ApiResponse<>(true, "Here you are!", playlist);
    }

    public static List<Playlist> getAllPlaylists() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();
        String sqlGetAllPlayLists = "select * from Playlist;";
        ResultSet resultSetGetAllPlaylists = statement.executeQuery(sqlGetAllPlayLists);
        if (!resultSetGetAllPlaylists.isBeforeFirst()) return new ArrayList<>();
        List<Playlist> playlists = new ArrayList<>();
        while (resultSetGetAllPlaylists.next()) {
            Playlist playlist = new Playlist();
            playlist.setId(resultSetGetAllPlaylists.getInt("id"));
            playlist.setPlaycount(resultSetGetAllPlaylists.getInt("playcount"));
            playlist.setListeners(resultSetGetAllPlaylists.getInt("Listeners"));
            playlist.setName(resultSetGetAllPlaylists.getString("name"));
            PlaylistType playlistType = getPlaylistTypeById(resultSetGetAllPlaylists.getInt("playlist_type_id"));
            playlist.setPlaylistType(playlistType);
            List<Track> tracksOfPlaylist = getTracksOfPlaylistMethod(resultSetGetAllPlaylists.getInt("id"));
            playlist.setTrackList(tracksOfPlaylist);
            playlist.setRank(getPlayListRankById(resultSetGetAllPlaylists.getInt("rank_id")));
            playlists.add(playlist);
        }
        connection.close();
        return playlists;
    }

    public static List<Track> getTracksOfPlaylistMethod(int playlistId) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from PlaylistTrack where playlistid=" + playlistId + ";");
        List<Track> tracksOfPlayList = new ArrayList<>();
        while (resultSet.next()) {
            int trackId = resultSet.getInt("trackid");
            Track track = getTracksForPlaylist(trackId);
            tracksOfPlayList.add(track);
        }
        connection.close();
        return tracksOfPlayList;
    }

    public static PlaylistType getPlaylistTypeById(int playlist_type_id) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select  * from PlaylistType where id=" + playlist_type_id + ";");
        if (!resultSet.isBeforeFirst()) return new PlaylistType();
        PlaylistType playlistType = new PlaylistType();
        while (resultSet.next()) {
            playlistType.setId(playlist_type_id);
            PlaylistTypeEnum playlisttypeenum = PlaylistTypeEnum.valueOf(resultSet.getString("playlisttypeenum"));
            playlistType.setPlaylistTypeEnum(playlisttypeenum);
        }
        connection.close();
        return playlistType;
    }

    public static ApiResponse<Playlist> updatePlaylist(String playlistId, PlaylistUpdateDto valuesFromRequest) throws SQLException, ClassNotFoundException {
        Integer playlistIdInt = Integer.parseInt(playlistId);
        ApiResponse<Playlist> playListById = getPlayListById(Integer.parseInt(playlistId));
        boolean playlistExistence = checkPlaylistExistence(playlistIdInt);
        if (playlistExistence) {
            Connection connection = DbConnection.getConnection();
            Statement connectionStatement = connection.createStatement();
//            PlaylistType playlistType = valuesFromRequest.getPlaylistType();
            Integer playlistTypeId = valuesFromRequest.getPlaylistTypeId();
            boolean checkPlaylistTypeForExistence = checkPlaylistTypeForExistence(playlistTypeId);
            if (!checkPlaylistTypeForExistence) {
                MainServiceException mainServiceException = new MainServiceException("PlaylistType not exist!");
                return new ApiResponse(false, mainServiceException.getMessage());
            }
            String sqlUpdatePlaylist = "UPDATE Playlist " +
                    "SET name = '" + valuesFromRequest.getName() + "'," +
                    " playlist_type_id =" + valuesFromRequest.getPlaylistTypeId() +
                    "WHERE id=" + playlistId + " ;";
            connectionStatement.execute(sqlUpdatePlaylist);
        } else {
            MainServiceException mainServiceException = new MainServiceException("Playlist not found!");
            return new ApiResponse<>(false, mainServiceException.getMessage());
        }
        ApiResponse<Playlist> playListById1 = getPlayListById(playlistIdInt);
        return new ApiResponse<Playlist>(true, "Here it is!", playListById1.getData());
    }

    public static boolean checkPlaylistTypeForExistence(Integer playlistTypeId) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement connectionStatement = connection.createStatement();
        String sqlCheckTrackExistenceInPlaylist = "select * from PlaylistType where id=" + playlistTypeId + ";";
        ResultSet resultSet = connectionStatement.executeQuery(sqlCheckTrackExistenceInPlaylist);
        connection.close();
        return resultSet.isBeforeFirst();
    }

    private static boolean checkPlaylistExistence(Integer playlistId) throws SQLException, ClassNotFoundException {

        Connection connection = DbConnection.getConnection();
        Statement connectionStatement = connection.createStatement();
        String sqlCheckTrackExistenceInPlaylist = "select * from Playlist where id=" + playlistId + ";";
        ResultSet resultSet = connectionStatement.executeQuery(sqlCheckTrackExistenceInPlaylist);
        connection.close();
        return resultSet.isBeforeFirst();
    }
}
