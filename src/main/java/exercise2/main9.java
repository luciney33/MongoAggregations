package exercise2;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Arrays;
import static com.mongodb.client.model.Accumulators.max;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Sorts.descending;

public class main9 {
    /***
     * The priciest Brands Phones
     */

    static void main() {
        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323/");
        MongoDatabase db = mongo.getDatabase("exercise2_lucia");
        MongoCollection<Document> col = db.getCollection("Products");

        col.aggregate(Arrays.asList(
                group("$brand", max("maxPrice", "$price")),
                sort(descending("maxPrice")),
                limit(1)
        )).into(new ArrayList<>()).forEach(System.out::println);


    }
}
