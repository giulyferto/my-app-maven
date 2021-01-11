package ar.com.ada.second.online.maven.model.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Todas estas anotaciones aplican codigo en tiempo de comilacion
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserDAO {
    private Integer id;
    private String nickname;
    private String email;
}
