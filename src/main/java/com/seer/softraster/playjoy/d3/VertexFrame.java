package com.seer.softraster.playjoy.d3;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.seer.math.Matrix4x4;
import com.seer.math.Vector2;
import com.seer.math.Vector3;
import com.seer.math.Vertex;
import com.seer.math.Vertex3;
import com.seer.softraster.BaseFrame;
import com.seer.softraster.Color;
import com.seer.softraster.Screen;
import com.seer.softraster.SoftRaster;
import com.seer.softraster.object.Box;
import com.seer.softraster.object.Triangle;

public class VertexFrame extends BaseFrame implements MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new VertexFrame().start();
	}
	
	OrthogonalCamera camera;
	Box box;

	@Override
	public void init() {
		super.init();
		this.addMouseMotionListener(this);
		box = new Box(new Vector3(0, 0, 0));
		Vector3 camPos = new Vector3(0, 0, -2.5f);
		camera = new OrthogonalCamera(camPos, -2, 2, -2, 2, 2, 10);
	}
	
	@Override
	public void render(SoftRaster raster) {
		clear(Color.BLACK);
		Matrix4x4 v = camera.lookAt(new Vector3(0, 0, 0));
		Vertex3[] vertexes = box.vert(v, camera.project, Screen.viewPortMatrix);
		Triangle[] triangles = box.getTriangles();
		for(int i = 0; i < triangles.length; i++)
		{
			Triangle triangle = triangles[i];
			int index1 = triangle.indices[0];
			int index2 = triangle.indices[1];
			int index3 = triangle.indices[2];
			Vertex v1 = new Vertex(vertexes[index1].screenPos, vertexes[index1].perspectivePosition, vertexes[index1].color);
			Vertex v2 = new Vertex(vertexes[index2].screenPos, vertexes[index2].perspectivePosition, vertexes[index2].color);
			Vertex v3 = new Vertex(vertexes[index3].screenPos, vertexes[index3].perspectivePosition, vertexes[index3].color);
			raster.scanPolygon(new Vertex[]{v1, v2, v3});
		}
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
//		box.Update(deltaTime);
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
			float px = (float) (r * Math.cos(cameraXTheta));
			float pz = (float) (r * Math.sin(cameraXTheta));
			camera.position.y = px;
			camera.position.z = pz;
		}
		lastPoint.x = x;
		lastPoint.y = y;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
}