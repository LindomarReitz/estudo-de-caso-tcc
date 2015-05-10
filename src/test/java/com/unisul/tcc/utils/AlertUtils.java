package com.unisul.tcc.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AlertUtils {
	private WebDriver driver;
	private Alert alerta;
	private String textoAlerta;
	
	public AlertUtils(WebDriver driver) {
		this.driver = driver;
	}

	public void clicarEmOk() {
		new WebDriverWait(driver, 10)
				.until(ExpectedConditions.alertIsPresent());

		alerta = driver.switchTo().alert();
		textoAlerta = alerta.getText();
		alerta.accept();
	}

	public String getMensagem() {
		return textoAlerta;
	}
}
