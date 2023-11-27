package dnd.vention.servlet.userCrud;

import dnd.vention.payload.ApiResponse;
import dnd.vention.payload.User;
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

@WebServlet("/v1/api/secured/user/blockUser")
public class BlockUserServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String headerToken = req.getHeader("Authorization");
        ApiResponse<?> apiResponse = UserCheckService.checkForAdminRole(headerToken);
        PrintWriter writer= resp.getWriter();
        if (!apiResponse.isSucces()){
            writer.write(apiResponse.toString());
            return;
        }
        String userId = req.getParameter("userId");
        String isBlocked = req.getParameter("isBlocked");
        ApiResponse<User> userApiResponse = UserService.blockUser(userId, isBlocked);
        writer.write(userApiResponse.toString());
    }
}
