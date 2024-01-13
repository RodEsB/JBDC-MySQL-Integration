import java.awt.*;
import java.awt.event.WindowAdapter;
import java.sql.*;
import javax.sql.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JDBC {
    static final String URL = "jdbc:mysql://localhost:3306/aulasdb";
    static final String USER = "root";
    static final String CLAVE = "1234";

    private static JLabel resultadoLabel;

    public static Connection getConexion() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //Carga el controlador
            con = (Connection) DriverManager.getConnection(URL,
                    USER, CLAVE);
            System.out.println("conexion establecida");
            JOptionPane.showMessageDialog(null, "conexion establecida");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from edificio"); //Tipo de consulta y para actualizar es update
            while (rs.next()) {
                System.out.println(rs.getString(2));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("error de conexion");
            JOptionPane.showMessageDialog(null, "error de conexion " + e);
        }
        return con;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    public void call() {
        // Crear un objeto JFrame (ventana principal)
        JFrame ventanaPrincipal = new JFrame("");

        // Crear un objeto JPanel (panel) para el contenido
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        // Crear un panel para el título con color naranja
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(0, 80, 47, 255));
        JLabel tituloLabel = new JLabel("Administrador Universitario");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setHorizontalAlignment(JLabel.CENTER);
        tituloLabel.setForeground(Color.WHITE); // Establecer el color del texto a blanco
        panelTitulo.add(tituloLabel);

        // Crear un panel para los botones con color verde
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(new Color(244, 123, 32, 255));

        // Crear botones
        JButton btnAgregar = new JButton("Agregar datos");
        JButton btnEliminar = new JButton("Eliminar datos");

        // Crear una etiqueta para reflejar las acciones
        resultadoLabel = new JLabel("");

        String[] opcionesInsertar = {"Ingresar consultas", "Consultar cupo de curso", "Consulta los salones de curso", "Consultar capacidad de aula", "Consultar aulas de edificio"};
        JComboBox<String> consultaComboBox = new JComboBox<>(opcionesInsertar);

        consultaComboBox.addActionListener(e -> {
            String consultaSeleccionada = (String) consultaComboBox.getSelectedItem();

            if ("Ingresar consultas".equals(consultaSeleccionada)) {

            } else if ("Consultar cupo de curso".equals(consultaSeleccionada)) {
                ventanaCupo(consultaSeleccionada);
            } else if ("Consulta los salones de curso".equals(consultaSeleccionada)) {
                ventanaSalonCurso(consultaSeleccionada);
            } else if ("Consultar capacidad de aula".equals(consultaSeleccionada)) {
                ventanaCapacidad(consultaSeleccionada);
            } else if ("Consultar aulas de edificio".equals(consultaSeleccionada)) {
                ventanaAulaEdificio(consultaSeleccionada);
            }
        });

        // Agregar ActionListener a la lista desplegable de consultas

        // Agregar ActionListener al botón Agregar
        btnAgregar.addActionListener(e -> {
            // Abrir una nueva ventana al hacer clic en el botón Agregar
            abrirNuevaVentana();
        });

        // Agregar ActionListener al botón Eliminar
        btnEliminar.addActionListener(e -> {
            // Abrir una nueva ventana con los mismos datos que el panelNuevaVentana
            abrirNuevaVentanaEliminar();
        });

            // Agregar botones al panel de botones
            panelBotones.add(btnAgregar);
            panelBotones.add(btnEliminar);

            // Agregar componentes al panel principal
            panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
            panelPrincipal.add(panelBotones, BorderLayout.CENTER);
            panelPrincipal.add(resultadoLabel, BorderLayout.SOUTH);
            panelPrincipal.add(consultaComboBox, BorderLayout.SOUTH);

            // Establecer el panel principal como contenido de la ventana principal
            ventanaPrincipal.getContentPane().add(panelPrincipal);

            // Establecer el tamaño de la ventana principal
            ventanaPrincipal.setSize(400, 300);

            // Establecer la operación por defecto al cerrar la ventana principal
            ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Hacer visible la ventana principal
            ventanaPrincipal.setVisible(true);
        }


    /////////////////////////////////////////////////Creación de las consultas//////////////////////////////////////////

    ////////////////////////////////////////////////Consultas cupo de curso

    private static void ventanaCupo(String opcionesConsulta) {
        JFrame ventanaCupo = new JFrame("Consultas cupo de curso");
        JPanel panelAula = new JPanel();
        panelAula.setLayout(new BoxLayout(panelAula, BoxLayout.Y_AXIS));
        panelAula.setBackground(new Color(244, 123, 32));
        ventanaCupo.getContentPane().add(panelAula);

        JLabel labelTexto = new JLabel("Ingresar el nombre del curso:");
        JTextField textFieldCurso = new JTextField(2);
        labelTexto.setForeground(Color.WHITE);
        labelTexto.setFont(new Font("Arial", Font.BOLD, labelTexto.getFont().getSize()));

        JLabel labelTextoA = new JLabel("Ingresar el horario:");
        JTextField textFieldHorario = new JTextField(2);
        labelTextoA.setForeground(Color.WHITE);
        labelTextoA.setFont(new Font("Arial", Font.BOLD, labelTexto.getFont().getSize()));

        JButton boton = new JButton("Aceptar");
        Dimension preferredSize = new Dimension(150, 30);
        boton.setPreferredSize(preferredSize);

        boton.addActionListener(e -> {
            String cursoIngresado = textFieldCurso.getText();
            String horarioIngresado = textFieldHorario.getText();
            System.out.println(horarioIngresado + cursoIngresado);
            int cupo = consultarCupoPorHorarioYCurso(horarioIngresado, cursoIngresado);

            JOptionPane.showMessageDialog(
                    null,
                    " Hay un cupo de: " + cupo);
        });

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(labelTexto);
        panelAula.add(textFieldCurso);

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(labelTextoA);
        panelAula.add(textFieldHorario);

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(boton);

        ventanaCupo.setSize(300, 200);
        ventanaCupo.setVisible(true);
        ventanaCupo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private static int consultarCupoPorHorarioYCurso(String horario, String nombreCurso) {
        int cupo = -1; // Valor predeterminado en caso de que no se encuentre la asignación

        try (Connection con = getConexion()) {
            String query = "SELECT cupo FROM Asignaciones " +
                    "INNER JOIN Cursos ON Asignaciones.Cursos_idCursos = Cursos.idCursos " +
                    "WHERE horario = ? AND Cursos.nombreCurso = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, horario);
                pstmt.setString(2, nombreCurso);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    cupo = rs.getInt("cupo");
                }

                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al consultar el cupo: " + e.getMessage());
        }

        return cupo;
    }


    ////////////////////////////////////////////////Fin de consultas cupo de curso

    ////////////////////////////////////////////////Consultas salón de un curso

    private static void ventanaSalonCurso(String opcionesConsulta) {
        JFrame ventanaSalonCurso = new JFrame("Consultas los salones de curso");
        JPanel panelAula = new JPanel();
        panelAula.setLayout(new BoxLayout(panelAula, BoxLayout.Y_AXIS));
        panelAula.setBackground(new Color(244, 123, 32));
        ventanaSalonCurso.getContentPane().add(panelAula);

        JLabel labelTexto = new JLabel("Ingresar el nombre del curso:");
        JTextField textField = new JTextField(2);
        labelTexto.setForeground(Color.WHITE); // Cambiar color del texto a blanco
        labelTexto.setFont(new Font("Arial", Font.BOLD, labelTexto.getFont().getSize()));
        JButton boton = new JButton("Aceptar");
        Dimension preferredSize = new Dimension(150, 30);
        boton.setPreferredSize(preferredSize);

        boton.addActionListener(e -> {
            String cursoIngresado = textField.getText();
            consultarAulasPorCurso(cursoIngresado);

            JOptionPane.showMessageDialog(
                    null,
                    "Se ha impreso en tu terminal tu aula: " + cursoIngresado);
        });

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(labelTexto);
        panelAula.add(textField);

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(boton);

        ventanaSalonCurso.setSize(300, 200);
        ventanaSalonCurso.setVisible(true);
        ventanaSalonCurso.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }




    private static void consultarAulasPorCurso(String nombreCurso) {
        try (Connection con = getConexion()) {
            String query = "SELECT Aula.numAula, Edificio.nombreEdificio " +
                    "FROM Aula " +
                    "INNER JOIN Edificio ON Aula.Edificio_idEdificio = Edificio.idEdificio " +
                    "INNER JOIN Asignaciones ON Aula.idAula = Asignaciones.Aula_idAula " +
                    "INNER JOIN Cursos ON Asignaciones.Cursos_idCursos = Cursos.idCursos " +
                    "WHERE Cursos.nombreCurso = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, nombreCurso);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    String numAula = rs.getString("numAula");
                    String nombreEdificio = rs.getString("nombreEdificio");

                    System.out.println("Aula: " + numAula + ", Edificio: " + nombreEdificio+"\n");
                }

                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al consultar las aulas por curso: " + e.getMessage());
        }
    }


    ////////////////////////////////////////////////Fin de consultas salón de un curso

    ////////////////////////////////////////////////Consultas capacidad de aula

    private static void ventanaCapacidad(String opcionesConsulta) {
        JFrame ventanaCapacidad = new JFrame("Consulta capacidad de aula");
        JPanel panelAula = new JPanel();
        panelAula.setLayout(new BoxLayout(panelAula, BoxLayout.Y_AXIS));
        panelAula.setBackground(new Color(244, 123, 32));
        ventanaCapacidad.getContentPane().add(panelAula);

        JLabel labelTexto = new JLabel("Ingresar el número de aula:");
        JTextField textField = new JTextField(2);
        labelTexto.setForeground(Color.WHITE); // Cambiar color del texto a blanco
        labelTexto.setFont(new Font("Arial", Font.BOLD, labelTexto.getFont().getSize()));
        JLabel labelTextoA = new JLabel("Ingresar el edificio:");
        JTextField textFieldA = new JTextField(2);
        labelTexto.setForeground(Color.WHITE); // Cambiar color del texto a blanco
        labelTexto.setFont(new Font("Arial", Font.BOLD, labelTexto.getFont().getSize()));
        JButton boton = new JButton("Aceptar");
        Dimension preferredSize = new Dimension(150, 30);
        boton.setPreferredSize(preferredSize);

        boton.addActionListener(e -> {
            String numeroAulaIngresado = textField.getText();
            String edificioIngresado = textFieldA.getText();
            int capacidad=consultarCapacidadAula(numeroAulaIngresado,edificioIngresado);

            JOptionPane.showMessageDialog(
                    null,
                    "La capacidad es : "+ capacidad);
        });

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(labelTexto);
        panelAula.add(textField);

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(labelTextoA);
        panelAula.add(textFieldA);

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(boton);

        ventanaCapacidad.setSize(300, 200);
        ventanaCapacidad.setVisible(true);
        ventanaCapacidad.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private static int consultarCapacidadAula(String numAula, String nombreEdificio) {
        int capacidad = -1; // Valor predeterminado en caso de que no se encuentre el aula

        try (Connection con = getConexion()) {
            String query = "SELECT capacidad FROM Aula WHERE numAula = ? AND Edificio_idEdificio = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                // Obtener el idEdificio por nombreEdificio
                int idEdificio = obtenerIdEdificioPorNombre(nombreEdificio);

                pstmt.setString(1, numAula);
                pstmt.setInt(2, idEdificio);

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    capacidad = rs.getInt("capacidad");
                }

                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al consultar la capacidad del aula: " + e.getMessage());
        }

        return capacidad;
    }



    ////////////////////////////////////////////////Fin de consulta lista de edificios

    ////////////////////////////////////////////////Consultas lista de edificio

   /* private static void ventanaListaEdificio(String opcionesConsulta) {
        JFrame ventanaListaEdificio = new JFrame("Consultas aulas de un edificio");
        JPanel panelAula = new JPanel();
        panelAula.setLayout(new BoxLayout(panelAula, BoxLayout.Y_AXIS));
        panelAula.setBackground(new Color(244, 123, 32));
        ventanaListaEdificio.getContentPane().add(panelAula);

                mostrarEdificios();



        ventanaListaEdificio.setSize(300, 200);
        ventanaListaEdificio.setVisible(true);
        ventanaListaEdificio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }*/

    private static void mostrarEdificios() {
        try (Connection con = getConexion()) {
            String query = "SELECT nombreEdificio FROM Edificio";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                ResultSet rs = pstmt.executeQuery();

                System.out.println("Nombre de Edificios:");

                while (rs.next()) {
                    String nombreEdificio = rs.getString("nombreEdificio");
                    System.out.println(nombreEdificio);
                }

                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al mostrar los edificios: " + e.getMessage());
        }
    }


    ////////////////////////////////////////////////Fin de consulta aulas de un edificio

    ////////////////////////////////////////////////Consultas aulas de un edificio

    private static void ventanaAulaEdificio(String opcionesConsulta) {
        JFrame ventanaAulaEdificio = new JFrame("Consultas capacidad de aula");
        JPanel panelAula = new JPanel();
        panelAula.setLayout(new BoxLayout(panelAula, BoxLayout.Y_AXIS));
        panelAula.setBackground(new Color(244, 123, 32));
        ventanaAulaEdificio.getContentPane().add(panelAula);

        JLabel labelTextoA = new JLabel("Ingresar el edificio:");
        JTextField textFieldA = new JTextField(2);
        labelTextoA.setForeground(Color.WHITE); // Cambiar color del texto a blanco
        labelTextoA.setFont(new Font("Arial", Font.BOLD, labelTextoA.getFont().getSize()));
        JButton boton = new JButton("Aceptar");
        Dimension preferredSize = new Dimension(150, 30);
        boton.setPreferredSize(preferredSize);

        boton.addActionListener(e -> {
            String edificioIngresado = textFieldA.getText();
          imprimirAulasPorEdificio(edificioIngresado);

            JOptionPane.showMessageDialog(
                    null,
                    "Se listaron las aulas en tu terminal : ");
        });


        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(labelTextoA);
        panelAula.add(textFieldA);

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(boton);

        ventanaAulaEdificio.setSize(300, 200);
        ventanaAulaEdificio.setVisible(true);
        ventanaAulaEdificio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private static void imprimirAulasPorEdificio(String nombreEdificio) {
        try (Connection con = getConexion()) {
            String query = "SELECT A.numAula, E.nombreEdificio " +
                    "FROM Aula A " +
                    "JOIN Edificio E ON A.Edificio_idEdificio = E.idEdificio " +
                    "WHERE E.nombreEdificio = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, nombreEdificio);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    String numAula = rs.getString("numAula");
                    String nombreEdif = rs.getString("nombreEdificio");
                    System.out.println( nombreEdif+ numAula +"\n");
                }

                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al imprimir las aulas por edificio: " + e.getMessage());
        }
    }



    ////////////////////////////////////////////////Fin de consulta aulas de un edificio




    private static void abrirNuevaVentana() {
        JFrame nuevaVentana = new JFrame("Insertar datos");

        JPanel panelNuevaVentana = getjPanel();

        nuevaVentana.getContentPane().add(panelNuevaVentana);
        nuevaVentana.setSize(300, 200);
        nuevaVentana.setVisible(true);
    }

    private static void abrirNuevaVentanaEliminar() {
        JFrame nuevaVentanaEliminar = new JFrame("Eliminar datos");

        JPanel panelNuevaVentanaEliminar = getjPanelEliminar();

        nuevaVentanaEliminar.getContentPane().add(panelNuevaVentanaEliminar);
        nuevaVentanaEliminar.setSize(300, 200);
        nuevaVentanaEliminar.setVisible(true);
    }

    /////////////////////////////////////////////////////////////edificio
    private static void ventanaEdificio(String opcionesConsulta) {
        JFrame ventanaEdificio = new JFrame("Edificios");

        JLabel labelEdificio = new JLabel("");
        JPanel panelEdificio = new JPanel();
        ventanaEdificio.add(labelEdificio);
        panelEdificio.setBackground(new Color(244, 123, 32));
        ventanaEdificio.getContentPane().add(panelEdificio);

        JLabel labelTextoE = new JLabel("Ingresar el edificio:");
        JTextField textField = new JTextField(2);
        labelTextoE.setForeground(Color.WHITE); // Cambiar color del texto a blanco
        labelTextoE.setFont(new Font("Arial", Font.BOLD, labelTextoE.getFont().getSize()));
        JButton boton = new JButton("Aceptar");

        boton.addActionListener(e -> {
            String textoIngresado = textField.getText();
            insertarEdificio(textoIngresado); // Llamada a la función para insertar el nuevo edificio
            JOptionPane.showMessageDialog(null, "Edificio ingresado : " + textoIngresado);
        });


        JPanel panelNuevaVentana = new JPanel(new GridLayout(3, 2));

        panelNuevaVentana.add(new JLabel());
        panelNuevaVentana.add(new JLabel());
        panelNuevaVentana.add(labelTextoE);
        panelNuevaVentana.add(textField);
        panelNuevaVentana.add(new JLabel());
        panelNuevaVentana.add(boton);
        ventanaEdificio.getContentPane().add(panelNuevaVentana);
        ventanaEdificio.setSize(400, 200);
        ventanaEdificio.setVisible(true);
    }


    private static void insertarEdificio(String nombreEdificio) {
        try (Connection con = getConexion()) {
            String sql = "INSERT INTO Edificio (nombreEdificio) VALUES (?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, nombreEdificio);
                pstmt.executeUpdate();
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar el edificio: " + e.getMessage());
        }
    }

    /////////////////////////////////////////////////////////////edificio termina
    /////////////////////////////////////////////////////////////aula

    private static void ventanaAula(String opcionesConsulta) {
        JFrame ventanaAula = new JFrame("Aulas");
        JPanel panelAula = new JPanel();
        panelAula.setLayout(new BoxLayout(panelAula, BoxLayout.Y_AXIS));
        panelAula.setBackground(new Color(244, 123, 32));
        ventanaAula.getContentPane().add(panelAula);

        JLabel labelTextoA = new JLabel("Ingresar el número de aula:");
        JTextField textField = new JTextField(6);
        labelTextoA.setForeground(Color.WHITE); // Cambiar color del texto a blanco
        labelTextoA.setFont(new Font("Arial", Font.BOLD, labelTextoA.getFont().getSize()));
        JLabel labelTextoAb = new JLabel("Ingresar la capacidad:");
        JTextField textFieldA = new JTextField(6);
        labelTextoAb.setForeground(Color.WHITE); // Cambiar color del texto a blanco
        labelTextoAb.setFont(new Font("Arial", Font.BOLD, labelTextoAb.getFont().getSize()));
        JLabel labelTextoAc = new JLabel("Ingresar el edificio:");
        JTextField textFieldAb = new JTextField(6);
        labelTextoAc.setForeground(Color.WHITE); // Cambiar color del texto a blanco
        labelTextoAc.setFont(new Font("Arial", Font.BOLD, labelTextoAc.getFont().getSize()));
        JButton boton = new JButton("Aceptar");
        Dimension preferredSize = new Dimension(150, 30);
        boton.setPreferredSize(preferredSize);

        boton.addActionListener(e -> {
            String aulaIngresado = textField.getText();
            int capacidadIngresado = Integer.parseInt(textFieldA.getText());
            String edificioIngresado = textFieldAb.getText();
            insertarAula(aulaIngresado,capacidadIngresado,edificioIngresado);

            JOptionPane.showMessageDialog(
                    null,
                    "Aula ingresada : " + edificioIngresado + aulaIngresado + " con capacidad de " + capacidadIngresado
            );
        });

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(labelTextoA);
        panelAula.add(textField);

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(labelTextoAb);
        panelAula.add(textFieldA);

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(labelTextoAc);
        panelAula.add(textFieldAb);

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(boton);

        ventanaAula.setSize(300, 200);
        ventanaAula.setVisible(true);
        ventanaAula.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private static void insertarAula(String numAula, int capacidad, String edificio) {
        try (Connection con = getConexion()) {
            String sql = "INSERT INTO Aula (numAula, capacidad, Edificio_idEdificio) VALUES (?,?,?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, numAula);
                pstmt.setInt(2, capacidad);
                pstmt.setInt(3, obtenerIdEdificioPorNombre(edificio)); //
                pstmt.executeUpdate();
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar el aula: " + e.getMessage());
        }
    }

    //////////////////////////////////Cursos

    private static void ventanaCursos(String opcionesConsulta) {
        JFrame ventanaCursos = new JFrame("Aulas");
        JPanel panelAula = new JPanel();
        panelAula.setLayout(new BoxLayout(panelAula, BoxLayout.Y_AXIS));
        panelAula.setBackground(new Color(244, 123, 32));
        ventanaCursos.getContentPane().add(panelAula);

        JLabel labelTextoA = new JLabel("Ingresar el nombre del curso:");
        JTextField textField = new JTextField(2);
        labelTextoA.setForeground(Color.WHITE); // Cambiar color del texto a blanco
        labelTextoA.setFont(new Font("Arial", Font.BOLD, labelTextoA.getFont().getSize()));
        JButton boton = new JButton("Aceptar");
        Dimension preferredSize = new Dimension(150, 30);
        boton.setPreferredSize(preferredSize);

        boton.addActionListener(e -> {
            String cursoIngresado = textField.getText();
            insertarCursos(cursoIngresado);

            JOptionPane.showMessageDialog(
                    null,
                    "Se ha agreado : " + cursoIngresado);
        });

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(labelTextoA);
        panelAula.add(textField);

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(boton);

        ventanaCursos.setSize(300, 200);
        ventanaCursos.setVisible(true);
        ventanaCursos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private static void insertarCursos(String nombreCurso) {
        try (Connection con = getConexion()) {
            String sql = "INSERT INTO Cursos (nombreCurso) VALUES (?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, nombreCurso);

                pstmt.executeUpdate();
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar el curso: " + e.getMessage());
        }
    }

    ////////////Fin de cursos

    /////////////Asignaciones

    private static void ventanaAsignaciones(String opcionesConsulta) {
        JFrame ventanaAsignaciones = new JFrame("Asignaciones");
        JPanel panelAula = new JPanel();
        panelAula.setLayout(new BoxLayout(panelAula, BoxLayout.Y_AXIS));
        panelAula.setBackground(new Color(244, 123, 32));
        ventanaAsignaciones.getContentPane().add(panelAula);

        JLabel labelTextoA = new JLabel("Ingresar el edificio del aula:");
        JTextField textFieldA = new JTextField(8);
        labelTextoA.setForeground(Color.WHITE); // Cambiar color del texto a blanco
        labelTextoA.setFont(new Font("Arial", Font.BOLD, labelTextoA.getFont().getSize()));

        JLabel labelTextoAb = new JLabel("Ingresar el número del aula:");
        JTextField textFieldB = new JTextField(8);
        labelTextoAb.setForeground(Color.WHITE); // Cambiar color del texto a blanco
        labelTextoAb.setFont(new Font("Arial", Font.BOLD, labelTextoAb.getFont().getSize()));

        JLabel labelTextoAc = new JLabel("Ingresar el nombre del curso:"); // Requiere una función para ID del curso
        JTextField textFieldC = new JTextField(8);
        labelTextoAc.setForeground(Color.WHITE); // Cambiar color del texto a blanco
        labelTextoAc.setFont(new Font("Arial", Font.BOLD, labelTextoAc.getFont().getSize()));

        JLabel labelTextoAd = new JLabel("Ingresar el cupo:");
        JTextField textFieldD = new JTextField(8);
        labelTextoAd.setForeground(Color.WHITE); // Cambiar color del texto a blanco
        labelTextoAb.setFont(new Font("Arial", Font.BOLD, labelTextoAb.getFont().getSize()));

        JLabel labelTextoAe = new JLabel("Ingresar el horario:");
        JTextField textFieldE = new JTextField(8);
        labelTextoAe.setForeground(Color.WHITE); // Cambiar color del texto a blanco
        labelTextoAe.setFont(new Font("Arial", Font.BOLD, labelTextoAe.getFont().getSize()));

        JButton boton = new JButton("Aceptar");
        Dimension preferredSize = new Dimension(150, 30);
        boton.setPreferredSize(preferredSize);

        boton.addActionListener(e -> {
            String edificioIngresado = textFieldA.getText();
            String numeroAulaIngresado = textFieldB.getText();
            String nombreCursoIngresado = textFieldC.getText();
            int cupoIngresado = Integer.parseInt(textFieldD.getText());
            String horarioIngresado = textFieldE.getText();
            insertarAsignacion(edificioIngresado, numeroAulaIngresado, nombreCursoIngresado, horarioIngresado, cupoIngresado);

            JOptionPane.showMessageDialog(null, "Se ha agregado: " + nombreCursoIngresado+" a "+ edificioIngresado + numeroAulaIngresado + " en horario de  "+ horarioIngresado);
        });

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(labelTextoA);
        panelAula.add(textFieldA);

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(labelTextoAb);
        panelAula.add(textFieldB);

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(labelTextoAc);
        panelAula.add(textFieldC);

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(labelTextoAd);
        panelAula.add(textFieldD);

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(labelTextoAe);
        panelAula.add(textFieldE);

        panelAula.add(Box.createVerticalStrut(5));
        panelAula.add(boton);

        ventanaAsignaciones.setSize(300, 400);
        ventanaAsignaciones.setVisible(true);
        ventanaAsignaciones.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private static void insertarAsignacion(String nombreEdificio, String numAula,String nombreCurso,String horario, int cupo  ) {
        try (Connection con = getConexion()) {
            int idEdificio= obtenerIdEdificioPorNombre(nombreEdificio);
            int idAula= obtenerIdAulaPorNombreYNumero(idEdificio,numAula);
            int idCurso=obtenerIdCursoPorNombre(nombreCurso);
            String sql = "INSERT INTO Asignaciones (Aula_idAula, Cursos_idCursos, horario, cupo) VALUES (?,?,?,?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                //hacer funcion para llamar id Aula usando el nombreEdificio y numAula
                pstmt.setInt(1, idAula);
                pstmt.setInt(2, idCurso);
                pstmt.setString(3, horario);
                pstmt.setInt(4, cupo);

                pstmt.executeUpdate();
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar la asignacion: " + e.getMessage());
        }
    }

    private static int obtenerIdAulaPorNombreYNumero(int idEdificio, String numAula) {
        int idAula = -1;

        try (Connection con = getConexion()) {
            String query = "SELECT idAula FROM Aula WHERE Edificio_idEdificio = ? AND numAula = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, idEdificio);
                pstmt.setString(2, numAula);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        idAula = rs.getInt("idAula");
                    }
                }
            }
        } catch (SQLException e) {
            // Maneja la excepción adecuadamente, por ejemplo, lanza una excepción personalizada
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el idAula: " + e.getMessage());
        }
        System.out.println(idAula);

        return idAula;
    }



    private static int obtenerIdCursoPorNombre(String nombreCurso) {
        int idCurso = -1; // Valor predeterminado en caso de que no se encuentre el curso

        try {
            Connection con = getConexion();
            String query = "SELECT idCursos FROM Cursos WHERE nombreCurso = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, nombreCurso);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    idCurso = rs.getInt("idCursos");
                }

                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el idCurso: " + e.getMessage());
        }

        return idCurso;
    }

    ////////////Fin de Asignaciones


