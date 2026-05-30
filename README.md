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
