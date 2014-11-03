package com.jstudy.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import jp.ne.so_net.ga2.no_ji.jcom.IDispatch;
import jp.ne.so_net.ga2.no_ji.jcom.ReleaseManager;
import jp.ne.so_net.ga2.no_ji.jcom.excel8.ExcelApplication;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建Pdf文档
 * 
 * @author xuxb
 * environment 环境 1：windows 2:linux  
 */

public class JComConvertor {
	
	private static final Logger log = LoggerFactory.getLogger(JComConvertor.class);
	
	/**
	 * pdf转swf工具
	 */
	private static final String SWFTools = "D:/Program Files/SWFTools/pdf2swf.exe";
	
	/**
     * JCom调用MS Office转换word为PDF
     * 
     * @param wordFile
     *            doc文档的绝对路径
     * @param pdfFile
     *            输出pdf文档的绝对路径，例如D:\\folder\\test.pdf
     */
    public static void word2pdf(String wordFile, String pdfFile) {
        ReleaseManager rm = null;
        IDispatch app = null;
        try {
            rm = new ReleaseManager();
            app = new IDispatch(rm, "Word.Application");// 启动word
            app.put("Visible", false); // 设置word不可见
            IDispatch docs = (IDispatch) app.get("Documents"); // 获得word中所有打开的文档
            IDispatch doc = (IDispatch) docs.method("Open", new Object[] {
                    wordFile, false, true });// 打开文档
            doc.method("SaveAs", new Object[] { pdfFile, 17 });// 转换文档为pdf格式
            doc.method("Close", new Object[] { false });
            app.method("Quit", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                app = null;
                rm.release();
                rm = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * JCom调用MS Office转换excel为HTML
     * 
     * @param excelFile
     *            源文件绝对路径
     * @param htmlFile
     *            目标文件绝对路径
     */
    public static void excel2html(String excelFile, String htmlFile) {
        ReleaseManager rm = null;
        IDispatch app = null;
        try {
            rm = new ReleaseManager();
            ExcelApplication ex = new ExcelApplication(rm);
            ex.put("Visible", false);
            IDispatch excs = (IDispatch) ex.get("Workbooks");
            IDispatch doc = (IDispatch) excs.method("Open", new Object[] {
                    excelFile, false, true });// 打开文档
            doc.method("SaveAs", new Object[] { htmlFile, 44 });
            doc.method("Close", new Object[] { false });
            ex.method("Quit", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                app = null;
                rm.release();
                rm = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * JCom调用MS Office转换Excel为PDF
     * 
     * @param excelFile
     *            源文件绝对路径
     * @param htmlFile
     *            目标文件绝对路径
     */
    public static void excel2pdf(String excelFile, String pdfFile) {
        ReleaseManager rm = null;
        IDispatch app = null;
        try {
            rm = new ReleaseManager();
            ExcelApplication ex = new ExcelApplication(rm);
            ex.put("Visible", false);
            IDispatch excs = (IDispatch) ex.get("Workbooks");
            IDispatch doc = (IDispatch) excs.method("Open", new Object[] {excelFile, false, true });// 打开文档
            doc.method("ExportAsFixedFormat", new Object[] { 0, pdfFile });
            doc.method("Close", new Object[] { false });
            ex.method("Quit", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                app = null;
                rm.release();
                rm = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * JCom调用MS Office转换Powerpoint为PDF
     * 
     * @param pptFile
     *            源文件绝对路径
     * @param pdfFile
     *            目标文件绝对路径
     */
    public static void ppt2pdf(String pptFile, String pdfFile) {
        ReleaseManager rm = null;
        IDispatch app = null;
        try {
            rm = new ReleaseManager();
            app = new IDispatch(rm, "PowerPoint.Application");// 启动word
            // app.put("Visible", false); // 设置word不可见
            IDispatch docs = (IDispatch) app.get("Presentations"); // 获得word中所有打开的文档
            IDispatch doc = (IDispatch) docs.method("Open", new Object[] {
                    pptFile, false, true });// 打开文档
            doc.method("SaveAs", new Object[] { pdfFile, 32 });// 转换文档为pdf格式
            // doc.method("Close", new Object[] { false });
            app.method("Quit", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                app = null;
                rm.release();
                rm = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * JCom调用MS Office转换Powerpoint为JPG
     * 
     * @param pptFile
     * @param pdfFile
     */
    public static void ppt2jpg(String pptFile, String jpgFile) {
        ReleaseManager rm = null;
        IDispatch app = null;
        try {
            rm = new ReleaseManager();
            app = new IDispatch(rm, "PowerPoint.Application");// 启动word
            // app.put("Visible", false); // 设置不可见
            IDispatch docs = (IDispatch) app.get("Presentations"); // 获得word中所有打开的文档
            IDispatch doc = (IDispatch) docs.method("Open", new Object[] {pptFile, false, true });// 打开文档
            doc.method("SaveAs", new Object[] { jpgFile, 17 });// 转换文档为pdf格式
            // doc.method("Close", new Object[] { false });
            app.method("Quit", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                app = null;
                rm.release();
                rm = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /** 
     * 转换成 swf 
     */  
     public static void pdf2swf(String pdfFile, String swfFile) {  
          final Runtime r = Runtime.getRuntime();  
	      if (StringUtils.isNotEmpty(pdfFile)) {
	    		   final String shell = SWFTools + " "+ pdfFile + " -o "+ swfFile + " -T 9";
	    		   new Thread(new Runnable() {
					@Override
					public void run() {
						try {  
							log.debug("process="+shell);
							Process process = r.exec(shell);
							BufferedReader br=new BufferedReader(new InputStreamReader(process.getInputStream()));   
				            String line=null;   
				            while((line=br.readLine())!=null){   
//				            	log.debug("pdf2swf="+line); 
				            }
						} catch (IOException e) {  
							e.printStackTrace();  
						}  
					}
	    		   }).start();
	    		   	
	        } else {  
	        	log.error("****pdf不存在,无法转换****");
	       } 
     }
     /**
      * 检测进程是否正在运行
      * @param processName 进程名称
      */
     public static boolean detectProcessRuning(String processName) {
    	 Runtime runtime = Runtime.getRuntime();
         Process process = null;
         try {
//        	 process = runtime.exec("tasklist");
             process = runtime.exec("tasklist /FI \"IMAGENAME eq "+processName+"\"");

             BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
             String s = "";
             while ((s = in.readLine()) != null) {
             	//看看有没有想要的进程
             	if (s.indexOf(processName) > -1) {
             		log.debug("进程="+s);
             		return true;
             	}
             }
         } catch(Exception e) {
         	
         }
         return false;
     }
     /**
      * 定时检测进程是否运行结束
      * @param processName 进程名称
      */
     public static void detectProcessFinished(final String processName){
    	 
    	 final BlockUtil block = new BlockUtil();
    	 
    	 Timer timer = new Timer();
    	 final Long millis = 1 * 1000L; //间隔秒
    	 timer.schedule(new TimerTask() {
    		 @Override
    		 public void run() {
    			 if (!detectProcessRuning(processName)) {
    				 cancel();
    			 }
 			}
			@Override
			public boolean cancel() {
				//解锁
				block.unlock();
				return super.cancel();
			}
    		 
    	 }, 0, millis);
    	 
    	 //上锁
    	 block.lock();
     }
     
	public static void main(String[] args) {
		log.debug("转换开始");
//		JComConvertor.word2pdf("D:/省内项目跟踪-需求规格说明书1121.doc", "D:/22.pdf");
//		JComConvertor.excel2pdf("D:/2014-08-15_01f364cc-6af7-4153-aa6b-b1c9285a5d2e.xls", "D:/33.pdf");
		JComConvertor.excel2html("D:/2014-08-15_01f364cc-6af7-4153-aa6b-b1c9285a5d2e.xls", "D:/33.html");
//		JComConvertor.ppt2pdf("D:/44.ppt", "D:/44.pdf");
		
//		JComConvertor.pdf2swf("D:/44.pdf", "D:/44.swf");
//		JComConvertor.pdf2swf("D:/55.pdf", "D:/55.swf");
//		JComConvertor.pdf2swf("D:/66.pdf", "D:/66.swf");
		
//		JComConvertor.pdf2swf("D:/77.pdf", "D:/77.swf");
//		JComConvertor.pdf2swf("D:/88.pdf", "D:/88.swf");
//		JComConvertor.pdf2swf("D:/99.pdf", "D:/99.swf");
		
//		log.debug("工具="+SWFTools.substring(SWFTools.lastIndexOf("/") + 1, SWFTools.length()));
//		JComConvertor.detectProcessFinished(SWFTools.substring(SWFTools.lastIndexOf("/") + 1, SWFTools.length()));
		
//		JComConvertor.detectProcessRuning("Maxthon.exe");
		log.debug("转换结束222222");
	}
}
