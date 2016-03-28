package com.enorth.cms.filter.upload;

import java.io.File;
import java.io.FilenameFilter;

public class UploadImgFileNameFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String filename) {
		if (filename.endsWith(".jpg") || filename.endsWith(".png")
				|| filename.endsWith(".jpeg"))
			return true;
		return false;
	}

}
