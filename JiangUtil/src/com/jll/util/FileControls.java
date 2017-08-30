package com.jll.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 文件对象操作工具包
 * 
 * @author 姜玲珑2017年8月28日
 *
 */
public class FileControls {
	public static boolean deletFile(String filePath) {
		return deletFile( new File(filePath));
	}
	public static boolean deletFile(File file) {
		return file.delete();
	}
	
	/**
	 * 文件拷贝
	 * 
	 * @param srcFile
	 *            源文件绝对路径的文件对象
	 * @param destFile
	 *            目标文件绝对路径的文件对象
	 * @return 成功拷贝返回true，否则为false
	 */
	public static boolean copyFile(File srcFile, File destFile) {
		boolean flag = false;
		if (srcFile.exists()) {
			try {
				if (!srcFile.isDirectory()) {
					destFile.getParentFile().mkdirs();
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
				}else {
					destFile.mkdirs();
					flag = true;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println(srcFile.getAbsolutePath() + "不存在");
		}
		return flag;
	}

	/**
	 * 
	 * 文件拷贝
	 * 
	 * @param srcFile
	 *            源文件绝对路径的字符串
	 * @param destFile
	 *            目标文件绝对路径的字符串
	 * @return 成功拷贝返回true，否则为false
	 */
	public static boolean copyFile(String srcPath, String desPath) {
		File srcFile = new File(srcPath);
		File destFile = new File(desPath);
		// if(srcFile.isDirectory()) {
		// return false;
		// }
		return copyFile(srcFile, destFile);
	}

	/**
	 * 移动文件
	 * 
	 * @param srcFile
	 *            源文件绝对路径的文件对象
	 * @param destFile
	 *            目标文件绝对路径的文件对象
	 * @return 成功移动返回true，否则为false
	 */

	public static boolean moveFile(File srcFile, File destFile) {
		boolean flag = copyFile(srcFile, destFile);
		if (flag) {
			srcFile.delete();
		}
		return flag;
	}

	/**
	 * 移动文件
	 * 
	 * @param srcFile
	 *            源文件绝对路径的字符串
	 * @param destFile
	 *            目标文件绝对路径的字符串
	 * @return 成功移动返回true，否则为false
	 */
	public static boolean moveFile(String srcPath, String desPath) {
		return moveFile(new File(srcPath), new File(desPath));
	}

	/**
	 * 遍历当前文件夹
	 * 
	 * @param srcFile
	 *            文件绝对路径字对象
	 * @return 文件不存在返回-1，否则返回文件家中的文件数
	 */
	public static int show(File srcFile) {
		if (!srcFile.exists()) {
			System.out.println(srcFile.getAbsolutePath() + "文件不存在");
			return -1;
		}
		File[] files = srcFile.listFiles();
		if (files == null || files.length < 1) {
			System.out.println("空文件夹");
			return 0;
		}
		for (File file : files) {
			System.out.println(file.getAbsolutePath());
		}
		return files.length;
	}

	/**
	 * 遍历当前文件夹
	 * 
	 * @param scrPath
	 *            文件绝对路径字符串
	 * @return 文件不存在返回-1，否则返回文件家中的文件数
	 */
	public static int show(String scrPath) {
		File srcFile = new File(scrPath);
		if (!srcFile.exists()) {
			System.out.println(scrPath + "文件不存在");
			return -1;
		}
		File[] files = srcFile.listFiles();
		if (files == null || files.length < 1) {
			System.out.println("空文件夹");
			return 0;
		}
		for (File file : files) {
			System.out.println(file.getAbsolutePath());
		}
		return files.length;
	}

	public static int showAllFiles(String srcPath) {
		File srcFile = new File(srcPath);
		if (!srcFile.exists()) {
			System.out.println(srcPath + "文件不存在");
			return -1;
		}
		File[] files = srcFile.listFiles();
		System.out.println(srcFile.getAbsolutePath() + ",下有" + files.length + "个文件");
		if (files == null || files.length < 1) {
			System.out.println(srcFile.getAbsolutePath());
			return 0;
		}
		for (File file : files) {
			if (file.isDirectory()) {
				showAllFiles(file.getAbsolutePath());
			} else {
				System.out.println(file.getAbsolutePath());
			}
		}
		return files.length;
	}

	public static void getAllFiles(String srcPath, LinkedList<String> files) {
		File file = new File(srcPath);
		if (file.exists()) {
			File[] fileList = file.listFiles();
			if (fileList != null && fileList.length > 0) {
				for (File src : fileList) {
					getAllFiles(src.getAbsolutePath(), files);
				}
			}
			files.add(file.getAbsolutePath());
		}
	}
	public static void getAllFiles(File srcPath, LinkedList<File> files) {
		if (srcPath.exists()) {
			File[] fileList = srcPath.listFiles();
			if (fileList != null && fileList.length > 0) {
				for (File src : fileList) {
					getAllFiles(src, files);
				}
			}
			files.add(srcPath);
		}
	}
}
