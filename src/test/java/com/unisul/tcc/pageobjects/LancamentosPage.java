package com.unisul.tcc.pageobjects;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LancamentosPage {
	private WebDriver driver;
	private Alert alerta;
	
	public LancamentosPage(WebDriver driver) {
		this.driver = driver;
	}

	public void visitar() {
		driver.findElement(By.id("botaoLancamentos")).click();
	}

	public CadastroLancamentoPage cadastrar() {
		WebElement botaoCadastrar = driver.findElement(By.id("botaoCadastrar"));
		botaoCadastrar.click();
		
		return new CadastroLancamentoPage(driver);
	}

	public EdicaoLancamentoPage editar() {
		WebElement botaoEditar = ultimoLancamentoCadastrado().findElement(By.linkText("Editar"));
		botaoEditar.click();
		
		return new EdicaoLancamentoPage(driver);
	}

	public void excluir() {
		WebElement botaoExcluir = ultimoLancamentoCadastrado().findElement(By.linkText("Excluir"));
		botaoExcluir.click();
		
		new WebDriverWait(driver, 10).until(
				ExpectedConditions.alertIsPresent());
		
		Alert alertConfirmacao = driver.switchTo().alert();
		alertConfirmacao.accept();

		new WebDriverWait(driver, 10).until(
				ExpectedConditions.alertIsPresent());
		
		alerta = driver.switchTo().alert();
		alerta.accept();
	}

	private WebElement ultimoLancamentoCadastrado() {
		WebElement tabela = driver.findElement(By.tagName("table"));
		List<WebElement> linhas = tabela.findElements(By.tagName("tr"));
		
		WebElement ultimoLancamento = linhas.get(linhas.size() - 1);
		
		return ultimoLancamento;
	}
	
	public boolean existeNaListagem(String descricao, String dataLancamento,
			String valor, String observacao, String nomeConta) {
		return driver.getPageSource().contains(descricao) 
				&& driver.getPageSource().contains(dataLancamento) 
				&& driver.getPageSource().contains(valor)
				&& driver.getPageSource().contains(observacao)
				&& driver.getPageSource().contains(nomeConta);
	}
	
	public String getMensagemAlerta() {
		return alerta.getText();
	}
}
