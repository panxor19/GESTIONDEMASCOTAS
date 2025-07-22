package com.testing.agil.repository;

import com.testing.agil.model.Mascota;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación concreta del repositorio de mascotas usando SQLite
 * Aplica principio DIP (Dependency Inversion Principle) - depende de la abstracción
 * Aplica principio SRP (Single Responsibility Principle) - solo maneja persistencia
 */
public class MascotaRepositoryImpl implements MascotaRepository {
    
    private final String dbUrl;
    private static final String CREATE_TABLE_SQL = """
        CREATE TABLE IF NOT EXISTS mascotas (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre VARCHAR(50) NOT NULL,
            especie VARCHAR(20) NOT NULL,
            raza VARCHAR(50),
            fecha_nacimiento DATE,
            color VARCHAR(30),
            propietario VARCHAR(100) NOT NULL,
            telefono VARCHAR(20),
            email VARCHAR(150),
            peso DECIMAL(5,2) CHECK (peso > 0),
            esterilizado BOOLEAN DEFAULT FALSE,
            activo BOOLEAN DEFAULT TRUE,
            fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )
        """;
    
    public MascotaRepositoryImpl() {
        // Permitir configurar la ruta de BD para tests
        String customPath = System.getProperty("sqlite.db.path");
        this.dbUrl = customPath != null ? "jdbc:sqlite:" + customPath : "jdbc:sqlite:mascotas.db";
        initializeDatabase();
    }
    
    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException("Error al inicializar la base de datos", e);
        }
    }
    
    @Override
    public Mascota crear(Mascota mascota) {
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota no puede ser null");
        }
        
        String sql = """
            INSERT INTO mascotas (nombre, especie, raza, fecha_nacimiento, color, 
                                propietario, telefono, email, peso, esterilizado, activo) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, mascota.getNombre());
            pstmt.setString(2, mascota.getEspecie());
            pstmt.setString(3, mascota.getRaza());
            pstmt.setDate(4, mascota.getFechaNacimiento() != null ? 
                         Date.valueOf(mascota.getFechaNacimiento()) : null);
            pstmt.setString(5, mascota.getColor());
            pstmt.setString(6, mascota.getPropietario());
            pstmt.setString(7, mascota.getTelefono());
            pstmt.setString(8, mascota.getEmail());
            pstmt.setDouble(9, mascota.getPeso());
            pstmt.setBoolean(10, mascota.isEsterilizado());
            pstmt.setBoolean(11, mascota.isActivo());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Error al crear mascota, no se insertaron filas");
            }
            
            // Obtener el ID usando last_insert_rowid() que es específico de SQLite
            try (PreparedStatement getIdStmt = conn.prepareStatement("SELECT last_insert_rowid()");
                 ResultSet rs = getIdStmt.executeQuery()) {
                if (rs.next()) {
                    mascota.setId(rs.getLong(1));
                    return mascota;
                } else {
                    throw new SQLException("Error al crear mascota, no se obtuvo ID");
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear mascota: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Optional<Mascota> buscarPorId(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        
        String sql = "SELECT * FROM mascotas WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToMascota(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar mascota por ID: " + e.getMessage(), e);
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Mascota> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE LOWER(nombre) LIKE LOWER(?) ORDER BY nombre";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + nombre.trim() + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                mascotas.add(mapResultSetToMascota(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar mascotas por nombre: " + e.getMessage(), e);
        }
        
        return mascotas;
    }
    
    @Override
    public List<Mascota> buscarPorEspecie(String especie) {
        if (especie == null || especie.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE LOWER(especie) = LOWER(?) ORDER BY nombre";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, especie.trim());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                mascotas.add(mapResultSetToMascota(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar mascotas por especie: " + e.getMessage(), e);
        }
        
        return mascotas;
    }
    
    @Override
    public List<Mascota> buscarPorPropietario(String propietario) {
        if (propietario == null || propietario.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE LOWER(propietario) LIKE LOWER(?) ORDER BY nombre";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + propietario.trim() + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                mascotas.add(mapResultSetToMascota(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar mascotas por propietario: " + e.getMessage(), e);
        }
        
        return mascotas;
    }
    
    @Override
    public List<Mascota> buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE LOWER(email) = LOWER(?) ORDER BY nombre";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email.trim());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                mascotas.add(mapResultSetToMascota(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar mascotas por email: " + e.getMessage(), e);
        }
        
        return mascotas;
    }
    
    @Override
    public List<Mascota> listarTodas() {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas ORDER BY nombre";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                mascotas.add(mapResultSetToMascota(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar mascotas: " + e.getMessage(), e);
        }
        
        return mascotas;
    }
    
    @Override
    public List<Mascota> listarActivas() {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE activo = TRUE ORDER BY nombre";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                mascotas.add(mapResultSetToMascota(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar mascotas activas: " + e.getMessage(), e);
        }
        
        return mascotas;
    }
    
    @Override
    public List<Mascota> listarCachorros() {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE fecha_nacimiento > ? AND activo = TRUE ORDER BY nombre";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, Date.valueOf(LocalDate.now().minusYears(1)));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                mascotas.add(mapResultSetToMascota(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar cachorros: " + e.getMessage(), e);
        }
        
        return mascotas;
    }
    
    @Override
    public List<Mascota> listarSenior() {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE fecha_nacimiento <= ? AND activo = TRUE ORDER BY nombre";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, Date.valueOf(LocalDate.now().minusYears(7)));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                mascotas.add(mapResultSetToMascota(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar mascotas senior: " + e.getMessage(), e);
        }
        
        return mascotas;
    }
    
    @Override
    public Mascota actualizar(Mascota mascota) {
        if (mascota == null || mascota.getId() == null) {
            throw new IllegalArgumentException("La mascota y su ID no pueden ser null");
        }
        
        String sql = """
            UPDATE mascotas SET nombre = ?, especie = ?, raza = ?, fecha_nacimiento = ?, 
                              color = ?, propietario = ?, telefono = ?, email = ?, peso = ?, 
                              esterilizado = ?, fecha_actualizacion = CURRENT_TIMESTAMP 
            WHERE id = ?
            """;
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, mascota.getNombre());
            pstmt.setString(2, mascota.getEspecie());
            pstmt.setString(3, mascota.getRaza());
            pstmt.setDate(4, mascota.getFechaNacimiento() != null ? 
                         Date.valueOf(mascota.getFechaNacimiento()) : null);
            pstmt.setString(5, mascota.getColor());
            pstmt.setString(6, mascota.getPropietario());
            pstmt.setString(7, mascota.getTelefono());
            pstmt.setString(8, mascota.getEmail());
            pstmt.setDouble(9, mascota.getPeso());
            pstmt.setBoolean(10, mascota.isEsterilizado());
            pstmt.setLong(11, mascota.getId());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Mascota no encontrada con ID: " + mascota.getId());
            }
            
            return mascota;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar mascota: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean eliminar(Long id) {
        if (id == null) {
            return false;
        }
        
        String sql = "UPDATE mascotas SET activo = FALSE, fecha_actualizacion = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar mascota: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean eliminarFisicamente(Long id) {
        if (id == null) {
            return false;
        }
        
        String sql = "DELETE FROM mascotas WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar físicamente mascota: " + e.getMessage(), e);
        }
    }
    
    @Override
    public long contar() {
        String sql = "SELECT COUNT(*) FROM mascotas";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getLong(1);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar mascotas: " + e.getMessage(), e);
        }
        
        return 0;
    }
    
    @Override
    public long contarActivas() {
        String sql = "SELECT COUNT(*) FROM mascotas WHERE activo = TRUE";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getLong(1);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar mascotas activas: " + e.getMessage(), e);
        }
        
        return 0;
    }
    
    @Override
    public long contarPorEspecie(String especie) {
        if (especie == null || especie.trim().isEmpty()) {
            return 0;
        }
        
        String sql = "SELECT COUNT(*) FROM mascotas WHERE LOWER(especie) = LOWER(?) AND activo = TRUE";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, especie.trim());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getLong(1);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar mascotas por especie: " + e.getMessage(), e);
        }
        
        return 0;
    }
    
    @Override
    public boolean existePorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        String sql = "SELECT COUNT(*) FROM mascotas WHERE LOWER(email) = LOWER(?)";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email.trim());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getLong(1) > 0;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar email: " + e.getMessage(), e);
        }
        
        return false;
    }
    
    @Override
    public boolean actualizarEsterilizacion(Long id, boolean esterilizado) {
        if (id == null) {
            return false;
        }
        
        String sql = "UPDATE mascotas SET esterilizado = ?, fecha_actualizacion = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setBoolean(1, esterilizado);
            pstmt.setLong(2, id);
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar esterilización: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean actualizarPeso(Long id, double peso) {
        if (id == null || peso <= 0) {
            return false;
        }
        
        String sql = "UPDATE mascotas SET peso = ?, fecha_actualizacion = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, peso);
            pstmt.setLong(2, id);
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar peso: " + e.getMessage(), e);
        }
    }
    
    /**
     * Método auxiliar para mapear ResultSet a objeto Mascota
     * Aplica principio DRY (Don't Repeat Yourself)
     */
    private Mascota mapResultSetToMascota(ResultSet rs) throws SQLException {
        LocalDate fechaNacimiento = null;
        Date sqlDate = rs.getDate("fecha_nacimiento");
        if (sqlDate != null) {
            fechaNacimiento = sqlDate.toLocalDate();
        }
        
        return new Mascota(
            rs.getLong("id"),
            rs.getString("nombre"),
            rs.getString("especie"),
            rs.getString("raza"),
            fechaNacimiento,
            rs.getString("color"),
            rs.getString("propietario"),
            rs.getString("telefono"),
            rs.getString("email"),
            rs.getDouble("peso"),
            rs.getBoolean("esterilizado"),
            rs.getBoolean("activo")
        );
    }
}