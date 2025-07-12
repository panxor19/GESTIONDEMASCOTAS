package com.testing.agil.repository;

import com.testing.agil.model.Mascota;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de integración para MascotaRepositoryImpl.
 * Prueba las operaciones CRUD básicas contra base de datos SQLite.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MascotaRepositoryImplIntegrationTest {
    
    private MascotaRepositoryImpl repository;
    private Mascota mascotaPrueba;
    
    @BeforeEach
    void setUp() {
        repository = new MascotaRepositoryImpl();
        mascotaPrueba = new Mascota(
            "Firulais Test", 
            "Perro", 
            "Golden Retriever",
            LocalDate.of(2020, 5, 15),
            "Dorado",
            "Juan Pérez",
            "555-1234",
            "juan@email.com",
            25.5
        );
    }
    
    @Test
    @Order(1)
    @DisplayName("Debería crear mascota en base de datos")
    void should_CreateMascota_When_DataIsValid() {
        // When
        Mascota created = repository.crear(mascotaPrueba);
        
        // Then
        assertNotNull(created);
        assertNotNull(created.getId());
        assertTrue(created.getId() > 0);
        assertEquals("Firulais Test", created.getNombre());
    }
    
    @Test
    @Order(2)
    @DisplayName("Debería buscar mascota por ID")
    void should_FindMascota_When_IdExists() {
        // Given
        Mascota created = repository.crear(mascotaPrueba);
        
        // When
        Optional<Mascota> found = repository.buscarPorId(created.getId());
        
        // Then
        assertTrue(found.isPresent());
        assertEquals(created.getId(), found.get().getId());
    }
    
    @Test
    @Order(3)
    @DisplayName("Debería actualizar mascota existente")
    void should_UpdateMascota_When_DataIsValid() {
        // Given
        Mascota created = repository.crear(mascotaPrueba);
        created.setNombre("Firulais Actualizado");
        
        // When
        Mascota updated = repository.actualizar(created);
        
        // Then
        assertNotNull(updated);
        assertEquals("Firulais Actualizado", updated.getNombre());
    }
    
    @Test
    @Order(4)
    @DisplayName("Debería eliminar mascota lógicamente")
    void should_DeactivateMascota_When_IdExists() {
        // Given
        Mascota created = repository.crear(mascotaPrueba);
        
        // When
        boolean deleted = repository.eliminar(created.getId());
        
        // Then
        assertTrue(deleted);
        
        // Verify logical deletion
        Optional<Mascota> found = repository.buscarPorId(created.getId());
        assertTrue(found.isPresent());
        assertFalse(found.get().isActivo());
    }
    
    @Test
    @Order(5)
    @DisplayName("Debería retornar empty cuando busca ID null")
    void should_ReturnEmpty_When_SearchingWithNullId() {
        // When
        Optional<Mascota> found = repository.buscarPorId(null);
        
        // Then
        assertTrue(found.isEmpty());
    }
    
    @Test
    @Order(6)
    @DisplayName("Debería retornar empty cuando busca ID inexistente")
    void should_ReturnEmpty_When_SearchingNonExistentId() {
        // When
        Optional<Mascota> found = repository.buscarPorId(99999L);
        
        // Then
        assertTrue(found.isEmpty());
    }
    
    @Test
    @Order(7)
    @DisplayName("Debería retornar false al eliminar ID inexistente")
    void should_ReturnFalse_When_DeletingNonExistentId() {
        // When
        boolean deleted = repository.eliminar(99999L);
        
        // Then
        assertFalse(deleted);
    }
    
    @Test
    @Order(8)
    @DisplayName("Debería listar todas las mascotas activas")
    void should_ListAllActiveMascotas_When_Called() {
        // Given
        Mascota created = repository.crear(mascotaPrueba);
        
        // When
        List<Mascota> mascotas = repository.listarTodas();
        
        // Then
        assertNotNull(mascotas);
        assertFalse(mascotas.isEmpty());
        // Verificar que al menos existe la mascota que acabamos de crear
        assertTrue(mascotas.stream().anyMatch(m -> m.getId().equals(created.getId())));
    }
    
    @Test
    @Order(9)
    @DisplayName("Debería buscar mascotas por especie")
    void should_FindMascotasByEspecie_When_SpeciesExists() {
        // Given
        repository.crear(mascotaPrueba);
        
        // When
        List<Mascota> perros = repository.buscarPorEspecie("Perro");
        
        // Then
        assertNotNull(perros);
        assertFalse(perros.isEmpty());
        assertTrue(perros.stream().allMatch(m -> "Perro".equals(m.getEspecie())));
    }
    
    @Test
    @Order(10)
    @DisplayName("Debería retornar lista vacía para especie inexistente")
    void should_ReturnEmptyList_When_SpeciesDoesNotExist() {
        // When
        List<Mascota> found = repository.buscarPorEspecie("EspecieInexistente");
        
        // Then
        assertNotNull(found);
        assertTrue(found.isEmpty());
    }
    
    @Test
    @Order(11)
    @DisplayName("Debería contar total de mascotas")
    void should_CountTotalMascotas_When_Called() {
        // Given
        repository.crear(mascotaPrueba);
        
        // When
        long count = repository.contar();
        
        // Then
        assertTrue(count >= 1);
    }
    
    @Test
    @Order(12)
    @DisplayName("Debería buscar mascotas por nombre")
    void should_FindMascotasByNombre_When_NameMatches() {
        // Given
        repository.crear(mascotaPrueba);
        
        // When
        List<Mascota> found = repository.buscarPorNombre("Firulais");
        
        // Then
        assertNotNull(found);
        assertFalse(found.isEmpty());
        assertTrue(found.stream().anyMatch(m -> m.getNombre().contains("Firulais")));
    }
    
    @Test
    @Order(13)
    @DisplayName("Debería buscar mascotas por propietario")
    void should_FindMascotasByPropietario_When_OwnerExists() {
        // Given
        repository.crear(mascotaPrueba);
        
        // When
        List<Mascota> found = repository.buscarPorPropietario("Juan Pérez");
        
        // Then
        assertNotNull(found);
        assertFalse(found.isEmpty());
        assertTrue(found.stream().anyMatch(m -> "Juan Pérez".equals(m.getPropietario())));
    }
    
    @Test
    @Order(14)
    @DisplayName("Debería buscar mascotas por email")
    void should_FindMascotasByEmail_When_EmailExists() {
        // Given
        repository.crear(mascotaPrueba);
        
        // When
        List<Mascota> found = repository.buscarPorEmail("juan@email.com");
        
        // Then
        assertNotNull(found);
        assertFalse(found.isEmpty());
        assertTrue(found.stream().anyMatch(m -> "juan@email.com".equals(m.getEmail())));
    }
    
    @Test
    @Order(15)
    @DisplayName("Debería contar mascotas activas")
    void should_CountActiveMascotas_When_Called() {
        // Given
        repository.crear(mascotaPrueba);
        
        // When
        long count = repository.contarActivas();
        
        // Then
        assertTrue(count >= 1);
    }
    
    @Test
    @Order(16)
    @DisplayName("Debería contar mascotas por especie")
    void should_CountMascotasByEspecie_When_SpeciesExists() {
        // Given
        repository.crear(mascotaPrueba);
        
        // When
        long count = repository.contarPorEspecie("Perro");
        
        // Then
        assertTrue(count >= 1);
    }
    
    @Test
    @Order(17)
    @DisplayName("Debería verificar si existe email")
    void should_VerifyEmailExists_When_EmailIsRegistered() {
        // Given
        repository.crear(mascotaPrueba);
        
        // When
        boolean exists = repository.existePorEmail("juan@email.com");
        
        // Then
        assertTrue(exists);
    }
    
    @Test
    @Order(18)
    @DisplayName("Debería retornar false para email inexistente")
    void should_ReturnFalse_When_EmailDoesNotExist() {
        // When
        boolean exists = repository.existePorEmail("inexistente@email.com");
        
        // Then
        assertFalse(exists);
    }
    
    @Test
    @Order(19)
    @DisplayName("Debería manejar búsqueda con parámetros nulos")
    void should_HandleNullParameters_When_Searching() {
        // When & Then - Verificar que no lanza excepciones
        assertDoesNotThrow(() -> {
            List<Mascota> result1 = repository.buscarPorNombre(null);
            List<Mascota> result2 = repository.buscarPorEspecie(null);
            List<Mascota> result3 = repository.buscarPorPropietario(null);
            List<Mascota> result4 = repository.buscarPorEmail(null);
            
            assertNotNull(result1);
            assertNotNull(result2);
            assertNotNull(result3);
            assertNotNull(result4);
        });
    }
    
    @Test
    @Order(20)
    @DisplayName("Debería retornar 0 al contar especies inexistentes")
    void should_ReturnZero_When_CountingNonExistentSpecies() {
        // When
        long count = repository.contarPorEspecie("EspecieInexistente");
        
        // Then
        assertEquals(0, count);
    }
    
    @Test
    @Order(21)
    @DisplayName("Debería manejar actualización con datos válidos completos")
    void should_HandleCompleteUpdate_When_AllFieldsProvided() {
        // Given
        Mascota created = repository.crear(mascotaPrueba);
        created.setNombre("Nuevo Nombre");
        created.setEspecie("Gato");
        created.setRaza("Persa");
        created.setColor("Blanco");
        created.setPeso(4.5);
        created.setEsterilizado(true);
        
        // When
        Mascota updated = repository.actualizar(created);
        
        // Then
        assertNotNull(updated);
        assertEquals("Nuevo Nombre", updated.getNombre());
        assertEquals("Gato", updated.getEspecie());
        assertEquals("Persa", updated.getRaza());
        assertEquals("Blanco", updated.getColor());
        assertEquals(4.5, updated.getPeso());
        assertTrue(updated.isEsterilizado());
    }
}
