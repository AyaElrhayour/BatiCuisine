package org.youcode.baticuisine.entities;

import java.util.UUID;

public class Client {
    private UUID id;
    private String name;
    private String address;
    private String telephone;
    private Boolean isProfessional;

    public Client(){}

    public Client(UUID id, String name, String address, String telephone, Boolean isProfessional){
        this.id = id;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.isProfessional = isProfessional;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean getProfessional() {
        return isProfessional;
    }

    public void setProfessional(Boolean professional) {
        isProfessional = professional;
    }
}