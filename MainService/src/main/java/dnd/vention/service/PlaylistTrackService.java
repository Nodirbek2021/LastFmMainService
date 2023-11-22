package dnd.vention.service;

import dnd.vention.db.DbConnection;
import dnd.vention.exception.MainServiceException;
import dnd.vention.payload.ApiResponse;
import dnd.vention.payload.Playlist;
import dnd.vention.payload.Track;
import dnd.vention.payload.payloadForUrl.PlaylistTracksDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static dnd.vention.service.PlaylistService.getPlayListById;
import static dnd.vention.service.TrackService.getTracksForPlaylist;

public class PlaylistTrackService {
    public static ApiResponse<Playlist> addTracksToPlayListMethod(PlaylistTracksDto playlistTracksDto) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();
        ApiResponse<Playlist> playListById = getPlayListById(playlistTracksDto.getListId());
        Playlist playlist = playListById.getData();
//        List<Track> trackList = playlist.getTrackList();
        List<Integer> trackIdList = playlistTracksDto.getTrackIdList();
        for (int i = 0; i < trackIdList.size(); i++) {
            Track tracksForPlaylist = getTracksForPlaylist(trackIdList.get(i));
            ResultSet resultSet = PlaylistTrackService.checkPlaylistTrackExistence(playlist.getId(), trackIdList.get(i));
            if (resultSet.isBeforeFirst()){
                continue;
            }
            Statement connectionStatement = connection.createStatement();
            String sqlAddTrackToPlaylist = "insert into PlaylistTrack (playlistid,trackid)" +
                    "values (" +
                    playlist.getId() + "," + tracksForPlaylist.getId() +
                    ");";
            boolean execute = connectionStatement.execute(sqlAddTrackToPlaylist);
            if (execute) {
                MainServiceException mainServiceException=new MainServiceException("Cannot save to db");
                return new ApiResponse<>(false,mainServiceException.getMessage());
            }
        }
        ApiResponse<Playlist> playListById1 = getPlayListById(playlistTracksDto.getListId());
        return playListById1;
    }

    public static ResultSet checkPlaylistTrackExistence(Integer playlistId,Integer trackId) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement connectionStatement = connection.createStatement();
        String sqlCheckTrackExistenceInPlaylist="select * from PlaylistTrack where playlistid="+playlistId+" and trackid="+trackId+";";
        ResultSet resultSet = connectionStatement.executeQuery(sqlCheckTrackExistenceInPlaylist);
        return resultSet;
    }

    public static ApiResponse<List<Track>> deleteTrackFromPlaylist(String playlistId, String trackId) throws SQLException, ClassNotFoundException {
        int playlistIdInt = Integer.parseInt(playlistId);
        int trackIdInt = Integer.parseInt(trackId);
        ResultSet resultSet = checkPlaylistTrackExistence(playlistIdInt, trackIdInt);
        if (resultSet.isBeforeFirst()){
            Connection connection = DbConnection.getConnection();
            Statement connectionStatement = connection.createStatement();
            connectionStatement.execute("delete from PlaylistTrack where playlistid="+playlistIdInt+" and trackid="+trackIdInt+";");
            connection.close();
        }else {
            MainServiceException mainServiceException=new MainServiceException("There is no this kind of track in This playlist");
            return new ApiResponse<>(false,mainServiceException.getMessage());
        }

        ApiResponse<Playlist> playListById = getPlayListById(playlistIdInt);

        return new ApiResponse<>(true,"Deleted!",playListById.getData().getTrackList());
    }
}
