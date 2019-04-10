package com.seer.softraster.test;

import com.seer.math.Quaternion;
import com.seer.math.Vector3;

public class QuaternionTest {

	public static void main(String[] args) {
		Quaternion q = Quaternion.angleAxis(new Vector3(0, 1, 0), 45);
		Quaternion p = Quaternion.angleAxis(new Vector3(0, -1, 1), 230);
		Quaternion qp = Quaternion.slerp(q, p, 0.2f);
		System.out.println(qp.toString());
		System.out.println(Quaternion.dot(q, p));
		System.out.println(Quaternion.angle(q, p));
	}
}