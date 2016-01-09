package com.jiangcoder.code;
/**
 * 
 * @author jiangtao
 *
 */
public class ReverseString {
/**
 * 给定一个字符串，要求把字符串前面的若干个字符移动到字符串的尾部，
 * 如把字符串“abcdef”前面的2个字符'a'和'b'移动到字符串的尾部，
 * 使得原字符串变成字符串“cdefab”。请写一个函数完成此功能，
 * 要求对长度为n的字符串操作的时间复杂度为 O(n)， 空间复杂度为 O(1)
 * @param args
 */
	public static void main(String[] args) {
		System.out.println(ReverseJuly("woaini",3));
	}
	/**
	 * author jiangtao
	 * @param s
	 * @param n
	 * @return
	 */
	public static String Reverse(String s,int n){
		char[]temp=s.toCharArray();
		String result = s.substring(n, s.length());
		System.out.println(result);
		if(s.length()>=n){
			for(int i=0;i<n;i++){
				result=result+temp[i];
			}
		}

		return result;
	}
	/**
	 * 暴力移位法
	 * @param s
	 * @param n
	 * @return
	 */
	public static String ReverseJuly(String s,int n){
		char[]temp=s.toCharArray();
		while (n-->0) {
			char first=temp[0];
			for(int i=1;i<s.length();i++){
				temp[i-1]=temp[i];
			}
			temp[s.length()-1]=first;	
		}

		String result="";
		for(int i=0;i< temp.length;i++){
			result=result+temp[i];
		}
		return result;
	}
}
