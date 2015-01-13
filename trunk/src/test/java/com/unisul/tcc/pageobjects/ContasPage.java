package com.unisul.tcc.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ContasPage {
	private WebDriver driver;
	private List<WebElement> linhas;
	private List<WebElement> colunas;
	
	public ContasPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void visitar() {
		driver.findElement(By.id("botaoContas")).click();
		
		WebElement tabela = driver.findElement(By.tagName("table"));
		linhas = tabela.findElements(By.tagName("tr"));
		colunas = linhas.get(1).findElements(By.tagName("td"));
	}
	
	public String getValor() {
		WebElement colunaValor = colunas.get(5);
		
		return colunaValor.getText();
	}
}
