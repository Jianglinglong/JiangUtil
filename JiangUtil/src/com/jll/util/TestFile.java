package com.jll.util;
import java.io.File;
import java.util.LinkedList;
public class TestFile {
	public static void main(String[] args) {
//		LinkedList<File> files = new LinkedList<File>();
		LinkedList<String> files = new LinkedList<String>();
		String srcDir= "D:\\HBuilder";
		String dir = "e:/test";
		FileControls.getAllFiles(srcDir, files);
		for(String file:files) {
//			System.out.println(file);
			FileControls.copyFile(file,(dir+file.substring(file.indexOf(srcDir)+srcDir.length())));
		}
	}
}
