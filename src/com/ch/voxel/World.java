package com.ch.voxel;

import java.awt.Color;

import com.ch.Camera;
import com.ch.Shader;


/**
 * in the provided code is a 3D rendering engine that manages a grid of chunks, each
 * representing a small part of the game world. The World class has several methods
 * for updating and rendering the chunks, including `gen`, `render`, and `updateBlocks`.
 * The `gen` method is responsible for generating new chunks based on the current
 * position of the player, while the `render` method renders the chunks using a shader
 * and a camera. The `updateBlocks` method updates the blocks in each chunk based on
 * the player's position.
 * 
 * Overall, the World class seems to be the core component of the 3D rendering engine,
 * handling the management and rendering of the game world.
 */
public class World {

	private int x, y, z; // in chunks
			// private int cunk_max;
	private Chunk[][][] chunks; // TODO: unwrap
	private int W = 4, H = 2, D = 4;

	public World() {
		x = 0;
		y = 0;
		z = 0;
		chunks = new Chunk[W][H][D];
		gen();
	}
	
	/**
	 * iterates through a 3D grid of chunks, creating new chunks at each position and
	 * updating their blocks and transforming them into a gen model.
	 */
	private void gen() {
		for (int i = 0; i < W; i++)
			for (int j = 0; j < H; j++)
				for (int k = 0; k < D; k++) {
					chunks[i][j][k] = new Chunk(i - W / 2 + x, j - H / 2 + y, k - D / 2 + z);
					chunks[i][j][k].updateBlocks();
					chunks[i][j][k].toGenModel();
				}
	}

	/**
	 * updates the position of a `Chunk` instance based on its `x`, `y`, and `z` variables,
	 * and if necessary, generates a new chunk at the updated position.
	 * 
	 * @param x 2D coordinate of the chunk to be generated, and is used to determine which
	 * chunk to generate based on its position relative to the world boundaries.
	 * 
	 * @param y 2D coordinate of the block being updated, and it is used to determine
	 * which chunks to update based on their relative positions to the block being updated.
	 * 
	 * @param z 3D position of the block being generated, and is used to determine whether
	 * the block should be generated based on the current position or a new position based
	 * on the difference between the current position and the target position.
	 */
	public void updatePos(float x, float y, float z) {
		final int _x = (int) (x / Chunk.CHUNK_SIZE);
		final int _y = 0;//(int) (y / Chunk.CHUNK_SIZE);
		final int _z = (int) (z / Chunk.CHUNK_SIZE);

		if (this.x == _x && this.y == _y && this.z == _z) { // short circuit
															// check for any
															// change
			//System.out.println("hello");
			return;
		}
		
		int wx = this.x;
		int wy = this.y;
		int wz = this.z;
		
//		class internal_chunk_thread extends Thread {
//			
//		private int  wx, wy, wz;
//		
//		void set(int x, int y, int z) {
//			this.wx = x;
//			this.wy = y;
//			this.wz = z;
//		}
//			
//		public void run() {

		/*
		 * all logic is unwrapped because its more efficient.. while its a pain
		 * to code and read.. tradeoff taken :D
		 */

		/* dont think these cases occure
		if (this.x != _x && this.y != _y && this.z != _z) {
			if (this.x < _x) {
				if (this.y < _y) {
					if (this.z < _z) {
						
					} else {
						
					}
				} else {
					if (this.z < _z) {
						
					} else {
						
					}
				}
			} else {
				if (this.y < _y) {
					if (this.z < _z) {
						
					} else {
						
					}
				} else {
					if (this.z < _z) {
						
					} else {
						
					}
				}
			}
		} else if (this.x != _x && this.y != _y) {
			if (this.x < _x) {
				if (this.y < _y) {
					
				} else {
					
				}
			} else {
				if (this.y < _y) {
					
				} else {
					
				}
			}
		} else if (this.x != _x && this.z != _z) {
			if (this.x < _x) {
				if (this.z < _z) {
					
				} else {
					
				}
			} else {
				if (this.z < _z) {
					
				} else {
					
				}
			}
		} else if (this.y != _y && this.z != _z) {
			if (this.y < _y) {
				if (this.z < _z) {
					
				} else {
					
				}
			} else {
				if (this.z < _z) {
					
				} else {
					
				}
			}
		} else 
		*/
		if (wx != _x) {
			if (wx < _x) {
				
			} else {
				
			}
		} else if (wy != _y) {
			if (wy < _y) {
				
			} else {
				
			}
		} else if (wz != _z) {
			if (wz < _z) {
				int dif = _z - wz;
				if (dif > D) {
					wx = _x;
					wy = _y;
					wz = _z;
					gen();
					return;
				} else {
					Chunk[][][] n_chunks = new Chunk[W][H][D];
					for (int i = 0; i < W; i++)
						for (int j = 0; j < H; j++)
							for (int k = 0; k < D - 1; k++) {
								n_chunks[i][j][k] = chunks[i][j][k + 1];
							}
					for (int i = 0; i < W; i++)
						for (int j = 0; j < H; j++) {
							n_chunks[i][j][D - 1] = new Chunk(i - W / 2 + _x, j - H / 2 + _y, (D - 1) - D / 2 + _z);
							n_chunks[i][j][D - 1].updateBlocks();
							n_chunks[i][j][D - 1].toGenModel();
						}
					World.this.chunks = n_chunks;
				}
			} else {
				int dif = wz - _z;
				if (dif > D) {
					wx = _x;
					wy = _y;
					wz = _z;
					gen();
					return;
				} else {
					Chunk[][][] n_chunks = new Chunk[W][H][D];
					for (int i = 0; i < W; i++)
						for (int j = 0; j < H; j++)
							for (int k = 1; k < D; k++) {
								n_chunks[i][j][k] = chunks[i][j][k - 1];
							}
					for (int i = 0; i < W; i++)
						for (int j = 0; j < H; j++) {
							n_chunks[i][j][0] = new Chunk(i - W / 2 + _x, j - H / 2 + _y, 0 - D / 2 + _z);
							n_chunks[i][j][0].updateBlocks();
							n_chunks[i][j][0].toGenModel();
						}
					World.this.chunks = n_chunks;
				}
			}
		}
//		
//		}
//		
//		};
//		internal_chunk_thread t = new internal_chunk_thread();
//		t.set(this.x, this.y, this.z);
//		t.start();
		
		this.x = _x;
		this.y = _y;
		this.z = _z;
		
		/* welp... this logic sure looks aweful */
	}

