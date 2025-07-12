package com.testing.agil.service;

import com.testing.agil.model.Usuario;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz del servicio de usuarios
 * Define la lógica de negocio para operaciones CRUD
 * Implementa principio ISP (Interface Segregation Principle)
 */
public interface UsuarioService {
    
    /**
     * Crear un nuevo usuario con validaciones
     * @param usuario Usuario a crear
     * @return Usuario creado
     * @throws IllegalArgumentException si los datos son inválidos
     */
    Usuario crearUsuario(Usuario usuario);
    
    /**
     * Buscar usuario por ID
     * @param id ID del usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> buscarUsuarioPorId(Long id);
    
    /**
     * Buscar usuario por email
     * @param email Email del usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> buscarUsuarioPorEmail(String email);
    
    /**
     * Listar todos los usuarios
     * @return Lista de usuarios
     */
    List<Usuario> listarTodosLosUsuarios();
    
    /**
     * Listar solo usuarios activos
     * @return Lista de usuarios activos
     */
    List<Usuario> listarUsuariosActivos();
    
    /**
     * Actualizar un usuario existente
     * @param id ID del usuario a actualizar
     * @param usuarioActualizado Datos actualizados
     * @return Usuario actualizado
     * @throws IllegalArgumentException si el usuario no existe o los datos son inválidos
     */
    Usuario actualizarUsuario(Long id, Usuario usuarioActualizado);
    
    /**
     * Eliminar usuario (borrado lógico - marcar como inactivo)
     * @param id ID del usuario a eliminar
     * @return true si se eliminó correctamente
     * @throws IllegalArgumentException si el usuario no existe
     */
    boolean eliminarUsuario(Long id);
    
    /**
     * Eliminar usuario físicamente de la base de datos
     * @param id ID del usuario a eliminar
     * @return true si se eliminó correctamente
     * @throws IllegalArgumentException si el usuario no existe
     */
    boolean eliminarUsuarioFisicamente(Long id);
    
    /**
     * Contar total de usuarios registrados
     * @return Número total de usuarios
     */
    long contarUsuarios();
    
    /**
     * Validar si un email está disponible
     * @param email Email a validar
     * @return true si está disponible, false si ya existe
     */
    boolean emailDisponible(String email);
}
