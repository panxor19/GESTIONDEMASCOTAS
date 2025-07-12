package com.testing.agil;

import com.testing.agil.model.Usuario;
import com.testing.agil.repository.UsuarioRepository;
import com.testing.agil.repository.UsuarioRepositoryImpl;
import com.testing.agil.service.UsuarioService;
import com.testing.agil.service.UsuarioServiceImpl;

import java.util.List;
import java.util.Scanner;

/**
 * Clase principal de la aplicación CRUD con TDD
 * Punto de entrada para demostrar las funcionalidades implementadas
 */
public class Main {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static UsuarioService usuarioService;
    
    public static void main(String[] args) {
        System.out.println("=== PORTAFOLIO CRUD CON TDD ===");
        System.out.println("Testing Ágil + Automatización de Pruebas");
        System.out.println("=====================================\n");
        
        // Inicializar dependencias
        inicializarServicios();
        
        // Mostrar menú principal
        mostrarMenu();
        
        scanner.close();
    }
    
    private static void inicializarServicios() {
        try {
            UsuarioRepository usuarioRepository = new UsuarioRepositoryImpl();
            usuarioService = new UsuarioServiceImpl(usuarioRepository);
            System.out.println("✅ Servicios inicializados correctamente");
        } catch (Exception e) {
            System.err.println("❌ Error al inicializar servicios: " + e.getMessage());
            System.exit(1);
        }
    }
    
    private static void mostrarMenu() {
        int opcion;
        
        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Crear usuario");
            System.out.println("2. Listar usuarios");
            System.out.println("3. Buscar usuario por ID");
            System.out.println("4. Buscar usuario por email");
            System.out.println("5. Actualizar usuario");
            System.out.println("6. Eliminar usuario (lógico)");
            System.out.println("7. Eliminar usuario (físico)");
            System.out.println("8. Contar usuarios");
            System.out.println("9. Listar usuarios activos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir línea pendiente
            
            ejecutarOpcion(opcion);
            
        } while (opcion != 0);
        
        System.out.println("¡Gracias por usar el sistema CRUD con TDD!");
    }
    
    private static void ejecutarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1:
                    crearUsuario();
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 3:
                    buscarUsuarioPorId();
                    break;
                case 4:
                    buscarUsuarioPorEmail();
                    break;
                case 5:
                    actualizarUsuario();
                    break;
                case 6:
                    eliminarUsuarioLogico();
                    break;
                case 7:
                    eliminarUsuarioFisico();
                    break;
                case 8:
                    contarUsuarios();
                    break;
                case 9:
                    listarUsuariosActivos();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Opción no válida");
            }
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }
    
    private static void crearUsuario() {
        System.out.println("\n--- Crear Usuario ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine();
        
        Usuario usuario = new Usuario(nombre, email, edad);
        Usuario usuarioCreado = usuarioService.crearUsuario(usuario);
        
        System.out.println("✅ Usuario creado: " + usuarioCreado);
    }
    
    private static void listarUsuarios() {
        System.out.println("\n--- Lista de Usuarios ---");
        List<Usuario> usuarios = usuarioService.listarTodosLosUsuarios();
        
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados");
        } else {
            usuarios.forEach(System.out::println);
        }
    }
    
    private static void buscarUsuarioPorId() {
        System.out.println("\n--- Buscar Usuario por ID ---");
        System.out.print("ID del usuario: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        
        usuarioService.buscarUsuarioPorId(id)
                .ifPresentOrElse(
                        usuario -> System.out.println("✅ Usuario encontrado: " + usuario),
                        () -> System.out.println("❌ Usuario no encontrado")
                );
    }
    
    private static void buscarUsuarioPorEmail() {
        System.out.println("\n--- Buscar Usuario por Email ---");
        System.out.print("Email del usuario: ");
        String email = scanner.nextLine();
        
        usuarioService.buscarUsuarioPorEmail(email)
                .ifPresentOrElse(
                        usuario -> System.out.println("✅ Usuario encontrado: " + usuario),
                        () -> System.out.println("❌ Usuario no encontrado")
                );
    }
    
    private static void actualizarUsuario() {
        System.out.println("\n--- Actualizar Usuario ---");
        System.out.print("ID del usuario a actualizar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        
        if (usuarioService.buscarUsuarioPorId(id).isEmpty()) {
            System.out.println("❌ Usuario no encontrado");
            return;
        }
        
        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nuevo email: ");
        String email = scanner.nextLine();
        System.out.print("Nueva edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine();
        
        Usuario usuarioActualizado = new Usuario(nombre, email, edad);
        Usuario resultado = usuarioService.actualizarUsuario(id, usuarioActualizado);
        
        System.out.println("✅ Usuario actualizado: " + resultado);
    }
    
    private static void eliminarUsuarioLogico() {
        System.out.println("\n--- Eliminar Usuario (Lógico) ---");
        System.out.print("ID del usuario a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        
        if (usuarioService.eliminarUsuario(id)) {
            System.out.println("✅ Usuario eliminado (marcado como inactivo)");
        } else {
            System.out.println("❌ Error al eliminar usuario");
        }
    }
    
    private static void eliminarUsuarioFisico() {
        System.out.println("\n--- Eliminar Usuario (Físico) ---");
        System.out.print("ID del usuario a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        
        System.out.print("¿Está seguro? (s/n): ");
        String confirmacion = scanner.nextLine();
        
        if ("s".equalsIgnoreCase(confirmacion)) {
            if (usuarioService.eliminarUsuarioFisicamente(id)) {
                System.out.println("✅ Usuario eliminado físicamente");
            } else {
                System.out.println("❌ Error al eliminar usuario");
            }
        } else {
            System.out.println("Operación cancelada");
        }
    }
    
    private static void contarUsuarios() {
        System.out.println("\n--- Contador de Usuarios ---");
        long total = usuarioService.contarUsuarios();
        System.out.println("Total de usuarios: " + total);
    }
    
    private static void listarUsuariosActivos() {
        System.out.println("\n--- Lista de Usuarios Activos ---");
        List<Usuario> usuarios = usuarioService.listarUsuariosActivos();
        
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios activos");
        } else {
            usuarios.forEach(System.out::println);
        }
    }
}
