package com.enorth.cms.utils;

import android.content.Context;
import android.content.res.Resources;

public class DimenUtil {
	
	private static Resources resources;

	public static float getDimension(Context context, int dimenId) {
		if (resources == null) {
			resources = context.getResources();
		}
		return resources.getDimension(dimenId);
	}
}
