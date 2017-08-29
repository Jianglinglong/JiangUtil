package com.jll.util;

import java.io.File;
import java.util.LinkedList;

public class TestFile {
	public static void main(String[] args) {
		LinkedList<String> files = new LinkedList<String>();
		String srcDir  = "E:\\姜玲珑\\";
		String dir = "e:\\test";
		File srcFile = new File(srcDir);
		File destFile = new File(dir);
		String srcFilePath = srcFile.getAbsolutePath();
		String destFilePath = destFile.getAbsolutePath();
		if (srcFile.exists()) {
			FileControls.getAllFiles(srcFilePath, files);
			int total = files.size();
			int copy = 0;
			for (String file : files) {
				copy++;
				System.out.println("移动:" + ((double) copy / total)*100 + "%");
				int index = file.indexOf(srcFilePath) + srcFilePath.length();
				String str = file.substring(index);
				String dest = destFilePath + str;
				if(!dest.equals(destFilePath)) {
					FileControls.moveFile(file, dest);
				}else {
					FileControls.deletFile(file);
				}
			}
		}else {
			System.out.println(srcDir+"不存在");
		}
	}
}
