package exercise2;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;

public class main12 {
    /***
     * Get statistics about available vs unavailable products
     */

    static void main() {
        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323/");
        MongoDatabase db = mongo.getDatabase("exercise2_lucia");
        MongoCollection<Document> col = db.getCollection("Products");
        col.aggregate(Arrays.asList(
                group("$available",
                    sum("count", 1),
                    avg("avgPrice", "$price"),
                    min("minPrice", "$price"),
                    max("maxPrice", "$price")
                ),
                sort(new Document("_id", -1))
        )).into(new ArrayList<>()).forEach(System.out::println);
    }
}

