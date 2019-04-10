package com.seer.softraster.object;

import com.seer.math.Vector2f;
import com.seer.math.Vector3;

public class Mesh {
	
	protected Vector3[] vertices;
		
	protected Triangle triangles[];
	
//	protected Vector2f[] uv;
	
//	protected Vector3[] normals;
	
	public Mesh()
	{
		
	}
	
	public Mesh(Vector3[] vertices, int[] indices)
	{
		init(vertices, indices);
	}
	
	protected void init(Vector3[] vertices, int[] indices)
	{
		this.vertices = vertices;
		this.triangles = new Triangle[indices.length/3];
		for(int i = 0; i < triangles.length; i++)
		{
			this.triangles[i] = new Triangle(this.vertices, indices[i * 3], indices[i * 3 + 1], indices[i * 3 + 2]);
		}
	}
	
	protected void init(Vector3[] vertices, int[] indices, Vector2f[] uvs, int[] uvIndices)
	{
		this.vertices = vertices;
		this.triangles = new Triangle[indices.length/3];
		for(int i = 0; i < triangles.length; i++)
		{
			this.triangles[i] = new Triangle(this.vertices, indices[i * 3], indices[i * 3 + 1], indices[i * 3 + 2]);
			Vector2f[] uv = new Vector2f[3];
			uv[0] = uvs[uvIndices[i * 3]];
			uv[1] = uvs[uvIndices[i * 3 + 1]];
			uv[2] = uvs[uvIndices[i * 3 + 2]];
			this.triangles[i].setUv(uv);
		}
	}
	
	protected void init(Vector3[] vertices, int[] indices, Vector2f[] uvs, int[] uvIndices, Vector3[] normal)
	{
		this.vertices = vertices;
		this.triangles = new Triangle[indices.length/3];
		for(int i = 0; i < triangles.length; i++)
		{
			this.triangles[i] = new Triangle(this.vertices, indices[i * 3], indices[i * 3 + 1], indices[i * 3 + 2]);
			Vector2f[] uv = new Vector2f[3];
			uv[0] = uvs[uvIndices[i * 3]];
			uv[1] = uvs[uvIndices[i * 3 + 1]];
			uv[2] = uvs[uvIndices[i * 3 + 2]];
			this.triangles[i].setUv(uv);
			
			Vector3[] normals = new Vector3[3];
			normals[0] = normal[0];
			normals[1] = normal[1];
			normals[2] = normal[2];
			this.triangles[i].setNormal(normals);
		}
	}
}