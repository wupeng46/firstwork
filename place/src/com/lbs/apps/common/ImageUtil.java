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
 * * @author WQ * @date 2011-01-14 * @versions 1.0 ͼƬѹ�������� �ṩ�ķ����п����趨���ɵ�
 * ����ͼƬ�Ĵ�С�ߴ��
 */
public class ImageUtil {
	LogHelper log = new LogHelper(this.getClass());

	
	/** * ͼƬ�ļ���ȡ * * @param srcImgPath * @return */
	private static BufferedImage InputImage(String srcImgPath) {
		BufferedImage srcImage = null;
		try {
			FileInputStream in = new FileInputStream(srcImgPath);
			srcImage = javax.imageio.ImageIO.read(in);
		} catch (IOException e) {
			System.out.println("��ȡͼƬ�ļ�����" + e.getMessage());
			e.printStackTrace();
		}
		return srcImage;
	}

	/**
	 * * ��ͼƬ����ָ����ͼƬ�ߴ�ѹ�� * * @param srcImgPath :ԴͼƬ·�� * @param outImgPath *
	 * :�����ѹ��ͼƬ��·�� * @param new_w * :ѹ�����ͼƬ�� * @param new_h * :ѹ�����ͼƬ��
	 */
	public static void compressImage(String srcImgPath, String outImgPath,
			int new_w, int new_h) {
		BufferedImage src = InputImage(srcImgPath);
		disposeImage(src, outImgPath, new_w, new_h);
	}

