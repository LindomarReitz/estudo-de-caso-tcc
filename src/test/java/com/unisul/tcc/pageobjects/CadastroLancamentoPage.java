package com.unisul.tcc.pageobjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CadastroLancamentoPage {
	private WebDriver driver;
	private Alert alerta;
	
	public CadastroLancamentoPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void preencher(String descricao, String dataLancamento,
			String valor, String observacao, String tipoLancamento, String nomeConta) {
		WebElement campoDescricao = driver.findElement(By.id("campoDescricao"));
		campoDescricao.sendKeys(descricao);
		
		WebElement campoData = driver.findElement(By.id("dataLancamento"));
		campoData.sendKeys(dataLancamento);
		
		WebElement campoValor = driver.findElement(By.id("campoValor"));
		campoValor.sendKeys(valor);
		
		WebElement campoObservacao = driver.findElement(By.id("campoObservacao"));
		campoObservacao.sendKeys(observacao);
		
		Select comboTipoLancamento = new Select(driver.findElement(By.id("comboTipoLancamento")));
		comboTipoLancamento.selectByVisibleText(tipoLancamento);

		Select comboConta = new Select(driver.findElement(By.id("comboConta")));
		comboConta.selectByVisibleText(nomeConta);
		
		WebElement botaoSalvar = driver.findElement(By.id("botaoCadastrar"));
		botaoSalvar.click();
		
		new WebDriverWait(driver, 10).until(
				ExpectedConditions.alertIsPresent());
		
		alerta = driver.switchTo().alert();
		alerta.accept();
	}
	
	public String getMensagemAlerta() {
		return alerta.getText();
	}
}

