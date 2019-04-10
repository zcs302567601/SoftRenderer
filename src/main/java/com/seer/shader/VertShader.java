package com.seer.shader;

import com.seer.math.Matrix4x4;
import com.seer.math.Vector2;
import com.seer.math.Vector3;
import com.seer.math.Vector4;
import com.seer.math.Vertex3;
import com.seer.softraster.Color;

public class VertShader implements Shader {
	
//	Matrix4x4 M;
//	Matrix4x4 V;
//	Matrix4x4 P;
//	Matrix4x4 viewPortM;
	
//	public Vert(Matrix4x4 M, Matrix4x4 V, Matrix4x4 P, Matrix4x4 viewPortM)
//	{
//		this.M = M;
//		this.V = V;
//		this.P = P;
//		this.viewPortM = viewPortM;
//	}
	
	@Override
	public Vertex3[] vert(Vector3[] vertices, Matrix4x4 M, Matrix4x4 V, Matrix4x4 P, Matrix4x4 viewPortM)
	{
		Vertex3[] vertexes = new Vertex3[vertices.length];
		
//		Matrix4x4 mvp = P.Mul(V).Mul(M);//P.Mul(V).Mul(M).Mul(R);
		for(int i = 0; i < vertexes.length; i++)
		{
			Color color = Color.White;
			vertexes[i] = new Vertex3(vertices[i], color);
			vertexes[i].worldPosition = M.mulPoint(vertexes[i].localPosition);
			vertexes[i].eyePosition = V.mul(new Vector4(vertexes[i].worldPosition, 1));
			vertexes[i].ndcPosition = P.mul(vertexes[i].eyePosition);

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
			Vector4 screenPoint = viewPortM.mul(vertexes[i].perspectivePosition);
			int x1 = (int)screenPoint.x;
			int y1 = (int)screenPoint.y;
			int x = screenPoint.x - x1 > 0.5 ? x1 +1 : x1 ;
			int y = screenPoint.y - y1 > 0.5 ? y1 +1 : y1 ;
			vertexes[i].screenPos = new Vector2(x, y);
			vertexes[i].perspectivePosition.w = w;//vertexes[i].eyePosition.z;
		}
		return vertexes;
	}

	@Override
	public Color fragment(V2F v) {
		Color col = v.texture.sample(v.uv0.x, v.uv0.y).mul(v.color);
		return col;
	}
}