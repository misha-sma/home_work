package homework.ru.aston.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultAdvice {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
	}
}