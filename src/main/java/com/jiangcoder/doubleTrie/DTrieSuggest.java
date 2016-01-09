//package com.jiangcoder.doubleTrie;
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map.Entry;
//import java.util.Set;
//import java.util.TreeSet;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import com.mongodb.BasicDBList;
//import com.mongodb.BasicDBObject;
//
//public class DTrieSuggest {
//	private int[] baseindex = null;
//	private int[] checkindex = null;
//	private DANode[] nodeindex = null;
//
//	private String suggestDir;
//	private Sterotoner ston = Sterotoner.getInstance();
//	private HashMap<Integer, DANode> hashindex = new HashMap<Integer, DANode>();
//	private List<QueryInfo> inverttable = new ArrayList<QueryInfo>();
//	private HashMap<String, Set<Character>> hsinverts = new HashMap<String, Set<Character>>();
//	private List<HashMap<String, Set<QueryInfo>>> wordFromIndex = new ArrayList<HashMap<String, Set<QueryInfo>>>();
//	private HashMap<String, String> fixhash = new HashMap<String, String>();
//	private Set<String> blacklist = new HashSet<String>();
//
//    protected static Logger logger = LoggerFactory.getLogger(DTrieSuggest.class);
//
//
//
//    private static int cursor = 0;
//	private static int MAXPREFIXLENGTH = 10;
//	private static int BASESIZE = 0;
//	private static int RESULTNUM = 15;
//	
//	public DTrieSuggest(){
////		ston.init(correctDir);  //加载数据不需要loadpinyin
//		suggestDir = StringUtil.append(Global.NFS_HOME + Global.EXTENDSEARCH_PATH + "suggest/");
//		MyGlobal.checkDir(suggestDir);
//	}
//	
//	public void addBlackWord(String word){
//		synchronized(blacklist){
//			blacklist.add(word);
//		}
//	}
//	
//	public void fixQuery(String key, String value){
//		synchronized(fixhash){
//			fixhash.put(key, value);
//		}
//	}
//		
//	public void load(){	
//		long start = System.currentTimeMillis();
//		load(suggestDir +"size.dat", 1);	
//		load(suggestDir + "base.dat", 2);
//		load(suggestDir + "check.dat", 3);
//		load(suggestDir + "node.dat", 4);
//		load(suggestDir + "invert.dat", 5);
//		logger.info("load cost:" + (System.currentTimeMillis()-start));
//	}
//	
//	
//	public void load(String filename, int type){
//		try{
//		 	BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), MyGlobal.ENCODE) );
//         String line;                     
//         while ( (line = reader.readLine()) != null){ 
//        	 	line = line.trim();
//    	 		String[] s = line.split("\t");
//    	 		switch(type){
//					case 1:
//						BASESIZE = Integer.parseInt(line);   
//						checkindex = new int[BASESIZE+1];
//						baseindex = new int[BASESIZE+1];
//						nodeindex = new DANode[BASESIZE+1];				
//						break;
//	
//					case 2:
//						baseindex[Integer.parseInt(s[0])] = Integer.parseInt(s[1]);
//						break;
//					case 3:
//						checkindex[Integer.parseInt(s[0])] = Integer.parseInt(s[1]);
//						break;
//					case 4:
//						nodeindex[Integer.parseInt(s[0])] = new DANode(Integer.parseInt(s[1]), Integer.parseInt(s[2]));
//						break;
//					case 5:
//						String[] cs = s[0].split(":");
//						QueryInfo qi = new QueryInfo(cs[0], Integer.parseInt(cs[1]), Integer.parseInt(cs[2]));
//						if(s.length>2){
//							cs = s[1].split(":");
//							qi.setCat(cs);
//							if(s.length==3){
//								cs = s[2].split(":");
//								qi.setCat(cs);
//							}
//						}
//						inverttable.add(qi);   
//						break;
//	    	 		}
//        	}   
//         reader.close();
//			
//		}catch(Exception e){
//            logger.error("suggest data load error,dataDir=[{}],type=[{}],exception=[{}]",new Object[]{filename,type,e});
//		}
//		
//	}
//	
//	public void save(){
//		save(suggestDir + "size.dat", 1);	
//		save(suggestDir + "base.dat", 2);
//		save(suggestDir + "check.dat", 3);
//		save(suggestDir + "node.dat", 4);
//		save(suggestDir + "invert.dat", 5);	
//	}
//	
//	public void save(String filename, int type){
//		try{
//			FileOutputStream out = new FileOutputStream(filename);
//			switch(type){
//				case 1:
//					out.write((baseindex.length+"\n").getBytes(MyGlobal.ENCODE));
//					break;
//				case 2:
//					for(int i=0; i<baseindex.length; ++i){
//						if(baseindex[i]==0){
//							continue;
//						}
//						String tmp = i + "\t" + baseindex[i] + "\n";	  
//						out.write(tmp.getBytes(MyGlobal.ENCODE));
//					}   
//					break;
//				case 3:
//					for(int i=0; i<checkindex.length; ++i){
//						if(checkindex[i]==0){
//							continue;
//						}
//						String tmp = i + "\t" + checkindex[i] + "\n";	  
//						out.write(tmp.getBytes(MyGlobal.ENCODE));
//					}   
//					
//					break;
//				case 4:
//					for(Entry<Integer, DANode> entry : hashindex.entrySet()){
//						int i = entry.getKey();
//						DANode da = entry.getValue();
//						String tmp = i + "\t" + da.start  + "\t" + da.end + "\n";
//		            out.write(tmp.getBytes(MyGlobal.ENCODE));
//					}   
//					break;
//				case 5:				
//					for(QueryInfo r : inverttable){
//						String tmp = r.word+":"+r.searchcnt+":"+r.docnum;
////						if(r.cat1!=null){
////							tmp+="\t" + r.cat1 + ":" + r.cat1num + ":" +  ":" + r.parentcatlist;
////						}
////						if(r.cat2!=null){
////							tmp+="\t" + r.cat2 + ":" + r.cat2num;
////						}
//						if(r.catdoclist!=null){
//							for(int i=0; i<r.catidlist.size(); ++i){
//								tmp += "\t" + r.catidlist.get(i) + ":" + r.catdoclist.get(i) + ":" + r.catnamelist.get(i) +
//										":" + r.parentcatlist.get(i) + ":" + r.parentcatnamelist.get(i);
//							}
//						}
//						tmp += "\n";
//		            out.write(tmp.getBytes(MyGlobal.ENCODE));
//					}  					  
//					break;
//			}
//			
//			out.close();
//			
//		}catch(Exception e){
//            logger.error("suggest data save error,dataDir=[{}],type=[{}],exception=[{}]",new Object[]{filename,type,e});
//		}
//	}
//	
//	
//	
//
//	public void insertQuery(String py, String query, QueryInfo qi){
//		int t = query.charAt(0);
//		if(t>cursor){
//			cursor = t+1;
//		}
//		String subword = "";
//		for(int i=0; i<py.length() && i<MAXPREFIXLENGTH; ++i){
//			subword += py.charAt(i);
//			int j = i+1;
//			if(j<py.length()){
//				if(hsinverts.containsKey(subword)){
//					hsinverts.get(subword).add(py.charAt(j));
//				}else{
//					Set<Character> set = new TreeSet<Character>();
//					set.add(py.charAt(j));
//					hsinverts.put(subword, set);
//				}
//			}
//			if(wordFromIndex.get(i).containsKey(subword)){
//				if(wordFromIndex.get(i).get(subword).size()<RESULTNUM){
//					wordFromIndex.get(i).get(subword).add(qi);
//				}
//				continue;
//			}
//			Set<QueryInfo> set = new TreeSet<QueryInfo>();
//			set.add(qi);
//			wordFromIndex.get(i).put(subword, set);
//		}
//	}
//	
//	
//	public void insertQuery(String query, QueryInfo qi){
//		int t = query.charAt(0);
//		if(t>cursor){
//			cursor = t+1;
//		}
//		String subword = "";
//		for(int i=0; i<query.length() && i<MAXPREFIXLENGTH; ++i){
//			subword += query.charAt(i);
//			int j = i+1;
//			if(j<query.length()){
//				if(hsinverts.containsKey(subword)){
//					hsinverts.get(subword).add(query.charAt(j));
//				}else{
//					Set<Character> set = new TreeSet<Character>();
//					set.add(query.charAt(j));
//					hsinverts.put(subword, set);
//				}
//			}
//			if(wordFromIndex.get(i).containsKey(subword)){
//				if(wordFromIndex.get(i).get(subword).size()<RESULTNUM){
//					wordFromIndex.get(i).get(subword).add(qi);
//				}
//				continue;
//			}
//			Set<QueryInfo> set = new TreeSet<QueryInfo>();
//			set.add(qi);
//			wordFromIndex.get(i).put(subword, set);
//		}
//	}
//	
//	public boolean loadCleanData(String filename){
//		for(int i=0; i<MAXPREFIXLENGTH; ++i){
//			HashMap<String, Set<QueryInfo>> invert = new HashMap<String, Set<QueryInfo>>();
//			wordFromIndex.add(invert);
//		}	
//		
//		checkindex = new int[1000000];
//		baseindex = new int[1000000];
//		HashMap<String, Boolean> hashquery = new HashMap<String, Boolean>();
//		BufferedReader reader = null;
//		try{
//			reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "utf-8") );
//			String line = null;
//			String query = null;
//			
//			int cnt = 0;
//			while ( (line = reader.readLine()) != null){ 
//				if(++cnt%10000==0){
//                    logger.error("suggest clean load,count=[{}]",new Object[]{cnt});
//				}
//				line = line.trim();
//				String[] s = line.split("\t");
//				String[] qs = s[0].split(":");	
//				query = StringHelp.normalizeString(qs[0].toLowerCase().trim());
//				if(query==null || query.length()==0){
//					continue;
//				}
//				QueryInfo qi = new QueryInfo(query, Integer.parseInt(qs[1]), Integer.parseInt(qs[2]));
//
//				if(s.length>1){
//					String[]cs = s[1].split(":");
//					qi.setCat(cs);
//					if(s.length==3){
//						cs = s[2].split(":");
//						qi.setCat(cs);
//					}			
//				}
//
//				hashquery.put(query, true);
//				insertQuery(query, qi);		
//				
//				for(int i=1; i<query.length(); ++i){
//					if(hashquery.containsKey(query.substring(i, query.length()))){
//						insertQuery(query.substring(i, query.length()), query, qi);
//					}
//				}
//				Set<String> fpy = new HashSet<String>();
//				Set<String> apy = new HashSet<String>();
//				ston.getPinyinAll(query, apy, fpy);
//				for(String py : fpy){
//					insertQuery(py, query,  qi);
//				}
//				for(String py : apy){
//					insertQuery(py, query, qi);
//				}
//				
//				
//	        }
//            logger.info("suggest data Max code={[]}", new Object[]{cursor});
//		}catch(Exception e){
//            logger.info("suggest prepare data error={[]}", new Object[]{e});
//		}finally{
//			if(null!=reader){
//				try {
//					reader.close();
//				} catch (IOException e) {
//                    logger.info("suggest prepare data file close error={[]}", new Object[]{e});
//				}
//			}
//		}
//		
//		return true;
//	}
//	
//	
//	public void resize(){
//		baseindex = Arrays.copyOf(baseindex, baseindex.length*2);
//		checkindex = Arrays.copyOf(checkindex, checkindex.length*2);
//	}
//	
//	public void checkSize(int t){
//		if(t>=baseindex.length){
//			resize();
//		}
//	}
//	
//	public boolean checkExist(int base, Set<Character> set){		
//		boolean isExist = false;
//		int i = base;
//		for(int c : set){
//			int t = base + c;
//			checkSize(t);
//			if(baseindex[++i]!=0 || baseindex[t]!=0 || checkindex[t]!=base){
//				isExist = true;
//				break;
//			}
//		}
//		return isExist;
//	}
//	
//
//	public int getPrefixBase(String query){
//		int t = query.charAt(0);
//		int base = baseindex[t];
//		if(base==0){
//            logger.info("{[]}:[0],[{}] not exist!", new Object[]{query, query.charAt(0)});
//			return -1;
//		}
//		int len = query.length();
//		for(int i=1; i<len; ++i){
//			t = base + query.charAt(i);
//			if(t>=baseindex.length){
//                logger.info("{[]}:[0],[{}] not exist!", new Object[]{query, query.charAt(0)});
//				return -1;
//			}
//			base = baseindex[t];
//			if(base==0 || checkindex[t]!=base){
//                logger.info("{[]}:[0],[{}] not exist!", new Object[]{query, query.charAt(0)});
//				return -1;
//			}	
//		}
//		return t;
//	}
//		
//	public int getPrefix(String query){
//		int t = query.charAt(0);
//		int base = baseindex[t];
//		if(base==0){
//            logger.info("{[]}:[0],[{}] not exist!", new Object[]{query,query.charAt(0)});
//			return -1;
//		}
//		int len = query.length();
//		for(int i=1; i<len; ++i){
//			t = base + query.charAt(i);
//			if(t>=baseindex.length){
//                logger.info("[{}] base==0!", new Object[]{query});
//				return -1;
//			}
//			base = baseindex[t];
//			if(base==0 && i==len-1){
//                logger.info("[{}] base==0!", new Object[]{query});
//				return -1;
//			}
//		}
//		return t;
//	}
//	
//	public void resetBase(int t, Set<Character> set){
//		int i = cursor+1;
//		int base = cursor+1;
//		boolean isbreak = true;
//		while(i<baseindex.length){	
//			for(int c : set){
//				int n = base + c;
//				checkSize(n);
//				if(baseindex[++i]==0 && baseindex[n]==0 && checkindex[n]==0){
//					continue;
//				}
//				isbreak = false;
//				break;
//			}
//			if(isbreak){
//				break;
//			}
//			isbreak = true;
//			while(baseindex[i]!=0){
//				i++;
//			}
//			base = i;
//		}
//		baseindex[t] = base;
//		i = base;		
//		for(int c : set){
//			baseindex[base+c] = ++i;
//			checkindex[t] = base;
//		}
//		while(baseindex[i]!=0){
//			i++;
//		}
//		cursor = i;
//	}
//	
//	public int  insertSuffix(String query, Set<Character> set){
//		int t = getPrefix(query);		
//		int id = t;
//		if(t==-1 || set==null){
//            logger.info("[{}] t==-1 base==0", new Object[]{query});
//			return 0;
//		}		
//		int base = baseindex[t];
//		int i = base;
//		boolean isExist = checkExist(base, set);		
//		if(isExist){
//			resetBase(t, set);
//		}else{
//			for(int c : set){
//				t = base + c;
//				baseindex[t] = ++i;
//				checkindex[t] = base;
//			}
//			while(baseindex[i]!=0){
//				i++;
//			}
//			cursor = i;
//		}
//		return id;
//	}
//
//	public int insertHeader(String query, Set<Character> set){
//		int t = query.charAt(0);
//		int id = t;
//		if(set==null){
//			return 0;
//		}		
//		int base = cursor;
//		baseindex[t] = cursor;
//		int i = cursor;
//		boolean isExist = checkExist(base, set);
//		if(isExist){
//			resetBase(t, set);
//		}else{
//			for(int c : set){
//				t = base + c;
//				baseindex[t] = ++i;
//				checkindex[t] = base;
//			}
//			while(baseindex[i]!=0){
//				i++;
//			}
//			cursor = i;
//		}		
//		return id;
//	}
//	
//	public void insertHeader(){
//		int id = 0;
//		HashMap<String, Set<QueryInfo>> hash = wordFromIndex.get(0);
//		for(Entry<String,Set<QueryInfo>> entry : hash.entrySet()){
//			String key = entry.getKey();
//			id = insertHeader(key, hsinverts.get(key));	
//			Set<QueryInfo> set = hash.get(key);
//			int invertpos = inverttable.size();
//			
//			for(QueryInfo s : set){
//				inverttable.add(s);
//			}
//			hashindex.put(id, new DANode(invertpos, inverttable.size()));
//		}
//	}
//	
//	public  void insertSuffix(){
//		int id = 0;
//		for(int i=1; i<wordFromIndex.size(); ++i){
//			HashMap<String, Set<QueryInfo>> hash = wordFromIndex.get(i);
//			for(Entry<String,Set<QueryInfo>> entry : hash.entrySet()){
//				String key = entry.getKey();
//				if(hsinverts.get(key)==null){
//					id = getPrefix(key);
//				}else{
//					id = insertSuffix(key, hsinverts.get(key));
//				}
//				Set<QueryInfo> set = hash.get(key);
//				int invertpos = inverttable.size();				
//				for(QueryInfo s : set){
//					inverttable.add(s);
//				}
//				hashindex.put(id, new DANode(invertpos, inverttable.size()));
//			}
//		}
//	}
//	
//	public void build(){		
//		insertHeader();
//		insertSuffix();
//		
//	}
//
//	public BasicDBList search(String query){	
//		if(query==null || query.length()==0){
//			return null;
//		}
//		int id = getPrefix(query);
//		if(id==-1){
//			return null;
//		}
//		DANode da = nodeindex[id];
//		if(da==null){
//			return null;
//		}
//		int start = da.start;
//		int end = da.end;
//		int cnt = 0;
//		
//		BasicDBList result = new BasicDBList();
//		boolean iscat = false;
//		for(int i=start; i<end && ++cnt<10; ++i){
//			QueryInfo qi = inverttable.get(i);
//			String word = qi.word;
//			if(blacklist.contains(word)){
//				continue;
//			}
//			if(fixhash.containsKey(word)){
//				result.add(inverttable.get(i));
//			}
//			BasicDBList list = new BasicDBList();
//			list.add(word);
//			list.add(qi.docnum);
//			list.add(0);
//			result.add(list);
//			if(qi.catidlist!=null && !iscat){
//				iscat = true;
//				for(int j=0; j<qi.catidlist.size() && j<2; ++j){
//					BasicDBList list2 = new BasicDBList();
//					list2.add(word);
//					list2.add(qi.docnum);
//					list2.add(1);					
//					BasicDBObject obj = new BasicDBObject();
//					BasicDBList list3 = new BasicDBList();
//					list3.add(qi.catnamelist.get(j));
//					list3.add(qi.catidlist.get(j));
//					list3.add(qi.catdoclist.get(j));
//                    list3.add(qi.parentcatnamelist.get(j));
//                    list3.add(qi.parentcatlist.get(j));
//					obj.put("cat", list3);
//					list2.add(obj);
//					result.add(list2);
//				}		
//			}		
//		}
//		return result;
//	}
//	
//	public static void main(String[] agrc){
//		DTrieSuggest da = new DTrieSuggest();
//		long start = System.currentTimeMillis();
//		da.loadCleanData("/home/fyy/dict/clean.data");
//		System.out.println("load clean log cost:" + (System.currentTimeMillis()-start)*1.0/1000 + "s");
//		start = System.currentTimeMillis();
//		da.build();
//		System.out.println("build cost:" + (System.currentTimeMillis()-start)*1.0/1000 + "s");
//		start = System.currentTimeMillis();
//		da.save();
//		System.out.println("save cost:" + (System.currentTimeMillis()-start)*1.0/1000 + "s");
//		
//		da.load();		
//		System.out.println( da.search("s"));	
////		System.out.println(da.Search("sn"));	
//		
////		try{
////			int cnt = 0;
////			int nullcnt = 0;
//////			long start = System.currentTimeMillis();
////		 	BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/home/fyy/py/testword.txt"), MyGlobal.ENCODE) );
////         String line;                     
////         while ( (line = reader.readLine()) != null){
////        	 	++cnt;
////        	 	line = StringHelp.normalizeString(line.trim());
////        	 	if(line.length()>2){
////        	 		line = line.substring(0, 2);
////        	 	}
////        	 	System.out.println(line + "\t" + da.Search(line));
//////        	 	String s = da.Search(line);
//////        	 	
//////        	 	if(s==null || s.length()==0){
//////        	 		++nullcnt;
//////        	 	}
////         	}
////         System.out.println(cnt + "\t|" + nullcnt  + "|\t"+ (System.currentTimeMillis()-start));
////         reader.close();
////		}catch(Exception e){
////			e.printStackTrace();
////		}		
//	}
//}
