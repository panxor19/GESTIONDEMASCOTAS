# Principios SOLID Aplicados en el Proyecto CRUD

## 📋 Resumen de Principios SOLID Implementados

Este proyecto implementa **TODOS los 5 principios SOLID** de manera práctica y documentada:

1. ✅ **SRP** - Single Responsibility Principle
2. ✅ **OCP** - Open/Closed Principle  
3. ✅ **LSP** - Liskov Substitution Principle
4. ✅ **ISP** - Interface Segregation Principle
5. ✅ **DIP** - Dependency Inversion Principle

---

## 🎯 1. SRP - Single Responsibility Principle

### **Definición**: "Una clase debe tener una sola razón para cambiar"

### **Implementación en el proyecto**:

#### **Clase Usuario** (`model/Usuario.java`)
```java
public class Usuario {
    // RESPONSABILIDAD ÚNICA: Representar entidad de dominio
    private Long id;
    private String nombre;
    private String email;
    private int edad;
    private boolean activo;
    
    // Solo métodos relacionados con la entidad Usuario
    public boolean esEmailValido() { ... }
    public boolean esMayorDeEdad() { ... }
}
```
**✅ Razón única de cambio**: Solo cambia si cambian las reglas de negocio de la entidad Usuario.

#### **Clase UsuarioRepositoryImpl** (`repository/UsuarioRepositoryImpl.java`)
```java
public class UsuarioRepositoryImpl implements UsuarioRepository {
    // RESPONSABILIDAD ÚNICA: Persistencia de datos
    public Usuario crear(Usuario usuario) { ... }
    public Optional<Usuario> buscarPorId(Long id) { ... }
    // Solo operaciones de acceso a datos
}
```
**✅ Razón única de cambio**: Solo cambia si cambia la forma de persistir datos.

#### **Clase UsuarioServiceImpl** (`service/UsuarioServiceImpl.java`)
```java
public class UsuarioServiceImpl implements UsuarioService {
    // RESPONSABILIDAD ÚNICA: Lógica de negocio
    public Usuario crearUsuario(Usuario usuario) {
        validarUsuarioParaCreacion(usuario); // Lógica de negocio
        // ...
    }
}
```
**✅ Razón única de cambio**: Solo cambia si cambian las reglas de negocio.

---

## 🔓 2. OCP - Open/Closed Principle

### **Definición**: "Abierto para extensión, cerrado para modificación"

### **Implementación en el proyecto**:

#### **Interfaz UsuarioRepository**
```java
public interface UsuarioRepository {
    Usuario crear(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    // ... otros métodos
}
```

#### **Extensión sin modificación**
```java
// ABIERTO para extensión: Nueva implementación
public class UsuarioRepositoryMongoDB implements UsuarioRepository {
    @Override
    public Usuario crear(Usuario usuario) {
        // Implementación para MongoDB
        // SIN MODIFICAR el código existente
    }
}

// ABIERTO para extensión: Nueva implementación
public class UsuarioRepositoryMemory implements UsuarioRepository {
    @Override
    public Usuario crear(Usuario usuario) {
        // Implementación en memoria
        // SIN MODIFICAR el código existente
    }
}
```

**✅ Beneficio**: Podemos agregar nuevas formas de persistencia sin tocar código existente.

---

## 🔄 3. LSP - Liskov Substitution Principle

### **Definición**: "Los objetos de una superclase deben ser reemplazables por objetos de sus subclases"

### **Implementación en el proyecto**:

#### **Sustituibilidad en Service**
```java
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository repository;
    
    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository; // Acepta cualquier implementación
    }
    
    public Usuario crearUsuario(Usuario usuario) {
        // Funciona con CUALQUIER implementación de UsuarioRepository
        return repository.crear(usuario);
    }
}
```

#### **Intercambiabilidad garantizada**
```java
// Todas estas implementaciones son intercambiables:
UsuarioRepository repo1 = new UsuarioRepositoryImpl();     // SQLite
UsuarioRepository repo2 = new UsuarioRepositoryMongoDB(); // MongoDB  
UsuarioRepository repo3 = new UsuarioRepositoryMemory();  // En memoria

// El service funciona igual con cualquiera:
UsuarioService service1 = new UsuarioServiceImpl(repo1);
UsuarioService service2 = new UsuarioServiceImpl(repo2);
UsuarioService service3 = new UsuarioServiceImpl(repo3);
```

**✅ Beneficio**: Cualquier implementación de repositorio funciona sin cambios en el service.

---

## 🧩 4. ISP - Interface Segregation Principle  

### **Definición**: "Ningún cliente debe depender de métodos que no usa"

### **Implementación en el proyecto**:

