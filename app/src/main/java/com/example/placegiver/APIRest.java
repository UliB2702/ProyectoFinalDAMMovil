package com.example.placegiver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;

public class APIRest {

    public Usuario obtenerDatosUsuario(int id) {
        try {
            URL url = new URL(
                    "http://10.0.2.2:8080/placegiver/rest/usuarios?id=" + id );

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();
            System.out.println("Código HTTP: " + code);

            InputStream stream = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8) );

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
            { response.append(line.trim());
            }

            if (code == 200) {
                JSONObject obj = new JSONObject(response.toString());

                String user = obj.getString("nombre");
                String email = obj.getString("email");
                String desc = obj.getString("descripcion");
                String password = obj.getString("password");
                String fecha = obj.getString("fechaCreacion");

                return new Usuario(id, user, desc, email, password, fecha);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Usuario obtenerDatosUsuario(String email, String pass) {
        try {
            URL url = new URL(
                    "http://10.0.2.2:8080/placegiver/rest/usuarios?email=" + email  + "&pass="+pass);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();
            System.out.println("Código HTTP: " + code);

            InputStream stream = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8) );

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
            { response.append(line.trim());
            }

            if (code == 200) {
                JSONObject obj = new JSONObject(response.toString());

                int id = obj.getInt("id");
                String user = obj.getString("nombre");
                String desc = obj.getString("descripcion");
                String fecha = obj.getString("fechaCreacion");

                return new Usuario(id, user, desc, email, pass, fecha);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Post> obtenerPostsDeUsuario(int id){
        ArrayList<Post> posts = new ArrayList<>();
        try {
            URL url = new URL(
                    "http://10.0.2.2:8080/placegiver/rest/posts?id=" + id);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();
            System.out.println("Código HTTP: " + code);



            if (code == 200) {
                InputStream stream = conn.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8) );

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                { response.append(line.trim());
                }
                JSONArray array = new JSONArray(response.toString());

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    String texto = obj.getString("texto");
                    String fechaPublicacion = obj.getString("fechaPublicacion");
                    int idUsuario = obj.getInt("idUsuario");
                    int idCategoria = obj.getInt("idCategoria");

                    posts.add(new Post(id,texto,fechaPublicacion , idUsuario, idCategoria));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts;
    }
}
