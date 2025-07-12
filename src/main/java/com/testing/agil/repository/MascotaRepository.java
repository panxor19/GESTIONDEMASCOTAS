package com.testing.agil.repository;

import com.testing.agil.model.Mascota;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones de persistencia para mascotas
 * Aplica principio DIP (Dependency Inversion Principle)
 * Permite implementaciones diferentes (SQLite, PostgreSQL, MongoDB, etc.)
 */
public interface MascotaRepository {
    
    /**
     * Crea una nueva mascota en el sistema
     * @param mascota la mascota a crear
     * @return la mascota creada con su ID asignado
     * @throws IllegalArgumentException si la mascota es null o inválida
     */
    Mascota crear(Mascota mascota);
    
    /**
     * Busca una mascota por su ID
     * @param id el ID de la mascota
     * @return Optional con la mascota si se encuentra, vacío si no
     */
    Optional<Mascota> buscarPorId(Long id);
    
    /**
     * Busca mascotas por nombre (búsqueda parcial)
     * @param nombre el nombre o parte del nombre a buscar
     * @return lista de mascotas que coinciden
     */
    List<Mascota> buscarPorNombre(String nombre);
    
    /**
     * Busca mascotas por especie
     * @param especie la especie a buscar (perro, gato, etc.)
     * @return lista de mascotas de esa especie
     */
    List<Mascota> buscarPorEspecie(String especie);
    
    /**
     * Busca mascotas por propietario
     * @param propietario el nombre del propietario
     * @return lista de mascotas del propietario
     */
    List<Mascota> buscarPorPropietario(String propietario);
    
    /**
     * Busca mascotas por email del propietario
     * @param email el email del propietario
     * @return lista de mascotas del propietario
     */
    List<Mascota> buscarPorEmail(String email);
    
    /**
     * Lista todas las mascotas en el sistema
     * @return lista completa de mascotas
     */
    List<Mascota> listarTodas();
    
    /**
     * Lista solo las mascotas activas
     * @return lista de mascotas activas
     */
    List<Mascota> listarActivas();
    
    /**
     * Lista mascotas cachorros (menores a 1 año)
     * @return lista de mascotas cachorros
     */
    List<Mascota> listarCachorros();
    
    /**
     * Lista mascotas senior (mayores a 7 años)
     * @return lista de mascotas senior
     */
    List<Mascota> listarSenior();
    
    /**
     * Actualiza los datos de una mascota existente
     * @param mascota la mascota con datos actualizados
     * @return la mascota actualizada
     * @throws IllegalArgumentException si la mascota es null o no existe
     */
    Mascota actualizar(Mascota mascota);
    
    /**
     * Elimina una mascota del sistema (eliminación lógica)
     * @param id el ID de la mascota a eliminar
     * @return true si se eliminó correctamente
     */
    boolean eliminar(Long id);
    
    /**
     * Elimina físicamente una mascota del sistema
     * @param id el ID de la mascota a eliminar
     * @return true si se eliminó correctamente
     */
    boolean eliminarFisicamente(Long id);
    
    /**
     * Cuenta el total de mascotas en el sistema
     * @return número total de mascotas
     */
    long contar();
    
    /**
     * Cuenta las mascotas activas
     * @return número de mascotas activas
     */
    long contarActivas();
    
    /**
     * Cuenta mascotas por especie
     * @param especie la especie a contar
     * @return número de mascotas de esa especie
     */
    long contarPorEspecie(String especie);
    
    /**
     * Verifica si existe una mascota con el email del propietario
     * @param email el email a verificar
     * @return true si existe al menos una mascota con ese email
     */
    boolean existePorEmail(String email);
    
    /**
     * Actualiza el estado de esterilización de una mascota
     * @param id el ID de la mascota
     * @param esterilizado el nuevo estado
     * @return true si se actualizó correctamente
     */
    boolean actualizarEsterilizacion(Long id, boolean esterilizado);
    
    /**
     * Actualiza el peso de una mascota
     * @param id el ID de la mascota
     * @param peso el nuevo peso
     * @return true si se actualizó correctamente
     */
    boolean actualizarPeso(Long id, double peso);
}
