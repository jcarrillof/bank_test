package com.example.bank_test.service;

import com.example.bank_test.model.entity.Cliente;
import com.example.bank_test.model.entity.Persona;
import com.example.bank_test.model.dto.ClienteRequestDTO;
import com.example.bank_test.repository.ClienteRepository;
import com.example.bank_test.repository.PersonaRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {
    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService = new ClienteService();

    @Captor
    ArgumentCaptor<Cliente> clienteCaptor;

    @Test
    public void shouldSaveClienteInformation() {
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

        clienteService.saveClienteInformation(clienteRequestDTO);

        verify(personaRepository, times(1)).save(any(Persona.class));
        verify(clienteRepository, times(1)).save(clienteCaptor.capture());
        assertThat(clienteCaptor.getValue().getClienteId()).isEqualTo(clienteId);
        assertThat(clienteCaptor.getValue().getClienteId()).isEqualTo(clienteId);
        assertThat(clienteCaptor.getValue().getContrasena()).isEqualTo(contrasena);
    }
}
