package condominio.controller.api;

import condominio.domain.Categoria;
import condominio.service.CategoriaService;
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
@RequestMapping("/api/categoria")
@Tag(name = "Categoria")
@Validated
public class ApiCategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<Page<Categoria>> listarCategorias(@RequestParam("pagina") Optional<Integer> pagina,
                                                    @RequestParam("tamanho") Optional<Integer> tamanho) {
        return new ResponseEntity<>(categoriaService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> localizaCategoria(@PathVariable("id") Long id) {
        return new ResponseEntity<>(categoriaService.ler(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Categoria> cadastrarCategoria(@Valid @RequestBody Categoria categoria) {
        return new ResponseEntity<>(categoriaService.salvar(categoria), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> editarCategoria(@Valid @RequestBody Categoria categoria, @PathVariable("id") Long id) {
        Categoria localizaCategoria = categoriaService.ler(id);
        if (localizaCategoria == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(categoriaService.editar(categoria), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void excluirCategoria(@PathVariable("id") Long id) {
        Categoria localizaCategoria = categoriaService.ler(id);
        if (localizaCategoria == null) {
            categoriaService.excluir(localizaCategoria);
        }
    }

}
