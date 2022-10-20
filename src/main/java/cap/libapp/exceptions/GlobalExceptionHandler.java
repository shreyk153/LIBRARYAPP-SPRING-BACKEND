package cap.libapp.exceptions;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cap.libapp.util.Constants;
import cap.libapp.util.ErrorInfo;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice @Slf4j
public class GlobalExceptionHandler 
{
	/* Global Exception Handler class for Library app.
	 * Author - Shrey.
	 */
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ErrorInfo handleException(MethodArgumentNotValidException ie,HttpServletRequest req)
	{
		log.info(Constants.HE);
		Map<String, String> err = new LinkedHashMap<>();
		ie.getBindingResult().getAllErrors().forEach(e->
		{
			String ef = ((FieldError)e).getField();
			String ec = e.getDefaultMessage();
			err.put(ef, ec);
		});
		
		return new ErrorInfo(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,err.toString(),
				req.getRequestURI(),req.getMethod());
	}
	
	//To handle the MethodArgumentNotValidException.
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({MemberAlreadyExistsException.class})
	public ErrorInfo handleException(MemberAlreadyExistsException me,HttpServletRequest req)
	{
		log.info(Constants.HE);
		return new ErrorInfo(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,me.getMessage(),
				req.getRequestURI(),req.getMethod());
	}
	//To handle the MemberAlreadyExistsException
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({MemberNotFoundException.class})
	public ErrorInfo handleException(MemberNotFoundException mnf,HttpServletRequest req)
	{
		log.info(Constants.HE);
		return new ErrorInfo(HttpStatus.NO_CONTENT.value(),HttpStatus.NO_CONTENT,mnf.getMessage(),
				req.getRequestURI(),req.getMethod());
	}
	//To handle the MemberNotFoundException
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({NoBookFoundException.class})
	public ErrorInfo handleException(NoBookFoundException nbf,HttpServletRequest req)
	{
		log.info(Constants.HE);
		return new ErrorInfo(HttpStatus.NO_CONTENT.value(),HttpStatus.NO_CONTENT,nbf.getMessage(),
				req.getRequestURI(),req.getMethod());
	}
	//To handle the NoBookFoundException
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({BadRangeException.class})
	public ErrorInfo handleException(BadRangeException br,HttpServletRequest req)
	{
		log.info(Constants.HE);
		return new ErrorInfo(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,br.getMessage(),
				req.getRequestURI(),req.getMethod());
	}
	//To handle the BadRangeException
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({WrongPasswordException.class})
	public ErrorInfo handleException(WrongPasswordException br,HttpServletRequest req)
	{
		log.info(Constants.HE);
		return new ErrorInfo(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,br.getMessage(),
				req.getRequestURI(),req.getMethod());
	}
	//To handle the WrongPasswordException
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({BorrowingNotFoundException.class})
	public ErrorInfo handleException(BorrowingNotFoundException br,HttpServletRequest req)
	{
		log.info(Constants.HE);
		return new ErrorInfo(HttpStatus.NO_CONTENT.value(),HttpStatus.NO_CONTENT,br.getMessage(),
				req.getRequestURI(),req.getMethod());
	}
	//To handle the BorrowingNotFoundException
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({NoBooksAvailableException.class})
	public ErrorInfo handleException(NoBooksAvailableException br,HttpServletRequest req)
	{
		log.info(Constants.HE);
		return new ErrorInfo(HttpStatus.NO_CONTENT.value(),HttpStatus.NO_CONTENT,br.getMessage(),
				req.getRequestURI(),req.getMethod());
	}
	//To handle the NoBooksAvailableException
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({AlreadyBorrowedException.class})
	public ErrorInfo handleException(AlreadyBorrowedException br,HttpServletRequest req)
	{
		log.info(Constants.HE);
		return new ErrorInfo(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,br.getMessage(),
				req.getRequestURI(),req.getMethod());
	}
	//To handle the AlreadyBorrowedException
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({Exception.class})
	public ErrorInfo handleException(Exception e,HttpServletRequest req)
	{
		log.info(Constants.HE);
		return new ErrorInfo(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,e.getMessage(),
				req.getRequestURI(),req.getMethod());
	}
	//To handle any general Exception
}
