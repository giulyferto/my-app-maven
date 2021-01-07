package ar.com.ada.second.online.maven.view;

public class UserView {
private static UserView userView;

private UserView(){

}
public static UserView getInstance(){
    if (userView == null) userView = new UserView();
    return userView;
}

}
