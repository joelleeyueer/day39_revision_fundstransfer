package ibf2022.batch2.paf.serverstub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2022.batch2.paf.serverstub.payloads.TransferRequest;
import ibf2022.batch2.paf.serverstub.services.FundsTransferServices;
import ibf2022.batch2.paf.serverstub.services.LoggingService;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;



@RestController
@RequestMapping
public class FundsTransferController {

	@Autowired
	private FundsTransferServices fundsSvc;

	@Autowired
	private LoggingService logSvc;
	
	@PostMapping("/api/transfer")
	public ResponseEntity<String> postTransfer(@RequestBody TransferRequest transferRequest) {

		try {
			fundsSvc.transferMoney(transferRequest.getSrcAcct(), transferRequest.getDestAcct(), transferRequest.getAmount());
			String transactionId = fundsSvc.generateTransactionId();
			// // Create a JSON object with the transaction ID
			JsonObjectBuilder builder = Json.createObjectBuilder();
			builder.add("transactionId", transactionId);
			JsonObject tid = builder.build();

			logSvc.insertTransaction(transactionId, transferRequest);
			
			return new ResponseEntity<String>(tid.toString(), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String exceptionAsString = e.toString();
			// Create a JSON object with the transaction ID
			JsonObjectBuilder builder = Json.createObjectBuilder();
			builder.add("message", exceptionAsString);
			JsonObject errorMessage = builder.build();
            return new ResponseEntity<String>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
		}

		// Transfer successful return the following JSON object
		// { "transactionId", "aTransactionId" }
		//
		// Transfer failed return the following JSON object
		// { "message", "Error message" }

	}
}
