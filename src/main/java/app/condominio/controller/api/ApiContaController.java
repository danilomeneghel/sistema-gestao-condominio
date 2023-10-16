package app.condominio.controller.api;

import app.condominio.domain.Conta;
import app.condominio.service.ContaService;
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
@RequestMapping("/api/conta")
@Tag(name = "Conta")
@Validated
public class ApiContaController {

    @Autowired
    private ContaService contaService;

    @GetMapping
    public ResponseEntity<Page<Conta>> listarContas(@RequestParam("pagina") Optional<Integer> pagina,
                                                    @RequestParam("tamanho") Optional<Integer> tamanho) {
        return new ResponseEntity<>(contaService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> localizaConta(@PathVariable("id") Long id) {
        return new ResponseEntity<>(contaService.ler(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Conta> cadastrarConta(@Valid @RequestBody Conta conta) {
        return new ResponseEntity<>(contaService.salvar(conta), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conta> editarConta(@Valid @RequestBody Conta conta, @PathVariable("id") Long id) {
        Conta localizaConta = contaService.ler(id);
        if (localizaConta == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(contaService.editar(conta), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void excluirConta(@PathVariable("id") Long id) {
        Conta localizaConta = contaService.ler(id);
        if (localizaConta == null) {
            contaService.excluir(localizaConta);
        }
    }

}
