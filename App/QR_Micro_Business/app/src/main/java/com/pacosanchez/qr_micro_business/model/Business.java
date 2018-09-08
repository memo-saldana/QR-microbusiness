package com.pacosanchez.qr_micro_business.model;

public class Business {

    private String businessName;
    private String token;
    private String email;
    private String password;
    private String newPassword;


    public void setBusinessName(String businessName){
        this.businessName = businessName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public String getBusinessName(){
        return businessName;
    }

    public void setNewPassword(String newPassword){
        this.newPassword = newPassword;
    }

    public void setToken(String token){
        this.token = token;
    }
}