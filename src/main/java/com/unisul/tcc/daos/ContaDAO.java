package com.unisul.tcc.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.unisul.tcc.beans.Conta;

public class ContaDAO implements IGenericDAO<Conta> {
	private EntityManagerFactory emf;
	
	public ContaDAO() {
		emf = PersistenceManager.getIstance().getEntityManagerFactory();
	}

	public ContaDAO(EntityManagerFactory factory) {
		this.emf = factory;
	}
	
	public List<Conta> listarTodos() {
		EntityManager entityManager = emf.createEntityManager();
		try {
			return entityManager.createQuery("from Conta c order by c.id asc", Conta.class).getResultList();
		} finally {
			entityManager.close();
		}
	}

	public void salvar(Conta conta) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			EntityTransaction transaction = entityManager.getTransaction();
			try {
				transaction.begin();

				entityManager.persist(conta);
				
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

	public void atualizar(Conta conta) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			EntityTransaction transaction = entityManager.getTransaction();
			try {
				transaction.begin();
				Conta contaEncontrada = entityManager.find(Conta.class, conta.getId());
				
				if (contaEncontrada != null) {
					entityManager.merge(conta);
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

	public void excluir(Long id) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			EntityTransaction transaction = entityManager.getTransaction();
			try {
				Conta conta = entityManager.find(Conta.class, id);
				
				if (conta != null) {
					transaction.begin();
					
					entityManager.remove(conta);

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

	public Conta buscarPeloId(Long id) {
		EntityManager entityManager = emf.createEntityManager();
		Conta conta = null;
			try {				
				Query query = entityManager.createQuery("select c from Conta c where c.id = :id");
				query.setParameter("id", id);
				
				conta = (Conta) query.getSingleResult();
			} catch (NoResultException e) {
				conta = null;
			} finally {
				entityManager.close();
			}
			
		return conta;
	}

}
