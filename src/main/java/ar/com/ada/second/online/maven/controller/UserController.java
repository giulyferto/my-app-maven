package ar.com.ada.second.online.maven.controller;

import ar.com.ada.second.online.maven.model.dao.JpaUserDAO;
import ar.com.ada.second.online.maven.model.dao.UserDAO;
import ar.com.ada.second.online.maven.model.dto.UserDTO;
import ar.com.ada.second.online.maven.utils.Keyboard;
import ar.com.ada.second.online.maven.utils.Paginator;
import ar.com.ada.second.online.maven.view.MainView;
import ar.com.ada.second.online.maven.view.UserView;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

    public void init() {
        boolean shouldItStay = true;
        userView.showTitleUserModule();


        while (shouldItStay) {
            Integer option = userView.userMenuSelectOption();
            switch (option) {
                case 1:
                    createNewUser();
                    break;
                case 2:
                    showAllUsers();
                    break;
                case 3:
                    editUser();
                    break;
                case 4:
                    deleteUser();
                    break;
                case 5:
                    shouldItStay = false;
                    break;
                default:
                    mainView.invalidOption();
            }
        }
    }

    private void editUser() {
        UserDAO userToEdit = getUserToEditOrDelete(Paginator.EDIT);
        if (userToEdit != null) {
            HashMap<String, String> dataEditUser = userView.getDataToEdit(userToEdit);
            if (!dataEditUser.get("Nickname").isEmpty()) ;
            userToEdit.setNickname(dataEditUser.get("Nickname"));

            if (!dataEditUser.get("Email").isEmpty()) ;
            userToEdit.setNickname(dataEditUser.get("Email"));

            jpaUserDAO.save(userToEdit);
            UserDTO userDTO = UserDAO.toDTO(userToEdit);

            userView.showUser(userDTO);

        }
    }

    private void showAllUsers() {
        printRecordsPerPage(null, true);
    }

    private void deleteUser() {
        UserDAO userToDelete = getUserToEditOrDelete(Paginator.DELETE);
        if (userToDelete != null) {
            Boolean answer = userView.areYouSureToRemoveIt(userToDelete);
            if (answer) {
                Boolean hasDeleted = jpaUserDAO.delete(userToDelete);
                if (hasDeleted) {
                    userView.userHasBeenSuccessfullyRemoved();
                } else {
                    userView.errorWhenDeletingUser();
                }
            } else userView.editOrDeleteUserCancelled(Paginator.DELETE);
        } else {
            userView.editOrDeleteUserCancelled(Paginator.DELETE);
        }
    }


    private UserDAO getUserToEditOrDelete(String optionEditOrDelete) {
        boolean shouldIGetOut = false;
        Optional<UserDAO> userToEditOptional = Optional.empty();
        String actionInfo = Paginator.EDIT.equals(optionEditOrDelete) ? "Editar" : "Eliminar";
        userView.selectUserIDToEditOrDeleteInfo(actionInfo);

        Integer userIDToEdit = printRecordsPerPage(optionEditOrDelete, false);

        if (userIDToEdit != 0) {
            while (shouldIGetOut) {
                Optional<UserDAO> byID = jpaUserDAO.findByID(userIDToEdit);
                if (!userToEditOptional.isPresent()) {
                    userView.userDoesntExist(userIDToEdit);
                    userIDToEdit = userView.userIDSelection(optionEditOrDelete);
                    shouldIGetOut = (userIDToEdit == 0);
                } else {
                    shouldIGetOut = true;
                }
            }
        }

        return userToEditOptional.isPresent() ? userToEditOptional.get() : null;
    }

    private Integer printRecordsPerPage(String optionSelectEditOrDelete, boolean isHeaderShown) {
        int limit = 4,
                currentPage = 0,
                totalUsers,
                totalPages,
                userIdSelected = 0;
        List<UserDAO> users;
        List<String> paginator;

        boolean shouldGetOut = false;
        while (!shouldGetOut) {
            totalUsers = jpaUserDAO.getTotalRecords();
            totalPages = (int) Math.ceil((double) totalUsers / limit);
            paginator = Paginator.buildPaginator(currentPage, totalPages);
            users = jpaUserDAO.findAll(currentPage * limit, limit);
            if (users.isEmpty()) {
                String choice = userView.printUsersPerPage(users, paginator, optionSelectEditOrDelete, isHeaderShown);

                switch (choice) {
                    case "i":
                    case "I":
                        currentPage = 0;
                        break;

                    case "a":
                    case "A":
                        if (currentPage > 0) currentPage--;
                        break;
                    case "s":
                    case "S":
                        if (currentPage + 1 < totalPages) currentPage++;
                        break;
                    case "u":
                    case "U":
                        currentPage = totalPages - 1;
                        break;
                    case "q":
                    case "Q":
                        shouldGetOut = true;
                        break;
                    default:
                        if (optionSelectEditOrDelete != null) {
                            userIdSelected = Integer.parseInt(choice);
                            shouldGetOut = true;
                        } else {
                            if (choice.matches("^-?\\d+$")) {
                                int page = Integer.parseInt(choice);
                                if (page > 0 && page <= totalPages) currentPage = page - 1;
                            } else Keyboard.invalidData();
                        }
                }
            } else {
                shouldGetOut = true;
                userView.usersListNotFound();

            }
        }

        return userIdSelected;
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