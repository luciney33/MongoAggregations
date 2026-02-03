import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Projections.*;

public class main8 {
    static void main() {
        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323/");
        MongoDatabase db = mongo.getDatabase("lucia_peñafiel_MongoDB");
        MongoCollection<Document> col = db.getCollection("Readers");
        col.aggregate(Arrays.asList(
                project(fields(
                        computed("numSubscriptions", new Document("$size", "$subscriptions"))
                )),
                group(null, avg("averageSubscriptions", "$numSubscriptions"))
        )).into(new ArrayList<>()).forEach(System.out::println);

    }
}