	/**
	 * * ��ͼƬ����ָ����ͼƬ�ߴ�ѹ�� * * @param srcImgPath :ԴͼƬ·�� * @param outImgPath *
	 * :�����ѹ��ͼƬ��·�� * @param new_w * :ѹ�����ͼƬ�� * @param new_h * :ѹ�����ͼƬ��
	 * @throws IOException 
	 */
	public static void compressImage2(String srcImgPath, String outImgPath,
			int new_w, int new_h) {
		BufferedImage src = InputImage(srcImgPath);
		if (null != src) {
			int old_w = src.getWidth();
			// �õ�Դͼ��
			int old_h = src.getHeight();

			double srcScale = (double) old_h / old_w;

			// �õ�Դͼ��
			int new_w_t = 0;
			// ��ͼ�Ŀ�
			int new_h_t = 0;
			// ��ͼ�ĳ�
			// ����ͼƬ�ߴ�ѹ���ȵõ���ͼ�ĳߴ�

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
					// ͼƬҪ���ŵı���
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
	 * * ָ�������߿�����ֵ��ѹ��ͼƬ * * @param srcImgPath * :ԴͼƬ·�� * @param outImgPath *
	 * :�����ѹ��ͼƬ��·�� * @param maxLength * :�����߿�����ֵ
	 */
	public static void compressImage(String srcImgPath, String outImgPath,
			int maxLength) {
		// �õ�ͼƬ
		BufferedImage src = InputImage(srcImgPath);
		if (null != src) {
			int old_w = src.getWidth();
			// �õ�Դͼ��
			int old_h = src.getHeight();
			// �õ�Դͼ��
			int new_w = 0;
			// ��ͼ�Ŀ�
			int new_h = 0;
			// ��ͼ�ĳ�
			// ����ͼƬ�ߴ�ѹ���ȵõ���ͼ�ĳߴ�
			if (old_w > old_h) {
				// ͼƬҪ���ŵı���
				new_w = maxLength;
				new_h = (int) Math.round(old_h * ((float) maxLength / old_w));
			} else {
				new_w = (int) Math.round(old_w * ((float) maxLength / old_h));
				new_h = maxLength;
			}
			disposeImage(src, outImgPath, new_w, new_h);
		}
	}

	/** * ����ͼƬ * * @param src * @param outImgPath * @param new_w * @param new_h */
	private synchronized static void disposeImage(BufferedImage src,
			String outImgPath, int new_w, int new_h) {
		// �õ�ͼƬ
		int old_w = src.getWidth();
		// �õ�Դͼ��
		int old_h = src.getHeight();
		// �õ�Դͼ��
		BufferedImage newImg = null;
		// �ж�����ͼƬ������
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
		// ��ԭͼ��ȡ��ɫ������ͼ
		g.drawImage(src, 0, 0, old_w, old_h, null);
		g.dispose();
		// ����ͼƬ�ߴ�ѹ���ȵõ���ͼ�ĳߴ�
		newImg.getGraphics().drawImage(
				src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0,
				null);
		// ���÷������ͼƬ�ļ�
		OutImage(outImgPath, newImg);
	}

	/**
	 * * ��ͼƬ�ļ������ָ����·���������趨ѹ������ * * @param outImgPath * @param newImg * @param
	 * per
	 */
	private static void OutImage(String outImgPath, BufferedImage newImg) {
		// �ж�������ļ���·���Ƿ���ڣ��������򴴽�
		File file = new File(outImgPath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}// ������ļ���
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
	 * Description: ����ԭͼ�����size��ȡ�ֲ�ͼƬ
	 * </p>
	 * 
	 * @param srcImg
	 *            ԴͼƬ
	 * @param output
	 *            ͼƬ�����
	 * @param rect
	 *            ��Ҫ��ȡ���ֵ�����ʹ�С
	 */
	public void cutImage(File srcImg, OutputStream output,
			java.awt.Rectangle rect) {
		if (srcImg.exists()) {
			java.io.FileInputStream fis = null;
			ImageInputStream iis = null;
			try {
				fis = new FileInputStream(srcImg);
				// ImageIO ֧�ֵ�ͼƬ���� : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG,
				// JPEG, WBMP, GIF, gif]
				String types = Arrays.toString(ImageIO.getReaderFormatNames())
						.replace("]", ",");
				String suffix = null;
				// ��ȡͼƬ��׺
				if (srcImg.getName().indexOf(".") > -1) {
					suffix = srcImg.getName().substring(
							srcImg.getName().lastIndexOf(".") + 1);
				}// ���ͺ�ͼƬ��׺ȫ��Сд��Ȼ���жϺ�׺�Ƿ�Ϸ�
				if (suffix == null
						|| types.toLowerCase().indexOf(
								suffix.toLowerCase() + ",") < 0) {
					log.error("Sorry, the image suffix is illegal. the standard image suffix is {"+ types + "}.");
					return;
				}
				// ��FileInputStream ת��ΪImageInputStream
				iis = ImageIO.createImageInputStream(fis);
				// ����ͼƬ���ͻ�ȡ�������͵�ImageReader
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
	 * ��ȡһ��ͼ�����������
	 * 
	 * @param image
	 *            ͼ��File
	 * @param w
	 *            ��Ҫ��ͼ�Ŀ��
	 * @param h
	 *            ��Ҫ��ͼ�ĸ߶�
	 * @return ����һ��
	 * @throws IOException
	 */
	public static void cutImage(File image, String outImgPath, int w, int h)
			throws IOException {
		InputStream inputStream = new FileInputStream(image);
		// ��ImageIO��ȡ�ֽ���
		BufferedImage bufferedImage = ImageIO.read(inputStream);
		BufferedImage distin = null;
		// ����ԴͼƬ�Ŀ�ȡ�
		int srcW = bufferedImage.getWidth();
		// ����ԴͼƬ�ĸ߶ȡ�
		int srcH = bufferedImage.getHeight();
		int x = 0, y = 0;
		// ʹ��ͼ�������
		x = srcW / 2 - w / 2;
		y = srcH / 2 - h / 2;
		srcW = srcW / 2 + w / 2;
		srcH = srcH / 2 + h / 2;
		// ����ͼƬ
		distin = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = distin.getGraphics();
		g.drawImage(bufferedImage, 0, 0, w, h, x, y, srcW, srcH, null);
		// ���÷������ͼƬ�ļ�
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
// * @date 2014��10��28�� ����10:24:26
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
// * <p>Description: ����ԭͼ�����size��ȡ�ֲ�ͼƬ</p>
// * @param srcImg ԴͼƬ
// * @param output ͼƬ�����
// * @param rect ��Ҫ��ȡ���ֵ�����ʹ�С
// */
// public void cutImage(File srcImg, OutputStream output, java.awt.Rectangle
// rect){
// if(srcImg.exists()){
// java.io.FileInputStream fis = null;
// ImageInputStream iis = null;
// try {
// fis = new FileInputStream(srcImg);
// // ImageIO ֧�ֵ�ͼƬ���� : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP,
// GIF, gif]
// String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]",
// ",");
// String suffix = null;
// // ��ȡͼƬ��׺
// if(srcImg.getName().indexOf(".") > -1) {
// suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
// }// ���ͺ�ͼƬ��׺ȫ��Сд��Ȼ���жϺ�׺�Ƿ�Ϸ�
// if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()+",") <
// 0){
// log.error("Sorry, the image suffix is illegal. the standard image suffix is {}."
// + types);
// return ;
// }
// // ��FileInputStream ת��ΪImageInputStream
// iis = ImageIO.createImageInputStream(fis);
// // ����ͼƬ���ͻ�ȡ�������͵�ImageReader
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
// * <p>Description: ����ͼƬ·����������ͼ </p>
// * @param imagePath ԭͼƬ·��
// * @param w ����ͼ��
// * @param h ����ͼ��
// * @param prevfix ��������ͼ��ǰ׺
// * @param force �Ƿ�ǿ�ư��տ����������ͼ(���Ϊfalse����������ѱ�������ͼ)
// */
// public void thumbnailImage(File srcImg, OutputStream output, int w, int h,
// String prevfix, boolean force){
// if(srcImg.exists()){
// try {
// // ImageIO ֧�ֵ�ͼƬ���� : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP,
// GIF, gif]
// String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]",
// ",");
// String suffix = null;
// // ��ȡͼƬ��׺
// if(srcImg.getName().indexOf(".") > -1) {
// suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
// }// ���ͺ�ͼƬ��׺ȫ��Сд��Ȼ���жϺ�׺�Ƿ�Ϸ�
// if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()+",") <
// 0){
// log.error("Sorry, the image suffix is illegal. the standard image suffix is {}."
// + types);
// return ;
// }
// log.debug("target image's size, width:{"+w+"}, height:{"+h+"}.");
// Image img = ImageIO.read(srcImg);
// // ����ԭͼ��Ҫ�������ͼ�������ҵ�����ʵ�����ͼ����
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
// // ��ͼƬ������ԭĿ¼������ǰ׺
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
// * �ȱ���ѹ���㷨��
// * �㷨˼�룺����ѹ��������ѹ������ѹ��ԭͼ������һ��ͼƬЧ����ӽ�ԭͼ������ͼ
// * @param srcURL ԭͼ��ַ
// * @param deskURL ����ͼ��ַ
// * @param comBase ѹ������
// * @param scale ѹ������(��/��)���� һ����1��
// *
// ��scale>=1,����ͼheight=comBase,width��ԭͼ��߱���;��scale<1,����ͼwidth=comBase,height��ԭͼ��߱���
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
// int deskHeight = 0;// ����ͼ��
// int deskWidth = 0;// ����ͼ��
// double srcScale = (double) srcHeight / srcWidth;
// /**����ͼ����㷨*/
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
// //������С���ͼ
// // FileOutputStream deskImage = new FileOutputStream(deskURL); //������ļ���
// // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
// // encoder.encode(tag); //��JPEG����
// // deskImage.close();
// }
//
// }