package capstone.placer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoginFailedException extends RuntimeException {

    public LoginFailedException(String msg) {
        super(msg);
    }
}
