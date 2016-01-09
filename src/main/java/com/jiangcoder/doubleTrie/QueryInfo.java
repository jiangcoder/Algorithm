package com.jiangcoder.doubleTrie;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class QueryInfo  implements Comparable<QueryInfo>{
		public String word = null;
		public int searchcnt = 0;
		public int docnum = 0;
		public String cat1 = null;
		public String cat2 = null;

		public List<String> catidlist = null;
		public List<String> catnamelist = null;
		public List<Integer> catdoclist = null;
		
		public List<String> parentcatlist = null;
		public List<String> parentcatnamelist = null;
		public QueryInfo(String q, int n, int dn){
			word = q;
			searchcnt = n;
			docnum = dn;
		}
		public void setCat(String[] s){
			if(s.length!=5){
				return;
			}
			if(catidlist==null){
				catidlist = new ArrayList<String>();
				catnamelist  = new ArrayList<String>();
				parentcatlist  = new ArrayList<String>();
				parentcatnamelist  = new ArrayList<String>();
				catdoclist = new ArrayList<Integer>();
			}
			catidlist.add(s[0]);			
			catdoclist.add(Integer.parseInt(s[1]));
			catnamelist.add(s[2]);
			
			parentcatlist.add(s[3]);
			parentcatnamelist.add(s[4]);
		}
		public int compareTo(QueryInfo o) {
			// TODO Auto-generated method stub
			if(this.searchcnt<o.searchcnt){
				return 1;
			}
			if(this.searchcnt==o.searchcnt){
				if(this.word.equals(o.word)){
					return 0;
				}
				return 1;
			}
			return -1;
		}
		public static void main(String[] argc){
			Set<QueryInfo> set = new TreeSet<QueryInfo>();
			set.add(new QueryInfo("a", 12, 1));
			set.add(new QueryInfo("b", 11, 1));
			set.add(new QueryInfo("c", 13, 1));
			set.add(new QueryInfo("d", 9, 1));
			for(QueryInfo q : set){
				System.out.println(q.word + "\t" + q.searchcnt);
			}
		}
	}