/////////////////////////////////////////ELIMINAR/////////////////////////////////////////////////////

//////////////////////////////////////ELIMINAR AULA

    private static void ventanaAulaEliminar(String opcionesConsulta) {
        JFrame ventanaAula = new JFrame("Eliminar Aulas");
        JPanel panelAula = new JPanel();
        panelAula.setLayout(new BoxLayout(panelAula, BoxLayout.Y_AXIS));
        panelAula.setBackground(new Color(244, 123, 32));
        ventanaAula.getContentPane().add(panelAula);

        JLabel labelTexto = new JLabel("Ingresar el nombre del edificio:");
        JTextField textField = new JTextField(3);
        labelTexto.setForeground(Color.WHITE); // Cambiar color del texto a blanco
        labelTexto.setFont(new Font("Arial", Font.BOLD, labelTexto.getFont().getSize()));
        JLabel labelTextoB = new JLabel("Ingresar el número de aula que se desea eliminar:");
        JTextField textFieldB = new JTextField(3);
        labelTextoB.setForeground(Color.WHITE); // Cambiar color del texto a blanco
        labelTextoB.setFont(new Font("Arial", Font.BOLD, labelTextoB.getFont().getSize()));
        JButton boton = new JButton("Aceptar"); // Agregar un pop-up para confirmar la acción
        Dimension preferredSize = new Dimension(150, 30);
        boton.setPreferredSize(preferredSize);

        boton.addActionListener(e -> {
            int result = showConfirmationDialogAula("Eliminar este aula eliminara todas las asignaciones en esta");

            if (result == JOptionPane.OK_OPTION) {
                String nombreEdificioIngresado = textField.getText();
                String nombreAulaIngresado = textField.getText();
                eliminarAula(nombreEdificioIngresado, nombreAulaIngresado);

                JOptionPane.showMessageDialog(null, "Aula eliminado : " + nombreEdificioIngresado+nombreAulaIngresado);
            } else {
                JOptionPane.showMessageDialog(null, "Acción cancelada.");
            }
        });

        panelAula.add(Box.createVerticalStrut(3));
        panelAula.add(labelTexto);
        panelAula.add(textField);

        panelAula.add(Box.createVerticalStrut(3));
        panelAula.add(boton);

        ventanaAula.setSize(300, 200);
        ventanaAula.setVisible(true);
    }

    private static int showConfirmationDialogAula(String mensaje) {
        Object[] opciones = {"Aceptar", "Cancelar"};
        int resultado = JOptionPane.showOptionDialog(null, mensaje, "Confirmación",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                null, opciones, opciones[1]);

        return resultado;
    }


    private static void eliminarAula(String numAula, String nombreEdificio) {
        try (Connection con = getConexion()) {
            int idEdificio= obtenerIdEdificioPorNombre(nombreEdificio);
            // Eliminar el aula de la tabla Aula
            String eliminarAulaSQL = "DELETE FROM Aula WHERE idAula = ?";
            try (PreparedStatement pstmt = con.prepareStatement(eliminarAulaSQL)) {
                pstmt.setInt(1, obtenerIdAulaPorNombreYNumero(idEdificio, numAula));
                pstmt.executeUpdate();

            }

            // Eliminar asignaciones relacionadas en la tabla Asignaciones
            String eliminarAsignacionesSQL = "DELETE FROM Asignaciones WHERE Aula_idAula = ?";
            try (PreparedStatement pstmt = con.prepareStatement(eliminarAsignacionesSQL)) {
                pstmt.setInt(1, obtenerIdAulaPorNombreYNumero(idEdificio, numAula));
                pstmt.executeUpdate();

            }
            con.close();
            JOptionPane.showMessageDialog(null, "Aula eliminada con éxito");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el aula: " + e.getMessage());
        }
    }


