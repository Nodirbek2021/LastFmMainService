package dnd.vention.servlet.playlist;

import com.google.gson.Gson;
import dnd.vention.filter.TokenGenerator;
import dnd.vention.filter.payload.TokenInfo;
import dnd.vention.payload.ApiResponse;
import dnd.vention.payload.Playlist;
import dnd.vention.service.PlaylistService;
import dnd.vention.service.UserCheckService;
import dnd.vention.service.UserService;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/v1/api/secure/playlist/getAllPlaylist")
public class GetAllPlaylistsServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        String headerToken = req.getHeader("Authorization");
        TokenInfo tokenInfo = UserCheckService.tokenDecoder(headerToken);
        boolean userTokenExpiration = UserCheckService.checkUserTokenExpiration(tokenInfo);
        if (!userTokenExpiration){
            ApiResponse<Object> objectApiResponse = new ApiResponse<>(false, "Token Expired!");
            writer.write(objectApiResponse.toString());
            return;
        }


        List<Playlist> allPlaylists = PlaylistService.getAllPlaylists();
        Gson gson =new Gson();
//        String stringJson = gson.toJson(allPlaylists);
//        resp.setContentType("application/json");
        writer.write(allPlaylists.toString());
    }


}
