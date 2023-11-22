package dnd.vention.service;

import dnd.vention.config.SecurityConfiguration;
import dnd.vention.db.DbConnection;
import dnd.vention.exception.MainServiceException;
import dnd.vention.payload.ApiResponse;
import dnd.vention.payload.Role;
import dnd.vention.payload.User;
import dnd.vention.payload.enums.RoleType;
import lombok.SneakyThrows;

import javax.security.auth.login.Configuration;
import java.sql.*;

public class UserService {
    @SneakyThrows
    public static ApiResponse checkUsernameExistence(String username) {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();
        String sqlCheckUserByUserName = "select * from Users where username='" + username + "';";
        ResultSet resultSet = statement.executeQuery(sqlCheckUserByUserName);
        if (!resultSet.isBeforeFirst()) {
            connection.close();
            return new ApiResponse(true, "Now you can create new user!");
        } else {
            MainServiceException mainServiceException = new MainServiceException();
            connection.close();
            return new ApiResponse(false, "Username Already Exist! ", mainServiceException);
        }

    }


    public static User registerUser(User getValuesFromRequest) throws SQLException, ClassNotFoundException {
        String firstname = getValuesFromRequest.getFirstname();
        String lastname = getValuesFromRequest.getLastname();
        String username = getValuesFromRequest.getUsername();
        String passwordFromUser = getValuesFromRequest.getPassword();


        Connection connection = DbConnection.getConnection();
        String sqlRegisterUser = "insert into Users(" +
                "firstname,lastname,username,password,role_id,is_enabled,is_blocked)" +
                "values(?,?,?,?,?,?,? " +
                ")";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlRegisterUser);
        preparedStatement.setString(1, firstname);
        preparedStatement.setString(2, lastname);
        preparedStatement.setString(3, username);
        String password = SecurityConfiguration.encodePassword(passwordFromUser);
        preparedStatement.setString(4, password);
        Role roleFromDbByRoleType = getRoleFromDbByRoleType();
        preparedStatement.setInt(5, roleFromDbByRoleType.getId());
        preparedStatement.setBoolean(6, true);
        preparedStatement.setBoolean(7, false);
        preparedStatement.execute();
        connection.close();
        Connection connection1 = DbConnection.getConnection();
        Statement statement = connection1.createStatement();
        String getUserFromDb = "select * from Users where username='" + getValuesFromRequest.getUsername() + "';";
        ResultSet resultSet = statement.executeQuery(getUserFromDb);
        User user = new User();
        while (resultSet.next()) {
            user.setId(resultSet.getInt("id"));
            user.setBlocked(resultSet.getBoolean("is_blocked"));
            user.setEnabled(resultSet.getBoolean("is_enabled"));
            user.setFirstname(resultSet.getString("firstname"));
            user.setLastname(resultSet.getString("lastname"));
            Role role = getRoleFromDbByRoleType();
            user.setRole(role);
            user.setUsername(resultSet.getString("username"));
            String passwordFromDb = resultSet.getString("password");
            String decodedPassword = SecurityConfiguration.decodePassword(passwordFromDb);
            user.setPassword(decodedPassword);
        }
        connection1.close();
        return user;
    }

    private static Role getRoleFromDbByRoleType() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Roles where role_type='" + RoleType.USER.toString() + "';");
        if (!resultSet.isBeforeFirst()) {
            throw new MainServiceException("RoleNotFound");
        }
        Role role = new Role();
        while (resultSet.next()) {
            role.setId(resultSet.getInt("id"));
            RoleType roleType = RoleType.USER;
            role.setRoleType(roleType);
        }
        return role;
    }

    private static Role getRoleFromDbByRoleId(Integer roleId) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Roles where id=" + roleId + ";");
        if (!resultSet.isBeforeFirst()) {
            throw new MainServiceException("RoleNotFound");
        }
        Role role = new Role();
        while (resultSet.next()) {
            role.setId(resultSet.getInt("id"));
            String role_typeFromDb = resultSet.getString("role_type");
            RoleType roleType = RoleType.valueOf(role_typeFromDb);
            role.setRoleType(roleType);
        }
        return role;
    }


    public static ApiResponse<User> loginUser(String username, String password) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statementForLogin = connection.createStatement();
        String usernameFromDb = "";
        String passwordFromDb = "";

        String getUserFromDb = "select * from Users where username='" + username + "';";
        ResultSet resultSetForGettingFromDb = statementForLogin.executeQuery(getUserFromDb);
        while (resultSetForGettingFromDb.next()) {
            usernameFromDb = resultSetForGettingFromDb.getString("username");
            passwordFromDb = resultSetForGettingFromDb.getString("password");
        }
        boolean isPasswordsMatched = SecurityConfiguration.validatePassword(password, passwordFromDb);
        boolean isUsernameMatched = username.equals(usernameFromDb);
        if (isUsernameMatched && isPasswordsMatched) {
            Connection connection1 = DbConnection.getConnection();
            Statement statement = connection1.createStatement();
            ResultSet resultSet = statement.executeQuery(getUserFromDb);
            User user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setBlocked(resultSet.getBoolean("is_blocked"));
                user.setEnabled(resultSet.getBoolean("is_enabled"));
                user.setFirstname(resultSet.getString("firstname"));
                user.setLastname(resultSet.getString("lastname"));
                int role_id = resultSet.getInt("role_id");
                Role role = getRoleFromDbByRoleId(role_id);
                user.setRole(role);
                user.setUsername(resultSet.getString("username"));
                String passwordFromDbResultSet = resultSet.getString("password");
                String decodedPassword = SecurityConfiguration.decodePassword(passwordFromDbResultSet);
                user.setPassword(null);
            }
            connection1.close();
            return new ApiResponse<>(true, "Here it is!", user);
        } else {
            return new ApiResponse<>(false, "username or password is wrong!");
        }
    }

    public static ApiResponse<User> blockUser(String userId, String isBlocked) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getConnection();
        Statement statement = connection.createStatement();


        String sqlGetUserFromDb = "select * from Users where id=" + Integer.parseInt(userId) + ";";
        ResultSet resultSet = statement.executeQuery(sqlGetUserFromDb);
        if (!resultSet.isBeforeFirst()) {
            MainServiceException mainServiceException = new MainServiceException("UserNot Found");
            return new ApiResponse<>(false, mainServiceException.getMessage());
        }
        Boolean aBoolean = Boolean.valueOf(isBlocked);
        String sqlBlockUser = "update  Users " +
                "set is_Blocked=" + aBoolean + "" +
                " where id ="+ Integer.parseInt(userId)+" ;";
        statement.execute(sqlBlockUser);
        ApiResponse<User> userById = getUserById(Integer.parseInt(userId));
        return new ApiResponse<>(true, "Done", userById.getData());
    }

    public static ApiResponse<User> getUserById(Integer id) throws SQLException, ClassNotFoundException {
        String getUserFromDb = "select * from Users where id=" + id + ";";
        Connection connection1 = DbConnection.getConnection();
        Statement statement = connection1.createStatement();
        ResultSet resultSet = statement.executeQuery(getUserFromDb);
        User user = new User();
        while (resultSet.next()) {
            user.setId(resultSet.getInt("id"));
            user.setBlocked(resultSet.getBoolean("is_blocked"));
            user.setEnabled(resultSet.getBoolean("is_enabled"));
            user.setFirstname(resultSet.getString("firstname"));
            user.setLastname(resultSet.getString("lastname"));
            int role_id = resultSet.getInt("role_id");
            Role role = getRoleFromDbByRoleId(role_id);
            user.setRole(role);
            user.setUsername(resultSet.getString("username"));
            String passwordFromDbResultSet = resultSet.getString("password");
            String decodedPassword = SecurityConfiguration.decodePassword(passwordFromDbResultSet);
            user.setPassword(null);
        }
        connection1.close();
        return new ApiResponse<>(true, "Here it is!", user);
    }

    public static ApiResponse<?> checkUserBlocked(User user){
        if (user.isBlocked())
            return new ApiResponse(true,"User is blocked!!!!");
    return new ApiResponse(false,"User not blocked!");
    }
}

