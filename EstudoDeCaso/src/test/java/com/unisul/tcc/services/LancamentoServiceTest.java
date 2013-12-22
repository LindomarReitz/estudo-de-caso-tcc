package com.unisul.tcc.services;

import static com.unisul.tcc.beans.TipoLancamento.DEPOSITO;
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

import com.unisul.tcc.beans.Banco;
import com.unisul.tcc.beans.Conta;
import com.unisul.tcc.beans.Lancamento;
import com.unisul.tcc.builders.CriadorDeLancamento;
import com.unisul.tcc.daos.ContaDAO;
import com.unisul.tcc.daos.LancamentoDAO;

public class LancamentoServiceTest {
	private LancamentoService lancamentoService;
	private LancamentoDAO lancamentoDAO;
	private Lancamento lancamento;
	private ContaDAO contaDAO;
	private Conta conta;
	
	@Before
	public void setUp() {
		lancamentoDAO = mock(LancamentoDAO.class);
		contaDAO = mock(ContaDAO.class);
		
		lancamentoService = new LancamentoService(lancamentoDAO, contaDAO);
		
		conta = new Conta();
		conta.setNome("Conta corrente");
		conta.setNumeroAgencia(1234);
		conta.setNumeroConta(13456698);

		Banco banco = new Banco();
		banco.setNome("Santander");

		conta.setBanco(banco);
		
		Lancamento deposito = new CriadorDeLancamento()
			.comId(1L)
			.paraAConta(conta)
			.comADescricao("Salário")
			.noValorDe(5000d)
			.naDataDe(Calendar.getInstance())
			.doTipo(DEPOSITO)
			.construir();
		
		deposito.lancar();
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
				.comId(2L)
				.comADescricao("Lancamento 1")
				.paraAConta(conta)
				.noValorDe(500d)
				.naDataDe(Calendar.getInstance())
				.doTipo(SAQUE)
				.construir();
		
		lancamentoService.cadastrarLancamento(lancamento);
		
		verify(lancamentoDAO, times(1)).salvar(lancamento);
		verify(contaDAO, times(1)).atualizar(conta);
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
				.comId(2L)
				.comADescricao("Lancamento 1")
				.paraAConta(conta)
				.noValorDe(500d)
				.naDataDe(Calendar.getInstance())
				.doTipo(SAQUE)
				.construir();

		lancamentoService.atualizarLancamento(lancamento);
		
		verify(lancamentoDAO, times(1)).atualizar(lancamento);
		verify(contaDAO, times(1)).atualizar(conta);
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
				.paraAConta(conta)
				.noValorDe(500d)
				.naDataDe(Calendar.getInstance())
				.doTipo(SAQUE)
				.construir();
		
		when(lancamentoDAO.buscarPeloId(lancamento.getId())).thenReturn(lancamento);
		
		lancamentoService.excluirLancamento(lancamento.getId());
	
		verify(contaDAO, times(1)).atualizar(conta);
		verify(lancamentoDAO, times(1)).excluir(lancamento.getId());
	}
}
