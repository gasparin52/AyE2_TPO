package model;

// Representa un usuario del sistema (una persona del equipo)
public class User {

    // Atributos: la informacion que tiene un usuario
    private String id;        // identificador unico, ej: "USR-001"
    private String username;  // nombre de usuario, ej: "franco"
    private String email;     // correo electronico

    // Constructor: se llama cuando creamos un usuario nuevo
    public User(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Getters: metodos para leer los atributos desde afuera de la clase
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    // toString: se llama automaticamente cuando imprimimos el objeto con System.out.println
    @Override
    public String toString() {
        return "Usuario: " + username + " (id=" + id + ", email=" + email + ")";
    }
}
