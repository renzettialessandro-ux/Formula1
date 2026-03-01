/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package formulauno;

import java.util.ArrayList;
import java.util.List;

/**
 * Si occupa di creare i piloti, avviare la gara e fermarla quando necessario.
 * @author renzetti.alessandro
 */
public class Gara {
    private List<Pilota> piloti;
    private double lunghezzaPercorso;
    
    public Gara(double lunghezzaPercorso) {
        this.lunghezzaPercorso = lunghezzaPercorso;
        this.piloti = new ArrayList<>();
    }
    
    public void aggiungiPilota(String nome) {
        Pilota pilota = new Pilota(nome, lunghezzaPercorso);
        piloti.add(pilota);
    }
    
    public void avvia() {
        System.out.println("=== INIZIO GARA ===");
        for (Pilota pilota : piloti) {
            pilota.start();
        }
    }
    
    public void ferma() {
        for (Pilota pilota : piloti) {
            pilota.ferma();
        }
    }
    
    public List<Pilota> getPiloti() {
        return piloti;
    }
}
