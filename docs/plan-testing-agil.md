# Plan de Testing Ágil para Sprint CRUD-TDD

## 📋 Información del Sprint
- **Sprint**: Sprint 1 - Implementación CRUD con TDD
- **Duración**: 2 semanas
- **Equipo**: 1 Developer (Tú) + 1 Product Owner (Instructor) + 1 Scrum Master (Facilitador)
- **Objetivo**: Implementar sistema CRUD completo aplicando TDD y principios de Testing Ágil

## 🎯 Goal del Sprint
Desarrollar un sistema CRUD funcional para gestión de usuarios utilizando metodología TDD, logrando una cobertura de pruebas >= 80% y aplicando al menos un principio SOLID documentado.

## 📖 Historias de Usuario

### Historia 1: Crear Usuario
**Como** administrador del sistema  
**Quiero** poder crear nuevos usuarios  
**Para** registrar personas en el sistema  

**Criterios de Aceptación:**
- [ ] El usuario debe tener nombre, email y edad
- [ ] El email debe ser único en el sistema
- [ ] El email debe tener formato válido (@, .)
- [ ] La edad debe ser un número positivo
- [ ] El usuario se crea como activo por defecto
- [ ] Se debe mostrar mensaje de confirmación

### Historia 2: Listar Usuarios
**Como** administrador del sistema  
**Quiero** ver la lista de usuarios registrados  
**Para** tener una visión general del sistema  

**Criterios de Aceptación:**
- [ ] Mostrar todos los usuarios registrados
- [ ] Mostrar información completa (id, nombre, email, edad, estado)
- [ ] Permitir filtrar solo usuarios activos
- [ ] Mostrar mensaje si no hay usuarios

### Historia 3: Buscar Usuario
**Como** administrador del sistema  
**Quiero** buscar usuarios específicos  
**Para** encontrar información rápidamente  

**Criterios de Aceptación:**
- [ ] Buscar por ID único
- [ ] Buscar por email único
- [ ] Mostrar mensaje si no se encuentra
- [ ] Mostrar información completa del usuario encontrado

### Historia 4: Actualizar Usuario
**Como** administrador del sistema  
**Quiero** modificar información de usuarios existentes  
**Para** mantener los datos actualizados  

**Criterios de Aceptación:**
- [ ] Buscar usuario existente por ID
- [ ] Permitir modificar nombre, email y edad
- [ ] Validar que el nuevo email sea único
- [ ] Mantener el ID original
- [ ] Mostrar confirmación de actualización

### Historia 5: Eliminar Usuario
**Como** administrador del sistema  
**Quiero** eliminar usuarios del sistema  
**Para** mantener información relevante  

**Criterios de Aceptación:**
- [ ] Eliminar lógicamente (marcar como inactivo)
- [ ] Eliminar físicamente con confirmación
- [ ] Verificar que el usuario existe antes de eliminar
- [ ] Mostrar confirmación de eliminación

## 🧪 Tipos de Pruebas

### 1. Pruebas Unitarias (JUnit 5)
- **Alcance**: Métodos individuales de cada clase
- **Herramientas**: JUnit 5 + Mockito
- **Objetivo**: Validar lógica de negocio aislada
- **Cobertura objetivo**: >= 80%

#### Tests por Componente:
**Model (Usuario.java)**
- ✅ Constructor con parámetros
- ✅ Validación de email
- ✅ Validación de mayoría de edad
- ✅ Métodos equals y hashCode

**Service Layer**
- ✅ Crear usuario con validaciones
- ✅ Buscar usuario por ID/email
- ✅ Listar usuarios (todos/activos)
- ✅ Actualizar usuario
- ✅ Eliminar usuario (lógico/físico)
- ✅ Validaciones de negocio

**Repository Layer**
- ✅ CRUD operations
- ✅ Queries específicas
- ✅ Manejo de excepciones

### 2. Pruebas de Integración
- **Alcance**: Interacción entre Service y Repository
- **Herramientas**: JUnit 5 + Base de datos en memoria
- **Objetivo**: Validar flujo completo de operaciones

### 3. Pruebas de Aceptación
- **Alcance**: Historias de usuario completas
- **Herramientas**: JUnit 5 + Fixtures de datos
- **Objetivo**: Validar criterios de aceptación

## ✅ Definición de "Terminado" (DoD)

Una historia está terminada cuando:
- [ ] Código implementado siguiendo TDD (RED-GREEN-REFACTOR)
- [ ] Pruebas unitarias implementadas y pasando
- [ ] Cobertura de código >= 80% para la funcionalidad
- [ ] Código revisado y sin errores de compilación
- [ ] Documentación actualizada (README, comentarios)
- [ ] Criterios de aceptación validados
- [ ] Principios SOLID aplicados y documentados
- [ ] Commit realizado con mensaje descriptivo

## 📅 Plan de Ejecución durante el Sprint

### Semana 1: Setup y CRUD básico
**Día 1-2: Configuración inicial**
- [ ] Setup del proyecto Maven
- [ ] Configuración de dependencias (JUnit, Mockito, JaCoCo)
- [ ] Estructura de paquetes
- [ ] Inicialización de Git

