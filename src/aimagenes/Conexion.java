package aimagenes;

import java.sql.*;

public class Conexion {

    private String controlador = "org.postgresql.Driver";
    private String cadenaConexion = "jdbc:postgresql://localhost:5432/bd_estrella";
    private String usuario = "postgres";
    private String clave = "silviopd";

    private Connection conexion;

    protected Connection abrirConexion() throws Exception {
        Class.forName(this.controlador);
        conexion = DriverManager.getConnection(
                cadenaConexion,
                usuario,
                clave);

        return conexion;
    }

    private void cerrarConexion(Connection con) throws Exception {
        con.close();
        con = null;
    }

    protected ResultSet ejecutarSQLSelect(String sql) throws Exception {
        Statement sentencia = null;
        ResultSet resultado = null;

        sentencia = this.abrirConexion().createStatement();
        resultado = sentencia.executeQuery(sql);
        this.cerrarConexion(conexion);

        return resultado;
    }

    protected ResultSet ejecutarSQLSelectSP(PreparedStatement sentencia) throws Exception {
        ResultSet resultado = null;
        resultado = sentencia.executeQuery();
        this.cerrarConexion(conexion);
        return resultado;
    }

    protected int ejecutarSQLsp(PreparedStatement sentencia, Connection con) throws Exception {
        int resultado = 0;
        try {
            resultado = sentencia.executeUpdate();
        } catch (Exception e) {
            con.rollback();
            throw e;
        }
        return resultado;
    }
    
}
