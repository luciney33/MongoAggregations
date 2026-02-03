package exercise1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Projections.computed;
import static com.mongodb.client.model.Projections.fields;

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
