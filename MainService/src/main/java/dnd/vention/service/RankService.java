package dnd.vention.service;

import dnd.vention.db.DbConnection;
import dnd.vention.payload.Rank;
import dnd.vention.payload.RankPlaylist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RankService {
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
}
