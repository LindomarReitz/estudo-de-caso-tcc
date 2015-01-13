package com.unisul.tcc.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.unisul.tcc.utils.AlertUtils;

public class EdicaoLancamentoPage {
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

	@FindBy(id = "botaoAtualizar")
	private WebElement botaoAtualizar;
	
	public EdicaoLancamentoPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void comDescricao(String descricao) {
		campoDescricao.clear();
		campoDescricao.sendKeys(descricao);
	}

	public void comDataDeLancamento(String dataLancamento) {
		campoData.clear();
		campoData.sendKeys(dataLancamento);
	}
	
	public void comValor(String valor) {
		campoValor.clear();
		campoValor.sendKeys(valor);
	}
	
	public void comObservacao(String observacao) {
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
		botaoAtualizar.click();
		
		alertUtils = new AlertUtils(driver);
		alertUtils.clicarEmOk();
	}
	
	public String getMensagemAlerta() {
		return alertUtils.getMensagem();
	}
}
