package dnd.vention.service;

import dnd.vention.db.DbConnection;
import dnd.vention.payload.Playlist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreatePlaylistService {
    public static Playlist savePlaylistToDb(Playlist playlist) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();
        String sqlSavePlaylist="insert into Playlist(name)" +
                "values('" +
                playlist.getName() +
                "')";
        statement.execute(sqlSavePlaylist);
        ResultSet resultSetPlayList = statement.executeQuery("select * from Playlist where name ='" + playlist.getName() + "';");
        Playlist responsePlaylist=new Playlist();
        while (resultSetPlayList.next()) {
            responsePlaylist.setId(resultSetPlayList.getInt("id"));
            responsePlaylist.setName(resultSetPlayList.getString("name"));
        }
        return responsePlaylist;
    }
}
