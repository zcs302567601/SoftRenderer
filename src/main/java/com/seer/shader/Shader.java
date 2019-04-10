package com.seer.shader;

import com.seer.math.Matrix4x4;
import com.seer.math.Vector3;
import com.seer.math.Vertex3;
import com.seer.softraster.Color;

public interface Shader {
	public Vertex3[] vert(Vector3[] vertices, Matrix4x4 M, Matrix4x4 V, Matrix4x4 P, Matrix4x4 viewPortM);
	
	public Color fragment(V2F v);
}
