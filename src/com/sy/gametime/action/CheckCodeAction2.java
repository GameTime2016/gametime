package com.sy.gametime.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class CheckCodeAction2 extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void getCheckCode() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		// 告知浏览当作图片处理
		response.setContentType("image/jpeg");
		// 告诉浏览器不缓存
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		response.setHeader("expires", "0");
		// 产生由4位数字构成的验证码
		int length = 4;
		String valcode = RandomStringUtils.random(4, true, true);
		// 把产生的验证码存入到Session中
		HttpSession session = request.getSession();
		session.setAttribute("valcode", valcode);
		// 产生图片
		int width = 120;
		int height = 30;
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取一个Graphics
		// Graphics g = img.getGraphics();
		// Graphics2D g2d = (Graphics2D) g;
		Graphics2D g = (Graphics2D) img.getGraphics();
		// 填充背景色
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		Random random = new Random();
		// 画100个噪点(颜色及位置随机)
		for (int i = 0; i < 100; i++) {
			// 随机颜色
			int rInt = random.nextInt(255);
			int gInt = random.nextInt(255);
			int bInt = random.nextInt(255);
			g.setColor(new Color(rInt, gInt, bInt));
			// 随机位置
			int xInt = random.nextInt(width - 3);
			int yInt = random.nextInt(height - 2);
			// 随机旋转角度
			int sAngleInt = random.nextInt(360);
			int eAngleInt = random.nextInt(360);
			// 随机大小
			int wInt = random.nextInt(6);
			int hInt = random.nextInt(6);
			g.fillArc(xInt, yInt, wInt, hInt, sAngleInt, eAngleInt);
			// 画5条干扰线
			if (i % 20 == 0) {
				int xInt2 = random.nextInt(width);
				int yInt2 = random.nextInt(height);
				g.drawLine(xInt, yInt, xInt2, yInt2);
			}

		}
		// 绘制边框
		// g.setColor(Color.GRAY);
		// g.drawRect(0, 0, width-1, height-1);
		// 绘制验证码
		int size = 24;
		Font[] fonts = { new Font("Times New Romans", Font.BOLD, size), new Font("Verdana", Font.BOLD, size), new Font("幼圆", Font.BOLD, size) };
		int x = 6;
		int y = 20;
		for (int i = 0; i < length; i++) {
			double d = random.nextInt() % 30;
			g.setColor(getColor(random.nextInt(10)));
			// 设置每个字符的字体
			g.setFont(fonts[random.nextInt(fonts.length)]);
			// 设置每个字符的随机旋转
			g.rotate(d * Math.PI / 180, x, y);
			// 字体是写在矩形里面 需要考虑字体空间以及字体的坐标，y标是不变的的
			g.drawString(valcode.charAt(i) + "", x, y);
			// 将字体弧度回调，不然下一个字体会旋转出去
			g.rotate(-d * Math.PI / 180, x, y);
			// 字体大小是 20，没间隔10 在写入一个
			x += 30;
		}
		// 输出图像
		g.dispose();
		try {
			ImageIO.write(img, "jpeg", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Color getColor(int index) {
//		Color start = new Color(192, 192, 0);
//		Color step = new Color(192, 128, 128);
//		Random r = new Random();
//		return new Color((start.getRed() + step.getRed() * index) % 256, 
//				(start.getGreen() + step.getGreen() * index) % 256,  
//				(start.getBlue() + step.getBlue() * index) % 256);
		Random random = new Random();
		int[] c = new int[3];
		int i = random.nextInt(c.length);
		for (int fi = 0; fi < c.length; fi++) {
			if (fi == i) {
				c[fi] = random.nextInt(71);
			} else {
				c[fi] = random.nextInt(256);
			}
		}
		return new Color(c[0], c[1], c[2]);
	}
}