//////////////////////////////////////////////////////////FIN ELIMINAR AULA

//////////////////////////////////////ELIMINAR EDIFICIO

    private static void ventanaEdificioEliminar(String opcionesConsulta) {
        JFrame ventanaEdificio = new JFrame("Eliminar Edificio");
        JPanel panelAula = new JPanel();
        panelAula.setLayout(new BoxLayout(panelAula, BoxLayout.Y_AXIS));
        panelAula.setBackground(new Color(244, 123, 32));
        ventanaEdificio.getContentPane().add(panelAula);

        //
        JLabel labelTexto = new JLabel("Ingresar el nombre del edificio:");
        JTextField textField = new JTextField(2);
        labelTexto.setForeground(Color.WHITE); // Cambiar color del texto a blanco
        labelTexto.setFont(new Font("Arial", Font.BOLD, labelTexto.getFont().getSize()));
        JButton boton = new JButton("Aceptar"); // Agregar un pop-up para confirmar la acción
        Dimension preferredSize = new Dimension(150, 30);
        boton.setPreferredSize(preferredSize);

        boton.addActionListener(e -> {
            int result = showConfirmationDialogEdificio("Eliminar este edificio eliminara todas las aulas y asignaciones relacionadas a este");

            if (result == JOptionPane.OK_OPTION) {
                String nombreEdificioIngresado = textField.getText();
                eliminarEdificio(nombreEdificioIngresado);

                JOptionPane.showMessageDialog(null, "Edificio eliminada : " + nombreEdificioIngresado);
            } else {
                JOptionPane.showMessageDialog(null, "Acción cancelada.");
            }
        });

        panelAula.add(Box.createVerticalStrut(3));
        panelAula.add(labelTexto);
        panelAula.add(textField);

        panelAula.add(Box.createVerticalStrut(3));
        panelAula.add(boton);

        ventanaEdificio.setSize(300, 200);
        ventanaEdificio.setVisible(true);
    }

    private static int showConfirmationDialogEdificio(String mensaje) {
        Object[] opciones = {"Aceptar", "Cancelar"};
        int resultado = JOptionPane.showOptionDialog(null, mensaje, "Confirmación",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                null, opciones, opciones[1]);

        return resultado;
    }


    private static void eliminarEdificio(String nombreEdificio) {
        try (Connection con = getConexion()) {
            // Obtener el ID del edificio
            int idEdificio = obtenerIdEdificioPorNombre(nombreEdificio);

            // Eliminar las asignaciones relacionadas en la tabla Asignaciones
            String eliminarAsignacionesSQL = "DELETE FROM Asignaciones WHERE Aula_idAula IN (SELECT idAula FROM Aula WHERE Edificio_idEdificio = ?)";
            try (PreparedStatement pstmtAsignaciones = con.prepareStatement(eliminarAsignacionesSQL)) {
                pstmtAsignaciones.setInt(1, idEdificio);
                pstmtAsignaciones.executeUpdate();
            }

            // Eliminar las aulas del edificio en la tabla Aula
            String eliminarAulasSQL = "DELETE FROM Aula WHERE Edificio_idEdificio = ?";
            try (PreparedStatement pstmtAulas = con.prepareStatement(eliminarAulasSQL)) {
                pstmtAulas.setInt(1, idEdificio);
                pstmtAulas.executeUpdate();
            }

            // Eliminar el edificio en la tabla Edificio
            String eliminarEdificioSQL = "DELETE FROM Edificio WHERE idEdificio = ?";
            try (PreparedStatement pstmtEdificio = con.prepareStatement(eliminarEdificioSQL)) {
                pstmtEdificio.setInt(1, idEdificio);
                pstmtEdificio.executeUpdate();
            }

            con.close();
            JOptionPane.showMessageDialog(null, "Edificio y aulas asociadas eliminados correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el aula: " + e.getMessage());
        }
    }

