package com.seer.softraster.object;

import com.seer.math.Vector2f;
import com.seer.math.Vector3;

public class Triangle {
	
	public Vector3[] vertices;
	
	public int[] indices;
	
	public Vector2f[] uv;
	public Vector3[] normal;
	
	public Triangle(Vector3[] vertices, int[] indices) throws Exception
	{
		if(indices.length != 3)
		{
			throw new Exception();
		}
		this.vertices = new Vector3[3];
		this.indices = indices;
		for(int i = 0; i < 3; i++)
		{
			this.vertices[i] = vertices[indices[i]];
		}
	}
	
	public Triangle(Vector3[] vertices, int indice1, int indice2, int indice3)
	{
		this.vertices = new Vector3[3];
		this.indices = new int[]{indice1, indice2, indice3};
		this.vertices[0] = vertices[indice1];
		this.vertices[1] = vertices[indice2];
		this.vertices[2] = vertices[indice3];
	}
	
	public void setUv(Vector2f[] uv)
	{
		this.uv = uv;
	}
	
	public void setNormal(Vector3[] normal)
	{
		this.normal = normal;
	}
}