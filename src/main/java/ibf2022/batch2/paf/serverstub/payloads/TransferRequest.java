package ibf2022.batch2.paf.serverstub.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
    private String destAcct;
    private String srcAcct;
    private Float amount;

    

    

}
