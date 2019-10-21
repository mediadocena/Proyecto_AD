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
 * @author aleja
 */
public class Persona {
    Connection con;
    CallableStatement cstmt = null;
    int cod_carnet;
    String nombre;
    Direccion direccion;

    public Persona(int cod_carnet, String nombre,Direccion direccion) {
        this. cod_carnet= cod_carnet;
        this.nombre=nombre;
        this.direccion=direccion;
    }

    public Persona() {
        
    }
    public void consultarUsuarios() throws SQLException {
		//EJEMPLO CURSOR
		con=Conexion.getConnection();
		cstmt=con.prepareCall("{call OBTENER_USUARIOS.GET_USUARIOS(?)}");
	    cstmt.registerOutParameter(1, OracleTypes.CURSOR);
	    cstmt.executeQuery();
	    ResultSet cursor = (ResultSet)cstmt.getObject(1);
	    int tamano=0;
	    while(cursor.next()) {
	    	tamano++;
	        System.out.println("TAMANO:"+tamano+" ID = " + cursor.getInt(1)+"Nombre:"+cursor.getString(2)+ "Calle:"+cursor.getString(3)+" Ciudad:"+cursor.getString(4)+" Cod_post:"+cursor.getString(5));
	    
	    }
	    cursor.close();
	    cstmt.close();
	    con.close();
	}
    
    
    public void BorrarUsuario(int id) throws SQLException{
        con=Conexion.getConnection();
		cstmt = con.prepareCall("{call BORRAR_USUARIOS(?)}");
                cstmt.setInt(1,id);
            cstmt.execute();
         cstmt.close();
        con.close();
    }
    public void PerdonarUsuario(int id) throws SQLException{
        con=Conexion.getConnection();
		cstmt = con.prepareCall("{call PERDONAR_USUARIO(?)}");
                cstmt.setInt(1, id);
            cstmt.execute();
         cstmt.close();
        con.close();
    }
    public void ActualizarUsuario(int id,String nombre, String calle, String ciudad,int cod_post) throws SQLException {
		con=Conexion.getConnection();
		cstmt = con.prepareCall("{call ACTUALIZAR_USUARIOS(?,?,?,?,?,?)}");
		 cstmt.setInt(1, id);
		 cstmt.setString(2, nombre);
		 cstmt.setString(3, calle);
		 cstmt.setString(4, ciudad);
                  cstmt.setInt(5,cod_post);
                  cstmt.setInt(6,0);
            cstmt.execute();
         cstmt.close();
        con.close();
	}
    
    
	public void insertarUsuario(String nombre, String calle, String ciudad,int cod_post) throws SQLException {
		con=Conexion.getConnection();
		cstmt = con.prepareCall("{call INSERTAR_USUARIOS(?,?,?,?)}");
		 cstmt.setString(1, nombre);
		 cstmt.setString(2, calle);
		 cstmt.setString(3, ciudad);
		 cstmt.setInt(4, cod_post);
            cstmt.execute();
         cstmt.close();
        con.close();
	}
        public DefaultTableModel listarUsuarios() {

		int tamano=0;
		String[] headers = { "ID","Nombre","Calle","Ciudad","Cod_Post","Penalizado" };
		DefaultTableModel plantilla = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		try {
			con=Conexion.getConnection();
			cstmt=con.prepareCall("{call OBTENER_USUARIOS.GET_usuarios(?)}");
		    cstmt.registerOutParameter(1, OracleTypes.CURSOR);
		    cstmt.executeQuery();
		    ResultSet cursor = (ResultSet)cstmt.getObject(1);
		    
		    while(cursor.next()) {
		    	tamano++;
		        System.out.println("ID = " + cursor.getInt(1)+" Nombre:"+cursor.getString(2)+" Calle:"+cursor.getString(3)+" Ciudad:"+cursor.getString(4)+" Cod_post:"+cursor.getInt(5));
		    }
		    cursor.close();
		    cstmt.close();
		    con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String[][] filas = new String[tamano][9];

		try {
			int id,Cod_post,penalizado;
			String nombre,calle,ciudad;
			con=Conexion.getConnection();
			cstmt=con.prepareCall("{call OBTENER_USUARIOS.GET_usuarios(?)}");
		    cstmt.registerOutParameter(1, OracleTypes.CURSOR);
		    cstmt.executeQuery();
		    ResultSet cursor = (ResultSet)cstmt.getObject(1);
			int i = 0;
			while (cursor.next()) {
				id = cursor.getInt(1);
				
				nombre=cursor.getString(2);
				
				calle=cursor.getString(3);
				ciudad=cursor.getString(4);
				Cod_post=cursor.getInt(5);
			
				penalizado=cursor.getInt(6);
				filas[i][0] = Integer.toString(id);
				filas[i][1] = nombre;
				filas[i][2] = calle;
				filas[i][3] = ciudad;
				filas[i][4] = Integer.toString(Cod_post);
				filas[i][5] = Integer.toString(penalizado);
				
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

    public int getCod_carnet() {
        return cod_carnet;
    }

    public void setCod_carnet(int cod_carnet) {
        this.cod_carnet = cod_carnet;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
    
    

}
