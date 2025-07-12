package com.testing.agil.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para la entidad Usuario
 * Sigue metodología TDD y principios de testing ágil
 */
@DisplayName("Tests de la entidad Usuario")
class UsuarioTest {
    
    // CICLO TDD #1: Test constructor básico
    @Test
    @DisplayName("Debería crear usuario con constructor básico")
    void should_CreateUser_When_UsingBasicConstructor() {
        // Given (Arrange)
        String nombre = "Juan Pérez";
        String email = "juan.perez@email.com";
        int edad = 30;
        
        // When (Act)
        Usuario usuario = new Usuario(nombre, email, edad);
        
        // Then (Assert)
        assertNotNull(usuario);
        assertEquals(nombre, usuario.getNombre());
        assertEquals(email, usuario.getEmail());
        assertEquals(edad, usuario.getEdad());
        assertTrue(usuario.isActivo(), "Usuario debería estar activo por defecto");
        assertNull(usuario.getId(), "ID debería ser null para usuario nuevo");
    }
    
    // CICLO TDD #2: Test constructor completo
    @Test
    @DisplayName("Debería crear usuario con constructor completo")
    void should_CreateUser_When_UsingFullConstructor() {
        // Given
        Long id = 1L;
        String nombre = "María García";
        String email = "maria.garcia@email.com";
        int edad = 25;
        boolean activo = false;
        
        // When
        Usuario usuario = new Usuario(id, nombre, email, edad, activo);
        
        // Then
        assertAll(
            () -> assertEquals(id, usuario.getId()),
            () -> assertEquals(nombre, usuario.getNombre()),
            () -> assertEquals(email, usuario.getEmail()),
            () -> assertEquals(edad, usuario.getEdad()),
            () -> assertEquals(activo, usuario.isActivo())
        );
    }
    
    // CICLO TDD #3: Test validación de email válido
    @Test
    @DisplayName("Debería validar email válido correctamente")
    void should_ReturnTrue_When_EmailIsValid() {
        // Given
        Usuario usuario = new Usuario("Test", "test@ejemplo.com", 25);
        
        // When
        boolean esValido = usuario.esEmailValido();
        
        // Then
        assertTrue(esValido, "Email válido debería retornar true");
    }
    
    // CICLO TDD #4: Test validación de email inválido
    @Test
    @DisplayName("Debería rechazar email inválido")
    void should_ReturnFalse_When_EmailIsInvalid() {
        // Given & When & Then
        assertAll(
            () -> {
                Usuario usuario1 = new Usuario("Test", "email_sin_arroba", 25);
                assertFalse(usuario1.esEmailValido(), "Email sin @ debería ser inválido");
            },
            () -> {
                Usuario usuario2 = new Usuario("Test", "email@sin_punto", 25);
                assertFalse(usuario2.esEmailValido(), "Email sin punto debería ser inválido");
            },
            () -> {
                Usuario usuario3 = new Usuario("Test", null, 25);
                assertFalse(usuario3.esEmailValido(), "Email null debería ser inválido");
            },
            () -> {
                Usuario usuario4 = new Usuario("Test", "", 25);
                assertFalse(usuario4.esEmailValido(), "Email vacío debería ser inválido");
            }
        );
    }
    
    // CICLO TDD #5: Test validación de mayoría de edad
    @Test
    @DisplayName("Debería validar mayoría de edad correctamente")
    void should_ReturnTrue_When_UserIsAdult() {
        // Given
        Usuario usuarioAdulto = new Usuario("Adulto", "adulto@email.com", 18);
        Usuario usuarioMayor = new Usuario("Mayor", "mayor@email.com", 30);
        
        // When & Then
        assertAll(
            () -> assertTrue(usuarioAdulto.esMayorDeEdad(), "Usuario de 18 años debería ser mayor de edad"),
            () -> assertTrue(usuarioMayor.esMayorDeEdad(), "Usuario de 30 años debería ser mayor de edad")
        );
    }
    
    @Test
    @DisplayName("Debería rechazar menor de edad")
    void should_ReturnFalse_When_UserIsMinor() {
        // Given
        Usuario menor = new Usuario("Menor", "menor@email.com", 17);
        Usuario bebe = new Usuario("Bebé", "bebe@email.com", 0);
        
        // When & Then
        assertAll(
            () -> assertFalse(menor.esMayorDeEdad(), "Usuario de 17 años debería ser menor de edad"),
            () -> assertFalse(bebe.esMayorDeEdad(), "Usuario de 0 años debería ser menor de edad")
        );
    }
    
    // CICLO TDD #6: Test método equals
    @Test
    @DisplayName("Debería comparar usuarios por ID correctamente")
    void should_CompareUsersById_When_UsingEquals() {
        // Given
        Usuario usuario1 = new Usuario(1L, "Juan", "juan@email.com", 30, true);
        Usuario usuario2 = new Usuario(1L, "Pedro", "pedro@email.com", 25, false);
        Usuario usuario3 = new Usuario(2L, "Juan", "juan@email.com", 30, true);
        
        // When & Then
        assertAll(
            () -> assertEquals(usuario1, usuario2, "Usuarios con mismo ID deberían ser iguales"),
            () -> assertNotEquals(usuario1, usuario3, "Usuarios con diferente ID deberían ser diferentes"),
            () -> assertEquals(usuario1, usuario1, "Usuario debería ser igual a sí mismo"),
            () -> assertNotEquals(usuario1, null, "Usuario no debería ser igual a null"),
            () -> assertNotEquals(usuario1, "string", "Usuario no debería ser igual a otro tipo de objeto")
        );
    }
    
    // CICLO TDD #7: Test método hashCode
    @Test
    @DisplayName("Debería generar hashCode consistente")
    void should_GenerateConsistentHashCode() {
        // Given
        Usuario usuario1 = new Usuario(1L, "Juan", "juan@email.com", 30, true);
        Usuario usuario2 = new Usuario(1L, "Pedro", "pedro@email.com", 25, false);
        Usuario usuarioSinId = new Usuario("Sin ID", "sinid@email.com", 20);
        
        // When & Then
        assertAll(
            () -> assertEquals(usuario1.hashCode(), usuario2.hashCode(), 
                "Usuarios con mismo ID deberían tener mismo hashCode"),
            () -> assertEquals(0, usuarioSinId.hashCode(), 
                "Usuario sin ID debería tener hashCode 0")
        );
    }
    
    // CICLO TDD #8: Test método toString
    @Test
    @DisplayName("Debería generar representación string correcta")
    void should_GenerateCorrectStringRepresentation() {
        // Given
        Usuario usuario = new Usuario(1L, "Juan Pérez", "juan@email.com", 30, true);
        
        // When
        String resultado = usuario.toString();
        
        // Then
        assertAll(
            () -> assertTrue(resultado.contains("Usuario{"), "Debería contener nombre de clase"),
            () -> assertTrue(resultado.contains("id=1"), "Debería contener ID"),
            () -> assertTrue(resultado.contains("nombre='Juan Pérez'"), "Debería contener nombre"),
            () -> assertTrue(resultado.contains("email='juan@email.com'"), "Debería contener email"),
            () -> assertTrue(resultado.contains("edad=30"), "Debería contener edad"),
            () -> assertTrue(resultado.contains("activo=true"), "Debería contener estado activo")
        );
    }
}
