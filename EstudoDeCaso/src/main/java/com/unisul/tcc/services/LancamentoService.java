package com.unisul.tcc.services;

import java.util.List;

import com.unisul.tcc.beans.Lancamento;
import com.unisul.tcc.daos.LancamentoDAO;

public class LancamentoService {
	private LancamentoDAO lancamentoDAO;
	
	public LancamentoService() {
		lancamentoDAO = new LancamentoDAO();
	}
	
	public LancamentoService(LancamentoDAO lancamentoDAO) {
		this.lancamentoDAO = lancamentoDAO;
	}
	
	public List<Lancamento> listarTodos() {
		return lancamentoDAO.listarTodos();
	}
	
	public void cadastrarLancamento(Lancamento lancamento) {
		if (lancamento != null) {
			lancamentoDAO.salvar(lancamento);
		}
	}
	
	public void atualizarLancamento(Lancamento lancamento) {
		if (lancamento != null) {
			lancamentoDAO.atualizar(lancamento);
		}
	}
	
	public void excluirLancamento(Long id) {
		lancamentoDAO.excluir(id);
	}
	
	public Lancamento buscarLancamentoPeloId(Long id) {
		return lancamentoDAO.buscarPeloId(id);
	}
}
