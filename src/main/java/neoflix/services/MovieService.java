package neoflix.services;

import neoflix.AppUtils;
import neoflix.NeoflixApp;
import neoflix.Params;
import org.neo4j.driver.Driver;
import org.neo4j.driver.TransactionContext;
import org.neo4j.driver.Values;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieService {
    private final Driver driver;
    private final List<Map<String,Object>> popular;
    private final List<Map<String, Object>> directedByCoppola;
    private final List<Map<String, Object>> actedInTomHanks;
    private final List<Map<String, Object>> comedyMovies;

    /**
     * The constructor expects an instance of the Neo4j Driver, which will be
     * used to interact with Neo4j.
     */
    public MovieService(Driver driver) {
        this.driver = driver;
        this.popular = AppUtils.loadFixtureList("popular");
        this.directedByCoppola = AppUtils.loadFixtureList("directed_by_coppola");
        this.actedInTomHanks = AppUtils.loadFixtureList("acted_in_tom_hanks");
        this.comedyMovies = AppUtils.loadFixtureList("comedy_movies");
    }

    /**
     * This method should return a paginated list of movies ordered by the `sort`
     * parameter and limited to the number passed as `limit`.  The `skip` variable should be
     * used to skip a certain number of rows.
     *
     * If a userId value is supplied, a `favorite` boolean property should be returned to
     * signify whether the user has aded the movie to their "My Favorites" list.
     *
     * @param params query params (query, sort, order, limit, skip)
     * @param userId
     * @returns {Promise<Record<string, any>[]>}
     */
    // tag::all[]
    public List<Map<String,Object>> all(Params params, String userId) {
        // TODO: Open an Session
        // TODO: Execute a query in a new Read Transaction
        // TODO: Get a list of Movies from the Result
        // TODO: Close the session

        return AppUtils.process(popular, params);
    }
    // end::all[]


    /**
     * @public
     * This method find a Movie node with the ID passed as the `id` parameter.
     * Along with the returned payload, a list of actors, directors, and genres should
     * be included.
     * The number of incoming RATED relationships should also be returned as `ratingCount`
     *
     * If a userId value is suppled, a `favorite` boolean property should be returned to
     * signify whether the user has aded the movie to their "My Favorites" list.
     *
     * @param {string} id
     * @returns {Promise<Record<string, any>>}
     */
    // tag::findById[]
    public Map<String,Object> findById(String id, String userId) {
        // TODO: Find a movie by its ID
        // MATCH (m:Movie {tmdbId: $id})

        return popular.stream().filter(m -> id.equals(m.get("tmdbId"))).findAny().get();
    }
    // end::findById[]

    /**
     * This method should return a paginated list of similar movies to the Movie with the
     * id supplied.  This similarity is calculated by finding movies that have many first
     * degree connections in common: Actors, Directors and Genres.
     *
     * Results should be ordered by the `sort` parameter, and in the direction specified
     * in the `order` parameter.
     * Results should be limited to the number passed as `limit`.
     * The `skip` variable should be used to skip a certain number of rows.
     *
     * If a userId value is suppled, a `favorite` boolean property should be returned to
     * signify whether the user has aded the movie to their "My Favorites" list.
     *
     * @param id
     * @param params Query parameters for pagination and ordering
     * @param userId
     * @returns List<Movie> similar movies
     */
    // tag::getSimilarMovies[]
    public List<Map<String,Object>> getSimilarMovies(String id, Params params, String userId) {
        // TODO: Get similar movies based on genres or ratings

        return AppUtils.process(popular, params).stream()
                .map(item -> {
                    Map<String,Object> copy = new HashMap<>(item);
                    copy.put("score", ((int)(Math.random() * 10000)) / 100.0);
                    return copy;
                }).toList();
    }
    // end::getSimilarMovies[]


    /**
     * This method should return a paginated list of movies that have a relationship to the
     * supplied Genre.
     *
     * Results should be ordered by the `sort` parameter, and in the direction specified
     * in the `order` parameter.
     * Results should be limited to the number passed as `limit`.
     * The `skip` variable should be used to skip a certain number of rows.
     *
     * If a userId value is supplied, a `favorite` boolean property should be returned to
     * signify whether the user has added the movie to their "My Favorites" list.
     *
     * @param name
     * @param params Query parameters for pagination and ordering
     * @param userId
     * @return List<Movie> movies for that genre
     */
    // tag::getByGenre[]
    public List<Map<String,Object>> byGenre(String name, Params params, String userId) {
        // TODO: Get Movies in a Genre
        // MATCH (m:Movie)-[:IN_GENRE]->(:Genre {name: $name})

        return AppUtils.process(comedyMovies, params);
    }
    // end::getByGenre[]

    /**
     * This method should return a paginated list of movies that have an ACTED_IN relationship
     * to a Person with the id supplied
     *
     * Results should be ordered by the `sort` parameter, and in the direction specified
     * in the `order` parameter.
     * Results should be limited to the number passed as `limit`.
     * The `skip` variable should be used to skip a certain number of rows.
     *
     * If a userId value is suppled, a `favorite` boolean property should be returned to
     * signify whether the user has aded the movie to their "My Favorites" list.
     *
     * @param actorId actor id
     * @param params query params with sorting and pagination
     * @param userId
     * @return List<Movie>
     */
    // tag::getForActor[]
    public List<Map<String,Object>> getForActor(String actorId, Params params,String userId) {
        // TODO: Get Movies acted in by a Person
        // MATCH (:Person {tmdbId: $id})-[:ACTED_IN]->(m:Movie)

        return AppUtils.process(actedInTomHanks, params);
    }
    // end::getForActor[]

    /**
     * This method should return a paginated list of movies that have an DIRECTED relationship
     * to a Person with the id supplied
     *
     * Results should be ordered by the `sort` parameter, and in the direction specified
     * in the `order` parameter.
     * Results should be limited to the number passed as `limit`.
     * The `skip` variable should be used to skip a certain number of rows.
     *
     * If a userId value is suppled, a `favorite` boolean property should be returned to
     * signify whether the user has aded the movie to their "My Favorites" list.
     *
     * @param directorId director id
     * @param params query params with sorting and pagination
     * @param userId
     * @return List<Movie>
     */
    // tag::getForDirector[]
    public List<Map<String,Object>> getForDirector(String directorId, Params params,String userId) {
        // TODO: Get Movies directed by a Person
        // MATCH (:Person {tmdbId: $id})-[:DIRECTED]->(m:Movie)

        return AppUtils.process(directedByCoppola, params);
    }
    // end::getForDirector[]


    /**
     * This function should return a list of tmdbId properties for the movies that
     * the user has added to their 'My Favorites' list.
     *
     * @param tx The open transaction
     * @param userId The ID of the current user
     * @return List<String> movieIds of favorite movies
     */
    // tag::getUserFavorites[]
    private List<String> getUserFavorites(TransactionContext tx, String userId) {
        return List.of();
    }
    // end::getUserFavorites[]

    record Movie() {} // todo
}
