package com.unisul.tcc.testesdesistema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.unisul.tcc.pageobjects.CadastroLancamentoPage;
import com.unisul.tcc.pageobjects.ContasPage;
import com.unisul.tcc.pageobjects.EdicaoLancamentoPage;
import com.unisul.tcc.pageobjects.LancamentosPage;

public class LancamentosTest {
	private WebDriver driver;
	private LancamentosPage lancamentos;
	private ContasPage contas;
	private String urlBase = "http://localhost:8083/EstudoDeCaso";
	
	@Before
	public void setUp()  throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.get(urlBase);
		
		lancamentos = new LancamentosPage(driver);
		contas = new ContasPage(driver);
		
		lancamentos.visitar();
	}
	
	@After
	public void tearDown() throws Exception {
		driver.get(urlBase + "/excluirTodosLancamentos");
		
		driver.quit();
	}
	
	@Test
	public void deveCadastrarUmDeposito() {
		String descricao = "Salário de teste";
		String dataLancamento = "13/12/2013";
		String valor = "5.000,00";
		String observacao = "Salário para testes";
		String tipoLancamento = "Depósito";
		String nomeConta = "Conta corrente PF";
		
		CadastroLancamentoPage paginaCadastro = lancamentos.cadastrar();
		paginaCadastro.preencher(descricao, dataLancamento, valor, observacao, tipoLancamento, nomeConta);
	
		assertEquals("Lançamento salvo com sucesso!", paginaCadastro.getMensagemAlerta());
		assertTrue(lancamentos.existeNaListagem(descricao, dataLancamento, valor, observacao, nomeConta));
		
		contas.visitar();
		assertEquals("5.000,00", contas.getValor());
	}
	
	@Test
	public void deveCadastrarUmSaque() {
		criarDepositoInicial();
		
		String descricao = "Compras no mercado";
		String dataLancamento = "13/12/2013";
		String valor = "500,00";
		String observacao = "";
		String tipoLancamento = "Saque";
		String nomeConta = "Conta corrente PF";
		
		CadastroLancamentoPage paginaCadastro = lancamentos.cadastrar();
		paginaCadastro.preencher(descricao, dataLancamento, valor, observacao, tipoLancamento, nomeConta);
	
		assertEquals("Lançamento salvo com sucesso!", paginaCadastro.getMensagemAlerta());
		assertTrue(lancamentos.existeNaListagem(descricao, dataLancamento, valor, observacao, nomeConta));
		
		contas.visitar();
		assertEquals("2.500,00", contas.getValor());
	}
	
	@Test
	public void naoDeveCadastrarUmLancamentoComCamposEmBranco() {
		String descricao = "";
		String dataLancamento = "13/12/2013";
		String valor = "3.000,00";
		String observacao = "Salário para testes";
		String tipoLancamento = "Depósito";
		String nomeConta = "Conta corrente PF";
		
		CadastroLancamentoPage paginaCadastro = lancamentos.cadastrar();
		paginaCadastro.preencher(descricao, dataLancamento, valor, observacao, tipoLancamento, nomeConta);

		assertTrue(paginaCadastro.getMensagemAlerta().contains("O campo descrição não pode estar em branco!"));
	}
	
	@Test
	public void naoDeveCadastrarLancamentoComDataNoFuturo() {
		String descricao = "Lançamento no futuro";	
		String dataLancamento = "13/12/3000";
		String valor = "3.000,00";
		String observacao = "Lançamento no futuro";
		String tipoLancamento = "Depósito";
		String nomeConta = "Conta corrente PF";
		
		CadastroLancamentoPage paginaCadastro = lancamentos.cadastrar();
		paginaCadastro.preencher(descricao, dataLancamento, valor, observacao, tipoLancamento, nomeConta);

		assertTrue(paginaCadastro.getMensagemAlerta().contains("Data não pode ser no futuro!"));
	}
	
	@Test
	public void naoDeveCadastrarUmSaqueComValorAcimaDoSaldo() {
		criarDepositoInicial();
		
		String descricao = "Lançamento no futuro";	
		String dataLancamento = "13/12/2013";
		String valor = "3.001,00";
		String observacao = "Lançamento no futuro";
		String tipoLancamento = "Saque";
		String nomeConta = "Conta corrente PF";
		
		CadastroLancamentoPage paginaCadastro = lancamentos.cadastrar();
		paginaCadastro.preencher(descricao, dataLancamento, valor, observacao, tipoLancamento, nomeConta);
		
		assertTrue(paginaCadastro.getMensagemAlerta().contains("Não se pode efetuar um saque com o valor maior que o saldo!"));
	}
	
	@Test
	public void naoDeveCadastrarUmLancamentoComValorZero() {
		String descricao = "Lançamento com valor zero";	
		String dataLancamento = "13/12/2013";
		String valor = "0,00";
		String observacao = "Lançamento com valor zero";
		String tipoLancamento = "Depósito";
		String nomeConta = "Conta corrente PF";
		
		CadastroLancamentoPage paginaCadastro = lancamentos.cadastrar();
		paginaCadastro.preencher(descricao, dataLancamento, valor, observacao, tipoLancamento, nomeConta);
		
		assertTrue(paginaCadastro.getMensagemAlerta().contains("O campo valor não pode ser zero!"));
	}

	@Test
	public void deveEditarUmLancamento() {
		criarDepositoInicial();
		
		String descricao = "Conta de luz";	
		String dataLancamento = "13/12/2013";
		String valor = "100,00";
		String observacao = "";
		String tipoLancamento = "Saque";
		String nomeConta = "Conta corrente PF";
		
		CadastroLancamentoPage paginaCadastro = lancamentos.cadastrar();
		paginaCadastro.preencher(descricao, dataLancamento, valor, observacao, tipoLancamento, nomeConta);

		contas.visitar();
		assertEquals("2.900,00", contas.getValor());
		
		lancamentos.visitar();
		
		String novoValor = "110,00";
		
		EdicaoLancamentoPage paginaEdicao = lancamentos.editar();
		paginaEdicao.preencher(descricao, dataLancamento, novoValor, observacao, tipoLancamento);
		assertEquals("Lançamento editado com sucesso!", paginaEdicao.getMensagemAlerta());
		assertTrue(lancamentos.existeNaListagem(descricao, dataLancamento, novoValor, observacao, nomeConta));

		contas.visitar();
		assertEquals("2.890,00", contas.getValor());
	}
	
	@Test
	public void naoDeveEditarUmLancamentoComOsCamposEmBranco() {
		criarDepositoInicial();
		
		String descricao = "";
		String dataLancamento = "13/12/2013";
		String valor = "3.000,00";
		String observacao = "Salário para testes";
		String tipoLancamento = "Depósito";
		
		EdicaoLancamentoPage paginaEdicao = lancamentos.editar();
		paginaEdicao.preencher(descricao, dataLancamento, valor, observacao, tipoLancamento);

		assertTrue(paginaEdicao.getMensagemAlerta().contains("O campo descrição não pode estar em branco!"));
	}
	
	@Test
	public void naoDeveEditarUmLancamentoComDataNoFuturo() {
		criarDepositoInicial();
		
		String descricao = "Depósito no futuro";
		String dataLancamento = "13/12/3000";
		String valor = "3.000,00";
		String observacao = "Salário para testes";
		String tipoLancamento = "Depósito";
		
		EdicaoLancamentoPage paginaEdicao = lancamentos.editar();
		paginaEdicao.preencher(descricao, dataLancamento, valor, observacao, tipoLancamento);

		assertTrue(paginaEdicao.getMensagemAlerta().contains("Data não pode ser no futuro!"));
	}
	
	@Test
	public void naoDeveEditarUmSaqueComValorAcimaDoSaldo() {
		criarDepositoInicial();
		
		String descricao = "Compra no mercado";	
		String dataLancamento = "15/12/2013";
		String valor = "300,00";
		String observacao = "";
		String tipoLancamento = "Saque";
		String nomeConta = "Conta corrente PF";
		
		CadastroLancamentoPage paginaCadastro = lancamentos.cadastrar();
		paginaCadastro.preencher(descricao, dataLancamento, valor, observacao, tipoLancamento, nomeConta);

		contas.visitar();
		assertEquals("2.700,00", contas.getValor());
		
		lancamentos.visitar();
		
		String novoValor = "3001,00";
		
		EdicaoLancamentoPage paginaEdicao = lancamentos.editar();
		paginaEdicao.preencher(descricao, dataLancamento, novoValor, observacao, tipoLancamento);
		assertTrue(paginaEdicao.getMensagemAlerta().contains("Não se pode efetuar um saque com o valor maior que o saldo!"));
	}
	
	@Test
	public void naoDeveEditarUmLancamentoComValorZero() {
		criarDepositoInicial();
		
		String descricao = "Conta de água";	
		String dataLancamento = "15/12/2013";
		String valor = "65,00";
		String observacao = "";
		String tipoLancamento = "Saque";
		String nomeConta = "Conta corrente PF";
		
		CadastroLancamentoPage paginaCadastro = lancamentos.cadastrar();
		paginaCadastro.preencher(descricao, dataLancamento, valor, observacao, tipoLancamento, nomeConta);

		contas.visitar();
		assertEquals("2.935,00", contas.getValor());
		
		lancamentos.visitar();
		
		String novoValor = "0,00";
		
		EdicaoLancamentoPage paginaEdicao = lancamentos.editar();
		paginaEdicao.preencher(descricao, dataLancamento, novoValor, observacao, tipoLancamento);
		assertTrue(paginaEdicao.getMensagemAlerta().contains(("O campo valor não pode ser zero!")));
	}
	
	@Test
	public void deveExcluirUmLancamento() {
		criarDepositoInicial();
		
		String descricao = "Ração para o cachorro";	
		String dataLancamento = "13/12/2013";
		String valor = "30,00";
		String observacao = "";
		String tipoLancamento = "Saque";
		String nomeConta = "Conta corrente PF";
		
		CadastroLancamentoPage paginaCadastro = lancamentos.cadastrar();
		paginaCadastro.preencher(descricao, dataLancamento, valor, observacao, tipoLancamento, nomeConta);

		contas.visitar();
		assertEquals("2.970,00", contas.getValor());
		
		lancamentos.visitar();
		lancamentos.excluir();
		
		assertEquals("Lançamento excluído com sucesso!", lancamentos.getMensagemAlerta());
		assertFalse(lancamentos.existeNaListagem(descricao, dataLancamento, valor, observacao, nomeConta));
		
		contas.visitar();
		assertEquals("3.000,00", contas.getValor());
	}
	
	private void criarDepositoInicial() {
		String descricao = "Salário de teste";
		String dataLancamento = "13/12/2013";
		String valor = "3.000,00";
		String observacao = "Salário para testes";
		String tipoLancamento = "Depósito";
		String nomeConta = "Conta corrente PF";
		
		CadastroLancamentoPage paginaCadastro = lancamentos.cadastrar();
		paginaCadastro.preencher(descricao, dataLancamento, valor, observacao, tipoLancamento, nomeConta);
	}
}
