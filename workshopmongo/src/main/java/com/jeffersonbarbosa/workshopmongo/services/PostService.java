package com.jeffersonbarbosa.workshopmongo.services;

import com.jeffersonbarbosa.workshopmongo.entities.Post;
import com.jeffersonbarbosa.workshopmongo.entities.User;
import com.jeffersonbarbosa.workshopmongo.respositories.PostRepository;
import com.jeffersonbarbosa.workshopmongo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;


    public Post findById(String id) {
        Optional<Post> obj = postRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Nenhum usuário encontrado."));
    }



}
