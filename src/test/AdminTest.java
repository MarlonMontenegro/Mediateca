package test;

import controllers.AdminController;
import model.*;

import java.util.List;

public class AdminTest {
    public static void main(String[] args) {
        AdminController adminController = new AdminController();

        // Crear 3 usuarios: Admin, Profesor y Alumno
        Usuario admin = new Admin(0, "adminTest", "admin123", RolUsuario.ADMIN);
        Usuario profesor = new Profesor(0, "profesorTest", "prof123", RolUsuario.PROFESOR);
        Usuario alumno = new Alumno(0, "alumnoTest", "alumno123", RolUsuario.ALUMNO);

        // Crear usuarios en la base de datos
        boolean creadoAdmin = adminController.crearUsuario(admin);
        boolean creadoProfesor = adminController.crearUsuario(profesor);
        boolean creadoAlumno = adminController.crearUsuario(alumno);

        // Mostrar resultados de creación
        System.out.println("Admin creado: " + creadoAdmin);
        System.out.println("Profesor creado: " + creadoProfesor);
        System.out.println("Alumno creado: " + creadoAlumno);

        System.out.println("\n--- Lista de todos los usuarios registrados ---");
        List<Usuario> usuarios = adminController.listarUsuarios();

        for (Usuario u : usuarios) {
            System.out.println("ID: " + u.getId() +
                    " | Usuario: " + u.getUsuario() +
                    " | Rol: " + u.getRol());
        }
    }
}