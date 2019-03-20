package ru.regorov.rrvs.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100)
    protected String name;

    @NotBlank
    @Size(max = 100)
    private String login;

    @NotBlank
    @Size(min = 5, max = 100)
    private String password;

    public UserTo() {
    }

    public UserTo(Integer id, String name, String login, String password) {
        super(id);
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
