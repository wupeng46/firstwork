package com.lbs.commons;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.FastHashMap;


/**
 * <p>
 * Title: leaf framework (lemis core platform)
 * </p>
 * <p>
 * Description: ������ʽpattern����
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: LBS
 * </p>
 * 
 * @author LBS POC TEAM
 * @version 1.0
 */
public class PatternFactory {
	static Map patternMap = new FastHashMap();

	public PatternFactory() {
	}

	/**
	 * ��ȡ������ʽ��pattern
	 * 
	 * @param regExp -
	 *            ������ʽ
	 * @return Pattern
	 * @throws MalformedPatternException
	 */
	public static Pattern getPattern(String regExp){
		Pattern pattern = null;
		if (patternMap.containsKey(regExp)) {
			pattern = (Pattern) patternMap.get(regExp);
		} else {
			pattern = pattern = Pattern.compile(regExp);
			patternMap.put(regExp, pattern);
		}
		return pattern;
	}
}