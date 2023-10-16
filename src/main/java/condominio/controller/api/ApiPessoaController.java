package condominio.controller.api;

import condominio.domain.Pessoa;
import condominio.service.PessoaService;
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
@RequestMapping("/api/pessoa")
@Tag(name = "Pessoa")
@Validated
public class ApiPessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<Page<Pessoa>> listarPessoas(@RequestParam("pagina") Optional<Integer> pagina,
                                                    @RequestParam("tamanho") Optional<Integer> tamanho) {
        return new ResponseEntity<>(pessoaService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> localizaPessoa(@PathVariable("id") Long id) {
        return new ResponseEntity<>(pessoaService.ler(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Pessoa> cadastrarPessoa(@Valid @RequestBody Pessoa pessoa) {
        return new ResponseEntity<>(pessoaService.salvar(pessoa), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> editarPessoa(@Valid @RequestBody Pessoa pessoa, @PathVariable("id") Long id) {
        Pessoa localizaPessoa = pessoaService.ler(id);
        if (localizaPessoa == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(pessoaService.editar(pessoa), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void excluirPessoa(@PathVariable("id") Long id) {
        Pessoa localizaPessoa = pessoaService.ler(id);
        if (localizaPessoa == null) {
            pessoaService.excluir(localizaPessoa);
        }
    }

}
