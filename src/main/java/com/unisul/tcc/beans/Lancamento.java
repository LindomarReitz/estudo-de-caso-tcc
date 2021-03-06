package com.unisul.tcc.beans;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.unisul.tcc.exceptions.DataInvalidaException;
import com.unisul.tcc.exceptions.DepositoInvalidoException;
import com.unisul.tcc.exceptions.SaldoInsuficienteException;
import com.unisul.tcc.exceptions.SaqueInvalidoException;

@Entity
@Table(name = "lancamentos")
public class Lancamento {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	private String descricao;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_lancamento")
	private Calendar data;

	private Double valor;

	private String observacao;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_lancamento")
	private TipoLancamento tipoLancamento;

	@ManyToOne
	@JoinColumn(name = "id_conta")
	private Conta conta;
	
	@ManyToOne
	@JoinColumn(name = "id_transferencia")
	private Transferencia transferencia;

	public Lancamento(Long id, String descricao, Calendar data, Double valor,
			String observacao, TipoLancamento tipoLancamento, Conta conta) {
		this.id = id;
		this.descricao = descricao;
		this.data = data;
		this.valor = valor;
		this.observacao = observacao;
		this.tipoLancamento = tipoLancamento;
		this.conta = conta;
	}

	public Lancamento() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public TipoLancamento getTipoLancamento() {
		return tipoLancamento;
	}

	public void setTipoLancamento(TipoLancamento tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Transferencia getTransferencia() {
		return transferencia;
	}

	public void setTransferencia(Transferencia transferencia) {
		this.transferencia = transferencia;
	}
	
	public void lancar() {
		if (this.data.after(Calendar.getInstance())) {
			throw new DataInvalidaException(
					"Data do lançamento não pode ser no futuro!");
		}

		conta.calcularSaldoAtual();
		
		if (tipoLancamento.equals(TipoLancamento.SAQUE)) {
			sacar();
		} else if (tipoLancamento.equals(TipoLancamento.DEPOSITO)) {
			depositar();
		}
		
		conta.calcularSaldoAtual();
	}

	private void sacar() {
		if (valor <= 0) {
			throw new SaqueInvalidoException(
					"Saque não pode ser feito com valores menores ou iguais a zero!");
		}

		if (valor > conta.getSaldoAtual()) {
			throw new SaldoInsuficienteException(
					"Não se pode efetuar um saque com o valor maior que o saldo!");
		}
		
		conta.getLancamentos().add(this);
	}

	private void depositar() {
		if (valor <= 0) {
			throw new DepositoInvalidoException(
					"Depósito não pode ser feito com valores menores ou iguais a zero!");
		}
		
		conta.getLancamentos().add(this);
	}
}
