package com.seer.softraster.object;

import com.seer.math.Matrix4x4;
import com.seer.math.Vector3;
import com.seer.math.Vector4;
import com.seer.math.Vertex3;
import com.seer.shader.Shader;
import com.seer.shader.VertShader;

public class Plane {
	
	private PlaneMesh mesh;
	private Matrix4x4 M;
//	private Matrix4x4 S;
	public Shader shader;
	
	public Plane(Vector3 position)
	{
		mesh = new PlaneMesh();
		M = new Matrix4x4();
		int scale = 5;
		M.setRow1(new Vector4(1 * scale, 0, 0, position.x));
		M.setRow2(new Vector4(0, 1 * scale, 0, position.y));
		M.setRow3(new Vector4(0, 0, 1 * scale, position.z));
		M.setRow4(new Vector4(0, 0, 0, 1));
		
		float angle = -(float) (Math.PI/6);
		Matrix4x4 R = new Matrix4x4();
		R.setRow1(new Vector4(1, 0, 0, 0));
		R.setRow2(new Vector4(0, (float)Math.cos(angle), -(float)Math.sin(angle), 0));
		R.setRow3(new Vector4(0, (float)Math.sin(angle), (float)Math.cos(angle), 0));
		R.setRow4(new Vector4(0, 0, 0, 1));
		M = R.mul(M);
		this.shader = new VertShader();
	}
	
	public Plane(Vector3 position, Shader shader)
	{
		mesh = new PlaneMesh();
		M = new Matrix4x4();
		int scale = 5;
		M.setRow1(new Vector4(1 * scale, 0, 0, position.x));
		M.setRow2(new Vector4(0, 1 * scale, 0, position.y));
		M.setRow3(new Vector4(0, 0, 1 * scale, position.z));
		M.setRow4(new Vector4(0, 0, 0, 1));
		
		float angle = (float) (Math.PI/6);
		Matrix4x4 R = new Matrix4x4();
		R.setRow1(new Vector4(1, 0, 0, 0));
		R.setRow2(new Vector4(0, (float)Math.cos(angle), -(float)Math.sin(angle), 0));
		R.setRow3(new Vector4(0, (float)Math.sin(angle), (float)Math.cos(angle), 0));
		R.setRow4(new Vector4(0, 0, 0, 1));
		M = R.mul(M);
		this.shader = shader;
	}
	
	public Triangle[] getTriangles()
	{
		return mesh.triangles;
	}
	
	public Vertex3[] shader(Matrix4x4 V, Matrix4x4 P, Matrix4x4 viewPortM)
	{
		return shader.vert(mesh.vertices, M, V, P, viewPortM);
	}
}