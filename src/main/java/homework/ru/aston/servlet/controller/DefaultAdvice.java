package homework.ru.aston.servlet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultAdvice {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
//		Response response = new Response(e.getMessage());
		System.out.println("qweqwe qeqweqweqweqw");
		return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
	}   
}