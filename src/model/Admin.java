package model;



public class Admin extends Usuario {

    public Admin(int id, String usuario, String contrasena, RolUsuario rol) {
        super(id, usuario, contrasena, rol);
    }

    @Override
    public void redirect() {
    }
}
