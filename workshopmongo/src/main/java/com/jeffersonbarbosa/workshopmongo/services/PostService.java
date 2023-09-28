package com.jeffersonbarbosa.workshopmongo.services;

import com.jeffersonbarbosa.workshopmongo.dto.PostDTO;


import com.jeffersonbarbosa.workshopmongo.respositories.PostRepository;
import com.jeffersonbarbosa.workshopmongo.services.exceptions.ObjectNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Date;

import java.util.concurrent.ExecutionException;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;


    public Mono<PostDTO> findById(String id)  {
        return postRepository.findById(id)
                .map(returnPost -> new PostDTO(returnPost)).switchIfEmpty(Mono.error(new ObjectNotFoundException("Recurso não encontrado")));
    }

    public Flux<PostDTO> findByTitle(String text) {
        return postRepository.findByTitle(text).map(returnPost -> new PostDTO(returnPost));
    }

    public Flux<PostDTO> fullSearch(String text, Date minDate, Date maxDate) {
        maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000); //adiciona mais 24 horas - considera até o final desse dia
        return postRepository.fullSearch(text, minDate, maxDate).map(returnPost -> new PostDTO(returnPost));
    }

    public Flux<PostDTO> findByUser(String id) {
        return postRepository.findByUser(new ObjectId(id)).map(returnPost -> new PostDTO(returnPost));
    }
}
