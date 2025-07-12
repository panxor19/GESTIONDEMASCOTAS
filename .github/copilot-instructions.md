<!-- Use this file to provide workspace-specific custom instructions to Copilot. For more details, visit https://code.visualstudio.com/docs/copilot/copilot-customization#_use-a-githubcopilotinstructionsmd-file -->

# Instrucciones para Copilot - Proyecto CRUD con TDD

## Contexto del Proyecto
Este es un proyecto de portafolio que implementa un sistema CRUD en Java utilizando metodología TDD (Test-Driven Development) para un curso de Testing Ágil.

## Objetivos Específicos
- Implementar 4 operaciones CRUD (Create, Read, Update, Delete)
- Completar mínimo 12 ciclos TDD documentados
- Crear 8-16 pruebas unitarias automatizadas
- Lograr >= 80% cobertura de código con JaCoCo
- Usar Mockito para al menos una dependencia externa
- Aplicar al menos un principio SOLID documentado

## Metodología TDD
Seguir estrictamente el ciclo RED-GREEN-REFACTOR:
1. **RED**: Escribir una prueba que falle primero
2. **GREEN**: Escribir el código mínimo para que la prueba pase
3. **REFACTOR**: Mejorar el código manteniendo las pruebas verdes

## Tecnologías del Proyecto
- Java 11
- Maven para gestión de dependencias
- JUnit 5 para pruebas unitarias
- Mockito para mocking
- SQLite como base de datos
- JaCoCo para cobertura de código

## Estructura de Paquetes
- `model`: Entidades del dominio (Usuario, Producto, etc.)
- `repository`: Acceso a datos y persistencia
- `service`: Lógica de negocio
- `Main`: Clase principal de la aplicación

## Patrones y Buenas Prácticas
- Repository Pattern para acceso a datos
- Service Layer para lógica de negocio
- Dependency Injection
- Principios SOLID (especialmente SRP y DIP)
- Clean Code principles

## Consideraciones para Testing
- Cada test debe ser independiente y aislado
- Usar nomenclatura descriptiva: `given_when_then` o `should_when`
- Mockear dependencias externas (base de datos, servicios)
- Validar tanto casos exitosos como de error
- Mantener tests simples y enfocados

## Base de Datos
- Usar SQLite para simplicidad
- Implementar scripts de inicialización
- Considerar transacciones para operaciones críticas
- Mockear el acceso a BD en tests unitarios

## Documentación Requerida
- Documentar cada ciclo TDD realizado
- Explicar el principio SOLID aplicado
- Incluir reflexión personal del aprendizaje
- Mantener README actualizado con instrucciones

## Comandos Maven Importantes
- `mvn test`: Ejecutar pruebas
- `mvn jacoco:report`: Generar reporte de cobertura
- `mvn clean install`: Compilar y instalar dependencias

Cuando generes código, prioriza la calidad, simplicidad y adherencia a TDD sobre la velocidad de desarrollo.
