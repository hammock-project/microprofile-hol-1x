package bg.jug.microprofile.hol.authors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Dmitry Alexandrov on 27.02.18.
 */
@RequestScoped
@Path("/")
public class AuthorsResource {

    @Inject
    private UserClient userClient;

    @Inject
    private AuthorsRepository authorsRepository;

    @GET
    @Path("/all")
    public Response getAuthors() {
        return Response.ok(buildAuthorJsonArray(authorsRepository.getAuthors()).build()).build();
    }

    @GET
    @Path("/findByEmail/{email}")
    public Response findAuthorById(@PathParam("email") String email) {
        return (authorsRepository.findAuthorByEmail(email)).map(author -> Response.ok(author.toJson()).build()).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }


    @POST
    public void addAuthor(String authorString) {
        Author author = Author.fromJson(authorString);

        JsonObject requestBody = Json.createObjectBuilder()
                .add("email", author.getEmail())
                .add("role", "author")
                .build();
        Response addResponse = userClient.createUser(requestBody);

        if (addResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            //FIXME: is this ok?
            authorsRepository.addAuthor(author);
        }
        addResponse.close();
    }


    private JsonArrayBuilder buildAuthorJsonArray(List<Author> authors){
        JsonArrayBuilder result = Json.createArrayBuilder();
        authors.forEach(e->{
            result.add(e.toJson());
        });
        return result;
    }

}
