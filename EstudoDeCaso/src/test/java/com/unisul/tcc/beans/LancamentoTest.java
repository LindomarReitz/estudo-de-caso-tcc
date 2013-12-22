package com.unisul.tcc.beans;

import static com.unisul.tcc.beans.TipoLancamento.DEPOSITO;
import static com.unisul.tcc.beans.TipoLancamento.SAQUE;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.unisul.tcc.builders.CriadorDeLancamento;
import com.unisul.tcc.exceptions.DataInvalidaException;
import com.unisul.tcc.exceptions.DepositoInvalidoException;
import com.unisul.tcc.exceptions.SaldoInsuficienteException;
import com.unisul.tcc.exceptions.SaqueInvalidoException;

public class LancamentoTest {
	private Conta conta;

	@Before
	public void setUp() {
		conta = new Conta();
		conta.setNome("Conta corrente");
		conta.setNumeroAgencia(1234);
		conta.setNumeroConta(13456698);

		Banco banco = new Banco();
		banco.setNome("Santander");

		conta.setBanco(banco);
	}

	@Test
	public void deveRealizarUmSaque() {
		Lancamento deposito = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Salário")
				.noValorDe(5000d)
				.naDataDe(Calendar.getInstance())
				.doTipo(DEPOSITO)
				.construir();
		
		deposito.lancar();
		
		Lancamento saque = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Pagamento de conta de luz")
				.noValorDe(150d)
				.naDataDe(Calendar.getInstance())
				.doTipo(SAQUE)
				.construir();

		saque.lancar();
		
		double saldoAtualEsperado = 4850d;

		Assert.assertEquals(saldoAtualEsperado, conta.getSaldoAtual());
	}

	@Test(expected = SaqueInvalidoException.class)
	public void naoDeveRealizarUmSaqueComValorNegativo() {
		Lancamento saque = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Lancamento com valor negativo")
				.noValorDe(-15d)
				.naDataDe(Calendar.getInstance())
				.doTipo(SAQUE)
				.construir();

		saque.lancar();
	}

	@Test(expected = SaqueInvalidoException.class)
	public void naoDeveRealizarUmSaqueComValorZero() {
		Lancamento saque = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Lancamento com valor zero")
				.noValorDe(0d)
				.naDataDe(Calendar.getInstance())
				.doTipo(SAQUE)
				.construir();

		saque.lancar();
	}
	
	@Test(expected = SaldoInsuficienteException.class)
	public void naoDeveRealizarSaqueComValorMaiorQueOSaldo() {
		Lancamento saque = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Sacando mais do que tenho")
				.noValorDe(1600d)
				.naDataDe(Calendar.getInstance())
				.doTipo(SAQUE)
				.construir();
		
		saque.lancar();
	}
	
	@Test
	public void deveRealizarSaqueComValorTotalDaConta() {
		Lancamento deposito = new CriadorDeLancamento()
			.paraAConta(conta)
			.comADescricao("Salário")
			.noValorDe(5000d)
			.naDataDe(Calendar.getInstance())
			.doTipo(DEPOSITO)
			.construir();

		deposito.lancar();

		Lancamento saque = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Saque total da conta")
				.noValorDe(5000d)
				.naDataDe(Calendar.getInstance())
				.doTipo(SAQUE)
				.construir();
		
		saque.lancar();

		double saldoAtualEsperado = 0d;

		assertEquals(saldoAtualEsperado, conta.getSaldoAtual());
	}

	@Test
	public void deveRealizarUmDeposito() {
		Lancamento deposito = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Salário")
				.noValorDe(5000d)
				.naDataDe(Calendar.getInstance())
				.doTipo(DEPOSITO)
				.construir();
		
		deposito.lancar();

		Lancamento saque = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Dinheiro que sobrou")
				.noValorDe(250d)
				.naDataDe(Calendar.getInstance())
				.doTipo(DEPOSITO)
				.construir();

		saque.lancar();
		
		double saldoAtualEsperado = 5250d;

		assertEquals(saldoAtualEsperado, conta.getSaldoAtual());
	}

	@Test(expected = DepositoInvalidoException.class)
	public void naoDeveRealizarUmDepositoComValorNegativo() {
		Lancamento saque = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Depósito de valor negativo")
				.noValorDe(-1d)
				.naDataDe(Calendar.getInstance())
				.doTipo(DEPOSITO)
				.construir();

		saque.lancar();
	}

	@Test(expected = DepositoInvalidoException.class)
	public void naoDeveRealizarUmDepositoComValorZero() {
		Lancamento saque = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Depositando nenhum valor")
				.noValorDe(0d)
				.naDataDe(Calendar.getInstance())
				.doTipo(DEPOSITO)
				.construir();

		saque.lancar();
	}

	@Test(expected = DataInvalidaException.class)
	public void naoDeveRealizarUmLancamentoComDataNoFuturo() {
		Calendar dataFutura = Calendar.getInstance();
		dataFutura.add(Calendar.DAY_OF_MONTH, 3);

		Lancamento saque = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Saque do futuro")
				.noValorDe(0d)
				.naDataDe(dataFutura)
				.doTipo(SAQUE)
				.construir();

		saque.lancar();
	}
}
