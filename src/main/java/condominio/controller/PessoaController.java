package condominio.controller;

import condominio.domain.Moradia;
import condominio.domain.Pessoa;
import condominio.domain.PessoaFisica;
import condominio.domain.PessoaJuridica;
import condominio.domain.enums.Estado;
import condominio.domain.enums.Genero;
import condominio.domain.enums.TipoPessoa;
import condominio.domain.enums.TipoRelacao;
import condominio.service.MoradiaService;
import condominio.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({ "sindico/pessoas", "sindico/condominos" })
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	MoradiaService moradiaService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "condominio", "condominos" };
	}

	@ModelAttribute("generos")
	public Genero[] generos() {
		return Genero.values();
	}

	@ModelAttribute("moradias")
	public List<Moradia> moradias() {
		return moradiaService.listar();
	}

	@ModelAttribute("tiposRelacao")
	public TipoRelacao[] tiposRelacao() {
		return TipoRelacao.values();
	}

	@ModelAttribute("tipos")
	public TipoPessoa[] tiposPessoa() {
		return TipoPessoa.values();
	}

	@ModelAttribute("estados")
	public Estado[] estados() {
		return Estado.values();
	}

	@GetMapping({ "", "/", "/lista" })
	public ModelAndView getPessoas(@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
		model.addAttribute("pessoas",
				pessoaService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
		model.addAttribute("conteudo", "pessoaLista");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getPessoaCadastro(@ModelAttribute("pessoa") Pessoa pessoa, ModelMap model) {
		model.addAttribute("tipo", "");
		model.addAttribute("conteudo", "pessoaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/{idPessoa}/cadastro")
	public ModelAndView getPessoaEditar(@PathVariable("idPessoa") Long idPessoa, ModelMap model) {
		Pessoa pessoa = pessoaService.ler(idPessoa);
		if (pessoa instanceof PessoaFisica) {
			model.addAttribute("pessoa", pessoa);
			model.addAttribute("tipo", TipoPessoa.F);
		} else {
			model.addAttribute("pessoa", pessoa);
			model.addAttribute("tipo", TipoPessoa.J);
		}
		model.addAttribute("conteudo", "pessoaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping(value = "/cadastro", params = { "PF" })
	public ModelAndView postPessoaFisicaCadastro(@Valid @ModelAttribute("pessoa") PessoaFisica pessoa,
			BindingResult validacao, ModelMap model) {
		pessoaService.validar(pessoa, validacao);
		if (validacao.hasErrors()) {
			pessoa.setIdPessoa(null);
			model.addAttribute("tipo", TipoPessoa.F);
			model.addAttribute("conteudo", "pessoaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		pessoaService.salvar(pessoa);
		return new ModelAndView("redirect:/sindico/condominos");
	}

	@PostMapping(value = "/cadastro", params = { "PJ" })
	public ModelAndView postPessoaJuridicaCadastro(@Valid @ModelAttribute("pessoa") PessoaJuridica pessoa,
			BindingResult validacao, ModelMap model) {
		pessoaService.validar(pessoa, validacao);
		if (validacao.hasErrors()) {
			pessoa.setIdPessoa(null);
			model.addAttribute("tipo", TipoPessoa.J);
			model.addAttribute("conteudo", "pessoaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		pessoaService.salvar(pessoa);
		return new ModelAndView("redirect:/sindico/condominos");
	}

	@PutMapping(value = "/cadastro", params = { "PF" })
	public ModelAndView putPessoaFisicaCadastro(@Valid @ModelAttribute("pessoa") PessoaFisica pessoa,
			BindingResult validacao, ModelMap model) {
		pessoaService.validar(pessoa, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("tipo", TipoPessoa.F);
			model.addAttribute("conteudo", "pessoaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		pessoaService.editar(pessoa);
		return new ModelAndView("redirect:/sindico/condominos");
	}

	@PutMapping(value = "/cadastro", params = { "PJ" })
	public ModelAndView putPessoaJuridicaCadastro(@Valid @ModelAttribute("pessoa") PessoaJuridica pessoa,
			BindingResult validacao, ModelMap model) {
		pessoaService.validar(pessoa, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("tipo", TipoPessoa.J);
			model.addAttribute("conteudo", "pessoaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		pessoaService.editar(pessoa);
		return new ModelAndView("redirect:/sindico/condominos");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deletePessoaCadastro(@RequestParam("idObj") Long idObj) {
		pessoaService.excluir(pessoaService.ler(idObj));
		return new ModelAndView("redirect:/sindico/condominos");
	}

}
