package com.testing.agil.repository;

import com.testing.agil.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación concreta del repositorio de usuarios usando SQLite
 * Aplica principio DIP (Dependency Inversion Principle) - depende de la abstracción
 * Aplica principio SRP (Single Responsibility Principle) - solo maneja persistencia
 */
public class UsuarioRepositoryImpl implements UsuarioRepository {
    
    private final String dbUrl;
    private static final String CREATE_TABLE_SQL = """
        CREATE TABLE IF NOT EXISTS usuarios (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre VARCHAR(100) NOT NULL,
            email VARCHAR(150) UNIQUE NOT NULL,
            edad INTEGER NOT NULL CHECK (edad >= 0),
            activo BOOLEAN DEFAULT TRUE,
            fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )
        """;
    
    public UsuarioRepositoryImpl() {
        // Permitir configurar la ruta de BD para tests
        String customPath = System.getProperty("sqlite.db.path");
        this.dbUrl = customPath != null ? "jdbc:sqlite:" + customPath : "jdbc:sqlite:usuarios.db";
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
    public Usuario crear(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser null");
        }
        
        String sql = "INSERT INTO usuarios (nombre, email, edad, activo) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setInt(3, usuario.getEdad());
            pstmt.setBoolean(4, usuario.isActivo());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Error al crear usuario, no se insertaron filas");
            }
            
            // Obtener el ID usando last_insert_rowid() que es específico de SQLite
            try (PreparedStatement getIdStmt = conn.prepareStatement("SELECT last_insert_rowid()");
                 ResultSet rs = getIdStmt.executeQuery()) {
                if (rs.next()) {
                    usuario.setId(rs.getLong(1));
                    return usuario;
                } else {
                    throw new SQLException("Error al crear usuario, no se obtuvo ID");
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear usuario: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToUsuario(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por ID: " + e.getMessage(), e);
        }
        
        return Optional.empty();
    }
    
    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Optional.empty();
        }
        
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToUsuario(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por email: " + e.getMessage(), e);
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY id";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                usuarios.add(mapResultSetToUsuario(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar usuarios: " + e.getMessage(), e);
        }
        
        return usuarios;
    }
    
    @Override
    public List<Usuario> listarActivos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE activo = TRUE ORDER BY id";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                usuarios.add(mapResultSetToUsuario(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar usuarios activos: " + e.getMessage(), e);
        }
        
        return usuarios;
    }
    
    @Override
    public Usuario actualizar(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            throw new IllegalArgumentException("El usuario y su ID no pueden ser null");
        }
        
        String sql = "UPDATE usuarios SET nombre = ?, email = ?, edad = ?, fecha_actualizacion = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setInt(3, usuario.getEdad());
            pstmt.setLong(4, usuario.getId());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Usuario no encontrado con ID: " + usuario.getId());
            }
            
            return usuario;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean eliminar(Long id) {
        if (id == null) {
            return false;
        }
        
        String sql = "DELETE FROM usuarios WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }
    
    @Override
    public long contar() {
        String sql = "SELECT COUNT(*) FROM usuarios";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getLong(1);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar usuarios: " + e.getMessage(), e);
        }
        
        return 0;
    }
    
    @Override
    public boolean existePorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getLong(1) > 0;
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar email: " + e.getMessage(), e);
        }
        
        return false;
    }
    
    /**
     * Método auxiliar para mapear ResultSet a objeto Usuario
     * Aplica principio DRY (Don't Repeat Yourself)
     */
    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
            rs.getLong("id"),
            rs.getString("nombre"),
            rs.getString("email"),
            rs.getInt("edad"),
            rs.getBoolean("activo")
        );
    }
}
