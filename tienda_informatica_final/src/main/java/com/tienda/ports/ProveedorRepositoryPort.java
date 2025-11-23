package com.tienda.ports;

import com.tienda.model.Proveedor;
import java.util.List;

public interface ProveedorRepositoryPort {
    /**
     * Guarda un proveedor y su dirección de forma transaccional.
     * @param proveedor El objeto Proveedor a persistir.
     * @return El ID generado por la base de datos (idProveedor).
     */
    int save(Proveedor proveedor);
    
    Proveedor findById(int id);

    List<Proveedor> findAll();

    boolean update(Proveedor proveedor);

    boolean delete(int id);
}