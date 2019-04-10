package com.seer.softraster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.seer.softraster.object.Texture2dMapping;
import com.seer.math.Vector2;
import com.seer.math.Vector2f;
import com.seer.math.Vector3;
import com.seer.math.Vertex;
import com.seer.shader.Shader;
import com.seer.shader.V2F;
import com.seer.softraster.object.Texture2d;

public class SoftRaster 
{
	protected int minX;
	protected int minY;
	protected int width;
	protected int height;
	
	byte[] bytes;
	float[][] zBuffer;
	boolean bEnabledDepthTest;
	
	public SoftRaster(int minX, int minY, int width, int height, byte[] bytes)
	{
		this.minX = minX;
		this.minY = minY;
		this.width = width;
		this.height = height;
		this.bytes = bytes;
		zBuffer = new float[width][height];
	}
	
	
	public SoftRaster(int width, int height, byte[] bytes)
	{
		this.minX = 0;
		this.minY = 0;
		this.width = width;
		this.height = height;
		this.bytes = bytes;
		zBuffer = new float[width][height];
	}
	
	void clearZDepth()
	{
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				zBuffer[i][j] = 1;
			}
		}
	}
	
	public void begin()
	{
		if(bEnabledDepthTest)clearZDepth();
	}
	
	public void enableDepthTest()
	{
		bEnabledDepthTest = true;
	}
	
	public void disableDepthTest()
	{
		bEnabledDepthTest = false;
	}
	
	
	public void drawPixel(int x, int y, Color color)
	{
		y = height -1 - y;
		int index = (x + y * width) * 4;
		bytes[index] = color.getAlpha();
		bytes[index + 1] = color.getBlue();
		bytes[index + 2] = color.getGreen();
		bytes[index + 3] = color.getRed();
	}
	
