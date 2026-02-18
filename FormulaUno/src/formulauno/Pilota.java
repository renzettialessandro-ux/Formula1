/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package formulauno;
import java.util.Random;

/**
 *
 * @author renzetti.alessandro
 */
public class Pilota extends Thread {
    private String nome;
    private double distanzaPercorsa;
    private double lunghezzaPercorso;
    private boolean inGara;
    private Random random;
    
    public Pilota(String nome, double lunghezzaPercorso) {
        this.nome = nome;
        this.lunghezzaPercorso = lunghezzaPercorso;
        this.distanzaPercorsa = 0;
        this.inGara = false;
        this.random = new Random();
    }
    
    @Override
    public void run() {
        inGara = true;
        while (distanzaPercorsa < lunghezzaPercorso && inGara) {
            // Incremento casuale della distanza
            double incremento = 0.5 + random.nextDouble() * 1.5;
            distanzaPercorsa += incremento;
            
            if (distanzaPercorsa > lunghezzaPercorso) {
                distanzaPercorsa = lunghezzaPercorso;
            }
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                inGara = false;
            }
        }
        
        inGara = false;
        System.out.println(nome + " ha terminato la gara!");
    }
    
    public void ferma() {
        inGara = false;
        interrupt();
    }
    
    public String getNome() {
        return nome;
    }
    
    public double getDistanzaPercorsa() {
        return distanzaPercorsa;
    }
    
    public double getPercentuale() {
        return (distanzaPercorsa / lunghezzaPercorso) * 100.0;
    }
    
    public boolean isInGara() {
        return inGara;
    }
}
