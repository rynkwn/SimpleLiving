package util;

public class StringUtils {
	
	public static String repeat(String str, int num) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < num; i++) {
			sb.append(str);
		}
		
		return sb.toString();
	}

}
