package com.multilix.hadoop.hbase;

import java.util.List;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository {

	@Autowired
	private HbaseTemplate hbaseTemplate;

	private String tableName = "customer";

	public static byte[] CF_LOGON = Bytes.toBytes("cfLogon");

	private byte[] qEmail = Bytes.toBytes("email");
	private byte[] qPassword = Bytes.toBytes("password");

	public List<Customer> findAll() {
		return hbaseTemplate.find(tableName, "cfLogon", new RowMapper<Customer>() {
			@Override
			public Customer mapRow(Result result, int rowNum) throws Exception {
				return new Customer(Bytes.toString(result.getValue(CF_LOGON, qEmail)),
							    Bytes.toString(result.getValue(CF_LOGON, qPassword)));
			}
		});

	}

	public Customer findByEmail(String email) {
		return hbaseTemplate.get(tableName, email, "cfLogon", new RowMapper<Customer>() {
			@Override
			public Customer mapRow(Result result, int rowNum) throws Exception {
				return new Customer(Bytes.toString(result.getValue(CF_LOGON, qEmail)),
							    Bytes.toString(result.getValue(CF_LOGON, qPassword)));
			}
		});

	}
	
	public Customer save(final String email, final String password) {
		return hbaseTemplate.execute(tableName, new TableCallback<Customer>() {
			public Customer doInTable(HTableInterface table) throws Throwable {
				Customer customer = new Customer(email, password);
				Put p = new Put(Bytes.toBytes(customer.getEmail()));
				p.add(CF_LOGON, qEmail, Bytes.toBytes(customer.getEmail()));
				p.add(CF_LOGON, qPassword, Bytes.toBytes(customer.getPassword()));
				table.put(p);
				return customer;
				
			}
		});
	}

}
