package com.yingu.match.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	 
	
	/**
	 * 将数据写入到Excel文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @param sheetName
	 *            工作表名称
	 * @param titles
	 *            工作表标题栏
	 * @param data_
	 *            工作表数据
	 * @throws FileNotFoundException
	 *             文件不存在异常
	 * @throws IOException
	 *             IO异常
	 */
	public String writeToFile(String filePath, String[] sheetName,
			List<List<String>> titles, List<List<List<String>>> data_)
			throws FileNotFoundException, IOException {
		// 创建并获取工作簿对象
		Workbook wb = getWorkBook(sheetName, titles, data_);
		// 写入到文件
		FileOutputStream out = new FileOutputStream(filePath);
		wb.write(out);
		out.close();
		return filePath;
	}
	
	public String writeToCSVFile(String filePath,
			List<List<String>> titles, List<List<List<String>>> data_)
			throws FileNotFoundException, IOException {
		// 创建并获取工作簿对象
		//Workbook wb = getWorkBook(sheetName, titles, data_);
		// 写入到文件
		 PrintWriter pw = new PrintWriter(new FileWriter(filePath));
		 StringBuffer sbTitle=new StringBuffer();
		 for (int i = 0; i < titles.get(0).size(); i++) {
			 if(i==titles.get(0).size()-1){
				 sbTitle.append(titles.get(0).get(i));
			 }else{
				 sbTitle.append(titles.get(0).get(i)).append(",");
			 }
		 }
		 pw.println(sbTitle);
		 for (int i = 0; i < data_.get(0).size(); i++) {
			 List<String> list=data_.get(0).get(i);
			 StringBuffer line=new StringBuffer();
			 for (int j = 0; j < list.size(); j++) {
				 if(j==list.size()-1){
					 line.append(list.get(j));
				 }else{
					 line.append(list.get(j)).append(",");
				 }
			 }
			 if(i!=data_.get(0).size()-1){
				 pw.println(line);
			 }else{
				 pw.print(line);
			 }
		}
		 
		pw.close();
		return filePath;
	}

	
	public static Map<String,Object> readCSVFile(String inputfile, Class clas) throws Exception {
		// 判断参数是否为空或没有意义
		if (inputfile == null || inputfile.trim().equals("")) {
			throw new IOException("NO input file!!!");
		}
		// 创建文件输入流
		InputStream is = new FileInputStream(inputfile);
		InputStreamReader  isr = new InputStreamReader(is,"utf-8");
		BufferedReader  br = new BufferedReader(isr);
		String str="";
		int i=0;
		List<List<String>> sss = new ArrayList<List<String>>();
		Map<String,Object> backMap=new HashMap<String, Object>();
		while ((str = br.readLine()) != null) {
		   if(i==0){
			   backMap.put("str", str);
			   i++;
			   continue;
		   }
		   i++;
		   String []strs=str.split(",");
		   List<String> strList=new ArrayList<String>(Arrays.asList(strs));
		   sss.add(strList);
		   
		}
		backMap.put("lis", toTranObj(sss, clas));
		return	backMap;

	}
	
	
	public static Object readFile(String inputfile, Class clas) throws Exception {
		// 判断参数是否为空或没有意义
		if (inputfile == null || inputfile.trim().equals("")) {
			throw new IOException("NO input file!!!");
		}
		// 设置开始行为0
		int currPosition = 1;
		// 创建文件输入流
		InputStream is = new FileInputStream(inputfile);

		// 如果是Excel文件则创建HSSFWorkbook读取
		XSSFWorkbook workbook = new XSSFWorkbook(is);
		// 读取第一个sheet文档（没有后续解析）
		XSSFSheet sheet = workbook.getSheetAt(0);
		// 判断当前行是否到但前Sheet的结尾
		List<List<String>> sss = new ArrayList<List<String>>();
		while (currPosition <= sheet.getLastRowNum()) {
			// 获取当前行数
			int row = currPosition;
			currPosition++;
			// 读取当前行数据
			sss.add(getLine(sheet, row));
		}
		return	toTranObj(sss, clas);

	}

	private static List<?> toTranObj(List<List<String>> sss, Class clazz)
			throws Exception {
		
		List<Object> objs = new ArrayList<Object>();
		// Object object = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();
		TreeMap<Integer, Method> m = new TreeMap<Integer, Method>();
		for (Field f : fields) {
			// String filedName = f.getName();
			// System.out.println(filedName);
			// 1、获取属性上的指定类型的注释
			String name = f.getName();
			if ("serialVersionUID".equals(name)) {
				continue;
			}
			String methodStr = "set" + name.toUpperCase().substring(0, 1)
					+ name.substring(1);
			Method method = clazz.getMethod(methodStr,
					new Class[] { f.getType() });
			Annotation annotation = f.getAnnotation(PropertyOrder.class);
			if (annotation == null)
				continue;
			Method[] me = annotation.annotationType().getDeclaredMethods();
			for (Method meth : me) {
				f.setAccessible(true);
				try {
					Integer order = (Integer) meth.invoke(annotation, null);
					m.put(order, method);
					// System.out.println(color+"------"+f.get(o));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		for (int i = 0; i < sss.size(); i++) {
			List<String> ss = sss.get(i);
			Object objj = clazz.newInstance();
			for (int j = 0; j < ss.size(); j++) {
				Method meth = m.get(j + 1);
			    Class<?>[] parameterTypes = meth.getParameterTypes();
                for (Class<?> clas : parameterTypes) {
                   String parameterName = clas.getName();
                   if(parameterName.contains("BigDecimal")){
                	   meth.invoke(objj, new BigDecimal(ss.get(j)));
                   }else{
                	   meth.invoke(objj, ss.get(j));
                   }
                }
				
			}
			objs.add(objj);
		}
		return objs;
	}

	private static List<String> getLine(XSSFSheet sheet, int row) {
		// 根据行数取得Sheet的一行
		XSSFRow rowline = sheet.getRow(row);
		// 获取当前行的列数
		int filledColumns = rowline.getLastCellNum();
		XSSFCell cell = null;
		List<String> cellStrs = new ArrayList<String>();
		// 循环遍历所有列
		for (int i = 0; i < filledColumns; i++) {
			// 取得当前Cell
			cell = rowline.getCell(i);
			String cellvalue = null;
			if (cell != null) {
				// 判断当前Cell的Type
				switch (cell.getCellType()) {
				// Cell的Type为NUMERIC
				// case HSSFCell.CELL_TYPE_NUMERIC: {
				// // 判断当前的cell是否为Date
				// if (HSSFDateUtil.isCellDateFormatted(cell)) {
				// // 如果是Date类型则，取得该Cell的Date值
				// Date date = cell.getDateCellValue();
				// // 把Date转换成本地格式的字符串
				// cellvalue = cell.getDateCellValue().toLocaleString();
				// } else { // 如果是纯数字
				// // 取得当前Cell的数值,强制转换为int
				// Integer num = new Integer(
				// (int) cell.getNumericCellValue());
				// cellvalue = String.valueOf(num);
				// }
				// break;
				// }
				// Cell的Type为STRING
				case HSSFCell.CELL_TYPE_STRING:
					// 取得当前的Cell字符串
					cellvalue = cell.getStringCellValue().replaceAll("'", "''");
					break;
				// 默认的Cell值
				default:
					cellvalue = " ";
				}
			} else {
				cellvalue = "";
			}
			cellStrs.add(cellvalue);
		}
		return cellStrs;
	}

	/**
	 * 创建工作簿对象<br>
	 * <font color="red">工作表名称，工作表标题，工作表数据最好能够对应起来</font><br>
	 * 比如三个不同或相同的工作表名称，三组不同或相同的工作表标题，三组不同或相同的工作表数据<br>
	 * <b> 注意：<br>
	 * 需要为每个工作表指定<font color="red">工作表名称，工作表标题，工作表数据</font><br>
	 * 如果工作表的数目大于工作表数据的集合，那么首先会根据顺序一一创建对应的工作表名称和数据集合，然后创建的工作表里面是没有数据的<br>
	 * 如果工作表的数目小于工作表数据的集合，那么多余的数据将不会写入工作表中 </b>
	 * 
	 * @param sheetName
	 *            工作表名称的数组
	 * @param titles
	 *            每个工作表名称的数组集合
	 * @param data_
	 *            每个工作表数据的集合的集合
	 * @return Workbook工作簿
	 * @throws FileNotFoundException
	 *             文件不存在异常
	 * @throws IOException
	 *             IO异常
	 */
	public Workbook getWorkBook(String[] sheetName, List<List<String>> titles,
			List<List<List<String>>> data_) throws FileNotFoundException,
			IOException {

		// 创建工作簿，支持2007及以后的文档格式
		Workbook wb = new XSSFWorkbook();
		// 创建一个工作表sheet
		Sheet sheet = null;
		// 申明行
		Row row = null;
		// 申明单元格
		Cell cell = null;
		// 单元格样式
		CellStyle titleStyle = wb.createCellStyle();
		CellStyle cellStyle = wb.createCellStyle();
		// 字体样式
		Font font = wb.createFont();
		// 粗体
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		titleStyle.setFont(font);
		// 水平居中
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 垂直居中
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		// 水平居中
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 垂直居中
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		// 标题数据
		List<String> title_temp = null;

		// 行数据
		List<String> rowData = null;

		// 工作表数据
		List<List<String>> sheetData = null;

		// 遍历sheet
		for (int sheetNumber = 0; sheetNumber < sheetName.length; sheetNumber++) {
			// 创建工作表
			sheet = wb.createSheet();
			// 设置工作表名称
			wb.setSheetName(sheetNumber, sheetName[sheetNumber]);
			// 设置标题
			title_temp = titles.get(sheetNumber);
			row = sheet.createRow(0);
			// 写入标题
			for (int i = 0; i < title_temp.size(); i++) {
				cell = row.createCell(i);
				cell.setCellStyle(titleStyle);
				cell.setCellValue(title_temp.get(i).toString());
			}
			try {
				sheetData = data_.get(sheetNumber);
			} catch (Exception e) {
				continue;
			}
			// 写入行数据
			for (int rowNumber = 0; rowNumber < sheetData.size(); rowNumber++) {
				// 如果没有标题栏，起始行就是0，如果有标题栏，行号就应该为1
				row = sheet.createRow(title_temp == null ? rowNumber
						: (rowNumber + 1));
				rowData = sheetData.get(rowNumber);
				for (int columnNumber = 0; columnNumber < rowData.size(); columnNumber++) {
					cell = row.createCell(columnNumber);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(rowData.get(columnNumber).toString());
				}
			}
		}
		return wb;
	}
	
	/**
	 * 
	 * @Description:打包下载Excel
	 * @param:
	 * @return:
	 * @throws 
	 * @author:	yumingjun
	 * @date:	2017-8-8 上午11:45:30
	 */
	public static void downToZipExcels(Object obj, List<List<String>> titles,
			String dest, String fileName,HttpServletResponse res) throws Exception{
		Long startTime = System.currentTimeMillis();
		List<Object>alls=(List<Object>) obj;
		int maxSize=2;
		int size=alls.size();
		int num=0;
		do{
			num++;
			size=size-maxSize;
		}while(size>0);
	    List<String>files=new ArrayList<String>();
		for (int i = 0; i < num-1; i++) {
			Object obj2=alls.subList(maxSize*i, maxSize*(i+1));
			List<List<List<String>>> data_ = dataToList(obj2, dest);
			// 写入到文件
			 String[] sheetName = {"数据列表"};
			 Workbook wb = new ExcelUtils().getWorkBook(sheetName, titles, data_);
			 String index= new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date());
			 String filePath=dest+index+ ".xls";
			 FileOutputStream out = new FileOutputStream(filePath);
			 wb.write(out);
			 out.close();
			 files.add(filePath);
		}
		if(maxSize*(num-1)<alls.size()){
			Object obj2=alls.subList(2*(num-1), alls.size());
			List<List<List<String>>> data_ = dataToList(obj2, dest);
			// 写入到文件
			 String[] sheetName = {"数据列表"};
			 Workbook wb = new ExcelUtils().getWorkBook(sheetName, titles, data_);
			 String index= new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date());
			 String filePath=dest+index+ ".xls";
			 FileOutputStream out = new FileOutputStream(filePath);
			 wb.write(out);
			 out.close();
			 files.add(filePath);
		}
		String index= new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date());
		res.setCharacterEncoding("utf-8");
		res.setContentType("application/octet-stream");  
		res.setHeader("Content-Disposition", "attachment;filename="+new String((fileName+"_"+index+".zip").getBytes(),"ISO8859-1"));  
		String zipFileName=dest+fileName+"_"+index+".zip";
		OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(zipFileName)));
		ZipOutputStream zos = new ZipOutputStream(os);
		for (String fileNames : files) {
			byte[] buf = new byte[8192];
	        int len;
		    File file = new File( fileNames);
            if ( !file.isFile() ) 
            	continue;
            ZipEntry ze = new ZipEntry( file.getName() );
            zos.putNextEntry( ze );
            BufferedInputStream bis = new BufferedInputStream( new FileInputStream( file ) );
            while ( ( len = bis.read( buf ) ) > 0 ) {
                zos.write( buf, 0, len );
            }
          bis.close();
          file.delete();
		}
		zos.close();
		
		File file = new File(zipFileName);
		FileInputStream fis = new java.io.FileInputStream(file);  
		byte[] buf2 = new byte[8*1024];
		ServletOutputStream ps = res.getOutputStream();
		int len2 = -1;
		while( (len2 = fis.read(buf2)) != -1){
			ps.write(buf2,0,len2);
		}
		ps.flush();
		ps.close();
		fis.close();
		file.delete();
		System.out.println("===================导出"+fileName+"结束,耗时："+(System.currentTimeMillis()-startTime)+"ms");
	}
	
	public static String objToWork(Object obj, List<List<String>> titles,
			String dest, String fileName) throws Exception, IOException {
		List<List<List<String>>> data_ = dataToList(obj, dest);
		return new ExcelUtils().writeToCSVFile(dest + fileName,  titles, data_);
		
	}

	private static List<List<List<String>>> dataToList(Object obj, String dest) {
		List<List<String>> data = new ArrayList<List<String>>();
		File destFile=new File(dest);
		if(!destFile.exists()||!destFile.isDirectory()){
			destFile.mkdirs();
		}
		for (Object o : (List<Object>) obj) {
			Field[] fields = o.getClass().getDeclaredFields();
			TreeMap<Integer, String> m = new TreeMap<Integer, String>();
			for (Field f : fields) {
				// String filedName = f.getName();
				// System.out.println(filedName);
				// 1、获取属性上的指定类型的注释
				Annotation annotation = f.getAnnotation(PropertyOrder.class);
				if (annotation == null)
					continue;
				Method[] me = annotation.annotationType().getDeclaredMethods();
				for (Method meth : me) {
					f.setAccessible(true);
					try {
						Integer order = (Integer) meth.invoke(annotation, null);
						if (f.get(o) == null) {
							continue;
						}else{
							
							m.put(order, f.get(o).toString());
						}
						// System.out.println(color+"------"+f.get(o));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
			List<String> data1 = new ArrayList<String>();
			for (Map.Entry<Integer, String> e : m.entrySet()) {
				data1.add(e.getValue());
			}
			data.add(data1);
		}

		List<List<List<String>>> data_ = new ArrayList<List<List<String>>>();
		data_.add(data);
		return data_;
	}

	public static void main(String[] args) throws Exception,
			IOException {
//		String filePath="E:/05/upload"+File.separator+DateUtil.date2Str(new Date(), "yyyyMMdd")+File.separator;;
//		File f=new File(filePath);
//		System.out.println(f.mkdirs());
		//readCSVFile("D:/BAG_20170525_000.csv", "");
		// List<String> title1=new ArrayList<String>();
		// title1.add("第一列a");
		// title1.add("第一列b");
		// title1.add("第一列c");
		//
		//
		//
		// List<List<String>> titles=new ArrayList<List<String>>();
		// titles.add(title1);
		//
		// String []data1={"i","j","k"};
		// String []data2={"m","n","o"};
		// String []data3={"x","y","z"};
		//
		// List<String[]> data=new ArrayList<String[]>();
		// data.add(data1);
		// data.add(data2);
		// data.add(data3);
		// List<List<String[]>> data_=new ArrayList<List<String[]>>();
		// data_.add(data);
		// String[] sheetName={"第一张表","第二张表","第三张表"};
		//
		// List<Object> objs=new ArrayList<Object>();
		// TestOrder t1=new TestOrder();
		// t1.setAge("12");
		// t1.setName("呵呵");
		// TestOrder t2=new TestOrder();
		// t2.setAge("13");
		// t2.setName("呵呵2");
		// objs.add(t1);
		// objs.add(t2);
		//
		// try {
		// new ExcelUtils().objToWork(objs, titles,"D:\\","123.csv");
		
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		
//		try {
//			// new ExcelUtils().readFile("D:\\BAG_20170704000", new Object());
//			Class<?> clazz = Class
//					.forName("com.yingu.match.common.util.ExcelUtils");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
