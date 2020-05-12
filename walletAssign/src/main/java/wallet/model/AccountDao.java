package wallet.model;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;


public interface AccountDao {

public void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	
	public int saveAccount(Account account);
	public int withdraw(int accountId,int amount);
	public Account getAccountById(int id);
	public int deposit(int accountId,int amount);
	public int fundTransfer(int accountId1,int accountId2,int amount);
	public List<Transaction>transaction(int userId);
}
