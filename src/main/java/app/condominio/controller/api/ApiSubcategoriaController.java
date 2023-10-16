package app.condominio.controller.api;

import app.condominio.domain.Subcategoria;
import app.condominio.service.SubcategoriaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/subcategoria")
@Tag(name = "Subcategoria")
@Validated
public class ApiSubcategoriaController {

    @Autowired
    private SubcategoriaService subcategoriaService;

    @GetMapping
    public ResponseEntity<Page<Subcategoria>> listarSubcategorias(@RequestParam("pagina") Optional<Integer> pagina,
                                                    @RequestParam("tamanho") Optional<Integer> tamanho) {
        return new ResponseEntity<>(subcategoriaService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subcategoria> localizaSubcategoria(@PathVariable("id") Long id) {
        return new ResponseEntity<>(subcategoriaService.ler(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Subcategoria> cadastrarSubcategoria(@Valid @RequestBody Subcategoria subcategoria) {
        return new ResponseEntity<>(subcategoriaService.salvar(subcategoria), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subcategoria> editarSubcategoria(@Valid @RequestBody Subcategoria subcategoria, @PathVariable("id") Long id) {
        Subcategoria localizaSubcategoria = subcategoriaService.ler(id);
        if (localizaSubcategoria == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(subcategoriaService.editar(subcategoria), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void excluirSubcategoria(@PathVariable("id") Long id) {
        Subcategoria localizaSubcategoria = subcategoriaService.ler(id);
        if (localizaSubcategoria == null) {
            subcategoriaService.excluir(localizaSubcategoria);
        }
    }

}
