package dnd.vention.servlet.album;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dnd.vention.filter.payload.TokenInfo;
import dnd.vention.payload.Album;
import dnd.vention.payload.ApiResponse;
import dnd.vention.payload.payloadForUrl.AlbumDto;
import dnd.vention.payload.payloadForUrl.AlbumDtoAddTracks;
import dnd.vention.service.AlbumService;
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

@WebServlet("/v1/api/secure/album/addTracks")
public class AddTracksToAlbumServlet extends HttpServlet {
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


        //create Album
        AlbumDtoAddTracks valuesFromRequest = getValuesFromRequest(req);
        ApiResponse<Album> albumApiResponse = AlbumService.addTracksToAlbum(valuesFromRequest);
        writer.write(albumApiResponse.toString());

    }
    public AlbumDtoAddTracks getValuesFromRequest(HttpServletRequest request) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        AlbumDtoAddTracks albumDto= objectMapper.readValue(requestBody.toString(), AlbumDtoAddTracks.class);
        return albumDto;
    }


}
