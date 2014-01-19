package com.unisul.tcc.pageobjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EdicaoLancamentoPage {
	private WebDriver driver;
	private Alert alerta;
	
	public EdicaoLancamentoPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void comDescricao(String descricao) {
		WebElement campoDescricao = driver.findElement(By.id("campoDescricao"));
		campoDescricao.clear();
		campoDescricao.sendKeys(descricao);
	}

	public void comDataDeLancamento(String dataLancamento) {
		WebElement campoData = driver.findElement(By.id("dataLancamento"));
		campoData.clear();
		campoData.sendKeys(dataLancamento);
	}
	
	public void comValor(String valor) {
		WebElement campoValor = driver.findElement(By.id("campoValor"));
		campoValor.clear();
		campoValor.sendKeys(valor);
	}
	
	public void comObservacao(String observacao) {
		WebElement campoObservacao = driver.findElement(By.id("campoObservacao"));
		campoObservacao.clear();
		campoObservacao.sendKeys(observacao);
	}
	
	public void selecionarTipoLancamento(String tipoLancamento) {
		Select comboTipoLancamento = new Select(driver.findElement(By.id("comboTipoLancamento")));
		comboTipoLancamento.selectByVisibleText(tipoLancamento);
	}
	
	public void selecionarConta(String nomeConta) {
		Select comboConta = new Select(driver.findElement(By.id("comboConta")));
		comboConta.selectByVisibleText(nomeConta);
	}

	public void clicarEmAtualizar() {
		WebElement botaoAtualizar = driver.findElement(By.id("botaoAtualizar"));
		botaoAtualizar.click();
		
		new WebDriverWait(driver, 10).until(
				ExpectedConditions.alertIsPresent());
		
		alerta = driver.switchTo().alert();
		alerta.accept();
	}
	
	public String getMensagemAlerta() {
		return alerta.getText();
	}
}
