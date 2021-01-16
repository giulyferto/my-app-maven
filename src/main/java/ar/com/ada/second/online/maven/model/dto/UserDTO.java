package ar.com.ada.second.online.maven.model.dto;

import lombok.*;

//Todas estas anotaciones aplican codigo en tiempo de comilacion
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {

    private Integer id;
    private String nickname;
    private String email;

    public UserDTO(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
