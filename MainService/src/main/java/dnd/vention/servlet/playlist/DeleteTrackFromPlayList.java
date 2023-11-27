package dnd.vention.servlet.playlist;


import dnd.vention.filter.payload.TokenInfo;
import dnd.vention.payload.ApiResponse;
import dnd.vention.payload.Track;
import dnd.vention.service.PlaylistTrackService;
import dnd.vention.service.UserCheckService;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/v1/api/secure/playlist/deleteTrackFromPlaylist")
public class DeleteTrackFromPlayList extends HttpServlet {
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
        //delete track
        String playlistId = req.getParameter("playlistId");
        String trackId = req.getParameter("trackId");
        ApiResponse<List<Track>> listApiResponse = PlaylistTrackService.deleteTrackFromPlaylist(playlistId, trackId);
        writer.write(listApiResponse.toString());
    }
}
