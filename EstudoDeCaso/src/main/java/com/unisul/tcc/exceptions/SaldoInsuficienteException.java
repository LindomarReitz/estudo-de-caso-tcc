package com.unisul.tcc.exceptions;

public class SaldoInsuficienteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SaldoInsuficienteException() {
		
	}

	public SaldoInsuficienteException(String mensagem) {
		super(mensagem);
	}
}
