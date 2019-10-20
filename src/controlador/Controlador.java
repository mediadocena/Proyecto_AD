/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Utiles.Salieri;
import Vista.Vista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.Interfaz;
import modelo.Libro;
import modelo.Persona;
import modelo.Prestamos;

/**
 *
 * @author aleja
 */
public class Controlador implements ActionListener{
    private Vista vista;
    Prestamos p = new Prestamos();
    Persona usuario = new Persona();
    Libro libro = new Libro();
    private Interfaz interfaz;
    Salieri ex = new Salieri();

    @Override
    public void actionPerformed(ActionEvent ae) {
       switch(AccionMVC.valueOf(ae.getActionCommand())){
           
       case __InsertarUsuario:
           
          String nombre = vista.Nombre_Usuario.getText();
          String ciudad = vista.Ciudad_Usuario.getText();
          int cod = Integer.parseInt(vista.CodPost_Usuario.getText());
          String calle =  vista.Calle_Usuario.getText();
       
           try {
               usuario.insertarUsuario(nombre, calle, ciudad, cod);
           } catch (SQLException ex) {
               ex.printStackTrace();
           }
       
        this.vista.Table_Usuarios.setModel(usuario.listarUsuarios());
           break;
           
       case __InsertarLibro:
            String titulo = vista.Titulo_Libro.getText();
          String genero = vista.Genero_Libro.getText();
          String autor= vista.Autor_Libro.getText();
          String editor =  vista.Editor_Libro.getText();
          int clase= Integer.parseInt(vista.Clase_Libro.getText());
       
           try {
               libro.insertarLibro(titulo, editor, autor, genero,clase);
           } catch (SQLException ex) {
               Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
           }
       
         this.vista.Table_Libros.setModel(libro.listarLibros());
           break;
       case __BorrarUsuario:
           int id = Integer.parseInt(vista.ID_Borrar_Usuario.getText());
       {
           try {
               usuario.BorrarUsuario(id);
           } catch (SQLException ex) {
               Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
      this.vista.Table_Usuarios.setModel(usuario.listarUsuarios());
           break;
       case __BorrarLibro:
            id = Integer.parseInt(vista.ID_Borrar_Libro.getText());
       {
           try {
               libro.BorrarLibro(id);
           } catch (SQLException ex) {
               Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       this.vista.Table_Libros.setModel(libro.listarLibros());
           break;
       case __RealizarPrestamo:
            int nuser =Integer.parseInt( vista.Numero_Usuario_Prestamo.getText());
            int nlib =Integer.parseInt( vista.Codigo_Libro_Prestamo.getText());
       {
           try {
               p.insertarPrestamo(nuser, nlib);
           } catch (SQLException ex) {
               Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       this.vista.Table_Prestamos.setModel(p.listarPrestamos());
       this.vista.Table_Historico.setModel(p.listarHistorico());
          this.vista.Table_Libros.setModel(libro.listarLibros());
         this.vista.Table_Usuarios.setModel(usuario.listarUsuarios());
           break;
       case __DevolverLibro:
           id = Integer.parseInt(vista.ID_Devolver_Libro.getText());
       {
           try {
               p.DevolverLibro(id);
           } catch (SQLException ex) {
               Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
            this.vista.Table_Prestamos.setModel(p.listarPrestamos());
       this.vista.Table_Historico.setModel(p.listarHistorico());
       this.vista.Table_Libros.setModel(libro.listarLibros());
         this.vista.Table_Usuarios.setModel(usuario.listarUsuarios());
           break;
       }
    }
    
    public enum AccionMVC{
    __InsertarUsuario,__InsertarLibro,__BorrarUsuario,__BorrarLibro,__RealizarPrestamo,__DevolverLibro
    
    }
    
    Controlador(Vista vista) {
      this.vista = new Vista();
     // this.interfaz = new Interfaz(vista);
    }
    
    public void iniciar(){
        this.vista.Table_Usuarios.setModel(usuario.listarUsuarios());
         this.vista.Table_Libros.setModel(libro.listarLibros());
         this.vista.Table_Prestamos.setModel(p.listarPrestamos());
         this.vista.Table_Historico.setModel(p.listarHistorico());
        vista.setVisible(true);
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(vista);
            vista.setVisible(true);
        }catch(UnsupportedLookAndFeelException ex){}
        catch(ClassNotFoundException ex){}
        catch(InstantiationException ex){}
        catch(IllegalAccessException ex){}
        
      this.vista.Insert_user.setActionCommand("__InsertarUsuario");
      this.vista.Insert_user.addActionListener(this);
      
      this.vista.Insert_libro.setActionCommand("__InsertarLibro");
      this.vista.Insert_libro.addActionListener(this);
     
      this.vista.Insert_prestamos.setActionCommand("__RealizarPrestamo");
      this.vista.Insert_prestamos.addActionListener(this);
      
      this.vista.Borrar_Libro.setActionCommand("__BorrarLibro");
      this.vista.Borrar_Libro.addActionListener(this);
      
      this.vista.Borrar_Usuario.setActionCommand("__BorrarUsuario");
      this.vista.Borrar_Usuario.addActionListener(this);
      
      this.vista.Devolver_Libro.setActionCommand("__DevolverLibro");
      this.vista.Devolver_Libro.addActionListener(this);
      
      
      
    }
 
}
