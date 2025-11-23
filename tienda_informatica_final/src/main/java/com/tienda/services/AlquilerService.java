package com.tienda.services;

import com.tienda.ports.AlquilerRepositoryPort;
import com.tienda.ports.AlquilerServicePort;
import com.tienda.model.Alquiler;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio de Alquiler: Contiene la lógica de negocio y coordina la persistencia transaccional.
 */
public class AlquilerService implements AlquilerServicePort {

    private final AlquilerRepositoryPort alquilerRepository;

    public AlquilerService(AlquilerRepositoryPort alquilerRepository) {
        this.alquilerRepository = alquilerRepository;
    }

    @Override
    public int registrarNuevoAlquiler(Alquiler alquiler) {
        //Lógica de Validación de Negocio
        if (alquiler.getCliente().getIdCliente() <= 0) {
            throw new IllegalArgumentException("El alquiler debe estar asociado a un Cliente válido.");
        }
        if (alquiler.getProducto().getIdProducto() <= 0) {
            throw new IllegalArgumentException("El alquiler debe incluir un Producto válido.");
        }
        if (alquiler.getInicioAlquiler() == null || alquiler.getFinAlquiler() == null) {
             throw new IllegalArgumentException("Las fechas de inicio y fin del alquiler son obligatorias.");
        }
        if (alquiler.getFinAlquiler().isBefore(alquiler.getInicioAlquiler())) {
             throw new IllegalArgumentException("La fecha de fin de alquiler no puede ser anterior a la fecha de inicio.");
        }
        if (alquiler.getTransaccion() == null || alquiler.getTransaccion().getPrecio() <= 0) {
             throw new IllegalArgumentException("El Precio de la Transacción es inválido.");
        }

        System.out.println("LOG: Lógica de negocio (Service) de Alquiler ejecutada. Delegando a Persistencia (Transaccional).");
        
        return alquilerRepository.save(alquiler);
    }

    @Override
    public Alquiler consultarAlquilerPorId(int id) {
        return alquilerRepository.findById(id);
    }

    @Override
    public List<Alquiler> obtenerTodosLosAlquileres() {
        return alquilerRepository.findAll();
    }

    @Override
    public boolean actualizarDatosAlquiler(Alquiler alquiler) {
        if (alquiler.getIdAlquiler() <= 0) {
            throw new IllegalArgumentException("ID de Alquiler inválido para actualización.");
        }
        return alquilerRepository.update(alquiler);
    }

    @Override
    public boolean eliminarAlquiler(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de Alquiler inválido para anulación.");
        }
        return alquilerRepository.delete(id);
    }
}