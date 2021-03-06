/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import model.User;
import java.util.List;

public interface UserService{
    List<User> getAll(int offset, int count);
    User findById(int id);
    User findByName(String name);
    void create(User user);
    void update(User user);
    void delete(int id);
    boolean exists(User user);
}