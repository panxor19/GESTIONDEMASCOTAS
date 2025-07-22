package com.testing.agil.repository;

import com.testing.agil.model.Mascota;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
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
    private Mascota cachorroPrueba;

    @TempDir
    File tempDir;
    
    @BeforeEach
    void setUp() throws Exception {
        String dbPath = tempDir.getAbsolutePath() + "/test-mascotas.db";
        System.setProperty("sqlite.db.path", dbPath);

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

        cachorroPrueba = new Mascota(
            "Cachorro Test",
            "Perro",
            "Labrador",
            LocalDate.now().minusMonths(6),
            "Blanco",
            "Ana Gómez",
            "555-5678",
            "anagomez@email.com",
            3.2
        );
        
        // Limpiar datos de pruebas anteriores
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM mascotas");
        }
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
    @DisplayName("Debería listar todas las mascotas")
    void should_ListAllMascotas_When_Called() {
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
    @Order(10)
    @DisplayName("Debería retornar lista vacía al buscar por especie vacia")
    void should_ReturnEmptyList_When_SearchingByEmptySpecies() {
        // When
        List<Mascota> found = repository.buscarPorEspecie("");
        
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
    @Order(12)
    @DisplayName("Debería retornar lista vacía al buscar por nombre vacio")
    void should_ReturnEmptyList_When_SearchingByEmptyName() {
        // When
        List<Mascota> found = repository.buscarPorNombre("");
        
        // Then
        assertNotNull(found);
        assertTrue(found.isEmpty());
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
    @Order(13)
    @DisplayName("Debería retornar lista vacía al buscar por propietario vacio")
    void should_ReturnEmptyList_When_SearchingByEmptyOwner() {
        // When
        List<Mascota> found = repository.buscarPorPropietario("");
        // Then
        assertNotNull(found);
        assertTrue(found.isEmpty());
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
    @Order(17)
    @DisplayName("Debería retornar false para email es vacio")
    void should_ReturnFalse_When_EmailIsEmpty() {
        // Given
        repository.crear(mascotaPrueba);
        // When
        boolean exists = repository.existePorEmail("");
        // Then
        assertFalse(exists);
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
    @Order(20)
    @DisplayName("Deberia retornar 0 al contar especies nulas")
    void should_ReturnZero_When_CountingNullSpecies() {
        // When
        long count = repository.contarPorEspecie(null);
        // Then
        assertEquals(0, count);
    }

    @Test
    @Order(20)
    @DisplayName("Deberia retornar 0 al contar especies vacias")
    void should_ReturnZero_When_CountingEmptySpecies() {
        // When
        long count = repository.contarPorEspecie("");
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

    @Test
    @Order(22)
    @DisplayName("Debería listar todas las mascotas activas")
    void should_ListAllActiveMascotas_When_Called() {
        // Given
        Mascota created = repository.crear(mascotaPrueba);
        
        // When
        List<Mascota> mascotas = repository.listarActivas();
        
        // Then
        assertNotNull(mascotas);
        assertFalse(mascotas.isEmpty());
        // Verificar que existe la mascota que acabamos de crear
        assertTrue(mascotas.stream().allMatch(m -> m.getId().equals(created.getId())));
    }

    @Test
    @Order(23)
    @DisplayName("Deberia listar solo mascotas cachorros")
    void should_ListOnlyCachorros_When_Called() {
        // Given
        Mascota createdCachorro = repository.crear(cachorroPrueba);
        
        // When
        List<Mascota> cachorros = repository.listarCachorros();
        
        // Then
        assertNotNull(cachorros);
        assertEquals(1, cachorros.size());
        assertEquals(createdCachorro.getId(), cachorros.get(0).getId());
    }

    @Test
    @Order(24)
    @DisplayName("Deberia listar solo mascotas senior")
    void should_ListOnlySeniorMascotas_When_Called() {
        // Given
        Mascota seniorMascota = new Mascota(
            "Senior Test",
            "Perro",
            "Beagle",
            LocalDate.now().minusYears(8),
            "Marrón",
            "Carlos López",
            "555-7890",
            "",
            10.0
        );
        Mascota createdSenior = repository.crear(seniorMascota);

        // When
        List<Mascota> seniors = repository.listarSenior();

        // Then
        assertNotNull(seniors);
        assertEquals(1, seniors.size());
        assertEquals(createdSenior.getId(), seniors.get(0).getId());
    }

    @Test
    @Order(25)
    @DisplayName("Debería eliminar fisicamente una mascota")
    void should_PhysicallyDeleteMascota_When_IdExists() {
        // Given
        Mascota created = repository.crear(mascotaPrueba);
        
        // When
        boolean deleted = repository.eliminarFisicamente(created.getId());
        
        // Then
        assertTrue(deleted);
        
        // Verify physical deletion
        Optional<Mascota> found = repository.buscarPorId(created.getId());
        assertTrue(found.isEmpty());

        // Withour id deletion
        boolean deletedWithoutId = repository.eliminarFisicamente(null);
        assertFalse(deletedWithoutId);
    }

    @Test
    @Order(26)
    @DisplayName("Debería retornar false al eliminar fisicamente ID inexistente")
    void should_ReturnFalse_When_PhysicallyDeletingNonExistentId() {
        // When
        boolean deleted = repository.eliminarFisicamente(99999L);
        // Then
        assertFalse(deleted);
    }

    @Test
    @Order(27)
    @DisplayName("Debería esterilizar una mascota")
    void should_SterilizeMascota_When_IdExists() {
        // Given
        Mascota created = repository.crear(mascotaPrueba);
        
        // When
        boolean updated = repository.actualizarEsterilizacion(created.getId(), true);

        assertTrue(updated);

        // Then
        Optional<Mascota> found = repository.buscarPorId(created.getId());
        assertTrue(found.isPresent());
        assertTrue(found.get().isEsterilizado());
    }

    @Test
    @Order(28)
    @DisplayName("Debería retornar false al esterilizar ID inexistente")
    void should_ReturnFalse_When_SterilizingNonExistentId() {
        // When
        boolean updated = repository.actualizarEsterilizacion(99999L, true);
        // Then
        assertFalse(updated);
    }

    @Test
    @Order(29)
    @DisplayName("Debería retornar false al esterilizar ID nulo")
    void should_ReturnFalse_When_SterilizingNullId() {
        // When
        boolean updated = repository.actualizarEsterilizacion(null, true);
        // Then
        assertFalse(updated);
    }

    @Test
    @Order(30)
    @DisplayName("Debería actualizar el peso de una mascota")
    void should_UpdateWeight_When_IdExists() {
        // Given
        Mascota created = repository.crear(mascotaPrueba);
        
        // When
        boolean updated = repository.actualizarPeso(created.getId(), 30.0);
        
        // Then
        assertTrue(updated);
        
        Optional<Mascota> found = repository.buscarPorId(created.getId());
        assertTrue(found.isPresent());
        assertEquals(30.0, found.get().getPeso());
    }

    @Test
    @Order(31)
    @DisplayName("Debería retornar false al actualizar peso de ID inexistente")
    void should_ReturnFalse_When_UpdatingWeightOfNonExistentId() {
        // When
        boolean updated = repository.actualizarPeso(99999L, 30.0);
        // Then
        assertFalse(updated);
    }

    @Test
    @Order(32)
    @DisplayName("Debería retornar false al actualizar peso de ID nulo")
    void should_ReturnFalse_When_UpdatingWeightOfNullId() {
        // When
        boolean updated = repository.actualizarPeso(null, 30.0);
        // Then
        assertFalse(updated);
    }

    @Test
    @Order(33)
    @DisplayName("Debería retornar false al actualizar peso con valor negativo")
    void should_ReturnFalse_When_UpdatingWeightWithNegativeValue() {
        // Given
        Mascota created = repository.crear(mascotaPrueba);
        
        // When
        boolean updated = repository.actualizarPeso(created.getId(), -5.0);
        
        // Then
        assertFalse(updated);
        
        Optional<Mascota> found = repository.buscarPorId(created.getId());
        assertTrue(found.isPresent());
        assertNotEquals(-5.0, found.get().getPeso());
    }

    @Test
    @Order(34)
    @DisplayName("Debería retornar false al eliminar mascota con ID nulo")
    void should_ReturnFalse_When_DeletingWithNullId() {
        // When
        boolean deleted = repository.eliminar(null);
        
        // Then
        assertFalse(deleted);
        
        // Verify no changes in the database
        List<Mascota> mascotas = repository.listarTodas();
        assertTrue(mascotas.isEmpty());
    }

    @Test
    @Order(35)
    @DisplayName("Debería lanzar excepción al crear mascota con datos inválidos")
    void should_ThrowException_When_CreatingMascotaWithInvalidData() {
        // Given
        Mascota invalidMascota = null;
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            repository.crear(invalidMascota);
        }, "No debería permitir crear mascota con datos nulos");
    }
}
