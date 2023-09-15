package com.jeffersonbarbosa.workshopmongo.config;

import com.jeffersonbarbosa.workshopmongo.dto.AuthorDTO;
import com.jeffersonbarbosa.workshopmongo.entities.Post;
import com.jeffersonbarbosa.workshopmongo.entities.User;
import com.jeffersonbarbosa.workshopmongo.respositories.PostRepository;
import com.jeffersonbarbosa.workshopmongo.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.Arrays;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        //Para limpar o banco
        userRepository.deleteAll();
        postRepository.deleteAll();

        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");

        userRepository.saveAll(Arrays.asList(maria, alex, bob));



        //POST

        Post post1 = new Post(null, Instant.now(), "Partiu viagem!", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria));
        Post post2 = new Post(null, Instant.now(), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));


        postRepository.saveAll(Arrays.asList(post1, post2));

        maria.getPosts().addAll(Arrays.asList(post1, post2));

        userRepository.save(maria);











    }
}
