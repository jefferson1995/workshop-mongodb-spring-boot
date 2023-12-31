package com.jeffersonbarbosa.workshopmongo.services;

import com.jeffersonbarbosa.workshopmongo.dto.UserDTO;
import com.jeffersonbarbosa.workshopmongo.entities.User;
import com.jeffersonbarbosa.workshopmongo.respositories.UserRepository;
import com.jeffersonbarbosa.workshopmongo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Flux<UserDTO> findAll() {
        return userRepository.findAll().map(user -> new UserDTO(user));
    }

    public Mono<UserDTO> findById(String id) throws InterruptedException, ExecutionException {
        return userRepository.findById(id).map(existingUser -> new UserDTO(existingUser))
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("Nenhum usuário encontrado!")));
    }

    @Transactional
    public Mono<UserDTO> insert(UserDTO objDTO) {
        User user = new User(objDTO.getId(), objDTO.getName(), objDTO.getEmail());
        Mono<UserDTO> result = userRepository.save(user).map(UserReturn -> new UserDTO(UserReturn));
        return result;
    }

    @Transactional
    public Mono<Void> delete(String id) throws InterruptedException, ExecutionException {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("Nenhum usuário encontrado!")))
                .flatMap(existingUser -> userRepository.delete(existingUser));
    }

    /*
        flatMap -> permitir que possa transformar uma ou mais streams em uma nova stream
     */
    @Transactional
    public Mono<UserDTO> update(String id, UserDTO obj) throws InterruptedException, ExecutionException {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    updateData(existingUser, obj);
                    return userRepository.save(existingUser);
                })
                .map(user -> new UserDTO(user))
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("Nenhum usuário encontrado!")));
    }


    //Método para atualizar usuário

    public void updateData(User entity, UserDTO obj) {
        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());
    }


}
