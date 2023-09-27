package com.jeffersonbarbosa.workshopmongo.respositories;

import com.jeffersonbarbosa.workshopmongo.entities.Post;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import java.util.Date;


@Repository
public interface PostRepository extends ReactiveMongoRepository<Post, String> {

    //Querymethods
    Flux<Post> findByTitleContainingIgnoreCase(String text);

    //Query simples
    @Query(value = "{ 'title': { $regex: ?0, $options: 'i' } }")
    Flux<Post> findByTitle(String text);

    /*
        gte -> maior o igual
        lte -> menor ou igual
     */
    //https://www.mongodb.com/docs/manual/reference/operator/query/and/
    @Query(value = "{ $and: [ { date: { $gte: ?1  } }, { date: { $lte: ?2 }  } ," +
            " { $or: [ { 'title': { $regex: ?0, $options: 'i' }  }, { 'body': { $regex: ?0, $options: 'i' }  }, {  'comments.text': { $regex: ?0, $options: 'i' } }  ] } ] }")
    Flux<Post> fullSearch(String text, Date minDate, Date maxDate);


}
