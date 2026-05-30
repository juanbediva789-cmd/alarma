package src;

public class SoundProfile {
    private String soundFile;
    private int volumeLevel;

    // Constructor para inicializar el perfil de sonido
    public SoundProfile(String soundFile, int volumeLevel) {
        this.soundFile = soundFile;
        // Validación: Si el volumen no está entre 0 y 100, se pone al 50% por defecto
        this.volumeLevel = (volumeLevel >= 0 && volumeLevel <= 100) ? volumeLevel : 50;
    }

    // Simula la reproducción del sonido por consola
    public void play() {
        System.out.println("🎵 Reproduciendo: " + soundFile + " [Volumen: " + volumeLevel + "%]");
    }

    // Métodos Getters y Setters necesarios para la lógica
    public String getSoundFile() { 
        return soundFile; 
    }

    public int getVolumeLevel() { 
        return volumeLevel; 
    }

    public void setVolumeLevel(int volumeLevel) { 
        if (volumeLevel >= 0 && volumeLevel <= 100) {
            this.volumeLevel = volumeLevel; 
        }
    }
}