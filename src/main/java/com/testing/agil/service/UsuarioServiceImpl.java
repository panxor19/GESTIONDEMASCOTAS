package com.testing.agil.service;

import com.testing.agil.model.Usuario;
import com.testing.agil.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;

/**
 * Implementación concreta del servicio de usuarios
 * Aplica múltiples principios SOLID:
 * - SRP: Solo maneja lógica de negocio de usuarios
 * - OCP: Abierto para extensión, cerrado para modificación
 * - DIP: Depende de la abstracción UsuarioRepository, no de la implementación
 * - ISP: Implementa solo la interfaz necesaria
 */
public class UsuarioServiceImpl implements UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    
    /**
     * Constructor que recibe la dependencia (Dependency Injection)
     * Implementa principio DIP
     */
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        if (usuarioRepository == null) {
            throw new IllegalArgumentException("UsuarioRepository no puede ser null");
        }
        this.usuarioRepository = usuarioRepository;
    }
    
    @Override
    public Usuario crearUsuario(Usuario usuario) {
        // Validaciones de negocio
        validarUsuarioParaCreacion(usuario);
        
        // Verificar que el email no exista
        if (usuarioRepository.existePorEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + usuario.getEmail());
        }
        
        // Crear usuario
        return usuarioRepository.crear(usuario);
    }
    
    @Override
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        
        return usuarioRepository.buscarPorId(id);
    }
    
    @Override
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Optional.empty();
        }
        
        return usuarioRepository.buscarPorEmail(email.trim().toLowerCase());
    }
    
    @Override
    public List<Usuario> listarTodosLosUsuarios() {
        return usuarioRepository.listarTodos();
    }
    
    @Override
    public List<Usuario> listarUsuariosActivos() {
        return usuarioRepository.listarActivos();
    }
    
    @Override
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        // Validar entrada
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido");
        }
        
        validarUsuarioParaActualizacion(usuarioActualizado);
        
        // Verificar que el usuario existe
        Optional<Usuario> usuarioExistente = usuarioRepository.buscarPorId(id);
        if (usuarioExistente.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        
        // Verificar que el email no esté en uso por otro usuario
        Optional<Usuario> usuarioConEmail = usuarioRepository.buscarPorEmail(usuarioActualizado.getEmail());
        if (usuarioConEmail.isPresent() && !usuarioConEmail.get().getId().equals(id)) {
            throw new IllegalArgumentException("El email ya está en uso por otro usuario");
        }
        
        // Actualizar usuario manteniendo el ID original
        usuarioActualizado.setId(id);
        return usuarioRepository.actualizar(usuarioActualizado);
    }
    
    @Override
    public boolean eliminarUsuario(Long id) {
        // Eliminación lógica - marcar como inactivo
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido");
        }
        
        Optional<Usuario> usuario = usuarioRepository.buscarPorId(id);
        if (usuario.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        
        Usuario usuarioAEliminar = usuario.get();
        usuarioAEliminar.setActivo(false);
        
        try {
            usuarioRepository.actualizar(usuarioAEliminar);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public boolean eliminarUsuarioFisicamente(Long id) {
        // Eliminación física de la base de datos
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido");
        }
        
        Optional<Usuario> usuario = usuarioRepository.buscarPorId(id);
        if (usuario.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        
        return usuarioRepository.eliminar(id);
    }
    
    @Override
    public long contarUsuarios() {
        return usuarioRepository.contar();
    }
    
    @Override
    public boolean emailDisponible(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        return !usuarioRepository.existePorEmail(email.trim().toLowerCase());
    }
    
    /**
     * Validaciones de negocio para creación de usuario
     * Aplica principio SRP - método con una sola responsabilidad
     */
    private void validarUsuarioParaCreacion(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser null");
        }
        
        validarDatosBasicos(usuario);
    }
    
    /**
     * Validaciones de negocio para actualización de usuario
     * Aplica principio SRP - método con una sola responsabilidad
     */
    private void validarUsuarioParaActualizacion(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser null");
        }
        
        validarDatosBasicos(usuario);
    }
    
    /**
     * Validaciones básicas de datos de usuario
     * Aplica principio DRY - evita duplicación de código
     */
    private void validarDatosBasicos(Usuario usuario) {
        // Validar nombre
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        
        if (usuario.getNombre().trim().length() < 2) {
            throw new IllegalArgumentException("El nombre debe tener al menos 2 caracteres");
        }
        
        if (usuario.getNombre().trim().length() > 100) {
            throw new IllegalArgumentException("El nombre no puede exceder 100 caracteres");
        }
        
        // Validar email
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        
        if (!usuario.esEmailValido()) {
            throw new IllegalArgumentException("El formato del email es inválido");
        }
        
        if (usuario.getEmail().trim().length() > 150) {
            throw new IllegalArgumentException("El email no puede exceder 150 caracteres");
        }
        
        // Validar edad
        if (usuario.getEdad() < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }
        
        if (usuario.getEdad() > 150) {
            throw new IllegalArgumentException("La edad no puede ser mayor a 150 años");
        }
        
        // Normalizar email a minúsculas
        usuario.setEmail(usuario.getEmail().trim().toLowerCase());
        
        // Normalizar nombre
        usuario.setNombre(usuario.getNombre().trim());
    }
}
