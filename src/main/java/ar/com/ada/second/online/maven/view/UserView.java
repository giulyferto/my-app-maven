package ar.com.ada.second.online.maven.view;

import ar.com.ada.second.online.maven.model.dao.UserDAO;
import ar.com.ada.second.online.maven.model.dto.UserDTO;
import ar.com.ada.second.online.maven.utils.CommandLineTable;
import ar.com.ada.second.online.maven.utils.Keyboard;
import ar.com.ada.second.online.maven.utils.Paginator;

import java.util.HashMap;
import java.util.List;

public class UserView {
    private static UserView userView;

    private UserView() {

    }

    public static UserView getInstance() {
        if (userView == null) userView = new UserView();
        return userView;
    }

    public void showTitleUserModule() {
        System.out.println("#####################################");
        System.out.println("#   Ada Social Network: Usuarios    #");
        System.out.println("#####################################\n");
    }

    public Integer userMenuSelectOption() {
        System.out.println("Que desea realizar?: ");
        System.out.println("| 1 | Crear usuario");
        System.out.println("| 2 | Lista de Usuarios ");
        System.out.println("| 3 | Editar usuario");
        System.out.println("| 5 | Regresar al menu principal");
        return Keyboard.getInputInteger();
    }

    public HashMap<String, String> getDataNewUser() {
        System.out.println("#####################################");
        System.out.println("#  Ada Social Network: Nuevo Usuario #");
        System.out.println("#####################################\n");

        HashMap<String, String> data = new HashMap<>();
        System.out.println("Ingrese el nuevo Nickname: ");

//        String nickname = Keyboard.getInputString();
//        data.put("nickname",nickname);
        data.put("nickname", Keyboard.getInputString());

        System.out.println("Ingrese el nuevo email: ");
        data.put("email", Keyboard.getInputEmail());

        return data;
    }

    public void existingUser() {
        System.out.println("Oops el usuario ya existe en la base de datos");
        Keyboard.pressEnterKeyToContinue();
    }


    public void showNewUser(UserDTO dto) {
        System.out.println("\nUsuario creado con exito");
        System.out.printf("id %d", dto.getId());
        System.out.printf("\nemail %s", dto.getEmail());
        System.out.printf("\nnickname %s\n", dto.getNickname());

        //   Keyboard.pressEnterKeyToContinue();
    }

    public String printUsersPerPage(List<UserDAO> users, List<String> paginator, String optionSelectEditOrDelete, boolean isHeaderShown) {
        if (isHeaderShown) {
            System.out.println("#####################################");
            System.out.println("#   Ada Social Network: Lista de Usuarios    #");
            System.out.println("#####################################\n");
        }
        CommandLineTable st = new CommandLineTable();
        st.setHeaders("ID", "Nickname", "Email");
        users.forEach(userDAO -> {
            st.addRow(
                    userDAO.getId().toString(),
                    userDAO.getNickname(),
                    userDAO.getEmail()
            );
        });
        st.print();
        if (optionSelectEditOrDelete != null && !optionSelectEditOrDelete.isEmpty())
            paginator.set(paginator.size() - 2, optionSelectEditOrDelete);

        System.out.println("\n+-------------------------------------------+");
        paginator.forEach(page -> System.out.println(page + " "));
        System.out.println("\n+-------------------------------------------+");
        return Keyboard.getInputString();
    }

    public void usersListNotFound() {
        System.out.println("No hay usuario registrados en la base de datos");
        Keyboard.pressEnterKeyToContinue();
    }

    public void selectUserIDToEditOrDeleteInfo(String action) {
        System.out.println("Seleccione el id para " + action + "de la siguiente lista de usuarios");
        Keyboard.pressEnterKeyToContinue();
    }

    public void userDoesntExist(Integer id) {
        System.out.println("No existe un usuario con el id " + id + "asociado");
        System.out.println("Seleccione un ID valido o 0 para cancelar");
    }

    public Integer userIDSelection(String action) {
        switch (action) {
            case Paginator.EDIT:
                action = "Editar";
                break;
        }
        System.out.println("Ingrese el numero de ID del usuario para " + action + " ó 0 para cancelar: \n");

        return Keyboard.getInputInteger();

    }

    public HashMap<String, String> getDataToEdit(UserDAO dao) {
        System.out.println("#####################################");
        System.out.println("#  Ada Social Network: Editar Usuario #");
        System.out.println("#####################################\n");

        HashMap<String, String> data = new HashMap<>();
        System.out.printf("Ingrese el nuevo Nickname (%s):  \n", dao.getNickname());

//        String nickname = Keyboard.getInputString();
//        data.put("nickname",nickname);
        data.put("nickname", Keyboard.getInputString());

        System.out.printf("Ingrese el nuevo email (%s): ", dao.getEmail());
        data.put("email", Keyboard.getInputEmail());

        return data;
    }

    public void showUser(UserDTO dto) {
        System.out.println("\nDatos del usuario: ");
        System.out.printf("id: %d", dto.getId());
        System.out.printf("\nEmail: %s", dto.getEmail());
        System.out.printf("\nNickname: %s\n", dto.getNickname());

        Keyboard.pressEnterKeyToContinue();
    }
}

