package com.seer.math;

// cos(0.5 * angle), sin(0.5 * angle) * i
public class Quaternion {
	
	float x, y, z, w;
	public Quaternion()
	{
		x = 0;
		y = 0;
		z = 0;
		w = 1;
	}
	
	public Quaternion(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public void normalize()
	{
		float magnitude = (float) Math.sqrt(x * x + y * y + z * z + w * w);
		this.x /= magnitude;
		this.y /= magnitude;
		this.z /= magnitude;
		this.w /= magnitude;
	}
	
	public Quaternion opposite()
	{
		float mag = x * x + y * y + z * z + w * w;
		Quaternion q = new Quaternion();
		q.x = -x / mag;
		q.y = -y / mag;
		q.z = -z / mag;
		q.w = w / mag;
		return q;
	}
	
	public Vector3 getAxis()
	{
		double sinHalfAngle = Math.sqrt( 1- w * w);
		return new Vector3((float)(x/sinHalfAngle), (float)(y/sinHalfAngle), (float)(z/sinHalfAngle));
	}
	
	public float getAngle()
	{
		double angle = (Math.acos(w) * 2);
		return (float) (angle * 180.0f/Math.PI);
	}
	
	public float sqrMagnitude()
	{
		return (float) Math.sqrt(magnitude());
	}
	
	public float magnitude()
	{
		return x * x + y * y + z * z + w * w;
	}
	
//	public Vector3 EulerAngles()
//	{
//		double sinHalfAngle = Math.sqrt( 1- w * w);
//		float x1 = (float) (x/sinHalfAngle);
//		float y1 = (float) (y/sinHalfAngle);
//		float z1 = (float) (z/sinHalfAngle);
//		
//		return Vector3.zero;
//	}
	
	public static Quaternion angleAxis(Vector3 axis, double angle)
	{
		axis = axis.normalize();
		Quaternion quaternion = new Quaternion();
		double radian = Math.PI * (angle/180.0f);
		double cosHalfAngle = Math.cos(radian * 0.5f);
		double sinHalfAngle = Math.sin(radian * 0.5f);
		quaternion.x = (float) (axis.x * sinHalfAngle);
		quaternion.y = (float) (axis.y * sinHalfAngle);
		quaternion.z = (float) (axis.z * sinHalfAngle);
		quaternion.w = (float) cosHalfAngle;
		return quaternion;
	}
	
	public static Quaternion mul(Quaternion a, Quaternion b)
	{
		Quaternion quaternion = new Quaternion();
		quaternion.w = a.w * b.w - a.x * b.x - a.y * b.y - a.z * b.z;
		quaternion.x = a.w * b.x + b.w * a.x + a.y * b.z - b.y * a.z;
		quaternion.y = a.w * b.y + b.w * a.y + a.z * b.x - b.z * a.x;
		quaternion.z = a.w * b.z + b.w * a.z + a.x * b.y - b.x * a.y;
		return quaternion;
	}
	
	public static Vector3 mul(Quaternion a, Vector3 point)
	{
		Vector3 target = Vector3.zero;
		Quaternion qp = new Quaternion();
		qp.w = -a.x * point.x - a.y * point.y - a.z * point.z;
		qp.x = a.w * point.x + a.y * point.z - point.y * a.z;
		qp.y = a.w * point.y + a.z * point.x - point.z * a.x;
		qp.z = a.w * point.z + a.x * point.y - point.x * a.y;
		Quaternion aOpposite = a.opposite();
		Quaternion qpq = Quaternion.mul(qp, aOpposite);
		target.x = qpq.x;
		target.y = qpq.y;
		target.z = qpq.z;
		return target;
	}
	
	public static Quaternion mul(Quaternion a, float scale)
	{
		Quaternion target = new Quaternion();
		target.x = a.x * scale;
		target.y = a.y * scale;
		target.z = a.z * scale;
		target.w = a.w * scale;
		return target;
	}
	
	public static float dot(Quaternion a, Quaternion b)
	{
		float dot = a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w;
		return dot;
	}
	
	public static float angle(Quaternion a, Quaternion b)
	{
		float cosAngle = dot(a, b)/(a.sqrMagnitude() * b.sqrMagnitude());
		cosAngle = Math.abs(cosAngle);
		float angle = (float) Math.acos(cosAngle);
		angle = (float) (angle * 180 /Math.PI);
		return angle * 2;
	}
	
	public static Quaternion slerp(Quaternion a, Quaternion b, float t)
	{
		if(t <= 0)
		{
			return a;
		}
		if(t >= 1)
		{
			return b;
		}
		int dotValue = 1;
		float cosAngle = dot(a, b)/(a.sqrMagnitude() * b.sqrMagnitude());
		dotValue = cosAngle < 0 ? -1 : 1;
		cosAngle = Math.abs(cosAngle);
		float angle = (float) Math.acos(cosAngle);
		float sinAngle = (float) Math.sin(angle);
		float sinAngleLeft = (float) Math.sin((1 - t) * angle);
		float sinAngleRight = (float) Math.sin(t * angle);
		float left = 0;
		float right = 0;
		if(sinAngle < 0.00001f)
		{
			left = 1 - t;
			right = t;
		}
		else
		{
			left = sinAngleLeft/sinAngle;
			right = sinAngleRight/sinAngle;
		}
		Quaternion target = new Quaternion();
		target.x = left * a.x + right * b.x * dotValue;
		target.y = left * a.y + right * b.y * dotValue;
		target.z = left * a.z + right * b.z * dotValue;
		target.w = left * a.w + right * b.w * dotValue;
		return target;
	}
	
	@Override
	public String toString() {
		return "Quaternion [x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + "]";
	}
}