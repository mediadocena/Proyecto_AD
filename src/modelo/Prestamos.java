/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author aleja
 */
public class Prestamos {
    Connection con;
   CallableStatement cstmt = null;
    public Prestamos() {
    }
    
       public void DevolverLibro(int ID) throws SQLException {
		con=Conexion.getConnection();
		cstmt = con.prepareCall("{call BORRAR_PRESTAMOS(?)}");
		 cstmt.setInt(1, ID);
            cstmt.execute();
         cstmt.close();
        con.close();
	}
    public void insertarPrestamo(int nuser,int nlib,String finicio,String plazo) throws SQLException {
		con=Conexion.getConnection();
		cstmt = con.prepareCall("{call INSERTAR_PRESTAMOS(?,?,?,?)}");
		 cstmt.setInt(1, nuser);
		 cstmt.setInt(2, nlib);
		 cstmt.setString(3, finicio);
		 cstmt.setString(4, plazo);
            cstmt.execute();
         cstmt.close();
        con.close();
	}
    
    public DefaultTableModel listarPrestamos() {

		int tamano=0;
		String[] headers = { "ID","Nombre","COD_CLIENTE","Titulo Libro","COD_LIBRO","Fecha Inicio","Fecha Fin","Plazo"};
		DefaultTableModel plantilla = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		try {
			con=Conexion.getConnection();
			cstmt=con.prepareCall("{call OBTENER_PRESTAMOS.GET_PRESTAMOS(?)}");
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

		String[][] filas = new String[tamano][9];

		try {
			
			con=Conexion.getConnection();
			cstmt=con.prepareCall("{call OBTENER_Prestamos.GET_PRESTAMOS(?)}");
		    cstmt.registerOutParameter(1, OracleTypes.CURSOR);
		    cstmt.executeQuery();
		    ResultSet cursor = (ResultSet)cstmt.getObject(1);
			int i = 0;
			while (cursor.next()) {
				
				filas[i][0] = cursor.getString("ID");
				filas[i][1] = cursor.getString("USUARIO.NOMBRE");
				filas[i][2] = cursor.getString("USUARIO.NUM_CARNET");
				filas[i][3] = cursor.getString("LIBRO.TITULO");
				filas[i][4] = cursor.getString("LIBRO.COD_LIBRO");
				filas[i][5] = cursor.getString("FECHA_INICIO");
                                filas[i][6] = cursor.getString("FECHA_FIN");
                                filas[i][7] = cursor.getString("PLAZO");
				
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
    public DefaultTableModel listarHistorico() {

		int tamano=0;
		String[] headers = { "ID","Nombre","COD_CLIENTE","Titulo Libro","COD_LIBRO","Fecha Inicio","Fecha Fin","Plazo"};
		DefaultTableModel plantilla = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		try {
			con=Conexion.getConnection();
			cstmt=con.prepareCall("{call OBTENER_HISTORICO.GET_HISTORICO(?)}");
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

		String[][] filas = new String[tamano][9];

		try {
			
			con=Conexion.getConnection();
			cstmt=con.prepareCall("{call OBTENER_HISTORICO.GET_HISTORICO(?)}");
		    cstmt.registerOutParameter(1, OracleTypes.CURSOR);
		    cstmt.executeQuery();
		    ResultSet cursor = (ResultSet)cstmt.getObject(1);
			int i = 0;
			while (cursor.next()) {
				
				filas[i][0] = cursor.getString("ID");
				filas[i][1] = cursor.getString("USUARIO.NOMBRE");
				filas[i][2] = cursor.getString("USUARIO.NUM_CARNET");
				filas[i][3] = cursor.getString("LIBRO.TITULO");
				filas[i][4] = cursor.getString("LIBRO.COD_LIBRO");
				filas[i][5] = cursor.getString("FECHA_INICIO");
                                filas[i][6] = cursor.getString("FECHA_FIN");
                                filas[i][7] = cursor.getString("PLAZO");
				
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
    
}
