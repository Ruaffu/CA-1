/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import entities.*;

import javax.persistence.EntityManagerFactory;

import utils.EMF_Creator;


/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        Facade fe = Facade.getFacade(emf);

        Person person1 = new Person("First 1", "Last 1", "Email 1");
        Person person2 = new Person("First 2", "Last 2", "Email 2");
        Person person3 = new Person("First 3", "Last 3", "Email 3");

        Address address = new Address("street 1",  "", new CityInfo("7400", "Herning"));

        person1.setAddress(address);
        person2.setAddress(address);
        person3.setAddress(address);

        person1.addHobby(new Hobby("fodbold", "Spark"));
        person1.addPhone(new Phone("1234", "Home"));

        PersonDTO p1 = new PersonDTO(person1);
        PersonDTO p2 = new PersonDTO(person2);
        PersonDTO p3 = new PersonDTO(person3);

        fe.create(p1);
        fe.create(p2);
        fe.create(p3);
    }
    
    public static void main(String[] args) {
        populate();
    }
}
