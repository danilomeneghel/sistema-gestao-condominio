package condominio.controller.api;

import condominio.domain.Movimento;
import condominio.service.MovimentoService;
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
@RequestMapping("/api/movimento")
@Tag(name = "Movimento")
@Validated
public class ApiMovimentoController {

    @Autowired
    private MovimentoService movimentoService;

    @GetMapping
    public ResponseEntity<Page<Movimento>> listarMovimentos(@RequestParam("pagina") Optional<Integer> pagina,
                                                    @RequestParam("tamanho") Optional<Integer> tamanho) {
        return new ResponseEntity<>(movimentoService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimento> localizaMovimento(@PathVariable("id") Long id) {
        return new ResponseEntity<>(movimentoService.ler(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Movimento> cadastrarMovimento(@Valid @RequestBody Movimento movimento) {
        return new ResponseEntity<>(movimentoService.salvar(movimento), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimento> editarMovimento(@Valid @RequestBody Movimento movimento, @PathVariable("id") Long id) {
        Movimento localizaMovimento = movimentoService.ler(id);
        if (localizaMovimento == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(movimentoService.editar(movimento), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void excluirMovimento(@PathVariable("id") Long id) {
        Movimento localizaMovimento = movimentoService.ler(id);
        if (localizaMovimento == null) {
            movimentoService.excluir(localizaMovimento);
        }
    }

}
