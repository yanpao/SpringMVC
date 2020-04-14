package com.yanpao.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 建立全局的Exception处理机制
 * 日后研究 2020-3-18
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<String> handle(ArithmeticException ex) {
        ResponseEntity<String> ssss= new ResponseEntity<String>(HttpStatus.LOCKED);
        return ssss;
    }

}
