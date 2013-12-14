package com.unisul.tcc.beans;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static com.unisul.tcc.beans.TipoLancamento.*;

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
		conta.setSaldoAtual(5000d);

		Lancamento lancamento = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Pagamento de conta de luz")
				.noValorDe(150d)
				.naDataDe(Calendar.getInstance())
				.doTipo(SAQUE)
				.construir();

		lancamento.realizarLancamento();

		double saldoAtualEsperado = 4850d;

		Assert.assertEquals(saldoAtualEsperado, conta.getSaldoAtual());
	}

	@Test(expected = SaqueInvalidoException.class)
	public void naoDeveRealizarUmSaqueComValorNegativo() {
		conta.setSaldoAtual(500d);
		
		Lancamento lancamento = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Lancamento com valor negativo")
				.noValorDe(-15d)
				.naDataDe(Calendar.getInstance())
				.doTipo(SAQUE)
				.construir();

		lancamento.realizarLancamento();
	}

	@Test(expected = SaqueInvalidoException.class)
	public void naoDeveRealizarUmSaqueComValorZero() {
		conta.setSaldoAtual(5000d);
		
		Lancamento lancamento = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Lancamento com valor zero")
				.noValorDe(0d)
				.naDataDe(Calendar.getInstance())
				.doTipo(SAQUE)
				.construir();

		lancamento.realizarLancamento();
	}

	@Test
	public void deveRealizarSaqueComValorTotalDaConta() {
		conta.setSaldoAtual(5000d);

		Lancamento lancamento = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Saque total da conta")
				.noValorDe(5000d)
				.naDataDe(Calendar.getInstance())
				.doTipo(SAQUE)
				.construir();
		
		lancamento.realizarLancamento();

		double saldoAtualEsperado = 0d;

		assertEquals(saldoAtualEsperado, conta.getSaldoAtual());
	}
	
	@Test(expected = SaldoInsuficienteException.class)
	public void naoDeveRealizarSaqueComValorMaiorQueOSaldo() {
		conta.setSaldoAtual(1500d);
		
		Lancamento lancamento = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Sacando mais do que tenho")
				.noValorDe(1600d)
				.naDataDe(Calendar.getInstance())
				.doTipo(SAQUE)
				.construir();
		
		lancamento.realizarLancamento();
	}

	@Test
	public void deveRealizarUmDeposito() {
		conta.setSaldoAtual(5000d);
		
		Lancamento lancamento = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Dinheiro que sobrou")
				.noValorDe(250d)
				.naDataDe(Calendar.getInstance())
				.doTipo(DEPOSITO)
				.construir();

		lancamento.realizarLancamento();

		double resultadoEsperado = 5250d;

		assertEquals(resultadoEsperado, conta.getSaldoAtual());
	}

	@Test(expected = DepositoInvalidoException.class)
	public void naoDeveRealizarUmDepositoComValorNegativo() {
		conta.setSaldoAtual(500d);

		Lancamento lancamento = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Depósito de valor negativo")
				.noValorDe(-1d)
				.naDataDe(Calendar.getInstance())
				.doTipo(DEPOSITO)
				.construir();

		lancamento.realizarLancamento();
	}

	@Test(expected = DepositoInvalidoException.class)
	public void naoDeveRealizarUmDepositoComValorZero() {
		conta.setSaldoAtual(5000d);
		
		Lancamento lancamento = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Depositando nenhum valor")
				.noValorDe(0d)
				.naDataDe(Calendar.getInstance())
				.doTipo(DEPOSITO)
				.construir();

		lancamento.realizarLancamento();
	}

	@Test(expected = DataInvalidaException.class)
	public void naoDeveRealizarUmLancamentoComDataNoFuturo() {
		conta.setSaldoAtual(5000d);

		Calendar dataFutura = Calendar.getInstance();
		dataFutura.add(Calendar.DAY_OF_MONTH, 3);

		Lancamento lancamento = new CriadorDeLancamento()
				.paraAConta(conta)
				.comADescricao("Saque do futuro")
				.noValorDe(0d)
				.naDataDe(dataFutura)
				.doTipo(SAQUE)
				.construir();

		lancamento.realizarLancamento();
	}
}
