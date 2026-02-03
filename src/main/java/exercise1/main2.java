package exercise1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.ascending;

public class main2 {
    static void main() {
        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323/");
        MongoDatabase db = mongo.getDatabase("lucia_peñafiel_MongoDB");
        MongoCollection<Document> col = db.getCollection("Readers");
        col.aggregate(Arrays.asList(
                        unwind("$subscriptions"),
                        lookup("Newspapers",
                                "subscriptions._id",
                                "_id",
                                "newspaper"),
                        unwind("$newspaper"),
                        match(eq("newspaper.name", "Tempo")),
                        sort(ascending("dob")),
                        limit(100),
                        project(fields(
                                excludeId(),
                                include("name")
                        ))
                )).into(new ArrayList<>())
                .forEach(doc -> System.out.println(doc.toJson()));

    }
}
