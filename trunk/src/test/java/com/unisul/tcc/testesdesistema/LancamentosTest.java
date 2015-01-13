package com.unisul.tcc.testesdesistema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.unisul.tcc.pageobjects.CadastroLancamentoPage;
import com.unisul.tcc.pageobjects.ContasPage;
import com.unisul.tcc.pageobjects.EdicaoLancamentoPage;
import com.unisul.tcc.pageobjects.LancamentosPage;

public class LancamentosTest {
	private static WebDriver driver;
	private static String URL_BASE = "http://localhost:8083/EstudoDeCaso";
	private LancamentosPage lancamentos;
	private ContasPage contas;
	
	@BeforeClass
	public static void setUpClass() {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.get(URL_BASE);
	}
	
	@Before
	public void setUp()  throws Exception {
		lancamentos = PageFactory.initElements(driver, LancamentosPage.class);
		contas = new ContasPage(driver);
		
		lancamentos.visitar();
	}
	
	@After
	public void tearDown() {
		driver.get(URL_BASE + "/excluirTodosLancamentos");
	}

	@AfterClass
	public static void tearDownClass() {
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
		paginaCadastro.comDescricao(descricao);
		paginaCadastro.comDataDeLancamento(dataLancamento);
		paginaCadastro.comValor(valor);
		paginaCadastro.comObservacao(observacao);
		paginaCadastro.selecionarTipoLancamento(tipoLancamento);
		paginaCadastro.selecionarConta(nomeConta);
		paginaCadastro.clicarEmSalvar();
		
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
		paginaCadastro.comDescricao(descricao);
		paginaCadastro.comDataDeLancamento(dataLancamento);
		paginaCadastro.comValor(valor);
		paginaCadastro.comObservacao(observacao);
		paginaCadastro.selecionarTipoLancamento(tipoLancamento);
		paginaCadastro.selecionarConta(nomeConta);
		paginaCadastro.clicarEmSalvar();
	
		assertEquals("Lançamento salvo com sucesso!", paginaCadastro.getMensagemAlerta());
		assertTrue(lancamentos.existeNaListagem(descricao, dataLancamento, valor, observacao, nomeConta));
		
		contas.visitar();
		assertEquals("2.500,00", contas.getValor());
	}
	
	@Test
	public void naoDeveCadastrarUmLancamentoComCamposEmBranco() {
		String descricao = "";
		
		CadastroLancamentoPage paginaCadastro = lancamentos.cadastrar();
		paginaCadastro.comDescricao(descricao);
		paginaCadastro.clicarEmSalvar();

		assertTrue(paginaCadastro.getMensagemAlerta().contains("O campo descrição não pode estar em branco!"));
	}
	
	@Test
	public void naoDeveCadastrarLancamentoComDataNoFuturo() {
		String dataLancamento = "13/12/3000";
		
		CadastroLancamentoPage paginaCadastro = lancamentos.cadastrar();
		paginaCadastro.comDataDeLancamento(dataLancamento);
		paginaCadastro.clicarEmSalvar();
		
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
		paginaCadastro.comDescricao(descricao);
		paginaCadastro.comDataDeLancamento(dataLancamento);
		paginaCadastro.comValor(valor);
		paginaCadastro.comObservacao(observacao);
		paginaCadastro.selecionarTipoLancamento(tipoLancamento);
		paginaCadastro.selecionarConta(nomeConta);
		paginaCadastro.clicarEmSalvar();
		
		assertTrue(paginaCadastro.getMensagemAlerta().contains("Não se pode efetuar um saque com o valor maior que o saldo!"));
	}
	
