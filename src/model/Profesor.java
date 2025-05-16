package model;


public class Profesor extends Usuario {

    public Profesor(int id, String usuario, String contrasena, RolUsuario rol) {
        super(id, usuario, contrasena, rol);
    }

    public Profesor(String usuario, String contrasena, RolUsuario rol) {
        super(usuario, contrasena, rol);
    }

    @Override
    public void redirect() {
        //TODO: Agregar la vista
    }
}
