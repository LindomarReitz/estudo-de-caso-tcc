package com.unisul.tcc.beans;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import com.unisul.tcc.builders.CriadorDeTransferencia;
import com.unisul.tcc.exceptions.DataInvalidaException;
import com.unisul.tcc.exceptions.SaldoInsuficienteException;
import com.unisul.tcc.exceptions.SaqueInvalidoException;
import com.unisul.tcc.exceptions.TransferenciaInvalidaException;

public class TransferenciaTest {
	private Conta contaOrigem, contaDestino;
	
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
	}
	
	@Test
	public void deveRealizarTransferencia() {
		contaOrigem.setSaldoAtual(2500d);
		contaDestino.setSaldoAtual(3500d);
		
		Transferencia transferencia = new CriadorDeTransferencia()
				.daConta(contaOrigem)
				.paraAConta(contaDestino)
				.comValorDe(500d)
				.naDataDe(Calendar.getInstance())
				.construir();
		
		double saldoEsperadoContaOrigem = 2000d;
		double saldoEsperadoContaDestino = 4000d;
		
		transferencia.transferir();
		
		assertEquals(saldoEsperadoContaOrigem, contaOrigem.getSaldoAtual());
		assertEquals(saldoEsperadoContaDestino, contaDestino.getSaldoAtual());
	}
	
	@Test(expected = SaldoInsuficienteException.class)
	public void naoDeveRealizarTransferenciaComValorMaiorQueOSaldo() {
		contaOrigem.setSaldoAtual(3850d);
		contaDestino.setSaldoAtual(5000d);
		
		Transferencia transferencia = new CriadorDeTransferencia()
				.daConta(contaOrigem)
				.paraAConta(contaDestino)
				.comValorDe(3851d)
				.naDataDe(Calendar.getInstance())
				.construir();
		
		transferencia.transferir();
	}
	
	@Test(expected = SaqueInvalidoException.class)
	public void naoDeveRealizarTransferenciaComValorNegativo() {
		contaOrigem.setSaldoAtual(3500d);
		contaDestino.setSaldoAtual(1500d);
		
		Transferencia transferencia = new CriadorDeTransferencia()
				.daConta(contaOrigem)
				.paraAConta(contaDestino)
				.comValorDe(-15d)
				.naDataDe(Calendar.getInstance())
				.construir();
		
		transferencia.transferir();
	}
	
	@Test
	public void deveRealizarTransferenciaComValorTotalDaConta() {
		contaOrigem.setSaldoAtual(5000d);
		contaDestino.setSaldoAtual(3000d);
		
		Transferencia transferencia = new CriadorDeTransferencia()
				.daConta(contaOrigem)
				.paraAConta(contaDestino)
				.comValorDe(5000d)
				.naDataDe(Calendar.getInstance())
				.construir();		
		
		transferencia.transferir();
		
		double saldoEsperadoContaOrigem = 0d;
		double saldoEsperadoContaDestino = 8000d;
		
		assertEquals(saldoEsperadoContaOrigem, contaOrigem.getSaldoAtual());
		assertEquals(saldoEsperadoContaDestino, contaDestino.getSaldoAtual());
	}
	
	@Test(expected = SaqueInvalidoException.class)
	public void naoDeveRealizarTransferenciaComValorZero() {
		contaOrigem.setSaldoAtual(500d);
		contaDestino.setSaldoAtual(1000d);
		
		Transferencia transferencia = new CriadorDeTransferencia()
				.daConta(contaOrigem)
				.paraAConta(contaDestino)
				.comValorDe(0d)
				.naDataDe(Calendar.getInstance())
				.construir();	
		
		transferencia.transferir();
	}
	
	@Test(expected = RuntimeException.class)
	public void naoDeveRealizarTransferenciaSemContaOrigem() {
		contaDestino.setSaldoAtual(3500d);
	
		Transferencia transferencia = new CriadorDeTransferencia()
				.daConta(null)
				.paraAConta(contaDestino)
				.comValorDe(350d)
				.naDataDe(Calendar.getInstance())
				.construir();		
		
		transferencia.transferir();
	}
	
	@Test(expected = RuntimeException.class)
	public void naoDeveRealizarTransferenciaSemContaDestino() {
		contaOrigem.setSaldoAtual(3500d);
		
		Transferencia transferencia = new CriadorDeTransferencia()
				.daConta(contaOrigem)
				.paraAConta(null)
				.comValorDe(500d)
				.naDataDe(Calendar.getInstance())
				.construir();		
		
		transferencia.transferir();
	}
	
	@Test(expected = TransferenciaInvalidaException.class)
	public void naoDeveRealizarTransferenciaComMesmaContaDeOrigemEDestino() {
		contaOrigem.setSaldoAtual(500d);
		
		Transferencia transferencia = new CriadorDeTransferencia()
				.daConta(contaOrigem)
				.paraAConta(contaOrigem)
				.comValorDe(250d)
				.naDataDe(Calendar.getInstance())
				.construir();
		
		transferencia.transferir();
	}
	
	@Test(expected = DataInvalidaException.class)
	public void naoDeveRealizarTransferenciaComDataNoFuturo() {
		contaOrigem.setSaldoAtual(5600d);
		contaDestino.setSaldoAtual(3500d);

		Calendar dataFutura = Calendar.getInstance();
		dataFutura.add(Calendar.DAY_OF_MONTH, 2);
		
		Transferencia transferencia = new CriadorDeTransferencia()
				.daConta(contaOrigem)
				.paraAConta(contaDestino)
				.comValorDe(500d)
				.naDataDe(dataFutura)
				.construir();
		
		transferencia.transferir();
	}
}
