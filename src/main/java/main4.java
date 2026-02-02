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
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Projections.*;

public class main4 {
    static void main() {
        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323/");
        MongoDatabase db = mongo.getDatabase("lucia_peñafiel_MongoDB");
        MongoCollection<Document> col = db.getCollection("Newspapers");
        col.aggregate(Arrays.asList(
                        match(eq("name", "Daily News")),
                        unwind("$articles"),
                        unwind("$articles.readarticle"),
                        match(lt("articles.readarticle.rating", 5)),
                        group("$articles.readarticle._idReader",
                                sum("lowRatingsCount", 1)
                        ),
                        project(fields(
                                excludeId(),
                                computed("readerId", "$_id"),
                                include("lowRatingsCount"),
                                computed("ratedMoreThan4Low",
                                        new Document("$gt", Arrays.asList("$lowRatingsCount", 4)))
                        ))
                )).into(new ArrayList<>())
                .forEach(doc -> System.out.println(doc.toJson()));
    }
}
