package ejem1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/posts")
public class GestionaPost {
    private static final String URL = "jdbc:mariadb://localhost:3306/placegiver";
    private static final String USER = "root";
    private static final String PASS = "";
    private static PreparedStatement ps = null;

    /**
     * It searches in the database for the most recent posts
     * @return A list with the posts founded or an Error Response in case there was any problem in the search
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPostsRecientes() {
        List<Post> posts = new ArrayList<Post>();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, USER, PASS);
                    Statement st = conexion.createStatement();
                    ResultSet rs = st.executeQuery("Select * from publicaciones ORDER BY fechaPublicacion DESC")) {
                while (rs.next()) {
                    posts.add(new Post(rs.getInt("id"), rs.getString("texto"), rs.getString("fechaPublicacion"),
                            rs.getString("usuario"), rs.getInt("idCategoria")));
                }

                GenericEntity<List<Post>> entity = new GenericEntity<List<Post>>(posts) {
                };
                return Response.ok(entity).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el Driver").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    /**
     * It searches in the database for all the post that belong in a certain category
     * @param categoria Category that wants to be search for the posts
     * @return A list with the posts founded or an Error Response in case there was any problem in the search
     */
    @GET
    @Path("/categoria")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPostsDeCategoria(@QueryParam("categoria") String categoria) {
        List<Post> posts = new ArrayList<Post>();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, USER, PASS);
                    Statement st = conexion.createStatement();
                    ResultSet rs = st.executeQuery("Select * from publicaciones WHERE idCategoria = "+ categoria +" ORDER BY fechaPublicacion DESC")) {
                while (rs.next()) {
                    posts.add(new Post(rs.getInt("id"), rs.getString("texto"), rs.getString("fechaPublicacion"),
                            rs.getString("usuario"), rs.getInt("idCategoria")));
                }

                GenericEntity<List<Post>> entity = new GenericEntity<List<Post>>(posts) {
                };
                return Response.ok(entity).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el Driver").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    /**
     * It searches in the database for all the post that belong to a certain user
     * @param nombre Name of the user that wants to be search for the posts
     * @return A list with the posts founded or an Error Response in case there was any problem in the search
     */
    @GET
    @Path("/{nombreUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodosLosPostDeUsuario(@PathParam("nombreUsuario") String nombre) {
        List<Post> posts = new ArrayList<Post>();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection(URL, USER, PASS);
                    Statement st = conexion.createStatement();
                    ResultSet rs = st.executeQuery("Select * from publicaciones WHERE usuario = '" + nombre
                            + "' ORDER BY fechaPublicacion DESC")) {
                while (rs.next()) {
                    posts.add(new Post(rs.getInt("id"), rs.getString("texto"), rs.getString("fechaPublicacion"),
                            rs.getString("usuario"), rs.getInt("idCategoria")));
                }

                GenericEntity<List<Post>> entity = new GenericEntity<List<Post>>(posts) {
                };
                return Response.ok(entity).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el Driver").build();
            }
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No encuentra el driver").build();
        }
    }

    /**
     * It lets you make a post with a predefined category
     * @param u Data of the post to submit
     * @return Returns an Ok or Error Response depending of it was available to make the Insert request or not
     */
    @POST
    @Path("/publicar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postPublicacion(Post u){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String sql = "INSERT INTO publicaciones (texto, fechaPublicacion, usuario, idCategoria) VALUES (?,CURRENT_TIMESTAMP,?,3)";
            try (Connection conexion = DriverManager.getConnection(URL, USER, PASS); Statement st = conexion.createStatement();) {
                ps = conexion.prepareStatement(sql);
                ps.setString(1, u.texto);
                ps.setString(2, u.usuario);
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
     * It lets you make a post on a category you want
     * @param u Data of the post to submit
     * @return Returns an Ok or Error Response depending of it was available to make the Insert request or not
     */
    @POST
    @Path("/publicarConCategoria")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postPublicacionConCategoria(Post u){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String sql = "INSERT INTO publicaciones (texto, fechaPublicacion, usuario, idCategoria) VALUES (?,CURRENT_TIMESTAMP,?,?)";
            try (Connection conexion = DriverManager.getConnection(URL, USER, PASS); Statement st = conexion.createStatement();) {
                ps = conexion.prepareStatement(sql);
                ps.setString(1, u.texto);
                ps.setString(2, u.usuario);
                ps.setInt(3, u.idCategoria);
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
     * Deletes a post in the database with a certain ID
     * @param id Id of the post that wants to be deleted
     * @return Returns an Ok or Error Response depending of the post was deleted or not
     */
    @DELETE
    @Path("/{idPost}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response borrarPost(@PathParam("idPost") int id) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String sql = "DELETE FROM publicaciones WHERE id = ?";
            try (Connection conexion = DriverManager.getConnection(URL, USER, PASS);
                    Statement st = conexion.createStatement();) {
                ps = conexion.prepareStatement(sql);
                ps.setInt(1, id);
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
