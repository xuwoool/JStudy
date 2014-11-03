package com.jstudy.sys.web;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AjaxFileUploadServlet extends HttpServlet {

	private static final Logger log = LoggerFactory.getLogger(AjaxFileUploadServlet.class);

	private static final long serialVersionUID = 1L;

	private String filePath; // 文件存放目录
	private String tempPath; // 临时文件目录

	// 初始化
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		// 从配置文件中获得初始化参数
		filePath = config.getInitParameter("filepath");
		tempPath = config.getInitParameter("temppath");

		ServletContext context = getServletContext();

		filePath = context.getRealPath(filePath);
		tempPath = context.getRealPath(tempPath);

		// 如果路径不存在，则创建路径
		File pathFile = new File(filePath);
		File pathTemp = new File(tempPath);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		if (!pathTemp.exists()) {
			pathTemp.mkdirs();
		}
		System.out.println("文件存放目录、临时文件目录准备完毕 ...");
	}

	// doPost
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/plain;charset=gbk");
		try {
			DiskFileItemFactory diskFactory = new DiskFileItemFactory();
			// threshold 极限、临界值，即硬盘缓存 1G
			diskFactory.setSizeThreshold(1000 * 1024 * 1024);
			// repository 贮藏室，即临时文件目录
			diskFactory.setRepository(new File(tempPath));

			ServletFileUpload upload = new ServletFileUpload(diskFactory);
			// 设置允许上传的最大文件大小 1G
			upload.setSizeMax(1000 * 1024 * 1024);
			// 解析HTTP请求消息头
			List<FileItem> fileItems = upload.parseRequest(new ServletRequestContext(request));
			Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				processUploadFile(item);
			}
			//跳转至预览页面
			response.getWriter().write("{data:{loaded:50,total:100}}");
		} catch (Exception e) {
			System.out.println("使用 fileupload 包时发生异常 ...");
			e.printStackTrace();
		}
	}

	/**
	 * 处理表单内容
	 * @param item
	 * @throws Exception
	 */
	private void processFormField(FileItem item) throws Exception {
		String name = item.getFieldName();
		String value = item.getString();
	}

	/**
	 * 处理上传的文件
	 * @param item
	 * @throws Exception
	 */
	private String processUploadFile(FileItem item) throws Exception {
		// 此时的文件名包含了完整的路径，得注意加工一下
		String filename = item.getName();
		int index = filename.lastIndexOf("\\");
		filename = filename.substring(index + 1, filename.length());

		long fileSize = item.getSize();

		if ("".equals(filename) && fileSize == 0) {
			log.error("文件名为空");
			return null;
		}
		String fullFilePath = filePath + File.separator + filename;
		File uploadFile = new File(fullFilePath);
		if (!uploadFile.exists()) {
			uploadFile.createNewFile();
		}
		item.write(uploadFile);
		
		return fullFilePath;
	}

	// doGet
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		doPost(req, res);
	}
}
