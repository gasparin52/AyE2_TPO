package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Representa un proyecto completo del sistema.
// Es lo mas grande: contiene modulos, y cada modulo tiene tareas.
// Es la raiz del ArbolNArio.
public class Project {

    private String id;               // identificador, ej: "PROJ-001"
    private String name;             // nombre del proyecto, ej: "App de Gestion"
    private String description;      // descripcion del proyecto
    private List<Module> modules;    // lista de modulos que tiene el proyecto
    private Set<String> memberIds;   // ids de los usuarios que forman parte del proyecto
                                     // usamos Set para que no haya ids repetidos

    // Constructor: los modulos y miembros empiezan vacios
    public Project(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.modules = new ArrayList<>();  // sin modulos al principio
        this.memberIds = new HashSet<>();  // sin miembros al principio
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Module> getModules() {
        return modules;
    }

    public Set<String> getMemberIds() {
        return memberIds;
    }

    // Agrega un modulo al proyecto
    public void agregarModulo(Module modulo) {
        modules.add(modulo);
    }

    // Agrega un miembro al proyecto (si ya estaba, el Set lo ignora)
    public void agregarMiembro(String userId) {
        memberIds.add(userId);
    }

    @Override
    public String toString() {
        return "Proyecto[" + id + "] " + name + " | modulos: " + modules.size() + " | miembros: " + memberIds.size();
    }
}
