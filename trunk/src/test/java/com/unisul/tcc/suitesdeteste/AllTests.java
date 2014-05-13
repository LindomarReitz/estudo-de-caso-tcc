package com.unisul.tcc.suitesdeteste;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestesDeUnidade.class, TestesDeIntegracao.class, TestesDeSistema.class })
public class AllTests {
	
}