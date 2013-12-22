package com.unisul.tcc.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "contas")
public class Conta {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	private String nome;
	
	@Column(name = "numero_agencia")
	private int numeroAgencia;
	
	@Column(name = "numero_conta")
	private int numeroConta;
	
	@ManyToOne
	@JoinColumn(name = "id_banco")
	private Banco banco;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "contas_lancamentos",
			joinColumns = @JoinColumn(name = "id_conta"),
			inverseJoinColumns = @JoinColumn(name = "id_lancamento"))
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	
	@Column(name = "saldo_atual")
	private Double saldoAtual;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	public Conta(Long id, String nome, int numeroAgencia, int numeroConta, Banco banco,
			List<Lancamento> lancamentos, Double saldoAtual, Usuario usuario) {
		this.id = id;
		this.nome = nome;
		this.numeroAgencia = numeroAgencia;
		this.numeroConta = numeroConta;
		this.lancamentos = lancamentos;
		this.saldoAtual = saldoAtual;
		this.usuario = usuario;
	}

	public Conta() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(int numeroConta) {
		this.numeroConta = numeroConta;
	}

	public int getNumeroAgencia() {
		return numeroAgencia;
	}

	public void setNumeroAgencia(int numeroAgencia) {
		this.numeroAgencia = numeroAgencia;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public Double getSaldoAtual() {
		return saldoAtual;
	}

	public void setSaldoAtual(Double saldoAtual) {
		this.saldoAtual = saldoAtual;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void calcularSaldoAtual() {
		Double totalSaque = 0d;
		Double totalDeposito = 0d;
		
		for (Lancamento lancamento : lancamentos) {
			if (lancamento.getTipoLancamento().equals(TipoLancamento.SAQUE)) {
				totalSaque += lancamento.getValor();
			} else if (lancamento.getTipoLancamento().equals(TipoLancamento.DEPOSITO)) {
				totalDeposito += lancamento.getValor();
			}
		}
		
		saldoAtual = totalDeposito - totalSaque;
	}
}
