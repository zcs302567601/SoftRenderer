package com.seer.softraster.object;

import com.seer.math.Vector2f;
import com.seer.math.Vector3;
import com.seer.math.Vertex3;
import com.seer.shader.Shader;

public class TriangleFace {
	
	public Vertex3[] vertices;
	
	public Vector2f[] uv;
	public Vector3[] normal;
	public Shader shader;
	
	public TriangleFace(Vertex3[] vertex)
	{
		this.vertices = vertex;
	}
	
	public TriangleFace(Vertex3[] vertex, Vector2f[] uv)
	{
		this.vertices = vertex;
		this.uv = uv;
	}
	
	public TriangleFace(Vertex3[] vertex, Vector2f[] uv, Shader shader)
	{
		this.vertices = vertex;
		this.uv = uv;
		this.shader = shader;
	}
	
	public TriangleFace(Vertex3[] vertex, Vector2f[] uv, Vector3[] normal, Shader shader)
	{
		this.vertices = vertex;
		this.uv = uv;
		this.normal = normal;
		this.shader = shader;
	}
}
