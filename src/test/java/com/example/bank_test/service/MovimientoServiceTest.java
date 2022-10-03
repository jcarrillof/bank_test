package com.example.bank_test.service;

import com.example.bank_test.model.dto.MovimientoRequestDTO;
import com.example.bank_test.model.dto.MovimientoResponseDTO;
import com.example.bank_test.model.entity.Cuenta;
import com.example.bank_test.model.entity.Movimiento;
import com.example.bank_test.model.enums.TipoCuenta;
import com.example.bank_test.model.enums.TipoMovimiento;
import com.example.bank_test.repository.CuentaRepository;
import com.example.bank_test.repository.MovimientoRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class MovimientoServiceTest {
    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private MovimientoService movimientoService;

    @Captor
    ArgumentCaptor<Movimiento> movimientoCaptor;

    @Test
    public void shouldSaveMovimientoWithPositiveValueWhenIsCredito() {
        double valor = 10.0;
        String numeroCuenta = "2345";
        MovimientoRequestDTO movimientoRequestDTO = new MovimientoRequestDTO(TipoMovimiento.CREDITO, valor, numeroCuenta);
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(numeroCuenta);
        cuenta.setSaldoInicial(0);
        when(cuentaRepository.findByNumeroCuenta(numeroCuenta)).thenReturn(cuenta);

        movimientoService.saveMovimiento(movimientoRequestDTO);

        verify(cuentaRepository, times(1)).findByNumeroCuenta(numeroCuenta);
        verify(movimientoRepository, times(1)).save(movimientoCaptor.capture());
        assertThat(movimientoCaptor.getValue().getValor()).isPositive();
        assertThat(movimientoCaptor.getValue().getTipoMovimiento()).isEqualTo(TipoMovimiento.CREDITO);
        assertThat(movimientoCaptor.getValue().getSaldo()).isEqualTo(10);
        assertThat(movimientoCaptor.getValue().getCuenta().getNumeroCuenta()).isEqualTo(numeroCuenta);
    }

    @Test
    public void shouldSaveMovimientoWithNegativeValueWhenIsDebito() {
        double valor = 5.0;
        String numeroCuenta = "2345";
        MovimientoRequestDTO movimientoRequestDTO = new MovimientoRequestDTO(TipoMovimiento.DEBITO, valor, numeroCuenta);
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(numeroCuenta);
        cuenta.setSaldoInicial(10);
        when(cuentaRepository.findByNumeroCuenta(numeroCuenta)).thenReturn(cuenta);

        movimientoService.saveMovimiento(movimientoRequestDTO);

        verify(cuentaRepository, times(1)).findByNumeroCuenta(numeroCuenta);
        verify(movimientoRepository, times(1)).save(movimientoCaptor.capture());
        assertThat(movimientoCaptor.getValue().getValor()).isNegative();
        assertThat(movimientoCaptor.getValue().getTipoMovimiento()).isEqualTo(TipoMovimiento.DEBITO);
        assertThat(movimientoCaptor.getValue().getSaldo()).isEqualTo(5);
        assertThat(movimientoCaptor.getValue().getCuenta().getNumeroCuenta()).isEqualTo(numeroCuenta);
    }

    @Test
    public void shouldSaveMovimientoTakingLastSaldo() {
        double valor = 15.0;
        String numeroCuenta = "2345";
        MovimientoRequestDTO movimientoRequestDTO = new MovimientoRequestDTO(TipoMovimiento.DEBITO, valor, numeroCuenta);
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(numeroCuenta);
        List<Movimiento> movimientos = new ArrayList<>();
        Movimiento firstMovimiento = new Movimiento();
        firstMovimiento.setTipoMovimiento(TipoMovimiento.CREDITO);
        firstMovimiento.setValor(10);
        firstMovimiento.setSaldo(30);
        movimientos.add(firstMovimiento);
        Movimiento secondMovimiento = new Movimiento();
        secondMovimiento.setTipoMovimiento(TipoMovimiento.DEBITO);
        secondMovimiento.setValor(-5);
        secondMovimiento.setSaldo(25);
        movimientos.add(secondMovimiento);
        when(cuentaRepository.findByNumeroCuenta(numeroCuenta)).thenReturn(cuenta);
        when(movimientoRepository.findByCuentaId(cuenta.getId())).thenReturn(movimientos);

        movimientoService.saveMovimiento(movimientoRequestDTO);

        verify(cuentaRepository, times(1)).findByNumeroCuenta(numeroCuenta);
        verify(movimientoRepository, times(1)).findByCuentaId(cuenta.getId());
        verify(movimientoRepository, times(1)).save(movimientoCaptor.capture());
        assertThat(movimientoCaptor.getValue().getValor()).isNegative();
        assertThat(movimientoCaptor.getValue().getTipoMovimiento()).isEqualTo(TipoMovimiento.DEBITO);
        assertThat(movimientoCaptor.getValue().getSaldo()).isEqualTo(10);
        assertThat(movimientoCaptor.getValue().getCuenta().getNumeroCuenta()).isEqualTo(numeroCuenta);
    }

    @Test
    public void shouldThrowExceptioWhenSaldoInicialIsZeroAndMovimientoIsDebito() {
        double valor = 15.0;
        String numeroCuenta = "2345";
        MovimientoRequestDTO movimientoRequestDTO = new MovimientoRequestDTO(TipoMovimiento.DEBITO, valor, numeroCuenta);
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(numeroCuenta);
        cuenta.setSaldoInicial(0);
        when(cuentaRepository.findByNumeroCuenta(numeroCuenta)).thenReturn(cuenta);
        when(movimientoRepository.findByCuentaId(cuenta.getId())).thenReturn(Collections.emptyList());

        assertThrows(IllegalArgumentException.class, () -> movimientoService.saveMovimiento(movimientoRequestDTO));
    }

    @Test
    public void shouldGetAllMovimientos() {
        Cuenta cuenta = new Cuenta("123", TipoCuenta.AHORROS, 100);
        List<Movimiento> movimientos = new ArrayList<>();
        Movimiento movimiento = new Movimiento();
        movimiento.setTipoMovimiento(TipoMovimiento.CREDITO);
        movimiento.setValor(10);
        movimiento.setSaldo(30);
        movimiento.setCuenta(cuenta);
        movimientos.add(movimiento);
        when(movimientoRepository.findAll()).thenReturn(movimientos);

        List<MovimientoResponseDTO> actualResponse = movimientoService.getMovimientos();

        assertThat(actualResponse).isNotEmpty();
        assertThat(actualResponse.get(0).getTipoCuenta()).isEqualTo(TipoCuenta.AHORROS);
        assertThat(actualResponse.get(0).getTipoMovimiento()).isEqualTo("Deposito de 10.0");
        assertThat(actualResponse.get(0).getNumeroCuenta()).isEqualTo("123");
        assertThat(actualResponse.get(0).getEstado()).isEqualTo("true");
    }
}