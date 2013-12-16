package com.unisul.tcc.controllers;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.unisul.tcc.beans.Lancamento;
import com.unisul.tcc.services.LancamentoService;

@Controller
public class LancamentoController {
	
	@RequestMapping(value = "listagemLancamentos")
	public String listar(Model model) {
		LancamentoService service = new LancamentoService();
		
		List<Lancamento> lancamentos = service.listarTodos();
		
		model.addAttribute("lancamentos", lancamentos);
		
		return "lancamentos";
	}
	
	@RequestMapping(value = "cadastrarLancamento")
	public String cadastrar() {
		return "cadastro_lancamento";
	}
	
	@RequestMapping(value = "salvarLancamento", method = RequestMethod.POST)
	public void salvar(@ModelAttribute(value="lancamento") Lancamento lancamento) throws ParseException {
		LancamentoService service = new LancamentoService();
		
		lancamento.setData(Calendar.getInstance());
	
		service.cadastrarLancamento(lancamento);
	}
	
	public void editar() {
		
	}
	
	public void excluir() {
		
	}
}