//////////////////////////////////////FIN ELIMINAR EDIFICI

////////////////////////////////////ELIMINAR CURSO

    private static void ventanaCursoEliminar(String opcionesConsulta) {
        JFrame ventanaCurso = new JFrame("Eliminar Curso");
        JPanel panelAula = new JPanel();
        panelAula.setLayout(new BoxLayout(panelAula, BoxLayout.Y_AXIS));
        panelAula.setBackground(new Color(244, 123, 32));
        ventanaCurso.getContentPane().add(panelAula);

        //
        JLabel labelTexto = new JLabel("Ingresar el nombre del curso que desea eliminar:");
        JTextField textField = new JTextField(2);
        labelTexto.setForeground(Color.WHITE); // Cambiar color del texto a blanco
        labelTexto.setFont(new Font("Arial", Font.BOLD, labelTexto.getFont().getSize()));
        JButton boton = new JButton("Aceptar");
        Dimension preferredSize = new Dimension(150, 30);
        boton.setPreferredSize(preferredSize);

        boton.addActionListener(e -> {
            int result = showConfirmationDialogCurso("Eliminar este curso eliminara todas las asignaciones en esta");

            if (result == JOptionPane.OK_OPTION) {
                String nombreCursoIngresado = textField.getText();
                eliminarCurso(nombreCursoIngresado);

                JOptionPane.showMessageDialog(null, "Curso eliminada : " + nombreCursoIngresado);
            } else {
                JOptionPane.showMessageDialog(null, "Acción cancelada.");
            }
        });

        panelAula.add(Box.createVerticalStrut(3));
        panelAula.add(labelTexto);
        panelAula.add(textField);

        panelAula.add(Box.createVerticalStrut(3));
        panelAula.add(boton);

        ventanaCurso.setSize(300, 200);
        ventanaCurso.setVisible(true);
    }

    private static int showConfirmationDialogCurso(String mensaje) {
        Object[] opciones = {"Aceptar", "Cancelar"};
        int resultado = JOptionPane.showOptionDialog(null, mensaje, "Confirmación",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                null, opciones, opciones[1]);

        return resultado;
    }


    private static void eliminarCurso(String nombreCurso) {
        try (Connection con = getConexion()) {
            // Obtener el ID del curso
            int idCurso = obtenerIdCursoPorNombre(nombreCurso);

            // Eliminar las asignaciones relacionadas en la tabla Asignaciones
            String eliminarAsignacionesSQL = "DELETE FROM Asignaciones WHERE Cursos_idCursos = ?";
            try (PreparedStatement pstmtAsignaciones = con.prepareStatement(eliminarAsignacionesSQL)) {
                pstmtAsignaciones.setInt(1, idCurso);
                pstmtAsignaciones.executeUpdate();
            }

            // Eliminar el curso en la tabla Cursos
            String eliminarCursoSQL = "DELETE FROM Cursos WHERE idCursos = ?";
            try (PreparedStatement pstmtCurso = con.prepareStatement(eliminarCursoSQL)) {
                pstmtCurso.setInt(1, idCurso);
                pstmtCurso.executeUpdate();
            }

            con.close();

            JOptionPane.showMessageDialog(null, "Curso y asignaciones asociadas eliminados correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el aula: " + e.getMessage());
        }
    }

