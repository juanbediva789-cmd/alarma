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

