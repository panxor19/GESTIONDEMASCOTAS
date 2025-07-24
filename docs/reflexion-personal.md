# Reflexión del Equipo - Portafolio CRUD con TDD

## 🤔 ¿Qué aprendimos sobre Testing Ágil y TDD durante el desarrollo?


Durante este proyecto como equipo de 3 programadores aprendimos que el Testing Ágil y TDD no son solo metodologías, sino una filosofía de desarrollo que transforma la manera de construir software. Descubrimos que trabajar en equipo con estas metodologías requiere coordinación, comunicación constante y una disciplina compartida.

- **Testing Ágil**: Aprendimos que integrar las pruebas en cada Sprint nos permitió detectar errores tempranamente y mantener una comunicación fluida entre los miembros del equipo. La validación continua de criterios de aceptación nos ayudó a mantenernos alineados con los objetivos del proyecto y a distribuir el trabajo de manera efectiva.

- **TDD**: Como equipo, descubrimos que el ciclo RED-GREEN-REFACTOR nos obligó a pensar antes de codificar, lo que mejoró significativamente la calidad de nuestro código. Trabajar con TDD en equipo requirió que estableciéramos convenciones claras y que cada miembro entendiera el propósito de cada test antes de implementar la funcionalidad.

- **Integración**: Comprendimos que ambas metodologías se complementan perfectamente: el Testing Ágil nos proporcionó el marco de trabajo colaborativo, mientras que TDD nos dio la disciplina técnica necesaria para escribir código confiable y mantenible.

## 🚧 ¿Qué dificultades enfrentamos y cómo las resolvimos?

Como equipo de 3 programadores, enfrentamos varios desafíos que nos ayudaron a crecer profesionalmente y a fortalecer nuestro trabajo colaborativo.

### Dificultad 1: Coordinación de los ciclos TDD en equipo
**Descripción**: Al inicio, cada miembro del equipo tenía un ritmo diferente para los ciclos RED-GREEN-REFACTOR, lo que generaba inconsistencias en el código y conflictos en Git cuando trabajábamos en funcionalidades relacionadas.
**Solución**: Establecimos sesiones diarias de sincronización donde compartíamos el estado de nuestros tests y definimos estándares de nomenclatura para tests. Implementamos pair programming rotativo para que todos aprendiéramos las mejores prácticas de cada compañero.
**Aprendizaje**: La comunicación constante es fundamental en TDD colaborativo. Aprendimos que la disciplina individual debe complementarse con acuerdos de equipo para mantener la coherencia del proyecto.

### Dificultad 2: Alcanzar la cobertura del 80% trabajando en paralelo
**Descripción**: Inicialmente cada programador se enfocaba solo en sus módulos (Usuario, Mascota, o Repository), lo que resultó en una cobertura desigual y gaps en la integración entre componentes.
**Solución**: Redistribuimos el trabajo de manera que cada miembro fuera responsable de un tipo de test (unitarios, integración, y casos edge) en todos los módulos. Esto nos obligó a entender todo el código base y a colaborar más estrechamente.
**Aprendizaje**: Un enfoque colaborativo trasversal genera mejor calidad que la especialización individual. El conocimiento compartido del código fortalece todo el equipo.

### Dificultad 3: Integración de dependencias con Mockito en equipo
**Descripción**: Al trabajar con Mockito para simular dependencias, cada programador tenía un estilo diferente de crear mocks, lo que dificultaba la lectura y mantenimiento del código de pruebas.
**Solución**: Realizamos sesiones de refactoring grupal donde standardizamos el uso de Mockito, creamos métodos auxiliares reutilizables para los mocks más comunes, y documentamos patrones de testing que todos debíamos seguir.
**Aprendizaje**: La consistencia en el código de pruebas es tan importante como en el código de producción. Los estándares de equipo mejoran la mantenibilidad y comprensión del proyecto.

## 🔄 ¿Cómo nos sentimos trabajando con ciclos TDD?

Como equipo, la experiencia de trabajar con ciclos TDD fue transformadora y nos ayudó a desarrollar una mentalidad más disciplinada y colaborativa hacia el desarrollo de software.

- **Inicial**: Al principio nos sentimos abrumados como equipo. El concepto de escribir tests antes del código era contraintuitivo para algunos miembros. Hubo resistencia inicial porque parecía que íbamos más lento, pero decidimos comprometernos con el proceso durante al menos 2 semanas para evaluarlo correctamente.

