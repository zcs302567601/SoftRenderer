package com.seer.softraster.test;

import com.seer.math.Matrix4x4;
import com.seer.math.Vector4;

public class MatrixTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vector4 v = new Vector4(1, 1, 2, 0);
		Matrix4x4 m1 = new Matrix4x4();
		m1.setRow1(new Vector4(1,0, 0, 1));
		m1.setRow2(new Vector4(0,1, 1, 1));
		m1.setRow3(new Vector4(0,0, 1, 1));
		m1.setRow4(new Vector4(0,0, 0, 1));
		
		Matrix4x4 m2 = new Matrix4x4();
		m2.setRow1(new Vector4(1,1, 0, 1));
		m2.setRow2(new Vector4(0,1, 1, 1));
		m2.setRow3(new Vector4(1,0, 1, 1));
		m2.setRow4(new Vector4(0,1, 0, 1));
		
		Matrix4x4 m3 = new Matrix4x4();
		m3.setRow1(new Vector4(1,1, 1, 1));
		m3.setRow2(new Vector4(0,0, 1, 1));
		m3.setRow3(new Vector4(1,1, 0, 1));
		m3.setRow4(new Vector4(0,1, 1, 1));
		
		Vector4 v1 = m1.mul(v);
		Vector4 v2 = m2.mul(v1);
		Vector4 v3 = m3.mul(v2);
		System.out.println(v3);
		Matrix4x4 mvp = m3.mul(m2).mul(m1);
		System.out.println(mvp.mul(v));
		System.out.println(m3.mul(m2));
	}

}
