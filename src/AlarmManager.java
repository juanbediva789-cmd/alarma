package src;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AlarmManager {
    private List<Alarm> alarms;
    private boolean vacationMode;

    // Constructor: inicializa la lista de alarmas vacía y el modo vacaciones apagado
    public AlarmManager() {
        this.alarms = new ArrayList<>();
        this.vacationMode = false;
    }

    // Añade una nueva alarma al sistema
    public void addAlarm(Alarm alarm) {
        alarms.add(alarm);
        System.out.println("➕ Alarma añadida con éxito: [" + alarm.getLabel() + "] a las " + alarm.getTime());
    }

    // Elimina una alarma buscando por su identificador único (UUID)
    public void removeAlarm(UUID id) {
        boolean removed = alarms.removeIf(alarm -> alarm.getId().equals(id));
        if (removed) {
            System.out.println("🗑️ Alarma con ID " + id + " eliminada correctamente.");
        } else {
            System.out.println("❌ No se encontró ninguna alarma con el ID especificado.");
        }
    }

    // Activa o desactiva el Modo Vacaciones
    public void setVacationMode(boolean vacationMode) {
        this.vacationMode = vacationMode;
        System.out.println("🌴 Modo Vacaciones cambiado a: " + (vacationMode ? "ACTIVADO (Las alarmas no sonarán)" : "DESACTIVADO"));
    }

    // Muestra por consola todas las alarmas guardadas en el sistema
    public void listAlarms() {
        System.out.println("\n--- 📋 LISTA DE ALARMAS ACTUALES ---");
        if (alarms.isEmpty()) {
            System.out.println("No hay alarmas configuradas.");
            return;
        }
        for (Alarm alarm : alarms) {
            String estado = alarm.isActive() ? "ACTIVA" : "INACTIVA";
            String reto = alarm.hasChallenge() ? "Con Reto Matemático" : "Sin Reto";
            System.out.println("- [" + alarm.getCategory() + "] " + alarm.getLabel() + " - " + alarm.getTime() + " (" + estado + " | " + reto + ")");
        }
        System.out.println("------------------------------------\n");
    }

    // Getters necesarios
    public List<Alarm> getAlarms() { return alarms; }
    public boolean isVacationMode() { return vacationMode; }
}