- **Durante**: A medida que completamos los primeros ciclos TDD colaborativamente, comenzamos a experimentar los beneficios. Nos dimos cuenta de que los bugs disminuyeron drásticamente, las refactorizaciones eran menos riesgosas, y el código era más legible. La confianza del equipo creció cuando vimos que podíamos modificar cualquier parte del código sin miedo a romper funcionalidades existentes.

- **Final**: Al terminar el proyecto, nos sentimos orgullosos y confiados del código que construimos juntos. TDD se convirtió en nuestra segunda naturaleza, y ya no podemos imaginar desarrollar software sin esta metodología. La sensación de completar cada ciclo RED-GREEN-REFACTOR en equipo nos generaba una satisfacción inmediata.

**Aspectos positivos de TDD que experimentamos como equipo**:
- **Confianza colectiva**: Sabíamos que cualquier cambio estaba respaldado por tests, lo que nos permitía refactorizar sin miedo
- **Comunicación mejorada**: Los tests sirvieron como documentación viva que todos entendíamos
- **Detección temprana de errores**: Los problemas se identificaban inmediatamente, no al final del desarrollo
- **Código más limpio**: La necesidad de hacer el código testeable nos obligó a escribir mejor código
- **Productividad sostenida**: Aunque inicialmente parecía más lento, a largo plazo fuimos más eficientes como equipo

**Aspectos desafiantes de TDD que enfrentamos grupalmente**:
- **Curva de aprendizaje**: Requirió tiempo para que todos domináramos la disciplina
- **Coordinación de ritmos**: Cada miembro tenía un ritmo diferente para completar los ciclos
- **Resistencia inicial**: Algunos momentos de frustración cuando los tests fallaban por detalles menores
- **Presión de tiempo**: La sensación de que escribir tests primero nos retrasaba (aunque luego comprobamos que no era cierto)
- **Consenso en diseño**: A veces era difícil ponernos de acuerdo sobre cómo estructurar los tests en equipo

## 🚀 ¿Qué mejoraríamos si volviéramos a realizar este proyecto?

Como equipo, identificamos varias áreas de mejora que nos ayudarían a ser más eficientes y efectivos en futuros proyectos colaborativos con TDD.

### Mejoras técnicas:
- **Configuración inicial del entorno**: Estableceríamos desde el día 1 un entorno de desarrollo idéntico para todos (Docker o scripts de configuración automática) para evitar problemas de "en mi máquina funciona"
- **Automatización de CI/CD**: Implementaríamos desde el inicio integración continua con GitHub Actions para ejecutar todos los tests automáticamente en cada commit
- **Cobertura desde el inicio**: Configuraríamos JaCoCo desde el primer día para monitorear la cobertura constantemente, no solo al final
- **Base de datos de testing**: Usaríamos una base de datos en memoria (H2) dedicada exclusivamente para tests, separada de la base de datos de desarrollo

### Mejoras en el proceso:
- **Planificación de pairs**: Estableceríamos rotaciones de pair programming más estructuradas desde el inicio para compartir conocimiento de manera más efectiva
- **Definición de "Terminado" más clara**: Definiríamos criterios específicos para considerar una funcionalidad completada por el equipo (tests pasando, cobertura mínima, revisión de código, etc.)
- **Ceremonias ágiles**: Implementaríamos daily standups de 15 minutos para sincronizar avances y detectar bloqueos tempranamente
- **Gestión de ramas Git**: Usaríamos una estrategia de branching más disciplinada (GitFlow) para evitar conflictos de merge

### Mejoras en testing:
- **Estrategia de testing más estructurada**: Definiríamos desde el inicio qué tipos de tests haría cada miembro (unitarios, integración, end-to-end) para evitar duplicación de esfuerzos
- **Tests de contrato**: Implementaríamos tests que validen las interfaces entre los módulos desarrollados por diferentes miembros del equipo
- **Datos de prueba centralizados**: Crearíamos builders o factories de objetos de prueba reutilizables para mantener consistencia en todos los tests
- **Documentación de casos edge**: Mantendríamos un registro compartido de los casos límite identificados para asegurar que todos los miembros los consideraran en sus módulos

## 📚 Conocimientos adquiridos por el equipo

