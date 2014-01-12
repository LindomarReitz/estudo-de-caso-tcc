package com.unisul.tcc.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.unisul.tcc.beans.Banco;

public class BancoDAO implements IGenericDAO<Banco> {
	private EntityManagerFactory emf;
	
	public BancoDAO(EntityManagerFactory factory) {
		emf = PersistenceManager.getIstance().getEntityManagerFactory();
	}
	
	public List<Banco> listarTodos() {
		EntityManager entityManager = emf.createEntityManager();
		try {
			return entityManager.createQuery("from Banco", Banco.class).getResultList();
		} finally {
			entityManager.close();
		}
	}

	public void salvar(Banco t) {
		// TODO Auto-generated method stub
		
	}

	public void atualizar(Banco t) {
		// TODO Auto-generated method stub
		
	}

	public void excluir(Long id) {
		// TODO Auto-generated method stub
		
	}

	public Banco buscarPeloId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
