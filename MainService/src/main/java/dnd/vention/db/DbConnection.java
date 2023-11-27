package dnd.vention.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DbConnection {
    public static String data_source_url = "jdbc:postgresql://localhost:5432/MainService";
    public static String data_source_username = "postgres";
    public static String data_source_password = "root123";


    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(
                data_source_url,
                data_source_username,
                data_source_password
        );

        return connection;
    }

    public static void createDbTables() throws SQLException, ClassNotFoundException {
        Statement statement1 = getConnection().createStatement();
//        String dropDB="drop database TrackLoader;";
//        String createDB="create database TrackLoader;";
//        statement1.execute(dropDB);
//        statement1.execute(createDB);

        //1
        String sqlPageCreation = "CREATE  TABLE Page(" +
                "id integer NOT NULL GENERATED ALWAYS AS IDENTITY," +
                "page INTEGER," +
                "per_page INTEGER," +
                "total INTEGER," +
                "totalPages INTEGER," +
                "PRIMARY KEY (id)" +
                ");";
        //2
        String sqlRankCreation = "CREATE  TABLE Rank(" +
                "rank INTEGER," +
                "id integer NOT NULL GENERATED ALWAYS AS IDENTITY," +
                "PRIMARY KEY (id)" +
                ");";
        //3
        String sqlArtistObjectCreation = "CREATE TABLE ArtistObject(" +
                "mbid character varying," +
                "listeners INTEGER," +
                "playcount INTEGER," +
                "url character varying," +
                "name character varying," +
                "streamable character varying," +
                "id integer NOT NULL GENERATED ALWAYS AS IDENTITY," +
                "PRIMARY KEY (id)" +
                ");";
        //4
        String sqlTrackObjectCreation = "CREATE TABLE TrackObject(" +
                "is_blocked BOOLEAN,"+
                "name character varying," +
                "duration INTEGER," +
                "playcount INTEGER," +
                "listeners INTEGER," +
                "mbid character varying," +
                "url character varying," +
                "rank_id integer," +
                "artist_object_id integer," +
                "id integer NOT NULL GENERATED ALWAYS AS IDENTITY," +
                "PRIMARY KEY (id)" +
                ");";
        //5
        String sqlUserCreation = "CREATE TABLE Users(" +
                "role_Id INTEGER," +
                "is_blocked BOOLEAN," +
                "is_enabled BOOLEAN," +
                "password character varying," +
                "username character varying," +
                "lastname character varying," +
                "firstname character varying," +
                "id integer NOT NULL GENERATED ALWAYS AS IDENTITY," +
                "PRIMARY KEY (id)" +
                ");";
        //5
        String sqlRoleCreation = "CREATE TABLE Roles(" +
                "role_type character varying," +
                "id integer NOT NULL GENERATED ALWAYS AS IDENTITY," +
                "PRIMARY KEY (id)" +
                ");";
        //6
        String sqlRateCreation = "CREATE TABLE Rate(" +
                "liked BOOLEAN," +
                "playlist_Id Integer," +
                "userId Integer ," +
                "id integer NOT NULL GENERATED ALWAYS AS IDENTITY," +
                "PRIMARY KEY (id)" +
                ");";
        //7
        String sqlPlaylistTrackCreation = "CREATE TABLE PlaylistTrack(" +
                "playlistId Integer," +
                "trackId Integer ," +
                "id integer NOT NULL GENERATED ALWAYS AS IDENTITY," +
                "PRIMARY KEY (id)" +
                ");";
        //8
        String sqlPlayListCreation = "CREATE TABLE PlayList(" +
                "playlist_type_id integer," +
                "rank_id Integer ," +
                "listeners Integer ," +
                "playcount Integer ," +
                "name character varying ," +
                "id integer NOT NULL GENERATED ALWAYS AS IDENTITY," +
                "PRIMARY KEY (id)" +
                ");";
        //9
        String sqlAlbumCreation = "CREATE TABLE Album(" +
                "artist_id INTEGER," +
                "isBlocked BOOLEAN," +
                "listeners Integer ," +
                "playcount Integer ," +
                "name character varying ," +
                "id integer NOT NULL GENERATED ALWAYS AS IDENTITY," +
                "PRIMARY KEY (id)" +
                ");";
        //10
        String sqlAlbumTracks = "CREATE TABLE AlbumTracks(" +
                "track_id INTEGER," +
                "album_id INTEGER," +

                "id integer NOT NULL GENERATED ALWAYS AS IDENTITY," +
                "PRIMARY KEY (id)" +
                ");";

        //11
        String sqlRankPlaylistCreation = "CREATE  TABLE RankPlaylist(" +
                "rank INTEGER," +
                "id integer NOT NULL GENERATED ALWAYS AS IDENTITY," +
                "PRIMARY KEY (id)" +
                ");";
        //12
        String sqlUserAlbumCreation = "CREATE  TABLE UserAlbum(" +
                "album_id INTEGER," +
                "user_id INTEGER," +
                "id integer NOT NULL GENERATED ALWAYS AS IDENTITY," +
                "PRIMARY KEY (id)" +
                ");";
//13
        String sqlUserPlaylistCreation = "CREATE  TABLE UserPlaylist(" +
                "playlist_id INTEGER," +
                "user_id INTEGER," +
                "id integer NOT NULL GENERATED ALWAYS AS IDENTITY," +
                "PRIMARY KEY (id)" +
                ");";
        //14
        String sqlPlaylistTypeCreation = "CREATE  TABLE PlaylistType(" +
                "playlistTypeEnum character varying," +
                "id integer NOT NULL GENERATED ALWAYS AS IDENTITY," +
                "PRIMARY KEY (id)" +
                ");";


        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        boolean executeSqlPageCreation = statement.execute(sqlPageCreation);
        boolean executeSqlRankCreation = statement.execute(sqlRankCreation);
        boolean executeSqlArtistObjectCreation = statement.execute(sqlArtistObjectCreation);
        boolean executeSqlTrackObjectCreation = statement.execute(sqlTrackObjectCreation);
        boolean executeSqlUserCreation = statement.execute(sqlUserCreation);
        boolean executeSqlRoleCreation = statement.execute(sqlRoleCreation);
        boolean executeSqlRateCreation = statement.execute(sqlRateCreation);
        boolean executeSqlPlaylistTrackCreation = statement.execute(sqlPlaylistTrackCreation);
        boolean executeSqlPlaylistCreation = statement.execute(sqlPlayListCreation);
        boolean executeSqlAlbumCreation = statement.execute(sqlAlbumCreation);
        boolean executeSqlAlbumTracksCreation = statement.execute(sqlAlbumTracks);
        boolean executeSqlRankPlaylistCreation = statement.execute(sqlRankPlaylistCreation);
        boolean executeSqlUserAlbumCreation = statement.execute(sqlUserAlbumCreation);
        boolean executeSqlUserPlaylistCreation = statement.execute(sqlUserPlaylistCreation);
        boolean executeSqlPlaylistTypeCreation = statement.execute(sqlPlaylistTypeCreation);

        connection.close();

    }


}
