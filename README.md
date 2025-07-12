# Portafolio CRUD con TDD - Testing Ágil

## 📖 Descripción
Este proyecto es un portafolio que integra **Testing Ágil** con **TDD (Test-Driven Development)** en Java. Implementa un sistema CRUD completo utilizando metodologías ágiles y automatización de pruebas.

## 🎯 Objetivos del Proyecto
- Implementar un CRUD completo con TDD
- Aplicar principios de Testing Ágil
- Lograr cobertura de pruebas >= 80% con JaCoCo
- Usar Mockito para dependencias externas
- Aplicar principios SOLID y buenas prácticas

## 🛠️ Tecnologías Utilizadas
- **Java 11**
- **Maven** - Gestión de dependencias
- **JUnit 5** - Framework de pruebas
- **Mockito** - Mock objects para pruebas
- **SQLite** - Base de datos local
- **JaCoCo** - Cobertura de código
- **Git** - Control de versiones

## 📁 Estructura del Proyecto
```
crud-tdd-portfolio/
├── src/
│   ├── main/
│   │   ├── java/com/testing/agil/
│   │   │   ├── model/          # Entidades del dominio
│   │   │   ├── repository/     # Acceso a datos
│   │   │   ├── service/        # Lógica de negocio
│   │   │   └── Main.java       # Clase principal
│   │   └── resources/
│   └── test/
│       └── java/com/testing/agil/
│           ├── model/          # Tests de entidades
│           ├── repository/     # Tests de repositorio
│           └── service/        # Tests de servicios
├── sql-scripts/                # Scripts SQL
├── jacoco-report/             # Reportes de cobertura
├── docs/                      # Documentación
├── pom.xml                    # Configuración Maven
└── README.md                  # Este archivo
```

## 🚀 Instalación y Configuración

### Prerrequisitos
- Java 11 o superior
- Maven 3.6 o superior
- Visual Studio Code con extensiones de Java

### Pasos de instalación
1. Clonar el repositorio:
   ```bash
   git clone <url-del-repositorio>
   cd crud-tdd-portfolio
   ```

2. Instalar dependencias:
   ```bash
   mvn clean install
   ```

3. Ejecutar pruebas:
   ```bash
   mvn test
   ```

4. Generar reporte de cobertura:
   ```bash
   mvn jacoco:report
   ```

## 🧪 Metodología TDD
Este proyecto sigue el ciclo **RED-GREEN-REFACTOR**:

1. **RED**: Escribir una prueba que falle
2. **GREEN**: Escribir el código mínimo para que pase
3. **REFACTOR**: Mejorar el código manteniendo las pruebas verdes

### Operaciones CRUD Implementadas
- ✅ **CREATE**: Crear nuevos registros
- ✅ **READ**: Listar y buscar registros
- ✅ **UPDATE**: Actualizar registros existentes
- ✅ **DELETE**: Eliminar registros

## 📊 Métricas de Calidad
- **Ciclos TDD**: Mínimo 12 ciclos documentados
- **Pruebas Unitarias**: 8-16 pruebas automatizadas
- **Cobertura**: >= 80% con JaCoCo
- **Uso de Mockito**: En al menos una dependencia externa

## 🔧 Comandos Útiles

### Ejecutar aplicación
```bash
mvn exec:java -Dexec.mainClass="com.testing.agil.Main"
```

### Ejecutar pruebas con cobertura
```bash
mvn clean test jacoco:report
```

### Ver reporte de cobertura
El reporte se genera en: `target/site/jacoco/index.html`

## 📋 Plan de Testing Ágil
Ver archivo: `docs/plan-testing-agil.md`

## 🏗️ Principios SOLID Aplicados
- **SRP**: Cada clase tiene una responsabilidad específica
- **OCP**: Abierto para extensión, cerrado para modificación
- **LSP**: Las subclases pueden sustituir a sus clases base
- **ISP**: Interfaces específicas para cada cliente
- **DIP**: Dependencias hacia abstracciones, no implementaciones

## 🤝 Contribución
1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abrir un Pull Request

## 📝 Reflexión Personal
*[Esta sección se completará al finalizar el proyecto]*

## 📄 Licencia
Este proyecto es parte de un portafolio educativo para Testing Ágil y TDD.

## 👤 Autor
**Estudiante de Testing Ágil**
- Módulo 2: Testing Ágil
- Módulo 3: TDD en Automatización de Pruebas

---
*Proyecto desarrollado como parte del portafolio final de Testing Ágil + TDD*
