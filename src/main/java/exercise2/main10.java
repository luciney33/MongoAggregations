package exercise2;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Aggregates.*;

public class main10 {
    /***
     * Get the average price of products by type
     */

    public static void main() {
        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323/");
        MongoDatabase db = mongo.getDatabase("exercise2_lucia");
        MongoCollection<Document> col = db.getCollection("Products");
        col.aggregate(Arrays.asList(
                unwind("$type"),
                group("$type", avg("avgPrice", "$price")),
                sort(new Document("avgPrice", -1))
        )).into(new ArrayList<>()).forEach(doc ->
                System.out.println("Type: " + doc.get("_id") + " - Avg Price: $" + doc.get("avgPrice"))
        );
    }
}
