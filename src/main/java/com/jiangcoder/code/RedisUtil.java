package com.jiangcoder.code;
//package com.jiangcoder.code;
//
//import java.util.Set;
//
//public class RedisUtil {
//	    public static void main(String[] args) {
//	
//			String ips = "10.58.22.12:7000,10.58.50.24:7000,10.58.51.86:7000,10.58.22.12:7001,10.58.50.24:7001,10.58.51.86:7001";
//			 String[] ipPorts = ips.split(",");//
//			Set<HostAndPort> nodes = new HashSet<HostAndPort>();
//			for (String item : ipPorts) {
//				String[] addrItem = item.split(":");
//				nodes.add(new HostAndPort(addrItem[0], Integer.parseInt(addrItem[1])));
//			}
//	
//			JedisPoolConfig conf = new JedisPoolConfig();
//			conf.setMaxWaitMillis(10000);
//			conf.setMaxIdle(60000);
//			conf.setMaxTotal(6000);
//	
//			JedisCluster jedis = new JedisCluster(nodes, 5000, conf);
//	
//			String redisKeyField[] = splitKey(StringUtil.append("d", "@", "A0005478769"));
//			boolean isUncompress = true;
//			byte[] valueb = jedis.hget(redisKeyField[0].getBytes(), redisKeyField[1].getBytes());
//		//	System.out.println(StringUtil.toString(StringUtil.uncompress(valueb)));
//			String newValue = valueb != null ? StringUtil.toString((true ? StringUtil.uncompress(valueb) : valueb)) : null;
//			System.out.println(JSON.parse(newValue));
//	
//	//		String value = jedis.get("category" + "@" + "cat18596268");
//	//		System.out.println(value);
//			try {
//				jedis.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//}
//
