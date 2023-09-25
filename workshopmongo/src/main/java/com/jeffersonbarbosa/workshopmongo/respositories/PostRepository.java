package com.jeffersonbarbosa.workshopmongo.respositories;

import com.jeffersonbarbosa.workshopmongo.entities.Post;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends ReactiveMongoRepository<Post, String> {

    //Querymethods
    List<Post> findByTitleContainingIgnoreCase(String text);

    //Query simples
    @Query(value = "{ 'title': { $regex: ?0, $options: 'i' } }")
    List<Post> findByTitle(String text);

    /*
        gte -> maior o igual
        lte -> menor ou igual
     */
    //https://www.mongodb.com/docs/manual/reference/operator/query/and/
    @Query(value = "{ $and: [ { date: { $gte: ?1  } }, { date: { $lte: ?2 }  } ," +
            " { $or: [ { 'title': { $regex: ?0, $options: 'i' }  }, { 'body': { $regex: ?0, $options: 'i' }  }, {  'comments.text': { $regex: ?0, $options: 'i' } }  ] } ] }")
    List<Post> fullSearch(String text, Date minDate, Date maxDate);



}
