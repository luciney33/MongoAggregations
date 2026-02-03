package exercise2;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.gte;

public class main11 {
    /***
     * Get products with rating >= 4 and their warranty information
     */

    public static void main() {
        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323/");
        MongoDatabase db = mongo.getDatabase("exercise2_lucia");
        MongoCollection<Document> col = db.getCollection("Products");
        col.aggregate(Arrays.asList(
                match(gte("rating", 4)),
                project(new Document("name", 1)
                        .append("rating", 1)
                        .append("price", 1)
                        .append("warranty_years", 1)
                        .append("quality_score", new Document("$multiply", Arrays.asList("$rating", "$warranty_years")))),
                sort(new Document("quality_score", -1))
        )).into(new ArrayList<>()).forEach(doc -> System.out.println(doc.toJson()));
    }
}

