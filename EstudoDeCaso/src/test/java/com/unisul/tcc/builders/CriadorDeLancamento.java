package com.unisul.tcc.builders;

import java.util.Calendar;

import com.unisul.tcc.beans.Conta;
import com.unisul.tcc.beans.Lancamento;
import com.unisul.tcc.beans.TipoLancamento;

public class CriadorDeLancamento {
	private Long id;
	private Conta conta;
	private String descricao;
	private double valor;
	private String observacao;
	private Calendar data;
	private TipoLancamento tipoLancamento;
	
	public CriadorDeLancamento paraAConta(Conta conta) {
		this.conta = conta;
		return this;
	}
	
	public CriadorDeLancamento noValorDe(Double valor) {
		this.valor = valor;
		return this;
	}
	
	public CriadorDeLancamento doTipo(TipoLancamento tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
		return this;
	}
	
	public CriadorDeLancamento naDataDe(Calendar data) {
		this.data = data;
		return this;
	}
	
	public CriadorDeLancamento comADescricao(String descricao) {
		this.descricao = descricao;
		return this;
	}
	
	public CriadorDeLancamento comAObservacao(String observacao) {
		this.observacao = observacao;
		return this;
	}
	
	public CriadorDeLancamento comId(Long id) {
		this.id = id;
		return this;
	}
	
	public Lancamento construir() {
		Lancamento lancamento = new Lancamento(id, descricao, data, valor, observacao, tipoLancamento, conta);
		
		return lancamento;
	}
}
