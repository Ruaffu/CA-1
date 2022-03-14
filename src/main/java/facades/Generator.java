package facades;

import dtos.PersonDTO;
import entities.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Generator {

    private static void generate() {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        Facade fe = Facade.getFacade(emf);

        Phone ph1 = new Phone("11111111", "Mobil");
        Phone ph2 = new Phone("22222222", "Mobil");
        Phone ph3 = new Phone("33333333", "Fastnet");
        Phone ph4 = new Phone("44444444", "Mobil");

        CityInfo c1 = new CityInfo("4040", "Jyllinge");
        CityInfo c2 = new CityInfo("2800", "Kongens Lyngby");
        CityInfo c3 = new CityInfo("2650", "Hvidovre");
        CityInfo c4 = new CityInfo("8210", "Aarhus V");

        Address a1 = new Address("Jyllingevej", "1", c1);
        Address a2 = new Address("Lyngbyvej", "2", c2);
        Address a3 = new Address("Hvidovrevej", "3", c3);
        Address a4 = new Address("Aarhusvej", "4", c4);

        Hobby h1 = new Hobby("Fodbold", "https://en.wikipedia.org/wiki/Soccer");
        Hobby h2 = new Hobby("Videospil", "https://en.wikipedia.org/wiki/Video_gaming");
        Hobby h3 = new Hobby("Film", "https://en.wikipedia.org/wiki/Movies");
        Hobby h4 = new Hobby("Bordfodbold", "https://en.wikipedia.org/wiki/Table_football");

        //First person has 1 phone number, an address and 1 hobby
        Person p1 = new Person("Alice", "A.", "alice@awesome.com");
        p1.addPhone(ph1);
        p1.setAddress(a1);
        p1.addHobby(h1); //sharing with person 4 (dave)

        //Second person has more than one phone number and hobby and an address
        Person p2 = new Person("Bob", "B.", "bob@bad.com");
        p2.addPhone(ph2);
        p2.addPhone(ph3);
        p2.setAddress(a2);
        p2.addHobby(h2);
        p2.addHobby(h3);
        p2.addHobby(h4); //sharing with person 4 (dave)

        //Third person has 1 phone number and an address
        Person p3 = new Person("Charlie", "C.", "charlie@charming.com");
        p3.addPhone(ph4);
        p3.setAddress(a3);

        //Fourth person has 1 hobby and an address
        Person p4 = new Person("Dave", "D.", "dave@dark.com");
        p4.setAddress(a4);
        p4.addHobby(h1); //sharing with person 1 (Alice)
        p4.addHobby(h4); //sharing with person 2 (bob)

        //Fifth person has only an address
        Person p5 = new Person("Eva", "E.", "eva@expert.com");
        p5.setAddress(a3); //sharing with person 3 (charlie)

//        fe.create(new PersonDTO(p1));
//        fe.create(new PersonDTO(p2));
//        fe.create(new PersonDTO(p3));
//        fe.create(new PersonDTO(p4));
//        fe.create(new PersonDTO(p5));



//        p1DTO = new PersonDTO(p1);
//        p2DTO = new PersonDTO(p2);
//        p3DTO = new PersonDTO(p3);
//        p4DTO = new PersonDTO(p4);
//        p5DTO = new PersonDTO(p5);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(ph1);
            em.persist(ph2);
            em.persist(ph3);
            em.persist(ph4);
            em.persist(h1);
            em.persist(h2);
            em.persist(h3);
            em.persist(h4);
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.persist(c4);
            em.persist(a1);
            em.persist(a2);
            em.persist(a3);
            em.persist(a4);
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(p4);
            em.persist(p5);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        generate();
    }


}
