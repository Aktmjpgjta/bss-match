package com.yingu.match.util;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelExpUtils {

	private static Logger log = Logger.getLogger(ExcelExpUtils.class);
	private static final int EXP_START = 0;// 起始条数
	private static final String EXP_ENCODING_UTF8 = "utf-8";
	private static final String EXP_ENCODING_ISO8 = "ISO8859-1";

	/**
	 * 
	 * @Description: 文件导出
	 * @param:String []title 列名
	 * @param:values 需要导出的数据
	 * @param:String fileName 文件名称
	 * @param:int limit 每份文件中的条数
	 * @param:HttpServletResponse response 响应
	 * @throws Exception
	 * @author: xianzhiqiang
	 * @date: 2017年8月4日 上午9:54:37
	 */
	public static void exportFile(HttpServletRequest request, String[] title, String[][] values, String fileName,
			int limit, HttpServletResponse response) throws Exception {
		log.info("===================开始执行导出" + fileName);
		// 获取tomcat根目录
		String projectPath = request.getSession().getServletContext().getRealPath("/");
		String rootPath;
		if(projectPath.contains("webapps")){
			rootPath = projectPath.substring(0, projectPath.lastIndexOf("webapps"));
		}else{
			rootPath = projectPath;
		}

		log.debug("===================rootPath:" + rootPath);
		Long startTime = System.currentTimeMillis();
		List<String> csvList = new ArrayList<String>();
		// 放入临时数据集合起始条数
		int start = EXP_START;
		// 需要导出数据总条数
		int length = values.length;
		List<String> fileZips = new ArrayList<String>();
		while (true) {
			if (values != null && length > 0 && start != length) {
				// 临时数据集合,用于存放自定义条数数据
				String[][] temp = null;
				// 需导出总条数小于每份文件中的条数上限,则设置临时数据集合大小为需导出总条数
				if (length < limit) {
					temp = new String[length][];
				} else {
					if (start > length || (length - start) < limit) {
						temp = new String[length - start][];
					} else {
						temp = new String[limit][];
					}
				}
				// 向临时数据集合中放入数据
				for (int i = 0; i < length; i++) {
					// 当达到需要导出的数据条数后停止往临时数据集合中放入数据
					if (i == limit) {
						break;
					}
					if (start > length) {
						if (i == temp.length) {
							break;
						}
						temp[i] = values[start - limit + i];

					} else if ((length - start) < limit) {

						if (i == temp.length) {
							break;
						}
						temp[i] = values[start + i];
					} else {
						temp[i] = values[start + i];
					}

				}

				String index = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date());
				String filePath = rootPath + File.separator + fileName + index + ".xls"; // 文件路径
				// 第一步，创建一个webbook，对应一个Excel文件
				HSSFWorkbook wb = new HSSFWorkbook();
				// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
				HSSFSheet sheet = wb.createSheet("sheet1");
				// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
				HSSFRow row = sheet.createRow(0);
				// 第四步，创建单元格，并设置值表头 设置表头居中
				HSSFCellStyle style = wb.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

				HSSFCell cell = null;
				// 创建标题
				for (int i = 0; i < title.length; i++) {
					cell = row.createCell(i);
					cell.setCellValue(title[i]);
					cell.setCellStyle(style);
				}
				// 创建内容
				for (int i = 0; i < temp.length; i++) {
					row = sheet.createRow(i + 1);
					for (int j = 0; j < temp[i].length; j++) {
						row.createCell(j).setCellValue(temp[i][j]);
					}
				}

				FileOutputStream fout = new FileOutputStream(filePath);
				wb.write(fout);
				fout.flush();
				fout.close();

				csvList.add(filePath);
				if (temp.length == limit) {
					start += limit;
				} else {
					break;
				}
			} else {
				if (values == null || length == 0 ){
					String index = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date());
					String filePath = rootPath + File.separator + fileName + index + ".xls"; // 文件路径
					// 第一步，创建一个webbook，对应一个Excel文件
					HSSFWorkbook wb = new HSSFWorkbook();
					// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
					HSSFSheet sheet = wb.createSheet("sheet1");
					// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
					HSSFRow row = sheet.createRow(0);
					// 第四步，创建单元格，并设置值表头 设置表头居中
					HSSFCellStyle style = wb.createCellStyle();
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

					HSSFCell cell = null;
					// 创建标题
					for (int i = 0; i < title.length; i++) {
						cell = row.createCell(i);
						cell.setCellValue(title[i]);
						cell.setCellStyle(style);
					}

					FileOutputStream fout = new FileOutputStream(filePath);
					wb.write(fout);
					fout.flush();
					fout.close();

					csvList.add(filePath);
				}
				break;
			}
		}

		if (csvList != null && csvList.size() > 0) {
			fileZips = ZipUtil.wirteZipFile(csvList.toArray(new String[0]), rootPath, fileName);
			for (String filePath : csvList) {
				File f = new File(filePath);
				if (f.exists())
					f.delete();
			}
		} else {
			log.info("===================导出" + fileName + "没有符合条件的数据");
		}

		response.setCharacterEncoding(EXP_ENCODING_UTF8);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileZips.get(1).getBytes(), EXP_ENCODING_ISO8));
		File file = new java.io.File(fileZips.get(0));
		FileInputStream fis = new java.io.FileInputStream(file);
		byte[] buf = new byte[8 * 1024];
		ServletOutputStream ps = response.getOutputStream();
		int len = -1;
		while ((len = fis.read(buf)) != -1) {
			ps.write(buf, 0, len);
		}
		ps.close();
		fis.close();

		file.delete();
		log.info("===================导出" + fileName + "结束,耗时：" + (System.currentTimeMillis() - startTime) + "ms");

	}
}
