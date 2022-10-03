package com.example.bank_test.service;

import com.example.bank_test.model.dto.ClienteRequestDTO;
import com.example.bank_test.model.entity.Cliente;
import com.example.bank_test.model.entity.Persona;
import com.example.bank_test.model.enums.Genero;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        int edad = 20;
        String clienteId = "jose123";
        String contrasena = "passjose123";
        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO(nombre, identificacion,
                direccion, telefono, Genero.MASCULINO, edad, clienteId, contrasena);

        clienteService.saveClienteInformation(clienteRequestDTO);

        verify(personaRepository, times(1)).save(any(Persona.class));
        verify(clienteRepository, times(1)).save(clienteCaptor.capture());
        assertThat(clienteCaptor.getValue().getClienteId()).isEqualTo(clienteId);
        assertThat(clienteCaptor.getValue().getContrasena()).isEqualTo(contrasena);
    }

    @Test
    public void shouldThrowExceptionWhenClienteIsNotFoundToUpdate() {
        Cliente cliente = new Cliente();
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.updateCliente(cliente.getId(), new ClienteRequestDTO()));

        verify(clienteRepository, times(0)).save(clienteCaptor.capture());
        assertThat(exception.getMessage()).isEqualTo("Cliente no existe en la base de datos");
    }

    @Test
    public void shouldUpdateTelefonoWhenFieldHasValue() {
        String telefono = "0935292062";
        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setTelefono(telefono);
        Persona persona = new Persona();
        Cliente cliente = new Cliente();
        cliente.setPersona(persona);
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        clienteService.updateCliente(cliente.getId(), clienteRequestDTO);

        verify(clienteRepository, times(1)).save(clienteCaptor.capture());
        assertThat(clienteCaptor.getValue().getPersona().getTelefono()).isEqualTo(telefono);
    }

    @Test
    public void shouldNotUpdateTelefonoWhenFieldDoesNotHaveValue() {
        Persona persona = new Persona();
        Cliente cliente = new Cliente();
        cliente.setPersona(persona);
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        clienteService.updateCliente(cliente.getId(), new ClienteRequestDTO());

        verify(clienteRepository, times(1)).save(clienteCaptor.capture());
        assertThat(clienteCaptor.getValue().getPersona().getTelefono()).isEqualTo(cliente.getPersona().getTelefono());
    }

    @Test
    public void shouldUpdateDireccionWhenFieldHasValue() {
        String direccion = "Guayaquil";
        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setDireccion(direccion);
        Persona persona = new Persona();
        Cliente cliente = new Cliente();
        cliente.setPersona(persona);
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        clienteService.updateCliente(cliente.getId(), clienteRequestDTO);

        verify(clienteRepository, times(1)).save(clienteCaptor.capture());
        assertThat(clienteCaptor.getValue().getPersona().getDireccion()).isEqualTo(direccion);
    }

    @Test
    public void shouldNotUpdateDireccionWhenFieldDoesNotHaveValue() {
        Persona persona = new Persona();
        Cliente cliente = new Cliente();
        cliente.setPersona(persona);
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        clienteService.updateCliente(cliente.getId(), new ClienteRequestDTO());

        verify(clienteRepository, times(1)).save(clienteCaptor.capture());
        assertThat(clienteCaptor.getValue().getPersona().getDireccion()).isEqualTo(cliente.getPersona().getDireccion());
    }

    @Test
    public void shouldChangeEstadoWhenClienteIsFound() {
        Cliente cliente = new Cliente();
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        clienteService.deleteCliente(cliente.getId());

        verify(clienteRepository, times(1)).save(clienteCaptor.capture());
        assertThat(clienteCaptor.getValue().getEstado()).isEqualTo("eliminado");
    }

    @Test
    public void shouldThrowExceptionWhenClienteIsNotFound() {
        Cliente cliente = new Cliente();
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                clienteService.deleteCliente(cliente.getId()));

        verify(clienteRepository, times(0)).save(clienteCaptor.capture());
        assertThat(exception.getMessage()).isEqualTo("Cliente no existe en la base de datos");
    }
}
