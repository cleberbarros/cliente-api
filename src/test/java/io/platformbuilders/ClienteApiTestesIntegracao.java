package io.platformbuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.platformbuilders.clienteapi.ClienteApiApplication;
import io.platformbuilders.clienteapi.model.Cliente;
import io.platformbuilders.clienteapi.model.Contato;
import io.platformbuilders.clienteapi.model.dto.FiltroClienteDTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ClienteApiApplication.class)
@AutoConfigureMockMvc
class ClienteApiTestesIntegracao {

	@Autowired
    private MockMvc mockMvc;
	
	Cliente cliente = new Cliente();
	
	
	FiltroClienteDTO filtro = new FiltroClienteDTO();

    @Test
    public void RequisicaoGet_para_CodigoExistente() throws Exception {
        this.mockMvc.perform(get("/cliente/8"))
                    .andDo(print())
                    .andExpect(status().isOk());
     
    }
    
    @Test
    public void RequisicaoGet_para_Codigo_NAO_Existente() throws Exception {
        this.mockMvc.perform(get("/cliente/999999999"))
                    .andDo(print())
                    .andExpect(status().isNotFound());
     
    }
   
    
    @Test
    public void RequisicaoPost_Salvar_Cliente() throws Exception {
    	Contato contato = new Contato();
    	List<Contato> contatos = new ArrayList<>();
    	
    	this.cliente.setNome("Maria Maezinha linda");
    	this.cliente.setCpf("03414849461");
    	this.cliente.setAtivo(true);
    	
    	
    	contato.setNome("José");
    	contato.setEmail("jose@servoDeus.com.br");
    	contato.setTelefone("8888888888");
    	contato.setCliente(cliente);
    	
    	contatos.add(contato);
    	this.cliente.setContatos(contatos);
    	
    	
    	this.mockMvc.perform(MockMvcRequestBuilders
    			  .post("/cliente")
    		      .content(asJsonString(cliente))
    		      .contentType(MediaType.APPLICATION_JSON)
    		      .accept(MediaType.APPLICATION_JSON))
    		      .andDo(print())	
    		      .andExpect(status().isCreated())
    		      .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").exists());
            
    }
    
    @Test
    public void RequisicaoPost_Pesquisa() throws Exception {
    	
    	//filtro.setCidade("Juaz");
    	filtro.setNome("cl");
    	
    	
    	this.mockMvc.perform(MockMvcRequestBuilders
    			  .post("/cliente/pesquisar")
    		      .content(asJsonString(filtro))
    		      .contentType(MediaType.APPLICATION_JSON)
    			  .accept(MediaType.APPLICATION_JSON))
    			  .andDo(print())
    			  .andExpect(status().isOk());
            
    }
    

    @Test
    public void RequisicaoPUT_Cliente() throws Exception {
    	Cliente clienteDadosNovos = new Cliente();
    	Contato contato = new Contato();
    	List<Contato> contatos = new ArrayList<>();
    	
    	clienteDadosNovos.setNome("Maria Mãe de Deus e Jesus");
    	clienteDadosNovos.setAtivo(true);
    	clienteDadosNovos.setCpf("03414849461");
    	
    	contato.setCodigo(10L);
    	contato.setNome("José");
    	contato.setEmail("jose@servoDeus.com.br");
    	contato.setTelefone("8888888888");
    	contato.setCliente(clienteDadosNovos);

    	
    	contatos.add(contato);
    	clienteDadosNovos.setContatos(contatos);

    	this.mockMvc.perform( MockMvcRequestBuilders
          .put("/cliente/{codigo}", 12)
          .content(asJsonString(clienteDadosNovos))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(clienteDadosNovos.getNome()));
    }

 
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }    
 
    }
    
    
       


}
