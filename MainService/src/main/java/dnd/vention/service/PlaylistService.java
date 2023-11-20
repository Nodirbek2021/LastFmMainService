package dnd.vention.service;

import dnd.vention.db.DbConnection;
import dnd.vention.payload.*;
import dnd.vention.payload.enums.PlaylistTypeEnum;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlaylistService {

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
            List<Track> tracksOfPlaylist=getTracksOfPlaylistMethod(resultSetGetAllPlaylists.getInt("id"));
            playlist.setTrackList(tracksOfPlaylist);
            playlist.setRank(getPlayListRankById(resultSetGetAllPlaylists.getInt("rank_id")));
            playlists.add(playlist);
        }
        connection.close();
        return playlists;
    }

    public static RankPlaylist getPlayListRankById(int rank_id) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet execute = statement.executeQuery("select  * from RankPlaylist where id=" + rank_id + ";");
        if (!execute.isBeforeFirst()) return new RankPlaylist();
        RankPlaylist rank =new RankPlaylist();
        while (execute.next()) {
            rank.setId(rank_id);
            rank.setRank(execute.getInt("rank"));
        }
        connection.close();
        return rank;

    }

    public static List<Track> getTracksOfPlaylistMethod(int playlistId) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from PlaylistTrack where playlistid=" + playlistId + ";");
        List<Track> tracksOfPlayList=new ArrayList<>();
        while (resultSet.next()) {
            int trackId=resultSet.getInt("trackid");
            Track track=getTracksForPlaylist(trackId);
            tracksOfPlayList.add(track);
        }
        connection.close();
        return tracksOfPlayList;
    }

    public static Track getTracksForPlaylist(int trackId) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSetTrack = statement.executeQuery("select  * from TrackObject where id=" + trackId + ";");
        if (!resultSetTrack.isBeforeFirst()) return new Track();
        Track track=new Track();
        while (resultSetTrack.next()) {
            track.setId(resultSetTrack.getInt("id"));
            track.setName(resultSetTrack.getString("name"));
            track.setBlocked(resultSetTrack.getBoolean("is_blocked"));
            track.setPlaycount(resultSetTrack.getInt("playcount"));
            track.setListeners(resultSetTrack.getInt("listeners"));
            track.setMbid(resultSetTrack.getString("mbid"));
            track.setRank(getRankById(resultSetTrack.getInt("rank_id")));
            track.setArtist(getArtistById(resultSetTrack.getInt("artist_object_id")));
            track.setDuration(resultSetTrack.getInt("duration"));
            track.setUrl(resultSetTrack.getString("url"));
        }
        connection.close();
        return track;

    }

    private static Artist getArtistById(int artist_object_id) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet execute = statement.executeQuery("select  * from ArtistObject where id=" + artist_object_id + ";");
        if (!execute.isBeforeFirst()) return new Artist();
        Artist artist =new Artist();
        while (execute.next()) {
            artist.setId(execute.getInt("id"));
            artist.setListeners(execute.getInt("listeners"));
            artist.setPlaycount(execute.getInt("playcount"));
            artist.setMbid(execute.getString("mbid"));
            artist.setStreamable(execute.getString("streamable"));
            artist.setUrl(execute.getString("url"));
            artist.setName(execute.getString("name"));
        }
        connection.close();
        return artist;

    }

    public static Rank getRankById(int rank_id) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet execute = statement.executeQuery("select  * from Rank where id=" + rank_id + ";");
        if (!execute.isBeforeFirst()) return new Rank();
        Rank rank =new Rank();
        while (execute.next()) {
            rank.setId(rank_id);
            rank.setRank(execute.getInt("rank"));
        }
        connection.close();
        return rank;

    }

    public static PlaylistType getPlaylistTypeById(int playlist_type_id) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select  * from PlaylistType where id=" + playlist_type_id + ";");
        if (!resultSet.isBeforeFirst())return new PlaylistType();
        PlaylistType playlistType=new PlaylistType();
        while (resultSet.next()) {
            playlistType.setId(playlist_type_id);
            PlaylistTypeEnum playlisttypeenum = PlaylistTypeEnum.valueOf(resultSet.getString("playlisttypeenum"));
            playlistType.setPlaylistTypeEnum(playlisttypeenum);
        }
        connection.close();
        return playlistType;
    }
}
