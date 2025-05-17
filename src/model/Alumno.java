package model;

import Visual.menualumno;

public class Alumno extends Usuario {

    public Alumno(int id, String usuario, String contrasena, RolUsuario rol) {
        super(id, usuario, contrasena, rol);
    }

    public Alumno(String usuario, String contrasena, RolUsuario rol) {
        super(usuario, contrasena, rol);
    }

    @Override
    public void redirect() {
        new menualumno().setVisible(true);
    }
}
