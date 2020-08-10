package br.com.suleimanmoraes.authserver.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.suleimanmoraes.authserver.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findTopByLogin(String login);

	boolean existsByLoginAndIdNot(String login, Long id);
}
