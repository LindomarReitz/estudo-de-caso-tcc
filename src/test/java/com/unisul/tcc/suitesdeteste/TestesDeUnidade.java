package com.unisul.tcc.suitesdeteste;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.unisul.tcc.beans.LancamentoTest;
import com.unisul.tcc.beans.TransferenciaTest;

@RunWith(Suite.class)
@SuiteClasses({LancamentoTest.class, TransferenciaTest.class})
public class TestesDeUnidade {

}
