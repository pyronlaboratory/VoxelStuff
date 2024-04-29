package com.ch.math;

/**
 * represents a 4x4 matrix and provides various methods for manipulating it, including
 * setting and getting individual elements, transforming vectors, and multiplying
 * with another matrix. It also provides an array of floats to represent the matrix
 * data and methods to get or set specific elements of the matrix.
 */
public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}
	/**
	 * sets all elements of a Matrix4f object to identity values, i.e., elements are equal
	 * to their own row and column sums.
	 * 
	 * @returns a matrix with all elements initialized to their identity values.
	 * 
	 * 	- The `data` array is a 4D matrix with dimensions [width, height, depth, channels],
	 * where each element is a floating-point value representing a component of the matrix.
	 * 	- Each dimension of the matrix contains values that are either 1 or 0, indicating
	 * whether the corresponding component is set to its default value (0) or not (1).
	 * 	- The matrix has no translation, rotation, or scaling components, as these have
	 * been initialized to their default values.
	 * 	- The matrix is in the identity transformation group, meaning that it does not
	 * change the position or orientation of any point in space.
	 */
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
	 * Initializes a matrix with translation values for x, y, and z coordinates. It
	 * modifies the matrix elements to represent the translation vectors and returns the
	 * modified matrix reference.
	 * 
	 * @param x 3D translation amount in the x-axis direction.
	 * 
	 * @param y 2D translation component in the returned matrix, where it is assigned to
	 * the value of `data[1][1]`.
	 * 
	 * @param z 3rd coordinate of the translation vector and is used to set the z-component
	 * of the resulting matrix.
	 * 
	 * @returns a modified Matrix4f object with translated coordinates.
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
	 * Generates a rotation matrix based on three Euler angles (x, y, and z) using the
	 * Rodrigues formula. It returns a Matrix4f object representing the rotation.
	 * 
	 * @param x 3D rotation around the x-axis.
	 * 
	 * @param y 2D rotation angle around the y-axis, which is used to compute the rotation
	 * matrix for the x and z axes.
	 * 
	 * @param z 3D rotation axis around which the matrix will be rotated, and its value
	 * is used to calculate the cosine and sine of the angle of rotation in radians, which
	 * are then applied to the creation of the rotation matrix.
	 * 
	 * @returns a new Matrix4f object representing the rotation matrix.
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
	 * Sets the scale factor for a matrix, assigning values to the respective elements
	 * in the data array.
	 * 
	 * @param x 3D scale factor for the X-axis of the matrix.
	 * 
	 * @param y 2nd element of the scaling factor for the matrix, which is applied to the
	 * rows of the matrix.
	 * 
	 * @param z 3rd element of the scaling matrix and sets its value to the input parameter,
	 * which is used to scale the 3D coordinates of the object being transformed.
	 * 
	 * @returns a modified instance of the `Matrix4f` class.
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
	 * Initializes a matrix for a perspective projection, where the view frustum is defined
	 * by the field of view (fov), aspect ratio, and near and far distances.
	 * 
	 * @param fov field of view (FOV) of the perspective projection, which determines the
	 * angular range of vision visible in the generated matrix.
	 * 
	 * @param aspectRatio 2D aspect ratio of the view frustum, which determines the shape
	 * of the perspective projection and affects the field of view.
	 * 
	 * @param zNear near plane distance in the perspective projection matrix, which
	 * determines how far the image appears to be from the viewer.
	 * 
	 * @param zFar 4th coordinate of the perspective matrix and sets the distance from
	 * the eye point to the farthest point in the image, which is used in calculation of
	 * the Z-range.
	 * 
	 * @returns a `Matrix4f` object with pre-multiplied values for perspective projection.
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
	 * Sets up a 4x4 matrix representing an orthographic projection, with the specified
	 * parameters determining the size and orientation of the projection.
	 * 
	 * @param left left edge of the orthographic projection.
	 * 
	 * @param right right edge of the orthographic projection, which is used to calculate
	 * the values for the matrix's data elements.
	 * 
	 * @param bottom 2D coordinate of the bottom-left corner of the orthographic projection,
	 * which is used to calculate the height of the projection.
	 * 
	 * @param top 2D coordinate of the top edge of the orthographic projection, which is
	 * used to calculate the corresponding 3D coordinate in the matrix.
	 * 
	 * @param near near plane of the orthographic projection, and it determines the
	 * distance from the viewer to the near side of the projection.
	 * 
	 * @param far 3D distance from the viewer to the near end of the orthographic projection,
	 * and it is used to calculate the depth aspect of the projected matrix.
	 * 
	 * @returns a `Matrix4f` object with the necessary transformation values for orthographic
	 * projection.
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
	 * Generates a rotation matrix from three vectors: `forward`, `up`, and `r`. The
	 * resulting matrix rotates an object around its `forward` axis while maintaining its
	 * orientation relative to the `up` axis.
	 * 
	 * @param forward 3D direction that the rotation will be applied to.
	 * 
	 * @param up 2D plane that defines the rotation axis, which is used to create a
	 * rotation matrix that aligns with the given 3D vector.
	 * 
	 * @returns a Matrix4f object representing a rotation matrix based on the given vector
	 * inputs.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	/**
	 * Sets up a rotation matrix based on three input vectors representing the x, y, and
	 * z components of the rightward, upward, and forward directions. The resulting matrix
	 * is returned.
	 * 
	 * @param forward 3D vector that points in the direction of rotation, which is used
	 * to initialize the X component of the rotation matrix.
	 * 
	 * @param up 3D unit vector pointing upwards, which is used to initialize the rotation
	 * matrix's third column.
	 * 
	 * @param right 3D right vector of the rotation, which is used to construct the
	 * rotation matrix.
	 * 
	 * @returns a `Matrix4f` object representing a rotation matrix based on the provided
	 * vectors.
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
	 * Takes a `Vector3f` argument `r` and returns a new `Vector3f` object with transformed
	 * coordinates based on a set of coefficients `data`.
	 * 
	 * @param r 3D vector that transforms the output of the `transform()` function.
	 * 
	 * @returns a new vector with the result of multiplying each element of the input
	 * vector `r` by the corresponding elements of a given matrix `data`, and adding the
	 * resulting values.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}


	/**
	 * Takes a second matrix `r` as input and multiplies it element-wise with the current
	 * matrix, storing the result in the current matrix.
	 * 
	 * @param r 4x4 matrix to be multiplied with the current matrix, resulting in the
	 * updated matrix.
	 * 
	 * @returns a matrix with the product of the input matrices element-wise.
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
	/**
	 * returns an array of arrays, where each inner array represents a 4x4 matrix,
	 * containing the values of the `data` array at the specified indices.
	 * 
	 * @returns an array of arrays of floating-point numbers.
	 */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
	/**
	 * returns an array of floats representing the linear data at each point in a
	 * two-dimensional space.
	 * 
	 * @returns an array of 12 floats, representing the linear data for a 3x3 matrix.
	 */
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
	 * Returns the value stored at a specific position in an array, given the coordinates
	 * `x` and `y`.
	 * 
	 * @param x 1D position of a pixel in the image data within the range of [0, dimensions-1].
	 * 
	 * @param y 2nd dimension of the data array that is being accessed by the function.
	 * 
	 * @returns a floating-point value representing the value at the specified position
	 * in the 2D array `data`.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}
	/**
	 * Sets the value of the object's `data` field to the input parameter `data`.
	 * 
	 * @param data 2D array of float values that will be stored in the class instance
	 * variable `data`.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}
	/**
	 * Sets a value at a specific coordinate within an array of integers.
	 * 
	 * @param x 0-based index of a pixel in the grid, which is used to determine the
	 * specific location within the grid where the `value` parameter is assigned.
	 * 
	 * @param y 2nd dimension of the data array being manipulated, and it corresponds to
	 * the vertical position of the cell being updated with the provided value.
	 * 
	 * @param value 3D point value that is assigned to the corresponding position in the
	 * `data` array.
	 */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	/**
	 * transposes a given matrix by swapping rows and columns, then assigns the resulting
	 * matrix to the function's own `data` field.
	 */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
