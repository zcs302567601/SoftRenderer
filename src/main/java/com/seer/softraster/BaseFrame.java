package com.seer.softraster;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.JFrame;

public abstract class BaseFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	BufferedImage image;
	BufferStrategy strategy;
	boolean running = true;
	SoftRaster softRaster;
	
	public final int WIDTH = 600;
	public final int HEIGHT = 600;
	
	int targetFrameCount = 60;
	int fpsCount = 0;

	public BaseFrame()
	{
		Screen.init(WIDTH, HEIGHT);
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setUndecorated(true);
		this.setVisible(true);
		this.createBufferStrategy(2);
		
		strategy = this.getBufferStrategy();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
		DataBufferByte buffer = (DataBufferByte)(image.getRaster().getDataBuffer());
		byte[] bytes = buffer.getData();
		softRaster = new SoftRaster(WIDTH, HEIGHT, bytes);
	}
	
	
	protected void start()
	{
		init();
		int secondsFrame = 1000000000/targetFrameCount;
		long startTime = System.nanoTime();
		int fpsCounter = 0;
		long fpsStartTime = System.nanoTime();
		long deltaTime = 0;
		while (running) {
		     do {
		    	 update(deltaTime * 1.0f/1000000000);
		    	 loop();
		     } while (strategy.contentsLost());
		    deltaTime = (System.nanoTime() - startTime);
			if(deltaTime < secondsFrame)
			{
				try {
					Thread.sleep((secondsFrame - deltaTime)/1000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			fpsCounter += 1;
			if(fpsCounter >= 30)
			{
				fpsCounter = 0;
				fpsCount = (int) (1000000000/((System.nanoTime() - fpsStartTime)/30));
				fpsStartTime = System.nanoTime();
			}
			startTime = System.nanoTime();
		 }
		 this.setVisible(false);
		 this.dispose();
	}
		
	void loop()
	{
		render(softRaster);
		drawImage();
	}

	void drawImage()
	{
		Graphics graphics = strategy.getDrawGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.setColor(java.awt.Color.white);
        graphics.drawString("FPS : " + fpsCount, 10, 40);
        graphics.dispose();
        strategy.show();
	}
	
	public void update(float deltaTime){}
	
	public void init(){}

	public abstract void render(SoftRaster raster);
	
	public void clear(Color color)
	{
		for(int i = 0; i < WIDTH; i++ )
		{
			for(int j = 0; j < HEIGHT; j++)
			{
				softRaster.drawPixel(i, j, color);
			}
		}
	}
}