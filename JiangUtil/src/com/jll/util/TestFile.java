package com.jll.util;

import java.io.File;
import java.util.LinkedList;

public class TestFile {
	public static void main(String[] args) {
		LinkedList<String> files = new LinkedList<String>();
		String srcDir = "D:\\【猴年新春特别版】问道怀旧单机";
		String dir = "d:\\问道怀旧单机";
		File srcFile = new File(srcDir);
		File destFile = new File(dir);
		String srcFilePath = srcFile.getAbsolutePath();
		String destFilePath = destFile.getAbsolutePath();
		if (srcFile.exists()) {
			if (destFile.exists() || destFile.mkdirs()) {
				FileControls.getAllFiles(srcFilePath, files);
				int total = files.size();
				int copy = 0;
				for (String file : files) {
					copy++;
					System.out.println("移动:" +(int) (((double) copy / total) * 100) + "%");
					int index = file.indexOf(srcFilePath) + srcFilePath.length();
					String str = file.substring(index);
					String dest = destFilePath + str;
					if (!dest.equals(destFilePath)) {
						FileControls.moveFile(file, dest);
					} else {
						FileControls.deletFile(file);
					}
				}
			}else {
				System.out.println("目标文件\""+destFile.getAbsolutePath()+"\"异常");
			}
		} else {
			System.out.println("源文件\"" + srcFile.getAbsolutePath() + "\"不存在");
		}
	}
}
