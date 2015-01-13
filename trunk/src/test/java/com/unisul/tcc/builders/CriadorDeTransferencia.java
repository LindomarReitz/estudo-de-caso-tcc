package com.unisul.tcc.builders;

import java.util.Calendar;

import com.unisul.tcc.beans.Conta;
import com.unisul.tcc.beans.Transferencia;

public class CriadorDeTransferencia {
	private Long id;
	private Double valor;
	private Calendar data;
	private Conta contaOrigem;
	private Conta contaDestino;
	
	public CriadorDeTransferencia() {
		
	}
	
	public CriadorDeTransferencia daConta(Conta contaOrigem) {
		this.contaOrigem = contaOrigem;
		return this;
	}
	
	public CriadorDeTransferencia paraAConta(Conta contaDestino) {
		this.contaDestino = contaDestino;
		return this;
	}
	
	public CriadorDeTransferencia comValorDe(Double valor) {
		this.valor = valor;
		return this;
	}
	
	public CriadorDeTransferencia naDataDeHoje() {
		this.data = Calendar.getInstance();
		return this;
	}
	
	public CriadorDeTransferencia naDataFutura() {
		Calendar dataFutura = Calendar.getInstance();
		dataFutura.add(Calendar.DATE, 1);
		
		this.data = dataFutura;
		return this;
	}
	
	public CriadorDeTransferencia comId(Long id) {
		this.id = id;
		return this;
	}
	
	public Transferencia construir() {
		Transferencia transferencia = new Transferencia(id, valor, data, contaOrigem, contaDestino);
		
		return transferencia;
	}
}
