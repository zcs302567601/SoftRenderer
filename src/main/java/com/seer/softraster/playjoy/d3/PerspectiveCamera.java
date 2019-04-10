package com.seer.softraster.playjoy.d3;

import com.seer.math.Matrix4x4;
import com.seer.math.Vector3;
import com.seer.math.Vector4;

public class PerspectiveCamera 
{
	public Vector3 position;
	float near;
	float far;
	float xLeft;
	float xRight;
	float yBottom;
	float yUp;
	public Matrix4x4 project;
	
	public PerspectiveCamera(Vector3 position, float xLeft, float xRight, float yBottom, float yUp, float near, float far)
	{
		this.position = position;
		this.xLeft = xLeft;
		this.xRight = xRight;
		this.yBottom = yBottom;
		this.yUp = yUp;
		this.near = near;
		this.far = far;
				
		project = new Matrix4x4();
		project.setRow1(new Vector4(2*near/(xRight - xLeft), 0, (xLeft + xRight)/(xLeft-xRight), 0));
		project.setRow2(new Vector4(0, 2*near/(yUp - yBottom), (yBottom + yUp)/(yBottom-yUp), 0));
		project.setRow3(new Vector4(0, 0, far/(far - near), far * near/(near - far)));
		project.setRow4(new Vector4(0, 0, 1, 0));
	}
	
	public Matrix4x4 lookAt(Vector3 targetPos)
	{
		Vector3 n = Vector3.del(targetPos, position);
		Vector3 u = Vector3.cross(new Vector3(0, 1, 0), n);
		Vector3 v = Vector3.cross(n, u);
		Vector3 N = n.normalize();
		Vector3 U = u.normalize();
		Vector3 V = v.normalize();
		Matrix4x4 view = new Matrix4x4();
		Vector3 t = Vector3.mul(position, -1);
		view.setRow1(new Vector4(U.x, U.y, U.z, Vector3.dot(U, t)));
		view.setRow2(new Vector4(V.x, V.y, V.z, Vector3.dot(V, t)));
		view.setRow3(new Vector4(N.x, N.y, N.z, Vector3.dot(N, t)));
		view.setRow4(new Vector4(0, 0, 0, 1));
		return view;
	}
}
