package com.multilix.hadoop.hbase;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerUtils implements InitializingBean {

	private String tableName = "customer";
	private byte[] tableNameAsBytes = Bytes.toBytes("customer");

	@Resource(name = "hbaseConfiguration")
	private Configuration config;

	@Autowired
	private HbaseTemplate hbaseTemplate;

	@Autowired
	private CustomerRepository customerRepository;

	private HBaseAdmin admin;

	public void initialize() throws IOException {
		if (admin.tableExists(tableNameAsBytes)) {
			if (!admin.isTableDisabled(tableNameAsBytes)) {
				System.out.printf("************** Disabling %s\n", tableName);
				admin.disableTable(tableNameAsBytes);
			}
			
			System.out.printf("************Deleting %s\n", tableName);
			admin.deleteTable(tableNameAsBytes);
		}

		HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
		HColumnDescriptor columnDescriptor = new HColumnDescriptor(CustomerRepository.CF_LOGON);
		tableDescriptor.addFamily(columnDescriptor);

		System.out.printf("************Creating %s\n", tableName);
		admin.createTable(tableDescriptor);

	}

	public void addCustomers() {
		System.out.printf("************Saving data in  %s\n", tableName);
		int cnt = 100;
		for (int i = 0; i < cnt; i++) {
			customerRepository.save(i + "@yahoo.com", "password" + i);
		}	
		
		System.out.printf("************Saved ["+ cnt + "] records data in  %s\n", tableName);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		admin = new HBaseAdmin(config);
	}

}
	