//  //have float	
//	public void DrawLine2d(int x1, int y1, int x2, int y2, Color color)
//	{		
//		float error = 0;
//		float k = (y2 - y1)/(float)(x2 - x1);
//		boolean sweep = Math.abs(k) > 1;
//		if(sweep)
//		{
//			int temp = x1;
//			x1 = y1;
//			y1 = temp;
//			temp = x2;
//			x2 = y2;
//			y2 = temp;
//		}
//		
//		int start_x = x1;
//		int end_x = x2;
//		int start_y = y1;
//		int end_y = y2;
//		if(x1 > x2)
//		{
//			start_x = x2;
//			start_y = y2;
//			end_x = x1;
//			end_y = y1;
//		}
//		k = (end_y - start_y)/(float)(end_x - start_x);
//		int ystep = k > 0 ? 1 : -1;
//		k = Math.abs(k);
//		int y = start_y;
//		for(int x = start_x; x < end_x; x++)
//		{
//			if(sweep)
//			{
//				DrawPixel(y, x, color);
//			}
//			else
//			{
//				DrawPixel(x, y, color);
//			}
//			error += k;
//			if(error >= 0.5)
//			{
//				error -= 1.0;
//				y += ystep;
//			}
//		}
//	}
	
	public void drawLine2d(int x1, int y1, int x2, int y2, Color color)
	{		
		boolean sweep = Math.abs(y2 - y1) > Math.abs(x2 - x1);
		if(sweep)
		{
			int temp = x1;
			x1 = y1;
			y1 = temp;
			temp = x2;
			x2 = y2;
			y2 = temp;
		}
		
		int start_x = x1;
		int end_x = x2;
		int start_y = y1;
		int end_y = y2;
		if(x1 > x2)
		{
			start_x = x2;
			start_y = y2;
			end_x = x1;
			end_y = y1;
		}
		int k = end_y - start_y;///(float)(end_x - start_x);
		int ystep = k > 0 ? 1 : -1;
		k = Math.abs(k);
		int y = start_y;
		int distanceX = end_x - start_x;
		int halfX = distanceX/2;
		int error = 0;

		for(int x = start_x; x < end_x; x++)
		{
			if(sweep)
			{
				drawPixel(y, x, color);
			}
			else
			{
				drawPixel(x, y, color);
			}
			error += k;
			if(error >= halfX)
			{
				error -= distanceX;
				y += ystep;
			}
		}
	}
	
	public void drawLineInBound(Line2D line, Bound bound, Color color)
	{
		drawLineInBound(line.startX, line.startY, line.endX, line.endY, bound.minX + 1, bound.minY +1, bound.minX + bound.width - 1, bound.minY + bound.heigh - 1, color);
	}
	
	public void drawLineInBound(int startX, int startY, int endX, int endY, int minX, int minY, int maxX, int maxY, Color color)
	{
		int x1 = startX;
		int y1 = startY;
		int x2 = endX;
		int y2 = endY;
		while(true)
		{
			int code1 = getLinePointCode(minX, minY, maxX, maxY, x1, y1);
			int code2 = getLinePointCode(minX, minY, maxX, maxY, x2, y2);
				
			if((code1&code2) != 0)
			{
				break;
			}
			if(code1 == 0 && code2 == 0)
			{
				drawLine2d(x1, y1, x2, y2, color);
				break;
			}
			float k = (y2 - y1)/(float)(x2 - x1);
			boolean startPoint1 = code1 != 0? true : false;
			int code = startPoint1 ? code1 : code2;
			int new_x1 = 0, new_y1 = 0, new_x2 = 0, new_y2 = 0;
			if((code&1) != 0)
			{
				new_y1 = (int)(k * (minX - x1) + y1);
				new_x1 = minX;
				
				new_x2 = x1 < minX ? x2 : x1;
				new_y2 = x1 < minX ? y2 : y1;
			}
			else if((code&2) != 0)
			{
				new_y1 = (int)(k * (maxX - x1) + y1);
				new_x1 = maxX;
				
				new_x2 = x1 > maxX ? x2 : x1;
				new_y2 = x1 > maxX ? y2 : y1;
			}
			else if((code&4) != 0)
			{
				new_y1 = maxY;
				new_x1 = (int)(x1 + (maxY - y1)/k);
				
				new_x2 = y1 > maxY ? x2 : x1;
				new_y2 = y1 > maxY ? y2 : y1;
			}
			else if((code&8) != 0)
			{
				new_y1 = minY;//(int)(k * (maxX - x1) + y1);
				new_x1 = (int)(x1 + (minY - y1)/k);
				
				new_x2 = y1 < minY ? x2 : x1;
				new_y2 = y1 < minY ? y2 : y1;
			}
			x1 = new_x1;
			y1 = new_y1;
			x2 = new_x2;
			y2 = new_y2;
		}
	}

	int getLinePointCode(int minX, int minY, int maxX, int maxY, int x, int y)
	{
//		inside = 0;
//		left = 1;
//		right = 2;
//		top = 4;
//		bottom = 8;
		int code = 0;
		if(x < minX)
		{
			code |= 1;
		}
		else if(x > maxX)
		{
			code |= 2;
		}
		if(y < minY)
		{
			code |= 8;
		}
		else if(y > maxY)
		{
			code |= 4;
		}
		return code;
	}
	
	public class Edge implements Comparable<Edge>
	{
		public int maxY;
		public float deltaX;
		public float Xymin; //边的下端点x左边
		public Color color;
		public Color deltaColor;
		
		public float z;
		public float deltaZ;
		
		public float reciprocalZ;
		public float deltaReciprocalZ;
		
		public Vector2f uv;
		public Vector2f deltaUv;
		
		public Vector3 worldPos;
		public Vector3 deltaWorldPos;
		
		@Override
		public int compareTo(Edge other) {
			int code = 0;
			if(Xymin > other.Xymin)
			{
				return 1;
			}
			else if(Xymin < other.Xymin)
			{
				return -1;
			}
			if(code == 0)
			{
				if(deltaX > other.deltaX)
				{
					return 1;
				}
				else if(deltaX < other.deltaX)
				{
					return -1;
				}
			}
			return code;
		}
	}

	public void scanPolygon(Vertex[] vertexes)
	{
		HashMap<String, ArrayList<Edge>> et = new HashMap<String, ArrayList<Edge>>();
		int minY = vertexes[0].position.y;
		int maxY = vertexes[0].position.y;
		for(int i = 1; i < vertexes.length; i++)
		{
			if(vertexes[i].position.y > maxY)
			{
				maxY = vertexes[i].position.y;
			}
			if(vertexes[i].position.y < minY)
			{
				minY = vertexes[i].position.y;
			}
		}
		
		for(int i = 0; i < vertexes.length; i++)
		{
			Vertex p1 = vertexes[i];
			int next = i + 1;
			next = next > vertexes.length - 1 ? 0 : next;
			Vertex p2 = vertexes[next];
			if(p1.position.y == p2.position.y)
			{
				continue;
			}
			Vertex yMin = p1.position.y > p2.position.y ? p2 : p1;
			Vertex yMax = p1.position.y > p2.position.y ? p1 : p2;
		
			int index = yMin.position.y;
			String key = index + "";
			if(et.get(key) == null)et.put(key, new ArrayList<Edge>());
			Edge edge = new Edge();
			edge.maxY = yMax.position.y;
			edge.Xymin = yMin.position.x;
			float f = (1.0f/(yMax.position.y - yMin.position.y));
			edge.deltaX = (yMax.position.x - yMin.position.x) *f;
			
			edge.color = new Color(yMin.color);
			edge.deltaColor = Color.del(yMax.color, yMin.color);
			edge.deltaColor = edge.deltaColor.mul(f);
			
			edge.z = yMin.perPosition.z;
			edge.deltaZ = (yMax.perPosition.z - yMin.perPosition.z) * f;
			
			edge.uv = yMin.uv;
			edge.deltaUv = Vector2f.del(yMax.uv, yMin.uv).mul(f);
			
			et.get(key).add(edge);
		}
		
		ArrayList<Edge> aet = new ArrayList<Edge>();
		for(int y = minY; y <= maxY; y++)
		{
			if(et.containsKey(y + ""))
			{
				aet.addAll(et.get(y + ""));
			}
			Collections.sort(aet);
			int row = aet.size()/2;
			for(int i = 0; i < row; i++)
			{
				Edge a = aet.get(i * 2);
				Edge b = aet.get(i * 2 + 1);
				int start = (int) Math.ceil(a.Xymin);
				int end = (int) Math.floor(b.Xymin);
				
				Color t = Color.del(b.color, a.color);
				float deltaZ = b.z - a.z;

				float tf = start == end ? 0 :(1.0f/(b.Xymin - a.Xymin));
				for(int x = start; x <= end; x++)
				{
					if(bEnabledDepthTest)
					{
						float z = a.z + deltaZ * tf * (x - start);
						if(zBuffer[x][y] > z)
						{
							zBuffer[x][y] = z;
							drawPixel(x, y, (Color.add(a.color, Color.mul(t, tf * (x - start)))));
						}
					}
					else
					{
						drawPixel(x, y, (Color.add(a.color, Color.mul(t, tf * (x - start)))));
					}
				}
				a.color.add(a.deltaColor);
				b.color.add(b.deltaColor);
				
				a.z += a.deltaZ;
				b.z += b.deltaZ;
				
				a.uv.add(a.deltaUv);
				b.uv.add(b.deltaUv);
			}
			for(int i = aet.size() - 1; i >= 0; i--)
			{
				Edge edge = aet.get(i);
				if(edge.maxY == y + 1)
				{
					aet.remove(i);
				}
				else
				{
					edge.Xymin += edge.deltaX;
				}
			}
		}
	}
	

	public void fillPolygon(Vertex[] vertexes)
	{
		HashMap<String, ArrayList<Edge>> fillet = new HashMap<String, ArrayList<Edge>>();
		int minY = vertexes[0].position.y;
		int maxY = vertexes[0].position.y;
		for(int i = 1; i < vertexes.length; i++)
		{
			if(vertexes[i].position.y > maxY)
			{
				maxY = vertexes[i].position.y;
			}
			if(vertexes[i].position.y < minY)
			{
				minY = vertexes[i].position.y;
			}
		}
		
		for(int i = 0; i < vertexes.length; i++)
		{
			Vertex p1 = vertexes[i];
			int next = i + 1;
			next = next > vertexes.length - 1 ? 0 : next;
			Vertex p2 = vertexes[next];
			if(p1.position.y == p2.position.y)
			{
				continue;
			}
			Vertex yMin = p1.position.y > p2.position.y ? p2 : p1;
			Vertex yMax = p1.position.y > p2.position.y ? p1 : p2;
		
			int index = yMin.position.y;
			String key = index + "";
			if(fillet.get(key) == null)fillet.put(key, new ArrayList<Edge>());
			Edge edge = new Edge();
			edge.maxY = yMax.position.y;
			edge.Xymin = yMin.position.x;
			float f = (1.0f/(yMax.position.y - yMin.position.y));
			edge.deltaX = (yMax.position.x - yMin.position.x) *f;
			
			edge.color = new Color(yMin.color);
			edge.deltaColor = Color.del(yMax.color, yMin.color);
			edge.deltaColor = edge.deltaColor.mul(f);
			
			edge.z = yMin.perPosition.z;
			edge.deltaZ = (yMax.perPosition.z - yMin.perPosition.z) * f;

			edge.reciprocalZ = 1.0f/yMin.perPosition.w;
			edge.deltaReciprocalZ = (1.0f/yMax.perPosition.w - 1.0f/yMin.perPosition.w)*f;
			
			edge.uv = new Vector2f(yMin.uv).mul(1.0f/yMin.perPosition.w);
			edge.deltaUv = Vector2f.del(Vector2f.mul(yMax.uv, 1.0f/yMax.perPosition.w), Vector2f.mul(yMin.uv, 1.0f/yMin.perPosition.w)).mul(f);
			fillet.get(key).add(edge);
		}
		
		ArrayList<Edge> aet = new ArrayList<Edge>();
		for(int y = minY; y <= maxY; y++)
		{
			if(fillet.containsKey(y + ""))
			{
				aet.addAll(fillet.get(y + ""));
			}
			Collections.sort(aet);
			int row = aet.size()/2;
			for(int i = 0; i < row; i++)
			{
				Edge a = aet.get(i * 2);
				Edge b = aet.get(i * 2 + 1);
				int start = (int) Math.ceil(a.Xymin);
				int end = (int) Math.floor(b.Xymin);
				
				Color t = Color.del(b.color, a.color);
				float deltaZ = b.z - a.z;
				Vector2f deltaUv = Vector2f.del(b.uv, a.uv);
				
				float tf = start == end ? 0 :(1.0f/(b.Xymin - a.Xymin));
				
				float deltaReciprocalZ = b.reciprocalZ - a.reciprocalZ;
				
				for(int x = start; x <= end; x++)
				{
					boolean willRender = true;
					if(bEnabledDepthTest)
					{
						float z = a.z + deltaZ * tf * (x - start);
						if(zBuffer[x][y] > z)
						{
							zBuffer[x][y] = z;
							willRender = true;
						}
						else
						{
							willRender = false;
						}
					}
					if(willRender)
					{
						float reciprocalZ = a.reciprocalZ + deltaReciprocalZ * tf * (x - start);
						float tempU = a.uv.x + (x - start)* tf * deltaUv.x;
						float tempV = a.uv.y + (x - start)* tf * deltaUv.y;
						tempU /= reciprocalZ;
						tempV /= reciprocalZ;
//						//uv rule clamp?repeat?
						float u = tempU >= 1? tempU -1 : tempU;
						float v = tempV >= 1? tempV -1 : tempV;
						Color color = Color.add(a.color, Color.mul(t, tf * (x - start)));
						Texture2d texture = Texture2dMapping.get("WaterResource.png");
						fragment(x, y, u, v, color, texture);
					}
				}
				a.color.add(a.deltaColor);
				b.color.add(b.deltaColor);
				
				a.z += a.deltaZ;
				b.z += b.deltaZ;
				
				a.reciprocalZ += a.deltaReciprocalZ;
				b.reciprocalZ += b.deltaReciprocalZ;
				
				a.uv.add(a.deltaUv);
				b.uv.add(b.deltaUv);
			}
			for(int i = aet.size() - 1; i >= 0; i--)
			{
				Edge edge = aet.get(i);
				if(edge.maxY == y + 1)
				{
					aet.remove(i);
				}
				else
				{
					edge.Xymin += edge.deltaX;
				}
			}
		}
	}
	
	public void fillPolygon(Vertex[] vertexes, Shader shader)
	{
		HashMap<String, ArrayList<Edge>> fillet = new HashMap<String, ArrayList<Edge>>();
		int minY = vertexes[0].position.y;
		int maxY = vertexes[0].position.y;
		for(int i = 1; i < vertexes.length; i++)
		{
			if(vertexes[i].position.y > maxY)
			{
				maxY = vertexes[i].position.y;
			}
			if(vertexes[i].position.y < minY)
			{
				minY = vertexes[i].position.y;
			}
		}
		
		for(int i = 0; i < vertexes.length; i++)
		{
			Vertex p1 = vertexes[i];
			int next = i + 1;
			next = next > vertexes.length - 1 ? 0 : next;
			Vertex p2 = vertexes[next];
			if(p1.position.y == p2.position.y)
			{
				continue;
			}
			Vertex yMin = p1.position.y > p2.position.y ? p2 : p1;
			Vertex yMax = p1.position.y > p2.position.y ? p1 : p2;
		
			int index = yMin.position.y;
			String key = index + "";
			if(fillet.get(key) == null)fillet.put(key, new ArrayList<Edge>());
			Edge edge = new Edge();
			edge.maxY = yMax.position.y;
			edge.Xymin = yMin.position.x;
			float f = (1.0f/(yMax.position.y - yMin.position.y));
			edge.deltaX = (yMax.position.x - yMin.position.x) *f;
			
			edge.color = new Color(yMin.color);
			edge.deltaColor = Color.del(yMax.color, yMin.color);
			edge.deltaColor = edge.deltaColor.mul(f);
			
			edge.z = yMin.perPosition.z;
			edge.deltaZ = (yMax.perPosition.z - yMin.perPosition.z) * f;

			edge.reciprocalZ = 1.0f/yMin.perPosition.w;
			edge.deltaReciprocalZ = (1.0f/yMax.perPosition.w - 1.0f/yMin.perPosition.w)*f;
			
			edge.uv = new Vector2f(yMin.uv).mul(1.0f/yMin.perPosition.w);
			edge.deltaUv = Vector2f.del(Vector2f.mul(yMax.uv, 1.0f/yMax.perPosition.w), Vector2f.mul(yMin.uv, 1.0f/yMin.perPosition.w)).mul(f);
			
			edge.worldPos = new Vector3(yMin.worldPosition).mul(1.0f/yMin.perPosition.w);
			edge.deltaWorldPos = Vector3.del(Vector3.mul(yMax.worldPosition, 1.0f/yMax.perPosition.w), Vector3.mul(yMin.worldPosition, 1.0f/yMin.perPosition.w)).mul(f);
			fillet.get(key).add(edge);
		}
		
		ArrayList<Edge> aet = new ArrayList<Edge>();
		for(int y = minY; y <= maxY; y++)
		{
			if(fillet.containsKey(y + ""))
			{
				aet.addAll(fillet.get(y + ""));
			}
			Collections.sort(aet);
			int row = aet.size()/2;
			for(int i = 0; i < row; i++)
			{
				Edge a = aet.get(i * 2);
				Edge b = aet.get(i * 2 + 1);
				int start = (int) Math.ceil(a.Xymin);
				int end = (int) Math.floor(b.Xymin);
				
				Color t = Color.del(b.color, a.color);
				float deltaZ = b.z - a.z;
				Vector2f deltaUv = Vector2f.del(b.uv, a.uv);
				Vector3 deltaWorldPos = Vector3.del(b.worldPos, a.worldPos);
				
				float tf = start == end ? 0 :(1.0f/(b.Xymin - a.Xymin));
				
				float deltaReciprocalZ = b.reciprocalZ - a.reciprocalZ;
				
				for(int x = start; x <= end; x++)
				{
					boolean willRender = true;
					if(bEnabledDepthTest)
					{
						float z = a.z + deltaZ * tf * (x - start);
						if(zBuffer[x][y] > z)
						{
							zBuffer[x][y] = z;
							willRender = true;
						}
						else
						{
							willRender = false;
						}
					}
					if(willRender)
					{
						float reciprocalZ = a.reciprocalZ + deltaReciprocalZ * tf * (x - start);
						float tempU = a.uv.x + (x - start)* tf * deltaUv.x;
						float tempV = a.uv.y + (x - start)* tf * deltaUv.y;
						tempU /= reciprocalZ;
						tempV /= reciprocalZ;
//						//uv rule clamp?repeat?
						float u = tempU >= 1? tempU -1 : tempU;
						float v = tempV >= 1? tempV -1 : tempV;
						Color color = Color.add(a.color, Color.mul(t, tf * (x - start)));
						Texture2d texture = Texture2dMapping.get("WaterResource.png");

						V2F v2f = new V2F();
						v2f.color = color;
						v2f.sv_position = new Vector2(x, y);
						v2f.texture = texture;
						v2f.uv0 = new Vector2f(u, v);
						v2f.worldPosition = Vector3.add(a.worldPos, Vector3.mul(deltaWorldPos, (x - start) * tf));
						v2f.worldPosition.mul(1.0f/reciprocalZ);
						v2f.normal = new Vector3(0, 0, -1);
						color = shader.fragment(v2f);
						fragment(x, y, u, v, color, texture);

					}
				}
				a.color.add(a.deltaColor);
				b.color.add(b.deltaColor);
				
				a.z += a.deltaZ;
				b.z += b.deltaZ;
				
				a.reciprocalZ += a.deltaReciprocalZ;
				b.reciprocalZ += b.deltaReciprocalZ;
				
				a.uv.add(a.deltaUv);
				b.uv.add(b.deltaUv);
				
				a.worldPos.add(a.deltaWorldPos);
				b.worldPos.add(b.deltaWorldPos);
			}
			for(int i = aet.size() - 1; i >= 0; i--)
			{
				Edge edge = aet.get(i);
				if(edge.maxY == y + 1)
				{
					aet.remove(i);
				}
				else
				{
					edge.Xymin += edge.deltaX;
				}
			}
		}
	}
	
	
	public void fragment(int x, int y, float u, float v, Color color, Texture2d texture)
	{
		Color col = texture.sample(u, v).mul(color);
		drawPixel(x, y, col);
	}
	
	public void drawBound(Vertex[] vertexes, Color color)
	{
		if(vertexes.length != 4)
		{
			return;
		}
		drawLine2d(vertexes[0].position.x, vertexes[0].position.y, vertexes[1].position.x, vertexes[1].position.y, color);
		drawLine2d(vertexes[1].position.x, vertexes[1].position.y, vertexes[2].position.x, vertexes[2].position.y, color);
		drawLine2d(vertexes[2].position.x, vertexes[2].position.y, vertexes[3].position.x, vertexes[3].position.y, color);
		drawLine2d(vertexes[3].position.x, vertexes[3].position.y, vertexes[0].position.x, vertexes[0].position.y, color);

	}
	
	public void drawTriangle(Vertex[] vertexes)
	{
		int minX = vertexes[0].position.x;
		int maxX = vertexes[0].position.x;
		int minY = vertexes[0].position.y;
		int maxY = vertexes[0].position.y;
		for(int i = 1; i < vertexes.length; i++)
		{
			if(minX > vertexes[i].position.x)
			{
				minX = vertexes[i].position.x;
			}
			if(maxX < vertexes[i].position.x)
			{
				maxX = vertexes[i].position.x;
			}
			if(minY > vertexes[i].position.y)
			{
				minY = vertexes[i].position.y;
			}
			if(maxY < vertexes[i].position.y)
			{
				maxY = vertexes[i].position.y;
			}
		}
		
		for(int x = minX; x <= maxX; x++)
		{
			for(int y = minY; y < maxY; y++)
			{
				float a = 0, b = 0, c = 0;
				//to do for (x, y)
				
				a = compute(vertexes[1].position, vertexes[2].position, x, y) * 1.0f/compute(vertexes[1].position, vertexes[2].position, vertexes[0].position.x, vertexes[0].position.y);
				b = compute(vertexes[2].position, vertexes[0].position, x, y) * 1.0f/compute(vertexes[2].position, vertexes[0].position, vertexes[1].position.x, vertexes[1].position.y);
				c = compute(vertexes[0].position, vertexes[1].position, x, y) * 1.0f/compute(vertexes[0].position, vertexes[1].position, vertexes[2].position.x, vertexes[2].position.y);

				if(a>0 && a<1 && b>0 && b<1 && c>0 && c<1)
				{
					float z = vertexes[0].perPosition.z * a + vertexes[1].perPosition.z * b + vertexes[2].perPosition.z * c;
					if(bEnabledDepthTest)
					{
						if(zBuffer[x][y] > z)
						{
							zBuffer[x][y] = z;
							Color colora = Color.mul(vertexes[0].color, a);
							Color colorb = Color.mul(vertexes[1].color, b);
							Color colorc = Color.mul(vertexes[2].color, c);
							Color color = Color.add(colora, colorb).add(colorc);
							drawPixel(x, y, color);
						}
					}
					else
					{
						Color colora = Color.mul(vertexes[0].color, a);
						Color colorb = Color.mul(vertexes[1].color, b);
						Color colorc = Color.mul(vertexes[2].color, c);
						Color color = Color.add(colora, colorb).add(colorc);
						drawPixel(x, y, color);
					}
				}
			}
		}
	}
	
	public void fillTriangle(Vertex[] vertexes)
	{
		int minX = vertexes[0].position.x;
		int maxX = vertexes[0].position.x;
		int minY = vertexes[0].position.y;
		int maxY = vertexes[0].position.y;
		for(int i = 1; i < vertexes.length; i++)
		{
			if(minX > vertexes[i].position.x)
			{
				minX = vertexes[i].position.x;
			}
			if(maxX < vertexes[i].position.x)
			{
				maxX = vertexes[i].position.x;
			}
			if(minY > vertexes[i].position.y)
			{
				minY = vertexes[i].position.y;
			}
			if(maxY < vertexes[i].position.y)
			{
				maxY = vertexes[i].position.y;
			}
		}
		
		for(int x = minX; x <= maxX; x++)
		{
			for(int y = minY; y < maxY; y++)
			{
				float a = 0, b = 0, c = 0;
				//to do for (x, y)
				
				a = compute(vertexes[1].position, vertexes[2].position, x, y) * 1.0f/compute(vertexes[1].position, vertexes[2].position, vertexes[0].position.x, vertexes[0].position.y);
				b = compute(vertexes[2].position, vertexes[0].position, x, y) * 1.0f/compute(vertexes[2].position, vertexes[0].position, vertexes[1].position.x, vertexes[1].position.y);
				c = compute(vertexes[0].position, vertexes[1].position, x, y) * 1.0f/compute(vertexes[0].position, vertexes[1].position, vertexes[2].position.x, vertexes[2].position.y);

				if(a>0 && a<1 && b>0 && b<1 && c>0 && c<1)
				{
					float z = vertexes[0].perPosition.z * a + vertexes[1].perPosition.z * b + vertexes[2].perPosition.z * c;
					if(bEnabledDepthTest)
					{
						if(zBuffer[x][y] > z)
						{
							zBuffer[x][y] = z;
							Vector2f va = Vector2f.mul(vertexes[0].uv, a/vertexes[0].perPosition.w);
							Vector2f vb = Vector2f.mul(vertexes[1].uv, b/vertexes[1].perPosition.w);
							Vector2f vc = Vector2f.mul(vertexes[2].uv, c/vertexes[2].perPosition.w);
							float ls = a/vertexes[0].perPosition.w + b/vertexes[1].perPosition.w + c/vertexes[2].perPosition.w;
							Vector2f uv = va.add(vb).add(vc);
							uv = uv.mul(1/ls);
							Texture2d texture = Texture2dMapping.get("WaterResource.png");
							Color color = texture.sample(uv.x, uv.y);
							drawPixel(x, y, color);
						}
					}
				}
			}
		}
	}
	
	public int compute(Vector2 p0, Vector2 p1, int x, int y)
	{
		return (p0.y - p1.y)*x + (p1.x - p0.x) * y + p0.x * p1.y - p1.x * p0.y;
	}
}