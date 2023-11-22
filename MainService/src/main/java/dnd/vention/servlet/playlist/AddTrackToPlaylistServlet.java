package dnd.vention.servlet.playlist;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import dnd.vention.filter.payload.TokenInfo;
import dnd.vention.payload.ApiResponse;
import dnd.vention.payload.Playlist;
import dnd.vention.payload.payloadForUrl.PlaylistTracksDto;
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

import static dnd.vention.service.PlaylistTrackService.addTracksToPlayListMethod;

@WebServlet("/v1/api/secure/playlist/addTracksToPlaylist")
public class AddTrackToPlaylistServlet extends HttpServlet {
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



        //Getting values
        PlaylistTracksDto valuesFromRequest = getValuesFromRequest(req);
        //Service
        ApiResponse<Playlist> playlistApiResponse = addTracksToPlayListMethod(valuesFromRequest);
        Gson gson=new Gson();
        String response = gson.toJson(playlistApiResponse);
        writer.write(response);
    }



    public PlaylistTracksDto getValuesFromRequest(HttpServletRequest request) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        PlaylistTracksDto playlistTracksDto = objectMapper.readValue(requestBody.toString(), PlaylistTracksDto.class);
        return playlistTracksDto;
    }
}
