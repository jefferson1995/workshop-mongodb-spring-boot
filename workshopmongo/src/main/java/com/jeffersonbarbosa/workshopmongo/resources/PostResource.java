package com.jeffersonbarbosa.workshopmongo.resources;

import com.jeffersonbarbosa.workshopmongo.dto.PostDTO;
import com.jeffersonbarbosa.workshopmongo.entities.Post;
import com.jeffersonbarbosa.workshopmongo.entities.User;
import com.jeffersonbarbosa.workshopmongo.resources.util.URL;
import com.jeffersonbarbosa.workshopmongo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

    @Autowired
    PostService postService;

    @GetMapping(value = "/{id}")
    public Mono<ResponseEntity<PostDTO>> findById(@PathVariable String id) throws ExecutionException, InterruptedException {
        return postService.findById(id).map(postDTO -> ResponseEntity.ok(postDTO));
    }

    @GetMapping(value = "/titlesearch")
    public Flux<PostDTO> findByTitle(@RequestParam(value = "text", defaultValue = "") String text) {
        text = URL.decodeParam(text);
        return postService.findByTitle(text);
    }

    @GetMapping(value = "/fullsearch")
    public Flux<PostDTO> fullSearch(@RequestParam(value = "text", defaultValue = "") String text,
                                    @RequestParam(value = "minDate", defaultValue = "") String minDate,
                                    @RequestParam(value = "maxDate", defaultValue = "") String maxDate) {
        text = URL.decodeParam(text);
        Date min = URL.convertDate(minDate, new Date(0L)); //primeira data 1970
        Date max = URL.convertDate(maxDate, new Date());

        return postService.fullSearch(text, min, max);

    }

    @GetMapping(value = "/{id}/users")
    public Flux<PostDTO> findByUser(@PathVariable String id) {
        return postService.findByUser(id);
    }


}
