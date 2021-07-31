package io.platformbuilders.clienteapi.model.dto;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import io.platformbuilders.clienteapi.model.Contato;
import io.platformbuilders.clienteapi.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

	
	private String nome;
	
	private String cpf;
	
	private String rg;
	
	private LocalDate dtNascimento;
	
	private Boolean ativo;
	
	private int idade;
	
	private Endereco endereco;
	
	private List<Contato> contatos;
	
	

	public int getIdade() {
		return Period.between(dtNascimento, LocalDate.now()).getYears();
	}



}
