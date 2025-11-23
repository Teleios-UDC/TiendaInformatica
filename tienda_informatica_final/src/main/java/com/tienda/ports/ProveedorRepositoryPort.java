package com.tienda.ports;

import com.tienda.model.Proveedor;
import java.util.List;

public interface ProveedorRepositoryPort {

    int save(Proveedor proveedor);
    
    Proveedor findById(int id);

    List<Proveedor> findAll();

    boolean update(Proveedor proveedor);

    boolean delete(int id);
}