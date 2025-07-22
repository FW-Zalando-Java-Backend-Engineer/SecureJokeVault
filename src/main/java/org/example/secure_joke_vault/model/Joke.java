package org.example.secure_joke_vault.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a Joke fetched from the external API and optionally saved to MongoDB.
 */
@Document(collection = "jokes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Joke {

    /** MongoDB document ID */
    @Id
    private String id;

    /** The type of joke, e.g. "general", "programming", etc. */
    private String type;

    /** The setup line of the joke */
    private String setup;

    /** The punchline or answer */
    private String punchline;

    /** Username of the user who fetched or saved this joke (optional, useful for user-specific queries) */
    private String createdBy;

}
