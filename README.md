# alarma
usecaseDiagram
    actor Usuario
    
    package "Sistema de Alarma Inteligente" {
        usecase "Gestionar Alarmas" as UC1
        usecase "Crear Alarma" as UC1_1
        usecase "Eliminar Alarma" as UC1_2
        usecase "Activar/Desactivar Alarma" as UC1_3
        
        usecase "Sonar Alarma" as UC2
        usecase "Posponer Alarma (Snooze)" as UC3
        usecase "Detener Alarma" as UC4
        usecase "Resolver Reto Matemático" as UC5
        usecase "Activar Modo Vacaciones" as UC6
        usecase "Consultar Próximas Alarmas" as UC7
    }

    Usuario --> UC1
    Usuario --> UC3
    Usuario --> UC4
    Usuario --> UC6
    Usuario --> UC7
    
    UC1 ..> UC1_1 : <<include>>
    UC1 ..> UC1_2 : <<include>>
    UC1 ..> UC1_3 : <<include>>
    
    UC4 <.. UC5 : <<extend>>
    UC2 ..> UC3 : <<extend>>

    classDiagram
    class AlarmManager {
        -List~Alarm~ alarms
        -boolean vacationMode
        +addAlarm(Alarm) void
        +removeAlarm(UUID) void
        +toggleVacationMode() void
        +getNextActiveAlarms() List~Alarm~
        +checkAndTriggerAlarms(LocalDateTime) void
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
        +snooze(int minutes) void
        +stop() void
        +isTriggerTime(LocalDateTime) boolean
    }

    class RepeatDays {
        -EnumSet~DayOfWeek~ days
        +addDay(DayOfWeek) void
        +removeDay(DayOfWeek) void
        +isActiveOn(DayOfWeek) boolean
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
        +calculateNextTrigger(LocalTime) LocalTime
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
        -Difficulty level
        +generateProblem() String
        +verifyAnswer(int) boolean
    }

    AlarmManager "1" *-- "0..*" Alarm : gestiona
    Alarm "1" *-- "1" RepeatDays : tiene
    Alarm "1" *-- "1" SoundProfile : reproduce
    Alarm "1" *-- "0..1" SnoozeManager : permite
    Alarm "1" o-- "1" Category : pertenece a
    Alarm "1" *-- "0..1" MathChallenge : requiere
