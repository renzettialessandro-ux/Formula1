/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package formulauno;

import javax.swing.SwingUtilities;

/**
 *
 * @author renzetti.alessandro
 */
public class FormulaUno {

    /**
     * Avvia il menu principale
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SwingUtilities.invokeLater(() -> {
            FrmMenu fm=new FrmMenu();
            fm.setVisible(true);
        });
    }
    
}
