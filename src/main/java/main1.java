import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.unwind;
import static com.mongodb.client.model.Projections.*;

public class main1 {
    static void main() {
        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323/");
        MongoDatabase db = mongo.getDatabase("lucia_peñafiel_MongoDB");
        MongoCollection<Document> col = db.getCollection("Newspapers");
        col.aggregate(Arrays.asList(
                unwind("$articles"),
                project(fields(
                        excludeId(),
                        computed("description", "$articles.description"),
                        computed("numReaders",
                                new Document("$size", "$articles.readarticle"))
                ))
        )).into(new ArrayList<>()).forEach(System.out::println);

    }
}
