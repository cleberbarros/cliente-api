package io.platformbuilders.clienteapi.rest;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.platformbuilders.clienteapi.event.RecursoCriadoEvent;
import io.platformbuilders.clienteapi.model.Cliente;
import io.platformbuilders.clienteapi.model.dto.ClienteDTO;
import io.platformbuilders.clienteapi.model.dto.FiltroClienteDTO;
import io.platformbuilders.clienteapi.repository.ClienteRepository;
import io.platformbuilders.clienteapi.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteRest {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ClienteService clienteService;
	
	
	@PostMapping("pesquisar")
	public ResponseEntity<Page<ClienteDTO>> listar(@RequestBody FiltroClienteDTO filtro, Pageable pageable){
		return ResponseEntity.ok(clienteService.listarComFiltro(filtro,pageable));
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Cliente> buscarPeloCodigo(@PathVariable Long codigo){
	  Optional<Cliente> clientePesquisado = clienteRepository.findById(codigo);
	  
	  if(clientePesquisado.isPresent()) {
		  return ResponseEntity.ok(clientePesquisado.get());
	  }
	  
	  return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Cliente>criar (@Valid @RequestBody Cliente cliente, HttpServletResponse response){
		Cliente clienteSalvo = clienteService.salvar(cliente);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalvo.getCodigo())); 
		
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		clienteRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Cliente>atualizar (@PathVariable Long codigo, @Valid @RequestBody Cliente cliente ){
		
		Cliente clienteSalvo = clienteService.atualizar(codigo, cliente);
		
		return ResponseEntity.ok(clienteSalvo);
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		clienteService.atualizarPropriedadeAtivo(codigo,ativo);
		
	}

}
