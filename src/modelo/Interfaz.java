/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Vista.Vista;
import java.awt.Component;

/**
 *
 * @author aleja
 */
public class Interfaz {
    Vista interfaces = null;
    
    public Interfaz(Vista interfaces){
        this.interfaces = interfaces;
    }
    public void Cambio(Component c){
        interfaces.Contenedor.removeAll();
        interfaces.Contenedor.add(c);
        interfaces.Contenedor.repaint();
        interfaces.Contenedor.revalidate();
        interfaces.pack();
    }
}
