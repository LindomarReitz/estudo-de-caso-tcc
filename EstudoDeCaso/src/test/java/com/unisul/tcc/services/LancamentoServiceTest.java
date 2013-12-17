package com.unisul.tcc.services;

import static com.unisul.tcc.beans.TipoLancamento.SAQUE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.unisul.tcc.beans.Conta;
import com.unisul.tcc.beans.Lancamento;
import com.unisul.tcc.builders.CriadorDeLancamento;
import com.unisul.tcc.daos.LancamentoDAO;

public class LancamentoServiceTest {
	private LancamentoService lancamentoService;
	private LancamentoDAO lancamentoDAO;
	private Lancamento lancamento;
	
	@Before
	public void setUp() {
		lancamentoDAO = mock(LancamentoDAO.class);
		
		lancamentoService = new LancamentoService(lancamentoDAO);
	}
	
	@Test
	public void deveListarLancamentosCadastrados() {
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();
		
		Lancamento lancamento1 = new CriadorDeLancamento()
				.comId(1L)
				.comADescricao("Lancamento 1")
				.paraAConta(new Conta())
				.noValorDe(500d)
				.naDataDe(Calendar.getInstance())
				.doTipo(SAQUE)
				.construir();

		Lancamento lancamento2 = new CriadorDeLancamento()
				.comId(2L)
				.comADescricao("Lancamento 2")
				.paraAConta(new Conta())
				.noValorDe(500d)
				.naDataDe(Calendar.getInstance())
				.doTipo(SAQUE)
				.construir();
		
		lancamentos.add(lancamento1);
		lancamentos.add(lancamento2);
		
		lancamentoService.listarTodos();
		
		when(lancamentoDAO.listarTodos()).thenReturn(lancamentos);
		
		int tamanhoEsperadoDaLista = 2;
		
		assertEquals(tamanhoEsperadoDaLista, lancamentos.size());
		
		assertEquals(1L, lancamentos.get(0).getId());
		assertEquals("Lancamento 1", lancamentos.get(0).getDescricao());

		assertEquals(2L, lancamentos.get(1).getId());
		assertEquals("Lancamento 2", lancamentos.get(1).getDescricao());
	}
	
	@Test
	public void deveCadastrarUmLancamento() {
		Lancamento lancamento = new CriadorDeLancamento()
				.comId(1L)
				.comADescricao("Lancamento 1")
				.paraAConta(new Conta())
				.noValorDe(500d)
				.naDataDe(Calendar.getInstance())
				.doTipo(SAQUE)
				.construir();
		
		lancamentoService.cadastrarLancamento(lancamento);
		
		verify(lancamentoDAO, times(1)).salvar(lancamento);
	}
	
	@Test
	public void naoDeveCadastrarQuandoUmLancamentoForNulo() {
		lancamento = null;
		
		lancamentoService.cadastrarLancamento(lancamento);
		
		verify(lancamentoDAO, never()).salvar(lancamento);
	}
	
	@Test
	public void deveAtualizarUmLancamento() {
		Lancamento lancamento = new CriadorDeLancamento()
				.comId(1L)
				.comADescricao("Lancamento 1")
				.paraAConta(new Conta())
				.noValorDe(500d)
				.naDataDe(Calendar.getInstance())
				.doTipo(SAQUE)
				.construir();

		lancamento.setValor(700d);
		
		lancamentoService.atualizarLancamento(lancamento);
		
		verify(lancamentoDAO, times(1)).atualizar(lancamento);
	}
	
	@Test
	public void naoDeveAtualizarUmLancamentoQuandoEstiverNulo() {
		lancamento = null;
		
		lancamentoService.atualizarLancamento(lancamento);
		
		verify(lancamentoDAO, never()).atualizar(lancamento);	
	}

	@Test
	public void deveExcluirUmLancamento() {
		Lancamento lancamento = new CriadorDeLancamento()
				.comId(1L)
				.comADescricao("Lancamento")
				.paraAConta(new Conta())
				.noValorDe(500d)
				.naDataDe(Calendar.getInstance())
				.doTipo(SAQUE)
				.construir();
		
		lancamentoService.excluirLancamento(lancamento.getId());
		
		verify(lancamentoDAO, times(1)).excluir(lancamento.getId());
	}
}
