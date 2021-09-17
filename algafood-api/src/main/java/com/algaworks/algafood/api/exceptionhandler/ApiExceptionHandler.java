package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntityNotDeleteException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	public static final String MSG_USER_GENERIC = "Ocorreu um erro inesperado. Tente novamente e caso o problema persista contacte o administrador do sistema.";
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handlerEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		var problem = createProblemBuilder(status, ProblemType.RESOURCE_NOT_FOUND, ex.getMessage()).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handlerNegocioException(NegocioException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		var problem = createProblemBuilder(status, ProblemType.ERROR_BUSINESS, ex.getMessage()).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntityNotDeleteException.class)
	public ResponseEntity<?> handlerEntityNotDeleteException(EntityNotDeleteException ex, WebRequest request) {
		var status = HttpStatus.CONFLICT;
		var problem = createProblemBuilder(status, ProblemType.ENTITY_NOT_DELETE, ex.getMessage()).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(ValidatorException.class)
	public ResponseEntity<?> handleValidatorException(ValidatorException e, WebRequest webRequest){
		return handleValidateInternal(e, e.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
	}
	
	/**
	 * Tratamento para qualquer exception não tratada
	 */
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleUncaught(Exception ex, WebRequest request) {
		var status = HttpStatus.INTERNAL_SERVER_ERROR;
		var problem = createProblemBuilder(status, ProblemType.SYSTEM_ERROR, ex.getMessage()).build();
	    ex.printStackTrace();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	/**
	 * Quando user passa tipo de dado incorreto e 
	 * quando passa propriedade ignoradas ou desconhecidas no payload
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}else if(rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		}
		
		var problem = createProblemBuilder(status, ProblemType.PAYLOAD_INCOMPREHENSIBLE, "Payload is not valid. Verify possible errors of sintaxe")
				.build();//message exposes details internal application
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	/**
	 * Quando o user passa um tipo de dado diferente do esperado na URI. Ex: desenvolvimento:8080/restaurantes/a
	 */
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) ex, new HttpHeaders(), status, request);
		}

		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders httpHeaders, HttpStatus status, WebRequest request) {
		
		var detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s'que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.", ex.getName(),  ex.getValue(), ex.getRequiredType().getSimpleName());
		var problem = createProblemBuilder(status, ProblemType.INVALID_PARAMETER, detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var fieldsUnknow = joinPath(ex.getPath());
		var detail = "";
		
		if (ex instanceof UnrecognizedPropertyException) {
			detail = String.format("The propertie '%s' is unknow", fieldsUnknow);
		}else if (ex instanceof IgnoredPropertyException) {
			detail = String.format("The propertie '%s' is ignored", fieldsUnknow);
		}
		var problem = createProblemBuilder(status, ProblemType.PROPERTY_NOT_VALID, detail).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException rootCause, HttpHeaders headers, HttpStatus status, WebRequest request) {

		var fieldName = joinPath(rootCause.getPath());
		var value = (String) rootCause.getValue();
		var property = rootCause.getTargetType().getSimpleName();
		
		var detail = String.format("The propertie '%s' was assign '%s' that is not valid. Correct and inform a valid value with type '%s' ", fieldName, value, property);
		var problem = createProblemBuilder(status, ProblemType.PAYLOAD_INCOMPREHENSIBLE, detail).userMessage(MSG_USER_GENERIC).build();
		
		return handleExceptionInternal(rootCause, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleValidateInternal(ex, ex.getBindingResult(), headers, status, request);
	}
	
	/**
	 * Criado para resolver o problema de lançar: org.springframework.web.HttpMediaTypeNotAcceptableException: Could not find acceptable representation
	 * quando o consumir informar um accept diferente da foto cadastrada
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}

	/**
	 * Método que todas execptions chamam no fim
	 */
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (Objects.isNull(body)) {
			var message = status.getReasonPhrase();
			body = Problem.builder().timestamp(OffsetDateTime.now()).status(status.value()).title(message).userMessage(MSG_USER_GENERIC).build();
		} else if(body instanceof String) {
			body = Problem.builder().timestamp(OffsetDateTime.now()).status(status.value()).title((String) body).userMessage(MSG_USER_GENERIC).build();
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	/**
	 * Realiza tratamento dos campos obrigatórios
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleValidateInternal(ex, ex.getBindingResult(), headers, status, request);
	}

	private ResponseEntity<Object> handleValidateInternal(Exception ex, BindingResult bindingResult,HttpHeaders headers, HttpStatus status, WebRequest request) {
	    List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
	    		.map(objectError -> {  
	    			var message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
	    			var name = objectError.getObjectName();
	    			
	    			if (objectError instanceof FieldError) {
						name = ((FieldError) objectError).getField();
					}
	    			
	    			return Problem.Object.builder()
	    				.name(name)
	    				.userMessage(message)
	    				.build();
	    		})
	    		.collect(Collectors.toList());
		
	    var problem = createProblemBuilder(status, ProblemType.INVALID_DATAS, ProblemType.INVALID_DATAS.getTitle()).objects(problemObjects).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	/**
	 * Quando user tentar acessar recurso inexistente. Ex: desenvolvimento:8080/restauraaaaaaaaaaaaaantes/1
	 */
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var problemType = ProblemType.RESOURCE_NOT_FOUND;
		var detail = String.format("O resource '%s', that you try access is nonexistent", ex.getRequestURL());
		
		var problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail){
		return Problem.builder().status(status.value()).type(problemType.getType()).title(problemType.getTitle()).detail(detail).timestamp(OffsetDateTime.now());
	}
	
	private String joinPath(List<Reference> references) {
	    return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	} 
	
	
	
	
	
	
}
