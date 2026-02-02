import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

public class main5 {
    static void main() {
        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323/");
        MongoDatabase db = mongo.getDatabase("lucia_peñafiel_MongoDB");
        MongoCollection<Document> col = db.getCollection("Newspapers");
        col.aggregate(Arrays.asList(
                unwind("$articles"),
                match(eq("articles.type", "Sports")),
                group("$name",
                        sum("sportsArticlesCount", 1)
                ),
                project(fields(
                        excludeId(),
                        computed("newspaper", "$_id"),
                        computed("sportsArticlesCount", "$sportsArticlesCount")
                ))
        )).into(new ArrayList<>())
                .forEach(doc -> System.out.println(doc.toJson()));
    }
}
