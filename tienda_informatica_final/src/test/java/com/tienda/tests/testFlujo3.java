package com.tienda.tests;

import com.tienda.adapters.MySQLProveedorRepositoryAdapter;
import com.tienda.services.ProveedorService;
import com.tienda.ports.ProveedorRepositoryPort;
import com.tienda.ports.ProveedorServicePort;

import com.tienda.model.Proveedor;
import com.tienda.model.Direccion;

import java.util.List;
import java.util.Scanner;

public class testFlujo3 {

    private static int solicitarOpcion(Scanner scanner) {
        System.out.print(">> ");
        if (scanner.hasNextInt()) {
            int opcion = scanner.nextInt();
            scanner.nextLine();
            return opcion;
        } else {
            System.out.println("Entrada inválida. Debe ser un número entero.");
            scanner.nextLine();
            return 0;
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n===== FLÚJO 3: GESTIÓN DE PROVEEDOR (CRUD) =====");
        System.out.println("1. Registrar Nuevo Proveedor");
        System.out.println("2. Actualizar Proveedor y Dirección");
        System.out.println("3. Consultar Proveedor por ID");
        System.out.println("4. Listar Todos los Proveedores");
        System.out.println("5. Eliminar Proveedor por ID");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public static void main(String[] args) {

        ProveedorRepositoryPort adaptadorBD = new MySQLProveedorRepositoryAdapter();
        ProveedorServicePort servicioProveedor = new ProveedorService(adaptadorBD);

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            opcion = solicitarOpcion(scanner); 

            try {
                switch (opcion) {
                    case 1:
                        registrarProveedor(scanner, servicioProveedor);
                        break;
                    case 2:
                        actualizarProveedor(scanner, servicioProveedor);
                        break;
                    case 3:
                        consultarProveedorPorId(scanner, servicioProveedor);
                        break;
                    case 4:
                        listarTodosLosProveedores(servicioProveedor);
                        break;
                    case 5:
                        eliminarProveedor(scanner, servicioProveedor);
                        break;
                    case 6:
                        System.out.println("Saliendo del Flujo 3. ¡Adiós!");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (IllegalArgumentException e) {
                System.err.println("ERROR DE VALIDACIÓN: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("ERROR INESPERADO EN LA OPERACIÓN: " + e.getMessage());
                e.printStackTrace();
            }
        } while (opcion != 6);
        
        scanner.close();
    }

    // 1. REGISTRAR PROVEEDOR
    private static void registrarProveedor(Scanner scanner, ProveedorServicePort servicio) {
        System.out.println("\n--- REGISTRAR NUEVO PROVEEDOR ---");
        
        System.out.print("NIF/RUC/ID Fiscal: ");
        String nif = scanner.nextLine();

        //DIRECCIÓN
        System.out.println("\n--- Datos de Dirección ---");
        
        System.out.print("Ciudad: ");
        String ciudad = scanner.nextLine();
        
        System.out.print("Barrio: ");
        String barrio = scanner.nextLine();
        
        System.out.print("Calle/Carrera: ");
        String calle = scanner.nextLine();
    
        Direccion nuevaDireccion = new Direccion(ciudad, calle, barrio);

        // ENSAMBLAR Y GUARDAR
        Proveedor nuevoProveedor = new Proveedor(0, nif, nuevaDireccion);

        int idGenerado = servicio.registrarNuevoProveedor(nuevoProveedor);

        if (idGenerado > 0) {
            System.out.println("Proveedor (NIF: " + nif + ") y Dirección registrados exitosamente. ID Proveedor: " + idGenerado);
            System.out.println("   ID Dirección: " + nuevoProveedor.getDireccion().getIdDIRECCION());
        } else {
            System.out.println("Error al registrar el proveedor.");
        }
    }

    // 3. CONSULTAR PROVEEDOR
    private static void consultarProveedorPorId(Scanner scanner, ProveedorServicePort servicio) {
        System.out.print("Ingrese el ID del proveedor a consultar: ");
        int id = solicitarOpcion(scanner);

        Proveedor proveedor = servicio.consultarProveedorPorId(id);

        if (proveedor != null) {
            System.out.println("\n--- DETALLES DEL PROVEEDOR ID: " + proveedor.getIdProveedor() + " ---");
            System.out.println("NIF: " + proveedor.getNif());
            
            // Dirección
            Direccion d = proveedor.getDireccion();
            System.out.println("\n[DIRECCIÓN ID: " + d.getIdDIRECCION() + "]");
            System.out.println("  Ciudad: " + d.getCiudad());
            System.out.println("  Barrio: " + d.getBarrio());
            System.out.println("  Calle: " + d.getCalle());

        } else {
            System.out.println("Proveedor con ID " + id + " no encontrado.");
        }
    }

    // 4. LISTAR PROVEEDORES
    private static void listarTodosLosProveedores(ProveedorServicePort servicio) {
        System.out.println("\n--- LISTADO COMPLETO DE PROVEEDORES ---");

        List<Proveedor> proveedores = servicio.obtenerTodosLosProveedores();

        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
            return;
        }

        System.out.printf("%-5s | %-15s | %s%n", "ID", "NIF", "DIRECCIÓN (Ciudad/Barrio)");
        System.out.println("---------------------------------------------------------");

        for (Proveedor p : proveedores) {
            String direccionResumen = (p.getDireccion() != null) ? p.getDireccion().getCiudad() + "/" + p.getDireccion().getBarrio() : "N/A";
            
            System.out.printf("%-5d | %-15s | %s%n", p.getIdProveedor(), p.getNif(), direccionResumen);
        }
    }

    // 2. ACTUALIZAR PROVEEDOR
    private static void actualizarProveedor(Scanner scanner, ProveedorServicePort servicio) {
        System.out.println("\n--- ACTUALIZAR PROVEEDOR Y DIRECCIÓN ---");
        System.out.print("Ingrese el ID del proveedor a actualizar: ");
        int id = solicitarOpcion(scanner);

        Proveedor proveedorExistente = servicio.consultarProveedorPorId(id);

        if (proveedorExistente == null) {
            System.out.println("Proveedor con ID " + id + " no encontrado.");
            return;
        }

        // Proveedor (Solo NIF)
        System.out.println("\n[PROVEEDOR] Actual NIF: " + proveedorExistente.getNif());
        
        System.out.print("Nuevo NIF (deje vacío para mantener): ");
        String nuevoNif = scanner.nextLine();
        if (!nuevoNif.isEmpty()) proveedorExistente.setNif(nuevoNif);

        // Dirección (Sin País)
        Direccion d = proveedorExistente.getDireccion();
        System.out.println("\n[DIRECCIÓN] Actual: " + d.getCiudad() + ", " + d.getBarrio());
        
        System.out.print("Nueva Ciudad (deje vacío para mantener): ");
        String nuevaCiudad = scanner.nextLine();
        if (!nuevaCiudad.isEmpty()) d.setCiudad(nuevaCiudad);

        System.out.print("Nuevo Barrio (deje vacío para mantener): ");
        String nuevoBarrio = scanner.nextLine();
        if (!nuevoBarrio.isEmpty()) d.setBarrio(nuevoBarrio);
        
        System.out.print("Nueva Calle (deje vacío para mantener): ");
        String nuevaCalle = scanner.nextLine();
        if (!nuevaCalle.isEmpty()) d.setCalle(nuevaCalle);

        boolean actualizado = servicio.actualizarDatosProveedor(proveedorExistente);

        if (actualizado) {
            System.out.println("Proveedor con ID " + id + " actualizado exitosamente (incluyendo Dir).");
        } else {
            System.out.println("Error al actualizar el proveedor. Revise los logs.");
        }
    }

    // 5. ELIMINAR PROVEEDOR
    private static void eliminarProveedor(Scanner scanner, ProveedorServicePort servicio) {
        System.out.print("Ingrese el ID del proveedor a eliminar: ");
        int id = solicitarOpcion(scanner);

        boolean eliminado = servicio.eliminarProveedor(id);

        if (eliminado) {
            System.out.println("Proveedor con ID " + id + " eliminado exitosamente.");
        } else {
            System.out.println("Error al eliminar el proveedor (puede que no exista o haya dependencias sin cascada).");
        }
    }
}