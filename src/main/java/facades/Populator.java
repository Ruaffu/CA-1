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

import java.util.HashSet;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        Facade fe = Facade.getFacade(emf);

        Person p1, p2;

        p1 = new Person("Some txt", "More text", "some more text", new HashSet<>(),
                new Address("Chr. den 8. vej", "",
                        new CityInfo("8600", "Silkeborg")), new HashSet<>());
        p1.addPhone(new Phone("12345678", "fastnet"));
        Hobby h1 = new Hobby("fodbold", "spark", new HashSet<>());
        p1.addHobby(h1);

        p2 = new Person("aaa", "bbb", "ccc", new HashSet<>(),
                new Address("Mobo vej", "",
                        new CityInfo("4040", "Jyllinge")), new HashSet<>());
        p2.addPhone(new Phone("87654321", "mobile"));
        p2.addHobby(h1);


        fe.create(new PersonDTO(p1));
        fe.create(new PersonDTO(p2));
    }
    
    public static void main(String[] args) {
        populate();
    }
}
