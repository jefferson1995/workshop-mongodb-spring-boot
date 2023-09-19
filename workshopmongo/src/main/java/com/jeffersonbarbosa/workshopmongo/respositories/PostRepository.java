package com.jeffersonbarbosa.workshopmongo.respositories;

import com.jeffersonbarbosa.workshopmongo.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    //Querymethods
    List<Post> findByTitleContainingIgnoreCase(String text);

    //Query simples
    @Query(value = "{ 'title': { $regex: ?0, $options: 'i' } }")
    List<Post> findByTitle(String text);



}
