package com.unisul.tcc.dao;

import java.util.List;

import com.unisul.tcc.beans.Lancamento;

public class Main {
	public static void main(String[] args) {
		LancamentoDAO lancamentoDAO = new LancamentoDAO();
		List<Lancamento> listarTodos = lancamentoDAO.listarTodos();
		System.out.println(listarTodos.size());
	}
}
