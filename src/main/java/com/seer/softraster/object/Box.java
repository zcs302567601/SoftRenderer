package com.seer.softraster.object;

import com.seer.math.Matrix4x4;
import com.seer.math.Vector2;
import com.seer.math.Vector3;
import com.seer.math.Vector4;
import com.seer.math.Vertex3;
import com.seer.softraster.Color;

public class Box {
	
	public Vector3 position;
	Matrix4x4 M;
	Matrix4x4 R;
	float theta = 0.1f;
	MeshBox mesh;
	
	public Box(Vector3 pos)
	{
		position = pos;
		mesh = new MeshBox();
		M = new Matrix4x4();
		M.setRow1(new Vector4(1, 0, 0, position.x));
		M.setRow2(new Vector4(0, 1, 0, position.y));
		M.setRow3(new Vector4(0, 0, 1, position.z));
		M.setRow4(new Vector4(0, 0, 0, 1));
		R = new Matrix4x4();
	}
	
	public Triangle[] getTriangles()
	{
		return mesh.triangles;
	}
	
	public Vertex3[] vert(Matrix4x4 V, Matrix4x4 P, Matrix4x4 viewPort)
	{
		Vertex3[] vertexes = new Vertex3[mesh.vertices.length];

		float cos = (float) Math.cos(theta);
		float sin = (float) Math.sin(theta);
		R.setRow1(new Vector4(cos, sin, 0, 0));
		R.setRow2(new Vector4(-sin, cos, 0, 0));
		R.setRow3(new Vector4(0, 0, 1, 0));
		R.setRow4(new Vector4(0, 0, 0, 1));
		
		Matrix4x4 mvp = P.mul(V).mul(M);//P.Mul(V).Mul(M).Mul(R);
		for(int i = 0; i < vertexes.length; i++)
		{
			Color color = Color.Red;
			if(i < 4){
				color = i%2 == 0? Color.Red : Color.Blue;
			}
			else
			{
				color = i%2 == 0? Color.White : Color.Green;
			}
			vertexes[i] = new Vertex3(mesh.vertices[i], color);
			vertexes[i].worldPosition = M.mulPoint(vertexes[i].localPosition);
			vertexes[i].eyePosition = V.mul(new Vector4(vertexes[i].worldPosition, 1));
			vertexes[i].ndcPosition = mvp.mul(new Vector4(vertexes[i].localPosition, 1));
			
			float w = vertexes[i].ndcPosition.w;
			vertexes[i].perspectivePosition = new Vector4();
			vertexes[i].perspectivePosition.x = vertexes[i].ndcPosition.x/w;
			vertexes[i].perspectivePosition.y = vertexes[i].ndcPosition.y/w;
			vertexes[i].perspectivePosition.z = vertexes[i].ndcPosition.z/w;
			vertexes[i].perspectivePosition.w = 1;
			if(vertexes[i].perspectivePosition.z < 0 || vertexes[i].perspectivePosition.z > 1)
			{
				System.err.println("0 < z || z > 1");
				return null;
			}
			Vector4 screenPoint = viewPort.mul(vertexes[i].perspectivePosition);
			int x1 = (int)screenPoint.x;
			int y1 = (int)screenPoint.y;
			int x = screenPoint.x - x1 > 0.5 ? x1 +1 : x1 ;
			int y = screenPoint.y - y1 > 0.5 ? y1 +1 : y1 ;
			vertexes[i].screenPos = new Vector2(x, y);
		}
		return vertexes;
	}
	
	public void update(float delta)
	{
		theta += delta;
	}
}