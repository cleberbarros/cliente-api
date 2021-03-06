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
        this.mockMvc.perform(get("/cliente/1"))
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
    public void RequisicaoPost_Pesquisa() throws Exception {
    	
    	filtro.setCidade("Petrolina");
    	    	
    	
    	this.mockMvc.perform(MockMvcRequestBuilders
    			  .post("/cliente/pesquisar")
    		      .content(asJsonString(filtro))
    		      .contentType(MediaType.APPLICATION_JSON)
    			  .accept(MediaType.APPLICATION_JSON))
    			  .andDo(print())
    			  .andExpect(status().isOk());
            
    }
    

    @Test
    public void RequisicaoPUT_Ativar_Inativar_CLiente() throws Exception {
    	
    	this.mockMvc.perform( MockMvcRequestBuilders
          .put("/cliente/1/ativo")
          .content(asJsonString(false))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());
          
    }

 
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }    
 
    }
    
    
       


}
