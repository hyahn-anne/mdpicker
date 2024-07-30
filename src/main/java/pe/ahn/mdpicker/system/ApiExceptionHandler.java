package pe.ahn.mdpicker.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.ahn.mdpicker.model.response.ErrorCode;
import pe.ahn.mdpicker.model.response.ErrorResponse;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getErrorCode(), e.getMessage()),
                e.getErrorCode().getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return new ResponseEntity<>(
                new ErrorResponse(ErrorCode.INTER_SERVER_ERROR, e.getMessage()),
                ErrorCode.INTER_SERVER_ERROR.getStatusCode());
    }
}
