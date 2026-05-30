```mermaid
flowchart LR
    %% Definición del Actor
    Usuario((Usuario))

    %% Límite del Sistema
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

    %% Relaciones del Actor con los Casos de Uso
    Usuario --> UC1
    Usuario --> UC3
    Usuario --> UC4
    Usuario --> UC6
    Usuario --> UC7

    %% Relaciones Include y Extend
    UC1 -.->|include| UC1_1
    UC1 -.->|include| UC1_2
    UC1 -.->|include| UC1_3
    
    UC5 -.->|extend| UC4
    UC3 -.->|extend| UC2

```mermaid
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
