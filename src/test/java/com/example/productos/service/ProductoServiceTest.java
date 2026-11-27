package com.example.productos.service;

import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void findAll_debeRetornarTodosLosProductos() {
        List<Producto> productos = List.of(
            new Producto(1L, "Taladro", new BigDecimal("1250.00"), 8),
            new Producto(2L, "Destornillador", new BigDecimal("150.50"), 20)
        );
        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> resultado = productoService.findAll();

        assertThat(resultado).hasSize(2);
        assertThat(resultado).extracting(Producto::getNombre)
            .containsExactly("Taladro", "Destornillador");
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void create_debeGuardarYRetornarElProducto() {
        Producto producto = new Producto("Rotomartillo", new BigDecimal("2350.00"), 5);
        Producto guardado = new Producto(1L, "Rotomartillo", new BigDecimal("2350.00"), 5);
        when(productoRepository.save(producto)).thenReturn(guardado);

        Producto resultado = productoService.create(producto);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Rotomartillo");
        assertThat(resultado.getPrecio()).isEqualByComparingTo("2350.00");
        assertThat(resultado.getStock()).isEqualTo(5);
        verify(productoRepository).save(producto);
    }

    @Test
    void create_debeRechazarNombreVacio() {
        Producto producto = new Producto(" ", new BigDecimal("100.00"), 3);

        assertThatThrownBy(() -> productoService.create(producto))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("El nombre del producto es obligatorio");
        verify(productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void create_debeEnviarProductoCorrectoAlRepositorio() {
        Producto producto = new Producto("Sierra", new BigDecimal("780.00"), 4);
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> {
            Producto recibido = invocation.getArgument(0);
            recibido.setId(10L);
            return recibido;
        });

        productoService.create(producto);

        ArgumentCaptor<Producto> captor = ArgumentCaptor.forClass(Producto.class);
        verify(productoRepository).save(captor.capture());
        assertThat(captor.getValue().getNombre()).isEqualTo("Sierra");
        assertThat(captor.getValue().getStock()).isEqualTo(4);
    }
}
