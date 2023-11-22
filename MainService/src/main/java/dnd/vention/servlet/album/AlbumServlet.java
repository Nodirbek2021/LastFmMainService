package dnd.vention.servlet.album;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dnd.vention.filter.payload.TokenInfo;
import dnd.vention.payload.Album;
import dnd.vention.payload.ApiResponse;
import dnd.vention.payload.Playlist;
import dnd.vention.payload.payloadForUrl.AlbumDto;
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

@WebServlet("/v1/api/secure/album")
public class AlbumServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        //Get by Id
        String id = req.getParameter("albumId");
        ApiResponse<Album> albumById = AlbumService.getAlbumById(id);

        writer.write(albumById.toString());
    }
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
        //create
        AlbumDto valuesFromRequest = getValuesFromRequest(req);
        ApiResponse<Album> album = AlbumService.createAlbum(valuesFromRequest);
        writer.write(album.toString());
    }

    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        //update
        String albumId = req.getParameter("albumId");
        AlbumDto valuesFromRequest = getValuesFromRequest(req);
        ApiResponse<Album> albumApiResponse = AlbumService.updateAlbum(albumId, valuesFromRequest);
        writer.write(albumApiResponse.toString());

    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        //delete
        String albumId = req.getParameter("albumId");
        ApiResponse apiResponse = AlbumService.deleteAlbum(albumId);
        writer.write(apiResponse.toString());
    }

    public AlbumDto getValuesFromRequest(HttpServletRequest request) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        AlbumDto albumDto= objectMapper.readValue(requestBody.toString(), AlbumDto.class);
        return albumDto;
    }
}
