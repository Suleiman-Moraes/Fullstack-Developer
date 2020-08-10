package br.com.suleimanmoraes.authserver.api.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.suleimanmoraes.authserver.api.model.dto.ClienteNovoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Suleiman Moraes
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String login;

	private String nome;

	private String senha;

	private Boolean ativo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_inclusao")
	private Date dataInclusao;
	
	@Column(name = "cliente_id")
	private Long clienteId;

	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@JoinTable(name = "usuario_permissao", joinColumns = { @JoinColumn(name = "usuario_id") }, inverseJoinColumns = {
			@JoinColumn(name = "permissao_id") })
	private List<Permissao> permissoes;
	
	public Usuario(ClienteNovoDto dto, Permissao permissao) {
		this.login = dto.getLogin();
		this.nome = dto.getNome();
		this.senha = dto.getSenha();
		this.clienteId = dto.getId();
		this.permissoes = new LinkedList<>();
		this.permissoes.add(permissao);
	}
	
	public void addPermissao(Permissao permissao) {
		if(this.permissoes == null) {
			this.permissoes = new LinkedList<>();
		}
		this.permissoes.add(permissao);
	}
	
	@PrePersist
	public void prePersist() {
		this.dataInclusao = new Date();
		prePersistAndUpdate();
	}

	@PreUpdate
	public void preUpdate() {
		prePersistAndUpdate();
	}
	
	private void prePersistAndUpdate() {
		this.ativo = this.ativo == null ? Boolean.TRUE : this.ativo;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
