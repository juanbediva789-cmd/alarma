package src;

import java.util.Random;

public class MathChallenge {
    private String problem;
    private int correctAnswer;

    // Al crear el reto, se genera automáticamente el primer problema
    public MathChallenge() {
        generateProblem();
    }

    // Genera una suma aleatoria entre números de dos dígitos
    public String generateProblem() {
        Random random = new Random();
        int a = random.nextInt(20) + 10; // Número aleatorio entre 10 y 30
        int b = random.nextInt(20) + 10; // Número aleatorio entre 10 y 30
        
        this.correctAnswer = a + b;
        this.problem = a + " + " + b;
        return this.problem;
    }

    // Verifica si la respuesta que mete el usuario es correcta
    public boolean verifyAnswer(int answer) {
        return this.correctAnswer == answer;
    }

    // Getter para mostrar el problema por pantalla
    public String getProblem() { 
        return problem; 
    }
}