package condominio.controller.api;

import condominio.domain.Bloco;
import condominio.service.BlocoService;
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
@RequestMapping("/api/bloco")
@Tag(name = "Bloco")
@Validated
public class ApiBlocoController {

    @Autowired
    private BlocoService blocoService;

    @GetMapping
    public ResponseEntity<Page<Bloco>> listarBlocos(@RequestParam("pagina") Optional<Integer> pagina,
                                                    @RequestParam("tamanho") Optional<Integer> tamanho) {
        return new ResponseEntity<>(blocoService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bloco> localizarBloco(@PathVariable("id") Long id) {
        return new ResponseEntity<>(blocoService.ler(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Bloco> cadastrarBloco(@Valid @RequestBody Bloco bloco) {
        return new ResponseEntity<>(blocoService.salvar(bloco), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bloco> editarBloco(@Valid @RequestBody Bloco bloco, @PathVariable("id") Long id) {
        Bloco localizaBloco = blocoService.ler(id);
        if (localizaBloco == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(blocoService.editar(bloco), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void excluirBloco(@PathVariable("id") Long id) {
        Bloco localizaBloco = blocoService.ler(id);
        if (localizaBloco != null) {
            blocoService.excluir(localizaBloco);
        }
    }

}
