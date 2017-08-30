package com.jll.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 文件对象操作工具包
 * 
 * @author 姜玲珑2017年8月28日
 *
 */
public class FileControls {
	public static class Filesize implements Serializable {
		private static final long serialVersionUID = -1035346214239079079L;
		private File filePath;
		private long fileSize;
		public File getFilePath() {
			return filePath;
		}
		public long getFileSize() {
			return fileSize;
		}
		public void setFilePath(String filePath) {
			this.filePath = new File(filePath);
			this.fileSize = this.filePath.length();
		}
	}

	public static boolean deletFile(String filePath) {
		return deletFile(new File(filePath));
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
				} else {
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

	public static void moveFiles(String srcDir, String dir) {
		LinkedList<String> files = new LinkedList<String>();
		File srcFile = new File(srcDir);
		File destFile = new File(dir);
		if (srcFile.exists()) {
			if (destFile.exists() || destFile.mkdirs()) {
				String srcFilePath = srcFile.getAbsolutePath();
				String destFilePath = destFile.getAbsolutePath();
				FileControls.getAllFiles(srcFilePath, files);
				int total = files.size();
				int copy = 0;
				int pre = (int) (((double) copy / total) * 1000);
				for (String file : files) {
					copy++;
					int p = (int) (((double) copy / total) * 1000);
					if (p - pre > 1 || copy == total) {
						System.out.println("移动:" + (double) p / 10 + "%");
						pre = p;
					}
					int index = file.indexOf(srcFilePath) + srcFilePath.length();
					String str = file.substring(index);
					String dest = destFilePath + str;
					if (!dest.equals(destFilePath)) {
						FileControls.moveFile(file, dest);
					} else {
						FileControls.deletFile(file);
					}
				}
			} else {
				System.out.println("目标文件\"" + destFile.getAbsolutePath() + "\"异常");
			}
		} else {
			System.out.println("源文件\"" + srcFile.getAbsolutePath() + "\"不存在");
		}
	}

	public static void getAllFiles(String srcPath, ArrayList<FileControls.Filesize> files) {
		File file = new File(srcPath);
		if (file.exists()) {
			File[] fileList = file.listFiles();
			if (fileList != null && fileList.length > 0) {
				for (File src : fileList) {
					getAllFiles(src.getAbsolutePath(), files);
				}
			}
			if (!file.isDirectory()) {
				FileControls.Filesize fileSize = new FileControls.Filesize();
				fileSize.setFilePath(file.getAbsolutePath());
				files.add(fileSize);
			}
			// files.add(file.getAbsolutePath());
		}
	}
}
