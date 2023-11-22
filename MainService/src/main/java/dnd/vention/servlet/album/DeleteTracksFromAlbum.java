package dnd.vention.servlet.album;

import dnd.vention.payload.ApiResponse;
import dnd.vention.service.AlbumService;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/v1/api/secure/album/deleteTracks")
public class DeleteTracksFromAlbum extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String albumId = req.getParameter("albumId");
        String trackId = req.getParameter("trackId");

        ApiResponse<?> apiResponse = AlbumService.deleteTrackFromAlbum(Integer.parseInt(albumId), Integer.parseInt(trackId));
        PrintWriter writer = resp.getWriter();
        writer.write(apiResponse.toString());

    }
}
