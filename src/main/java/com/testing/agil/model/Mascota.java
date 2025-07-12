package com.testing.agil.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Entidad que representa una mascota en el sistema
 * Aplica principios de Clean Code y encapsulamiento
 * Incluye validaciones de negocio integradas
 */
public class Mascota {
    
    private Long id;
    private String nombre;
    private String especie; // Perro, Gato, Ave, etc.
    private String raza;
    private LocalDate fechaNacimiento;
    private String color;
    private String propietario;
    private String telefono;
    private String email;
    private double peso; // en kilogramos
    private boolean esterilizado;
    private boolean activo;
    
    // Constructor vacío para frameworks
    public Mascota() {
        this.activo = true;
        this.esterilizado = false;
    }
    
    // Constructor para nuevas mascotas (sin ID)
    public Mascota(String nombre, String especie, String raza, LocalDate fechaNacimiento, 
                   String color, String propietario, String telefono, String email, double peso) {
        this();
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.color = color;
        this.propietario = propietario;
        this.telefono = telefono;
        this.email = email;
        this.peso = peso;
    }
    
    // Constructor completo (con ID para recuperación de BD)
    public Mascota(Long id, String nombre, String especie, String raza, LocalDate fechaNacimiento, 
                   String color, String propietario, String telefono, String email, double peso,
                   boolean esterilizado, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.color = color;
        this.propietario = propietario;
        this.telefono = telefono;
        this.email = email;
        this.peso = peso;
        this.esterilizado = esterilizado;
        this.activo = activo;
    }
    
    // Métodos de validación de negocio
    
    /**
     * Valida si el nombre es válido
     * @return true si el nombre es válido
     */
    public boolean esNombreValido() {
        return nombre != null && 
               !nombre.trim().isEmpty() && 
               nombre.trim().length() >= 2 && 
               nombre.trim().length() <= 50 &&
               nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    }
    
    /**
     * Valida si la especie está dentro de las permitidas
     * @return true si la especie es válida
     */
    public boolean esEspecieValida() {
        if (especie == null || especie.trim().isEmpty()) {
            return false;
        }
        
        String especieNormalizada = especie.trim().toLowerCase();
        return especieNormalizada.equals("perro") || 
               especieNormalizada.equals("gato") || 
               especieNormalizada.equals("ave") || 
               especieNormalizada.equals("conejo") ||
               especieNormalizada.equals("hamster") ||
               especieNormalizada.equals("pez") ||
               especieNormalizada.equals("reptil");
    }
    
    /**
     * Valida si el email del propietario es válido
     * @return true si el email es válido
     */
    public boolean esEmailValido() {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailPattern);
    }
    
    /**
     * Valida si el teléfono es válido
     * @return true si el teléfono es válido
     */
    public boolean esTelefonoValido() {
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }
        
        // Acepta formatos: +57 300 123 4567, 300-123-4567, 3001234567, etc.
        String telefonoLimpio = telefono.replaceAll("[\\s-()]", "");
        return telefonoLimpio.matches("^\\+?[0-9]{7,15}$");
    }
    
    /**
     * Valida si el peso es válido
     * @return true si el peso es válido
     */
    public boolean esPesoValido() {
        return peso > 0 && peso <= 200; // Entre 1 gramo y 200 kg
    }
    
    /**
     * Calcula la edad aproximada de la mascota en años
     * @return edad en años
     */
    public int calcularEdadEnAnios() {
        if (fechaNacimiento == null) {
            return 0;
        }
        
        LocalDate hoy = LocalDate.now();
        return hoy.getYear() - fechaNacimiento.getYear();
    }
    
    /**
     * Determina si la mascota es cachorro/cría (menor a 1 año)
     * @return true si es cachorro
     */
    public boolean esCachorro() {
        return calcularEdadEnAnios() < 1;
    }
    
    /**
     * Determina si la mascota es senior (mayor a 7 años para perros/gatos)
     * @return true si es senior
     */
    public boolean esSenior() {
        int edad = calcularEdadEnAnios();
        if (especie != null) {
            String especieNormalizada = especie.toLowerCase();
            if (especieNormalizada.equals("perro") || especieNormalizada.equals("gato")) {
                return edad >= 7;
            }
        }
        return edad >= 5; // Para otras especies
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
    
    public String getEspecie() {
        return especie;
    }
    
    public void setEspecie(String especie) {
        this.especie = especie;
    }
    
    public String getRaza() {
        return raza;
    }
    
    public void setRaza(String raza) {
        this.raza = raza;
    }
    
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public String getPropietario() {
        return propietario;
    }
    
    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public double getPeso() {
        return peso;
    }
    
    public void setPeso(double peso) {
        this.peso = peso;
    }
    
    public boolean isEsterilizado() {
        return esterilizado;
    }
    
    public void setEsterilizado(boolean esterilizado) {
        this.esterilizado = esterilizado;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mascota mascota = (Mascota) o;
        return Objects.equals(id, mascota.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Mascota{id=%d, nombre='%s', especie='%s', raza='%s', propietario='%s', edad=%d años}", 
                           id, nombre, especie, raza, propietario, calcularEdadEnAnios());
    }
}
