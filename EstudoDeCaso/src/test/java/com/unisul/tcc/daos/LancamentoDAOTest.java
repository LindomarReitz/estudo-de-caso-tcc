package com.unisul.tcc.daos;

import static com.unisul.tcc.beans.TipoLancamento.DEPOSITO;
import static com.unisul.tcc.beans.TipoLancamento.SAQUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.sql.Connection;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.unisul.tcc.beans.Lancamento;
import com.unisul.tcc.builders.CriadorDeLancamento;
import com.unisul.tcc.daos.LancamentoDAO;

public class LancamentoDAOTest {
	private LancamentoDAO dao;
	private IDatabaseConnection dbConn;
	private IDataSet dataSet;
	private EntityManagerFactory factory;
	
	@Before
	public void setUp() throws Exception {
		factory = Persistence.createEntityManagerFactory("test");
		EntityManager entityManager = factory.createEntityManager();

		dao = new LancamentoDAO(factory);
		
		Session session = entityManager.unwrap(Session.class);
		SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
		ConnectionProvider cp = sfi.getConnectionProvider();
		Connection conn = cp.getConnection();
		
		dbConn = new DatabaseConnection(conn);
		dataSet = new FlatXmlDataSet(new File("lancamentos-dataset.xml"));
		
		DatabaseOperation.CLEAN_INSERT.execute(dbConn, dataSet);
	}

	@After
	public void tearDown() throws Exception {
		DatabaseOperation.DELETE_ALL.execute(dbConn, dataSet);
	}
	
	@Test
	public void deveSalvarUmLancamento() {
		Lancamento lancamento = new CriadorDeLancamento()
				.comADescricao("Compra no mercado")
				.noValorDe(200d)
				.doTipo(SAQUE)
				.naDataDe(Calendar.getInstance())
				.comAObservacao("teste")
				.construir();
	
		dao.salvar(lancamento);
		
		java.util.List<Lancamento> lancamentos = dao.listarTodos();

		Lancamento ultimoLancamentoSalvo = lancamentos.get(lancamentos.size() - 1);
		
		assertEquals(200d, ultimoLancamentoSalvo.getValor());
	}
	
	@Test(expected = RuntimeException.class)
	public void naoDeveSalvarUmLancamentoInvalido() {
		Lancamento lancamento = new CriadorDeLancamento()
				.noValorDe(200d)
				.naDataDe(Calendar.getInstance())
				.comAObservacao("teste")
				.construir();
	
		dao.salvar(lancamento);
	}
	
	@Test
	public void deveAtualizarUmLancamento() {
		Lancamento lancamento = dao.buscarPeloId(1L);
		lancamento.setValor(300d);
		
		dao.atualizar(lancamento);
		
		Lancamento lancamentoAtualizado = dao.buscarPeloId(1L);
		assertEquals(300d, lancamentoAtualizado.getValor());		
	}

	@Test(expected = RuntimeException.class)
	public void naoDeveAtualizarUmLancamentoInvalido() {
		Lancamento lancamento = dao.buscarPeloId(1L);
		lancamento.setDescricao(null);
		
		dao.atualizar(lancamento);
	}
	
	@Test
	public void deveExcluirUmLancamento() {
		dao.excluir(1L);
		
		Lancamento lancamento = dao.buscarPeloId(1L);
		assertNull(lancamento);
	}
	
	@Test
	public void naoDeveExcluirUmLancamentoInexistente() {
		dao.excluir(99L);
		
		Lancamento lancamento = dao.buscarPeloId(99L);
		assertNull(lancamento);
	}
	
	@Test
	public void deveListarTodosLancamentos() {
		java.util.List<Lancamento> lancamentos = dao.listarTodos();
		
		assertNotNull(lancamentos);
		assertEquals(4, lancamentos.size());
		assertEquals(1L, lancamentos.get(0).getId());
	}
	
	@Test
	public void deveListarTodosLancamentosDoTipoSaque() {
		List<Lancamento> lancamentos = dao.buscarLancamentosPeloTipo(SAQUE);
		
		assertEquals(2, lancamentos.size());
		assertEquals(1L, lancamentos.get(0).getId());
		assertEquals(3L, lancamentos.get(1).getId());
	}
	
	@Test
	public void deveListarTodosLancamentosDoTipoDeposito() {
		List<Lancamento> lancamentos = dao.buscarLancamentosPeloTipo(DEPOSITO);
		
		assertEquals(2, lancamentos.size());
		assertEquals(2L, lancamentos.get(0).getId());
		assertEquals(4L, lancamentos.get(1).getId());
	}
	
	@Test
	public void deveBuscarUmLancamentoPeloSeuID() {
		Lancamento lancamento = dao.buscarPeloId(1L);
		
		assertNotNull(lancamento);
		assertEquals("Mercado", lancamento.getDescricao());
	}
}
