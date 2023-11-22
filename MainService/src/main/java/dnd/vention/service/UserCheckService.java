package dnd.vention.service;

import dnd.vention.db.DbConnection;
import dnd.vention.filter.TokenGenerator;
import dnd.vention.filter.payload.TokenInfo;
import dnd.vention.payload.ApiResponse;
import dnd.vention.payload.Role;
import dnd.vention.payload.enums.RoleType;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserCheckService {
    public static TokenInfo tokenDecoder(String token) {
        TokenInfo tokenInfo = TokenGenerator.decodeTokenWithRole(token);
        return tokenInfo;
    }

    public static boolean checkUserTokenExpiration(TokenInfo tokenInfo) {
        boolean valid = TokenGenerator.isValid(tokenInfo);
        return valid;
    }

    public static ApiResponse<?> checkForAdminRole(String token) throws SQLException, ClassNotFoundException {
        TokenInfo tokenInfo = tokenDecoder(token);
        Integer role_id = tokenInfo.getRole_id();
        Connection connection = DbConnection.getConnection();
        Statement connectionStatement = connection.createStatement();
        ResultSet resultSet = connectionStatement.executeQuery("select * from Roles where id =" + role_id + ";");
        Role role = new Role();
        while (resultSet.next()) {
            role.setId(resultSet.getInt("id"));
            RoleType roleType = RoleType.valueOf(resultSet.getString("role_type"));
            role.setRoleType(roleType);
        }
        if (role.getRoleType().toString().equals("ADMIN")) {
            return new ApiResponse<>(true, "Admin");
        }
        return new ApiResponse<>(false, "Not Admin");
    }
}

