package pe.ahn.mdpicker.system;

import lombok.Getter;
import pe.ahn.mdpicker.model.response.ErrorCode;

@Getter
public class ApiException extends RuntimeException {
    private final ErrorCode errorCode;

    public ApiException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
