package ejem1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuarios")
public class GestionaUsuario {

    private static final String URL = "jdbc:mariadb://localhost:3306/placegiver";
    private static final String USER = "root";
    private static final String PASS = "";
    private static PreparedStatement ps = null;

    public static void main(String[] args) {
    try {
        Connection con = DriverManager.getConnection(
            "jdbc:mariadb://localhost:3306/placegiver",
            "root",
            ""
        );
        System.out.println("Conectado correctamente");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    /**
     * It searches for a user using a name sent as a query param
     * @param nombre Name of the user that wants to be searched
     * @return The user data if the search was successful or an Error Response if there was any problems with it
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUsuario(@QueryParam("nombre") String nombre) {
        Usuario usuario = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String sql = "Select * from usuarios WHERE nombre = ?";
            try (Connection conexion = DriverManager.getConnection(URL, USER, PASS)) {
                ps = conexion.prepareStatement(sql);
                ps.setString(1, nombre);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    usuario = new Usuario(rs.getString("nombre"), rs.getString("descripcion"), rs.getString("email"), rs.getString("password"),
                            (rs.getDate("fechaCreacion").toString()));
                }
                return Response.ok(usuario).build();
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(e.getMessage())
                        .build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }
    
    
    /**
     * It confirms if there is a user with a certain name and password in the database
     * @param nombre Name of the user for the search
     * @param password Password of the user for the search
     * @return Returns an Ok or Error response in case the search was successful or not
     */
    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuario(@QueryParam("nombre") String nombre, @QueryParam("pass") String password) {
        Usuario usuario = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String sql = "Select * from usuarios WHERE nombre = ? AND password = ?";
            try (Connection conexion = DriverManager.getConnection(URL, USER, PASS)) {
                ps = conexion.prepareStatement(sql);
                ps.setString(1, nombre);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    usuario = (new Usuario(rs.getString("nombre"), rs.getString("descripcion"), rs.getString("email"), rs.getString("password"), rs.getDate("fechaCreacion").toString()));
                }
                return Response.ok(usuario).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el Driver").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    /**
     * Creates a user in the database based on the data sent as a param
     * @param u Data of the user that wants to be created
     * @return Returns an Ok or Error response in case the Insert was correct or not
     */
    @POST
    @Path("/registro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUsuario(Usuario u){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String sql = "INSERT INTO usuarios (nombre, descripcion, email, password, fechaCreacion) VALUES (?,?,?,?,CURRENT_TIMESTAMP)";
            try (Connection conexion = DriverManager.getConnection(URL, USER, PASS); Statement st = conexion.createStatement();) {
                ps = conexion.prepareStatement(sql);
                ps.setString(1, u.nombre);
                ps.setString(2, u.descripcion);
                ps.setString(3, u.email);
                ps.setString(4, u.password);
                int num = ps.executeUpdate();
                return Response.ok("{\"success\": true, \"filas\": " + num + "}").build();
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"success\": false, \"message\": \"Error en BD\"}")
                .build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity("{\"success\": false, \"message\": \"Driver no encontrado\"}")
            .build();
        }
    }
    
    /**
     * Updates an user's data based on what is sent as a param
     * @param u Data of the user that wants to be updated
     * @return Returns an Ok or Error response in case the Update was correct or not
     */
    @PUT
    @Path("/actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUsuario(Usuario u){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String sql = "UPDATE usuarios SET descripcion = ?, email = ?, password = ? WHERE nombre = ?";
            try (Connection conexion = DriverManager.getConnection(URL, USER, PASS); Statement st = conexion.createStatement();) {
                ps = conexion.prepareStatement(sql);
                ps.setString(1, u.descripcion);
                ps.setString(2, u.email);
                ps.setString(3, u.password);
                ps.setString(4, u.nombre);
                int num = ps.executeUpdate();
                return Response.ok("{\"success\": true, \"filas\": " + num + "}").build();
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"success\": false, \"message\": \"Error en BD\"}")
                .build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity("{\"success\": false, \"message\": \"Driver no encontrado\"}")
            .build();
        }
    }

} 
