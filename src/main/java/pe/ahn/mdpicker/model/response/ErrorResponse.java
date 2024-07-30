package pe.ahn.mdpicker.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse extends CommonResponse{
    public ErrorResponse(ErrorCode errorCode, String message) {
        super(errorCode.getStatusCode().value(), message);
    }
}
