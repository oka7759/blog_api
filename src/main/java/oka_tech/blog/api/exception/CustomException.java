package oka_tech.blog.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class CustomException extends ResponseStatusException {
    private final String code;
    private final String message;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getHttpStatus(), errorCode.getMessage(), null, errorCode.getCode(), null);
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }


}
