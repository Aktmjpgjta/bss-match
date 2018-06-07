package com.yingu.match.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;

import com.yingu.match.common.util.DateUtil;

/**
 * 
 * @Description: zip压缩/解压工具
 * @author:	xianzhiqiang
 * @date:	2017年8月4日 上午11:00:15
 */
public class ZipUtil {


	private static String date;
	private static  Map<String, String> map = new HashMap<String, String>();
	
	static{
		date = DateUtil.date2Str(new Date(), DateUtil.PURE_FORMATE_DAY);
	}
	/**
	 * 
	 * @Description:
	 * @param: fileNames 文件名称 ; savePath: 要保存的路径: type:压缩的文件类型
	 * @return:
	 * @throws IOException 
	 * @throws 
	 * @author:	yaoyi
	 * @date:	2017年6月28日 上午10:38:43
	 */
	public static List<String> wirteZipFile(String[] fileNames, String savePath, String type) throws IOException{
		StringBuilder sb = new StringBuilder();
		List<String> ss=new ArrayList<String>();
		String name = type;	//需要生成的文件名称前缀
		
		String curDate = DateUtil.date2Str(new Date(), DateUtil.PURE_FORMATE_DAY);	//当前日期
		if(!date.equals(curDate)){
			date = curDate;
			map.clear();
		}
		File filePath=new File(savePath);
		if(!filePath.exists()&& !filePath .isDirectory()){
			filePath.mkdir();
		}
		String sequence = getSequence(date , type);
		
		name = sb.append(name).append("_").append(curDate).append("_").append(sequence).append(".zip").toString();
		String fileName=savePath+name;
		OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
		
		ZipOutputStream zos = new ZipOutputStream(os);
		
		byte[] buf = new byte[8192];
        int len;
        for (int i=0;i<fileNames.length;i++) {  
            File file = new File( fileNames[i]);
            if ( !file.isFile() ) 
            	continue;
            ZipEntry ze = new ZipEntry( file.getName() );
            zos.putNextEntry( ze );
            BufferedInputStream bis = new BufferedInputStream( new FileInputStream( file ) );
            while ( ( len = bis.read( buf ) ) > 0 ) {
                zos.write( buf, 0, len );
            }
            bis.close();
        }
        
        zos.closeEntry();
        zos.close();
        os.close();
        ss.add(fileName);
        ss.add(name);
        return ss;
	}
	
	/** 
     * 解压到指定目录 
     * @param zipPath E:/文件/账户存管相关文档/测试数据文件/BAGR_20170525_000.zip
     * @param descDir E:/BAGR_20170525_000/文件.csv
     * 解压后结果为E:/
     * @author ymj 
     */  
    public static String unZipFiles(String zipPath,String descDir)throws IOException{  
       return  unZipFiles(new File(zipPath), descDir);  
    } 
    /** 
     * 解压到相对当前目录下，同时增加一级zip文件名称的目录 
     * @param zipPath  E:/文件/账户存管相关文档/测试数据文件/BAGR_20170525_000.zip
     * 		解压后为 E:/文件/账户存管相关文档/测试数据文件/BAGR_20170525_000/文件.csv
     * @author ymj 
     */  
    public static String unZipFiles(String zipPath)throws IOException{  
    	File f=new File(zipPath);
       return  unZipFiles(f, f.getParent());  
    } 
    /** 
     * 解压文件到指定目录 
     * @param zipFile 
     * @param descDir 
     * @author isea533 
     */  
    @SuppressWarnings("rawtypes")  
    public static String unZipFiles(File zipFile,String descDir)throws IOException{  
        File pathFile = new File(descDir);  
        if(!pathFile.exists()){  
            pathFile.mkdirs();  
        }  
        ZipFile zip = new ZipFile(zipFile); 
        //读取文件名称
        String fileName=zipFile.getName();
        String fileNamePath=fileName.substring(0, fileName.lastIndexOf("."));
        descDir=descDir+File.separator+fileNamePath+File.separator;
        File path=new File(descDir);
        if(!path.exists()||!path.isDirectory()){
        	path.mkdir();
        }
        for(Enumeration entries = zip.entries();entries.hasMoreElements();){  
            ZipEntry entry = (ZipEntry)entries.nextElement();  
            String zipEntryName = entry.getName();  
            InputStream in = zip.getInputStream(entry);  
            String outPath = (descDir+zipEntryName).replaceAll("\\*", "/");;  
//            //判断路径是否存在,不存在则创建文件路径  
//            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));  
//            if(!file.exists()){  
//                file.mkdirs();  
//            }  
//            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压  
//            if(new File(outPath).isDirectory()){  
//                continue;  
//            }  
            //输出文件路径信息  
            //System.out.println(outPath);  
              
            OutputStream out = new FileOutputStream(outPath);  
            byte[] buf1 = new byte[1024];  
            int len;  
            while((len=in.read(buf1))>0){  
                out.write(buf1,0,len);  
            }  
            in.close();  
            out.close();  
            }  
       /// System.out.println("******************解压完毕********************"); 
        return descDir;
    } 
	
	/**
	 * 生成序列
	 * @Description:
	 * @param: date：先前时间  curDate：当前时间
	 * @return:
	 * @throws 
	 * @author:	yaoyi
	 * @date:	2017年6月28日 下午12:28:33
	 */
	public static synchronized String getSequence(String date, String type){
		
		StringBuilder sb = new StringBuilder();
		String key = date+"_"+type;
		String value = map.get(key);
		
		if(StringUtils.isEmpty(value)){	//当前key对应的值为空，则设初始值
			map.put(key, "0001");
		}else{	//进行序号值得累加
			int index = -1;
			for(int i=0; i< value.length(); i++){	//找第一个非0字符的位置
				if('0'!=value.charAt(i)){
					index = i;
					break;
				}
			}
			if(index == 0){	//达到最大上限值
				map.put(key, "0001");
			}else{
				String subStr = value.substring(0, index);
				String val = value.substring(index , value.length());
				sb.append(subStr).append(Integer.parseInt(val)+1);
				map.put(key, sb.toString());
			}
		}
		return map.get(key);
	}
	
	
	public static void main(String[] args) throws Exception {

	}

	
}
