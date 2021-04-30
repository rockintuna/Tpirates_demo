package me.rockintuna.tpiratesdemo.config;

import me.rockintuna.tpiratesdemo.config.exceptions.BusinessTimeInvalidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalController {

    @ExceptionHandler
    public ResponseEntity<?> businessTimeInvalidExceptionHandler(BusinessTimeInvalidException exception) {
        return ResponseEntity.badRequest().body("시간을 제대로 입력해주세요.");
    }

}
