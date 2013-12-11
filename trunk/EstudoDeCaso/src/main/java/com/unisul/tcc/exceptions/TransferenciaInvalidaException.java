package com.unisul.tcc.exceptions;

public class TransferenciaInvalidaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TransferenciaInvalidaException() {
		
	}
	
	public TransferenciaInvalidaException(String mensagem) {
		super(mensagem);
	}
}
