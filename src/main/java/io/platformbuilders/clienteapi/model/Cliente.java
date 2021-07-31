package io.platformbuilders.clienteapi.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cliente {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	
	@NotNull
	private String nome;
	
	@NotNull
	private String cpf;
	
	private String rg;
	
	@Column(name = "data_nascimento")
	private LocalDate dtNascimento;
	
	@NotNull
	private Boolean ativo;
	
	@Embedded
	private Endereco endereco;
	
	@JsonIgnoreProperties("cliente") //APENDICE AULA 22.24 ignorando a propriedade pessoa que esta lá em contato e assim evitando recursividade
	//APENDICE AULA 22.23
	@Valid
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)  /*com isso o hibernate vai remover o que estive na base de dados mas não estive mais na lista. 
										   então caso não passe o contato ele vai apagar o que tiver no banco*/	
	private List<Contato> contatos;

}
