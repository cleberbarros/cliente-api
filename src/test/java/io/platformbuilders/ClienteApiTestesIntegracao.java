package io.platformbuilders;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import io.platformbuilders.clienteapi.ClienteApiApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ClienteApiApplication.class)
@AutoConfigureMockMvc
class ClienteApiTestesIntegracao {

	@Autowired
    private MockMvc mockMvc;

    @Test
    public void executar_RequisicaoGet_para_CodigoExistente_no_BancoDados() throws Exception {
        this.mockMvc.perform(get("/cliente/8"))
                    .andDo(print())
                    .andExpect(status().isOk());
     
    }
    
    @Test
    public void executar_RequisicaoGet_para_Codigo_NAO_Existente_no_BancoDados() throws Exception {
        this.mockMvc.perform(get("/cliente/999999999"))
                    .andDo(print())
                    .andExpect(status().isNotFound());
     
    }

}
