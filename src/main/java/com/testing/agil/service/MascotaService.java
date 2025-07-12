package com.testing.agil.service;

import com.testing.agil.model.Mascota;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz del servicio para gestión de mascotas.
 * Define las operaciones de negocio para el manejo de mascotas.
 */
public interface MascotaService {
    
    /**
     * Registra una nueva mascota en el sistema.
     * 
     * @param mascota La mascota a registrar
     * @return La mascota registrada con su ID asignado
     * @throws IllegalArgumentException si la mascota es inválida
     */
    Mascota registrarMascota(Mascota mascota);
    
    /**
     * Busca una mascota por su ID.
     * 
     * @param id El ID de la mascota
     * @return Optional con la mascota si existe, empty si no
     */
    Optional<Mascota> buscarPorId(Long id);
    
    /**
     * Obtiene todas las mascotas registradas.
     * 
     * @return Lista de todas las mascotas
     */
    List<Mascota> listarTodas();
    
    /**
     * Actualiza los datos de una mascota existente.
     * 
     * @param mascota La mascota con los datos actualizados
     * @return La mascota actualizada
     * @throws IllegalArgumentException si la mascota es inválida
     */
    Mascota actualizarMascota(Mascota mascota);
    
    /**
     * Elimina una mascota del sistema.
     * 
     * @param id El ID de la mascota a eliminar
     * @return true si se eliminó, false si no existía
     */
    boolean eliminarMascota(Long id);
    
    /**
     * Busca mascotas por especie.
     * 
     * @param especie La especie a buscar
     * @return Lista de mascotas de la especie especificada
     */
    List<Mascota> buscarPorEspecie(String especie);
    
    /**
     * Lista mascotas cachorros (menores a 1 año).
     * 
     * @return Lista de mascotas cachorros
     */
    List<Mascota> listarCachorros();
    
    /**
     * Lista mascotas senior (mayores a 7 años).
     * 
     * @return Lista de mascotas senior
     */
    List<Mascota> listarSenior();
    
    /**
     * Busca mascotas por nombre (búsqueda parcial).
     * 
     * @param patron El patrón de búsqueda
     * @return Lista de mascotas que coinciden con el patrón
     */
    List<Mascota> buscarPorNombre(String patron);
    
    /**
     * Busca mascotas que requieren vacunación.
     * (mascotas sin vacunas o con vacunas vencidas)
     * 
     * @return Lista de mascotas que necesitan vacunación
     */
    List<Mascota> listarMascotasQueRequierenVacunacion();
    
    /**
     * Registra una vacuna para una mascota.
     * 
     * @param mascotaId ID de la mascota
     * @param fechaVacuna Fecha de la vacuna
     * @return true si se registró correctamente
     */
    boolean registrarVacuna(Long mascotaId, LocalDate fechaVacuna);
    
    /**
     * Obtiene estadísticas de mascotas por especie.
     * 
     * @return Map con especie como clave y cantidad como valor
     */
    java.util.Map<String, Long> obtenerEstadisticasPorEspecie();
    
    /**
     * Cuenta el total de mascotas registradas.
     * 
     * @return Número total de mascotas
     */
    long contarTotalMascotas();
    
    /**
     * Valida si una mascota cumple con todos los requisitos de negocio.
     * 
     * @param mascota La mascota a validar
     * @return true si es válida
     * @throws IllegalArgumentException con detalles del error si es inválida
     */
    boolean validarMascota(Mascota mascota);
}
