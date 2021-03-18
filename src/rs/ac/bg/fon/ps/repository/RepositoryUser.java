/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository;

import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.User;

/**
 *
 * @author Matija
 */
public class RepositoryUser {

    private final List<User> users;

    public RepositoryUser() {
        users = new ArrayList<User>() {
            {
                add(new User(1l, "Matija1", "Djekovic1", "admin1", "admin1"));
                add(new User(2l, "Matija2", "Djekovic2", "admin2", "admin2"));
            }
        };
    }

    public List<User> getAll() {

        return users;
    }
}
