package app.condominio.dao;

import app.condominio.domain.Usuario;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UsuarioDao extends PagingAndSortingRepository<Usuario, Long> {

	Usuario findOneByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByUsernameAndIdNot(String username, Long id);

}
