package com.java.march.junittest.person;

public interface PersonDao {

    Person getPerson(int id);

    boolean update(Person person);
}