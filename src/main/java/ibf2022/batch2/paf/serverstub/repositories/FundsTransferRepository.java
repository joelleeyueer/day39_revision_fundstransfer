package ibf2022.batch2.paf.serverstub.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.paf.serverstub.models.BankAccount;

@Repository
public class FundsTransferRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private final String SELECT_ALL_SQL = "SELECT * FROM accounts";
    private final String CHECK_BALANCE_BY_NAME_SQL = "select balance from accounts where name = ?";
    private final String GET_ACCOUNT_SQL = "select * from accounts where name = ?";
    private final String WITHDRAW_SQL = "update accounts set balance = balance - ? where name = ?";
    private final String DEPOSIT_SQL = "update accounts set balance = balance + ? where name = ?";


    public List<BankAccount> getAllAccounts(){
        List<BankAccount> result = new ArrayList<>();
        
            result = jdbcTemplate.query(SELECT_ALL_SQL, BeanPropertyRowMapper.newInstance(BankAccount.class));
            return result;
    }

    public Boolean checkBalanceByName(String name, Float withdrawnAmount){
        Boolean isWithdrawnBalanceSufficient = false;

        try {
            Float returnedBalance = jdbcTemplate.queryForObject(CHECK_BALANCE_BY_NAME_SQL, Float.class, name);
            System.out.println(name+ " has a balance of " + returnedBalance);
            if (withdrawnAmount <= returnedBalance){
                isWithdrawnBalanceSufficient = true;
            }
        } catch (DataAccessException e) {
            System.err.println("Withdrawal Failed, withdraw account does not exist");
        }

        return isWithdrawnBalanceSufficient;
    }

    public void checkBalanceOnly(String name){
        try {
            Float returnedBalance = jdbcTemplate.queryForObject(CHECK_BALANCE_BY_NAME_SQL, Float.class, name);
            System.out.println(name+ " has a balance of " + returnedBalance);
        } catch (Exception e){
            System.err.println(name + " account does not exist");

        }
    }
    
    public BankAccount getAccount(String name){

        BankAccount bankAccount = jdbcTemplate.queryForObject
                                    (GET_ACCOUNT_SQL, BeanPropertyRowMapper.newInstance(BankAccount.class)
                                    , name);

        return bankAccount;
    }

    public Boolean withdrawAmount(String name, Float withdrawAmount) throws DataAccessException{
        Boolean isWithdrawn = false;
        try{
            int updated = jdbcTemplate.update(WITHDRAW_SQL, withdrawAmount, name); //
            System.out.println("Withdrawing from " + name);

            if (updated>0){ //returns 0 or 1 or error if row not inserted
                isWithdrawn = true;
            }
        } catch (DataAccessException e){
            System.err.println("Withdrawal Failed, withdraw account does not exist");
        }

        return isWithdrawn;
        
    }

    public Boolean depositAmount(String name, Float depositAmount) throws DataAccessException{
        Boolean isDeposited = false;

        try {
            int updated = jdbcTemplate.update(DEPOSIT_SQL, depositAmount, name);
            System.out.println("Depositing to " + name);

            if (updated>0){ //returns 0 or 1 or error if row not inserted
                isDeposited = true;
            }

        } catch (DataAccessException e) {
            System.err.println("Deposit Failed, deposit account does not exist");
        }

        return isDeposited;
 
    }

}
