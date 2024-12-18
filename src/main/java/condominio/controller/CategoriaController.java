package condominio.controller;

import condominio.domain.Categoria;
import condominio.domain.enums.TipoCategoria;
import condominio.domain.enums.TipoClasseCategoria;
import condominio.service.CategoriaService;
import condominio.service.SubcategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping({ "sindico/categorias", "sindico/plano-de-contas" })
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private SubcategoriaService subcategoriaService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "contabilidade", "categorias" };
	}

	@ModelAttribute("tiposCategoria")
	public TipoCategoria[] tiposCategoria() {
		return TipoCategoria.values();
	}

	@ModelAttribute("tiposClasseCategoria")
	public TipoClasseCategoria[] tiposClasseCategoria() {
		return TipoClasseCategoria.values();
	}

	@ModelAttribute("categorias")
	public List<Categoria> categorias() {
		return categoriaService.listar();
	}

	@GetMapping({ "", "/", "/lista", "/todos" })
	public ModelAndView getCategorias(ModelMap model) {
		model.addAttribute("contagemSubcategorias", subcategoriaService.contagem());
		model.addAttribute("conteudo", "categoriaLista");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getCategoriaCadastro(@ModelAttribute("categoria") Categoria categoria, ModelMap model) {
		model.addAttribute("classe", "");
		model.addAttribute("conteudo", "categoriaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/{idCategoria}/cadastro")
	public ModelAndView getCategoriaEditar(@PathVariable("idCategoria") Long idCategoria, ModelMap model) {
		model.addAttribute("classe", TipoClasseCategoria.C);
		model.addAttribute("categoria", categoriaService.ler(idCategoria));
		model.addAttribute("conteudo", "categoriaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping("/cadastro")
	public ModelAndView postCategoriaCadastro(@Valid @ModelAttribute("categoria") Categoria categoria,
			BindingResult validacao, ModelMap model) {
		categoriaService.validar(categoria, validacao);
		if (validacao.hasErrors()) {
			categoria.setIdCategoria(null);
			model.addAttribute("classe", TipoClasseCategoria.C);
			model.addAttribute("conteudo", "categoriaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		categoriaService.salvar(categoria);
		return new ModelAndView("redirect:/sindico/categorias");
	}

	@PutMapping("/cadastro")
	public ModelAndView putCategoriaCadastro(@Valid @ModelAttribute("categoria") Categoria categoria,
			BindingResult validacao, ModelMap model) {
		categoriaService.validar(categoria, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("classe", TipoClasseCategoria.C);
			model.addAttribute("conteudo", "categoriaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		categoriaService.editar(categoria);
		return new ModelAndView("redirect:/sindico/categorias");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteCategoriaCadastro(@RequestParam("idObj") Long idObj) {
		categoriaService.excluir(categoriaService.ler(idObj));
		return new ModelAndView("redirect:/sindico/categorias");
	}

}
