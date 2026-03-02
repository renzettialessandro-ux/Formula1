package formulauno;

import java.util.Random;

/**
 * Ogni pilota viene eseguito in un thread separato e avanza lungo il percorso
 * in modo casuale, simulando il comportamento reale di una gara.
 * Gestisce anche eventi speciali (DNF, pioggia, safety car, ala rotta)
 * e la sosta ai box per il cambio gomme.
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

    /**
     * Imposta il nome del pilota controllato dal giocatore.
     * Serve per applicare comportamenti differenti al pilota umano (es. sosta ai box manuale).
     * @param nomeGiocatore il nome del pilota scelto dall'utente
     */
    public void setNomeGiocatore(String nomeGiocatore) {
        this.nomeGiocatore = nomeGiocatore;
    }

    /**
     * Restituisce se il pilota ha gia' effettuato la sosta ai box.
     * @return true se la sosta e' gia' stata effettuata, false altrimenti
     */
    public boolean isBoxFatto() {
        return boxFatto;
    }

    /**
     * Imposta lo stato della sosta ai box del pilota.
     *
     * @param boxFatto true se la sosta e' stata effettuata, false altrimenti
     */
    public void setBoxFatto(boolean boxFatto) {
        this.boxFatto = boxFatto;
    }

    /**
     * Applica un evento speciale al pilota che influenzera' il suo comportamento
     * nel prossimo ciclo di esecuzione.
     *
     * @param evento l'evento da applicare (es. DNF, RAIN, ALA_ROTTA, SAFETY_CAR)
     */
    public void applicaEvento(Eventi evento) {
        this.eventoAttivo = evento;
    }

    /**
     * Esegue la logica di avanzamento del pilota durante la gara.
     * Viene chiamato automaticamente quando il thread del pilota viene avviato.
     * Gestisce l'avanzamento casuale, gli eventi speciali, il degrado delle gomme
     * e la sosta automatica ai box per i piloti CPU.
     */
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

            
            double incremento = (0.5 * (0.5 + random.nextDouble() * 1.5)* (100.0 / lunghezzaPercorso)* fattoreGomme)*0.75;
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

    /**
     * Avvia il thread del pilota, dando inizio alla sua corsa.
     */
    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Ferma il pilota interrompendo il suo thread.
     * Viene chiamato quando la gara viene riavviata o fermata manualmente.
     */
    public void ferma() {
        inGara = false;
        if (thread != null) {
            thread.interrupt();
        }
    }

    /**
     * Restituisce il nome del pilota.
     * @return il nome del pilota
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce la distanza percorsa dal pilota dall'inizio della gara.
     * @return la distanza percorsa
     */
    public double getDistanzaPercorsa() {
        return distanzaPercorsa;
    }

    /**
     * Restituisce la percentuale del percorso completata dal pilota.
     * @return un valore tra 0.0 e 100.0 che rappresenta la percentuale completata
     */
    public double getPercentuale() {
        return (distanzaPercorsa / lunghezzaPercorso) * 100.0;
    }

    /**
     * Indica se il pilota e' attualmente in gara.
     * @return true se il pilota sta ancora correndo, false se ha terminato o si e' ritirato
     */
    public boolean isInGara() {
        return inGara;
    }
}