	/**
	 * renders a scene using a shader and a camera. It iterates over each chunk in a 3D
	 * grid, rendering each chunk using the shader and passing the camera's view projection
	 * matrix as an uniform variable.
	 * 
	 * @param s 3D shader instance that renders the scene, and it is used to set the
	 * uniform values for the shader using its `unifrom*` methods.
	 * 
	 * 	- `Shader s`: Represents a shader object that defines the fragment shader's code.
	 * 	- `Camera c`: Represents a camera object that provides the viewpoint for rendering
	 * the 3D scene.
	 * 	- `W`, `H`, and `D`: Indicate the dimensions of the image to be rendered, respectively.
	 * 	- `chunks[][][]`: A two-dimensional array representing a 3D scene with chunks of
	 * varying sizes. Each chunk has three properties: `x`, `y`, and `z`, which represent
	 * its position in the 3D space.
	 * 	- `Chunk ch`: Represents an individual chunk in the 3D scene, with properties
	 * `x`, `y`, and `z`.
	 * 	- `Color cl`: Represents a color object created from the hash code of the chunk's
	 * position in the 3D space. The color is used to set the fragment shader's uniform
	 * values.
	 * 	- `r`, `g`, and `b`: Represents the red, green, and blue components of the color,
	 * respectively. These values are set using the `unifrom` method.
	 * 	- `MVP`: Represents a matrix object that represents the viewprojection transformation
	 * of the camera. This transformation is used to transform the chunk's position in
	 * 3D space into screen coordinates.
	 * 	- `modelMatrix`: Represents a matrix object that represents the modelview
	 * transformation of the chunk's position in 3D space. This transformation is used
	 * to transform the chunk's position in 3D space into the world coordinate system.
	 * 
	 * @param c 3D camera object, which is used to transform the model's vertices according
	 * to the camera's viewprojection matrix.
	 * 
	 * 	- `c`: A `Camera` object representing the camera used for rendering. Its properties
	 * include the viewport dimensions (`W`, `H`), field of view (`fov`), and projection
	 * matrix (`getViewProjection`).
	 * 
	 * The code inside the function iterates over the chunks in a 3D grid, applying the
	 * following operations to each chunk:
	 * 
	 * 1/ Creating a color object based on the chunk's position (`ch.x`, `ch.y`, `ch.z`)
	 * using hash code method.
	 * 2/ Calculating and storing the red, green, and blue values of the color object in
	 * the `r`, `g`, and `b` variables respectively.
	 * 3/ Setting the uniform values for the shader program using the `uniformf` method
	 * with the name `"color"` and passing the calculated values as arguments.
	 * 4/ Setting the uniform value for the modelview matrix (`MVP`) using the `unifromMat4`
	 * method with the name `"MVP"` and passing the product of the viewprojection matrix
	 * and the model matrix as an argument.
	 * 5/ Drawing the 3D model associated with the chunk using the `getModel().draw()` method.
	 */
	public void render(Shader s, Camera c) {
		for (int i = 0; i < W; i++)
			for (int j = 0; j < H; j++)
				for (int k = 0; k < D; k++) {
					Chunk ch = chunks[i][j][k];
					if (ch != null) { // just in case for now although i dont suspect it will ever be
	//					float r = (W - i) / (float) W;
	//					float g = j / (float) H;
	//					float b = k / (float) D;
						Color cl = new Color(("" + ch.x + ch.y + ch.z + (ch.x * ch.z) + (ch.y * ch.y)).hashCode());
						
						float r = cl.getRed() / 255f;
						float g = cl.getGreen() / 255f;
						float b = cl.getBlue() / 255f;
						s.uniformf("color", r, g, b);
						s.unifromMat4("MVP", (c.getViewProjection().mul(ch.getModelMatrix())));
						ch.getModel().draw();
					}
				}
	}

	// public

}
