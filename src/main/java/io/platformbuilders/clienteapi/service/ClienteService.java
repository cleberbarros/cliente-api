package io.platformbuilders.clienteapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.platformbuilders.clienteapi.Mapper.ClienteMapper;
import io.platformbuilders.clienteapi.model.Cliente;
import io.platformbuilders.clienteapi.model.dto.ClienteDTO;
import io.platformbuilders.clienteapi.model.dto.FiltroClienteDTO;
import io.platformbuilders.clienteapi.repository.ClienteRepository;


@Service
public class ClienteService {

	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteMapper clienteMapper;
	
	public Cliente atualizar(Long codigo, Cliente cliente) {
		
		
		if(! clienteRepository.existsById(codigo)) {
			throw new EmptyResultDataAccessException(1);  
		}
		
		 
		cliente.setCodigo(codigo);

	    cliente.getContatos().forEach(c -> c.setCliente(cliente)); 
		
	    return clienteRepository.save(cliente);
		
	}
	
	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		
		Cliente clienteSalvo = buscarPessoaPeloCodigo(codigo);
		clienteSalvo.setAtivo(ativo);
		clienteRepository.save(clienteSalvo);
		
	}
	
	public Cliente buscarPessoaPeloCodigo(Long codigo) {
		Optional<Cliente> clienteSalvo = clienteRepository.findById(codigo);
		
		if(!clienteSalvo.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return clienteSalvo.get();
	}
	public Cliente salvar(Cliente cliente) {
		cliente.getContatos().forEach(c -> c.setCliente(cliente)); 
		
		return clienteRepository.save(cliente);
	}

	public Page<ClienteDTO> listarComFiltro( FiltroClienteDTO filtro, Pageable pageable) {
		
		
		return clienteRepository.listarPorFiltro(filtro, pageable).map(clienteMapper::toDTO);
		
		

	}
}
