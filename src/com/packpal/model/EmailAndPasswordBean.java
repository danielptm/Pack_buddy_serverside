package com.packpal.model;

public class EmailAndPasswordBean {
	
    String email;
    String password;

    public EmailAndPasswordBean(String email, String password){
        this.email = email;
        this.password =password;

    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