#### **Interfaces específicas y cohesivas**
```java
// INTERFAZ ESPECÍFICA para operaciones de usuario
public interface UsuarioService {
    // Solo métodos relacionados con usuarios
    Usuario crearUsuario(Usuario usuario);
    Optional<Usuario> buscarUsuarioPorId(Long id);
    List<Usuario> listarUsuariosActivos();
    // NO incluye métodos de otros dominios
}

// INTERFAZ ESPECÍFICA para persistencia de usuario  
public interface UsuarioRepository {
    // Solo operaciones de persistencia
    Usuario crear(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    boolean eliminar(Long id);
    // NO incluye lógica de negocio
}
```

#### **Sin interfaces "gordas"**
```java
// ❌ VIOLACIÓN del ISP (no implementado en nuestro proyecto)
public interface ServicioCompleto {
    // Métodos de usuario
    Usuario crearUsuario(Usuario usuario);
    // Métodos de producto  
    Producto crearProducto(Producto producto);
    // Métodos de pedido
    Pedido crearPedido(Pedido pedido);
    // Clientes se ven obligados a implementar métodos que no necesitan
}

// ✅ CUMPLE ISP (nuestro enfoque)
public interface UsuarioService { /* solo métodos de usuario */ }
public interface ProductoService { /* solo métodos de producto */ }
public interface PedidoService { /* solo métodos de pedido */ }
```

**✅ Beneficio**: Cada cliente solo implementa lo que necesita.

---

## 🔄 5. DIP - Dependency Inversion Principle

### **Definición**: "Depender de abstracciones, no de concreciones"

### **Implementación en el proyecto**:

#### **Dependencias hacia abstracciones**
```java
public class UsuarioServiceImpl implements UsuarioService {
    // ✅ DEPENDE de la abstracción (interfaz)
    private final UsuarioRepository usuarioRepository;
    
    // ❌ NO depende de la implementación concreta:
    // private final UsuarioRepositoryImpl usuarioRepository;
    
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository; // Abstracción
    }
}
```

#### **Inversión de control**
```java
// Módulo de alto nivel (Service) NO depende de módulo de bajo nivel (Repository)
// Ambos dependen de la abstracción (UsuarioRepository interface)

public class Main {
    public static void main(String[] args) {
        // Inyección de dependencias
        UsuarioRepository repository = new UsuarioRepositoryImpl();
        UsuarioService service = new UsuarioServiceImpl(repository);
        
        // Fácil cambiar implementación:
        // UsuarioRepository repository = new UsuarioRepositoryMongoDB();
    }
}
```

**✅ Beneficio**: Fácil testing, mantenimiento y extensibilidad.

---

## 🎯 Beneficios Obtenidos

### **1. Mantenibilidad**
- Cada clase tiene una responsabilidad clara
- Cambios localizados sin afectar otros componentes

### **2. Testabilidad**  
- Fácil crear mocks para testing unitario
- Dependencias inyectables permiten testing aislado

### **3. Extensibilidad**
- Nuevas implementaciones sin modificar código existente
- Interfaces permiten múltiples implementaciones

### **4. Flexibilidad**
- Componentes intercambiables
- Configuración por inyección de dependencias

### **5. Legibilidad**
- Código más claro y enfocado
- Interfaces describen contratos claros

---

## 🏆 Ejemplos Prácticos de Testing con SOLID

### **Testing facilitado por DIP**
```java
@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {
    @Mock
    private UsuarioRepository usuarioRepository; // Mock de la abstracción
    
    @InjectMocks  
    private UsuarioServiceImpl usuarioService;
    
    @Test
    void should_CreateUser_When_ValidDataProvided() {
        // El testing es fácil gracias a DIP
        when(usuarioRepository.crear(any())).thenReturn(usuario);
        // ...
    }
}
```

### **Extensibilidad demostrada**
```java
// Agregar nueva funcionalidad sin romper código existente
public class UsuarioRepositoryCache implements UsuarioRepository {
    private final UsuarioRepository repository;
    private final Cache cache;
    
    // Decorator pattern + OCP
    @Override
    public Usuario crear(Usuario usuario) {
        Usuario created = repository.crear(usuario);
        cache.put(created.getId(), created);
        return created;
    }
}
```

---

## 📝 Conclusión

Este proyecto demuestra la aplicación práctica de **todos los principios SOLID**:

- **SRP**: Clases con responsabilidades únicas y bien definidas
- **OCP**: Interfaces que permiten extensión sin modificación  
- **LSP**: Implementaciones intercambiables que mantienen el contrato
- **ISP**: Interfaces específicas sin métodos innecesarios
- **DIP**: Dependencias hacia abstracciones con inyección

Los principios SOLID no son solo teoría, sino herramientas prácticas que mejoran significativamente la calidad del código, facilitando el mantenimiento, testing y evolución del software.

**Principio destacado para el portafolio**: **DIP (Dependency Inversion Principle)** - por su impacto directo en testabilidad y flexibilidad del diseño.
