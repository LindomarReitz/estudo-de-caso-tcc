package com.unisul.tcc.daos;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {
	public static final boolean DEBUG = false;
	private static final PersistenceManager singleton = new PersistenceManager();
	protected EntityManagerFactory emf;

	private PersistenceManager() {

	}

	public static PersistenceManager getIstance() {
		return singleton;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (emf == null)
			createEntityManagerFactory();
		return emf;
	}

	public void closeEntityManagerFactory() {
		if (emf != null) {
			emf.close();
			emf = null;
			if (DEBUG)
				System.out.println("Persistence finished at " + new java.util.Date());
		}
	}

	protected void createEntityManagerFactory() {
		this.emf = Persistence.createEntityManagerFactory("persist");
		if (DEBUG)
			System.out.println("Persistence started at " + new java.util.Date());
	}
}