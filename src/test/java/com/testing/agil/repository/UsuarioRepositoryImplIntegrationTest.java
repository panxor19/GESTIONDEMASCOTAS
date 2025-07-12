package com.testing.agil.repository;

import com.testing.agil.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de integración para UsuarioRepositoryImpl
 * Prueba la interacción real con la base de datos SQLite
 * Utiliza base de datos temporal para aislamiento
 */
@DisplayName("Tests de integración del repositorio de usuarios")
class UsuarioRepositoryImplIntegrationTest {
    
    private UsuarioRepositoryImpl repository;
    
    @TempDir
    File tempDir;
    
    @BeforeEach
    void setUp() throws Exception {
        // Crear base de datos temporal para cada test
        String dbPath = tempDir.getAbsolutePath() + "/test-usuarios.db";
        System.setProperty("sqlite.db.path", dbPath);
        
        // Crear repositorio con base de datos temporal
        repository = new UsuarioRepositoryImpl();
        
        // Limpiar datos de pruebas anteriores
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM usuarios");
        }
    }
    
    @Test
    @DisplayName("Debería crear y recuperar usuario exitosamente")
    void should_CreateAndRetrieveUser_When_ValidDataProvided() {
        // Given
        Usuario usuario = new Usuario("Juan Pérez", "juan@email.com", 30);
        
        // When
        Usuario usuarioCreado = repository.crear(usuario);
        Optional<Usuario> usuarioRecuperado = repository.buscarPorId(usuarioCreado.getId());
        
        // Then
        assertAll(
            () -> assertNotNull(usuarioCreado.getId()),
            () -> assertTrue(usuarioRecuperado.isPresent()),
            () -> assertEquals(usuario.getNombre(), usuarioRecuperado.get().getNombre()),
            () -> assertEquals(usuario.getEmail(), usuarioRecuperado.get().getEmail()),
            () -> assertEquals(usuario.getEdad(), usuarioRecuperado.get().getEdad()),
            () -> assertTrue(usuarioRecuperado.get().isActivo())
        );
    }
    
    @Test
    @DisplayName("Debería manejar email duplicado correctamente")
    void should_ThrowException_When_DuplicateEmailProvided() {
        // Given
        Usuario usuario1 = new Usuario("Juan", "test@email.com", 30);
        Usuario usuario2 = new Usuario("Pedro", "test@email.com", 25);
        
        // When
        repository.crear(usuario1);
        
        // Then
        assertThrows(RuntimeException.class, () -> repository.crear(usuario2));
    }
    
    @Test
    @DisplayName("Debería listar usuarios correctamente")
    void should_ListUsers_When_UsersExist() {
        // Given
        Usuario usuario1 = new Usuario("Juan", "juan@email.com", 30);
        Usuario usuario2 = new Usuario("María", "maria@email.com", 25);
        
        repository.crear(usuario1);
        repository.crear(usuario2);
        
        // When
        List<Usuario> usuarios = repository.listarTodos();
        List<Usuario> usuariosActivos = repository.listarActivos();
        
        // Then
        assertAll(
            () -> assertEquals(2, usuarios.size()),
            () -> assertEquals(2, usuariosActivos.size()),
            () -> assertTrue(usuarios.stream().allMatch(u -> u.getId() != null))
        );
    }
    
    @Test
    @DisplayName("Debería actualizar usuario correctamente")
    void should_UpdateUser_When_ValidDataProvided() {
        // Given
        Usuario usuario = new Usuario("Juan", "juan@email.com", 30);
        Usuario usuarioCreado = repository.crear(usuario);
        
        // When
        usuarioCreado.setNombre("Juan Actualizado");
        usuarioCreado.setEdad(31);
        Usuario usuarioActualizado = repository.actualizar(usuarioCreado);
        
        // Then
        Optional<Usuario> usuarioRecuperado = repository.buscarPorId(usuarioCreado.getId());
        assertAll(
            () -> assertTrue(usuarioRecuperado.isPresent()),
            () -> assertEquals("Juan Actualizado", usuarioRecuperado.get().getNombre()),
            () -> assertEquals(31, usuarioRecuperado.get().getEdad())
        );
    }
    
    @Test
    @DisplayName("Debería eliminar usuario correctamente")
    void should_DeleteUser_When_UserExists() {
        // Given
        Usuario usuario = new Usuario("Juan", "juan@email.com", 30);
        Usuario usuarioCreado = repository.crear(usuario);
        
        // When
        boolean eliminado = repository.eliminar(usuarioCreado.getId());
        
        // Then
        assertAll(
            () -> assertTrue(eliminado),
            () -> assertTrue(repository.buscarPorId(usuarioCreado.getId()).isEmpty())
        );
    }
    
    @Test
    @DisplayName("Debería contar usuarios correctamente")
    void should_CountUsers_When_UsersExist() {
        // Given
        Usuario usuario1 = new Usuario("Juan", "juan@email.com", 30);
        Usuario usuario2 = new Usuario("María", "maria@email.com", 25);
        
        // When
        long countInicial = repository.contar();
        repository.crear(usuario1);
        repository.crear(usuario2);
        long countFinal = repository.contar();
        
        // Then
        assertAll(
            () -> assertEquals(0, countInicial),
            () -> assertEquals(2, countFinal)
        );
    }
    
    @Test
    @DisplayName("Debería buscar por email correctamente")
    void should_FindByEmail_When_EmailExists() {
        // Given
        Usuario usuario = new Usuario("Juan", "juan@email.com", 30);
        repository.crear(usuario);
        
        // When
        Optional<Usuario> encontrado = repository.buscarPorEmail("juan@email.com");
        Optional<Usuario> noEncontrado = repository.buscarPorEmail("noexiste@email.com");
        
        // Then
        assertAll(
            () -> assertTrue(encontrado.isPresent()),
            () -> assertEquals("Juan", encontrado.get().getNombre()),
            () -> assertTrue(noEncontrado.isEmpty())
        );
    }
    
    @Test
    @DisplayName("Debería verificar existencia de email correctamente")
    void should_CheckEmailExistence_When_EmailProvided() {
        // Given
        Usuario usuario = new Usuario("Juan", "juan@email.com", 30);
        repository.crear(usuario);
        
        // When & Then
        assertAll(
            () -> assertTrue(repository.existePorEmail("juan@email.com")),
            () -> assertFalse(repository.existePorEmail("noexiste@email.com")),
            () -> assertFalse(repository.existePorEmail(null)),
            () -> assertFalse(repository.existePorEmail(""))
        );
    }
}
