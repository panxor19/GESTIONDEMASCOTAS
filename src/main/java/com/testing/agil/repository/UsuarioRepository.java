package com.testing.agil.repository;

import com.testing.agil.model.Usuario;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz del repositorio de usuarios
 * Implementa el patrón Repository y principio DIP (Dependency Inversion Principle)
 */
public interface UsuarioRepository {
    
    /**
     * Crear un nuevo usuario
     * @param usuario Usuario a crear
     * @return Usuario creado con ID asignado
     */
    Usuario crear(Usuario usuario);
    
    /**
     * Buscar usuario por ID
     * @param id ID del usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> buscarPorId(Long id);
    
    /**
     * Buscar usuario por email
     * @param email Email del usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> buscarPorEmail(String email);
    
    /**
     * Listar todos los usuarios
     * @return Lista de todos los usuarios
     */
    List<Usuario> listarTodos();
    
    /**
     * Listar usuarios activos
     * @return Lista de usuarios activos
     */
    List<Usuario> listarActivos();
    
    /**
     * Actualizar un usuario existente
     * @param usuario Usuario con datos actualizados
     * @return Usuario actualizado
     */
    Usuario actualizar(Usuario usuario);
    
    /**
     * Eliminar usuario por ID
     * @param id ID del usuario a eliminar
     * @return true si se eliminó, false si no existía
     */
    boolean eliminar(Long id);
    
    /**
     * Contar total de usuarios
     * @return Número total de usuarios
     */
    long contar();
    
    /**
     * Verificar si existe un usuario con el email dado
     * @param email Email a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existePorEmail(String email);
}
