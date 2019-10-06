/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import model.User;
import service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

// ==== Obtener todos lo usuario ===
    @RequestMapping(method = RequestMethod.GET) // --Metodo GET --obtener
    public ResponseEntity<List<User>> getAll(@RequestParam(value = "offset", defaultValue = "0") int offset, // --Respuesta de todos los usuario getALL--
            @RequestParam(value = "count", defaultValue = "10") int count) {
        LOG.info("usuario no encontrado: {}, y contados: {},", offset, count); // --Log de petición--
        List<User> users = userService.getAll(offset, count); // --Metodo para obtener los usuarios--

        if (users == null || users.isEmpty()) {
            LOG.info("usuario no encontrado");
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
    
// === Obtener Usuario por ID ===
    @RequestMapping(value = "{id}", method = RequestMethod.GET) // peticion Get
    public ResponseEntity<User> get(@PathVariable("id") int id) { // Id de referencia
        LOG.info("usuario obtenido con el id: {}", id); //log de salida
        User user = userService.findById(id); // metodo para buscar por el ID
        
        if (user == null){
            LOG.info("usuario con id {} no encontrado", id);
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
// === Crear nuevo usuario ===
    @RequestMapping(method = RequestMethod.POST) // POST(INSERTAR)
    public ResponseEntity<Void> create(@RequestBody User user, UriComponentsBuilder ucBuilder){ // Crear User
        LOG.info("creando nuevo usuario: {}", user);
        if (userService.exists(user)){
            LOG.info("el usuario con nombre " + user.getUsername() + " ya existe"); // si existe, log de salida
                return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        userService.create(user);
        HttpHeaders headers= new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri()); // se crea el usuario y se asigna ID
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    
// === Actualizar Usuario ===
    @RequestMapping(value = "{id}", method = RequestMethod.PUT) // PUT (Actualizar)
    public ResponseEntity<User> update(@PathVariable int id, @RequestBody User user){
    LOG.info("actualizando usuario: {}", user);
    User currentUser = userService.findById(id);
        if (currentUser == null){
            LOG.info("Usuario con id {} no encontrado",id);
                return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        currentUser.setId(user.getId());
        currentUser.setUsername(user.getUsername());
        userService.update(user);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }
    
// === Borrar Usuario ===
     @RequestMapping(value = "{id}", method = RequestMethod.DELETE) //  (Borrar)
    public ResponseEntity<Void> delete(@PathVariable ("id") int id){
    LOG.info("borrando usuario con id: {}", id);
    User user = userService.findById(id);
        if (user == null){
            LOG.info("No se puede borrar usuario con id {} no encontrado", id);
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        
        userService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}