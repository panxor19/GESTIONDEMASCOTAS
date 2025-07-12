package com.testing.agil.service;

import com.testing.agil.model.Mascota;
import com.testing.agil.repository.MascotaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de gestión de mascotas.
 * Contiene la lógica de negocio para el manejo de mascotas.
 */
public class MascotaServiceImpl implements MascotaService {
    
    private final MascotaRepository mascotaRepository;
    
    /**
     * Constructor que recibe el repositorio de mascotas.
     * 
     * @param mascotaRepository El repositorio de mascotas
     */
    public MascotaServiceImpl(MascotaRepository mascotaRepository) {
        if (mascotaRepository == null) {
            throw new IllegalArgumentException("El repositorio de mascotas no puede ser nulo");
        }
        this.mascotaRepository = mascotaRepository;
    }
    
    @Override
    public Mascota registrarMascota(Mascota mascota) {
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota no puede ser nula");
        }
        
        // Validar datos de la mascota
        validarMascota(mascota);
        
        // Verificar que no exista una mascota con el mismo nombre y propietario
        List<Mascota> mascotasExistentes = mascotaRepository.buscarPorNombre(mascota.getNombre());
        boolean yaExiste = mascotasExistentes.stream()
            .anyMatch(m -> m.getPropietario().equalsIgnoreCase(mascota.getPropietario()));
        
        if (yaExiste) {
            throw new IllegalArgumentException(
                String.format("Ya existe una mascota llamada '%s' para el propietario '%s'", 
                    mascota.getNombre(), mascota.getPropietario()));
        }
        
        return mascotaRepository.crear(mascota);
    }
    
    @Override
    public Optional<Mascota> buscarPorId(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        
        return mascotaRepository.buscarPorId(id);
    }
    
    @Override
    public List<Mascota> listarTodas() {
        return mascotaRepository.listarTodas();
    }
    
    @Override
    public Mascota actualizarMascota(Mascota mascota) {
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota no puede ser nula");
        }
        
        if (mascota.getId() == null || mascota.getId() <= 0) {
            throw new IllegalArgumentException("El ID de la mascota debe ser válido para actualizar");
        }
        
        // Verificar que la mascota existe
        Optional<Mascota> mascotaExistente = mascotaRepository.buscarPorId(mascota.getId());
        if (mascotaExistente.isEmpty()) {
            throw new IllegalArgumentException("No existe una mascota con ID: " + mascota.getId());
        }
        
        // Validar los nuevos datos
        validarMascota(mascota);
        
        // Verificar que no exista otra mascota con el mismo nombre y propietario
        List<Mascota> mascotasConMismoNombre = mascotaRepository.buscarPorNombre(mascota.getNombre());
        boolean existeOtraConMismoNombre = mascotasConMismoNombre.stream()
            .anyMatch(m -> !m.getId().equals(mascota.getId()) && 
                         m.getPropietario().equalsIgnoreCase(mascota.getPropietario()));
        
        if (existeOtraConMismoNombre) {
            throw new IllegalArgumentException(
                String.format("Ya existe otra mascota llamada '%s' para el propietario '%s'", 
                    mascota.getNombre(), mascota.getPropietario()));
        }
        
        return mascotaRepository.actualizar(mascota);
    }
    
    @Override
    public boolean eliminarMascota(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        
        return mascotaRepository.eliminar(id);
    }
    
    @Override
    public List<Mascota> buscarPorEspecie(String especie) {
        if (especie == null || especie.trim().isEmpty()) {
            return List.of();
        }
        
        return mascotaRepository.buscarPorEspecie(especie.trim());
    }
    
    @Override
    public List<Mascota> listarCachorros() {
        return mascotaRepository.listarCachorros();
    }
    
    @Override
    public List<Mascota> listarSenior() {
        return mascotaRepository.listarSenior();
    }
    
    @Override
    public List<Mascota> buscarPorNombre(String patron) {
        if (patron == null || patron.trim().isEmpty()) {
            return List.of();
        }
        
        return mascotaRepository.buscarPorNombre(patron.trim());
    }
    
    @Override
    public List<Mascota> listarMascotasQueRequierenVacunacion() {
        // Simplificamos: mascotas que no han sido registradas recientemente como vacunadas
        // En una implementación real, tendríamos un campo específico para vacunas
        List<Mascota> todasLasMascotas = mascotaRepository.listarTodas();
        LocalDate haceUnAno = LocalDate.now().minusYears(1);
        
        // Por ahora, consideramos que todas las mascotas mayores a 1 año necesitan vacunación
        return todasLasMascotas.stream()
            .filter(mascota -> mascota.calcularEdadEnAnios() >= 1)
            .collect(Collectors.toList());
    }
    
    @Override
    public boolean registrarVacuna(Long mascotaId, LocalDate fechaVacuna) {
        if (mascotaId == null || mascotaId <= 0) {
            throw new IllegalArgumentException("El ID de la mascota debe ser válido");
        }
        
        if (fechaVacuna == null) {
            throw new IllegalArgumentException("La fecha de vacuna no puede ser nula");
        }
        
        if (fechaVacuna.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de vacuna no puede ser futura");
        }
        
        Optional<Mascota> mascotaOpt = mascotaRepository.buscarPorId(mascotaId);
        if (mascotaOpt.isEmpty()) {
            throw new IllegalArgumentException("No existe una mascota con ID: " + mascotaId);
        }
        
        // Por ahora, solo validamos que la mascota existe
        // En una implementación completa, tendríamos una tabla separada para vacunas
        return true;
    }
    
    @Override
    public Map<String, Long> obtenerEstadisticasPorEspecie() {
        List<Mascota> todasLasMascotas = mascotaRepository.listarTodas();
        
        return todasLasMascotas.stream()
            .collect(Collectors.groupingBy(
                Mascota::getEspecie,
                Collectors.counting()
            ));
    }
    
    @Override
    public long contarTotalMascotas() {
        return mascotaRepository.contar();
    }
    
    @Override
    public boolean validarMascota(Mascota mascota) {
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota no puede ser nula");
        }
        
        // Validar nombre
        if (!mascota.esNombreValido()) {
            throw new IllegalArgumentException("El nombre de la mascota debe tener entre 2 y 50 caracteres");
        }
        
        // Validar especie
        if (!mascota.esEspecieValida()) {
            throw new IllegalArgumentException("La especie debe ser válida (Perro, Gato, Ave, Reptil, Pez, Roedor, Otro)");
        }
        
        // Validar fecha de nacimiento (debe estar presente y no ser futura)
        if (mascota.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula");
        }
        
        if (mascota.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura");
        }
        
        // Validar que la mascota no sea demasiado vieja (más de 50 años)
        if (mascota.calcularEdadEnAnios() > 50) {
            throw new IllegalArgumentException("La edad de la mascota no puede ser mayor a 50 años");
        }
        
        // Validar propietario
        if (mascota.getPropietario() == null || mascota.getPropietario().trim().isEmpty() ||
            mascota.getPropietario().length() < 2 || mascota.getPropietario().length() > 100) {
            throw new IllegalArgumentException("El propietario debe tener entre 2 y 100 caracteres");
        }
        
        // Validar email
        if (!mascota.esEmailValido()) {
            throw new IllegalArgumentException("El email del propietario no es válido");
        }
        
        // Validar teléfono
        if (!mascota.esTelefonoValido()) {
            throw new IllegalArgumentException("El teléfono del propietario no es válido");
        }
        
        // Validar peso
        if (!mascota.esPesoValido()) {
            throw new IllegalArgumentException("El peso debe ser mayor a 0 kg");
        }
        
        return true;
    }
}
