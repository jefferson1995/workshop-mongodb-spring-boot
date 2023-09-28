package com.jeffersonbarbosa.workshopmongo.resources;

import com.jeffersonbarbosa.workshopmongo.dto.UserDTO;
import com.jeffersonbarbosa.workshopmongo.entities.Post;
import com.jeffersonbarbosa.workshopmongo.entities.User;
import com.jeffersonbarbosa.workshopmongo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Flux<UserDTO>> findAll() {
        Flux<UserDTO> listUserDTO = userService.findAll();
        return ResponseEntity.ok().body(listUserDTO);
    }

    @GetMapping(value = "/{id}")
    public Mono<ResponseEntity<UserDTO>> findById(@PathVariable String id) throws InterruptedException, ExecutionException {
        return userService.findById(id).map(userDTO -> ResponseEntity.ok().body(userDTO));
    }

    @PostMapping
    public Mono<ResponseEntity<UserDTO>> insert(@RequestBody UserDTO objDTO, UriComponentsBuilder builder) {
        return userService.insert(objDTO)
                .map(newUserDTO -> ResponseEntity.created(builder.path("/users/{id}").buildAndExpand(newUserDTO.getId()).toUri())
                        .body(newUserDTO));
    }

    @DeleteMapping(value = "/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) throws InterruptedException, ExecutionException {
        return userService.delete(id).then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    @PutMapping(value = "/{id}")
    public Mono<ResponseEntity<UserDTO>> update(@PathVariable String id, @RequestBody UserDTO objDTO) throws InterruptedException, ExecutionException {
        return userService.update(id, objDTO)
                .map(userUpdateDTO -> ResponseEntity.ok().body(userUpdateDTO));
    }


}
