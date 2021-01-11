package ar.com.ada.second.online.maven.controller;

import ar.com.ada.second.online.maven.view.MainView;
import ar.com.ada.second.online.maven.view.UserView;

public class UserController {
    private static  UserController userController;
    private MainView mainView = MainView.getInstance();
    private UserView userView = UserView.getInstance();

    private UserController() {

    }
    public static UserController getInstance() {
        if (userController == null) userController = new UserController();
        return userController;
    }
}
