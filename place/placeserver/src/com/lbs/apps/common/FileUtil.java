package com.lbs.apps.common;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class FileUtil {
	private static final int BUFFER_SIZE = 20480;  //1024*20
	
	public static void CreateDir(String path) throws IOException {
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdir();
	}

	public static Boolean CheckFile(String path) throws IOException {
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			return true;
		} else {
			return false;
		}
	}

	public static Boolean CheckPath(String path) throws IOException {
		File file = new File(path);
		if (file.exists() && file.isDirectory()) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean DeleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}
	
	public static void copy(File src, File dst) throws Exception {

		InputStream in = null;
		OutputStream out = null;
		byte[] buffer =null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst),
					BUFFER_SIZE);
			buffer = new byte[BUFFER_SIZE];
			for (int byteRead = 0; (byteRead = in.read(buffer)) > 0;) {
				out.write(buffer, 0, byteRead);
			}
			
			if (null != in) {
				in.close();
				in = null;
			}
			if (null != out) {
				out.close();
				out = null;
			}
			
			buffer=null;
		} 
		catch (Exception e )
	    {
			if (null != in) {
				in.close();
				in = null;
			}
			if (null != out) {
				out.close();
				out = null;
			}
			
			buffer=null;
			throw e;
		}
	}
	
	/** 
     * Created on 2010-7-2  
     * <p>Discription:[isImage,判断文件是否为图片]</p> 
     * @param file 
     * @return true 是 | false 否 
     * @author:[shixing_11@sina.com] 
     */  
    public static final boolean isImage(File file){  
        boolean flag = false;  
        try  
        {  
            BufferedImage bufreader = ImageIO.read(file);  
            int width = bufreader.getWidth();  
            int height = bufreader.getHeight();  
            if(width==0 || height==0){  
                flag = false;  
            }else {  
                flag = true;  
            }  
        }  
        catch (IOException e)  
        {  
            flag = false;  
        }catch (Exception e) {  
            flag = false;  
        }  
        return flag;  
    }  
	
	/**  
     * Created on 2010-7-1   
     * <p>Discription:[getImageFileType,获取图片文件实际类型,若不是图片则返回null]</p>  
     * @param File  
     * @return fileType  
     * @author:[shixing_11@sina.com]  
     */    
    public final static String getImageFileType(File f)    
    {    
        if (isImage(f))  
        {  
            try  
            {  
                ImageInputStream iis = ImageIO.createImageInputStream(f);  
                Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);  
                if (!iter.hasNext())  
                {  
                    return null;  
                }  
                ImageReader reader = iter.next();  
                iis.close();  
                return reader.getFormatName();  
            }  
            catch (IOException e)  
            {  
                return null;  
            }  
            catch (Exception e)  
            {  
                return null;  
            }  
        }  
        return null;  
    }    

}
