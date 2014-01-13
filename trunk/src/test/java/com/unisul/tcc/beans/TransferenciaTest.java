package com.unisul.tcc.beans;

import static junit.framework.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import com.unisul.tcc.builders.CriadorDeLancamento;
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
		criarDepositoInicialDaContaOrigem(2500d);
		criarDepositoInicialDaContaDestino(3500d);
		
		Transferencia transferencia = new CriadorDeTransferencia()
				.daConta(contaOrigem)
				.paraAConta(contaDestino)
				.comValorDe(500d)
				.naDataDeHoje()
				.construir();
		
		double saldoEsperadoContaOrigem = 2000d;
		double saldoEsperadoContaDestino = 4000d;
		
		transferencia.transferir();
		
		assertEquals(saldoEsperadoContaOrigem, contaOrigem.getSaldoAtual());
		assertEquals(saldoEsperadoContaDestino, contaDestino.getSaldoAtual());
	}
	
	@Test(expected = SaldoInsuficienteException.class)
	public void naoDeveRealizarTransferenciaComValorMaiorQueOSaldo() {
		criarDepositoInicialDaContaOrigem(3850d);
		criarDepositoInicialDaContaDestino(5000d);
		
		Transferencia transferencia = new CriadorDeTransferencia()
				.daConta(contaOrigem)
				.paraAConta(contaDestino)
				.comValorDe(3851d)
				.naDataDeHoje()
				.construir();
		
		transferencia.transferir();
	}
	
	@Test(expected = SaqueInvalidoException.class)
	public void naoDeveRealizarTransferenciaComValorNegativo() {
		criarDepositoInicialDaContaOrigem(5000d);
		criarDepositoInicialDaContaDestino(5000d);
		
		Transferencia transferencia = new CriadorDeTransferencia()
				.daConta(contaOrigem)
				.paraAConta(contaDestino)
				.comValorDe(-15d)
				.naDataDeHoje()
				.construir();
		
		transferencia.transferir();
	}
	
	@Test
	public void deveRealizarTransferenciaComValorTotalDaConta() {
		criarDepositoInicialDaContaOrigem(5000d);
		criarDepositoInicialDaContaDestino(3000d);
		
		Transferencia transferencia = new CriadorDeTransferencia()
				.daConta(contaOrigem)
				.paraAConta(contaDestino)
				.comValorDe(5000d)
				.naDataDeHoje()
				.construir();		
		
		transferencia.transferir();
		
		double saldoEsperadoContaOrigem = 0d;
		double saldoEsperadoContaDestino = 8000d;
		
		assertEquals(saldoEsperadoContaOrigem, contaOrigem.getSaldoAtual());
		assertEquals(saldoEsperadoContaDestino, contaDestino.getSaldoAtual());
	}
	
	@Test(expected = SaqueInvalidoException.class)
	public void naoDeveRealizarTransferenciaComValorZero() {
		criarDepositoInicialDaContaOrigem(5000d);
		criarDepositoInicialDaContaDestino(5000d);
		
		Transferencia transferencia = new CriadorDeTransferencia()
				.daConta(contaOrigem)
				.paraAConta(contaDestino)
				.comValorDe(0d)
				.naDataDeHoje()
				.construir();	
		
		transferencia.transferir();
	}
	
	@Test(expected = RuntimeException.class)
	public void naoDeveRealizarTransferenciaSemContaOrigem() {
		Transferencia transferencia = new CriadorDeTransferencia()
				.daConta(null)
				.paraAConta(contaDestino)
				.comValorDe(350d)
				.naDataDeHoje()
				.construir();		
		
		transferencia.transferir();
	}
	
	@Test(expected = RuntimeException.class)
	public void naoDeveRealizarTransferenciaSemContaDestino() {
		Transferencia transferencia = new CriadorDeTransferencia()
				.daConta(contaOrigem)
				.paraAConta(null)
				.comValorDe(500d)
				.naDataDeHoje()
				.construir();		
		
		transferencia.transferir();
	}
	
	@Test(expected = TransferenciaInvalidaException.class)
	public void naoDeveRealizarTransferenciaComMesmaContaDeOrigemEDestino() {
		Transferencia transferencia = new CriadorDeTransferencia()
				.daConta(contaOrigem)
				.paraAConta(contaOrigem)
				.comValorDe(250d)
				.naDataDeHoje()
				.construir();
		
		transferencia.transferir();
	}
	
	@Test(expected = DataInvalidaException.class)
	public void naoDeveRealizarTransferenciaComDataNoFuturo() {
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
	
	private void criarDepositoInicialDaContaOrigem(Double valor) {
		Lancamento depositoContaOrigem = new CriadorDeLancamento()
				.paraAConta(contaOrigem)
				.comADescricao("Salário")
				.noValorDe(valor)
				.naDataDeHoje()
				.comAObservacao("Saldo inicial")
				.doTipo(TipoLancamento.DEPOSITO)
				.construir();
		
		depositoContaOrigem.lancar();
	}
	
	private void criarDepositoInicialDaContaDestino(Double valor) {
		Lancamento depositoContaDestino = new CriadorDeLancamento()
				.paraAConta(contaDestino)
				.comADescricao("Salário")
				.noValorDe(valor)
				.naDataDeHoje()
				.comAObservacao("Saldo inicial")
				.doTipo(TipoLancamento.DEPOSITO)
				.construir();
		
		depositoContaDestino.lancar();
	}
}
