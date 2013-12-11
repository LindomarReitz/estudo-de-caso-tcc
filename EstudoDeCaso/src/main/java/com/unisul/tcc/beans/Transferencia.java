package com.unisul.tcc.beans;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.unisul.tcc.exceptions.DataInvalidaException;
import com.unisul.tcc.exceptions.TransferenciaInvalidaException;

@Entity
@Table(name = "transferencias")
public class Transferencia {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	private Double valor;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_transferencia")
	private Calendar dataTransferencia;
	
	@ManyToOne
	@JoinColumn(name = "id_conta_origem")
	private Conta contaOrigem;
	
	@ManyToOne
	@JoinColumn(name = "id_conta_destino")
	private Conta contaDestino;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Calendar getDataTransferencia() {
		return dataTransferencia;
	}

	public void setDataTransferencia(Calendar dataTransferencia) {
		this.dataTransferencia = dataTransferencia;
	}

	public Conta getContaOrigem() {
		return contaOrigem;
	}

	public void setContaOrigem(Conta contaOrigem) {
		this.contaOrigem = contaOrigem;
	}

	public Conta getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(Conta contaDestino) {
		this.contaDestino = contaDestino;
	}

	public void transferir(Conta origem, Conta destino) {
		if (contaOrigem == null || contaDestino == null) {
			throw new RuntimeException(
					"Conta origem/destino não pode ser nula!");
		}

		if (origem.equals(destino)) {
			throw new TransferenciaInvalidaException(
					"Conta origem e destino não podem ser iguais!");
		}

		if (this.dataTransferencia.after(Calendar.getInstance())) {
			throw new DataInvalidaException(
					"Data de transferência não pode ser no futuro!");
		}

		Lancamento saqueContaOrigem = new Lancamento();
		saqueContaOrigem.setDescricao("Saque");
		saqueContaOrigem.setData(Calendar.getInstance());
		saqueContaOrigem.setConta(contaOrigem);
		saqueContaOrigem.setValor(this.valor);
		saqueContaOrigem.setTipoLancamento(TipoLancamento.SAQUE);
		saqueContaOrigem.setObservacao("Transferência");

		saqueContaOrigem.realizarLancamento();

		Lancamento saqueContaDestino = new Lancamento();
		saqueContaDestino.setDescricao("Depósito");
		saqueContaDestino.setData(Calendar.getInstance());
		saqueContaDestino.setConta(contaDestino);
		saqueContaDestino.setValor(this.valor);
		saqueContaDestino.setTipoLancamento(TipoLancamento.DEPOSITO);
		saqueContaDestino.setObservacao("Transferência");

		saqueContaDestino.realizarLancamento();
	}
}
