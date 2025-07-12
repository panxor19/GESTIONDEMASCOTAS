package com.testing.agil.service;

import com.testing.agil.model.Mascota;
import com.testing.agil.repository.MascotaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del Servicio de Mascotas - Estrategia 80%")
class MascotaServiceImplTest {

    @Mock
    private MascotaRepository repository;

    @InjectMocks
    private MascotaServiceImpl service;

    private Mascota mascotaCompleta;

    @BeforeEach
    void setUp() {
        mascotaCompleta = new Mascota();
        mascotaCompleta.setId(1L);
        mascotaCompleta.setNombre("Luna");
        mascotaCompleta.setEspecie("Perro");
        mascotaCompleta.setRaza("Golden Retriever");
        mascotaCompleta.setColor("Dorado");
        mascotaCompleta.setPeso(25.5);
        mascotaCompleta.setEsterilizado(true);
        mascotaCompleta.setPropietario("Juan Pérez");
        mascotaCompleta.setEmail("juan@test.com");
        mascotaCompleta.setTelefono("555-1234");
        mascotaCompleta.setFechaNacimiento(LocalDate.of(2020, 5, 15));
    }

    // TESTS DE REGISTRO - Cobertura de validaciones
    @Test
    @DisplayName("Debería registrar mascota con datos válidos")
    void should_RegisterMascota_When_ValidData() {
        // Given
        when(repository.buscarPorNombre("Luna")).thenReturn(Collections.emptyList());
        when(repository.crear(any(Mascota.class))).thenReturn(mascotaCompleta);

        // When
        Mascota result = service.registrarMascota(mascotaCompleta);

        // Then
        assertNotNull(result);
        assertEquals("Luna", result.getNombre());
        verify(repository).buscarPorNombre("Luna");
        verify(repository).crear(any(Mascota.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando mascota es null")
    void should_ThrowException_When_MascotaIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.registrarMascota(null);
        });
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando nombre es null")
    void should_ThrowException_When_NombreIsNull() {
        mascotaCompleta.setNombre(null);
        assertThrows(IllegalArgumentException.class, () -> {
            service.registrarMascota(mascotaCompleta);
        });
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando nombre está vacío")
    void should_ThrowException_When_NombreIsEmpty() {
        mascotaCompleta.setNombre("");
        assertThrows(IllegalArgumentException.class, () -> {
            service.registrarMascota(mascotaCompleta);
        });
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando especie es null")
    void should_ThrowException_When_EspecieIsNull() {
        mascotaCompleta.setEspecie(null);
        assertThrows(IllegalArgumentException.class, () -> {
            service.registrarMascota(mascotaCompleta);
        });
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando propietario es null")
    void should_ThrowException_When_PropietarioIsNull() {
        mascotaCompleta.setPropietario(null);
        assertThrows(IllegalArgumentException.class, () -> {
            service.registrarMascota(mascotaCompleta);
        });
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando email es null")
    void should_ThrowException_When_EmailIsNull() {
        mascotaCompleta.setEmail(null);
        assertThrows(IllegalArgumentException.class, () -> {
            service.registrarMascota(mascotaCompleta);
        });
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando fecha nacimiento es null")
    void should_ThrowException_When_FechaNacimientoIsNull() {
        mascotaCompleta.setFechaNacimiento(null);
        assertThrows(IllegalArgumentException.class, () -> {
            service.registrarMascota(mascotaCompleta);
        });
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando peso es negativo")
    void should_ThrowException_When_PesoIsNegative() {
        mascotaCompleta.setPeso(-5.0);
        assertThrows(IllegalArgumentException.class, () -> {
            service.registrarMascota(mascotaCompleta);
        });
    }

    // TESTS DE BÚSQUEDA
    @Test
    @DisplayName("Debería buscar mascota por ID existente")
    void should_FindMascota_When_IdExists() {
        when(repository.buscarPorId(1L)).thenReturn(Optional.of(mascotaCompleta));
        Optional<Mascota> result = service.buscarPorId(1L);
        assertTrue(result.isPresent());
        assertEquals("Luna", result.get().getNombre());
    }

    @Test
    @DisplayName("Debería retornar vacío cuando ID no existe")
    void should_ReturnEmpty_When_IdNotExists() {
        when(repository.buscarPorId(999L)).thenReturn(Optional.empty());
        Optional<Mascota> result = service.buscarPorId(999L);
        assertTrue(result.isEmpty());
    }

    // TESTS DE ACTUALIZACIÓN
    @Test
    @DisplayName("Debería actualizar mascota existente")
    void should_UpdateMascota_When_Exists() {
        when(repository.buscarPorId(1L)).thenReturn(Optional.of(mascotaCompleta));
        when(repository.actualizar(any(Mascota.class))).thenReturn(mascotaCompleta);
        
        Mascota result = service.actualizarMascota(mascotaCompleta);
        
        assertNotNull(result);
        verify(repository).actualizar(any(Mascota.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción al actualizar inexistente")
    void should_ThrowException_When_UpdatingNonExistent() {
        when(repository.buscarPorId(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> {
            service.actualizarMascota(mascotaCompleta);
        });
    }

    @Test
    @DisplayName("Debería lanzar excepción al actualizar null")
    void should_ThrowException_When_UpdatingNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.actualizarMascota(null);
        });
    }

    // TESTS DE ELIMINACIÓN
    @Test
    @DisplayName("Debería eliminar mascota existente")
    void should_DeleteMascota_When_Exists() {
        when(repository.eliminar(1L)).thenReturn(true);
        boolean result = service.eliminarMascota(1L);
        assertTrue(result);
    }

    @Test
    @DisplayName("Debería retornar false al eliminar inexistente")
    void should_ReturnFalse_When_DeletingNonExistent() {
        when(repository.eliminar(999L)).thenReturn(false);
        boolean result = service.eliminarMascota(999L);
        assertFalse(result);
    }

    // TESTS DE BÚSQUEDA POR ESPECIE
    @Test
    @DisplayName("Debería buscar por especie")
    void should_FindByEspecie_When_Exists() {
        List<Mascota> mascotas = Arrays.asList(mascotaCompleta);
        when(repository.buscarPorEspecie("Perro")).thenReturn(mascotas);
        
        List<Mascota> result = service.buscarPorEspecie("Perro");
        
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no hay especie")
    void should_ReturnEmpty_When_NoEspecie() {
        when(repository.buscarPorEspecie("Gato")).thenReturn(Collections.emptyList());
        
        List<Mascota> result = service.buscarPorEspecie("Gato");
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // TESTS DE BÚSQUEDA POR NOMBRE
    @Test
    @DisplayName("Debería buscar por nombre")
    void should_FindByNombre_When_Exists() {
        List<Mascota> mascotas = Arrays.asList(mascotaCompleta);
        when(repository.buscarPorNombre("Luna")).thenReturn(mascotas);
        
        List<Mascota> result = service.buscarPorNombre("Luna");
        
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no hay nombre")
    void should_ReturnEmpty_When_NoNombre() {
        when(repository.buscarPorNombre("Inexistente")).thenReturn(Collections.emptyList());
        
        List<Mascota> result = service.buscarPorNombre("Inexistente");
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
