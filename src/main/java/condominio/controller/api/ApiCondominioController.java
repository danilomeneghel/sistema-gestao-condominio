package condominio.controller.api;

import condominio.domain.Condominio;
import condominio.service.CondominioService;
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
@RequestMapping("/api/condominio")
@Tag(name = "Condominio")
@Validated
public class ApiCondominioController {

    @Autowired
    private CondominioService condominioService;

    @GetMapping
    public ResponseEntity<Page<Condominio>> listarCondominios(@RequestParam("pagina") Optional<Integer> pagina,
                                                    @RequestParam("tamanho") Optional<Integer> tamanho) {
        return new ResponseEntity<>(condominioService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Condominio> localizaCondominio(@PathVariable("id") Long id) {
        return new ResponseEntity<>(condominioService.ler(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Condominio> cadastrarCondominio(@Valid @RequestBody Condominio condominio) {
        return new ResponseEntity<>(condominioService.salvar(condominio), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Condominio> editarCondominio(@Valid @RequestBody Condominio condominio, @PathVariable("id") Long id) {
        Condominio localizaCondominio = condominioService.ler(id);
        if (localizaCondominio == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(condominioService.editar(condominio), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void excluirCondominio(@PathVariable("id") Long id) {
        Condominio localizaCondominio = condominioService.ler(id);
        if (localizaCondominio == null) {
            condominioService.excluir(localizaCondominio);
        }
    }

}
