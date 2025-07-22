package org.example.secure_joke_vault.repository;

import org.example.secure_joke_vault.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

/**
 * MongoDB repository interface for managing user documents.
 */
public interface UserRepository extends MongoRepository<User, String> {

    /**
     *  @Query("{ 'username': ?0 }")
     * Finds a user by their unique username.
     * @param username the username to search for
     * @return Optional<User>
     */

    Optional<User> findByUsername(String username);

    /**
     * @Query(value = "{'username':?0}", exists = true)
     * Checks if a username already exists in the database.
     * @param username the username to check
     * @return true if exists, false otherwise
     */

    boolean existsByUsername(String username);
}

/**
 * Why are these custom methods auto-implemented?
 * Spring Data MongoDB provides a query derivation mechanism.
 * This means:
 *      - it looks at the method name.
 *      - Tries to understand what the query should do.
 *      - Then automatically generates the query behind the scenes.
 *
 * */
