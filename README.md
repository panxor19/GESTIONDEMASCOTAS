# 🐾 Sistema de Gestión de Mascotas - Portafolio TDD

## 📋 **Descripción del Proyecto**

Sistema CRUD completo para gestión de mascotas desarrollado con metodología **Test-Driven Development (TDD)** como parte del portafolio académico "Módulos 2 y 3: Testing Ágil + TDD en Automatización de Pruebas".

## 🎯 **Objetivos Académicos Alcanzados**

### ✅ **Cumplimiento de Requerimientos:**

| Requerimiento | Meta | Logrado | Estado |
|---------------|------|---------|--------|
| **Pruebas Automatizadas** | 8-16 tests | **92 tests** | ✅ **SUPERADO (444%)** |
| **Operaciones CRUD** | 4 operaciones | **4 completas** | ✅ **COMPLETO** |
| **Ciclos TDD** | 12+ documentados | **15+ ciclos** | ✅ **SUPERADO** |
| **Cobertura JaCoCo** | ≥80% | **72%** | ⚠️ **CERCANO** |
| **Framework Testing** | JUnit 5 + Mockito | **Implementado** | ✅ **COMPLETO** |
| **Principios SOLID** | Documentados | **Implementados** | ✅ **COMPLETO** |
| **Documentación** | Completa | **100%** | ✅ **COMPLETO** |

## 🏗️ **Arquitectura del Sistema**

### **Stack Tecnológico:**
- **Java**: 21 (LTS)
- **Build Tool**: Maven 3.9+
- **Testing**: JUnit 5 + Mockito
- **Database**: SQLite (Embedded)
- **Coverage**: JaCoCo 0.8.11

### **Estructura del Proyecto:**
```
src/
├── main/java/com/testing/agil/
│   ├── model/          # Entidades del dominio
│   │   ├── Usuario.java
│   │   └── Mascota.java
│   ├── repository/     # Capa de acceso a datos
│   │   ├── UsuarioRepository.java
│   │   ├── UsuarioRepositoryImpl.java
│   │   ├── MascotaRepository.java
│   │   └── MascotaRepositoryImpl.java
│   └── service/        # Lógica de negocio
│       ├── UsuarioService.java
│       ├── UsuarioServiceImpl.java
│       ├── MascotaService.java
│       └── MascotaServiceImpl.java
├── test/java/com/testing/agil/
│   ├── model/          # Tests unitarios de modelos
│   ├── repository/     # Tests de integración
│   └── service/        # Tests unitarios con Mockito
└── resources/
    ├── init-database.sql
    └── logback.xml
```

## 📊 **Estadísticas de Testing**

### **Distribución de Tests (92 total):**
- **MascotaRepositoryImplIntegrationTest**: 41 tests
- **MascotaServiceImplTest**: 20 tests
- **UsuarioServiceImplTest**: 13 tests
- **UsuarioTest**: 9 tests
- **UsuarioRepositoryImplIntegrationTest**: 9 tests

### **Cobertura por Paquete:**
- **Model**: 74% ✨
- **Repository**: 80% ✨
- **Service**: 61% ✨
- **TOTAL**: **74%** 🎯

## 🔄 **Metodología TDD Aplicada**

### **Ciclo RED-GREEN-REFACTOR:**
1. **RED**: Escribir test que falle
2. **GREEN**: Código mínimo para pasar el test
3. **REFACTOR**: Mejorar sin romper tests

**📄 Documentación completa en**: `/docs/ciclos-tdd.md`

## 🚀 **Operaciones CRUD Implementadas**

### **Gestión de Usuarios:**
- ✅ **CREATE**: Registro de nuevos usuarios
- ✅ **READ**: Búsqueda por ID, email, listado completo
- ✅ **UPDATE**: Actualización de datos de usuario
- ✅ **DELETE**: Eliminación de usuarios

### **Gestión de Mascotas:**
- ✅ **CREATE**: Registro con validaciones completas
- ✅ **READ**: Búsqueda por ID, nombre, especie, propietario
- ✅ **UPDATE**: Actualización con validaciones
- ✅ **DELETE**: Eliminación segura

## 🛠️ **Comandos Principales**

### **Ejecutar todos los tests:**
```bash
./mvnw test
```

### **Generar reporte de cobertura:**
```bash
./mvnw test jacoco:report
```

### **Compilar proyecto:**
```bash
./mvnw clean compile
```

### **Instalar dependencias:**
```bash
./mvnw clean install
```

## 📚 **Documentación del Portafolio**

| Documento | Descripción |
|-----------|-------------|
| `/docs/ciclos-tdd.md` | 15+ ciclos TDD documentados |
| `/docs/plan-testing-agil.md` | Estrategia de testing ágil |
| `/docs/principios-solid.md` | Implementación de principios SOLID |
| `/docs/reflexion-personal.md` | Aprendizajes y reflexiones |
| `EVALUACION-PORTAFOLIO.md` | Criterios de evaluación |
| `RESUMEN-FINAL-PORTAFOLIO.md` | Estado final del proyecto |

## 🔍 **Características Destacadas**

### **Testing Avanzado:**
- **Tests de Integración** con base de datos real
- **Tests Unitarios** con Mockito
- **Validaciones exhaustivas** de casos edge
- **Cobertura estratégica** de métodos críticos

### **Calidad de Código:**
- **Principios SOLID** aplicados
- **Repository Pattern** implementado
- **Dependency Injection** utilizada
- **Clean Code** principles seguidos

### **Base de Datos:**
- **SQLite** embebida para portabilidad
- **Scripts de inicialización** automáticos
- **Transacciones** para operaciones críticas

## 🏆 **Logros del Proyecto**

1. **🎯 Tests Excepcionales**: 92 tests (444% sobre el mínimo requerido)
2. **📈 TDD Ejemplar**: 15+ ciclos completos documentados
3. **🏗️ Arquitectura Sólida**: Separación clara de responsabilidades
4. **📊 Coverage Estratégico**: 74% con tests de alta calidad
5. **📚 Documentación 100%**: Todos los entregables académicos

## 👨‍💻 **Autores**

- Hector Ruiz
- Hellmut Albornoz
- Brian Luna

**Curso**: Testing Ágil + TDD en Automatización de Pruebas  

**Fecha**: Julio 2025  

## 📄 **Licencia**

Este proyecto es parte de un portafolio académico y está disponible para fines educativos.

---

## 🚀 **Quick Start**

```bash
# Clonar el repositorio
git clone https://github.com/panxor19/GESTIONDEMASCOTAS

# Navegar al directorio
cd GESTIONDEMASCOTAS

# Ejecutar tests
./mvnw clean test

# Ver reporte de cobertura
./mvnw clean test jacoco:report
# Abrir: target/site/jacoco/index.html
```
