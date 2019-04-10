package com.seer.softraster.playjoy.d3;

import java.io.IOException;
import java.util.ArrayList;

import com.seer.math.Matrix4x4;
import com.seer.math.Vector3;
import com.seer.math.Vertex;
import com.seer.math.Vertex3;
import com.seer.softraster.BaseFrame;
import com.seer.softraster.Color;
import com.seer.softraster.Screen;
import com.seer.softraster.SoftRaster;
import com.seer.softraster.object.Box;
import com.seer.softraster.object.Texture2d;
import com.seer.softraster.object.Texture2dMapping;
import com.seer.softraster.object.Triangle;
import com.seer.softraster.object.TriangleFace;

public class PerspectiveFrame extends BaseFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new PerspectiveFrame().start();
	}

	PerspectiveCamera camera;
	ArrayList<Box> boxes;
	ArrayList<TriangleFace> renderList;

	@Override
	public void init() {
		super.init();
		renderList = new ArrayList<TriangleFace>();
		boxes = new ArrayList<Box>();
		for(int i = 0; i < 10; i++)
		{
			boxes.add(new Box(new Vector3(0 + i, -5 + i, 10 + i * 8)));
		}
		Vector3 camPos = new Vector3(0, 0, -3);
		camera = new PerspectiveCamera(camPos, -1, 1, -1, 1, 2, 100);
		try {
			Texture2d texture = new Texture2d("assets/WaterResource.png");
			Texture2dMapping.put("WaterResource.png", texture);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void render(SoftRaster raster) {
		clear(Color.BLACK);
		renderList.clear();
		Matrix4x4 v = camera.lookAt(new Vector3(0, 0, 0));
		raster.begin();
		raster.enableDepthTest();
		for(int i = 0; i < boxes.size(); i++)
		{
			addRenderList(v, boxes.get(i));
		}
		renderRenderList(raster);
	}
	
	void addRenderList(Matrix4x4 v, Box box)
	{
		Vertex3[] vertexes = box.vert(v, camera.project, Screen.viewPortMatrix);
		Triangle[] triangles = box.getTriangles();
		for(int i = 0; i < triangles.length; i++)
		{
			Triangle triangle = triangles[i];
			int index1 = triangle.indices[0];
			int index2 = triangle.indices[1];
			int index3 = triangle.indices[2];
			TriangleFace face = new TriangleFace(new Vertex3[]{vertexes[index1], vertexes[index2], vertexes[index3]}, triangle.uv);
			renderList.add(face);
		}
	}
	
	void renderRenderList(SoftRaster raster)
	{
		for(int i = 0;i < renderList.size(); i++)
		{
			TriangleFace face = renderList.get(i);
			Vertex v1 = new Vertex(face.vertices[0].screenPos, face.uv[0], face.vertices[0].perspectivePosition, face.vertices[0].color);
			Vertex v2 = new Vertex(face.vertices[1].screenPos, face.uv[1], face.vertices[1].perspectivePosition, face.vertices[1].color);
			Vertex v3 = new Vertex(face.vertices[2].screenPos, face.uv[2], face.vertices[2].perspectivePosition, face.vertices[2].color);
			raster.fillTriangle(new Vertex[]{v1, v2, v3});
		}
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}
}