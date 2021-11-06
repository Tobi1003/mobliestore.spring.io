package com.fpt.MobileStore;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginForm {
	@NotNull
    @Size(min = 3, max = 30)
    private String userName;

    @NotNull
    @Min(1)
    private String password;

    public String getuserName() {
        return this.userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "LoginForm(UserName: " + this.userName + ", Password: " + this.password + ")";
    }
}
