/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import model.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserServiceImpl implements UserService {

    private static final AtomicInteger counter = new AtomicInteger();
    static List<User> users = new ArrayList<User>(
            Arrays.asList(
                    new User(counter.incrementAndGet(), "Diego Vasco"),
                    new User(counter.incrementAndGet(), "Andres Rojas"),
                    new User(counter.incrementAndGet(), "James Rodriguez"),
                    new User(counter.incrementAndGet(), "Cristina Ronaldo"),
                    new User(counter.incrementAndGet(), "Leo Messi")));


    @Override
    public List<User> getAll(int offset, int count) {
        return users;
    }

    @Override
    public User findById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }

        return null;

    }
    
    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }

        return null;

    }

    @Override
    public void create(User user) {
        user.setId(counter.incrementAndGet());
        users.add(user);
    }
    
 @Override
    public void update (User user) {
        int index = users.indexOf (user);
        users.set(index, user);
    }
    
 @Override
    public void delete (int id) {
        User user = findById(id);
        users.remove(user);
    }
    
 @Override
    public boolean exists (User user) {
       return findByName(user.getUsername()) !=null;
    }
}