package io.platformbuilders.clienteapi.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FiltroClienteDTO {

	private String nome;
	private String cpf;
	private String rg;
	private String cidade;
	
}
