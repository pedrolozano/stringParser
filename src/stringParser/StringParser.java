package stringParser;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str1 = "{\"Nothing\": \"is\", \"impossible\" : { \"test\":\"in\", \"the\":\"world!\"}}";

		HashMap<String, Object> map = parserStringJson(str1);
	}

	private static HashMap<String, Object> parserStringJson(String str1) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String key = null;
		Object value = null;
		String delimSpace = ",";
        int index = 0;
		String[] arr1 = str1.split(delimSpace);

		for (String uniqVal1 : arr1) {
			index++;
			if (uniqVal1.startsWith("{")) {
				String node = str1.substring(index);
				String[] splittedNode = node.split(":");
				index++;
				key = findField(splittedNode[0]);
				value = findField(splittedNode[1]);
				if (value.toString().contains("{")) {
					map.put(key, parserStringJson(node));
				} else {
					map.put(key, value);
					index+=key.length()+value.toString().length()+5;
				}
			} else if (uniqVal1.endsWith("}")) {

				String node = uniqVal1.substring(1);
				String[] splittedNode = node.split(":");
				key = findField(splittedNode[0]);
				value = findField(splittedNode[1]);
				System.out.print(key);
				System.out.print(value);
				break;

			} else {
				String node = str1.substring(index, str1.length()-1);
			
				String[] splittedNode2 = node.split(",");
				key = findField(splittedNode2[0]);
				index+= key.length()+3;
				value = str1.substring(index, str1.length()-1);
				if (value.toString().startsWith("{")) {
				
					map.put(key, parserStringJson(value.toString()));
				} else {
					map.put(key, value);
				}
			}

		}
		System.out.print(map);
		return (HashMap<String, Object>) map.put(key, value);

	}

	private static String findField(String uniqVal1) {
		Pattern p = Pattern.compile("\"([^\"]*)\"");
		Matcher m = p.matcher(uniqVal1);
		while (m.find()) {
			return m.group(1);
		}
		return null;
	}

}
