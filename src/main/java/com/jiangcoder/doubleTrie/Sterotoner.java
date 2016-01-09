//package com.jiangcoder.doubleTrie;
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
//import java.util.HashSet;
//import java.util.Set;
//import java.util.Vector;
//
//
//public class Sterotoner {
//	private Vector<Set<String>> pinyinIndex = new Vector<Set<String>>();
//	private static final String dictname = "NewDict.dat";
//	private static String correctDir;
//	private final int MaxChNum = 100000;
//	private static Sterotoner instance = null;
//
//	static{
//		instance = new Sterotoner();
//	}
//	
//	public static Sterotoner getInstance(){
//		return instance;
//	}
//	
//	private Sterotoner(){
//		correctDir = StringUtil.append(Global.NFS_HOME, Global.EXTENDSEARCH_PATH, "correct/") ;
//		MyGlobal.checkDir(correctDir);
//		for(int i=0; i<MaxChNum; ++i){
//			Set<String> set = new HashSet<String>();
//			pinyinIndex.add(set);
//		}
//		loadPinyinDict();
//	}
//	
//	public boolean loadPinyinDict(){
//		try{
//			String filename = correctDir + dictname;
//			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "utf-8"));
//			String line = "";
//			while((line=reader.readLine())!=null){
//				line = line.trim();
//				if(line.length()==0){
//					continue;
//				}
//				String[] strArray = line.split("\t");
//				if(strArray.length!=2){
//					continue;
//				}
//				if(strArray[0].length()!=1){
//					continue;
//				}
//				int index = strArray[0].charAt(0);
//				if(CharUtil.isChinese(strArray[0].charAt(0))){
////					System.out.println(strArray[0].charAt(0));
//					pinyinIndex.elementAt(index).add(strArray[1]);
//				}
//			}
//			reader.close();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return false;
//	}
//	
//	public Set<String> getPinyin(int index){
//		if(index<0 || index>pinyinIndex.size()){
//			return null;
//		}
//		return pinyinIndex.elementAt(index);
//	}
//	
//	public void getPinyinAll(String query, Set<String> pinyinset, Set<String> firstpinyinset){
//		String str1 = "";
//		String str2 = "";
//		int pos = 0;
//		getPinyinAll(query, pos, str1, str2,  pinyinset,  firstpinyinset);
//	}
//	
//	public void getPinyinAll(String query, int pos, String str1, String str2, Set<String> pinyinset, Set<String> firstpinyinset){
//		if(pos==query.length()){
//			pinyinset.add(str1);
//			firstpinyinset.add(str2);
//		}else{
//			int index = query.charAt(pos);
//			if(CharUtil.isChinese(query.charAt(pos))){
//				Set<String> set = getPinyin(index);
//				if(set==null){
//					return;
//				}
//				for(String s : set){
//					getPinyinAll(query, pos+1, str1+s, str2+s.charAt(0),  pinyinset,  firstpinyinset);
//				}
//			}else{
//				getPinyinAll(query, pos+1, str1+query.charAt(pos), str2,  pinyinset,  firstpinyinset);
//			}
//		}
//	}	
//	
//	public static void main(String[] argc){
//		Sterotoner st = new Sterotoner();
//		String str = "笔记本";
//		Set<String> fpy = new HashSet<String>();
//		Set<String> apy = new HashSet<String>();
//		st.getPinyinAll(str, apy, fpy);
//		for(String s: apy)
//		{
//			System.out.println(s);
//		}
//		for(String s: fpy)
//		{
//			System.out.println(s);
//		}		
//	}
//}
//
//
//
//