**Día 3-4: Historia 1 - Crear Usuario**
- [ ] **Ciclo TDD 1**: Test crear usuario básico → Implementar → Refactor
- [ ] **Ciclo TDD 2**: Test validación email → Implementar → Refactor
- [ ] **Ciclo TDD 3**: Test email único → Implementar → Refactor

**Día 5-7: Historia 2 - Listar Usuarios**
- [ ] **Ciclo TDD 4**: Test listar todos → Implementar → Refactor
- [ ] **Ciclo TDD 5**: Test listar activos → Implementar → Refactor
- [ ] **Ciclo TDD 6**: Test lista vacía → Implementar → Refactor

### Semana 2: Operaciones avanzadas y refinamiento
**Día 8-10: Historia 3 - Buscar Usuario**
- [ ] **Ciclo TDD 7**: Test buscar por ID → Implementar → Refactor
- [ ] **Ciclo TDD 8**: Test buscar por email → Implementar → Refactor
- [ ] **Ciclo TDD 9**: Test usuario no encontrado → Implementar → Refactor

**Día 11-12: Historia 4 - Actualizar Usuario**
- [ ] **Ciclo TDD 10**: Test actualizar datos → Implementar → Refactor
- [ ] **Ciclo TDD 11**: Test validaciones → Implementar → Refactor

**Día 13-14: Historia 5 - Eliminar Usuario**
- [ ] **Ciclo TDD 12**: Test eliminar lógico → Implementar → Refactor
- [ ] **Ciclo TDD 13**: Test eliminar físico → Implementar → Refactor (Extra)

## 👥 Roles y Responsabilidades

### Developer (Tú)
- Implementar código siguiendo TDD
- Escribir y ejecutar pruebas automatizadas
- Mantener cobertura >= 80%
- Aplicar principios SOLID
- Documentar ciclos TDD realizados
- Hacer commits frecuentes y descriptivos

### Product Owner (Instructor)
- Definir y clarificar criterios de aceptación
- Revisar que las historias cumplan los requisitos
- Validar la funcionalidad implementada
- Proporcionar feedback sobre el producto

### Scrum Master (Facilitador)
- Facilitar el proceso de desarrollo
- Ayudar a resolver impedimentos
- Asegurar seguimiento de la metodología ágil
- Promover mejora continua

## 🔄 Ceremonias Ágiles

### Daily Standup (Auto-reflexión diaria)
**Preguntas clave:**
- ¿Qué hice ayer para cumplir el objetivo del Sprint?
- ¿Qué haré hoy para cumplir el objetivo del Sprint?
- ¿Hay algún impedimento que me bloquee?

### Sprint Review (Al finalizar)
- Demostrar funcionalidades implementadas
- Revisar cobertura de pruebas lograda
- Mostrar principios SOLID aplicados
- Recopilar feedback

### Sprint Retrospective
- ¿Qué funcionó bien en el Sprint?
- ¿Qué se puede mejorar?
- ¿Qué compromisos tomamos para el siguiente Sprint?

## 📊 Métricas de Calidad

### Métricas de Testing
- **Cobertura de líneas**: >= 80%
- **Cobertura de branches**: >= 70%
- **Número de tests**: 8-16 pruebas unitarias
- **Tests fallidos**: 0 (todas las pruebas deben pasar)

### Métricas de TDD
- **Ciclos TDD completados**: Mínimo 12
- **Tiempo en RED**: < 5 minutos por ciclo
- **Tiempo en GREEN**: < 10 minutos por ciclo
- **Frecuencia de refactor**: Al menos 1 por cada 3 ciclos

### Métricas de Calidad de Código
- **Principios SOLID aplicados**: Mínimo 1 documentado
- **Complejidad ciclomática**: < 10 por método
- **Duplicación de código**: < 5%
- **Code smells**: 0 críticos

## 🛠️ Herramientas de Testing

### Frameworks y Librerías
- **JUnit 5**: Framework de pruebas principal
- **Mockito**: Para mocking de dependencias
- **JaCoCo**: Medición de cobertura
- **SQLite**: Base de datos para testing

### Comandos útiles
```bash
# Ejecutar todas las pruebas
mvn test

# Generar reporte de cobertura
mvn jacoco:report

# Verificar cobertura mínima
mvn jacoco:check

# Ejecutar aplicación
mvn exec:java -Dexec.mainClass="com.testing.agil.Main"
```

## 🔄 Integración de TDD en el Flujo Ágil

### Cada Historia de Usuario sigue este flujo:
1. **Análisis**: Entender criterios de aceptación
2. **Diseño**: Planificar tests y estructura
3. **TDD Cycles**: Implementar usando RED-GREEN-REFACTOR
4. **Integración**: Asegurar que funciona con el resto
5. **Review**: Validar criterios de aceptación
6. **Demo**: Mostrar funcionalidad al Product Owner

### Integración Continua simulada:
- Commits frecuentes (mínimo diarios)
- Todas las pruebas deben pasar antes de cada commit
- Reporte de cobertura generado automáticamente
- Documentación actualizada en cada cambio

---

**Nota**: Este plan está diseñado para un desarrollador individual que simula un entorno ágil real, aplicando las mejores prácticas de Testing Ágil y TDD en un proyecto práctico.
