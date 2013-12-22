package com.unisul.tcc.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.unisul.tcc.beans.Conta;
import com.unisul.tcc.beans.Lancamento;
import com.unisul.tcc.beans.TipoLancamento;

public class LancamentoDAO implements IGenericDAO<Lancamento>{
	private EntityManagerFactory emf;
	
	public LancamentoDAO() {
		emf = PersistenceManager.getIstance().getEntityManagerFactory();
	}
	
	public LancamentoDAO(EntityManagerFactory factory) {
		emf = factory;
	}

	@Override
	public List<Lancamento> listarTodos() {
		EntityManager entityManager = emf.createEntityManager();
		try {
			return entityManager.createQuery("from Lancamento l order by l.id asc", Lancamento.class).getResultList();
		} finally {
			entityManager.close();
		}
	}

	@Override
	public void salvar(Lancamento lancamento) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			EntityTransaction transaction = entityManager.getTransaction();
			try {
				transaction.begin();

				entityManager.persist(lancamento);
				
				transaction.commit();
			} catch (PersistenceException e) {
				throw new RuntimeException(e);
			} finally {
				if (transaction.isActive()) {
					transaction.rollback();
				}
			}	
		} finally {
			entityManager.close();
		}
	}

	@Override
	public void atualizar(Lancamento lancamento) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			EntityTransaction transaction = entityManager.getTransaction();
			try {
				transaction.begin();
				Lancamento lancamentoEncontrado = entityManager.find(Lancamento.class, lancamento.getId());
				
				if (lancamentoEncontrado != null) {
					entityManager.merge(lancamento);
				}
				
				transaction.commit();
			} catch (PersistenceException e) {
				throw new RuntimeException(e);
			} finally {
				if (transaction.isActive()) {
					transaction.rollback();
				}
			}	
		} finally {
			entityManager.close();
		}
	}

	@Override
	public void excluir(Long id) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			EntityTransaction transaction = entityManager.getTransaction();
			try {
				Lancamento lancamento = entityManager.find(Lancamento.class, id);
				
				if (lancamento != null) {
					transaction.begin();
					
					entityManager.remove(lancamento);

					transaction.commit();
				}
			} finally {
				if (transaction.isActive()) {
					transaction.rollback();
				}
			}
		} finally {
			entityManager.close();
		}
	}
	
	@Override
	public Lancamento buscarPeloId(Long id) {
		EntityManager entityManager = emf.createEntityManager();
		Lancamento lancamento = null;
			try {				
				Query query = entityManager.createQuery("select l from Lancamento l where l.id = :id");
				query.setParameter("id", id);
				
				lancamento = (Lancamento) query.getSingleResult();
			} catch (NoResultException e) {
				lancamento = null;
			} finally {
				entityManager.close();
			}
			
		return lancamento;
	}
	
	public List<Lancamento> buscarLancamentosPeloTipo(TipoLancamento tipoLancamento) {
		EntityManager entityManager = emf.createEntityManager();
		List<Lancamento> lancamentos = null;
		try {				
				Query query = entityManager.createQuery("select l from Lancamento l where l.tipoLancamento = :tipoLancamento");
				query.setParameter("tipoLancamento", tipoLancamento);
				
				lancamentos = query.getResultList();
			} finally {
				entityManager.close();
			}
		
		return lancamentos;
	}
	
	public List<Lancamento> buscarLancamentosPelaConta(Conta conta) {
		EntityManager entityManager = emf.createEntityManager();
		List<Lancamento> lancamentos = null;
		try {				
				Query query = entityManager.createQuery("select l from Lancamento l where l.conta = :conta");
				query.setParameter("conta", conta);
				
				lancamentos = query.getResultList();
			} finally {
				entityManager.close();
			}
		
		return lancamentos;
	}
}
