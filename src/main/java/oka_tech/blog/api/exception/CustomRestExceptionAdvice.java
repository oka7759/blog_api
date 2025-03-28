package oka_tech.blog.api.exception;

import lombok.extern.slf4j.Slf4j;
import oka_tech.blog.api.dto.ErrorResp;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomRestExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResp> handleCustomException(CustomException ex) {
        log.error("CustomException: {}, Code: {}", ex.getMessage(), ex.getCode());
        ErrorResp response = new ErrorResp(ex.getCode(), ex.getReason());
        log.debug("Response Body: {}", response); // 디버깅 로그 추가
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(response, headers, ex.getStatusCode());
    }
}
