package com.seer.math;

//row matrix4x4, not column

public class Matrix4x4 {
	private float m11, m12, m13, m14; 
	private float m21, m22, m23, m24;
	private float m31, m32, m33, m34;
	private float m41, m42, m43, m44;
	
	public Matrix4x4()
	{
		
	}
	
	public Matrix4x4(Vector4 v1, Vector4 v2, Vector4 v3, Vector4 v4)
	{
		setRow1(v1);
		setRow2(v1);
		setRow3(v1);
		setRow4(v1);
	}
	
	public void setRow1(Vector4 v)
	{
		m11 = v.x; m12 = v.y; m13 = v.z; m14 = v.w;
	}
	
	public void setRow2(Vector4 v)
	{
		m21 = v.x; m22 = v.y; m23 = v.z; m24 = v.w;
	}
	
	public void setRow3(Vector4 v)
	{
		m31 = v.x; m32 = v.y; m33 = v.z; m34 = v.w;
	}
	
	public void setRow4(Vector4 v)
	{
		m41 = v.x; m42 = v.y; m43 = v.z; m44 = v.w;
	}
	
	public Matrix4x4 mul(float t)
	{
		this.m11 *= t;
		this.m12 *= t;
		this.m13 *= t;
		this.m14 *= t;
		this.m21 *= t;
		this.m22 *= t;
		this.m23 *= t;
		this.m24 *= t;
		this.m31 *= t;
		this.m32 *= t;
		this.m33 *= t;
		this.m34 *= t;
		this.m41 *= t;
		this.m42 *= t;
		this.m43 *= t;
		this.m44 *= t;
		return this;
	}
	
	public Matrix4x4 mul(Matrix4x4 m)
	{
		return Matrix4x4.mul(this, m);
	}

	public Vector4 mul(Vector4 v)
	{
		Vector4 t = new Vector4();
		t.x = m11 * v.x + m12 * v.y + m13 * v.z + m14 * v.w;
		t.y = m21 * v.x + m22 * v.y + m23 * v.z + m24 * v.w;
		t.z = m31 * v.x + m32 * v.y + m33 * v.z + m34 * v.w;
		t.w = m41 * v.x + m42 * v.y + m43 * v.z + m44 * v.w;
		return t;
	}

	public Vector3 mulPoint(Vector3 point)
	{
		Vector4 v = mul(new Vector4(point.x, point.y, point.z, 1.0f));
		return new Vector3(v.x, v.y, v.z);
	}

	public Vector3 mulVector3(Vector3 v)
	{
		Vector4 v4 = mul(new Vector4(v.x, v.y, v.z, 0));
		return new Vector3(v4.x, v4.y, v4.z);
	}
	
	public Matrix4x4 transposition()
	{
		Matrix4x4 m = new Matrix4x4();
		m.m11 = m11;
		m.m12 = m21;
		m.m13 = m31;
		m.m14 = m41;
		
		m.m21 = m12;
		m.m22 = m22;
		m.m23 = m32;
		m.m24 = m42;
		
		m.m31 = m13;
		m.m32 = m23;
		m.m33 = m33;
		m.m34 = m43;
		
		m.m41 = m14;
		m.m42 = m24;
		m.m43 = m34;
		m.m44 = m44;
		return m;
	}
	
	public Matrix4x4 inverse()
	{
		Matrix4x4 m = new Matrix4x4();

		float m11 = 1 * det(m22, m23, m24, m32, m33, m34, m42, m43, m44);
		float m12 = -1 * det(m21, m23, m24, m31, m33, m34, m41, m43, m44);
		float m13 = 1 * det(m21, m22, m24, m31, m32, m34, m41, m42, m44);
		float m14 = -1 * det(m21, m22, m23, m31, m32, m33, m41, m42, m43);
		
		float m21 = -1 * det(m12, m13, m14, m32, m33, m34, m42, m43, m44);
		float m22 = 1 * det(m11, m13, m14, m31, m33, m34, m41, m43, m44);
		float m23 = -1 * det(m11, m12, m14, m31, m32, m34, m41, m42, m44);
		float m24 = 1 * det(m11, m12, m13, m31, m32, m33, m41, m42, m43);
		
		float m31 = 1 * det(m12, m13, m14, m22, m23, m24, m42, m43, m44);
		float m32 = -1 * det(m11, m13, m14, m21, m23, m23, m41, m43, m44);
		float m33 = 1 * det(m11, m12, m13, m21, m22, m24, m41, m42, m44);
		float m34 = -1 * det(m11, m12, m13, m21, m22, m23, m41, m42, m43);
		
		float m41 = -1 * det(m12, m13, m14, m22, m23, m24, m32, m33, m34);
		float m42 = 1 * det(m11, m13, m14, m21, m23, m24, m31, m33, m34);
		float m43 = -1 * det(m11, m12, m14, m21, m22, m24, m31, m32, m34);
		float m44 = 1 * det(m11, m12, m13, m21, m22, m23, m31, m32, m33);
		
		m.m11 = m11;
		m.m12 = m21;
		m.m13 = m31;
		m.m14 = m41;
		
		m.m21 = m12;
		m.m22 = m22;
		m.m23 = m32;
		m.m24 = m42;
		
		m.m31 = m13;
		m.m32 = m23;
		m.m33 = m33;
		m.m34 = m43;
		
		m.m41 = m14;
		m.m42 = m24;
		m.m43 = m34;
		m.m44 = m44;
		float det = det();
		m.mul(det);
		return m;
	}
	
