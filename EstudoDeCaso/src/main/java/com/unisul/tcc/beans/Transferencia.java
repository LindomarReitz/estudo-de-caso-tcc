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
import com.unisul.tcc.exceptions.SaldoInsuficienteException;
import com.unisul.tcc.exceptions.SaqueInvalidoException;
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
	private Calendar data;

	@ManyToOne
	@JoinColumn(name = "id_conta_origem")
	private Conta contaOrigem;

	@ManyToOne
	@JoinColumn(name = "id_conta_destino")
	private Conta contaDestino;

	public Transferencia(Long id, Double valor, Calendar data,
			Conta contaOrigem, Conta contaDestino) {
		this.id = id;
		this.valor = valor;
		this.data = data;
		this.contaOrigem = contaOrigem;
		this.contaDestino = contaDestino;
	}
	
	public Transferencia() {
		
	}
	
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

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
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

	public void transferir() {
		if (contaOrigem == null || contaDestino == null) {
			throw new RuntimeException(
					"Conta origem/destino não pode ser nula!");
		}

		if (contaOrigem.equals(contaDestino)) {
			throw new TransferenciaInvalidaException(
					"Conta origem e destino não podem ser iguais!");
		}

		if (this.data.after(Calendar.getInstance())) {
			throw new DataInvalidaException(
					"Data de transferência não pode ser no futuro!");
		}

		if (valor > contaOrigem.getSaldoAtual()) {
			throw new SaldoInsuficienteException(
					"Não se pode transferir com valor acima do saldo da conta origem!");
		}
		
		if (valor <= 0) {
			throw new SaqueInvalidoException(
					"Transferência não pode ser realizada com valores menores ou iguais a zero!");
		}
		
		Lancamento lancamentoContaOrigem = new Lancamento();
		lancamentoContaOrigem.setConta(contaOrigem);
		lancamentoContaOrigem.setDescricao("Transferência");
		lancamentoContaOrigem.setValor(valor);
		lancamentoContaOrigem.setData(data);
		lancamentoContaOrigem.setObservacao("");
		lancamentoContaOrigem.setTipoLancamento(TipoLancamento.SAQUE);
		lancamentoContaOrigem.setTransferencia(this);
		
		lancamentoContaOrigem.lancar();
		
		Lancamento lancamentoContaDestino = new Lancamento();
		lancamentoContaDestino.setConta(contaDestino);
		lancamentoContaDestino.setDescricao("Transferência");
		lancamentoContaDestino.setValor(valor);
		lancamentoContaDestino.setData(data);
		lancamentoContaDestino.setObservacao("");
		lancamentoContaDestino.setTipoLancamento(TipoLancamento.DEPOSITO);
		lancamentoContaDestino.setTransferencia(this);
		
		lancamentoContaDestino.lancar();
	}
}