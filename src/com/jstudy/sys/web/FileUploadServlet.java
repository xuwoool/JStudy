package com.jstudy.sys.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

import com.jstudy.common.utils.JComConvertor;

public class FileUploadServlet extends HttpServlet {

	private static final Logger log = LoggerFactory.getLogger(FileUploadServlet.class);

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
			List<String> filePathList = new ArrayList<String>();
			Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
					processFormField(item);
				} else {
					filePathList.add(processUploadFile(item));
				}
			}// end while()
			String swfFile = null;
			if (!filePathList.isEmpty()) {
				for (String filePath : filePathList) {
					if (filePath != null) {
						Long fileName = new Date().getTime();
						String path = filePath.substring(0, filePath.lastIndexOf("\\"));
						String extension = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
						String pdfPath = path + "\\" + fileName + ".pdf";
						if ("doc".equals(extension) || "docx".equals(extension)) {
							JComConvertor.word2pdf(filePath, pdfPath);
						} else if ("ppt".equals(extension) || "pptx".equals(extension)) {
							JComConvertor.ppt2pdf(filePath, pdfPath);
						} else if ("xls".equals(extension) || "xlsx".equals(extension)) {
							JComConvertor.excel2html(filePath, path + "\\" + fileName + ".html");
							request.setAttribute("filename", this.getBashPath(request) + "/uploadfile/" + fileName + ".html");
							continue;
						}
						//成功转成pdf后，继续转swf文件
						swfFile = fileName+".swf";
						JComConvertor.pdf2swf(pdfPath, "E:/gitworkspace/JStudy/WebContent/swf/"+swfFile);
						//flexpaper展示swf文件
						request.setAttribute("flexpaper", "flexpaper");
						request.setAttribute("filename", "swf/"+swfFile);
					}
				}
				//检测文件是否转换成功
				JComConvertor.detectProcessFinished("pdf2swf.exe");
			}
			//跳转至预览页面
			request.getRequestDispatcher("preview.jsp").forward(request, response);
//			response.sendRedirect("preview.jsp");
			return;
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
	/**
	 * 获取工程的路径
	 * @param request
	 * @return
	 */
	private String getBashPath(HttpServletRequest request) {
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + request.getContextPath();
	}
}
