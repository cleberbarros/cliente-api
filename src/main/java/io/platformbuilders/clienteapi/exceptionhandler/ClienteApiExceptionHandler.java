package io.platformbuilders.clienteapi.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ClienteApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	
	
	//Método para tratar exceções de atributos não existentes na classe da API
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String mensagemUsuario = messageSource.getMessage("mensagem.invalida",null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		
		List<Erro>erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	
	// Método para tratar informações invalidas, não coerentes com o Bean Validation
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro>erros = criarListaDeErros(ex.getBindingResult());  
		
		return handleExceptionInternal(ex,erros,headers, HttpStatus.BAD_REQUEST, request);
	}
	
	
	//Método para tratar a exceção ao tentar excluir informação do banco que já foi excluida  
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		
		String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado",null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		
		List<Erro>erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	
	
	private List<Erro> criarListaDeErros(BindingResult bindingResult){
		List<Erro>erros = new ArrayList<>();
		
		for(FieldError fieldError: bindingResult.getFieldErrors() ) {  
			
			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();
			erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		}
		
		return erros;
	}
	
	//CLASSE Interna para mensagem ao usuario e ao programador
	public class Erro {
		private String mensagemUsuario;
		private String mensagemProgramador;
		
		public Erro(String mensagemUsuario, String mensagemProgramador) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemProgramador = mensagemProgramador;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemProgramador;
		}
		
	}

}
