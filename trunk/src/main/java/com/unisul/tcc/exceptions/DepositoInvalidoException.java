package com.unisul.tcc.exceptions;

public class DepositoInvalidoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DepositoInvalidoException() {
		
	}
	
	public DepositoInvalidoException(String mensagem) {
		super(mensagem);
	}
}
