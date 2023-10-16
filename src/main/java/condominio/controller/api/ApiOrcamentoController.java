package condominio.controller.api;

import condominio.domain.Orcamento;
import condominio.service.OrcamentoService;
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
@RequestMapping("/api/orcamento")
@Tag(name = "Orcamento")
@Validated
public class ApiOrcamentoController {

    @Autowired
    private OrcamentoService orcamentoService;

    @GetMapping
    public ResponseEntity<Page<Orcamento>> listarOrcamentos(@RequestParam("pagina") Optional<Integer> pagina,
                                                    @RequestParam("tamanho") Optional<Integer> tamanho) {
        return new ResponseEntity<>(orcamentoService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orcamento> localizaOrcamento(@PathVariable("id") Long id) {
        return new ResponseEntity<>(orcamentoService.ler(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Orcamento> cadastrarOrcamento(@Valid @RequestBody Orcamento orcamento) {
        return new ResponseEntity<>(orcamentoService.salvar(orcamento), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orcamento> editarOrcamento(@Valid @RequestBody Orcamento orcamento, @PathVariable("id") Long id) {
        Orcamento localizaOrcamento = orcamentoService.ler(id);
        if (localizaOrcamento == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(orcamentoService.editar(orcamento), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void excluirOrcamento(@PathVariable("id") Long id) {
        Orcamento localizaOrcamento = orcamentoService.ler(id);
        if (localizaOrcamento == null) {
            orcamentoService.excluir(localizaOrcamento);
        }
    }

}
