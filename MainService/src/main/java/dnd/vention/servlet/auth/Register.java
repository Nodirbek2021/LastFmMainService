package dnd.vention.servlet.auth;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dnd.vention.filter.TokenGenerator;
import dnd.vention.payload.ApiResponse;
import dnd.vention.payload.User;
import dnd.vention.service.UserService;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/v1/api/register")
public class Register extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User getValuesFromRequest = getValuesFromRequest(req);
        PrintWriter writer = resp.getWriter();

        ApiResponse apiResponseCheckUsernameExistence = UserService.checkUsernameExistence(getValuesFromRequest.getUsername());
        if (apiResponseCheckUsernameExistence.isSucces()) {
            User user = UserService.registerUser(getValuesFromRequest);
//            String token = TokenGenerator.generateBase64Token(user.getUsername(),user.getPassword());
            String token = TokenGenerator.generateToken(user.getUsername(), user.getPassword(), user.getRole().getId());
            resp.setHeader("Authorization",token);
            writer.write("Token: "+ token);
        }else {
            writer.write(apiResponseCheckUsernameExistence.getMessage());
        }



    }

    private User getValuesFromRequest(HttpServletRequest request) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        User user = objectMapper.readValue(requestBody.toString(), User.class);
        String username = user.getUsername();
        String password = user.getPassword();
        String firstname = user.getFirstname();
        String lastname = user.getLastname();


        return user;
    }
}
