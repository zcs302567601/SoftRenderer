package com.seer.softraster.playjoy.d3;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;

import com.seer.math.Matrix4x4;
import com.seer.math.Vector2;
import com.seer.math.Vector3;
import com.seer.math.Vertex;
import com.seer.math.Vertex3;
import com.seer.softraster.BaseFrame;
import com.seer.softraster.Color;
import com.seer.softraster.Screen;
import com.seer.softraster.SoftRaster;
import com.seer.softraster.object.Plane;
import com.seer.softraster.object.Texture2d;
import com.seer.softraster.object.Texture2dMapping;
import com.seer.softraster.object.Triangle;
import com.seer.softraster.object.TriangleFace;

public class PlaneFrame extends BaseFrame implements MouseMotionListener{
	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new PlaneFrame().start();
	}

	PerspectiveCamera camera;
	ArrayList<TriangleFace> renderList;
	Plane plane;

	@Override
	public void init() {
		super.init();
//		this.addMouseMotionListener(this);
		renderList = new ArrayList<TriangleFace>();
		plane = new Plane(new Vector3(0, 0, 0));
		Vector3 camPos = new Vector3(0, -5, -5);
		camera = new PerspectiveCamera(camPos, -2, 2, -2, 2, 2, 100);
		try {
			Texture2d texture = new Texture2d("assets/WaterResource.png");
			Texture2dMapping.put("WaterResource.png", texture);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void render(SoftRaster raster) {
		clear(Color.Blue);
		renderList.clear();
		Matrix4x4 v = camera.lookAt(new Vector3(0, 0, 0));
		raster.begin();
		raster.enableDepthTest();
		addRenderList(v, plane);
		renderRenderList(raster);
	}
	
	void addRenderList(Matrix4x4 v, Plane plane)
	{
		Vertex3[] vertexes = plane.shader(v, camera.project, Screen.viewPortMatrix);
		Triangle[] triangles = plane.getTriangles();
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
			raster.fillPolygon(new Vertex[]{v1, v2, v3});
//			raster.FillTriangle(new Vertex[]{v1, v2, v3});
		}
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}
	
	float cameraZTheta;
	float cameraXTheta;
	
	Vector2 lastPoint = new Vector2();
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getXOnScreen();
		int y = e.getYOnScreen();
		
		boolean h = Math.abs(x - lastPoint.x) > Math.abs(y - lastPoint.y);
		if(h)
		{
			cameraZTheta += 0.01f;
			float r = 5;
			float px = (float) (r * Math.cos(cameraZTheta));
			float pz = (float) (r * Math.sin(cameraZTheta));
			camera.position.x = px;
			camera.position.z = pz;
		}
		else
		{
			cameraXTheta += 0.01f;
			float r = 5;
			float py = (float) (r * Math.cos(cameraXTheta));
			float pz = -(float) (r * Math.sin(cameraXTheta));
			camera.position.y = py;
			camera.position.z = pz;
		}
		lastPoint.x = x;
		lastPoint.y = y;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
}
