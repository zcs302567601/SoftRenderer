package com.seer.softraster;

public class Color {
	
	public static final Color Red = new Color(1.0f, 0, 0, 1.0f);
	public static final Color White = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	public static final Color BLACK = new Color(0, 0, 0, 1.0f);
	public static final Color Green = new Color(0, 1.0f, 0, 1.0f);
	public static final Color Blue = new Color(0, 0, 1.0f, 1.0f);
	
	public float r;
	public float g;
	public float b;
	public float a;
	
	
	public Color(float r, float g, float b, float a)
	{
//		this.r = (byte)(r & 0x000000FF);
//		this.g = (byte)(g & 0x000000FF);
//		this.b = (byte)(b & 0x000000FF);
//		this.a = (byte)(a & 0x000000FF);
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public Color(int r, int g, int b, int a)
	{
		this.r = r/255.0f;
		this.g = g/255.0f;
		this.b = b/255.0f;
		this.a = a/255.0f;
	}
	
	public Color(Color color)
	{
		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
		this.a = color.a;
	}
	
	public byte getRed()
	{
		int r = (int) Math.min(this.r * 255 + 0.5, 255.0);
		return (byte) r;
	}
	
	public byte getGreen()
	{
		int g = (int) Math.min(this.g * 255 + 0.5, 255.0);
		return (byte) g;
	}
	
	public byte getBlue()
	{
		int b = (int) Math.min(this.b * 255 + 0.5, 255.0);
		return (byte) b;
	}
	
	public byte getAlpha()
	{
		int a = (int) Math.min(this.a * 255 + 0.5, 255.0);
		return (byte) a;
	}
	
	public Color mul(float f)
	{
		this.r = (this.r * f);
		this.g = (this.g * f);
		this.b = (this.b * f);
		this.a = (this.a * f);
		return this;
	}
	
	public Color del(Color color)
	{
		this.r -= color.r;
		this.g -= color.g;
		this.b -= color.b;
		this.a -= color.a;
		return this;
	}
	
	public Color add(Color color)
	{
		this.r += color.r;
		this.g += color.g;
		this.b += color.b;
		this.a += color.a;
		return this;
	}
	
	public Color mul(Color color)
	{
		this.r *= color.r;
		this.g *= color.g;
		this.b *= color.b;
		this.a *= color.a;
		return this;
	}
	
	@Override
	public String toString() {
		return "Color [r=" + r + ", g=" + g + ", b=" + b + ", a=" + a + "]";
	}

	public static Color del(Color a, Color b)
	{
		Color c = new Color(a.r - b.r, a.g - b.g, a.b - b.b, a.a - b.a);
		return c;
	}
	
	public static Color add(Color a, Color b)
	{
		Color c = new Color(a.r + b.r, a.g + b.g, a.b + b.b, a.a + b.a);
		return c;
	}
	
	public static Color mul(Color a, float t)
	{
		float r = a.r * t;
		float g = a.g * t;
		float b = a.b * t;
		float alhpa = a.a * t;
		Color c = new Color(r, g, b, alhpa);
		return c;
	}
	
	public static Color mul(Color color1, Color color2)
	{
		float r = color1.r * color2.r;
		float g = color1.g * color2.g;
		float b = color1.b * color2.b;
		float a = color1.a * color2.a;
		return new Color(r, g, b, a);
	}
}