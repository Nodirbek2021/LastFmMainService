package dnd.vention.servlet.playlist;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import dnd.vention.filter.TokenGenerator;
import dnd.vention.filter.payload.TokenInfo;
import dnd.vention.payload.ApiResponse;
import dnd.vention.payload.Playlist;
import dnd.vention.service.CreatePlaylistService;
import dnd.vention.service.UserCheckService;
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
        //Auth
        PrintWriter writer = resp.getWriter();
        String headerToken = req.getHeader("Authorization");
        TokenInfo tokenInfo = UserCheckService.tokenDecoder(headerToken);
        boolean userTokenExpiration = UserCheckService.checkUserTokenExpiration(tokenInfo);
        if (!userTokenExpiration){
            ApiResponse<Object> objectApiResponse = new ApiResponse<>(false, "Token Expired!");
            writer.write(objectApiResponse.toString());
            return;
        }

        //creation of playlist
        Playlist valuesFromRequest = getValuesFromRequest(req);
        Playlist playlist = CreatePlaylistService.savePlaylistToDb(valuesFromRequest);
        Gson gson=new Gson();
        String stringPlaylistJson = gson.toJson(playlist);
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
