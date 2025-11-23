package com.tienda.ports;

import com.tienda.model.Proveedor;
import java.util.List;

public interface ProveedorServicePort {

    int registrarNuevoProveedor(Proveedor proveedor);
    Proveedor consultarProveedorPorId(int id);

    List<Proveedor> obtenerTodosLosProveedores();

    boolean actualizarDatosProveedor(Proveedor proveedor);

    boolean eliminarProveedor(int id);
}