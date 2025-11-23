package com.tienda.services;

import com.tienda.ports.ProveedorRepositoryPort;
import com.tienda.ports.ProveedorServicePort;
import com.tienda.model.Proveedor;

import java.util.List;

public class ProveedorService implements ProveedorServicePort {

    private final ProveedorRepositoryPort proveedorRepository;

    public ProveedorService(ProveedorRepositoryPort proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    public int registrarNuevoProveedor(Proveedor proveedor) {
        // Lógica de validación de NIF/Nombre aquí
        if (proveedor.getNif() == null || proveedor.getNif().trim().isEmpty()) {
            throw new IllegalArgumentException("El NIF del proveedor es obligatorio.");
        }
        // Validación de 'nombre' ELIMINADA
        
        System.out.println("LOG: Lógica de negocio (Service) de Proveedor ejecutada. Delegando a Persistencia.");
        
        return proveedorRepository.save(proveedor);
    }

    // ... (El resto de métodos quedan igual)
    // El método actualizarDatosProveedor debe seguir funcionando ya que solo usa el puerto.
    @Override
    public Proveedor consultarProveedorPorId(int id) {
        return proveedorRepository.findById(id);
    }

    @Override
    public List<Proveedor> obtenerTodosLosProveedores() {
        return proveedorRepository.findAll();
    }

    @Override
    public boolean actualizarDatosProveedor(Proveedor proveedor) {
        if (proveedor.getIdProveedor() <= 0) {
            throw new IllegalArgumentException("ID de Proveedor inválido para actualización.");
        }
        return proveedorRepository.update(proveedor);
    }

    @Override
    public boolean eliminarProveedor(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de Proveedor inválido para eliminación.");
        }
        return proveedorRepository.delete(id);
    }
}