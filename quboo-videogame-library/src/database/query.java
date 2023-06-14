package database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class query {
    public static boolean ComprobarUsuario(String username) {
        String query = "SELECT Nombre_usuario FROM Usuarios WHERE Nombre_usuario='" + username + "'";
        try {
            Statement stmt = MainD.conection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("Nombre_usuario").equals(username)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            return false;
        }
    }
    public static boolean ComprobarContrasena(String password) {
        String query = "SELECT Contrasena_usuario FROM Usuarios WHERE Contrasena_usuario='" + password + "'";
        try {
            Statement stmt = MainD.conection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("Contrasena_usuario").equals(password)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            return false;
        }
    }
    public static boolean ComprobarUsuarioContrasena(String username, String password) {
        String query = "SELECT Nombre_usuario,Contrasena_usuario FROM Usuarios WHERE Nombre_usuario='" + username + "' OR Contrasena_usuario='" + password + "'";
        try {
            Statement stmt = MainD.conection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("Nombre_usuario").equals(username) && rs.getString("Contrasena_usuario").equals(password)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            return false;
        }
    }
    public static void CrearJugador(String username, String password) {
        String query = "INSERT INTO Usuarios (Nombre_usuario,Contrasena_usuario,Descripcion_usuario,Monedas_juego) VALUES ('" + username + "', '" + password + "', 'Hi, im "+ username +".', 20)";
        try {
            Statement stmt = MainD.conection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
        }
    }
    public static String ObtenerDescripcionUsuario(String username) {
        String query = "SELECT Descripcion_usuario FROM Usuarios WHERE Nombre_usuario='" + username + "'";
        try {
            Statement stmt = MainD.conection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                return rs.getString("Descripcion_usuario");
            }
            return "Usuario no encontrado";
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            return "Error al obtener la descripcion del usuario.";
        }
    }
    public static String ObtenerRangoUsuario(String username) {
        String query = "SELECT R.Nombre_rango FROM Usuarios U INNER JOIN Rangos R ON U.Id_rango = R.Id_rango WHERE U.Nombre_usuario ='"+username+"'";
        try {
            PreparedStatement stmt = MainD.conection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Nombre_rango");
            } else {
                return "Rango no encontrado";
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            return "Error al obtener el rango del usuario.";
        }
    }
    public static String ObtenerInfoRangoUsuario(String username) {
        String query = "SELECT R.Info_rango FROM Usuarios U INNER JOIN Rangos R ON U.Id_rango = R.Id_rango WHERE U.Nombre_usuario ='"+username+"'";
        try {
            PreparedStatement stmt = MainD.conection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Info_rango");
            } else {
                return "Rango no encontrado";
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            return "Error al obtener el rango del usuario.";
        }
    }
    public static int obtenerMonedasUsuario(String username) {
        String query = "SELECT Monedas_juego FROM Usuarios WHERE Nombre_usuario ='"+username+"'";
        try {
            PreparedStatement stmt = MainD.conection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Monedas_juego");
            } else {
                System.out.println("Usuario no encontrado.");
                return -1;
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            return -1;
        }
    }
    public static int obtenerPuntosPong(String username) {
        String query = "SELECT J.Puntos_juego FROM Juegos J INNER JOIN Usuarios U ON J.Id_usuario = U.Id_usuario WHERE U.Nombre_usuario ='"+username+"' AND J.Nombre_juego = 'pong'";
        try {
            PreparedStatement stmt = MainD.conection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Puntos_juego");
            } else {
                System.out.println("Juego no encontrado.");
                return -1;
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            return -1;
        }
    }
    public static int obtenerPongComprado(String username) {
        String query = "SELECT J.Comprado FROM Juegos J INNER JOIN Usuarios U ON J.Id_usuario = U.Id_usuario WHERE U.Nombre_usuario ='"+username+"' AND J.Nombre_juego = 'pong'";
        try {
            PreparedStatement stmt = MainD.conection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Comprado");
            } else {
                System.out.println("Juego no encontrado.");
                return -1;
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            return -1;
        }
    }
    public static int obtenerPuntosSnake(String username) {
        String query = "SELECT J.Puntos_juego FROM Juegos J INNER JOIN Usuarios U ON J.Id_usuario = U.Id_usuario WHERE U.Nombre_usuario ='"+username+"' AND J.Nombre_juego = 'snake'";
        try {
            PreparedStatement stmt = MainD.conection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Puntos_juego");
            } else {
                System.out.println("Juego no encontrado.");
                return -1;
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            return -1;
        }
    }
    public static int obtenerSnakeComprado(String username) {
        String query = "SELECT J.Comprado FROM Juegos J INNER JOIN Usuarios U ON J.Id_usuario = U.Id_usuario WHERE U.Nombre_usuario ='"+username+"' AND J.Nombre_juego = 'snake'";
        try {
            PreparedStatement stmt = MainD.conection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Comprado");
            } else {
                System.out.println("Juego no encontrado.");
                return -1;
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            return -1;
        }
    }
    public static int obtenerPuntosPacman(String username) {
        String query = "SELECT J.Puntos_juego FROM Juegos J INNER JOIN Usuarios U ON J.Id_usuario = U.Id_usuario WHERE U.Nombre_usuario ='"+username+"' AND J.Nombre_juego = 'pacman'";
        try {
            PreparedStatement stmt = MainD.conection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Puntos_juego");
            } else {
                System.out.println("Juego no encontrado.");
                return -1;
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            return -1;
        }
    }
    public static int obtenerPacmanComprado(String username) {
        String query = "SELECT J.Comprado FROM Juegos J INNER JOIN Usuarios U ON J.Id_usuario = U.Id_usuario WHERE U.Nombre_usuario ='"+username+"' AND J.Nombre_juego = 'pacman'";
        try {
            PreparedStatement stmt = MainD.conection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Comprado");
            } else {
                System.out.println("Juego no encontrado.");
                return -1;
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            return -1;
        }
    }
    public static int obtenerPuntosJumpan(String username) {
        String query = "SELECT J.Puntos_juego FROM Juegos J INNER JOIN Usuarios U ON J.Id_usuario = U.Id_usuario WHERE U.Nombre_usuario ='"+username+"' AND J.Nombre_juego = 'jumpman'";
        try {
            PreparedStatement stmt = MainD.conection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Puntos_juego");
            } else {
                System.out.println("Juego no encontrado.");
                return -1;
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            return -1;
        }
    }
    public static int obtenerJumpanComprado(String username) {
        String query = "SELECT J.Comprado FROM Juegos J INNER JOIN Usuarios U ON J.Id_usuario = U.Id_usuario WHERE U.Nombre_usuario ='"+username+"' AND J.Nombre_juego = 'jumpman'";
        try {
            PreparedStatement stmt = MainD.conection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Comprado");
            } else {
                System.out.println("Juego no encontrado.");
                return -1;
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            return -1;
        }
    }
    public static int obtenerPuntosTypingtest(String username) {
        String query = "SELECT J.Puntos_juego FROM Juegos J INNER JOIN Usuarios U ON J.Id_usuario = U.Id_usuario WHERE U.Nombre_usuario ='"+username+"' AND J.Nombre_juego = 'typingtest'";
        try {
            PreparedStatement stmt = MainD.conection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Puntos_juego");
            } else {
                System.out.println("Juego no encontrado.");
                return -1;
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            return -1;
        }
    }
    public static int obtenerTypingtestComprado(String username) {
        String query = "SELECT J.Comprado FROM Juegos J INNER JOIN Usuarios U ON J.Id_usuario = U.Id_usuario WHERE U.Nombre_usuario ='"+username+"' AND J.Nombre_juego = 'typingtest'";
        try {
            PreparedStatement stmt = MainD.conection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Comprado");
            } else {
                System.out.println("Juego no encontrado.");
                return -1;
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            return -1;
        }
    }
    public static void JuegoCompra(String username) {
        String query = "UPDATE Usuarios SET Monedas_juego = Monedas_juego - 20 WHERE Nombre_usuario ='"+username+"'";
        try {
            PreparedStatement stmt = MainD.conection.prepareStatement(query);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Ha dado el dinero con éxito.");
            } else {
                System.out.println("No se encontró el usuario con el nombre especificado.");
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
        }
    }
    public static void CambiarComprado(String username, String game) {
        String query = "UPDATE Juegos SET Comprado = 1 WHERE Nombre_juego ='"+game+"' AND Id_usuario = (SELECT Id_Usuario FROM Usuarios WHERE Nombre_usuario ='"+username+"')";
        try {
            PreparedStatement stmt = MainD.conection.prepareStatement(query);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Juego comprado con éxito.");
            } else {
                System.out.println("No se encontró el usuario con el nombre especificado.");
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
        }
    }
    public static int obtenerTotalPuntos(String username) {
        String query = "SELECT SUM(J.Puntos_juego) AS Total_puntos FROM Juegos J INNER JOIN Usuarios U ON J.Id_usuario = U.Id_usuario WHERE U.Nombre_usuario ='"+username+"'";
        try {
            PreparedStatement stmt = MainD.conection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total_puntos");
            } else {
                System.out.println("Usuario no encontrado.");
                return -1;
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            return -1;
        }
    }
    public static void actualizarDescripcion(String descripcion, String username) {
        String query = "UPDATE Usuarios SET Descripcion_usuario = ? WHERE Nombre_usuario = ?";
        try {
            PreparedStatement stmt = MainD.conection.prepareStatement(query);
            stmt.setString(1, descripcion);
            stmt.setString(2, username);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Descripción actualizada con éxito.");
            } else {
                System.out.println("No se encontró el usuario con el nombre especificado.");
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
        }
    }
}