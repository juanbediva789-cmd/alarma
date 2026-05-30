# ⏰ Sistema de Despertador Inteligente (Lógica en Java)

## 1. Descripción del proyecto
Este proyecto consiste en el diseño e implementación de la lógica interna (back-end) en Java para una aplicación de despertador inteligente. Simula las capacidades de los smartphones modernos sin depender de una interfaz gráfica, centrándose exclusivamente en la robustez de la lógica de negocio, la gestión del tiempo y la arquitectura de software.

Se han implementado funcionalidades estándar (crear, editar, posponer alarmas) y funcionalidades avanzadas como **Modo Vacaciones**, **Categorización de Alarmas** y **Retos Matemáticos** para apagar la alarma.

## 2. Objetivos
- Diseñar software robusto orientado a objetos aplicando principios SOLID.
- Implementar la lógica de negocio totalmente desacoplada de cualquier interfaz visual.
- Analizar requisitos funcionales y traducirlos a especificaciones técnicas.
- Gestionar un flujo de trabajo profesional con Git y GitHub mediante el uso de ramas (`feature/`, `develop`, `main`).
- Utilizar herramientas de IA de forma responsable como asistencia al desarrollo y documentar su impacto.

## 3. Tecnologías utilizadas
- **Lenguaje:** Java (JDK 17 o superior)
- **Control de Versiones:** Git y GitHub
- **Modelado UML:** Mermaid (nátivo en GitHub Markdown)
- **Librerías clave:** `java.time` (para la gestión precisa de horas y fechas), `java.util.UUID` (identificadores únicos).

## 4. Instalación y ejecución
Dado que el proyecto carece de interfaz gráfica, la interacción se realiza a través de la consola ejecutando la clase `Main`.

