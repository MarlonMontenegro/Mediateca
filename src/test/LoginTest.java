package test;

import controllers.LoginController;

public class LoginTest {

    public static void main(String[] args) {
        LoginController loginController = new LoginController();

        System.out.println("Probando inicio de sesión con credenciales incorrectas:");
        loginController.iniciarSesion("usuarioNoExiste", "1234");

        System.out.println("Probando inicio de sesión con credenciales correctas:");
        loginController.iniciarSesion("prueba_user", "1234");
    }
}
