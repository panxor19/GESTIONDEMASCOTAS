CREATE TABLE IF NOT EXISTS usuarios (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    edad INTEGER NOT NULL CHECK (edad >= 0),
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices para mejorar rendimiento
CREATE INDEX IF NOT EXISTS idx_usuarios_email ON usuarios(email);
CREATE INDEX IF NOT EXISTS idx_usuarios_activo ON usuarios(activo);

-- Datos de prueba iniciales (opcional)
INSERT OR IGNORE INTO usuarios (nombre, email, edad, activo) VALUES
('Juan Pérez', 'juan.perez@email.com', 30, true),
('María García', 'maria.garcia@email.com', 25, true),
('Carlos López', 'carlos.lopez@email.com', 35, true),
('Ana Martínez', 'ana.martinez@email.com', 28, false);

-- Trigger para actualizar fecha_actualizacion automáticamente
CREATE TRIGGER IF NOT EXISTS update_usuarios_timestamp 
AFTER UPDATE ON usuarios
BEGIN
    UPDATE usuarios SET fecha_actualizacion = CURRENT_TIMESTAMP 
    WHERE id = NEW.id;
END;
