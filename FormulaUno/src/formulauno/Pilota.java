package formulauno;

import java.util.Random;

/**
 *
 * @author renzetti.alessandro
 */
public class Pilota implements Runnable {

    private String nome;
    private String nomeGiocatore;
    private double distanzaPercorsa;
    private double lunghezzaPercorso;
    private boolean inGara;
    private boolean boxFatto = false;
    private Random random;
    private Thread thread;
    private Eventi eventoAttivo = null;

    public Pilota(String nome, double lunghezzaPercorso) {
        this.nome = nome;
        this.lunghezzaPercorso = lunghezzaPercorso;
        this.distanzaPercorsa = 0;
        this.inGara = false;
        this.random = new Random();
    }

    public void setNomeGiocatore(String nomeGiocatore) {
        this.nomeGiocatore = nomeGiocatore;
    }

    public boolean isBoxFatto() {
        return boxFatto;
    }

    public void setBoxFatto(boolean boxFatto) {
        this.boxFatto = boxFatto;
    }

    public void applicaEvento(Eventi evento) {
        this.eventoAttivo = evento;
    }

    @Override
    public void run() {
        inGara = true;
        boolean pilotaGiocatore = nome.equals(nomeGiocatore);

        while (distanzaPercorsa < lunghezzaPercorso && inGara) {

            // gestione eventi
            if (eventoAttivo != null) {
                switch (eventoAttivo) {
                    case DNF:
                        inGara = false;
                        System.out.println(nome + " ritirato per DNF!");
                        eventoAttivo = null;
                        continue;
                    case RAIN:
                    case SAFETY_CAR:
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            inGara = false;
                        }
                        eventoAttivo = null;
                        break;
                    case ALA_ROTTA:
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            inGara = false;
                        }
                        eventoAttivo = null;
                        break;
                }
            }

            double fattoreGomme = 1.0;

            if (distanzaPercorsa > lunghezzaPercorso * 0.5 && !boxFatto) {
                if (pilotaGiocatore) {
                    fattoreGomme = 0.25;
                } else {
                    fattoreGomme = 0.25;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        inGara = false;
                    }
                    boxFatto = true;
                }
            }

            double incremento = 0.5 * (0.5 + random.nextDouble() * 1.5)
                    * (100.0 / lunghezzaPercorso)
                    * fattoreGomme;
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
