package model;

import Visual.menu;

public class Admin extends Usuario {

    public Admin(int id, String usuario, String contrasena, RolUsuario rol) {
        super(id, usuario, contrasena, rol);
    }

    public Admin(String usuario, String contrasena, RolUsuario rol) {
        super(usuario, contrasena, rol);
    }

    @Override
    public void redirect() {
        new menu().setVisible(true);
    }
}
