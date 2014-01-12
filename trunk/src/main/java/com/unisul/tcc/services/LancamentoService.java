package com.unisul.tcc.services;

import java.util.List;

import com.unisul.tcc.beans.Conta;
import com.unisul.tcc.beans.Lancamento;
import com.unisul.tcc.daos.ContaDAO;
import com.unisul.tcc.daos.LancamentoDAO;

public class LancamentoService {
	private LancamentoDAO lancamentoDAO;
	private ContaDAO contaDAO;
	
	public LancamentoService() {
		lancamentoDAO = new LancamentoDAO();
		contaDAO = new ContaDAO();
	}
	
	public LancamentoService(LancamentoDAO lancamentoDAO, ContaDAO contaDAO) {
		this.lancamentoDAO = lancamentoDAO;
		this.contaDAO = contaDAO;
	}
	
	public List<Lancamento> listarTodos() {
		return lancamentoDAO.listarTodos();
	}
	
	public void cadastrarLancamento(Lancamento lancamento) {
		if (lancamento != null) {
			lancamento.lancar();
			
			Conta conta = lancamento.getConta();

			lancamentoDAO.salvar(lancamento);
			contaDAO.atualizar(conta);
		}
	}
	
	public void atualizarLancamento(Lancamento lancamento) {
		if (lancamento != null) {
			Conta conta = lancamento.getConta();
			
			int posicao = 0;
			for (Lancamento l : conta.getLancamentos()) {
				if (l.getId().equals(lancamento.getId())) {
					conta.getLancamentos().remove(posicao);
					break;
				}
				posicao++;
			}

			lancamento.lancar();

			lancamentoDAO.atualizar(lancamento);
			contaDAO.atualizar(conta);
		}
	}
	
	public void excluirLancamento(Long id) {
		Lancamento lancamento = lancamentoDAO.buscarPeloId(id);
		
		Conta conta = lancamento.getConta();
		conta.getLancamentos().remove(lancamento);
		conta.calcularSaldoAtual();
		
		contaDAO.atualizar(conta);
		lancamentoDAO.excluir(id);
	}
	
	public Lancamento buscarLancamentoPeloId(Long id) {
		return lancamentoDAO.buscarPeloId(id);
	}
}