//////////////////////////////////////FIN ELIMINAR CURSO

//////////////////////////////////////ELIMINAR ASIGNACION

    private static void ventanaAsignacionEliminar(String opcionesConsulta) {
        JFrame ventanaAsignacion = new JFrame("Eliminar Asignacion");
        JPanel panelAula = new JPanel();
        panelAula.setLayout(new BoxLayout(panelAula, BoxLayout.Y_AXIS));
        panelAula.setBackground(new Color(244, 123, 32));
        ventanaAsignacion.getContentPane().add(panelAula);

        //
        JLabel labelTextoA = new JLabel("Ingresar el edificio de la asignacion que deseas eliminar:");
        JTextField textField = new JTextField(3);
        JLabel labelTextoAb = new JLabel("Ingresar el número de la de la asignacion que deseas eliminar:");
        JTextField textFieldA = new JTextField(3);
        JLabel labelTextoAc = new JLabel("Ingresar el horario de la de la asignacion que deseas eliminar:"); //Requiere una funcion para ID del curso
        JTextField textFieldAb = new JTextField(3);
        JButton boton = new JButton("Aceptar");
        Dimension preferredSize = new Dimension(150, 30);
        boton.setPreferredSize(preferredSize);

        boton.addActionListener(e -> {
            int result = showConfirmationDialogAsignacion("Se eliminara la asignacion");

            if (result == JOptionPane.OK_OPTION) {
                String edificioIngresado = textField.getText();
                String numeroAulaIngresado = textField.getText();
                String horarioIngresado = textField.getText();
                eliminarAsignacion(edificioIngresado, numeroAulaIngresado, horarioIngresado);

                JOptionPane.showMessageDialog(null, "Edificio eliminada : " + edificioIngresado);
            } else {
                JOptionPane.showMessageDialog(null, "Acción cancelada.");
            }
        });

        panelAula.add(Box.createVerticalStrut(3));
        panelAula.add(labelTextoA);
        panelAula.add(textField);

        panelAula.add(Box.createVerticalStrut(3));
        panelAula.add(labelTextoAb);
        panelAula.add(textFieldA);

        panelAula.add(Box.createVerticalStrut(3));
        panelAula.add(labelTextoAc);
        panelAula.add(textFieldAb);

        panelAula.add(Box.createVerticalStrut(3));
        panelAula.add(boton);

        ventanaAsignacion.setSize(600, 200);
        ventanaAsignacion.setVisible(true);
    }

    private static int showConfirmationDialogAsignacion(String mensaje) {
        Object[] opciones = {"Aceptar", "Cancelar"};
        int resultado = JOptionPane.showOptionDialog(null, mensaje, "Confirmación",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                null, opciones, opciones[1]);

        return resultado;
    }


    private static void eliminarAsignacion(String nombreEdificio, String numAula, String horario) {
        try (Connection con = getConexion()) {
            // Obtener el ID de la asignación
            int idAsignacion = obtenerIdAsignacionPorAulaEdificioYHorario(nombreEdificio, numAula, horario);

            // Eliminar la asignación en la tabla Asignaciones
            String eliminarAsignacionSQL = "DELETE FROM Asignaciones WHERE idAsignaciones = ?";
            try (PreparedStatement pstmtAsignacion = con.prepareStatement(eliminarAsignacionSQL)) {
                pstmtAsignacion.setInt(1, idAsignacion);
                pstmtAsignacion.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Asignación eliminada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar la asignación: " + e.getMessage());
        }
    }


    private static int obtenerIdAsignacionPorAulaEdificioYHorario(String nombreEdificio, String numAula, String horario) {
        int idAsignacion = -1; // Valor predeterminado en caso de que no se encuentre la asignación
        int idEdificio= obtenerIdEdificioPorNombre(nombreEdificio);
        try (Connection con = getConexion()) {
            // Obtener el ID de la asignación en la tabla Asignaciones
            String query = "SELECT idAsignaciones FROM Asignaciones " +
                    "WHERE Aula_idAula = ? " +
                    "AND Aula_Edificio_idEdificio = ? " +
                    "AND horario = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, obtenerIdAulaPorNombreYNumero(idEdificio, numAula));
                pstmt.setInt(2, obtenerIdEdificioPorNombre(nombreEdificio));
                pstmt.setString(3, horario);

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    idAsignacion = rs.getInt("idAsignaciones");
                }

                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el idAsignacion: " + e.getMessage());
        }

        return idAsignacion;
    }

