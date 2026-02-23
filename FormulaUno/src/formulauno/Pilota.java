package formulauno;
import java.util.Random;
/**
 *
 * @author renzetti.alessandro
 */
public class Pilota implements Runnable {
    private String nome;
    private double distanzaPercorsa;
    private double lunghezzaPercorso;
    private boolean inGara;
    private Random random;
    private Thread thread;
    
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
    
    public void start() {
        thread = new Thread(this);
        thread.start();
    }
    
    public void ferma() {
        inGara = false;
        if (thread != null) {
            thread.interrupt();
        }
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
