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
     * Counts products by availability status and calculates price stats
     */

    public static void main() {
        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323/");
        MongoDatabase db = mongo.getDatabase("exercise2_lucia");
        MongoCollection<Document> col = db.getCollection("Products");

        System.out.println("Product availability statistics:");
        col.aggregate(Arrays.asList(
                group("$available",
                    sum("count", 1),
                    avg("avgPrice", "$price"),
                    min("minPrice", "$price"),
                    max("maxPrice", "$price")
                ),
                sort(new Document("_id", -1))
        )).into(new ArrayList<>()).forEach(doc -> {
            System.out.println("\n" + (doc.get("_id") != null ? "Available: " + doc.get("_id") : "Availability not specified"));
            System.out.println("  Count: " + doc.get("count"));
            System.out.println("  Avg Price: $" + doc.get("avgPrice"));
            System.out.println("  Min Price: $" + doc.get("minPrice"));
            System.out.println("  Max Price: $" + doc.get("maxPrice"));
        });
    }
}

