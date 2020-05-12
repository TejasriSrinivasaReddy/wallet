package com.ibm.wallet.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ibm.wallet.beans.Transactions;
import com.ibm.wallet.beans.WalletAccount;
@Component("walletDao")
public class WalletDaoImpl implements WalletDao{
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	//public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		//this.jdbcTemplate = jdbcTemplate;
	//}

	
	
	public String createAccount(String userName, double balance) {
		
		String sql1="select max(userId) from accounts";
		List<String> list;
		list=jdbcTemplate.query(sql1,new ResultSetExtractor<List<String>>(){  
		    public List<String> extractData(ResultSet rs) throws SQLException,  
		            DataAccessException {    
		    		List<String> list=new ArrayList<String>();  
		    		while(rs.next()){  
		    			String maxAccountId=rs.getString(1);
		    			maxAccountId=(maxAccountId != null)? maxAccountId.substring(2):"0";
		    			int accountId1=1+Integer.parseInt(maxAccountId);
		    			String userId="WW"+String.format("%09d",accountId1 );
		    			list.add(userId);  
		        }  
		        return list;  
		        }  
		    });  
		String userId=list.get(0);
		String sql="insert into accounts values(?,?,?)";
		jdbcTemplate.update(sql,userName,userId,balance);
		return userId;
	}


	

	public double withdraw(double amount,String userId) throws AccountNotFoundException {
		double current=0;
		String sql="select	* from accounts where userId=?";
		WalletAccount res=jdbcTemplate.queryForObject(sql,new Object[] {userId},new BeanPropertyRowMapper(WalletAccount.class));
		
		if(res.getBalance()>amount)
		{
		 current=res.getBalance()-amount;
		}
		String sql1="update accounts set balance= ? where userId= ?";
		jdbcTemplate.update(sql1,current,userId);
		String transtype="wd";
		java.sql.Timestamp sqlTimeStamp=new java.sql.Timestamp(new java.util.Date().getTime());
		jdbcTemplate.update("insert into transaction values (?,?,?,?)",userId,amount,transtype,sqlTimeStamp);
		return current;
		
	}

	
	public double deposit(double amount,String userId) {
		double current=0;
		String sql="select	* from accounts where userId=?";
		WalletAccount res=jdbcTemplate.queryForObject(sql,new Object[] {userId},new BeanPropertyRowMapper(WalletAccount.class));
		 current=res.getBalance()+amount;
		String sql1="update accounts set balance= ? where userId= ?";
		jdbcTemplate.update(sql1,current,userId);
		String transtype="dp";
		
		java.sql.Timestamp sqlTimeStamp=new java.sql.Timestamp(new java.util.Date().getTime());
		jdbcTemplate.update("insert into transaction values (?,?,?,?)",userId,amount,transtype,sqlTimeStamp);
		return current;
	}



	public List<Transactions> trasaction(String userId) {
		String sql="Select * from transaction where userId='"+userId+"'";
		List<Transactions> transactions=jdbcTemplate.query(sql,new RowMapper<Transactions>(){

			public Transactions mapRow(ResultSet rs, int rn) throws SQLException {
				Transactions trans=new Transactions();
				trans.setUserId(rs.getString(1));
				trans.setAmount(rs.getDouble(2));
				trans.setTransType(rs.getString(3));
				trans.setTimeStamp(rs.getTimestamp(4));
				return trans;
			}
			
		});
		return transactions;
	}
		
}
