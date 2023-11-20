package dnd.vention.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import dnd.vention.filter.TokenGenerator;
import dnd.vention.filter.payload.TokenInfo;
import dnd.vention.payload.Playlist;
import dnd.vention.payload.User;
import dnd.vention.service.CreatePlaylistService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/v1/api/secure/playlist/createPlaylist")
public class CreatePlaylistServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String headerToken = req.getHeader("Authorization");
//        UserService.isUserHavePermission(headerToken);
        TokenInfo tokenInfo = TokenGenerator.decodeTokenWithRole(headerToken);
        //checking token expiration
        boolean valid = TokenGenerator.isValid(tokenInfo);
        Playlist valuesFromRequest = getValuesFromRequest(req);
        Playlist playlist = CreatePlaylistService.savePlaylistToDb(valuesFromRequest);
        Gson gson=new Gson();
        String stringPlaylistJson = gson.toJson(playlist);
        PrintWriter writer = resp.getWriter();
        writer.write(stringPlaylistJson);

    }

    public Playlist getValuesFromRequest(HttpServletRequest request) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Playlist playlist = objectMapper.readValue(requestBody.toString(), Playlist.class);
        return playlist;
    }
}
