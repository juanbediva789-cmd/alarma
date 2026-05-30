package src;

import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AlarmManager manager = new AlarmManager();

        System.out.println("=================================================");
        System.out.println("⏰ BIENVENIDO AL SISTEMA DE DESPERTADOR INTELIGENTE");
        System.out.println("=================================================");

        // 1. Creación de perfiles de sonido
        SoundProfile sonidoSuave = new SoundProfile("acoustic_guitar.mp3", 40);
        SoundProfile sonidoFuerte = new SoundProfile("heavy_metal_alarm.wav", 90);

        // 2. Creación de alarmas (Una normal y otra con reto matemático)
        System.out.println("\n--- [PASO 1] Creando e instalando alarmas de prueba ---");
        
        Alarm alarmaGimnasio = new Alarm(
            LocalTime.of(7, 0), 
            "Ir al gimnasio 🏋️", 
            Category.SPORTS, 
            sonidoSuave, 
            null // Sin reto matemático
        );

        Alarm alarmaTrabajo = new Alarm(
            LocalTime.of(8, 30), 
            "Entrar a trabajar 💻", 
            Category.WORK, 
            sonidoFuerte, 
            new MathChallenge() // ¡Esta requiere reto para apagarse!
        );

        // Añadimos las alarmas al cerebro del sistema
        manager.addAlarm(alarmaGimnasio);
        manager.addAlarm(alarmaTrabajo);

        // 3. Listar las alarmas para verificar que todo está guardado
        manager.listAlarms();

        // 4. SIMULACIÓN: ¿Qué pasa cuando suena la alarma con reto matemático?
        System.out.println("--- [PASO 2] Simulando disparo de Alarma con Reto Matemático ---");
        System.out.println("¡BEEP BEEP BEEP! La alarma [" + alarmaTrabajo.getLabel() + "] está sonando.");
        alarmaTrabajo.getSound().play();

        if (alarmaTrabajo.hasChallenge()) {
            System.out.println("\n⚠️ ¡SISTEMA ANTIPEREZA ACTIVO! Resuelve el reto para apagar la alarma:");
            MathChallenge reto = alarmaTrabajo.getChallenge();
            
            boolean retoResuelto = false;
            while (!retoResuelto) {
                System.out.print("¿Cuánto es " + reto.getProblem() + "? Respuesta: ");
                
                // Validación para evitar que el programa explote si el usuario no mete un número
                if (scanner.hasNextInt()) {
                    int respuestaUsuario = scanner.nextInt();
                    
                    if (reto.verifyAnswer(respuestaUsuario)) {
                        System.out.println("\n✅ ¡Respuesta CORRECTA! Te has despertado con éxito.");
                        retoResuelto = true;
                    } else {
                        System.out.println("❌ Respuesta INCORRECTA. La alarma sigue sonando... ¡Inténtalo de nuevo!");
                        reto.generateProblem(); // Genera un problema nuevo para fastidiar un poco más
                    }
                } else {
                    System.out.println("⚠ Por favor, introduce un número válido.");
                    scanner.next(); // Limpiar el buffer
                }
            }
        }

        // 5. SIMULACIÓN: Probar el Modo Vacaciones
        System.out.println("\n--- [PASO 3] Probando el Modo Vacaciones ---");
        manager.setVacationMode(true);
        
        System.out.println("\n=================================================");
        System.out.println("🎉 SIMULACIÓN FINALIZADA CON ÉXITO");
        System.out.println("=================================================");
        scanner.close();
    }
}