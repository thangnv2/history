package demo2;

import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.geotools.swing.JMapPane;

public class Line extends JMapPane{

	private static final long serialVersionUID = 1L;

	private double mLon = 55.4188;

	private double mLat = -107.0855;
	
	private Image mImage;
	
	/**
	 * 
	 * @param x: tọa độ x.
	 * @param y: tọa độ y.
	 * @param w: Chiều rộng của tấm hình.
	 * @param h: Chiều cao của tấm hinh.
	 * @param urlImage: đường dới tới ảnh lưu trên máy.
	 */
	public Line(int x, int y, int width, int height, String urlImage) {
		resolutionImage(x, y, width, height, urlImage);
	}
	
	@Override
	public void paint(Graphics g) {
		if (mImage != null) {
			g.drawImage(mImage, 0, 0, getWidth(), getHeight(), null);
			
		}
	}
	
	public double getLon() {
		return mLon;
	}

	public double getLat() {
		return mLat;
	}

	private Image loadImage(String urlImage) {
		System.out.println(urlImage);
		Image image = null;
		try {
			image = ImageIO.read(getClass().getResource(urlImage));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	private void resolutionImage(int x, int y, int width, int height, String urlImage) {
		setBounds(x, y, width, height);
		mImage = loadImage(urlImage);

		
	}

}

