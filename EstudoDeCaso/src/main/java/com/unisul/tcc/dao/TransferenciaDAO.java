package com.unisul.tcc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.unisul.tcc.beans.Transferencia;

public class TransferenciaDAO implements IGenericDAO<Transferencia> {
	private EntityManagerFactory emf;
	
	public TransferenciaDAO(EntityManagerFactory factory) {
		emf = PersistenceManager.getIstance().getEntityManagerFactory();
	}
	
	@Override
	public List<Transferencia> listarTodos() {
		EntityManager entityManager = emf.createEntityManager();
		try {
			return entityManager.createQuery("from Transferencia", Transferencia.class).getResultList();
		} finally {
			entityManager.close();
		}
	}

	@Override
	public void salvar(Transferencia transferencia) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			EntityTransaction transaction = entityManager.getTransaction();
			try {
				transaction.begin();

				entityManager.persist(transferencia);
				
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
	public void atualizar(Transferencia transferencia) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			EntityTransaction transaction = entityManager.getTransaction();
			try {
				transaction.begin();
				Transferencia transferenciaEncontrada = entityManager.find(Transferencia.class, transferencia.getId());
				
				if (transferenciaEncontrada != null) {
					entityManager.merge(transferencia);
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
				Transferencia transferencia = entityManager.find(Transferencia.class, id);
				
				if (transferencia != null) {
					transaction.begin();
					
					entityManager.remove(transferencia);

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
	public Transferencia buscarPeloId(Long id) {
		EntityManager entityManager = emf.createEntityManager();
			Transferencia transferencia = null;
			try {				
				Query query = entityManager.createQuery("select t from Transferencia t where t.id = :id");
				query.setParameter("id", id);
				
				transferencia = (Transferencia) query.getSingleResult();
			} catch (NoResultException e) {
				transferencia = null;
			} finally {
				entityManager.close();
			}
			
		return transferencia;
	}
}
