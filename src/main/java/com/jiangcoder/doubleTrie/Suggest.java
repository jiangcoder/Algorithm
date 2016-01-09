//package com.jiangcoder.doubleTrie;
//
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.mongodb.BasicDBList;
//
//public class Suggest {
//    private Logger logger = LoggerFactory.getLogger(Suggest.class);
//
//
//    private DTrieSuggest dt = new DTrieSuggest();
//	public Suggest(){		
//	}
//	
//	public void addBlackWord(String word){
//		dt.addBlackWord(word);
//	}
//	
//	public void fixQuery(String key, String value){
//		dt.fixQuery(key, value);
//	}
//	
////	public BasicDBList search(String word){
////        String normquery = StringHelp.normalizeString(word);
////		if(normquery==null || normquery.length()==0){
////			return null;
////		}
////		BasicDBList result = dt.search(word);
////		
////		return result;		
////	}
//}
//
//
//
