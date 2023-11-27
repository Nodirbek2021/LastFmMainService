package dnd.vention;

import dnd.vention.config.SecurityConfiguration;
import dnd.vention.db.DbConnection;
import dnd.vention.payload.enums.PlaylistTypeEnum;
import dnd.vention.payload.enums.RoleType;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Base64;

public class Main {
//    public static void main(String[] args) throws IOException {
////        URL url = new URL("http://localhost:8081/api/v1/getTopArtists");
////        URLConnection connection = url.openConnection();
////        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
////        StringBuilder stringBuilder = new StringBuilder();
////
////        String inputline;
////        while ((inputline = bufferedReader.readLine()) != null) {
////            stringBuilder.append(inputline);
////        }
////        bufferedReader.close();
////        System.out.println(inputline);
//
//
////        URL oracle = new URL("http://localhost:8081/api/v1/getTopArtists?page=15");
//        URL oracle = new URL("http://localhost:8081/api/v1/getTopArtists?page=1");
//        URLConnection yc = oracle.openConnection();
//        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
//        StringBuilder stringBuilder =new StringBuilder();
//
//        String inputLine;
//        while ((inputLine = in.readLine()) != null)
//            stringBuilder.append(inputLine);
//        in.close();
//
//        System.out.println("String Builder: >>>>>>>>>>");
//        System.out.println(stringBuilder);
//
//
//
////        Gson gson = new Gson();
////        Arrays.asList( gson.fromJson(stringBuilder,ArtistObject[].class));
//
//        ObjectMapper mapper =new ObjectMapper();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
//        ArtistObject[] artistObjectList= mapper.readValue(stringBuilder.toString(), ArtistObject[].class);
//        System.out.println(artistObjectList[1]);
//        ArtistObject artistObject=artistObjectList[1];
//        System.out.println(artistObject.getId());
//
//
//    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        DbConnection.createDbTables();
//
////        Role Creation;
        Connection connection = DbConnection.getConnection();
//        String sqlInsertSuperAdminRole = "insert into Roles (role_type) " +
//                "values('" + RoleType.SUPER_ADMIN.toString() + "');";
//        String sqlInsertAdminRole = "insert into Roles (role_type) " +
//                "values('" + RoleType.ADMIN.toString() + "');";
//        String sqlInsertUserRole = "insert into Roles (role_type) " +
//                "values('" + RoleType.USER.toString() + "');";
        Statement statement = connection.createStatement();
//        statement.execute(sqlInsertSuperAdminRole);
//        statement.execute(sqlInsertAdminRole);
//        statement.execute(sqlInsertUserRole);

//        String sqlInsertPlayListTypePr = "insert into PlayListType (playlisttypeenum)" +
//                "values('" + PlaylistTypeEnum.PRIVATE + "');";
//        String sqlInsertPlayListTypePub = "insert into PlayListType (playlisttypeenum)" +
//                "values('" + PlaylistTypeEnum.PUBLIC + "');";
//    statement.execute(sqlInsertPlayListTypePr);
//    statement.execute(sqlInsertPlayListTypePub);

    }


}
