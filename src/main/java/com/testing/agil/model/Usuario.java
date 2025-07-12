package com.testing.agil.model;

/**
 * Entidad Usuario para el sistema CRUD
 * Implementa los principios de Clean Code y SOLID
 */
public class Usuario {
    private Long id;
    private String nombre;
    private String email;
    private int edad;
    private boolean activo;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(String nombre, String email, int edad) {
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
        this.activo = true;
    }

    // Constructor completo
    public Usuario(Long id, String nombre, String email, int edad, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
        this.activo = activo;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // Métodos de negocio
    public boolean esEmailValido() {
        return email != null && email.contains("@") && email.contains(".");
    }

    public boolean esMayorDeEdad() {
        return edad >= 18;
    }

    // Métodos Object
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return id != null && id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", edad=" + edad +
                ", activo=" + activo +
                '}';
    }
}
