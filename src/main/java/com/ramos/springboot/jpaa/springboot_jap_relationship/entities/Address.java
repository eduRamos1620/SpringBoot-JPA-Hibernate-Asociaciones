package com.ramos.springboot.jpaa.springboot_jap_relationship.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private Integer number;

    //constructores ------------------------
    public Address(){

    }
    public Address(String street, Integer number){
        this.street = street;
        this.number = number;
    }

    //Metodos getter and setter------------------------
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getStreet(){
        return street;
    }
    public void setStreet(String street){
        this.street = street;
    }

    public Integer getNumbre(){
        return number;
    }
    public void setNumber(Integer number){
        this.number = number;
    }

    //ToString ----------------
    @Override
    public String toString() {
        return "Address [id=" + id + ", street=" + street + ", number=" + number + "]";
    }    
}
