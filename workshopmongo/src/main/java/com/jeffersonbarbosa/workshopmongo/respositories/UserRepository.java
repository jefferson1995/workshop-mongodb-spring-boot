package com.jeffersonbarbosa.workshopmongo.respositories;

import com.jeffersonbarbosa.workshopmongo.entities.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
