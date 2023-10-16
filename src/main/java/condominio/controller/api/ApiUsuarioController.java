package condominio.controller.api;

import condominio.domain.Usuario;
import condominio.service.UsuarioService;
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
@RequestMapping("/api/usuario")
@Tag(name = "Usuario")
@Validated
public class ApiUsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<Usuario>> listarUsuarios(@RequestParam("pagina") Optional<Integer> pagina,
                                                    @RequestParam("tamanho") Optional<Integer> tamanho) {
        return new ResponseEntity<>(usuarioService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> localizaUsuario(@PathVariable("id") Long id) {
        return new ResponseEntity<>(usuarioService.ler(id), HttpStatus.OK);
    }

    @PostMapping("/sindico")
    public ResponseEntity<Usuario> cadastrarSindico(@Valid @RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.salvarSindico(usuario), HttpStatus.CREATED);
    }

    @PostMapping("/condomino")
    public ResponseEntity<Usuario> cadastrarCondomino(@Valid @RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.salvarCondomino(usuario), HttpStatus.CREATED);
    }

    @PostMapping("/admin")
    public ResponseEntity<Usuario> cadastrarAdmin(@Valid @RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.salvarAdmin(usuario), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> editarUsuario(@Valid @RequestBody Usuario usuario, @PathVariable("id") Long id) {
        Usuario localizaUsuario = usuarioService.ler(id);
        if (localizaUsuario == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(usuarioService.editar(usuario), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void excluirUsuario(@PathVariable("id") Long id) {
        Usuario localizaUsuario = usuarioService.ler(id);
        if (localizaUsuario == null) {
            usuarioService.excluir(localizaUsuario);
        }
    }

}
