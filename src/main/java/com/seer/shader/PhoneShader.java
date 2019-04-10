package com.seer.shader;

import com.seer.math.Matrix4x4;
import com.seer.math.Vector2;
import com.seer.math.Vector3;
import com.seer.math.Vector4;
import com.seer.math.Vertex3;
import com.seer.softraster.Color;
import com.seer.softraster.playjoy.d3.PerspectiveCamera;

public class PhoneShader implements Shader {
	
	public PerspectiveCamera camera;

	public PhoneShader(PerspectiveCamera camera)
	{
		this.camera = camera;
	}
	
	@Override
	public Vertex3[] vert(Vector3[] vertices, Matrix4x4 M, Matrix4x4 V, Matrix4x4 P, Matrix4x4 viewPortM)
	{
		Vertex3[] vertexes = new Vertex3[vertices.length];
		for(int i = 0; i < vertexes.length; i++)
		{
			vertexes[i] = new Vertex3(vertices[i]);
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
			vertexes[i].color = CalPhoneInWorldSpace(vertexes[i], M);
		}
		return vertexes;	
	}
	
	Color CalPhoneInWorldSpace(Vertex3 vertex, Matrix4x4 M)
	{
		// the angle of x,y,z have the same sacel,so use M temp
		vertex.normal = M.mulVector3(new Vector3(0, 0, -1)).normalize();
		Vector3 lightPos = new Vector3(-2, 0, -4);
		Color light = Color.White;
		float ambientStrength = 0.1f;
		//ambient
		Color ambient = Color.mul(light, ambientStrength);
		//diffuse
		Vector3 lightDirection = Vector3.del(lightPos, vertex.worldPosition);
		lightDirection = lightDirection.normalize();
		float diff = Math.max(0, Vector3.dot(lightDirection, vertex.normal));
		Color diffuse = Color.mul(light, diff);
		//specular
		float specularStrength = 1.0f;
		Vector3 viewDir = Vector3.del(camera.position, vertex.worldPosition);
		viewDir = viewDir.normalize();
		Vector3 half = Vector3.add(lightDirection, viewDir);
		half = half.normalize();
		float dotHalfNormal = Vector3.dot(half, vertex.normal);
		float spec = (float) Math.pow(Math.max(0, dotHalfNormal), 256);
		Color specular = Color.mul(light, spec * specularStrength);
		Color color = Color.add(ambient, diffuse).add(specular);
		return color;
	}

	@Override
	public Color fragment(V2F v) {
		Color col = v.texture.sample(v.uv0.x, v.uv0.y).mul(v.color);
		return col;
	}
}