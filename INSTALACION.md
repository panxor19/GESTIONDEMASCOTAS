# Guía de Instalación - Proyecto CRUD con TDD

## 🛠️ Prerrequisitos

### 1. Java Development Kit (JDK) 11
Verificar si tienes Java 11 instalado:
```powershell
java -version
```

Si no tienes Java 11, descarga desde:
- **Oracle JDK**: https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html
- **OpenJDK**: https://jdk.java.net/11/

### 2. Apache Maven
Verificar si tienes Maven instalado:
```powershell
mvn -version
```

## 📥 Instalación de Maven en Windows

### Opción 1: Descarga Manual
1. Descargar Maven desde: https://maven.apache.org/download.cgi
2. Extraer en `C:\Program Files\Apache\maven`
3. Agregar al PATH del sistema:
   - Ir a "Sistema" → "Configuración avanzada del sistema"
   - "Variables de entorno"
   - En "Variables del sistema", buscar "Path"
   - Agregar: `C:\Program Files\Apache\maven\bin`

### Opción 2: Usando Chocolatey
```powershell
# Instalar Chocolatey (si no lo tienes)
Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))

# Instalar Maven
choco install maven
```

### Opción 3: Usando Scoop
```powershell
# Instalar Scoop (si no lo tienes)
iwr -useb get.scoop.sh | iex

# Instalar Maven
scoop install maven
```

## ✅ Verificación de Instalación

```powershell
java -version
mvn -version
```

Deberías ver algo similar a:
```
Apache Maven 3.9.x
Maven home: C:\Program Files\Apache\maven
Java version: 11.x.x
```

## 🚀 Comandos del Proyecto

Una vez que tengas Maven instalado, puedes usar estos comandos:

```powershell
# Limpiar y compilar
mvn clean compile

# Ejecutar pruebas
mvn test

# Generar reporte de cobertura
mvn jacoco:report

# Ejecutar aplicación
mvn exec:java -Dexec.mainClass="com.testing.agil.Main"

# Compilar todo y generar JAR
mvn clean package
```

## 🐛 Solución de Problemas

### Error: "mvn no se reconoce"
- Verificar que Maven esté en el PATH
- Reiniciar PowerShell/CMD después de instalar
- Verificar instalación con `where mvn`

### Error: "JAVA_HOME no está definido"
```powershell
# Definir JAVA_HOME temporalmente
$env:JAVA_HOME = "C:\Program Files\Java\jdk-11.x.x"

# O agregarlo permanentemente a variables del sistema
```

### Error de permisos
```powershell
# Ejecutar PowerShell como administrador
Set-ExecutionPolicy RemoteSigned
```

## 📁 Estructura del Proyecto Después de Compilar

```
crud-tdd-portfolio/
├── src/
├── target/                    # Archivos compilados
│   ├── classes/              # .class files
│   ├── test-classes/         # Test .class files
│   ├── site/jacoco/          # Reportes de cobertura
│   └── surefire-reports/     # Reportes de tests
├── pom.xml
└── README.md
```

## 🎯 Próximos Pasos

1. **Instalar Maven** siguiendo una de las opciones anteriores
2. **Verificar instalación** con `mvn -version`
3. **Compilar proyecto** con `mvn clean compile`
4. **Ejecutar tests** con `mvn test`
5. **Ver reporte de cobertura** en `target/site/jacoco/index.html`

¡Una vez que tengas Maven funcionando, podrás continuar con el desarrollo usando TDD!
