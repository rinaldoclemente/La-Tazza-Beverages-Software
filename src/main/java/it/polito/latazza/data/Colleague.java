package it.polito.latazza.data;

/**
 * 
 */
public class Colleague {

    private Integer id;
    private String name;
    private String surname;
    private PersonalAccount pa;

    /**
     * Default constructor
     */

    public Colleague(Integer id, String name, String surname, PersonalAccount pa) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pa = pa;
    }


    /**
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return
     */
    public String getSurname() {
        return this.surname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public PersonalAccount getPa() {
        return pa;
    }

    public void setPa(PersonalAccount pa) {
        this.pa = pa;
    }
}