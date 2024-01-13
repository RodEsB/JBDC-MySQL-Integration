import java.sql.*;
import javax.sql.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
public class Main { ///usr/local/mysql/bin/mysql -u root -p
    ;

    public static void main(String[] args) {
        Connection con = null;

        JDBC jdbc = new JDBC();

        Connection conexion = jdbc.getConexion();
        con = conexion;
        if(con != null){
            System.out.println("Conectado correctamente");
            jdbc.call();
            //Cerrar la conexión conexion.close();
        }else {
            System.out.println("No has podido conectarte");
        }
    }
}
