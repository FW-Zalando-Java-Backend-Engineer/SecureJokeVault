package org.example.secure_joke_vault.repository;


import org.example.secure_joke_vault.model.Joke;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repository for storing and retrieving jokes from MongoDB.
 */
public interface JokeRepository extends MongoRepository<Joke, String> {

    /**
     * @Query("{ 'createdBy': ?0 }")
     * Find jokes saved by a specific user.
     * @param createdBy the username of the user
     * @return list of jokes fetched or saved by that user
     */
    List<Joke> findByCreatedBy(String createdBy);
}
