package dnd.vention.service;

import dnd.vention.db.DbConnection;
import dnd.vention.exception.MainServiceException;
import dnd.vention.payload.ApiResponse;
import dnd.vention.payload.Artist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArtistService {

    public static ApiResponse<Artist> getArtistById(int artist_object_id) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet execute = statement.executeQuery("select  * from ArtistObject where id=" + artist_object_id + ";");
        if (!execute.isBeforeFirst()){
            MainServiceException mainServiceException=new MainServiceException("Artist Not Found! ");
            return new ApiResponse<Artist>(false,mainServiceException.getMessage());
        }
        Artist artist = new Artist();
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
        return new ApiResponse<Artist>(true,"Here it is ! ",artist);

    }
}
