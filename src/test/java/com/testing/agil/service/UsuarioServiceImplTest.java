package com.testing.agil.service;

import com.testing.agil.model.Usuario;
import com.testing.agil.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para UsuarioServiceImpl
 * Utiliza Mockito para aislar la lógica de negocio
 * Implementa principios de Testing Ágil y TDD
 */
@DisplayName("Tests del servicio de usuarios")
class UsuarioServiceImplTest {
    
    @Mock
    private UsuarioRepository usuarioRepository;
    
    @InjectMocks
    private UsuarioServiceImpl usuarioService;
    
    private Usuario usuarioValido;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioValido = new Usuario("Juan Pérez", "juan.perez@email.com", 30);
    }
    
    // CICLO TDD #9: Test crear usuario exitoso
    @Test
    @DisplayName("Debería crear usuario cuando datos son válidos")
    void should_CreateUser_When_ValidDataProvided() {
        // Given
        Usuario usuarioEsperado = new Usuario(1L, "Juan Pérez", "juan.perez@email.com", 30, true);
        when(usuarioRepository.existePorEmail(anyString())).thenReturn(false);
        when(usuarioRepository.crear(any(Usuario.class))).thenReturn(usuarioEsperado);
        
        // When
        Usuario resultado = usuarioService.crearUsuario(usuarioValido);
        
        // Then
        assertAll(
            () -> assertNotNull(resultado),
            () -> assertEquals(usuarioEsperado.getId(), resultado.getId()),
            () -> assertEquals(usuarioEsperado.getNombre(), resultado.getNombre()),
            () -> assertEquals(usuarioEsperado.getEmail(), resultado.getEmail()),
            () -> verify(usuarioRepository).existePorEmail("juan.perez@email.com"),
            () -> verify(usuarioRepository).crear(usuarioValido)
        );
    }
    
    // CICLO TDD #10: Test crear usuario con email duplicado
    @Test
    @DisplayName("Debería lanzar excepción cuando email ya existe")
    void should_ThrowException_When_EmailAlreadyExists() {
        // Given
        when(usuarioRepository.existePorEmail(anyString())).thenReturn(true);
        
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> usuarioService.crearUsuario(usuarioValido)
        );
        
        assertAll(
            () -> assertTrue(exception.getMessage().contains("Ya existe un usuario con el email")),
            () -> verify(usuarioRepository).existePorEmail("juan.perez@email.com"),
            () -> verify(usuarioRepository, never()).crear(any(Usuario.class))
        );
    }
    
    // CICLO TDD #11: Test validaciones de usuario nulo
    @Test
    @DisplayName("Debería lanzar excepción cuando usuario es null")
    void should_ThrowException_When_UserIsNull() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> usuarioService.crearUsuario(null)
        );
        
        assertEquals("El usuario no puede ser null", exception.getMessage());
        verify(usuarioRepository, never()).existePorEmail(anyString());
        verify(usuarioRepository, never()).crear(any(Usuario.class));
    }
    
    // CICLO TDD #12: Test validaciones de datos inválidos
    @Test
    @DisplayName("Debería lanzar excepción con datos inválidos")
    void should_ThrowException_When_InvalidDataProvided() {
        // Test nombre vacío
        assertAll(
            () -> {
                Usuario usuarioSinNombre = new Usuario("", "test@email.com", 25);
                IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> usuarioService.crearUsuario(usuarioSinNombre)
                );
                assertTrue(ex.getMessage().contains("nombre"));
            },
            () -> {
                Usuario usuarioSinEmail = new Usuario("Test", "", 25);
                IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> usuarioService.crearUsuario(usuarioSinEmail)
                );
                assertTrue(ex.getMessage().contains("email"));
            },
            () -> {
                Usuario usuarioEdadNegativa = new Usuario("Test", "test@email.com", -1);
                IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> usuarioService.crearUsuario(usuarioEdadNegativa)
                );
                assertTrue(ex.getMessage().contains("edad"));
            }
        );
    }
    
    @Test
    @DisplayName("Debería buscar usuario por ID exitosamente")
    void should_FindUser_When_ValidIdProvided() {
        // Given
        Long id = 1L;
        Usuario usuarioEsperado = new Usuario(id, "Juan", "juan@email.com", 30, true);
        when(usuarioRepository.buscarPorId(id)).thenReturn(Optional.of(usuarioEsperado));
        
        // When
        Optional<Usuario> resultado = usuarioService.buscarUsuarioPorId(id);
        
        // Then
        assertAll(
            () -> assertTrue(resultado.isPresent()),
            () -> assertEquals(usuarioEsperado, resultado.get()),
            () -> verify(usuarioRepository).buscarPorId(id)
        );
    }
    
    @Test
    @DisplayName("Debería retornar empty cuando ID es inválido")
    void should_ReturnEmpty_When_InvalidIdProvided() {
        assertAll(
            () -> {
                Optional<Usuario> resultado = usuarioService.buscarUsuarioPorId(null);
                assertTrue(resultado.isEmpty());
            },
            () -> {
                Optional<Usuario> resultado = usuarioService.buscarUsuarioPorId(0L);
                assertTrue(resultado.isEmpty());
            },
            () -> {
                Optional<Usuario> resultado = usuarioService.buscarUsuarioPorId(-1L);
                assertTrue(resultado.isEmpty());
            }
        );
        
        verify(usuarioRepository, never()).buscarPorId(any());
    }
    
    @Test
    @DisplayName("Debería listar todos los usuarios")
    void should_ReturnAllUsers_When_ListingUsers() {
        // Given
        List<Usuario> usuariosEsperados = Arrays.asList(
            new Usuario(1L, "Juan", "juan@email.com", 30, true),
            new Usuario(2L, "María", "maria@email.com", 25, false)
        );
        when(usuarioRepository.listarTodos()).thenReturn(usuariosEsperados);
        
        // When
        List<Usuario> resultado = usuarioService.listarTodosLosUsuarios();
        
        // Then
        assertAll(
            () -> assertNotNull(resultado),
            () -> assertEquals(2, resultado.size()),
            () -> assertEquals(usuariosEsperados, resultado),
            () -> verify(usuarioRepository).listarTodos()
        );
    }
    
    @Test
    @DisplayName("Debería listar solo usuarios activos")
    void should_ReturnActiveUsers_When_ListingActiveUsers() {
        // Given
        List<Usuario> usuariosActivos = Arrays.asList(
            new Usuario(1L, "Juan", "juan@email.com", 30, true),
            new Usuario(3L, "Carlos", "carlos@email.com", 35, true)
        );
        when(usuarioRepository.listarActivos()).thenReturn(usuariosActivos);
        
        // When
        List<Usuario> resultado = usuarioService.listarUsuariosActivos();
        
        // Then
        assertAll(
            () -> assertNotNull(resultado),
            () -> assertEquals(2, resultado.size()),
            () -> assertTrue(resultado.stream().allMatch(Usuario::isActivo)),
            () -> verify(usuarioRepository).listarActivos()
        );
    }
    
    @Test
    @DisplayName("Debería actualizar usuario exitosamente")
    void should_UpdateUser_When_ValidDataProvided() {
        // Given
        Long id = 1L;
        Usuario usuarioExistente = new Usuario(id, "Juan Viejo", "juan.viejo@email.com", 30, true);
        Usuario usuarioActualizado = new Usuario("Juan Nuevo", "juan.nuevo@email.com", 31);
        
        when(usuarioRepository.buscarPorId(id)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.buscarPorEmail("juan.nuevo@email.com")).thenReturn(Optional.empty());
        when(usuarioRepository.actualizar(any(Usuario.class))).thenReturn(usuarioActualizado);
        
        // When
        Usuario resultado = usuarioService.actualizarUsuario(id, usuarioActualizado);
        
        // Then
        assertAll(
            () -> assertNotNull(resultado),
            () -> assertEquals(id, usuarioActualizado.getId()),
            () -> verify(usuarioRepository).buscarPorId(id),
            () -> verify(usuarioRepository).actualizar(usuarioActualizado)
        );
    }
    
    @Test
    @DisplayName("Debería eliminar usuario lógicamente")
    void should_DeactivateUser_When_LogicalDelete() {
        // Given
        Long id = 1L;
        Usuario usuario = new Usuario(id, "Juan", "juan@email.com", 30, true);
        when(usuarioRepository.buscarPorId(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.actualizar(any(Usuario.class))).thenReturn(usuario);
        
        // When
        boolean resultado = usuarioService.eliminarUsuario(id);
        
        // Then
        assertAll(
            () -> assertTrue(resultado),
            () -> assertFalse(usuario.isActivo()),
            () -> verify(usuarioRepository).buscarPorId(id),
            () -> verify(usuarioRepository).actualizar(usuario)
        );
    }
    
    @Test
    @DisplayName("Debería eliminar usuario físicamente")
    void should_DeleteUser_When_PhysicalDelete() {
        // Given
        Long id = 1L;
        Usuario usuario = new Usuario(id, "Juan", "juan@email.com", 30, true);
        when(usuarioRepository.buscarPorId(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.eliminar(id)).thenReturn(true);
        
        // When
        boolean resultado = usuarioService.eliminarUsuarioFisicamente(id);
        
        // Then
        assertAll(
            () -> assertTrue(resultado),
            () -> verify(usuarioRepository).buscarPorId(id),
            () -> verify(usuarioRepository).eliminar(id)
        );
    }
    
    @Test
    @DisplayName("Debería contar usuarios correctamente")
    void should_CountUsers_When_Requested() {
        // Given
        long expectedCount = 5L;
        when(usuarioRepository.contar()).thenReturn(expectedCount);
        
        // When
        long resultado = usuarioService.contarUsuarios();
        
        // Then
        assertEquals(expectedCount, resultado);
        verify(usuarioRepository).contar();
    }
    
    @Test
    @DisplayName("Debería verificar disponibilidad de email")
    void should_CheckEmailAvailability_When_Requested() {
        // Given
        String emailDisponible = "disponible@email.com";
        String emailOcupado = "ocupado@email.com";
        
        when(usuarioRepository.existePorEmail(emailDisponible)).thenReturn(false);
        when(usuarioRepository.existePorEmail(emailOcupado)).thenReturn(true);
        
        // When & Then
        assertAll(
            () -> assertTrue(usuarioService.emailDisponible(emailDisponible)),
            () -> assertFalse(usuarioService.emailDisponible(emailOcupado)),
            () -> assertFalse(usuarioService.emailDisponible(null)),
            () -> assertFalse(usuarioService.emailDisponible(""))
        );
    }
}
