# alarma
```mermaid
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
