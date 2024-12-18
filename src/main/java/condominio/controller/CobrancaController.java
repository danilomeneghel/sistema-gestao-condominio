package condominio.controller;

import condominio.domain.Cobranca;
import condominio.domain.Moradia;
import condominio.domain.enums.MotivoBaixa;
import condominio.domain.enums.MotivoEmissao;
import condominio.domain.enums.SituacaoCobranca;
import condominio.service.CobrancaService;
import condominio.service.MoradiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("sindico/cobrancas")
public class CobrancaController {

	@Autowired
	private CobrancaService cobrancaService;

	@Autowired
	MoradiaService moradiaService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "financeiro", "cobrancas" };
	}

	@ModelAttribute("motivosEmissao")
	public MotivoEmissao[] motivosEmissao() {
		return MotivoEmissao.values();
	}

	@ModelAttribute("motivosBaixa")
	public MotivoBaixa[] motivosBaixa() {
		return MotivoBaixa.values();
	}

	@ModelAttribute("situacoes")
	public SituacaoCobranca[] situacoes() {
		return SituacaoCobranca.values();
	}

	@ModelAttribute("moradias")
	public List<Moradia> moradias() {
		return moradiaService.listar();
	}

	@GetMapping({ "", "/", "/lista" })
	public ModelAndView getCobrancas(@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
		model.addAttribute("cobrancas",
				cobrancaService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
		model.addAttribute("conteudo", "cobrancaLista");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getCobrancaCadastro(@ModelAttribute("cobranca") Cobranca cobranca) {
		cobranca.setDataEmissao(LocalDate.now());
		cobranca.setMotivoEmissao(MotivoEmissao.O);
		return new ModelAndView("fragmentos/layoutSindico", "conteudo", "cobrancaCadastro");
	}

	@GetMapping("/{idCobranca}/cadastro")
	public ModelAndView getCobrancaEditar(@PathVariable("idCobranca") Long idCobranca, ModelMap model) {
		model.addAttribute("cobranca", cobrancaService.ler(idCobranca));
		model.addAttribute("conteudo", "cobrancaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping("/cadastro")
	public ModelAndView postCobrancaCadastro(@Valid @ModelAttribute("cobranca") Cobranca cobranca,
			BindingResult validacao) {
		cobrancaService.validar(cobranca, validacao);
		if (validacao.hasErrors()) {
			cobranca.setIdCobranca(null);
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "cobrancaCadastro");
		}
		cobrancaService.salvar(cobranca);
		return new ModelAndView("redirect:/sindico/cobrancas");
	}

	@PutMapping("/cadastro")
	public ModelAndView putCobrancaCadastro(@Valid @ModelAttribute("cobranca") Cobranca cobranca,
			BindingResult validacao) {
		cobrancaService.validar(cobranca, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "cobrancaCadastro");
		}
		cobrancaService.editar(cobranca);
		return new ModelAndView("redirect:/sindico/cobrancas");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteCobrancaCadastro(@RequestParam("idObj") Long idObj) {
		cobrancaService.excluir(cobrancaService.ler(idObj));
		return new ModelAndView("redirect:/sindico/cobrancas");
	}
}
