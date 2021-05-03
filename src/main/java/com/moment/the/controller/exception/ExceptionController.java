package com.moment.the.controller.exception;

import com.moment.the.advice.exception.*;
import com.moment.the.domain.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {
    // userNotFoundException
    @GetMapping(value = "/userNotFound")
    public CommonResult userNotFoundException() {
        throw new UserNotFoundException();
    }

    @GetMapping(value = "/userAlreadyExists")
    public CommonResult userAlreadyExistsException() {
        throw new UserAlreadyExistsException();
    }

    @GetMapping(value = "/noPost")
    public CommonResult noPostException() {
        throw new NoPostException();
    }

    @GetMapping(value = "/noComment")
    public CommonResult noCommentException() {
        throw new NoCommentException();
    }

    @GetMapping(value = "/noImprovement")
    public CommonResult noImprovement() {
        throw new NoCommentException();
    }
  
    @GetMapping(value = "/method-argument-not-valid")
    public CommonResult methodArgumentNotValid() {
        throw new CustomMethodArgumentNotValidException();
    }

    @GetMapping(value = "/goods-not-cancel")
    public CommonResult goodsNotCancel(){
        throw new GoodsNotCancelException();
    }

    @GetMapping(value = "/access-token-expired")
    public CommonResult accessTokenExpired(){throw new AccessTokenExpiredException();}

    @GetMapping(value = "/invalid-token")
    public CommonResult invalidToken(){throw new InvalidTokenException();}
}
