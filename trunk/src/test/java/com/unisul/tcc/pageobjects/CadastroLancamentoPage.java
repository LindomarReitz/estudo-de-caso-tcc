package com.unisul.tcc.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.unisul.tcc.utils.AlertUtils;

public class CadastroLancamentoPage {
	private WebDriver driver;
	private AlertUtils alertUtils;
	
	@FindBy(id = "campoDescricao")
	private WebElement campoDescricao;
	
	@FindBy(id = "dataLancamento")
	private WebElement campoData;
	
	@FindBy(id = "campoValor")
	private WebElement campoValor;
	
	@FindBy(id = "campoObservacao")
	private WebElement campoObservacao;

	@FindBy(id = "botaoSalvar")
	private WebElement botaoSalvar;
	
	public CadastroLancamentoPage(WebDriver driver) {
		this.driver = driver;
	}

	public void comDescricao(String descricao) {
		campoDescricao.sendKeys(descricao);
	}

	public void comDataDeLancamento(String dataLancamento) {
		campoData.sendKeys(dataLancamento);
	}

	public void comValor(String valor) {
		campoValor.sendKeys(valor);
	}

	public void comObservacao(String observacao) {
		campoObservacao.sendKeys(observacao);
	}
	
	public void selecionarConta(String nomeConta) {
		Select comboConta = new Select(driver.findElement(By.id("comboConta")));
		comboConta.selectByVisibleText(nomeConta);
	}

	public void selecionarTipoLancamento(String tipoLancamento) {
		Select comboTipoLancamento = new Select(driver.findElement(By.id("comboTipoLancamento")));
		comboTipoLancamento.selectByVisibleText(tipoLancamento);
	}

	public void clicarEmSalvar() {
		botaoSalvar.click();
		
		alertUtils = new AlertUtils(driver);
		alertUtils.clicarEmOk();
	}

	public String getMensagemAlerta() {
		return alertUtils.getMensagem();
	}
}