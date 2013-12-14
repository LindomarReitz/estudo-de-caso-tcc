package com.unisul.tcc.beans;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import com.unisul.tcc.exceptions.DataInvalidaException;
import com.unisul.tcc.exceptions.SaldoInsuficienteException;
import com.unisul.tcc.exceptions.SaqueInvalidoException;
import com.unisul.tcc.exceptions.TransferenciaInvalidaException;

public class TransferenciaTest {
	private Conta contaOrigem, contaDestino;
	private Transferencia transferencia;
	
	@Before
	public void setUp() {
		Banco banco = new Banco();
		banco.setNome("Banco do Brasil");
		
		contaOrigem = new Conta();
		contaOrigem.setNome("Conta corrente");
		contaOrigem.setNumeroAgencia(1234);
		contaOrigem.setNumeroConta(123456789);
		contaOrigem.setBanco(banco);
		
		contaDestino = new Conta();
		contaDestino.setNome("Conta corrente");
		contaDestino.setNumeroAgencia(1235);
		contaDestino.setNumeroConta(123456899);
		contaDestino.setBanco(banco);
	
		transferencia = new Transferencia();
		transferencia.setContaOrigem(contaOrigem);
		transferencia.setContaDestino(contaDestino);
		transferencia.setDataTransferencia(Calendar.getInstance());
	}
	
	@Test
	public void deveRealizarTransferencia() {
		contaOrigem.setSaldoAtual(2500d);
		contaDestino.setSaldoAtual(3500d);
		
		transferencia.setValor(500d);
		
		double saldoEsperadoContaOrigem = 2000d;
		double saldoEsperadoContaDestino = 4000d;
		
		transferencia.transferir(contaOrigem, contaDestino);
		
		assertEquals(saldoEsperadoContaOrigem, contaOrigem.getSaldoAtual());
		assertEquals(saldoEsperadoContaDestino, contaDestino.getSaldoAtual());
	}
	
	@Test(expected = SaldoInsuficienteException.class)
	public void naoDeveRealizarTransferenciaComValorMaiorQueOSaldo() {
		contaOrigem.setSaldoAtual(3850d);
		contaDestino.setSaldoAtual(5000d);
		
		transferencia.setValor(3851d);
		
		transferencia.transferir(contaOrigem, contaDestino);
	}
	
	@Test(expected = SaqueInvalidoException.class)
	public void naoDeveRealizarTransferenciaComValorNegativo() {
		contaOrigem.setSaldoAtual(3500d);
		contaDestino.setSaldoAtual(1500d);
		
		transferencia.setValor(-15d);
		
		transferencia.transferir(contaOrigem, contaDestino);
	}
	
	@Test
	public void deveRealizarTransferenciaComValorTotalDaConta() {
		contaOrigem.setSaldoAtual(5000d);
		contaDestino.setSaldoAtual(3000d);
		
		transferencia.setValor(5000d);
		
		transferencia.transferir(contaOrigem, contaDestino);
		
		double saldoEsperadoContaOrigem = 0d;
		double saldoEsperadoContaDestino = 8000d;
		
		assertEquals(saldoEsperadoContaOrigem, contaOrigem.getSaldoAtual());
		assertEquals(saldoEsperadoContaDestino, contaDestino.getSaldoAtual());
	}
	
	@Test(expected = SaqueInvalidoException.class)
	public void naoDeveRealizarTransferenciaComValorZero() {
		contaOrigem.setSaldoAtual(500d);
		contaDestino.setSaldoAtual(1000d);
		
		transferencia.setValor(0d);
		
		transferencia.transferir(contaOrigem, contaDestino);
	}
	
	@Test(expected = RuntimeException.class)
	public void naoDeveRealizarTransferenciaSemContaOrigem() {
		contaDestino.setSaldoAtual(3500d);
		
		transferencia.setValor(350d);
		
		transferencia.transferir(null, contaDestino);
	}
	
	@Test(expected = RuntimeException.class)
	public void naoDeveRealizarTransferenciaSemContaDestino() {
		contaOrigem.setSaldoAtual(3500d);
		
		transferencia.setValor(350d);
		
		transferencia.transferir(contaOrigem, null);
	}
	
	@Test(expected = TransferenciaInvalidaException.class)
	public void naoDeveRealizarTransferenciaComMesmaContaDeOrigemEDestino() {
		contaOrigem.setSaldoAtual(500d);
		
		transferencia.setValor(250d);
		
		transferencia.transferir(contaOrigem, contaOrigem);
	}
	
	@Test(expected = DataInvalidaException.class)
	public void naoDeveRealizarTransferenciaComDataNoFuturo() {
		contaOrigem.setSaldoAtual(5600d);
		contaDestino.setSaldoAtual(3500d);

		Calendar dataFutura = transferencia.getDataTransferencia();
		dataFutura.add(Calendar.DAY_OF_MONTH, 2);
		
		transferencia.setDataTransferencia(dataFutura);
		transferencia.setValor(500d);
		
		transferencia.transferir(contaOrigem, contaDestino);
	}
}
