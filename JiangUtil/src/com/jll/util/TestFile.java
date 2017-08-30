package com.jll.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class TestFile {
	public static void main(String[] args) {
		String srcDir = "E:\\testsrc\\";
		String dir = "E:\\testdir\\";
		packageFile(srcDir, dir);
		upPackge(dir + FileControls.PACKAGE_TYPE);
	}

	@SuppressWarnings("unchecked")
	public static boolean upPackge(String packageFilePath) {
		File packageFile = new File(packageFilePath);
		FileInputStream fin = null;
		ObjectInputStream oin = null;
		if (packageFile.exists()) {
			try {
				fin = new FileInputStream(packageFile);
				oin = new ObjectInputStream(fin);
				ArrayList<FileControls.Filesize> list = new ArrayList<FileControls.Filesize>();
				list = (ArrayList<FileControls.Filesize>) oin.readObject();
				if (list.size() != 0) {
					for (int i = 0; i < list.size(); i++) {
						File file = list.get(i).getFilePath();
						if (!file.isDirectory()) {
							String dir = packageFile.getParent()+"\\" + file.getName();
							File destfile = new File(dir);
							destfile.getParentFile().mkdirs();
							FileOutputStream fout = new FileOutputStream(destfile);
							long filesize = list.get(i).getFileSize();
							byte[] buffer = new byte[4096];
							while (true && filesize > 0) {
								if (filesize > 4096) {
									fin.read(buffer);
									fout.write(buffer);
									filesize -= 4096;
								} else {
									fin.read(buffer, 0, (int) filesize);
									fout.write(buffer, 0, (int) filesize);
									break;
								}
							}
							fout.flush();
							fout.close();
						}else {
							file.mkdirs();
						}
					}
					oin.close();
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean packageFile(String srcDir, String dir) {
		File srcFile = new File(srcDir);
		File destFile = new File(dir + ".pakge");
		String srcFilePath = srcFile.getAbsolutePath();
		if (srcFile.exists()) {
				ArrayList<FileControls.Filesize> fileList = new ArrayList<FileControls.Filesize>();
				FileControls.getAllFiles(srcFilePath, fileList);
				try {
					ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(destFile));
					os.writeObject(fileList);
					os.flush();
					os.close();
					FileOutputStream fs = new FileOutputStream(destFile,true);
					for (FileControls.Filesize f : fileList) {
						if (!f.getFilePath().isDirectory()) {
							FileInputStream fi = new FileInputStream(f.getFilePath());
							byte[] buffer = new byte[4096];
							long size = f.getFileSize();
							while (true) {
								if (size > 4096) {
									fi.read(buffer);
									fs.write(buffer);
									size -= 4096;
								} else {
									fi.read(buffer, 0, (int) size);
									fs.write(buffer, 0, (int) size);
									fi.close();
									break;
								}
							}
						}
					}
					fs.flush();
					fs.close();
					return true;
				} catch (IOException e) {
					e.printStackTrace();
				}
		} else {
			System.out.println("源文件\"" + srcFile.getAbsolutePath() + "\"不存在");
		}
		return false;
	}
}
