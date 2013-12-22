package com.unisul.tcc.selenium;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LancamentosTest {
	private WebDriver driver;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.get("http://localhost:8083/EstudoDeCaso/");

		WebElement botaoLancamentos = driver.findElement(By.id("btnLancamentos"));
		botaoLancamentos.click();
	}
	
	@After
	public void tearDown() {
		driver.close();
	}
	
	@Test
	public void deveCadastrarUmLancamento() throws InterruptedException {
		WebElement botaoCadastrar = driver.findElement(By.id("botaoCadastrar"));
		botaoCadastrar.click();
		
		WebElement campoDescricao = driver.findElement(By.id("campoDescricao"));
		campoDescricao.sendKeys("Salário");
		
		WebElement campoData = driver.findElement(By.id("dataLancamento"));
		campoData.sendKeys("21/12/2013");
		
		WebElement campoValor = driver.findElement(By.id("campoValor"));
		campoValor.sendKeys("5.000,00");
		
		WebElement campoObservacao = driver.findElement(By.id("campoObservacao"));
		campoObservacao.sendKeys("Salário para testes");
		
		WebElement comboTipoLancamento = driver.findElement(By.id("comboTipoLancamento"));
		List<WebElement> opcoesCombo = comboTipoLancamento.findElements(By.tagName("option"));
		
		WebElement opcaoDeposito = opcoesCombo.get(1);
		opcaoDeposito.click();
		
		WebElement botaoSalvar = driver.findElement(By.id("botaoCadastrar"));
		botaoSalvar.click();
		
		new WebDriverWait(driver, 10).until(
				ExpectedConditions.alertIsPresent());
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Lançamento salvo com sucesso!", alert.getText());
		alert.accept();

		WebElement tabelaDeLancamentos = driver.findElement(By.tagName("table"));
		List<WebElement> linhasTabela = tabelaDeLancamentos.findElements(By.tagName("tr"));
		List<WebElement> colunas = linhasTabela.get(linhasTabela.size() - 1).findElements(By.tagName("td"));
		
		WebElement colunaDeValor = colunas.get(3);

		Assert.assertEquals("5.000,00", colunaDeValor.getText());
		
		WebElement botaoContas = driver.findElement(By.id("btnContas"));
		botaoContas.click();
		
		WebElement tabelaDeContas = driver.findElement(By.tagName("table"));
		List<WebElement> linhasTabelaDeContas = tabelaDeContas.findElements(By.tagName("tr"));
		List<WebElement> colunasDaConta = linhasTabelaDeContas.get(1).findElements(By.tagName("td"));
				
		WebElement colunaSaldoAtual = colunasDaConta.get(5);
		
		Assert.assertEquals("20.000,00", colunaSaldoAtual.getText());
	}
}
