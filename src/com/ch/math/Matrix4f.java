package com.ch.math;

public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}
	public Matrix4f initIdentity() {
		data[0][0] = 1;
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = 0;
		data[1][0] = 0;
		data[1][1] = 1;
		data[1][2] = 0;
		data[1][3] = 0;
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = 1;
		data[2][3] = 0;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}
	/**
	 * initializes a matrix with translation data, where `x`, `y`, and `z` are set to
	 * specified values, replacing previous values.
	 * 
	 * @param x 3D translation amount in the x-axis direction.
	 * 
	 * @param y 2D translation amount of the matrix in the x-axis direction, which is
	 * added to the previous value of the y-axis component of the matrix.
	 * 
	 * @param z 3rd dimension of the translation vector, which is added to the matrix's
	 * elements in the (2,2) position.
	 * 
	 * @returns a matrix that represents the translation of a point in 4D space.
	 */
	public Matrix4f initTranslation(float x, float y, float z) {
//        x = -x;
		data[0][0] = 1;
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = x;
		data[1][0] = 0;
		data[1][1] = 1;
		data[1][2] = 0;
		data[1][3] = y;
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = 1;
		data[2][3] = z;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}

	
	/**
	 * Initializes a rotation matrix from three Euler angles (x, y, z). It calculates the
	 * rotation matrix using the law of cosines and stores it in a `Matrix4f` object
	 * called `data`. The function returns a reference to the same `Matrix4f` object.
	 * 
	 * @param x 3D rotation around the x-axis and is used to calculate the z-component
	 * of the resulting matrix.
	 * 
	 * @param y 2D rotation axis around the z-axis, which is used to create a new rotation
	 * matrix based on the dot product of the 3D rotation axis and the x-axis, resulting
	 * in a 4x4 rotation matrix that represents the combined rotation.
	 * 
	 * @param z 3D rotation axis around which the rotation is performed, and it is used
	 * to calculate the rotational component of the matrix along that axis.
	 * 
	 * @returns a `Matrix4f` object representing the rotated coordinate system.
	 */
	public Matrix4f initRotation(float x, float y, float z) {
		Matrix4f rx = new Matrix4f();
		Matrix4f ry = new Matrix4f();
		Matrix4f rz = new Matrix4f();

		x = (float) Math.toRadians(x);
		y = (float) Math.toRadians(y);
		z = (float) Math.toRadians(z);

		rz.data[0][0] = (float) Math.cos(z);
		rz.data[0][1] = -(float) Math.sin(z);
		rz.data[0][2] = 0;
		rz.data[0][3] = 0;
		rz.data[1][0] = (float) Math.sin(z);
		rz.data[1][1] = (float) Math.cos(z);
		rz.data[1][2] = 0;
		rz.data[1][3] = 0;
		rz.data[2][0] = 0;
		rz.data[2][1] = 0;
		rz.data[2][2] = 1;
		rz.data[2][3] = 0;
		rz.data[3][0] = 0;
		rz.data[3][1] = 0;
		rz.data[3][2] = 0;
		rz.data[3][3] = 1;

		rx.data[0][0] = 1;
		rx.data[0][1] = 0;
		rx.data[0][2] = 0;
		rx.data[0][3] = 0;
		rx.data[1][0] = 0;
		rx.data[1][1] = (float) Math.cos(x);
		rx.data[1][2] = -(float) Math.sin(x);
		rx.data[1][3] = 0;
		rx.data[2][0] = 0;
		rx.data[2][1] = (float) Math.sin(x);
		rx.data[2][2] = (float) Math.cos(x);
		rx.data[2][3] = 0;
		rx.data[3][0] = 0;
		rx.data[3][1] = 0;
		rx.data[3][2] = 0;
		rx.data[3][3] = 1;

		ry.data[0][0] = (float) Math.cos(y);
		ry.data[0][1] = 0;
		ry.data[0][2] = -(float) Math.sin(y);
		ry.data[0][3] = 0;
		ry.data[1][0] = 0;
		ry.data[1][1] = 1;
		ry.data[1][2] = 0;
		ry.data[1][3] = 0;
		ry.data[2][0] = (float) Math.sin(y);
		ry.data[2][1] = 0;
		ry.data[2][2] = (float) Math.cos(y);
		ry.data[2][3] = 0;
		ry.data[3][0] = 0;
		ry.data[3][1] = 0;
		ry.data[3][2] = 0;
		ry.data[3][3] = 1;

		data = rz.mul(ry.mul(rx)).getData();

		return this;
	}

	/**
	 * sets the scale factors for a matrix, modifying its elements accordingly. It returns
	 * the modified matrix.
	 * 
	 * @param x 3D scale along the x-axis of the matrix.
	 * 
	 * @param y 2nd component of the scale vector in the initialization of the matrix's
	 * data.
	 * 
	 * @param z 2nd coordinate of the scaling factor for the matrix, which is applied to
	 * the 2D projection of the 3D vector in the resulting matrix.
	 * 
	 * @returns a reference to the same `Matrix4f` instance.
	 */
	public Matrix4f initScale(float x, float y, float z) {
		data[0][0] = x;
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = 0;
		data[1][0] = 0;
		data[1][1] = y;
		data[1][2] = 0;
		data[1][3] = 0;
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = z;
		data[2][3] = 0;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}


	/**
	 * initializes a matrix with values for a perspective projection, where the field of
	 * view (fov), aspect ratio, near and far distances are provided as inputs. The
	 * function returns the initialized matrix.
	 * 
	 * @param fov field of view (FOV) of the perspective projection, which determines the
	 * angle of the vertical line that appears to be parallel to the viewer in the resulting
	 * image.
	 * 
	 * @param aspectRatio 2D screen aspect ratio of the viewport, which is used to scale
	 * the perspective projection matrix accordingly.
	 * 
	 * @param zNear near plane distance of the perspective projection, which determines
	 * how far the near objects will appear from the viewer's position.
	 * 
	 * @param zFar 3D space far clip plane distance, which is used to compute the near
	 * and far clipping planes distances and ultimately affects the perspective projection
	 * of the output matrix.
	 * 
	 * @returns a matrix representing a perspective projection, with the appropriate
	 * values for the near and far planes.
	 */
	public Matrix4f initPerspective(float fov, float aspectRatio, float zNear, float zFar) {
		float tanHalfFOV = (float) Math.tan(Math.toRadians(fov) / 2);
		float zRange = zNear - zFar;

		data[0][0] = 1.0f / (tanHalfFOV * aspectRatio);
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = 0;
		data[1][0] = 0;
		data[1][1] = 1.0f / tanHalfFOV;
		data[1][2] = 0;
		data[1][3] = 0;
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = (-zNear - zFar) / zRange;
		data[2][3] = 2 * zFar * zNear / zRange;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 1;
		data[3][3] = 0;

		return this;
	}

	
	/**
	 * initializes a matrix for orthographic projection, setting its elements to preserve
	 * proportions and ensure consistency with the specified viewing parameters.
	 * 
	 * @param left left edge of the orthogonal projection in the X-axis.
	 * 
	 * @param right right side of the orthographic projection, which determines the scale
	 * factor for the x-axis of the resulting matrix.
	 * 
	 * @param bottom 2D coordinate of the bottom-left corner of the orthographic projection,
	 * which is used to determine the dimensions and angles of the projection matrix.
	 * 
	 * @param top 2D coordinate of the top edge of the orthographic projection, which
	 * determines the scale factor for the y-axis in the resulting matrix.
	 * 
	 * @param near near plane of the orthographic projection, and it is used to calculate
	 * the ratio of the far plane to the near plane in the function.
	 * 
	 * @param far 3D far distance of the orthographic projection, which determines how
	 * the 3D points are projected onto the image plane.
	 * 
	 * @returns a new instance of the `Matrix4f` class with orthographic projection parameters.
	 */
	public Matrix4f initOrthographic(float left, float right, float bottom, float top, float near, float far) {
		float width = right - left;
		float height = top - bottom;
		float depth = far - near;

		data[0][0] = 2 / width;
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = -(right + left) / width;
		data[1][0] = 0;
		data[1][1] = 2 / height;
		data[1][2] = 0;
		data[1][3] = -(top + bottom) / height;
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = -2 / depth;
		data[2][3] = -(far + near) / depth;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}

	
	/**
	 * generates a rotation matrix based on two input vectors: `forward` and `up`. The
	 * resulting matrix represents a rotation around the cross product of `forward` and
	 * `up`.
	 * 
	 * @param forward 3D direction of the object's motion.
	 * 
	 * @param up 2D plane that defines the orientation of the rotation, and it is used
	 * to create the cross product with the `forward` parameter to determine the rotation
	 * axis.
	 * 
	 * @returns a 4x4 matrix representing the rotation transformation.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	
	/**
	 * sets the rotation matrix based on three input vectors: forward, up, and right. It
	 * returns a rotated matrix.
	 * 
	 * @param forward 3D direction in which the rotation will be applied.
	 * 
	 * @param up 3D direction of the up vector in the rotation, which is used to generate
	 * the third row of the matrix.
	 * 
	 * @param right 3D right vector of the rotation, which is used to set the elements
	 * of the matrix's data array accordingly.
	 * 
	 * @returns a Matrix4f object representing the rotation transformation.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up, Vector3f right) {
		Vector3f f = forward;
		Vector3f r = right;
		Vector3f u = up;

		data[0][0] = r.getX();
		data[0][1] = r.getY();
		data[0][2] = r.getZ();
		data[0][3] = 0;
		data[1][0] = u.getX();
		data[1][1] = u.getY();
		data[1][2] = u.getZ();
		data[1][3] = 0;
		data[2][0] = f.getX();
		data[2][1] = f.getY();
		data[2][2] = f.getZ();
		data[2][3] = 0;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}

	
	/**
	 * takes a vector `r` as input and returns a transformed vector with the same components,
	 * but scaled by the values stored in the `data` array.
	 * 
	 * @param r 3D vector that transforms the output of the function.
	 * 
	 * @returns a new Vector3f object with the result of multiplying each component of
	 * the input vector `r` by the corresponding components of the input data matrix, and
	 * summing them together.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

	
	/**
	 * multiplies a given matrix by another matrix, resulting in a new matrix with the
	 * element-wise multiplication of the corresponding elements.
	 * 
	 * @param r 4x4 matrix that is multiplied with the current matrix to produce the
	 * result matrix.
	 * 
	 * @returns a matrix that represents the product of two 4x4 matrices.
	 */
	public Matrix4f mul(Matrix4f r) {
		Matrix4f res = new Matrix4f();

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				res.set(i, j, data[i][0] * r.get(0, j) + data[i][1] * r.get(1, j) + data[i][2] * r.get(2, j) + data[i][3] * r.get(3, j));
			}
		}

		return res;
	}

	
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
	
	public float[] getLinearData() {
		return new float[] {
			data[0][0],
			data[1][0],
			data[2][0],
			data[3][0],
			data[0][1],
			data[1][1],
			data[2][1],
			data[3][1],
			data[0][2],
			data[1][2],
			data[2][2],
			data[3][2],
			data[0][3],
			data[1][3],
			data[2][3],
			data[3][3],
		};
	}

	
	/**
	 * retrieves the value stored at a specific coordinate within an array of float values.
	 * 
	 * @param x 1D coordinate of the point in the grid where the data is retrieved.
	 * 
	 * @param y 2nd dimension of the data array being accessed by the function.
	 * 
	 * @returns a float value representing the data at the specified coordinates.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}

	
	/**
	 * sets the `data` member of the current object to the given array of floats.
	 * 
	 * @param data 2D array of floating-point values that will be assigned to the `data`
	 * field of the `SetM` method.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}

	
	/**
	 * updates a two-dimensional array 'data' by setting the element at the specified
	 * coordinate (x, y) to the given value.
	 * 
	 * @param x 0-based index of the array element being written to with the `value` parameter.
	 * 
	 * @param y 2D coordinate of the element in the array being manipulated, and it is
	 * used to specify the position within the array where the value should be stored.
	 * 
	 * @param value 3D point value that is to be assigned to the corresponding position
	 * in the `data` array.
	 */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
