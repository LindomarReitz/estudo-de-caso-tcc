package com.unisul.tcc.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.unisul.tcc.beans.Conta;
import com.unisul.tcc.beans.Lancamento;
import com.unisul.tcc.beans.TipoLancamento;
import com.unisul.tcc.dao.LancamentoDAO;

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
		
		lancamento = new Lancamento();
		lancamento.setConta(new Conta());
		lancamento.setDescricao("Lancamento 1");
		lancamento.setData(Calendar.getInstance());
		lancamento.setTipoLancamento(TipoLancamento.SAQUE);
		lancamento.setValor(500d);
		lancamento.setObservacao("Lançamento");
		
		lancamentos.add(lancamento);
		
		lancamento = new Lancamento();
		lancamento.setConta(new Conta());
		lancamento.setDescricao("Lancamento 2");
		lancamento.setData(Calendar.getInstance());
		lancamento.setTipoLancamento(TipoLancamento.SAQUE);
		lancamento.setValor(600d);
		lancamento.setObservacao("Lançamento");
		
		lancamentos.add(lancamento);
		
		lancamentoService.listarTodos();
		
		when(lancamentoDAO.listarTodos()).thenReturn(lancamentos);
		
		int tamanhoEsperadoDaLista = 2;
		
		assertEquals(tamanhoEsperadoDaLista, lancamentos.size());
		assertEquals("Lancamento 1", lancamentos.get(0).getDescricao());
		assertEquals("Lancamento 2", lancamentos.get(1).getDescricao());
	}
	
	@Test
	public void deveCadastrarUmLancamento() {
		lancamento = new Lancamento();
		lancamento.setConta(new Conta());
		lancamento.setDescricao("Lancamento");
		lancamento.setData(Calendar.getInstance());
		lancamento.setTipoLancamento(TipoLancamento.SAQUE);
		lancamento.setValor(500d);
		lancamento.setObservacao("Lançamento");
		
		lancamentoService.cadastrarLancamento(lancamento);
		
		verify(lancamentoDAO, atMost(1)).salvar(lancamento);
	}
	
	@Test
	public void naoDeveCadastrarQuandoUmLancamentoForNulo() {
		lancamento = null;
		
		lancamentoService.cadastrarLancamento(lancamento);
		
		verify(lancamentoDAO, never()).salvar(lancamento);
	}
	
	@Test
	public void deveAtualizarUmLancamento() {
		lancamento = new Lancamento();
		lancamento.setConta(new Conta());
		lancamento.setDescricao("Lancamento");
		lancamento.setData(Calendar.getInstance());
		lancamento.setTipoLancamento(TipoLancamento.SAQUE);
		lancamento.setValor(500d);
		lancamento.setObservacao("Lançamento");
		
		lancamento.setValor(700d);
		
		lancamentoService.atualizarLancamento(lancamento);
		
		verify(lancamentoDAO, atMost(1)).atualizar(lancamento);
	}
	
	@Test
	public void naoDeveAtualizarUmLancamentoQuandoEstiverNulo() {
		lancamento = null;
		
		lancamentoService.atualizarLancamento(lancamento);
		
		verify(lancamentoDAO, never()).atualizar(lancamento);	
	}

	@Test
	public void deveExcluirUmLancamento() {
		lancamento = new Lancamento();
		lancamento.setId(2L);
		lancamento.setConta(new Conta());
		lancamento.setDescricao("Lancamento");
		lancamento.setData(Calendar.getInstance());
		lancamento.setTipoLancamento(TipoLancamento.SAQUE);
		lancamento.setValor(500d);
		lancamento.setObservacao("Lançamento");
		
		lancamentoService.excluirLancamento(lancamento.getId());
		
		verify(lancamentoDAO, atMost(1)).excluir(lancamento.getId());
	}
}
