package exercise2;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Aggregates.*;

public class main13 {
    /***
     * Get products grouped by price ranges
     * */

    static void main() {
        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323/");
        MongoDatabase db = mongo.getDatabase("exercise2_lucia");
        MongoCollection<Document> col = db.getCollection("Products");

        col.aggregate(Arrays.asList(
                project(new Document("name", 1)
                        .append("price", 1)
                        .append("rating", 1)
                        .append("priceRange", new Document("$switch", new Document()
                                .append("branches", Arrays.asList(
                                        new Document("case", new Document("$lt", Arrays.asList("$price", 20)))
                                                .append("then", "Budget (< $20)"),
                                        new Document("case", new Document("$lt", Arrays.asList("$price", 50)))
                                                .append("then", "Mid-Range ($20-$50)"),
                                        new Document("case", new Document("$lt", Arrays.asList("$price", 100)))
                                                .append("then", "Premium ($50-$100)")
                                ))
                                .append("default", "Luxury ($100+)")
                        ))
                ),
                sort(new Document("price", 1))
        )).into(new ArrayList<>()).forEach(System.out::println);
    }
}
