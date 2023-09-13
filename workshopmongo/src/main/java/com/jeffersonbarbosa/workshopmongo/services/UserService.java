package com.jeffersonbarbosa.workshopmongo.services;

import com.jeffersonbarbosa.workshopmongo.dto.UserDTO;
import com.jeffersonbarbosa.workshopmongo.entities.User;
import com.jeffersonbarbosa.workshopmongo.respositories.UserRepository;
import com.jeffersonbarbosa.workshopmongo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Nenhum usuário encontrado."));
    }
    @Transactional
    public UserDTO insert(UserDTO objDTO){
        User user = new User(objDTO.getId(), objDTO.getName(), objDTO.getEmail());
        userRepository.save(user);
        return new UserDTO(user);
    }

    @Transactional
    public void delete(String id){
        findById(id);
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDTO update(String id, UserDTO obj){
        try {

           final Optional<User> objUser = userRepository.findById(id);

            User entity = objUser.get();
            entity.setId(id);
            updateData(entity, obj);
            userRepository.save(entity);

            return new UserDTO(entity);

        }catch (NoSuchElementException e){
            throw new ObjectNotFoundException("User não encontrado!");
        }
    }


    //Método para atualizar usuário

    public void updateData(User entity, UserDTO obj){
        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());
    }


}
