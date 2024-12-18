package condominio.controller;

import condominio.domain.Categoria;
import condominio.domain.Subcategoria;
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
@RequestMapping("sindico/subcategorias")
public class SubcategoriaController {

	@Autowired
	private SubcategoriaService subcategoriaService;

	@Autowired
	private CategoriaService categoriaService;

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
	public ModelAndView getSubcategorias(ModelMap model) {
		model.addAttribute("contagemSubcategorias", subcategoriaService.contagem());
		model.addAttribute("conteudo", "categoriaLista");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getSubcategoriaCadastro(@ModelAttribute("categoria") Subcategoria subcategoria,
			ModelMap model) {
		model.addAttribute("classe", TipoClasseCategoria.S);
		model.addAttribute("conteudo", "categoriaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/{idSubcategoria}/cadastro")
	public ModelAndView getSubcategoriaEditar(@PathVariable("idSubcategoria") Long idSubcategoria, ModelMap model) {
		model.addAttribute("classe", TipoClasseCategoria.S);
		model.addAttribute("categoria", subcategoriaService.ler(idSubcategoria));
		model.addAttribute("conteudo", "categoriaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping("/cadastro")
	public ModelAndView postSubcategoriaCadastro(@Valid @ModelAttribute("categoria") Subcategoria categoria,
			BindingResult validacao, ModelMap model) {
		subcategoriaService.validar(categoria, validacao);
		if (validacao.hasErrors()) {
			categoria.setIdSubcategoria(null);
			model.addAttribute("classe", TipoClasseCategoria.S);
			model.addAttribute("conteudo", "categoriaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		subcategoriaService.salvar(categoria);
		return new ModelAndView("redirect:/sindico/subcategorias");
	}

	@PutMapping("/cadastro")
	public ModelAndView putSubcategoriaCadastro(@Valid @ModelAttribute("categoria") Subcategoria categoria,
			BindingResult validacao, ModelMap model) {
		subcategoriaService.validar(categoria, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("classe", TipoClasseCategoria.S);
			model.addAttribute("conteudo", "categoriaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		subcategoriaService.editar(categoria);
		return new ModelAndView("redirect:/sindico/subcategorias");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteSubcategoriaCadastro(@RequestParam("idObj") Long idObj) {
		subcategoriaService.excluir(subcategoriaService.ler(idObj));
		return new ModelAndView("redirect:/sindico/subcategorias");
	}
}
