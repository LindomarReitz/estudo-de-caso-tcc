package com.unisul.tcc.exceptions;

public class SaqueInvalidoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SaqueInvalidoException() {
		
	}
	
	public SaqueInvalidoException(String mensagem) {
		super(mensagem);
	}
}
