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
	 * initializes a matrix with translation coordinates x, y, and z. It sets the
	 * corresponding elements of the matrix to the appropriate values and returns the
	 * modified matrix.
	 * 
	 * @param x 3D translation vector component along the x-axis.
	 * 
	 * @param y 2nd element of the translation vector, which is added to the upper-left
	 * corner of the matrix's data array.
	 * 
	 * @param z 3rd dimension of the translation vector, which is added to the current
	 * position of the matrix.
	 * 
	 * @returns a reference to the original matrix object, which has been modified to
	 * represent the translation.
	 * 
	 * 	- The `Matrix4f` object is returned as the output, which represents a 4x4 homogeneous
	 * transformation matrix.
	 * 	- The elements of the matrix are set to specific values based on the input
	 * parameters `x`, `y`, and `z`.
	 * 	- The matrix data structure consists of four rows and four columns, with each
	 * element representing a component of the transformation matrix.
	 * 	- The matrix is initialized with the identity matrix, with the exception of the
	 * last row and column, which are set to the input values `x`, `y`, and `z` respectively.
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
	 * creates a rotation matrix based on three Euler angles (x, y, z) and returns the
	 * resulting rotation matrix as a new Matrix4f object.
	 * 
	 * @param x azimuth angle of rotation, which is used to calculate the rotation matrix
	 * for the x-axis.
	 * 
	 * @param y 2D rotation angle around the z-axis, which is used to calculate the
	 * rotation matrix rz.
	 * 
	 * @param z 3D rotation axis around the z-axis, which is used to create a rotation
	 * matrix by multiplying it with other rotation matrices.
	 * 
	 * @returns a new `Matrix4f` object representing the rotation matrix based on the
	 * provided angles.
	 * 
	 * 	- The `data` field is an instance of `Matrix4f`, which represents a 4x4 homogeneous
	 * transformation matrix.
	 * 	- The elements of the matrix represent the rotation and scaling components of the
	 * overall transformation.
	 * 	- The matrices `rx`, `ry`, and `rz` are created by rotating the origin around the
	 * x, y, and z axes, respectively.
	 * 	- The `mul()` method is used to multiply the matrices element-wise, resulting in
	 * a 4x4 matrix that represents the combined rotation and scaling.
	 * 	- The `getData()` method is used to retrieve the elements of the resulting matrix
	 * as a flattened array.
	 * 
	 * In summary, the `initRotation` function returns a transformed matrix that represents
	 * the result of rotating the origin around three axes by a specified amount, followed
	 * by a scaling transformation.
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
	 * sets the scale factors for a matrix, by setting the corresponding elements of the
	 * matrix to the input values x, y, and z.
	 * 
	 * @param x 2D scaling factor for the x-axis of the matrix.
	 * 
	 * @param y 2nd component of the scaling vector, which is applied to the matrix's 2nd
	 * row.
	 * 
	 * @param z 2nd dimension of the scale factor for the matrix, which is multiplied by
	 * the corresponding element in the matrix to transform the coordinate system.
	 * 
	 * @returns a reference to the original matrix.
	 * 
	 * The returned object is a `Matrix4f` instance, indicating that it is a 4x4 matrix
	 * with floating-point elements.
	 * 
	 * The `data` array of the returned matrix contains the scale factors for each
	 * dimension, where the first element represents the x-axis, the second element
	 * represents the y-axis, and the third element represents the z-axis. Each element
	 * is a single float value representing the scaling factor for that axis.
	 * 
	 * Therefore, the returned matrix will have the same values as the input matrix, but
	 * with the x, y, and z scales updated to the specified values.
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
	 * initializes a matrix for perspective projection, setting the proper values for the
	 * view volume, aspect ratio, near and far distances, and resulting matrices.
	 * 
	 * @param fov field of view of the camera, which determines the aspect ratio of the
	 * projection matrix and affects the scaling of the view volume.
	 * 
	 * @param aspectRatio 2D aspect ratio of the viewport, which is used to scale the
	 * horizontal and vertical dimensions of the projection matrix.
	 * 
	 * @param zNear near clipping plane of the perspective projection, which determines
	 * the distance from the viewer at which objects appear to be part of the scene.
	 * 
	 * @param zFar 2D perspective's far clipping plane distance, which determines the
	 * range of depth values that are visible and affects the rendering result.
	 * 
	 * @returns a `Matrix4f` object that represents a perspective projection matrix.
	 * 
	 * 	- `data`: This is an array of 16 float values that represent the components of a
	 * 4x4 matrix.
	 * 	- `tanHalfFOV`: This variable represents the tangent of half of the field of view
	 * in radians.
	 * 	- `aspectRatio`: This variable represents the aspect ratio of the viewport.
	 * 	- `zNear`: This variable represents the near plane distance in the perspective projection.
	 * 	- `zFar`: This variable represents the far plane distance in the perspective projection.
	 * 
	 * The properties of the returned matrix include:
	 * 
	 * 	- The matrix has a 4x4 size, with 16 float values representing its components.
	 * 	- The matrix is initialized with values that represent the perspective projection
	 * transformation for a given field of view, aspect ratio, near plane distance, and
	 * far plane distance.
	 * 	- The matrix can be used to transform 3D points into the perspective projection
	 * space.
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
	 * initializes a matrix representing an orthographic projection, where the near and
	 * far planes are separated by a distance `depth`, and the left, right, bottom, and
	 * top edges are separated by distances `width`, `height`, and `near`, respectively.
	 * 
	 * @param left left edge of the orthographic projection in the x-axis.
	 * 
	 * @param right right edge of the orthographic projection, which is used to compute
	 * the scaling factors for the matrix.
	 * 
	 * @param bottom 2D distance from the top of the image, and is used to calculate the
	 * z-coordinate of each pixel in the resulting orthographic projection.
	 * 
	 * @param top 2D coordinate of the top edge of the orthographic projection, which is
	 * used to compute the values for the matrix elements in the Y dimension.
	 * 
	 * @param near near clipping plane of the orthographic projection, and it is used to
	 * calculate the values of the matrix elements related to the near plane.
	 * 
	 * @param far 3D far clip plane, which determines how much of the scene beyond the
	 * near clip plane is visible and can be rendered.
	 * 
	 * @returns a reference to the same Matrix4f object.
	 * 
	 * 	- The matrix data is stored in a 4x4 array, with each element representing a
	 * linear transformation component.
	 * 	- The elements of the matrix are computed based on the input parameters, such as
	 * the aspect ratio of the image and the near and far clipping planes.
	 * 	- The matrix is a orthographic projection matrix, which means it maps all points
	 * in the 3D space to their corresponding 2D positions on the image plane.
	 * 	- The matrix has a determinant of 1, indicating that it is an invertible matrix.
	 * 	- The matrix has no shear or rotation components, only scaling and translation components.
	 * 
	 * In summary, the `initOrthographic` function returns an orthographic projection
	 * matrix that can be used to transform 3D points into their corresponding 2D image
	 * coordinates.
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
	 * initializes a rotation matrix from three vectors: forward, up, and a cross product
	 * of two of these vectors. The resulting rotation matrix is returned.
	 * 
	 * @param forward 3D direction of rotation.
	 * 
	 * 	- `forward` is a vector in 3D space with a length of 3 elements (x, y, z).
	 * 	- The x, y, and z components of `forward` represent the direction of the forward
	 * vector.
	 * 	- `forward` is normalized, meaning that its length is equal to 1.
	 * 	- `forward` has no explicit attributes beyond its magnitude and direction.
	 * 
	 * @param up 3D direction perpendicular to the forward direction, which is used to
	 * compute the rotation matrix.
	 * 
	 * 	- `up`: A vector representing the upward direction in the 3D space. It is a
	 * normalized vector with magnitude equal to 1.
	 * 	- `normalized`: The vector is scaled so that its magnitude is equal to 1, which
	 * means that it has been divided by its magnitude without changing its direction.
	 * This property ensures that the vector has the same direction as the original upward
	 * vector.
	 * 
	 * @returns a matrix representation of a rotation transformation based on the provided
	 * forward and up vectors.
	 * 
	 * The returned output is a `Matrix4f` object that represents a rotation matrix. The
	 * matrix has three columns representing the x, y, and z rotations, respectively.
	 * These rotations are calculated using the input vectors `forward` and `up`, which
	 * represent the forward direction and the upward direction of the rotation, respectively.
	 * 
	 * The matrix is initialized with the values computed from the cross products of the
	 * input vectors. Specifically, the x-axis rotation is given by the vector `(f.x,
	 * f.y, 0, 0)`, where `f` is the normalized forward vector. The y-axis rotation is
	 * given by the vector `(0, f.y, 0, 0)`, and the z-axis rotation is given by the
	 * vector `(0, 0, f.z, 0)`. Finally, the rotation matrix includes the cross product
	 * of the two input vectors, which represents the roll angle of the rotation.
	 * 
	 * In summary, the `initRotation` function returns a rotation matrix that can be used
	 * to represent a 3D rotation in a 4D space. The matrix has three columns representing
	 * the x, y, and z rotations, respectively, and is initialized using the cross products
	 * of the input vectors `forward` and `up`.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	/**
	 * initializes a rotation matrix based on three vectors: `forward`, `right`, and `up`.
	 * It sets the elements of the matrix to the corresponding components of the vectors.
	 * 
	 * @param forward 3D direction in which the rotation will be applied, and its value
	 * is used to set the x, y, and z components of the rotation matrix's data array.
	 * 
	 * 	- `forward` is a `Vector3f` object that represents a 3D vector with its x, y, and
	 * z components.
	 * 	- It has three components: `x`, `y`, and `z`, which correspond to the coordinates
	 * of the forward direction in 3D space.
	 * 
	 * @param up 3D direction of the up vector, which is used to initialize the upper
	 * triangular matrix of the rotation matrix.
	 * 
	 * 	- `up`: A `Vector3f` object representing the upward direction in 3D space. It has
	 * three attributes: `x`, `y`, and `z`, which represent the coordinates of the up
	 * vector in the local reference frame of the matrix.
	 * 
	 * @param right 3D rightward direction of the rotation, which is used to initialize
	 * the components of the rotation matrix.
	 * 
	 * 	- `r.getX()` returns the x-component of the right vector.
	 * 	- `r.getY()` returns the y-component of the right vector.
	 * 	- `r.getZ()` returns the z-component of the right vector.
	 * 
	 * Therefore, `data[0][0] = r.getX(); data[0][1] = r.getY(); data[0][2] = r.getZ();`
	 * sets the x, y, and z components of the rotation matrix to the corresponding values
	 * of the right vector.
	 * 
	 * Similarly, `data[1][0] = u.getX(); data[1][1] = u.getY(); data[1][2] = u.getZ();`
	 * sets the x, y, and z components of the rotation matrix to the corresponding values
	 * of the up vector.
	 * 
	 * Finally, `data[2][0] = f.getX(); data[2][1] = f.getY(); data[2][2] = f.getZ();`
	 * sets the x, y, and z components of the rotation matrix to the corresponding values
	 * of the forward vector.
	 * 
	 * @returns a `Matrix4f` object representing a rotation matrix.
	 * 
	 * 	- `data`: This is an array of type `float[]`, where each element represents a
	 * component of the rotation matrix. The length of the array is 4, corresponding to
	 * the 4 dimensions of a 4D vector.
	 * 	- `this`: This refers to the `Matrix4f` object that was passed as a parameter to
	 * the function. It is returned unchanged as part of the output.
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
	 * takes a `Vector3f` object `r` as input and returns a new `Vector3f` object with
	 * the result of multiplying each component of `r` by the corresponding components
	 * of a 3x3 matrix `data`.
	 * 
	 * @param r 3D transformation to be applied to the output vector.
	 * 
	 * 	- `r.getX()` and `r.getY()` represent the x- and y-coordinates of the input vector,
	 * respectively.
	 * 	- `r.getZ()` represents the z-coordinate of the input vector.
	 * 	- `data[0][0]`, `data[0][1]`, `data[0][2]`, and `data[0][3]` are values that make
	 * up the output vector.
	 * 	- `data[1][0]`, `data[1][1]`, `data[1][2]`, and `data[1][3]` are additional values
	 * that make up the output vector.
	 * 	- `data[2][0]`, `data[2][1]`, `data[2][2]`, and `data[2][3]` are even more values
	 * that contribute to the final output vector.
	 * 
	 * @returns a new `Vector3f` instance with the result of multiplying each element of
	 * the input `r` vector by corresponding elements of a pre-defined data array, and
	 * then adding the results.
	 * 
	 * 	- The return type is a Vector3f object, which represents a 3D vector with float
	 * values for x, y, and z components.
	 * 	- Each component of the returned vector is calculated by multiplying the corresponding
	 * element in the input vector (represented by `r`) by a specific value from the input
	 * data array (`data`), followed by adding the result to the corresponding component
	 * of the output vector.
	 * 	- The input data array has 4 elements, each representing a different component
	 * of the output vector.
	 * 	- The returned vector has 3 components, representing the x, y, and z coordinates
	 * of the transformed vector.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}


	/**
	 * multiplies a matrix by another matrix, element-wise multiplying corresponding
	 * elements and storing the result in a new matrix.
	 * 
	 * @param r 4x4 matrix to be multiplied with the current matrix, resulting in the new
	 * matrix being returned in the `res` output parameter.
	 * 
	 * `Matrix4f r`: This is a floating-point matrix with 4 rows and 4 columns, representing
	 * a transformation matrix in 3D space. The matrix elements are represented by 4
	 * doubles, which are multiplied element-wise with the corresponding elements of the
	 * input matrix `data`.
	 * 
	 * @returns a matrix that represents the product of two 4x4 matrices.
	 * 
	 * 	- The returned value is a `Matrix4f` instance, representing the matrix product
	 * of the input matrices.
	 * 	- The elements of the resulting matrix are calculated by multiplying corresponding
	 * elements of the input matrices and summing them up.
	 * 	- The resulting matrix has the same dimensions as the input matrices.
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
	 * retrieves a value from a two-dimensional array `data`. The value is located at
	 * position `(x, y)` and is returned as a `float` variable.
	 * 
	 * @param x 0-based index of a cell in the 2D array `data`.
	 * 
	 * @param y 2nd dimension of the data array that is being accessed by the function.
	 * 
	 * @returns a floating-point value representing the value at the specified position
	 * in a 2D array.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}
	/**
	 * sets the value of a field named `data`.
	 * 
	 * @param data 2D array of float values that are to be stored as the value of the
	 * class instance field `data`.
	 * 
	 * 	- It is an array of arrays, where each inner array represents a matrix in the
	 * problem domain.
	 * 	- The size of the outer array corresponds to the number of matrices in the problem
	 * domain.
	 * 	- Each inner array has a length equal to the number of rows in the matrix, and
	 * contains values representing the elements of the matrix.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}
	/**
	 * sets a value to an element at a specific position in a two-dimensional array.
	 * 
	 * @param x 0-based index of the row in the two-dimensional data array that contains
	 * the element to be set.
	 * 
	 * @param y 2nd dimension of the data array being modified by the function.
	 * 
	 * @param value 3D coordinate's value that will be stored at the specified position
	 * in the data array.
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
