package com.example.bank_test.controller;

import com.example.bank_test.model.TipoMovimiento;
import com.example.bank_test.model.dto.MovimientoRequestDTO;
import com.example.bank_test.service.MovimientoService;
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
class MovimientoControllerTest {
    @Mock
    private MovimientoService movimientoService;
    @InjectMocks
    MovimientoController movimientoController;
    @Test
    public void shouldReturnSuccessfulResponseWhenMovimientoIsOk()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        double valor = 15.0;
        String numeroCuenta = "2345";
        MovimientoRequestDTO movimientoRequestDTO = new MovimientoRequestDTO(TipoMovimiento.DEBITO, valor, numeroCuenta);

        ResponseEntity<String> responseEntity = movimientoController.createMovimiento(movimientoRequestDTO);

        verify(movimientoService, times(1)).saveMovimiento(movimientoRequestDTO);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo("Transaccion realizada exitosamente");
    }
}