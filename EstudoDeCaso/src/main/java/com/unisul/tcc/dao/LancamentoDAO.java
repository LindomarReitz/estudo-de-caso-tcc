package com.unisul.tcc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.unisul.tcc.beans.Lancamento;

public class LancamentoDAO implements IGenericDAO<Lancamento>{
	private EntityManagerFactory emf;
	
	public LancamentoDAO() {
		emf = PersistenceManager.getIstance().getEntityManagerFactory();
	}
	
	public List<Lancamento> listarTodos() {
		EntityManager entityManager = emf.createEntityManager();
		try {
			return entityManager.createQuery("from Lancamento", Lancamento.class).getResultList();
		} finally {
			entityManager.close();
		}
	}

	public void salvar(Lancamento lancamento) {
		// TODO Auto-generated method stub
	}

	public void atualizar(Lancamento lancamento) {
		// TODO Auto-generated method stub
	}

	public void excluir(Long id) {
		// TODO Auto-generated method stub
	}

	public Lancamento buscarPeloId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
