/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author aleja
 */
public class Libro {
    int cod_libro;
    int clase;
    int prestado;
    String genero;
    String autor;
    String titulo;
    String editor;
    Connection con;
    CallableStatement cstmt =null;
    int tamano =0;

    public Libro(int cod_libro, int clase, int prestado, String genero,String autor,String titulo, String editor) {
        this.cod_libro=cod_libro;
        this.clase=clase;
        this.prestado=prestado;
        this.genero=genero;
        this.autor=autor;
        this.titulo=titulo;
        this.autor=autor;
    }

    public Libro() {
        
    }
    
    public void insertarLibro(String titulo,String editor,String autor,String genero,int clase) throws SQLException{
        con = Conexion.getConnection();
        cstmt = con.prepareCall("{call INSERTAR_LIBROS(?,?,?,?,?,?)}");
        cstmt.setInt(1,clase);
        cstmt.setInt(2,0);
        cstmt.setString(3,genero);
        cstmt.setString(4,autor);
        cstmt.setString(5,titulo);
        cstmt.setString(6,editor);
        cstmt.execute();
        cstmt.close();
        con.close();
        
    }
    public void BorrarLibro(int id) throws SQLException {
		con=Conexion.getConnection();
		cstmt = con.prepareCall("{call BORRAR_LIBROS(?)}");
		 cstmt.setInt(1, id);
         cstmt.execute();
         cstmt.close();
		con.close();
	}
    public DefaultTableModel listarLibros() {

		tamano=0;
		String[] headers = { "ID","Titulo","Genero","Autor","Prestado","Editor","Clase"};
		DefaultTableModel plantilla = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		try {
			con=Conexion.getConnection();
			cstmt=con.prepareCall("{call OBTENER_LIBROS.GET_libros(?)}");
		    cstmt.registerOutParameter(1, OracleTypes.CURSOR);
		    cstmt.executeQuery();
		    ResultSet cursor = (ResultSet)cstmt.getObject(1);
		    
		    while(cursor.next()) {
		    	tamano++;
		    }
		    cursor.close();
		    cstmt.close();
		    con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String[][] filas = new String[tamano][8];

		try {
			int id,codigo,disponibilidad,clase;
			String titulo,genero,autor,editor;
			con=Conexion.getConnection();
			cstmt=con.prepareCall("{call OBTENER_LIBROS.GET_LIBROS(?)}");
		    cstmt.registerOutParameter(1, OracleTypes.CURSOR);
		    cstmt.executeQuery();
		    ResultSet cursor = (ResultSet)cstmt.getObject(1);
			int i = 0;
			while (cursor.next()) {
                              
				filas[i][0] = cursor.getString("COD_LIBRO");
				filas[i][1] = cursor.getString("TITULO");
				filas[i][2] = cursor.getString("GENERO");
				filas[i][3] = cursor.getString("AUTOR");
                                filas[i][4] = cursor.getString("PRESTADO");
				filas[i][5] = cursor.getString("EDITOR");
				filas[i][6] = cursor.getString("CLASE");
				i++;
				System.out.println(filas.toString());
			}
			con.close();
			plantilla.setDataVector(filas, headers);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plantilla;
	}
    
    

    public int getCod_libro() {
        return cod_libro;
    }

    public void setCod_libro(int cod_libro) {
        this.cod_libro = cod_libro;
    }

    public int getClase() {
        return clase;
    }

    public void setClase(int clase) {
        this.clase = clase;
    }

    public int getPrestado() {
        return prestado;
    }

    public void setPrestado(int prestado) {
        this.prestado = prestado;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
    
    
    
}
