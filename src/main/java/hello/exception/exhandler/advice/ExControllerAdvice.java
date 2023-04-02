package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 여러 컨트롤러에서 발생한 예외들을 여기서 처리
 */
@Slf4j
@RestControllerAdvice //@controllerAdvice + @responsebody
public class ExControllerAdvice {
    /**
     * 이 컨트롤러안에서 IllegalArgumentExepction이 터지면
     * 얘가 잡고 로직이 호출, restcontroller라서 그대로 JSON으로 반환이된다.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)//정상 흐름으로 바껴서 status가 200으로 반환되는데, 200이 아니라 400으로 바꾸고 싶음
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e){
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e){
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity(errorResult,HttpStatus.BAD_REQUEST);
    }

    /**
     * 위에 선언한 거는 Illegal, UserException은 그 자식 예외까지 처리
     * 그 외의 더 넓은 범위는 여기서 처리 RuntimeExcepion 같은 Rundtime의 부모는 Exception임
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e){
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX","내부 오류");
    }
}
