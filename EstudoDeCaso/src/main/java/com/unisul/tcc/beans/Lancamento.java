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
import javax.persistence.Transient;

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

	@Transient
	private TipoLancamento tipoLancamento;

	@ManyToOne
	@JoinColumn(name = "id_conta")
	private Conta conta;

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

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public Calendar getData() {
		return data;
	}

	public Double getValor() {
		return valor;
	}

	public String getObservacao() {
		return observacao;
	}

	public TipoLancamento getTipoLancamento() {
		return tipoLancamento;
	}

	public Conta getConta() {
		return conta;
	}

	public void realizarLancamento() {
		if (this.data.after(Calendar.getInstance())) {
			throw new DataInvalidaException(
					"Data do lançamento não pode ser no futuro");
		}

		if (tipoLancamento.equals(TipoLancamento.SAQUE)) {
			sacar(this.valor);
		} else if (tipoLancamento.equals(TipoLancamento.DEPOSITO)) {
			depositar(this.valor);
		}
	}

	private void sacar(double valor) {
		if (valor <= 0) {
			throw new SaqueInvalidoException(
					"Saque não pode ser feito com valores menores ou iguais a zero!");
		}

		if (valor > conta.getSaldoAtual()) {
			throw new SaldoInsuficienteException(
					"Não se pode efetuar um saque com o valor maior que o saldo!");
		}

		conta.setSaldoAtual(conta.getSaldoAtual() - valor);
	}

	public void depositar(double valor) {
		if (valor <= 0) {
			throw new DepositoInvalidoException(
					"Depósito não pode ser feito com valores menores ou iguais a zero!");
		}

		conta.setSaldoAtual(conta.getSaldoAtual() + valor);
	}
}
