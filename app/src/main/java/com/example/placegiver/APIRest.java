package com.example.placegiver;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;

public class APIRest {

    public interface LoginCallback {
        void onLoginResult(boolean success, Usuario u);

    }

    public interface PostsCallback {
        void onPostsResult(boolean success, ArrayList<Post> posts);
    }

    public interface RegistroCallback{
        void onRegistroResult(boolean succes);
    }
    //API de Usuarios

    public void crearUsuario(String nombre, String password, String email, Date fecha, RegistroCallback callback){
        new Thread(() -> {
            try{
                URL url = new URL("http://10.0.2.2:8080/apirest_placegiver/rest/usuarios/registro");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);
                JSONObject json = new JSONObject();
                json.put("nombre", nombre);
                json.put("descripcion", "");
                json.put("password", password);
                json.put("email", email);
                json.put("fechaCreacion", fecha);

                System.out.println(json);
                try(OutputStream os = con.getOutputStream()) {
                    os.write(json.toString().getBytes(StandardCharsets.UTF_8));
                }
                System.out.println("jj");
                int code = con.getResponseCode();
                Log.i("API", "Código respuesta: " + code);
                callback.onRegistroResult(true);

            } catch (Exception e) {
                e.printStackTrace();
                callback.onRegistroResult(false);
            }
        }).start();
    }
    public void obtenerDatosUsuario(String nombre, LoginCallback callback) {
        new Thread(() ->  {
        try {
            URL url = new URL(
                    "http://10.0.2.2:8080/apirest_placegiver/rest/usuarios?nombre=" + nombre);

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

                String nombreApi = obj.getString("nombre");
                String email = obj.getString("email");
                String desc = obj.getString("descripcion");
                String password = obj.getString("password");
                String fecha = obj.getString("fechaCreacion");
                Usuario u = new Usuario(nombreApi, desc, email, password, fecha);
                System.out.println(obj);
                callback.onLoginResult(true, u);
            }
            else {
                callback.onLoginResult(false, null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            callback.onLoginResult(false, null);
        }}).start();

    }

    public void obtenerDatosUsuario(String nombre, String pass, LoginCallback callback) {
        new Thread(() -> {try {
            URL url = new URL(
                    "http://10.0.2.2:8080/apirest_placegiver/rest/usuarios/login?nombre=" + nombre  + "&pass="+pass);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            System.out.println("HOLA");
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

                System.out.println(obj);
                String email = obj.getString("email");
                String desc = obj.getString("descripcion");
                String fecha = obj.getString("fechaCreacion");
                Usuario u = new Usuario(nombre, desc, email, pass, fecha);
                callback.onLoginResult(true, u);
            }else {
                callback.onLoginResult(false, null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            callback.onLoginResult(false, null);
        }}).start();


    }

    // API de Publicaciones

    public void obtenerPostsMasRecientes(PostsCallback callback){
        ArrayList<Post> posts = new ArrayList<>();
        new Thread(()-> {
            try {
                URL url = new URL(
                        "http://10.0.2.2:8080/placegiver/rest/posts");

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

                        int id = obj.getInt("id");
                        String texto = obj.getString("texto");
                        String fechaPublicacion = obj.getString("fechaPublicacion");
                        String nombre = obj.getString("usuario");
                        int idCategoria = obj.getInt("idCategoria");

                        posts.add(new Post(id,texto,fechaPublicacion , nombre, idCategoria));
                    }
                    callback.onPostsResult(true, posts);
                }
                else{
                    callback.onPostsResult(false, null);
                }

            } catch (Exception e) {
                e.printStackTrace();
                callback.onPostsResult(false, null);
            }
        }).start();

    }

    public void obtenerPostsDeUsuario(String nombre, PostsCallback callback){
        ArrayList<Post> posts = new ArrayList<>();
        new Thread(()-> {
            try {
                URL url = new URL(
                        "http://10.0.2.2:8080/placegiver/rest/posts?nombre=" + nombre);

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

                        int id = obj.getInt("id");
                        String texto = obj.getString("texto");
                        String fechaPublicacion = obj.getString("fechaPublicacion");
                        int idCategoria = obj.getInt("idCategoria");

                        posts.add(new Post(id,texto,fechaPublicacion , nombre, idCategoria));
                    }
                    callback.onPostsResult(true, posts);
                }
                else{
                    callback.onPostsResult(false, null);
                }

            } catch (Exception e) {
                e.printStackTrace();
                callback.onPostsResult(false, null);
            }
        }).start();

    }
}
