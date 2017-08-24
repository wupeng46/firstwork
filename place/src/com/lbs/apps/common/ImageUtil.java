package com.lbs.apps.common;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.lbs.commons.log.LogHelper;


/**
 * * @author WQ * @date 2011-01-14 * @versions 1.0 图片压缩工具类 提供的方法中可以设定生成的
 * 缩略图片的大小尺寸等
 */
public class ImageUtil {
	LogHelper log = new LogHelper(this.getClass());

	
	/** * 图片文件读取 * * @param srcImgPath * @return */
	private static BufferedImage InputImage(String srcImgPath) {
		BufferedImage srcImage = null;
		try {
			FileInputStream in = new FileInputStream(srcImgPath);
			srcImage = javax.imageio.ImageIO.read(in);
		} catch (IOException e) {
			System.out.println("读取图片文件出错！" + e.getMessage());
			e.printStackTrace();
		}
		return srcImage;
	}

	/**
	 * * 将图片按照指定的图片尺寸压缩 * * @param srcImgPath :源图片路径 * @param outImgPath *
	 * :输出的压缩图片的路径 * @param new_w * :压缩后的图片宽 * @param new_h * :压缩后的图片高
	 */
	public static void compressImage(String srcImgPath, String outImgPath,
			int new_w, int new_h) {
		BufferedImage src = InputImage(srcImgPath);
		disposeImage(src, outImgPath, new_w, new_h);
	}

