package com.example.productos.service;

import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto create(Producto producto) {
        if (producto == null) throw new IllegalArgumentException("El producto no puede ser nulo");
        if (producto.getNombre() == null || producto.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        if (producto.getPrecio() == null || producto.getPrecio().signum() <= 0)
            throw new IllegalArgumentException("El precio debe ser mayor que cero");
        if (producto.getStock() == null || producto.getStock() < 0)
            throw new IllegalArgumentException("El stock no puede ser negativo");
        return productoRepository.save(producto);
    }

    public Producto findById(Long id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró el producto con ID: " + id));
    }
}
