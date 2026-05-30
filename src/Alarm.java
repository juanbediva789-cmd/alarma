package src;

import java.time.LocalTime;
import java.util.UUID;

public class Alarm {
    private UUID id;
    private LocalTime time;
    private String label;
    private boolean isActive;
    private SoundProfile sound;
    private Category category;
    private MathChallenge challenge;

    // Constructor principal
    public Alarm(LocalTime time, String label, Category category, SoundProfile sound, MathChallenge challenge) {
        this.id = UUID.randomUUID(); // Genera un identificador único automáticamente
        this.time = time;
        this.label = label;
        this.category = category;
        this.sound = sound;
        this.challenge = challenge;
        this.isActive = true; // Por defecto, al crearla está encendida
    }

    // Método para activar/desactivar la alarma (Toggle)
    public void toggle() {
        this.isActive = !this.isActive;
        System.out.println("⏰ Alarma [" + label + "] cambiada a: " + (this.isActive ? "ACTIVA" : "INACTIVA"));
    }

    // Getters y Setters para controlar los datos de forma segura
    public UUID getId() { return id; }
    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }
    public SoundProfile getSound() { return sound; }
    public Category getCategory() { return category; }
    public MathChallenge getChallenge() { return challenge; }
    
    // Permite saber si la alarma tiene un reto matemático asignado
    public boolean hasChallenge() {
        return this.challenge != null;
    }
}