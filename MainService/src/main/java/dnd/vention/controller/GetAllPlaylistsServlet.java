package dnd.vention.controller;

import com.google.gson.Gson;
import dnd.vention.filter.TokenGenerator;
import dnd.vention.filter.payload.TokenInfo;
import dnd.vention.payload.Playlist;
import dnd.vention.service.PlaylistService;
import dnd.vention.service.UserService;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

@WebServlet("/v1/api/secure/playlist/getAllPlaylist")
public class GetAllPlaylistsServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String headerToken = req.getHeader("Authorization");
//        UserService.isUserHavePermission(headerToken);
        TokenInfo tokenInfo = TokenGenerator.decodeTokenWithRole(headerToken);
        //checking token expiration
        boolean valid = TokenGenerator.isValid(tokenInfo);
        List<Playlist> allPlaylists = PlaylistService.getAllPlaylists();
        PrintWriter writer = resp.getWriter();
        Gson gson =new Gson();
//        String stringJson = gson.toJson(allPlaylists);
//        resp.setContentType("application/json");
        writer.write(allPlaylists.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
