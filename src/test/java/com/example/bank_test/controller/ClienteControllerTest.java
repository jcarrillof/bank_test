package com.example.bank_test.controller;

import com.example.bank_test.model.dto.ClienteRequestDTO;
import com.example.bank_test.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ClienteControllerTest {
    @Mock
    private ClienteService clienteService;
    @InjectMocks
    ClienteController clienteController;

    @Test
    public void shouldReturnSuccessfulResponseWhenRequestIsOk()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        String nombre = "Jose Perez";
        String identificacion = "202930";
        String direccion = "Quito";
        String telefono = "0994923933";
        String genero = "masculino";
        int edad = 20;
        String clienteId = "jose123";
        String contrasena = "passjose123";
        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO(nombre, identificacion,
                direccion, telefono, genero, edad, clienteId, contrasena);

        ResponseEntity<String> responseEntity = clienteController.createCliente(clienteRequestDTO);

        verify(clienteService, times(1)).saveClienteInformation(clienteRequestDTO);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo("Cliente creado exitosamente");
    }
}