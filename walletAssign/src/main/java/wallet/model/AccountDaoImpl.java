package wallet.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;



public class AccountDaoImpl implements AccountDao{

	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
		
	}

	public int saveAccount(Account account) {
		//insert into transaction values (?,?,?,?)",accountId,amount,transtype,sqlTimeStamp
		String sql="INSERT into account1 (name,balance) VALUES('"+account.getAccountName()+"',"+account.getAccountBalance()+")";
		//String sql="INSERT into account1 values(?,?)",account.get
		return jdbcTemplate.update(sql);
	}

	public int withdraw(int accountId, int amount) {
		
	/*int accountBalance=0;
		try {
			
			accountBalance=getAccountById(accountId).getAccountBalance()-amount;
			if(accountBalance<0) {
				System.out.println("Insufficient amount");
			}
			
			java.sql.Connection connection=Connection.getConnection();
			String sql="UPDATE account1 SET balance=? WHERE id=?";
			java.sql.PreparedStatement statement=connection.prepareStatement(sql);
			statement.setInt(1, accountBalance);
			statement.setInt(2, accountId);
			statement.executeUpdate();
			connection.close();
		}catch(ClassNotFoundException cnfe) {
				System.err.println(cnfe.getMessage());
			} catch (java.sql.SQLException e) {
				System.err.println(e.getMessage());
			}
			String transtype="withdraw";
		java.sql.Timestamp sqlTimeStamp=new java.sql.Timestamp(new java.util.Date().getTime());
		jdbcTemplate.update("insert into transaction values (?,?,?,?)",accountId,amount,transtype,sqlTimeStamp);*/
		String sql="update account1 set balance=? where id=?";
		int accountBalance=(int)(getAccountById(accountId).getAccountBalance()-amount);
		jdbcTemplate.update(sql,accountBalance,accountId);
		String transtype="withdraw";
		java.sql.Timestamp sqlTimeStamp=new java.sql.Timestamp(new java.util.Date().getTime());
		jdbcTemplate.update("insert into transaction values (?,?,?,?)",accountId,amount,transtype,sqlTimeStamp);
		return accountBalance;
	}

	public Account getAccountById(int accountId) {
		Account account=null;
		try {
			java.sql.Connection connection=Connection.getConnection();
			
			String sql="SELECT * FROM account1 WHERE id='"+accountId+"'";
			java.sql.Statement statement = connection.createStatement();
			java.sql.ResultSet result=statement.executeQuery(sql);
			if(result.next()) {
				String name=result.getString(2);
				int balance=result.getInt(3);
				account=new Account(accountId,name,balance);
			}else {
				System.out.println("Account with id:"+accountId+"not Found!");
			}
			connection.close();
			}catch(ClassNotFoundException cnfe) {
			System.err.println(cnfe.getMessage());
		}catch(java.sql.SQLException sqle) {
			sqle.printStackTrace();
		}
		
	return account;
	}

	public int deposit(int accountId, int amount) {
	/*	int accountBalance=0;
		try {
			AccountDao accountdao=new AccountDaoImpl();
			Account account=accountdao.getAccountById(accountId);
			
			java.sql.Connection connection=Connection.getConnection();
			
			String sql="UPDATE account1 SET balance=? WHERE id=?";
			
			 accountBalance=account.getAccountBalance()+amount;
			java.sql.PreparedStatement statement=connection.prepareStatement(sql);
			statement.setInt(1,accountBalance);
			statement.setInt(2, accountId);
			statement.executeUpdate();
			connection.close();
		}catch(ClassNotFoundException cnfe) {
			System.err.println(cnfe.getMessage());
		}catch(java.sql.SQLException sqle) {
			System.err.println(sqle.getMessage());
		} */
		String sql="update account1 set balance=? where id=?";
		int accountBalance=(int) (getAccountById(accountId).getAccountBalance()+amount);
		jdbcTemplate.update(sql,accountBalance,accountId);
		String transtype="deposit";
		
		java.sql.Timestamp sqlTimeStamp=new java.sql.Timestamp(new java.util.Date().getTime());
		jdbcTemplate.update("insert into transaction values (?,?,?,?)",accountId,amount,transtype,sqlTimeStamp);
		return accountBalance;
	}

	public int fundTransfer(int accountId1, int accountId2, int amount) {
		Account account=new Account();
		if(account.getAccountBalance()<1000) {
			System.out.println("You dont have sufficient amount");
		}
		withdraw( accountId1,  amount);
		return deposit(accountId2,amount);
	}

	public List<Transaction> transaction(int userId) {
		String sql="Select * from transaction where id='"+userId+"'";
		List<Transaction> transactions=jdbcTemplate.query(sql,new RowMapper<Transaction>(){

			public Transaction mapRow(ResultSet rs, int rn) throws SQLException {
				Transaction trans=new Transaction();
				trans.setId(rs.getInt(1));
				trans.setAmount(rs.getInt(2));
				trans.setTransactionType(rs.getString(3));
				trans.setTimeStamp(rs.getTimestamp(4));
				return trans;
			}
			
		});
		return transactions;
	}
}
