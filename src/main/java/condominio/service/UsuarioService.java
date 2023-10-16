package condominio.service;

import condominio.domain.Usuario;

public interface UsuarioService extends CrudService<Usuario, Long> {

	public Usuario salvarSindico(Usuario usuario);

	public Usuario salvarCondomino(Usuario usuario);

	public Usuario salvarAdmin(Usuario usuario);

	public Usuario ler(String username);

	public Usuario lerLogado();

	public boolean redefinirSenha(String username);

	public boolean redefinirSenha(String username, String token, String password);

}
