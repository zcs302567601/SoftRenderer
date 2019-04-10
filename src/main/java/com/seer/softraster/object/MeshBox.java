package com.seer.softraster.object;

import com.seer.math.Vector2f;
import com.seer.math.Vector3;

public class MeshBox extends Mesh{
	
	public MeshBox()
	{
		Vector3[] vertices = new Vector3[8];
		vertices[0] = new Vector3(-0.5f, -0.5f, -0.5f);
		vertices[1] = new Vector3(0.5f, -0.5f, -0.5f);
		vertices[2] = new Vector3(0.5f, 0.5f, -0.5f);
		vertices[3] = new Vector3(-0.5f, 0.5f, -0.5f);
		
		vertices[4] = new Vector3(-0.5f, -0.5f, 0.5f);
		vertices[5] = new Vector3(0.5f, -0.5f, 0.5f);
		vertices[6] = new Vector3(0.5f, 0.5f, 0.5f);
		vertices[7] = new Vector3(-0.5f, 0.5f, 0.5f);
		
		int[] indices = new int[]{0, 3, 2, 2, 1, 0, 1, 2, 6, 6, 5, 1, 4, 5, 6, 6, 7, 4, 0, 4, 7, 7, 3, 0, 0, 1, 5, 5, 4, 0, 3, 7, 6, 6, 2, 3};
		
		Vector2f[] uv = new Vector2f[8];
		uv[0] = new Vector2f(0, 0);
		uv[1] = new Vector2f(1, 0);
		uv[2] = new Vector2f(1, 1);
		uv[3] = new Vector2f(0, 1);
		
		int[] uvIndices = new int[]{0, 3, 2, 2, 1, 0, 0, 3, 2, 2, 1, 0, 0, 3, 2, 2, 1, 0, 0, 3, 2, 2, 1, 0, 0, 3, 2, 2, 1, 0, 0, 3, 2, 2, 1, 0};
		init(vertices, indices, uv, uvIndices);
	}
}