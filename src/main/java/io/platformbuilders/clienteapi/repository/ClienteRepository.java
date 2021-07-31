package io.platformbuilders.clienteapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.platformbuilders.clienteapi.model.Cliente;
import io.platformbuilders.clienteapi.model.dto.ClienteDTO;
import io.platformbuilders.clienteapi.model.dto.FiltroClienteDTO;

public interface ClienteRepository  extends JpaRepository<Cliente, Long>{

	public Page<Cliente> findByNomeContaining(String nome, Pageable pageable);

	
	
	
	@Query("SELECT c FROM Cliente c WHERE (:#{#filtro.nome} IS NULL OR LOWER(c.nome)"
			+ "LIKE LOWER(CONCAT(CONCAT('%', :#{#filtro.nome}), '%'))) "
			+ "AND (:#{#filtro.cpf} IS NULL OR c.cpf = :#{#filtro.cpf}) "
			+ "AND (:#{#filtro.rg} IS NULL OR c.rg = :#{#filtro.rg}) "
			+ "AND (:#{#filtro.cidade} IS NULL OR LOWER(c.endereco.cidade) LIKE LOWER(CONCAT(CONCAT('%', :#{#filtro.cidade}), '%'))) "
			)
	Page<Cliente> listarPorFiltro(@Param("filtro") final FiltroClienteDTO filtro,
									 final Pageable pageable);
	
}