```bash
# 1. Clonar el repositorio
git clone [https://github.com/TU_USUARIO/TU_REPOSITORIO.git](https://github.com/TU_USUARIO/TU_REPOSITORIO.git)

# 2. Navegar al directorio del proyecto
cd TU_REPOSITORIO

# 3. Compilar el código fuente
javac src/*.java

# 4. Ejecutar el programa principal
java src.Main

/src          # Código fuente de la lógica en Java (clases y Main)
/docs         # Documentación adicional (si procede)
/tests        # Pruebas de funcionamiento
README.md     # Este archivo de documentación técnica

El sistema se ha diseñado bajo el principio de alta cohesión y bajo acoplamiento (SRP - Single Responsibility Principle):

AlarmManager: Actúa como el controlador principal. Su responsabilidad es mantener el registro global de alarmas, verificar el tiempo del sistema y determinar qué alarmas deben dispararse.

Alarm: Es la entidad principal, pero delega funciones. Conoce su hora y su estado, pero no sabe cómo reproducir sonido ni cómo posponerse.

SnoozeManager y SoundProfile: Componentes inyectados en la alarma mediante composición. Tienen ciclos de vida ligados a la alarma y gestionan la lógica específica de posponer (contadores de snooze) y el volumen/archivo de audio.

Encapsulación: Todos los atributos de las clases son estrictamente privados (-). Las modificaciones de estado (como activar/desactivar una alarma) se realizan exclusivamente a través de métodos públicos seguros como toggle().

classDiagram
    class AlarmManager {
        -Alarm[] alarms
        -boolean vacationMode
        +addAlarm(Alarm alarm) void
        +removeAlarm(UUID id) void
        +toggleVacationMode() void
        +getNextActiveAlarms() Alarm[]
        +checkAndTriggerAlarms(LocalDateTime now) void
    }

    class Alarm {
        -UUID id
        -LocalTime time
        -String label
        -boolean isActive
        -RepeatDays repeatDays
        -SoundProfile sound
        -Category category
        -MathChallenge challenge
        +toggle() void
        +snooze() void
        +stop() void
        +isTriggerTime(LocalDateTime now) boolean
    }

    class RepeatDays {
        -DayOfWeek[] days
        +addDay(DayOfWeek day) void
        +removeDay(DayOfWeek day) void
        +isActiveOn(DayOfWeek day) boolean
    }

    class SoundProfile {
        -String soundFile
        -int volumeLevel
        +play() void
    }

    class SnoozeManager {
        -int snoozeCount
        -int maxSnoozes
        -int snoozeDurationMinutes
        +calculateNextTrigger(LocalTime currentTime) LocalTime
        +canSnooze() boolean
    }

    class Category {
        <<enumeration>>
        WORK
        STUDY
        SPORTS
        MEDICINE
        GENERAL
    }

    class MathChallenge {
        -String problem
        -int correctAnswer
        +generateProblem() String
        +verifyAnswer(int answer) boolean
    }

    AlarmManager "1" *-- "0..*" Alarm : gestiona
    Alarm "1" *-- "1" RepeatDays : posee
    Alarm "1" *-- "1" SoundProfile : reproduce
    Alarm "1" *-- "0..1" SnoozeManager : controla
    Alarm "1" --> "1" Category : clasificada en
    Alarm "1" *-- "0..1" MathChallenge : requiere

flowchart LR
    Usuario((Usuario))

    subgraph Sistema ["Sistema de Alarma Inteligente"]
        UC1(Gestionar Alarmas)
        UC1_1(Crear Alarma)
        UC1_2(Eliminar Alarma)
        UC1_3(Activar/Desactivar Alarma)
        
        UC2(Sonar Alarma)
        UC3(Posponer Alarma - Snooze)
        UC4(Detener Alarma)
        UC5(Resolver Reto Matemático)
        UC6(Activar Modo Vacaciones)
        UC7(Consultar Próximas Alarmas)
    end

    Usuario --> UC1
    Usuario --> UC3
    Usuario --> UC4
    Usuario --> UC6
    Usuario --> UC7

    UC1 -.->|include| UC1_1
    UC1 -.->|include| UC1_2
    UC1 -.->|include| UC1_3

    UC5 -.->|extend| UC4
    UC3 -.->|extend| UC2


## 9. Especificación de casos de uso

A continuación, se detalla la lógica de interacción de los procesos más importantes del sistema, siguiendo la plantilla de especificación funcional requerida.

### Caso de Uso 1: Crear Alarma
* **Nombre:** Crear Alarma.
* **Objetivo:** Permitir al usuario configurar y guardar una nueva alarma en el sistema, definiendo su hora, repetición y comportamiento.
* **Actor principal:** Usuario.
* **Precondiciones:** El sistema principal (`AlarmManager`) debe estar inicializado y ejecutándose correctamente en memoria.
* **Flujo principal:**
  1. El usuario inicia la acción para crear una nueva alarma.
  2. El sistema solicita que se introduzca la hora y los minutos.
  3. El usuario introduce el tiempo deseado (por ejemplo, `07:30`).
  4. El sistema pide los parámetros adicionales: etiqueta identificativa, días de repetición y categoría (Trabajo, Deporte, etc.).
  5. El usuario proporciona todos los datos solicitados.
  6. El sistema crea una nueva instancia de la clase `Alarm`, le asigna un identificador único autogenerado (UUID) y la almacena en la lista de alarmas del sistema.
  7. El sistema muestra un mensaje por consola confirmando que la alarma ha sido guardada exitosamente.
* **Flujos alternativos:**
  * *3a. Formato de hora incorrecto:* Si el usuario introduce una hora no válida (ej. `25:99`), el sistema muestra un mensaje de error advirtiendo del formato incorrecto y vuelve al paso 2 para solicitar la hora nuevamente.
  * *5a. Omisión de días de repetición:* Si el usuario no selecciona ningún día de la semana específico, el sistema asume una alarma eventual y la configura para que suene una única vez dentro de las próximas 24 horas.
* **Postcondiciones:** La nueva alarma queda registrada de forma segura en la memoria del sistema y programada con su estado inicial activo (`isActive = true`).
* **Reglas de negocio:** * La etiqueta descriptiva de la alarma no puede superar los 50 caracteres para evitar desbordamientos de memoria. 
  * Si el usuario no especifica un perfil de sonido, el sistema asignará uno por defecto con el volumen ajustado al 50%.

---

### Caso de Uso 2: Detener Alarma con Reto Matemático
* **Nombre:** Detener Alarma mediante Reto Matemático.
* **Objetivo:** Asegurar el despertar efectivo del usuario exigiéndole la resolución mental de una operación matemática antes de permitirle silenciar el dispositivo.
* **Actor principal:** Usuario.
* **Precondiciones:** Una alarma debe haber alcanzado su hora de activación, encontrarse en estado "Sonando", y tener vinculada la funcionalidad avanzada `MathChallenge`.
* **Flujo principal:**
  1. El usuario interactúa con el sistema intentando detener la alarma que está sonando.
  2. El sistema comprueba la configuración interna y detecta que el reto matemático está activado para esta alarma en particular.
  3. El sistema genera una operación matemática aleatoria según la dificultad (ej. `14 + 27`) y la muestra por pantalla.
  4. El usuario calcula mentalmente y teclea la respuesta.
  5. El sistema valida que la respuesta introducida coincide con el resultado correcto, silencia inmediatamente el perfil de sonido (`SoundProfile`) y desactiva el disparador actual de la alarma.
* **Flujos alternativos:**
  * *4a. Respuesta incorrecta:* Si el usuario introduce un resultado numérico erróneo, el sistema notifica el fallo, mantiene el sonido de la alarma activo a volumen máximo y genera automáticamente una nueva operación matemática para intentarlo de nuevo.
* **Postcondiciones:** La alarma queda completamente silenciada. Si tiene una repetición semanal programada (ej. de lunes a viernes), el sistema la deja a la espera del próximo día correspondiente; de lo contrario, su estado general pasa a inactivo.
* **Reglas de negocio:** * Una vez que la alarma comienza a sonar bajo esta configuración, el sistema bloquea cualquier otra forma de apagarla (incluso la función de posponer o *Snooze* queda deshabilitada) hasta que el problema matemático sea resuelto con éxito.


Durante el desarrollo de esta práctica, se tomaron varias decisiones arquitectónicas importantes:

Gestión del tiempo: Se optó por utilizar LocalTime en la clase Alarm ya que las alarmas generalmente se configuran para una hora del día, independientemente de la fecha. La comparación temporal real se realiza en el AlarmManager usando LocalDateTime para manejar correctamente el paso de los días y las repeticiones semanales.

Identificadores únicos: Para evitar conflictos entre alarmas duplicadas (ej: dos alarmas a las 07:00), se implementó java.util.UUID, asegurando que el borrado o edición afecte exactamente a la instancia correcta.

Deuda técnica futura: Al carecer de una base de datos, las alarmas residen en memoria RAM. Una mejora futura fundamental sería implementar un patrón de persistencia (como guardar los datos en un archivo JSON) para que las alarmas sobrevivan al cierre del programa.


En este proyecto, se utilizó IA generativa (Gemini) principalmente como herramienta de asistencia para la arquitectura de software y documentación:

Uso de la IA: Se utilizó para generar la sintaxis de los diagramas Mermaid, ya que la documentación oficial puede ser propensa a errores tipográficos. También se empleó como "sparring" para validar el diseño de clases y discutir si una relación debía ser de composición o agregación.

Prompts destacados: "Genera el código Mermaid para un diagrama de casos de uso basado en estos requisitos..." y "Revisa esta estructura de clases, ¿estoy rompiendo el principio de Responsabilidad Única?"

Errores y validación manual: La IA inicialmente sugirió diagramas con una sintaxis que no era 100% compatible con el renderizador nativo de GitHub (errores al usar genéricos como List<Alarm>). Tuve que iterar y modificar manualmente el código Mermaid para usar arrays clásicos (Alarm[]) logrando que se visualizara correctamente en el repositorio.

Aprendizaje: Comprobé que la IA es excelente para estructurar ideas ("scaffolding"), pero la lógica de negocio final, la resolución de bugs en el IDE y la compilación dependieron íntegramente de mi comprensión del lenguaje Java.



    UC3 -.->|extend| UC2



