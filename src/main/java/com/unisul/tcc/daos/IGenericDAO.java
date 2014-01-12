package com.unisul.tcc.daos;

import java.util.List;

public interface IGenericDAO<T> {
	public List<T> listarTodos();
	public void salvar(T t);
	public void atualizar(T t);
	public void excluir(Long id);
	public T buscarPeloId(Long id);
}
