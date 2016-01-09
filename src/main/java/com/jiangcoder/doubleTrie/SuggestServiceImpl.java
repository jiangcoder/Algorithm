//package com.jiangcoder.doubleTrie;
//
//import java.util.concurrent.locks.ReadWriteLock;
//import java.util.concurrent.locks.ReentrantReadWriteLock;
//
//import javax.inject.Singleton;
//
//import org.louis.elasticrpc.annotation.RPCSignature;
//import org.louis.elasticrpc.util.Context;
//import org.louis.elasticrpc.util.LifecycleAware;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.mongodb.BasicDBList;
//import com.mongodb.BasicDBObject;
//
//public class SuggestServiceImpl implements SuggestApi, LifecycleAware {
//	
//	private DTrieSuggest dTrieSuggest = new DTrieSuggest();
//	private ReadWriteLock rwl = new ReentrantReadWriteLock();
//	
//	protected static Logger logger = LoggerFactory.getLogger(SuggestServiceImpl.class);
//
//	@Override
//	public void shutdown() throws Exception {
//		// TODO Auto-generated method stub		
//	}
//
//	@Override
//	public void startup() throws Exception {
//		logger.info("start up called");
//		dTrieSuggest.load();
//	}
//
//    @Override
//	public BasicDBList match(BasicDBObject prefixQuery) {
//		String queryString =  prefixQuery.getString("queryString", "");
//        queryString = queryString.toLowerCase();
//		BasicDBList result;
//		try{
//			rwl.readLock().lock();
//			result = dTrieSuggest.search(queryString);
//		}
//		finally{
//			rwl.readLock().unlock();
//		}
//		return result;
//
//	}
//
//	/**
//	 * (non Javadoc)
//	 * @Title: noticeLoad
//	 * @Description: TODO
//	 * @param para
//	 * @return
//	 * @see org.t3.service.extendsearch.suggest.SuggestApi#noticeLoad(com.mongodb.BasicDBObject)
//	 */
//	@Override
//	public boolean noticeLoad(BasicDBObject para) {
//		DTrieSuggest newSuggest = new DTrieSuggest();
//		long start = System.currentTimeMillis();
//		logger.info("[SuggestServiceImpl] receive Msg noticeLoad");
//		//把suggest需要的参数文件加载到内存
//		newSuggest.load();
//		logger.info("[SuggestServiceImpl] load SuggestFilePara spend Second={}s", new Object[]{(System.currentTimeMillis() - start) * 1.0 / 1000});
//		try{
//			rwl.writeLock().lock();
//			dTrieSuggest = newSuggest;
//		}
//		finally{
//			rwl.writeLock().unlock();
//		}
//		
//		return true;
//	}
//
//	@Override
//	public void callback(Context context) throws Exception {
//	}
//}
