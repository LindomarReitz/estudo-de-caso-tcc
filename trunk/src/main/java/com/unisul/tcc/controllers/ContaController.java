package com.unisul.tcc.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unisul.tcc.beans.Conta;
import com.unisul.tcc.daos.ContaDAO;

@Controller
public class ContaController {
	
	@RequestMapping(value = "listagemContas")
	public String listar(Model model) {
		ContaDAO dao = new ContaDAO();

		List<Conta> contas = dao.listarTodos();
		
		model.addAttribute("contas", contas);
		
		return "contas";
	}
}