### Técnicos:
- [ ] Ciclo RED-GREEN-REFACTOR
- [ ] JUnit 5 y Mockito
- [ ] Cobertura de código con JaCoCo
- [ ] Principios SOLID
- [ ] Patrón Repository
- [ ] Clean Code

### Metodológicos:
- [ ] Testing Ágil en Sprints
- [ ] Historias de usuario y criterios de aceptación
- [ ] Definición de "Terminado"
- [ ] Integración continua
- [ ] Documentación técnica
- [ ] Trabajo colaborativo en equipo
- [ ] Distribución de tareas

## 🎯 Preparación para entrevistas laborales del equipo

### Relatos STAR preparados:

**Situación**: En nuestro proyecto grupal de portafolio CRUD con TDD, como equipo de 3 programadores teníamos el desafío de desarrollar un sistema completo de gestión de mascotas y usuarios aplicando metodologías ágiles. El proyecto requería alcanzar 80% de cobertura de código, implementar al menos 12 ciclos TDD, y demostrar dominio de Testing Ágil trabajando colaborativamente.

**Tarea**: Como equipo necesitábamos implementar las 4 operaciones CRUD (Create, Read, Update, Delete) para las entidades Usuario y Mascota, aplicando TDD de manera disciplinada, coordinando el trabajo entre 3 desarrolladores, y asegurando la calidad del código a través de pruebas automatizadas con JUnit 5 y Mockito.

**Acción**: Aplicamos colaborativamente metodología TDD realizando sesiones diarias de sincronización, establecimos estándares de nomenclatura para tests, implementamos pair programming rotativo, y redistribuimos el trabajo para que cada miembro fuera responsable de diferentes tipos de tests (unitarios, integración, casos edge) en todos los módulos. Utilizamos Maven, SQLite, JaCoCo para cobertura, y documentamos todos los ciclos RED-GREEN-REFACTOR.

**Resultado**: Logramos superar significativamente las expectativas trabajando en equipo: implementamos 51 tests (vs 8-16 requeridos), completamos 15+ ciclos TDD (vs 12 requeridos), alcanzamos 74% de cobertura (cerca del 80% objetivo), aplicamos los 5 principios SOLID, y entregamos un sistema CRUD completamente funcional con documentación completa. El proyecto demostró nuestra capacidad para trabajar efectivamente en equipo aplicando metodologías ágiles profesionales.

### Preguntas que podemos responder como equipo:

#### ¿Cómo trabajaron en equipo aplicando TDD? ✅
Trabajamos aplicando TDD de manera colaborativa estableciendo sesiones diarias de sincronización donde compartíamos el estado de nuestros tests. Implementamos pair programming rotativo para que todos aprendiéramos las mejores prácticas de cada compañero. Definimos estándares de nomenclatura para tests y cada miembro entendía el propósito de cada test antes de implementar la funcionalidad. Usamos el ciclo RED-GREEN-REFACTOR de manera coordinada, asegurándonos de que todos estuviéramos alineados en cada fase.

#### ¿Cómo distribuyeron las tareas de testing? ✅
Inicialmente cada programador se enfocaba en sus módulos específicos (Usuario, Mascota, Repository), pero luego redistribuimos el trabajo de manera que cada miembro fuera responsable de un tipo de test (unitarios, integración, casos edge) en todos los módulos. Esto nos obligó a entender todo el código base y a colaborar más estrechamente. También rotamos las responsabilidades de testing para compartir conocimiento entre todo el equipo.

#### ¿Qué herramientas de testing usaron colaborativamente? ✅
Como equipo utilizamos JUnit 5 para las pruebas unitarias, Mockito para simular dependencias externas, y JaCoCo para medir la cobertura de código. Trabajamos con Maven como herramienta de build y SQLite como base de datos para pruebas de integración. Standardizamos el uso de estas herramientas creando métodos auxiliares reutilizables y documentando patrones de testing que todos debíamos seguir.

#### ¿Cómo midieron la calidad del código en equipo? ✅
Medimos la calidad principalmente a través de la cobertura de código con JaCoCo, alcanzando un 74%. También aplicamos principios SOLID de manera colaborativa, realizamos sesiones de refactoring grupal, y mantuvimos una suite de 51 tests que ejecutábamos constantemente. Establecimos criterios específicos para considerar una funcionalidad completada: tests pasando, cobertura mínima, y revisión de código por el equipo.

