package com.seer.softraster.object;

import java.util.HashMap;

public class Texture2dMapping {
	
	static HashMap<String, Texture2d> textures = new HashMap<String, Texture2d>();
	
	public static Texture2d get(String name)
	{
		return textures.get(name);
	}
	
	public static void put(String name, Texture2d texture)
	{
		textures.put(name, texture);
	}
}
