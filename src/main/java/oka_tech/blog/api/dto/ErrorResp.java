package oka_tech.blog.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResp {
    String errorCode;

    String message;


    public ErrorResp(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
