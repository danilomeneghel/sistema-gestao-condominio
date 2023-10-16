package app.condominio.controller.api;

import app.condominio.domain.Cobranca;
import app.condominio.service.CobrancaService;
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
@RequestMapping("/api/cobranca")
@Tag(name = "Cobranca")
@Validated
public class ApiCobrancaController {

    @Autowired
    private CobrancaService cobrancaService;

    @GetMapping
    public ResponseEntity<Page<Cobranca>> listarCobrancas(@RequestParam("pagina") Optional<Integer> pagina,
                                                    @RequestParam("tamanho") Optional<Integer> tamanho) {
        return new ResponseEntity<>(cobrancaService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cobranca> localizarCobranca(@PathVariable("id") Long id) {
        return new ResponseEntity<>(cobrancaService.ler(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Cobranca> cadastrarCobranca(@Valid @RequestBody Cobranca cobranca) {
        return new ResponseEntity<>(cobrancaService.salvar(cobranca), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cobranca> editarCobranca(@Valid @RequestBody Cobranca cobranca, @PathVariable("id") Long id) {
        Cobranca localizaCobranca = cobrancaService.ler(id);
        if (localizaCobranca == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(cobrancaService.editar(cobranca), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void excluirCobranca(@PathVariable("id") Long id) {
        Cobranca localizaCobranca = cobrancaService.ler(id);
        if (localizaCobranca != null) {
            cobrancaService.excluir(localizaCobranca);
        }
    }

}
