//package com.jiangcoder.search;
//
//import org.t3.client.beanstalk.BeanstalkJob;
//import org.t3.client.beanstalk.StringSerializer;
//import org.t3.client.beanstalk.pool.BeanstalkClient;
//import org.t3.client.beanstalk.pool.BeanstalkPool;
//
//import com.mongodb.BasicDBList;
//import com.mongodb.util.JSON;
//
//public class Test {
//
//	public static void main(String[] args) throws Exception {
//		// TODO Auto-generated method stub
//		
//		BeanstalkClient watchBeansConnect=BeanstalkPool.getClient("10.58.50.53", 11300, "category",new StringSerializer());
//		BeanstalkJob job = null;
//		 job = watchBeansConnect.reserve(5);
//         //watchBeansConnect.deleteJob(job);
//		  BasicDBList jobData =  (BasicDBList) JSON.parse((String)job.get());
//		  System.out.println(jobData);
//	}
//
//}
