package com.seer.softraster.object;

import com.seer.math.Vector2f;
import com.seer.math.Vector3;

public class PlaneMesh extends Mesh{
	
	public PlaneMesh()
	{
		Vector3[] vertices = new Vector3[4];
		vertices[0] = new Vector3(-0.5f, -0.5f, 0);
		vertices[1] = new Vector3(-0.5f, 0.5f, 0);
		vertices[2] = new Vector3(0.5f, 0.5f, 0);
		vertices[3] = new Vector3(0.5f, -0.5f, 0);
		int[] indices = new int[]{0, 3, 2, 2, 1, 0};
		
		Vector2f[] uv = new Vector2f[4];
		uv[0] = new Vector2f(0, 0);
		uv[1] = new Vector2f(0, 1);
		uv[2] = new Vector2f(1, 1);
		uv[3] = new Vector2f(1, 0);
		int[] uvIndices = new int[]{0, 3, 2, 2, 1, 0};
		
		Vector3[] normals = new Vector3[4];
		normals[0] = new Vector3(0, 0, -1);
		normals[1] = new Vector3(0, 0, -1);
		normals[2] = new Vector3(0, 0, -1);
		normals[3] = new Vector3(0, 0, -1);
		init(vertices, indices, uv, uvIndices, normals);
	}
}
