package io.platformbuilders.clienteapi.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import io.platformbuilders.clienteapi.model.Cliente;
import io.platformbuilders.clienteapi.model.dto.ClienteDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClienteMapper {

	ClienteDTO toDTO(Cliente entity);
	Cliente toEntity(ClienteDTO dto);
	List<ClienteDTO> toDTO(List<Cliente>entities);
	
    
    
}