	@Test
	public void naoDeveCadastrarUmLancamentoComValorZero() {
		String valor = "0,00";
		
		CadastroLancamentoPage paginaCadastro = lancamentos.cadastrar();
		paginaCadastro.comValor(valor);
		paginaCadastro.clicarEmSalvar();
		
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
		paginaCadastro.comDescricao(descricao);
		paginaCadastro.comDataDeLancamento(dataLancamento);
		paginaCadastro.comValor(valor);
		paginaCadastro.comObservacao(observacao);
		paginaCadastro.selecionarTipoLancamento(tipoLancamento);
		paginaCadastro.selecionarConta(nomeConta);
		paginaCadastro.clicarEmSalvar();

		contas.visitar();
		assertEquals("2.900,00", contas.getValor());
		
		lancamentos.visitar();
		
		String novoValor = "110,00";
		
		EdicaoLancamentoPage paginaEdicao = lancamentos.editar();
		paginaEdicao.comValor(novoValor);
		paginaEdicao.clicarEmAtualizar();

		assertEquals("Lançamento editado com sucesso!", paginaEdicao.getMensagemAlerta());
		assertTrue(lancamentos.existeNaListagem(descricao, dataLancamento, novoValor, observacao, nomeConta));

		contas.visitar();
		assertEquals("2.890,00", contas.getValor());
	}
	
	@Test
	public void naoDeveEditarUmLancamentoComOsCamposEmBranco() {
		criarDepositoInicial();
		
		String descricao = "";
		
		EdicaoLancamentoPage paginaEdicao = lancamentos.editar();
		paginaEdicao.comDescricao(descricao);
		paginaEdicao.clicarEmAtualizar();

		assertTrue(paginaEdicao.getMensagemAlerta().contains("O campo descrição não pode estar em branco!"));
	}
	
	@Test
	public void naoDeveEditarUmLancamentoComDataNoFuturo() {
		criarDepositoInicial();
		
		String dataLancamento = "13/12/3000";
		
		EdicaoLancamentoPage paginaEdicao = lancamentos.editar();
		paginaEdicao.comDataDeLancamento(dataLancamento);
		paginaEdicao.clicarEmAtualizar();
		
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
		paginaCadastro.comDescricao(descricao);
		paginaCadastro.comDataDeLancamento(dataLancamento);
		paginaCadastro.comValor(valor);
		paginaCadastro.comObservacao(observacao);
		paginaCadastro.selecionarTipoLancamento(tipoLancamento);
		paginaCadastro.selecionarConta(nomeConta);
		paginaCadastro.clicarEmSalvar();

		contas.visitar();
		assertEquals("2.700,00", contas.getValor());
		
		lancamentos.visitar();
		
		String novoValor = "3001,00";
		
		EdicaoLancamentoPage paginaEdicao = lancamentos.editar();
		paginaEdicao.comValor(novoValor);
		paginaEdicao.clicarEmAtualizar();
		
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
		paginaCadastro.comDescricao(descricao);
		paginaCadastro.comDataDeLancamento(dataLancamento);
		paginaCadastro.comValor(valor);
		paginaCadastro.comObservacao(observacao);
		paginaCadastro.selecionarTipoLancamento(tipoLancamento);
		paginaCadastro.selecionarConta(nomeConta);
		paginaCadastro.clicarEmSalvar();

		contas.visitar();
		assertEquals("2.935,00", contas.getValor());
		
		lancamentos.visitar();
		
		String novoValor = "0,00";
		
		EdicaoLancamentoPage paginaEdicao = lancamentos.editar();
		paginaEdicao.comValor(novoValor);
		paginaEdicao.clicarEmAtualizar();
		
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
		paginaCadastro.comDescricao(descricao);
		paginaCadastro.comDataDeLancamento(dataLancamento);
		paginaCadastro.comValor(valor);
		paginaCadastro.comObservacao(observacao);
		paginaCadastro.selecionarTipoLancamento(tipoLancamento);
		paginaCadastro.selecionarConta(nomeConta);
		paginaCadastro.clicarEmSalvar();

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
		paginaCadastro.comDescricao(descricao);
		paginaCadastro.comDataDeLancamento(dataLancamento);
		paginaCadastro.comValor(valor);
		paginaCadastro.comObservacao(observacao);
		paginaCadastro.selecionarTipoLancamento(tipoLancamento);
		paginaCadastro.selecionarConta(nomeConta);
		paginaCadastro.clicarEmSalvar();
	}
}
