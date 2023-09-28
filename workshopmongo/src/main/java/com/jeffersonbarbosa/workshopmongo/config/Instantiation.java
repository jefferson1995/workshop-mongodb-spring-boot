package com.jeffersonbarbosa.workshopmongo.config;

import com.jeffersonbarbosa.workshopmongo.dto.AuthorDTO;
import com.jeffersonbarbosa.workshopmongo.dto.CommentDTO;
import com.jeffersonbarbosa.workshopmongo.entities.Post;
import com.jeffersonbarbosa.workshopmongo.entities.User;
import com.jeffersonbarbosa.workshopmongo.respositories.PostRepository;
import com.jeffersonbarbosa.workshopmongo.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        //Para limpar o banco
        Mono<Void> deleteUsers = userRepository.deleteAll();
        deleteUsers.subscribe();
        Mono<Void> deletePosts = postRepository.deleteAll();
        deletePosts.subscribe();



        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");

        Flux<User> insertUsers = userRepository.saveAll(Arrays.asList(maria, alex, bob));
        insertUsers.subscribe();



        //POST

        //Recupera os usuários do banco de dados
        maria = userRepository.searchEmail(maria.getEmail()).toFuture().get();
        alex =  userRepository.searchEmail(maria.getEmail()).toFuture().get();
        bob = userRepository.searchEmail(maria.getEmail()).toFuture().get();

        Post post1 = new Post(null, sdf.parse("21/03/2023"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria));
        Post post2 = new Post(null, sdf.parse("23/03/2023"), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));

        CommentDTO c1 = new CommentDTO("Boa viagem!", sdf.parse("21/03/2023"), new AuthorDTO(alex));
        CommentDTO c2 = new CommentDTO("Aproveite!", sdf.parse("22/03/2023"), new AuthorDTO(bob));
        CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", sdf.parse("23/03/2023"), new AuthorDTO(alex));

        post1.getComments().addAll(Arrays.asList(c1, c2));
        post2.getComments().add(c3);



        //Seta os usuários de referência nos posts
        post1.setUser(userRepository.searchEmail(maria.getEmail()).block());
        post2.setUser(userRepository.searchEmail(maria.getEmail()).block());

        Flux<Post> insertPosts = postRepository.saveAll(Arrays.asList(post1, post2));
        insertPosts.subscribe();


        Mono<User> insertUser = userRepository.save(maria);
        insertUser.subscribe();











    }
}
