/**
 * 
 */
package com.yingu.match.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @Description: tar.gz 压缩包工具类
 * @author: liyufei
 * @date: 2017年12月8日 下午2:31:11
 */
public class TarUtils {
	/**
	 * 打包目录下的所有文件
	 * 
	 * @param folderPath
	 *            ,要压缩的文件夹的路径 如：/root/lyf/
	 * @param zipFilePath
	 *            ,压缩后文件的全路径 如：/root/lyf/target.tar.gz
	 * */
	public static boolean CompressedFiles(String folderPath, String targzipFilePath) {
		File srcPath = new File(folderPath);
		int length = srcPath.listFiles().length;
		byte[] buf = new byte[1024]; // 设定读入缓冲区尺寸
		File[] files = srcPath.listFiles();
		boolean flag = false; // 标识是否打包成功
		// 建立压缩文件输出流
		FileOutputStream fout = null;
		// 建立tar压缩输出流
		TarArchiveOutputStream tout = null;
		try {
			// 建立压缩文件输出流
			fout = new FileOutputStream(targzipFilePath);
			// 建立tar压缩输出流
			tout = new TarArchiveOutputStream(fout);
			for (int i = 0; i < length; i++) {
				String filename = srcPath.getPath() + File.separator + files[i].getName();
				// 打开需压缩文件作为文件输入流
				FileInputStream fin = new FileInputStream(filename); // filename是文件全路径
				TarArchiveEntry tarEn = new TarArchiveEntry(files[i]); // 此处必须使用new
																		// TarEntry(File
																		// file);
				tarEn.setName(files[i].getName()); // 此处需重置名称，默认是带全路径的，否则打包后会带全路径
				tout.putArchiveEntry(tarEn);
				int num;
				while ((num = fin.read(buf, 0, 1024)) != -1) {
					tout.write(buf, 0, num);
				}
				tout.closeArchiveEntry();
				fin.close();
			}
			tout.close();
			fout.close();
			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			flag = false;
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			if (tout != null) {
				try {
					tout.close();
				} catch (IOException e) {
					throw new RuntimeException("TarArchiveOutputStream Close Failed");
				}
			}
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					throw new RuntimeException("FileOutputStream Close Failed");
				}
			}
		}
		return flag;
	}

	/** 
	 * @Description: 打包单个文件
	 * @param folderfilePath
	 *            ,要压缩的文件的路径 如：/root/lyf/target.txt
	 * @param targzipFilePath
	 *            ,压缩后文件的全路径 如：/root/lyf/target.tar.gz
	 * @return:
	 * @throws 
	 * @author:	liyufei
	 * @date:	2017年12月9日 上午10:26:51 
	 */
	public static boolean CompressedOneFile(String folderfilePath, String targzipFilePath) {
		File file = new File(folderfilePath);
		byte[] buf = new byte[1024]; // 设定读入缓冲区尺寸
		boolean flag = false; // 标识是否打包成功
		// 建立压缩文件输出流
		FileOutputStream fout = null;
		// 建立tar压缩输出流
		TarArchiveOutputStream tout = null;
		try {
			// 建立压缩文件输出流
			fout = new FileOutputStream(targzipFilePath);
			// 建立tar压缩输出流
			tout = new TarArchiveOutputStream(fout);
			// 打开需压缩文件作为文件输入流
			FileInputStream fin = new FileInputStream(folderfilePath); // filename是文件全路径
			TarArchiveEntry tarEn = new TarArchiveEntry(file); // 此处必须使用new
																			// TarEntry(File
																			// file);
			tarEn.setName(file.getName()); // 此处需重置名称，默认是带全路径的，否则打包后会带全路径
			tout.putArchiveEntry(tarEn);
			int num;
			while ((num = fin.read(buf, 0, 1024)) != -1) {
				tout.write(buf, 0, num);
			}
			tout.closeArchiveEntry();
			fin.close();
			tout.close();
			fout.close();
			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			flag = false;
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			if (tout != null) {
				try {
					tout.close();
				} catch (IOException e) {
					throw new RuntimeException("TarArchiveOutputStream Close Failed");
				}
			}
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					throw new RuntimeException("FileOutputStream Close Failed");
				}
			}
		}
		return flag;
	}

	public static final int BUFFER = 1024;
	public static final String EXT = ".gz";

	/**
	 * 数据压缩
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] compress(byte[] data) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// 压缩
		compress(bais, baos);

		byte[] output = baos.toByteArray();

		baos.flush();
		baos.close();

		bais.close();

		return output;
	}

	/**
	 * 文件压缩
	 *
	 * @param file
	 * @throws Exception
	 */
	public static void compress(File file) throws Exception {
		compress(file, true);
	}

	/**
	 * 文件压缩
	 *
	 * @param file
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void compress(File file, boolean delete) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file.getPath() + EXT);

		compress(fis, fos);

		fis.close();
		fos.flush();
		fos.close();

		if (delete) {
			file.delete();
		}
	}

	/**
	 * 数据压缩
	 *
	 * @param is
	 * @param os
	 * @throws Exception
	 */
	public static void compress(InputStream is, OutputStream os)
			throws Exception {

		GZIPOutputStream gos = new GZIPOutputStream(os);

		int count;
		byte data[] = new byte[BUFFER];
		while ((count = is.read(data, 0, BUFFER)) != -1) {
			gos.write(data, 0, count);
		}

		gos.finish();

		gos.flush();
		gos.close();
	}

	/**
	 * 文件压缩
	 *
	 * @param path
	 * @throws Exception
	 */
	public static void compress(String path) throws Exception {
		compress(path, true);
	}

	/**
	 * 文件压缩
	 *
	 * @param path
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void compress(String path, boolean delete) throws Exception {
		File file = new File(path);
		compress(file, delete);
	}

	/**
	 * 数据解压缩
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decompress(byte[] data) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// 解压缩

		decompress(bais, baos);

		data = baos.toByteArray();

		baos.flush();
		baos.close();

		bais.close();

		return data;
	}

	/**
	 * 文件解压缩
	 *
	 * @param file
	 * @throws Exception
	 */
	public static void decompress(File file) throws Exception {
		decompress(file, false);
	}

	/**
	 * 文件解压缩
	 *
	 * @param file
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void decompress(File file, boolean delete) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file.getPath().replaceAll(EXT,""));
		decompress(fis, fos);
		fis.close();
		fos.flush();
		fos.close();
		if (delete) {
			file.delete();
		}
	}

	/**
	 * 数据解压缩
	 *
	 * @param is
	 * @param os
	 * @throws Exception
	 */
	public static void decompress(InputStream is, OutputStream os)
			throws Exception {

		GZIPInputStream gis = new GZIPInputStream(is);

		int count;
		byte data[] = new byte[BUFFER];
		while ((count = gis.read(data, 0, BUFFER)) != -1) {
			os.write(data, 0, count);
		}
		gis.close();
	}

	/**
	 * 文件解压缩
	 *
	 * @param path
	 * @throws Exception
	 */
	public static void decompress(String path) throws Exception {
		System.out.println("解压文件："+path);
		decompress(path, false);
	}
	/**
	 * 文件解压缩
	 *
	 * @param path
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void decompress(String path, boolean delete) throws Exception {
		File file = new File(path);
		decompress(file, delete);
	}
}
