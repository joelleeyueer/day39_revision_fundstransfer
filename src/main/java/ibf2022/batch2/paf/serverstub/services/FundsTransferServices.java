package ibf2022.batch2.paf.serverstub.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import ibf2022.batch2.paf.serverstub.models.BankAccount;
import ibf2022.batch2.paf.serverstub.repositories.FundsTransferRepository;

@Service
public class FundsTransferServices {

    @Autowired
    private FundsTransferRepository fundsTransferRepo;

    public List<BankAccount> getAllAccounts(){
        return fundsTransferRepo.getAllAccounts();
    }

    public String generateTransactionId(){
        return UUID.randomUUID().toString().substring(0, 8);
        
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Boolean transferMoney(String accountFrom, String accountTo, Float amount){
        Boolean isAmountValid = false;
        Boolean isWithdrawnBalanceSufficient = false;
        Boolean isAccountFromAndToSame = false;
        Boolean isWithdrawSuccessful = false;
        Boolean isDepositSuccessful = false;

        

        //check if accountFrom has sufficient balance
        isWithdrawnBalanceSufficient = fundsTransferRepo.checkBalanceByName(accountFrom, amount);
        if (!isWithdrawnBalanceSufficient){
            throw new IllegalArgumentException("Insufficient Balance from Withdrawal Account or Invalid Account");
        }
        fundsTransferRepo.checkBalanceOnly(accountTo);


        //check if amount is not less or equal to zero
        if (amount > 0){
            isAmountValid = true;
        }
        System.out.println("Amount to withdraw: " + amount);

        //check if accountFrom and accountTo are not the same
        if (accountFrom == accountTo){
            isAccountFromAndToSame = true;
            throw new IllegalArgumentException("Withdrawal Account and Deposit Account cannot be the same");
        }


        //perform withdraw operation
        isWithdrawSuccessful = fundsTransferRepo.withdrawAmount(accountFrom, amount);
        if (!isWithdrawSuccessful){
            throw new IllegalArgumentException("Withdrawal Failed");
        }


        //perform deposit operation
        isDepositSuccessful = fundsTransferRepo.depositAmount(accountTo, amount);
        if (!isDepositSuccessful){
            throw new IllegalArgumentException("Deposit Failed");
        }

        //print result if all successful
        if (isAmountValid && isWithdrawSuccessful && isDepositSuccessful && !isAccountFromAndToSame){
            System.out.println(amount + " successfully transferred from " + accountFrom + " to " + accountTo);
            fundsTransferRepo.checkBalanceOnly(accountFrom);
            fundsTransferRepo.checkBalanceOnly(accountTo);
            return true;
        } else {
            System.out.println("Transfer Failed");
            return false;
    }
}
    
}
