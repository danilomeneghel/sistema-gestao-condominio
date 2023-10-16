package condominio.controller.api;

import condominio.domain.Periodo;
import condominio.service.PeriodoService;
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
@RequestMapping("/api/periodo")
@Tag(name = "Periodo")
@Validated
public class ApiPeriodoController {

    @Autowired
    private PeriodoService periodoService;

    @GetMapping
    public ResponseEntity<Page<Periodo>> listarPeriodos(@RequestParam("pagina") Optional<Integer> pagina,
                                                    @RequestParam("tamanho") Optional<Integer> tamanho) {
        return new ResponseEntity<>(periodoService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Periodo> localizaPeriodo(@PathVariable("id") Long id) {
        return new ResponseEntity<>(periodoService.ler(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Periodo> cadastrarPeriodo(@Valid @RequestBody Periodo periodo) {
        return new ResponseEntity<>(periodoService.salvar(periodo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Periodo> editarPeriodo(@Valid @RequestBody Periodo periodo, @PathVariable("id") Long id) {
        Periodo localizaPeriodo = periodoService.ler(id);
        if (localizaPeriodo == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(periodoService.editar(periodo), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void excluirPeriodo(@PathVariable("id") Long id) {
        Periodo localizaPeriodo = periodoService.ler(id);
        if (localizaPeriodo == null) {
            periodoService.excluir(localizaPeriodo);
        }
    }

}
