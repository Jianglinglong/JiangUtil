package com.jll.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileControls {
	
	public static boolean copyFile(File srcFile, File destFile) {
		boolean flag = false;
		if (srcFile.exists()) {
			try {
				FileInputStream in = new FileInputStream(srcFile);
				DataInputStream srcIn = new DataInputStream(in);
				FileOutputStream out = new FileOutputStream(destFile);
				DataOutputStream desOut = new DataOutputStream(out);
				int length = 0;
				byte[] readBuffer = new byte[4096];
				while ((length = srcIn.read(readBuffer)) != -1) {
					desOut.write(readBuffer, 0, length);
				}
				desOut.flush();
				desOut.close();
				in.close();
				srcIn.close();
				flag = true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println(srcFile.getAbsolutePath()+"不存在");
		}
		return flag;
	}

	public static boolean copyFile(String srcPath, String desPath) {
		return copyFile(new File(srcPath), new File(desPath));
	}

	public static boolean moveFile(File srcFile, File destFile) {
		boolean flag = copyFile(srcFile, destFile);
		if (flag) {
			srcFile.delete();
		}
		return flag;
	}

	public static boolean moveFile(String srcPath, String desPath) {
		return moveFile(new File(srcPath), new File(desPath));
	}
}
