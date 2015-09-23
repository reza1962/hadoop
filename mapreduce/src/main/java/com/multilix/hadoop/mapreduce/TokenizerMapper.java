package com.multilix.hadoop.mapreduce;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.springframework.context.support.AbstractApplicationContext;

public class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable>{

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    // Spring context injection
    private static final String[] CONFIGS = new String[] {"/META-INF/spring/txm-hbase.xml" };
    private static AbstractApplicationContext ctx;
    
    @Override
    protected void setup(org.apache.hadoop.mapreduce.Mapper<Object,Text,Text,IntWritable>.Context context) throws IOException, InterruptedException {
          super.setup(context);
          //ctx = new ClassPathXmlApplicationContext(CONFIGS);
          //ctx.registerShutdownHook();

          //hbService = ctx.getBean(HbService.class);
    }
    
    
    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
      StringTokenizer itr = new StringTokenizer(value.toString());
      while (itr.hasMoreTokens()) {
        word.set(itr.nextToken());
        context.write(word, one);
      }
    }
  }