package com.yingu.match.common.util;

import java.io.File;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import com.alibaba.fastjson.JSON;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTPUtil {

	private static Session session = null;

	private static Channel channel = null;

	protected static Logger logger = LoggerFactory.getLogger(FTPUtil.class);

	/**
	 * 
	 * @Description: 上传本地文件到远程sftp服务器
	 * @param: localFilePath 本地文件路径 remoteFilePath远程文件路径
	 * @return:
	 * @throws
	 * @author: yaoyi
	 * @date: 2017年7月4日 下午4:41:21
	 */
	public static void upload(String localFilePath, String remoteFilePath, String userName, String password, String ip, Integer port) {
		try {
			ChannelSftp chSftp = getChannel(userName, password, ip, port);
			chSftp.cd("/");
			String remotePath = remoteFilePath.substring(0, remoteFilePath.lastIndexOf("/"));
			String[] folders = remotePath.split("/");
			for (String folder : folders) {
				if (folder.length() > 0) {
					try {
						chSftp.cd(folder);
					} catch (SftpException e) {
						chSftp.mkdir(folder);
						chSftp.cd(folder);
					}
				}
			}
			chSftp.put(localFilePath, remoteFilePath, ChannelSftp.OVERWRITE); // 以覆盖的方式上传文件
			logger.info("上传文件已完成");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * @Description: 判断服务器文件是否存在
	 * @param:
	 * @return:
	 * @throws
	 * @author:	liyufei
	 * @date:	2018/1/2 上午 11:42
	 */
	public static boolean isFileExists(String remoteFilePath, String userName, String password, String ip, Integer port) {
		boolean flag = false;
		try {
			logger.info("查询文件：" + remoteFilePath + " 是否存在...");
			ChannelSftp chSftp = getChannel(userName, password, ip, port);
			String remotePath = remoteFilePath.substring(0, remoteFilePath.lastIndexOf("/"));
			String remoteFileName = remoteFilePath.substring(remoteFilePath.lastIndexOf("/") + 1);
			Vector fileList = chSftp.ls(remotePath);
			logger.info("文件列表：" + JSON.toJSONString(fileList));
			Iterator it = fileList.iterator();
			while (it.hasNext()) {
				String fileName = ((ChannelSftp.LsEntry) it.next()).getFilename();
				if (".".equals(fileName) || "..".equals(fileName)) {
					continue;
				}
				logger.info("remoteFileName = " + remoteFileName + "; fileName = " + fileName);
				if (remoteFileName.equals(fileName)) {
					flag = true;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			close();
		}

		return flag;
	}

	/**
	 * 
	 * @Description:从远程sftp服务器下载文件到本地
	 * @param: remoteFilePath :远程文件路径，必须是文件 ；localFilePath ：本地存放文件的目录
	 * @return:
	 * @throws
	 * @author: yaoyi
	 * @date: 2017年7月4日 下午6:41:53
	 */
	public static void download(String remoteFilePath, String localFilePath, String userName, String password, String ip, Integer port) {
		try {
			File file = new File(localFilePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			ChannelSftp chSftp = getChannel(userName, password, ip, port);
			chSftp.get(remoteFilePath, localFilePath); // 默认采用OVERWRITE的方式进行下载
			logger.info("下载文件已完成");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			close();
		}
	}

	private static ChannelSftp getChannel(String userName, String password, String ip, Integer port) throws JSchException {
//		System.out.println(" username = " + userName +" ip = "+ip +" port = "+port +" password = "+ password);
		JSch jsch = new JSch();
		// 获取session对象
		session = jsch.getSession(userName, ip, port);
		logger.info("session 创建完成");
		if (StringUtils.isNotEmpty(password)) {
			session.setPassword(password);
		}
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config); // 为Session对象设置properties
		session.connect();
		logger.info("已建立session连接");

		channel = session.openChannel("sftp");
		channel.connect();
		logger.info("Connected successfully to ftpHost = " + ip + ",as ftpUserName = " + userName
				+ ", returning: " + channel);
		return (ChannelSftp) channel;
	}

	private static void close() {
		if (channel != null) {
			channel.disconnect();
		}
		if (session != null) {
			session.disconnect();
		}
	}

}