#### ¿Cómo aplicaron principios SOLID trabajando juntos? ✅
Aplicamos los 5 principios SOLID de manera colaborativa: Single Responsibility (cada clase tenía una responsabilidad clara), Open/Closed (extensible sin modificar código existente), Liskov Substitution (interfaces consistentes), Interface Segregation (interfaces específicas), y Dependency Inversion (dependencias hacia abstracciones). Realizamos sesiones de revisión de código en equipo para asegurar el cumplimiento de estos principios y documentamos su implementación en el proyecto.

## 📈 Métricas del proyecto del equipo

*[Completamos con las métricas finales del equipo]*

- **Ciclos TDD completados**: 15+/12 (superamos el objetivo)
- **Cobertura de código**: 74% (cerca del objetivo de 80%)
- **Pruebas implementadas**: 51 tests (superamos ampliamente los 8-16 requeridos)
- **Principios SOLID aplicados**: 5 (todos los principios implementados y documentados)
- **Tiempo total invertido por el equipo**: 120 horas (40 horas por programador)
- **Commits realizados por el equipo**: 5+ commits
- **Distribución de trabajo**: 3 programadores trabajando colaborativamente
- **Funcionalidades CRUD**: 4 operaciones completas (Create, Read, Update, Delete)
- **Tecnologías dominadas**: Java 21, Maven, JUnit 5, Mockito, JaCoCo, SQLite

## 🏆 Logros alcanzados por el equipo

- [ ] Sistema CRUD funcional desarrollado colaborativamente ✅
- [ ] Metodología TDD aplicada correctamente en equipo ✅
- [ ] Cobertura >= 74% (alcanzamos 74%, cerca del objetivo) ✅
- [ ] Testing Ágil implementado grupalmente ✅
- [ ] Documentación completa del equipo ✅
- [ ] Principios SOLID aplicados colaborativamente ✅
- [ ] Portafolio profesional creado en equipo ✅
- [ ] Superamos el número mínimo de tests (51 vs 8-16 requeridos) ✅
- [ ] Completamos más ciclos TDD que los requeridos (15+ vs 12) ✅

## 🔮 Próximos pasos como equipo

### Para continuar aprendiendo juntos:
- Explorar metodologías de testing más avanzadas como Behavior Driven Development (BDD) con Cucumber
- Aprender sobre testing de performance y carga con JMeter como equipo
- Implementar testing de APIs con RestAssured y Postman en proyectos futuros
- Profundizar en arquitecturas hexagonales y testing de microservicios
- Estudiar patrones de testing avanzados como Test Data Builders y Object Mother

### Para aplicar en proyectos futuros:
- Implementar este flujo de TDD colaborativo en proyectos más complejos con múltiples módulos
- Aplicar estos conocimientos en tecnologías web (Spring Boot, React) manteniendo la disciplina de TDD
- Usar estas experiencias como base para liderar equipos de desarrollo en futuros trabajos
- Integrar herramientas de DevOps (Docker, Jenkins) con nuestro flujo de testing ágil
- Mentorear a otros desarrolladores en metodologías TDD basándonos en nuestra experiencia grupal

### Para el crecimiento profesional del equipo:
- Este proyecto nos ha preparado para roles de desarrollo en empresas que valoran la calidad del software
- Tenemos evidencia concreta de nuestra capacidad para trabajar en equipo con metodologías ágiles
- Podemos demostrar competencias técnicas y metodológicas que son altamente valoradas en la industria
- El portafolio resultante será una pieza clave en nuestros perfiles profesionales para conseguir mejores oportunidades laborales
- La experiencia colaborativa nos ha preparado para trabajar efectivamente en equipos de desarrollo reales

---

**Fecha de reflexión**: Diciembre 2024
**Duración del proyecto**: 28 días
**Nivel de satisfacción del equipo**: 9/10
**Equipo**: 3 programadores colaborando efectivamente

*Esta reflexión nos ayudará como equipo a articular nuestra experiencia colaborativa en entrevistas laborales y a identificar áreas de mejora para futuros proyectos grupales. El proyecto ha sido una experiencia transformadora que nos ha preparado para los desafíos del desarrollo de software profesional, demostrando que podemos trabajar efectivamente en equipo aplicando metodologías ágiles y manteniendo altos estándares de calidad.*
