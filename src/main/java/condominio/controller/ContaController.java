package condominio.controller;

import condominio.domain.Conta;
import condominio.domain.ContaBancaria;
import condominio.domain.enums.TipoConta;
import condominio.domain.enums.TipoContaBancaria;
import condominio.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("sindico/contas")
public class ContaController {

	@Autowired
	private ContaService contaService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "financeiro", "contas" };
	}

	@ModelAttribute("tipos")
	public TipoConta[] tiposConta() {
		return TipoConta.values();
	}

	@ModelAttribute("tiposBc")
	public TipoContaBancaria[] tiposContaBancaria() {
		return TipoContaBancaria.values();
	}

	@GetMapping({ "", "/", "/lista" })
	public ModelAndView getContas(@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
		model.addAttribute("contas",
				contaService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
		model.addAttribute("conteudo", "contaLista");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getContaCadastro(@ModelAttribute("conta") Conta conta, ModelMap model) {
		conta.setSaldoInicial(BigDecimal.ZERO);
		model.addAttribute("tipo", "");
		model.addAttribute("conteudo", "contaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/{idConta}/cadastro")
	public ModelAndView getContaEditar(@PathVariable("idConta") Long idConta, ModelMap model) {
		Conta conta = contaService.ler(idConta);
		if (conta instanceof ContaBancaria) {
			model.addAttribute("conta", conta);
			model.addAttribute("tipo", TipoConta.BC);
		} else {
			model.addAttribute("conta", conta);
			model.addAttribute("tipo", TipoConta.CX);
		}
		model.addAttribute("conteudo", "contaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping(value = "/cadastro", params = { "CX" })
	public ModelAndView postContaCadastro(@Valid @ModelAttribute("conta") Conta conta, BindingResult validacao,
			ModelMap model) {
		contaService.validar(conta, validacao);
		if (validacao.hasErrors()) {
			conta.setIdConta(null);
			model.addAttribute("tipo", TipoConta.CX);
			model.addAttribute("conteudo", "contaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		contaService.salvar(conta);
		return new ModelAndView("redirect:/sindico/contas");
	}

	@PostMapping(value = "/cadastro", params = { "BC" })
	public ModelAndView postContaBancariaCadastro(@Valid @ModelAttribute("conta") ContaBancaria conta,
			BindingResult validacao, ModelMap model) {
		contaService.validar(conta, validacao);
		if (validacao.hasErrors()) {
			conta.setIdConta(null);
			model.addAttribute("tipo", TipoConta.BC);
			model.addAttribute("conteudo", "contaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		contaService.salvar(conta);
		return new ModelAndView("redirect:/sindico/contas");
	}

	@PutMapping(value = "/cadastro", params = { "CX" })
	public ModelAndView putContaCadastro(@Valid @ModelAttribute("conta") Conta conta, BindingResult validacao,
			ModelMap model) {
		contaService.validar(conta, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("tipo", TipoConta.CX);
			model.addAttribute("conteudo", "contaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		contaService.editar(conta);
		return new ModelAndView("redirect:/sindico/contas");
	}

	@PutMapping(value = "/cadastro", params = { "BC" })
	public ModelAndView putContaBancariaCadastro(@Valid @ModelAttribute("conta") ContaBancaria conta,
			BindingResult validacao, ModelMap model) {
		contaService.validar(conta, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("tipo", TipoConta.BC);
			model.addAttribute("conteudo", "contaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		contaService.editar(conta);
		return new ModelAndView("redirect:/sindico/contas");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteContaCadastro(@RequestParam("idObj") Long idObj) {
		contaService.excluir(contaService.ler(idObj));
		return new ModelAndView("redirect:/sindico/contas");
	}

}
