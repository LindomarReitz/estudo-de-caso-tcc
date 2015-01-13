package com.unisul.tcc.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.unisul.tcc.utils.AlertUtils;

public class LancamentosPage {
	private WebDriver driver;
	private AlertUtils alertUtils;
	
	@FindBy(id = "botaoCadastrar")
	private WebElement botaoCadastrar;
	
	@FindBy(id = "botaoLancamentos")
	private WebElement botaoLancamentos;
	
	@FindBy(tagName = "table")
	private WebElement tabela;
	
	public LancamentosPage(WebDriver driver) {
		this.driver = driver;
	}

	public void visitar() {
		botaoLancamentos.click();
	}

	public CadastroLancamentoPage cadastrar() {
		botaoCadastrar.click();
		
		return PageFactory.initElements(driver, CadastroLancamentoPage.class);
	}

	public EdicaoLancamentoPage editar() {
		WebElement botaoEditar = ultimoLancamentoCadastrado().findElement(By.linkText("Editar"));
		botaoEditar.click();
		
		return PageFactory.initElements(driver, EdicaoLancamentoPage.class);
	}

	public void excluir() {
		WebElement botaoExcluir = ultimoLancamentoCadastrado().findElement(By.linkText("Excluir"));
		botaoExcluir.click();
		
		alertUtils = new AlertUtils(driver);
		// Clique para confirmar o cancelamento
		alertUtils.clicarEmOk();
		alertUtils.clicarEmOk();
	}

	private WebElement ultimoLancamentoCadastrado() {
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
		return alertUtils.getMensagem();
	}
}
