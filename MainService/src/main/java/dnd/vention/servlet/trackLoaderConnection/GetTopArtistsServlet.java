package dnd.vention.servlet.trackLoaderConnection;

import com.google.gson.Gson;
import dnd.vention.filter.payload.TokenInfo;
import dnd.vention.payload.ApiResponse;
import dnd.vention.service.UserCheckService;
import dnd.vention.service.trackLoader.service.GetTopArtistsService;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/v1/api/secured/trackLoader/getTopArtists")
public class GetTopArtistsServlet extends HttpServlet {
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
        ApiResponse<?> apiResponse1 = UserCheckService.checkForAdminRole(headerToken);
        if (!apiResponse1.isSucces()) {
            writer.write(apiResponse1.toString());
            return;
        }
        String page = req.getParameter("page");
        String artistsFromDBAsJSon = GetTopArtistsService.getArtistsFromDBAsJSon(page);
        ApiResponse apiResponse=new ApiResponse(true,"tracks",artistsFromDBAsJSon);
        Gson gson=new Gson();
        String s = gson.toJson(apiResponse);
        writer.write(s);
    }
}