	/**
	 * * 将图片按照指定的图片尺寸压缩 * * @param srcImgPath :源图片路径 * @param outImgPath *
	 * :输出的压缩图片的路径 * @param new_w * :压缩后的图片宽 * @param new_h * :压缩后的图片高
	 * @throws IOException 
	 */
	public static void compressImage2(String srcImgPath, String outImgPath,
			int new_w, int new_h) {
		BufferedImage src = InputImage(srcImgPath);
		if (null != src) {
			int old_w = src.getWidth();
			// 得到源图宽
			int old_h = src.getHeight();

			double srcScale = (double) old_h / old_w;

			// 得到源图长
			int new_w_t = 0;
			// 新图的宽
			int new_h_t = 0;
			// 新图的长
			// 根据图片尺寸压缩比得到新图的尺寸

			if ((old_w >= new_w) && (old_h >= new_h)) {
				 if (new_w < new_h){
					 new_w_t = new_w;
					 new_h_t = (int) (new_w * srcScale);
				 }
				 else
				 {
					
					 new_h_t =  new_h;
					 new_w_t = (int) (new_h / srcScale);
				 }
			} else {
				int maxLength = 0;
				if (new_w > new_h) {
					maxLength = new_w;
				} else {
					maxLength = new_h;
				}

				if (old_w > old_h) {
					// 图片要缩放的比例
					new_w_t = maxLength;
					new_h_t = (int) Math.round(old_h
							* ((float) maxLength / old_w));
				} else {
					new_w_t = (int) Math.round(old_w
							* ((float) maxLength / old_h));
					new_h_t = maxLength;
				}
			}
			String ls_temp=outImgPath.replace(".", "_temp.");
			disposeImage(src, ls_temp, new_w_t, new_h_t);
			
			try {
				cutImage(new File(ls_temp),outImgPath,new_w, new_h);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

	}

	/**
	 * * 指定长或者宽的最大值来压缩图片 * * @param srcImgPath * :源图片路径 * @param outImgPath *
	 * :输出的压缩图片的路径 * @param maxLength * :长或者宽的最大值
	 */
	public static void compressImage(String srcImgPath, String outImgPath,
			int maxLength) {
		// 得到图片
		BufferedImage src = InputImage(srcImgPath);
		if (null != src) {
			int old_w = src.getWidth();
			// 得到源图宽
			int old_h = src.getHeight();
			// 得到源图长
			int new_w = 0;
			// 新图的宽
			int new_h = 0;
			// 新图的长
			// 根据图片尺寸压缩比得到新图的尺寸
			if (old_w > old_h) {
				// 图片要缩放的比例
				new_w = maxLength;
				new_h = (int) Math.round(old_h * ((float) maxLength / old_w));
			} else {
				new_w = (int) Math.round(old_w * ((float) maxLength / old_h));
				new_h = maxLength;
			}
			disposeImage(src, outImgPath, new_w, new_h);
		}
	}

	/** * 处理图片 * * @param src * @param outImgPath * @param new_w * @param new_h */
	private synchronized static void disposeImage(BufferedImage src,
			String outImgPath, int new_w, int new_h) {
		// 得到图片
		int old_w = src.getWidth();
		// 得到源图宽
		int old_h = src.getHeight();
		// 得到源图长
		BufferedImage newImg = null;
		// 判断输入图片的类型
		switch (src.getType()) {
		case 13:
			// png,gif
			newImg = new BufferedImage(new_w, new_h,
					BufferedImage.TYPE_4BYTE_ABGR);
			break;
		default:
			newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
			break;
		}
		Graphics2D g = newImg.createGraphics();
		// 从原图上取颜色绘制新图
		g.drawImage(src, 0, 0, old_w, old_h, null);
		g.dispose();
		// 根据图片尺寸压缩比得到新图的尺寸
		newImg.getGraphics().drawImage(
				src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0,
				null);
		// 调用方法输出图片文件
		OutImage(outImgPath, newImg);
	}

	/**
	 * * 将图片文件输出到指定的路径，并可设定压缩质量 * * @param outImgPath * @param newImg * @param
	 * per
	 */
	private static void OutImage(String outImgPath, BufferedImage newImg) {
		// 判断输出的文件夹路径是否存在，不存在则创建
		File file = new File(outImgPath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}// 输出到文件流
		try {
			ImageIO.write(newImg,
					outImgPath.substring(outImgPath.lastIndexOf(".") + 1),
					new File(outImgPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Title: cutImage
	 * </p>
	 * <p>
	 * Description: 根据原图与裁切size截取局部图片
	 * </p>
	 * 
	 * @param srcImg
	 *            源图片
	 * @param output
	 *            图片输出流
	 * @param rect
	 *            需要截取部分的坐标和大小
	 */
	public void cutImage(File srcImg, OutputStream output,
			java.awt.Rectangle rect) {
		if (srcImg.exists()) {
			java.io.FileInputStream fis = null;
			ImageInputStream iis = null;
			try {
				fis = new FileInputStream(srcImg);
				// ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG,
				// JPEG, WBMP, GIF, gif]
				String types = Arrays.toString(ImageIO.getReaderFormatNames())
						.replace("]", ",");
				String suffix = null;
				// 获取图片后缀
				if (srcImg.getName().indexOf(".") > -1) {
					suffix = srcImg.getName().substring(
							srcImg.getName().lastIndexOf(".") + 1);
				}// 类型和图片后缀全部小写，然后判断后缀是否合法
				if (suffix == null
						|| types.toLowerCase().indexOf(
								suffix.toLowerCase() + ",") < 0) {
					log.error("Sorry, the image suffix is illegal. the standard image suffix is {"+ types + "}.");
					return;
				}
				// 将FileInputStream 转换为ImageInputStream
				iis = ImageIO.createImageInputStream(fis);
				// 根据图片类型获取该种类型的ImageReader
				ImageReader reader = ImageIO.getImageReadersBySuffix(suffix)
						.next();
				reader.setInput(iis, true);
				ImageReadParam param = reader.getDefaultReadParam();
				param.setSourceRegion(rect);
				BufferedImage bi = reader.read(0, param);
				ImageIO.write(bi, suffix, output);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fis != null)
						fis.close();
					if (iis != null)
						iis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			log.warn("the src image is not exist.");
		}
	}

	public void cutImage(File srcImg, OutputStream output, int x, int y,
			int width, int height) {
		cutImage(srcImg, output, new java.awt.Rectangle(x, y, width, height));
	}

	/**
	 * 截取一个图像的中央区域
	 * 
	 * @param image
	 *            图像File
	 * @param w
	 *            需要截图的宽度
	 * @param h
	 *            需要截图的高度
	 * @return 返回一个
	 * @throws IOException
	 */
	public static void cutImage(File image, String outImgPath, int w, int h)
			throws IOException {
		InputStream inputStream = new FileInputStream(image);
		// 用ImageIO读取字节流
		BufferedImage bufferedImage = ImageIO.read(inputStream);
		BufferedImage distin = null;
		// 返回源图片的宽度。
		int srcW = bufferedImage.getWidth();
		// 返回源图片的高度。
		int srcH = bufferedImage.getHeight();
		int x = 0, y = 0;
		// 使截图区域居中
		x = srcW / 2 - w / 2;
		y = srcH / 2 - h / 2;
		srcW = srcW / 2 + w / 2;
		srcH = srcH / 2 + h / 2;
		// 生成图片
		distin = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = distin.getGraphics();
		g.drawImage(bufferedImage, 0, 0, w, h, x, y, srcW, srcH, null);
		// 调用方法输出图片文件
		OutImage(outImgPath, distin);
	}
}
// import java.awt.Color;
// import java.awt.Graphics;
// import java.awt.Image;
// import java.awt.image.BufferedImage;
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileNotFoundException;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.io.OutputStream;
// import java.util.Arrays;
//
// import javax.imageio.ImageIO;
// import javax.imageio.ImageReadParam;
// import javax.imageio.ImageReader;
// import javax.imageio.stream.ImageInputStream;
//
// import com.lbs.commons.log.LogHelper;
//
//
// /**
// * <p>Title: ImageUtil </p>
// * <p>Description: </p>
// * <p>Email: icerainsoft@hotmail.com </p>
// * @author Ares
// * @date 2014年10月28日 上午10:24:26
// */
// public class ImageUtil {
//
// LogHelper log = new LogHelper(this.getClass());
//
// private static String DEFAULT_THUMB_PREVFIX = "thumb_";
// private static String DEFAULT_CUT_PREVFIX = "cut_";
// private static Boolean DEFAULT_FORCE = false;
//
//
// /**
// * <p>Title: cutImage</p>
// * <p>Description: 根据原图与裁切size截取局部图片</p>
// * @param srcImg 源图片
// * @param output 图片输出流
// * @param rect 需要截取部分的坐标和大小
// */
// public void cutImage(File srcImg, OutputStream output, java.awt.Rectangle
// rect){
// if(srcImg.exists()){
// java.io.FileInputStream fis = null;
// ImageInputStream iis = null;
// try {
// fis = new FileInputStream(srcImg);
// // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP,
// GIF, gif]
// String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]",
// ",");
// String suffix = null;
// // 获取图片后缀
// if(srcImg.getName().indexOf(".") > -1) {
// suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
// }// 类型和图片后缀全部小写，然后判断后缀是否合法
// if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()+",") <
// 0){
// log.error("Sorry, the image suffix is illegal. the standard image suffix is {}."
// + types);
// return ;
// }
// // 将FileInputStream 转换为ImageInputStream
// iis = ImageIO.createImageInputStream(fis);
// // 根据图片类型获取该种类型的ImageReader
// ImageReader reader = ImageIO.getImageReadersBySuffix(suffix).next();
// reader.setInput(iis,true);
// ImageReadParam param = reader.getDefaultReadParam();
// param.setSourceRegion(rect);
// BufferedImage bi = reader.read(0, param);
// ImageIO.write(bi, suffix, output);
// } catch (FileNotFoundException e) {
// e.printStackTrace();
// } catch (IOException e) {
// e.printStackTrace();
// } finally {
// try {
// if(fis != null) fis.close();
// if(iis != null) iis.close();
// } catch (IOException e) {
// e.printStackTrace();
// }
// }
// }else {
// log.warn("the src image is not exist.");
// }
// }
//
// public void cutImage(File srcImg, OutputStream output, int x, int y, int
// width, int height){
// cutImage(srcImg, output, new java.awt.Rectangle(x, y, width, height));
// }
//
// public void cutImage(File srcImg, String destImgPath, java.awt.Rectangle
// rect){
// File destImg = new File(destImgPath);
// if(destImg.exists()){
// String p = destImg.getPath();
// try {
// if(!destImg.isDirectory()) p = destImg.getParent();
// if(!p.endsWith(File.separator)) p = p + File.separator;
// cutImage(srcImg, new java.io.FileOutputStream(p + DEFAULT_CUT_PREVFIX + "_" +
// new java.util.Date().getTime() + "_" + srcImg.getName()), rect);
// } catch (FileNotFoundException e) {
// log.warn("the dest image is not exist.");
// }
// }else log.warn("the dest image folder is not exist.");
// }
//
// public void cutImage(File srcImg, String destImg, int x, int y, int width,
// int height){
// cutImage(srcImg, destImg, new java.awt.Rectangle(x, y, width, height));
// }
//
// public void cutImage(String srcImg, String destImg, int x, int y, int width,
// int height){
// cutImage(new File(srcImg), destImg, new java.awt.Rectangle(x, y, width,
// height));
// }
// /**
// * <p>Title: thumbnailImage</p>
// * <p>Description: 根据图片路径生成缩略图 </p>
// * @param imagePath 原图片路径
// * @param w 缩略图宽
// * @param h 缩略图高
// * @param prevfix 生成缩略图的前缀
// * @param force 是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
// */
// public void thumbnailImage(File srcImg, OutputStream output, int w, int h,
// String prevfix, boolean force){
// if(srcImg.exists()){
// try {
// // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP,
// GIF, gif]
// String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]",
// ",");
// String suffix = null;
// // 获取图片后缀
// if(srcImg.getName().indexOf(".") > -1) {
// suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
// }// 类型和图片后缀全部小写，然后判断后缀是否合法
// if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()+",") <
// 0){
// log.error("Sorry, the image suffix is illegal. the standard image suffix is {}."
// + types);
// return ;
// }
// log.debug("target image's size, width:{"+w+"}, height:{"+h+"}.");
// Image img = ImageIO.read(srcImg);
// // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
// if(!force){
// int width = img.getWidth(null);
// int height = img.getHeight(null);
// if((width*1.0)/w < (height*1.0)/h){
// if(width > w){
// h = Integer.parseInt(new java.text.DecimalFormat("0").format(height *
// w/(width*1.0)));
// log.debug("target image's height, width:{"+w+"}, height:{"+h+"}.");
// }
// } else {
// if(height > h){
// w = Integer.parseInt(new java.text.DecimalFormat("0").format(width *
// h/(height*1.0)));
// log.debug("target image's width, width:{"+w+"}, height:{"+h+"}.");
// }
// }
// }
// BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
// Graphics g = bi.getGraphics();
// g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
// g.dispose();
// // 将图片保存在原目录并加上前缀
// ImageIO.write(bi, suffix, output);
// output.close();
// } catch (IOException e) {
// log.error("generate thumbnail image failed." + e.getMessage());
// }
// }else{
// log.warn("the src image is not exist.");
// }
// }
// public void thumbnailImage(File srcImg, int w, int h, String prevfix, boolean
// force){
// String p = srcImg.getAbsolutePath();
// try {
// if(!srcImg.isDirectory()) p = srcImg.getParent();
// if(!p.endsWith(File.separator)) p = p + File.separator;
// thumbnailImage(srcImg, new java.io.FileOutputStream(p + prevfix
// +srcImg.getName()), w, h, prevfix, force);
// } catch (FileNotFoundException e) {
// log.error("the dest image is not exist."+ e.getMessage());
// }
// }
//
// public void thumbnailImage(String imagePath, int w, int h, String prevfix,
// boolean force){
// File srcImg = new File(imagePath);
// thumbnailImage(srcImg, w, h, prevfix, force);
// }
//
// public void thumbnailImage(String imagePath, int w, int h, boolean force){
// thumbnailImage(imagePath, w, h, DEFAULT_THUMB_PREVFIX, DEFAULT_FORCE);
// }
//
// public void thumbnailImage(String imagePath, int w, int h){
// thumbnailImage(imagePath, w, h, DEFAULT_FORCE);
// }
//
// public static void main(String[] args) {
// new ImageUtil().thumbnailImage("imgs/Tulips.jpg", 150, 100);
// new ImageUtil().cutImage("imgs/Tulips.jpg","imgs", 250, 70, 300, 400);
// }
//
// /**
// * 等比例压缩算法：
// * 算法思想：根据压缩基数和压缩比来压缩原图，生产一张图片效果最接近原图的缩略图
// * @param srcURL 原图地址
// * @param deskURL 缩略图地址
// * @param comBase 压缩基数
// * @param scale 压缩限制(宽/高)比例 一般用1：
// *
// 当scale>=1,缩略图height=comBase,width按原图宽高比例;若scale<1,缩略图width=comBase,height按原图宽高比例
// * @throws Exception
// * @author shenbin
// * @createTime 2014-12-16
// * @lastModifyTime 2014-12-16
// */
// public static void saveMinPhoto(String srcURL, String deskURL, double
// comBase,
// double scale) throws Exception {
// File srcFile = new java.io.File(srcURL);
// Image src = ImageIO.read(srcFile);
// int srcHeight = src.getHeight(null);
// int srcWidth = src.getWidth(null);
// int deskHeight = 0;// 缩略图高
// int deskWidth = 0;// 缩略图宽
// double srcScale = (double) srcHeight / srcWidth;
// /**缩略图宽高算法*/
// if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
// if (srcScale >= scale || 1 / srcScale > scale) {
// if (srcScale >= scale) {
// deskHeight = (int) comBase;
// deskWidth = srcWidth * deskHeight / srcHeight;
// } else {
// deskWidth = (int) comBase;
// deskHeight = srcHeight * deskWidth / srcWidth;
// }
// } else {
// if ((double) srcHeight > comBase) {
// deskHeight = (int) comBase;
// deskWidth = srcWidth * deskHeight / srcHeight;
// } else {
// deskWidth = (int) comBase;
// deskHeight = srcHeight * deskWidth / srcWidth;
// }
// }
// } else {
// deskHeight = srcHeight;
// deskWidth = srcWidth;
// }
// // BufferedImage tag = new BufferedImage(deskWidth, deskHeight,
// BufferedImage.TYPE_3BYTE_BGR);
// // tag.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null);
// //绘制缩小后的图
// // FileOutputStream deskImage = new FileOutputStream(deskURL); //输出到文件流
// // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
// // encoder.encode(tag); //近JPEG编码
// // deskImage.close();
// }
//
// }