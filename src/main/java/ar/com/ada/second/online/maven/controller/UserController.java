package ar.com.ada.second.online.maven.controller;

import ar.com.ada.second.online.maven.model.dao.JpaUserDAO;
import ar.com.ada.second.online.maven.model.dao.UserDAO;
import ar.com.ada.second.online.maven.model.dto.UserDTO;
import ar.com.ada.second.online.maven.view.MainView;
import ar.com.ada.second.online.maven.view.UserView;

import java.util.HashMap;

public class UserController {
    private static UserController userController;
    private MainView mainView = MainView.getInstance();
    private UserView userView = UserView.getInstance();
    private JpaUserDAO jpaUserDAO = JpaUserDAO.getInstance();

    private UserController() {

    }

    public static UserController getInstance() {
        if (userController == null) userController = new UserController();
        return userController;
    }

    public void init()  {
        boolean shouldItStay = true;
        userView.showTitleUserModule();


        while (shouldItStay) {
            Integer option = userView.userMenuSelectOption();
            switch (option) {
                case 1:
                    createNewUser();
                    break;
                case 5:
                    shouldItStay = false;
                    break;
                default:
                    mainView.invalidOption();
            }
        }
    }

    private void createNewUser() {
        HashMap<String, String> dataNewUser = userView.getDataNewUser();
        //1ra forma
        String nickname = dataNewUser.get("nickname");
        String email = dataNewUser.get("email");
        UserDTO userDTO = new UserDTO(nickname, email);
/**
 //2da forma A
 UserDTO userDTO = new userDTO();
 String nickname = dataNewUser.get("nickname");
 String email = dataNewUser.get("email");
 userDTO.setNickname(nickname);
 userDTO.setEmail(email);

 //2da B
 UserDTO userDTO = new UserDTO();
 userDTO.setNickname(dataNewUser.get("nickname"));
 userDTO.setEmail(dataNewUser.get("email"));
 */
//Validacion de registro en la base de datos
        try {
            jpaUserDAO.findByEmailAndNickname(userDTO.getEmail(), userDTO.getNickname());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            userView.existingUser();
            return;
        }

        System.out.println(userDTO.toString());
        UserDAO userDAO = UserDAO.toDAO(userDTO);

        jpaUserDAO.save(userDAO);

        userDTO.setId(userDAO.getId());

        userView.showNewUser(userDTO);
    }

}