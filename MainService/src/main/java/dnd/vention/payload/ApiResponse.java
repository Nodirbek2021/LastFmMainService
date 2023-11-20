package dnd.vention.payload;


import dnd.vention.exception.MainServiceException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse<T> implements Serializable {
    private boolean succes;
    private String message;
    private T data;
    private MainServiceException response;

    public ApiResponse(boolean succes, String message, T data) {
        this.succes = succes;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(boolean succes, String message) {
        this.succes = succes;
        this.message = message;
    }

    public ApiResponse(boolean succes, String message, MainServiceException response) {
        this.succes = succes;
        this.message = message;
        this.response = response;
    }

}
