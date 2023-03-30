package ibf2022.batch2.paf.serverstub.services;
import java.util.Date;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import ibf2022.batch2.paf.serverstub.payloads.TransferRequest;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;


@Service
public class LoggingService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void insertTransaction(String uuid, TransferRequest trfrequest){
        System.out.println("in insertTransaction "+ trfrequest.getDestAcct() + " " + trfrequest.getSrcAcct() + " " + trfrequest.getAmount());
        JsonObjectBuilder incomingTransactionBuilder = Json.createObjectBuilder();
        incomingTransactionBuilder.add("uuid", uuid)
                            .add("from", trfrequest.getSrcAcct())
                            .add("to", trfrequest.getDestAcct())
                            .add("amount", trfrequest.getAmount());
                            
        JsonObject incomingTransaction = incomingTransactionBuilder.build();

        Document toInsert = mongoTemplate.insert(Document.parse(incomingTransaction.toString()), "fundstransfer");



        //print the document to be inserted
        System.out.println(toInsert.toString());
    }

    // public Boolean insertMongo(Transaction tran, String id){ 
 
    //     JsonObjectBuilder job = Json.createObjectBuilder(); 
 
    //     job.add("transactionId",id).add("fromName", tran.getFromName()) 
    //     .add("toName", tran.getToName()).add("amount", tran.getAmount()); 
 
    //     JsonObject detail = job.build(); 
 
    //     // insert(object to save, string collection name) 
    //     Document inserted = mtemplate.insert(Document.parse(detail.toString()), "transaction"); 
 
    //     return (inserted != null); 
    // }
    
}
