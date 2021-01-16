package ar.com.ada.second.online.maven.view;

import ar.com.ada.second.online.maven.model.dto.UserDTO;
import ar.com.ada.second.online.maven.utils.Keyboard;

import java.util.HashMap;

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
        System.out.println("| 5 | Regresar al menu principal");
        return Keyboard.getInputInteger();
    }

    public HashMap<String, String> getDataNewUser() {
        HashMap<String, String> data = new HashMap<>();
        System.out.print("Ingrese el nuevo Nickname: ");

//        String nickname = Keyboard.getInputString();
//        data.put("nickname",nickname);
        data.put("nickname", Keyboard.getInputString());

        System.out.print("Ingrese el nuevo email: ");
        data.put("email", Keyboard.getInputEmail());

        return data;
    }

    public void existingUser(){
        System.out.println("Oops el usuario ya existe en la base de datos");
        Keyboard.pressEnterKeyToContinue();
    }


    public void showNewUser(UserDTO dto){
        System.out.println("\nUsuario creado con exito");
        System.out.printf("id %d", dto.getId());
        System.out.printf("\nemail %s",dto.getEmail());
        System.out.printf("\nnickname %s\n",dto.getNickname());

     //   Keyboard.pressEnterKeyToContinue();
    }

}
