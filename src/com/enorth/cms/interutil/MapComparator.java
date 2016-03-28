package com.enorth.cms.interutil;

import java.util.Comparator;

public class MapComparator implements Comparator<Integer> {

	@Override
	public int compare(Integer lhs, Integer rhs) {
		return lhs.compareTo(rhs);
	}

}
