package com.unisul.tcc.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unisul.tcc.beans.Conta;
import com.unisul.tcc.beans.Lancamento;
import com.unisul.tcc.daos.ContaDAO;
import com.unisul.tcc.exceptions.SaldoInsuficienteException;
import com.unisul.tcc.services.LancamentoService;

@Controller
public class LancamentoController {
	private LancamentoService service;
	private ContaDAO contaDAO;
	
	public LancamentoController() {
		service = new LancamentoService();
		contaDAO = new ContaDAO();
	}
	
	@RequestMapping(value = "listagemLancamentos")
	public String listar(Model model) {
		List<Lancamento> lancamentos = service.listarTodos();
		List<Conta> contas = contaDAO.listarTodos();
		
		model.addAttribute("lancamentos", lancamentos);
		model.addAttribute("contas", contas);
		
		return "lancamentos";
	}
	
	@RequestMapping(value = "cadastrarLancamento")
	public String cadastrar(Model model) {
		List<Conta> contas = contaDAO.listarTodos();
		model.addAttribute("contas", contas);
		
		return "cadastro_lancamento";
	}
	
	@RequestMapping(value = "salvarLancamento", method = RequestMethod.POST)
	public @ResponseBody String salvar(@RequestParam("idConta") Long idConta, @RequestParam("dataLancamento") String data,
			@ModelAttribute(value="lancamento") Lancamento lancamento) {
		Conta conta = contaDAO.buscarPeloId(idConta);
		lancamento.setConta(conta);
			
		Calendar dataLancamento = formatarData(data);			
		lancamento.setData(dataLancamento);
		try {
			service.cadastrarLancamento(lancamento);
		} catch(SaldoInsuficienteException e) {
			return "saldoInsuficiente";
		}
	
		return "sucesso";
	}
	
	@RequestMapping(value = "editar")
	public String editarLancamento(Model model, Long id) {
		Lancamento lancamento = service.buscarLancamentoPeloId(id);
		
		List<Conta> contas = contaDAO.listarTodos();

		model.addAttribute("contas", contas);
		model.addAttribute("lancamento", lancamento);

		return "edicao_lancamento";	
	}
	
	@RequestMapping(value = "editarLancamento", method = RequestMethod.POST)
	public @ResponseBody String editar(@RequestParam("idConta") Long idConta, @RequestParam("dataLancamento") String data,
			@ModelAttribute(value="lancamento") Lancamento lancamento) {
		Conta conta = contaDAO.buscarPeloId(idConta);
		lancamento.setConta(conta);

		Calendar dataLancamento = formatarData(data);
		lancamento.setData(dataLancamento);
		
		try {
			service.atualizarLancamento(lancamento);
		} catch(SaldoInsuficienteException e) {
			return "saldoInsuficiente";
		}
		
		return "sucesso";
	}
	
	@RequestMapping(value = "excluirLancamento", method = RequestMethod.POST)
	public @ResponseBody String excluir(@RequestParam("idLancamento") Long id) {
		service.excluirLancamento(id);
		
		return "sucesso";
	}

	@RequestMapping(value = "excluirTodosLancamentos")
	public String excluirTodosLancamentos() {
		List<Lancamento> lancamentos = service.listarTodos();
		
		for (Lancamento lancamento : lancamentos) {
			service.excluirLancamento(lancamento.getId());
		}
		
		return "lancamentos";
	}
	
	public Calendar formatarData(String data) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = format.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
			
		Calendar dataLancamento = Calendar.getInstance();
		dataLancamento.setTime(date);
	
		return dataLancamento;
	}
}