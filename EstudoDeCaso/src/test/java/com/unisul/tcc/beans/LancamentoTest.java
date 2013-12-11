package com.unisul.tcc.beans;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.unisul.tcc.exceptions.DataInvalidaException;
import com.unisul.tcc.exceptions.DepositoInvalidoException;
import com.unisul.tcc.exceptions.SaldoInsuficienteException;
import com.unisul.tcc.exceptions.SaqueInvalidoException;

public class LancamentoTest {
	private Conta conta;
	private Lancamento lancamento;
	
	@Before
	public void setUp() {
		conta = new Conta();
		conta.setNome("Conta corrente");
		conta.setNumeroAgencia(1234);
		conta.setNumeroConta(13456698);

		Banco banco = new Banco();
		banco.setNome("Santander");
		
		conta.setBanco(banco);

		lancamento = new Lancamento();
		lancamento.setConta(conta);
		lancamento.setData(Calendar.getInstance());
		lancamento.setDescricao("Compra no mercado");
		lancamento.setObservacao("Compra feita no mercado");
	}
	
	@Test
	public void deveRealizarUmSaque() {
		conta.setSaldoAtual(5000d);

		lancamento.setTipoLancamento(TipoLancamento.SAQUE);
		lancamento.setValor(500d);
		
		lancamento.realizarLancamento();
		
		double saldoAtualEsperado = 4500d;
		
		Assert.assertEquals(saldoAtualEsperado, conta.getSaldoAtual());
	}
	
	@Test(expected = SaqueInvalidoException.class)
	public void naoDeveRealizarUmSaqueComValorNegativo() {
		conta.setSaldoAtual(500d);
		
		lancamento.setTipoLancamento(TipoLancamento.SAQUE);
		lancamento.setValor(-6d);

		lancamento.realizarLancamento();		
	}
	
	@Test(expected = SaqueInvalidoException.class)
	public void naoDeveRealizarUmSaqueComValorZero() {
		conta.setSaldoAtual(5000d);

		lancamento.setTipoLancamento(TipoLancamento.SAQUE);
		lancamento.setValor(-6d);
		
		lancamento.realizarLancamento();
	}
	
	@Test
	public void deveRealizarSaqueComValorTotalDaConta() {
		conta.setSaldoAtual(5000d);
		
		lancamento.setTipoLancamento(TipoLancamento.SAQUE);
		lancamento.setValor(5000d);
		
		lancamento.realizarLancamento();
		
		double resultadoEsperado = 0;
		
		assertEquals(resultadoEsperado, conta.getSaldoAtual());
	}
	
	@Test
	public void deveRealizarUmDeposito() {
		conta.setSaldoAtual(5000d);
		
		lancamento.setTipoLancamento(TipoLancamento.DEPOSITO);
		lancamento.setValor(2500d);

		lancamento.realizarLancamento();
		
		double resultadoEsperado = 7500d;
		
		assertEquals(resultadoEsperado, conta.getSaldoAtual());
	}
	
	@Test(expected = SaldoInsuficienteException.class)
	public void naoDeveRealizarSaqueComValorMaiorQueOSaldo() {
		conta.setSaldoAtual(1500d);
		
		lancamento.setTipoLancamento(TipoLancamento.SAQUE);
		lancamento.setValor(1600d);
		
		lancamento.realizarLancamento();
	}
	
	@Test(expected = DepositoInvalidoException.class)
	public void naoDeveRealizarUmDepositoComValorNegativo() {
		conta.setSaldoAtual(500d);

		lancamento.setTipoLancamento(TipoLancamento.DEPOSITO);
		lancamento.setValor(-1d);

		lancamento.realizarLancamento();
	}
	
	@Test(expected = DepositoInvalidoException.class)
	public void naoDeveRealizarUmDepositoComValorZero() {
		conta.setSaldoAtual(5000d);
		
		lancamento.setTipoLancamento(TipoLancamento.DEPOSITO);
		lancamento.setValor(0d);
		
		lancamento.realizarLancamento();
	}
	
	@Test(expected = DataInvalidaException.class)
	public void naoDeveRealizarUmLancamentoComDataNoFuturo() {
		conta.setSaldoAtual(5000d);
		
		Calendar dataFutura = Calendar.getInstance();
		dataFutura.add(Calendar.DAY_OF_MONTH, 3);
		
		lancamento.setData(dataFutura);
		lancamento.setTipoLancamento(TipoLancamento.SAQUE);
		lancamento.setValor(500d);
		
		lancamento.realizarLancamento();
	}
}