	public float det(float x1, float y1, float z1,
					 float x2, float y2, float z2,
					 float x3, float y3, float z3)
	{
		float detValue = x1 * y2 * z3 + y1 * z2 * x3 + z1 * x2 * y3 - x1 * z2 * y3 - y1 * x2 * z3 - z1 * y2 * x3;
		return detValue;
	}
	
	public float det()
	{
		return m11 * m22 * m33 * m44 + m12 * m23 * m34 * m41 + m13 * m24 * m31 * m42 + m14 * m21 * m32 * m43 -
			   m11 * m24 * m33 * m42 - m12 * m21 * m34 * m43 - m13 * m22 * m31 * m44 - m14 * m23 * m32 * m41;
	}
	
	public static Matrix4x4 mul(Matrix4x4 m1, Matrix4x4 m2)
	{
		Matrix4x4 m = new Matrix4x4();
		m.m11 = vector4x4(m1.m11, m1.m12, m1.m13, m1.m14, m2.m11, m2.m21, m2.m31, m2.m41);
		m.m21 = vector4x4(m1.m21, m1.m22, m1.m23, m1.m24, m2.m11, m2.m21, m2.m31, m2.m41);
		m.m31 = vector4x4(m1.m31, m1.m32, m1.m33, m1.m34, m2.m11, m2.m21, m2.m31, m2.m41);
		m.m41 = vector4x4(m1.m41, m1.m42, m1.m43, m1.m44, m2.m11, m2.m21, m2.m31, m2.m41);

		m.m12 = vector4x4(m1.m11, m1.m12, m1.m13, m1.m14, m2.m12, m2.m22, m2.m32, m2.m42);
		m.m22 = vector4x4(m1.m21, m1.m22, m1.m23, m1.m24, m2.m12, m2.m22, m2.m32, m2.m42);
		m.m32 = vector4x4(m1.m31, m1.m32, m1.m33, m1.m34, m2.m12, m2.m22, m2.m32, m2.m42);
		m.m42 = vector4x4(m1.m41, m1.m42, m1.m43, m1.m44, m2.m12, m2.m22, m2.m32, m2.m42);

		m.m13 = vector4x4(m1.m11, m1.m12, m1.m13, m1.m14, m2.m13, m2.m23, m2.m33, m2.m43);
		m.m23 = vector4x4(m1.m21, m1.m22, m1.m23, m1.m24, m2.m13, m2.m23, m2.m33, m2.m43);
		m.m33 = vector4x4(m1.m31, m1.m32, m1.m33, m1.m34, m2.m13, m2.m23, m2.m33, m2.m43);
		m.m43 = vector4x4(m1.m41, m1.m42, m1.m43, m1.m44, m2.m13, m2.m23, m2.m33, m2.m43);

		m.m14 = vector4x4(m1.m11, m1.m12, m1.m13, m1.m14, m2.m14, m2.m24, m2.m34, m2.m44);
		m.m24 = vector4x4(m1.m21, m1.m22, m1.m23, m1.m24, m2.m14, m2.m24, m2.m34, m2.m44);
		m.m34 = vector4x4(m1.m31, m1.m32, m1.m33, m1.m34, m2.m14, m2.m24, m2.m34, m2.m44);
		m.m44 = vector4x4(m1.m41, m1.m42, m1.m43, m1.m44, m2.m14, m2.m24, m2.m34, m2.m44);
		return m;
	}
	
	static float vector4x4(float x1, float y1, float z1, float w1, float x2, float y2, float z2, float w2)
	{
		float value = x1 * x2 + y1 * y2 + z1 * z2 + w1 * w2;
		return value;
	}
	
	static float vector4x4(Vector4 v1, Vector4 v2)
	{
		float value = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z + v1.w * v2.w;
		return value;
	}

	@Override
	public String toString() {
		return "Matrix4x4 [m11=" + m11 + ", m12=" + m12 + ", m13=" + m13 + ", m14=" + m14 + ", m21=" + m21 + ", m22="
				+ m22 + ", m23=" + m23 + ", m24=" + m24 + ", m31=" + m31 + ", m32=" + m32 + ", m33=" + m33 + ", m34="
				+ m34 + ", m41=" + m41 + ", m42=" + m42 + ", m43=" + m43 + ", m44=" + m44 + "]";
	}
}