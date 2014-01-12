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

	public void preencher(String descricao, String dataLancamento,
			String valor, String observacao, String tipoLancamento) {
		WebElement campoDescricao = driver.findElement(By.id("campoDescricao"));
		campoDescricao.clear();
		campoDescricao.sendKeys(descricao);
		
		WebElement campoData = driver.findElement(By.id("dataLancamento"));
		campoData.clear();
		campoData.sendKeys(dataLancamento);
		
		WebElement campoValor = driver.findElement(By.id("campoValor"));
		campoValor.clear();
		campoValor.sendKeys(valor);
		
		WebElement campoObservacao = driver.findElement(By.id("campoObservacao"));
		campoObservacao.clear();
		campoObservacao.sendKeys(observacao);
		
		Select comboTipoLancamento = new Select(driver.findElement(By.id("comboTipoLancamento")));
		comboTipoLancamento.selectByVisibleText(tipoLancamento);
		
		WebElement botaoEditar = driver.findElement(By.id("botaoEditar"));
		botaoEditar.click();
		
		new WebDriverWait(driver, 10).until(
				ExpectedConditions.alertIsPresent());
		
		alerta = driver.switchTo().alert();
		alerta.accept();
	}
	
	public String getMensagemAlerta() {
		return alerta.getText();
	}
}
