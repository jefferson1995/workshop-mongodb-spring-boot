package com.jeffersonbarbosa.workshopmongo.services;

import com.jeffersonbarbosa.workshopmongo.entities.Post;

import com.jeffersonbarbosa.workshopmongo.respositories.PostRepository;
import com.jeffersonbarbosa.workshopmongo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;


    public Mono<Post> findById(String id) {
        return postRepository.findById(id).switchIfEmpty(Mono.error(new ObjectNotFoundException("Recurso não encontrado")));
    }

    public List<Post> findByTitle(String text){
        return postRepository.findByTitle(text);
    }

    public List<Post> fullSearch(String text, Date minDate, Date maxDate){
        maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000); //adiciona mais 24 horas - considera até o final desse dia
        return postRepository.fullSearch(text, minDate, maxDate);
    }

}