//////////////////////////////////////FIN ELIMINAR ASIGNACION




    //////obtener edificio por nombre

    private static int obtenerIdEdificioPorNombre(String nombreEdificio) {
        int idEdificio = -1; // Valor predeterminado en caso de que no se encuentre el edificio

        try {
            Connection con = getConexion();
            String query = "SELECT idEdificio FROM Edificio WHERE nombreEdificio = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, nombreEdificio);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    idEdificio = rs.getInt("idEdificio");
                }

                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el idEdificio: " + e.getMessage());
        }

        return idEdificio;
    }
/////////////////////////////////////////////////////////////aula termina


    private static JPanel getjPanel() {
        JPanel panelNuevaVentana = new JPanel();

        JLabel seleccionarTablasLabel = new JLabel("Seleccionar Tablas:");
        panelNuevaVentana.add(seleccionarTablasLabel);

        String[] opcionesInsertar = {"Edificios", "Aula",  "Cursos", "Asignaciones"};
        JComboBox<String> opcionInsertar = new JComboBox<>(opcionesInsertar);

        // Agregar ActionListener a la lista desplegable de consultas
            opcionInsertar.addActionListener(e -> {
            // Reflejar la consulta seleccionada en la etiqueta
            String consultaSeleccionada = (String) opcionInsertar.getSelectedItem();

            // Abrir una nueva ventana según la opción seleccionada
            if ("Insertar consulta".equals(consultaSeleccionada)) {
                abrirNuevaVentana();
            } else if ("Edificios".equals(consultaSeleccionada)) {
                ventanaEdificio(consultaSeleccionada);
            } else if ("Aula".equals(consultaSeleccionada)) {
                ventanaAula(consultaSeleccionada);
            } else if ("Cursos".equals(consultaSeleccionada)){
                ventanaCursos(consultaSeleccionada);
            } else if ("Asignaciones".equals(consultaSeleccionada)){
                ventanaAsignaciones(consultaSeleccionada);
            }
        });

        panelNuevaVentana.add(opcionInsertar);
        return panelNuevaVentana;
    }
    private static JPanel getjPanelEliminar() {
        JPanel panelNuevaVentana = new JPanel();

        JLabel seleccionarTablasLabel = new JLabel("Seleccionar Tablas:");
        panelNuevaVentana.add(seleccionarTablasLabel);

        String[] opcionesEliminar = {"Edificios", "Aula",  "Cursos", "Asignaciones"};
        JComboBox<String> opcionEliminar = new JComboBox<>(opcionesEliminar);

        // Agregar ActionListener a la lista desplegable de consultas
        opcionEliminar.addActionListener(e -> {
            // Reflejar la consulta seleccionada en la etiqueta
            String consultaSeleccionada = (String) opcionEliminar.getSelectedItem();
            // Abrir una nueva ventana según la opción seleccionada
            if ("Insertar consulta".equals(consultaSeleccionada)) {
                abrirNuevaVentana();
            } else if ("Edificios".equals(consultaSeleccionada)) {
                ventanaEdificioEliminar(consultaSeleccionada);
            } else if ("Aula".equals(consultaSeleccionada)) {
                ventanaAulaEliminar(consultaSeleccionada);
            } else if ("Cursos".equals(consultaSeleccionada)){
                ventanaCursoEliminar(consultaSeleccionada);
            } else if ("Asignaciones".equals(consultaSeleccionada)) {
                ventanaAsignacionEliminar(consultaSeleccionada);
            }

        });

        panelNuevaVentana.add(opcionEliminar);
        return panelNuevaVentana;
    }

    }

