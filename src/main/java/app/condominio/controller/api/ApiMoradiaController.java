package app.condominio.controller.api;

import app.condominio.domain.Moradia;
import app.condominio.service.MoradiaService;
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
@RequestMapping("/api/moradia")
@Tag(name = "Moradia")
@Validated
public class ApiMoradiaController {

    @Autowired
    private MoradiaService moradiaService;

    @GetMapping
    public ResponseEntity<Page<Moradia>> listarMoradias(@RequestParam("pagina") Optional<Integer> pagina,
                                                    @RequestParam("tamanho") Optional<Integer> tamanho) {
        return new ResponseEntity<>(moradiaService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moradia> localizaMoradia(@PathVariable("id") Long id) {
        return new ResponseEntity<>(moradiaService.ler(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Moradia> cadastrarMoradia(@Valid @RequestBody Moradia moradia) {
        return new ResponseEntity<>(moradiaService.salvar(moradia), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moradia> editarMoradia(@Valid @RequestBody Moradia moradia, @PathVariable("id") Long id) {
        Moradia localizaMoradia = moradiaService.ler(id);
        if (localizaMoradia == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(moradiaService.editar(moradia), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void excluirMoradia(@PathVariable("id") Long id) {
        Moradia localizaMoradia = moradiaService.ler(id);
        if (localizaMoradia == null) {
            moradiaService.excluir(localizaMoradia);
        }
    }

}
