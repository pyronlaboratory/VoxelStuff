package com.ch.math;

/**
 * represents a 4x4 matrix of floats and provides methods for initializing rotation
 * matrices, transforming vectors, multiplying matrices, and getting the linear data.
 * It also has getters and setters for the individual elements of the matrix.
 */
public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

	/**
	 * initializes a matrix with all elements set to identity values, i.e., rows and
	 * columns are identical and have the same value as the diagonal element.
	 * 
	 * @returns a Matrix4f object with all elements set to zero, except for the identity
	 * matrix.
	 * 
	 * The `Matrix4f` object returned by the function is the same instance as the original
	 * one.
	 * 
	 * The data array of the matrix is initialized with values that satisfy the identity
	 * condition, where each element is either 0 or 1. Specifically, the elements at
	 * positions (0,0), (0,1), (1,0), and (1,1) are set to 1, while all other elements
	 * are set to 0.
	 * 
	 * The resulting matrix has the following properties:
	 * 
	 * 	- It is an identity matrix, meaning that it preserves the identity of any vector
	 * applied to it.
	 * 	- Its determinant is equal to 1, which means that it has a unique inverse.
	 * 	- It is orthogonally diagonal, meaning that its diagonal elements are all non-zero
	 * and the off-diagonal elements are all zero.
	 * 
	 * Overall, the `initIdentity` function is used to initialize a matrix to an identity
	 * matrix, which is commonly used in various mathematical operations and applications.
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
	 * sets the translation components (x, y, z) for a matrix, using the negative of the
	 * input x value to create the y component and setting each element of the matrix
	 * accordingly. It returns the modified matrix.
	 * 
	 * @param x 3D translation along the x-axis in the returned matrix.
	 * 
	 * @param y 2D translation along the y-axis, where positive values shift the translation
	 * upwards and negative values shift it downwards.
	 * 
	 * @param z 3rd dimension of the translation vector, which is added to the corresponding
	 * element in the matrix's data array.
	 * 
	 * @returns a reference to the original matrix object, unchanged.
	 * 
	 * 	- The `Matrix4f` object is returned as the output.
	 * 	- The data array of the matrix is modified to set the elements according to the
	 * input parameters `x`, `y`, and `z`.
	 * 	- The elements of the matrix are arranged in a 4x4 grid, with each element
	 * representing a point in 3D space.
	 * 	- The elements of the matrix are set to either 0 or 1, depending on whether they
	 * represent a translation along the x, y, or z axis, respectively.
	 * 
	 * In summary, the `initTranslation` function modifies a 4x4 matrix to represent a
	 * translation in 3D space, based on the input parameters `x`, `y`, and `z`.
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
	 * creates a rotation matrix based on three Euler angles (`x`, `y`, and `z`) using
	 * the Rodrigues formula. It returns a Matrix4f object representing the rotation.
	 * 
	 * @param x 3D rotation around the x-axis and is used to calculate the rotation matrix
	 * rx.
	 * 
	 * @param y 2D rotation angle around the x-axis and is used to compute the rotation
	 * matrix for the z-axis.
	 * 
	 * @param z 3D rotation axis around which the matrix is rotated, and it is used to
	 * calculate the rotation matrix `rz`.
	 * 
	 * @returns a new Matrix4f object representing the rotation matrix.
	 * 
	 * 	- The `data` field is an instance of the `Matrix4f` class, representing a 4x4 matrix.
	 * 	- The elements of the matrix are computed using the cosine and sine functions of
	 * the input angles (x, y, z) in radians.
	 * 	- The matrix has the following properties:
	 * 	+ The first column represents the rotation around the x-axis.
	 * 	+ The second column represents the rotation around the y-axis.
	 * 	+ The third column represents the rotation around the z-axis.
	 * 	+ The fourth column represents the translation component.
	 * 	- The matrix is computed using the following formulas:
	 * 	+ rx.data[0][0] = 1;
	 * 	+ rx.data[0][1] = 0;
	 * 	+ rx.data[0][2] = 0;
	 * 	+ rx.data[0][3] = 0;
	 * 	+ rx.data[1][0] = 0;
	 * 	+ rx.data[1][1] = Math.cos(z);
	 * 	+ rx.data[1][2] = -Math.sin(z);
	 * 	+ rx.data[1][3] = 0;
	 * 	+ rx.data[2][0] = 0;
	 * 	+ rx.data[2][1] = Math.sin(z);
	 * 	+ rx.data[2][2] = Math.cos(z);
	 * 	+ rx.data[2][3] = 0;
	 * 	+ rx.data[3][0] = 0;
	 * 	+ rx.data[3][1] = 0;
	 * 	+ rx.data[3][2] = 0;
	 * 	+ rx.data[3][3] = 1;
	 * 	- The `ry` and `rz` matrices are computed using the same formulas as the `rx`
	 * matrix, but with different inputs (y and z, respectively).
	 * 	- The `data` field is an instance of the `Matrix4f` class, representing a 4x4 matrix.
	 * 	- The `getData()` method returns a copy of the internal data array, which can be
	 * used to access the individual elements of the matrix.
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
	 * initializes a matrix with scaled values for each element, allowing for uniform
	 * scaling of a 3D transformation matrix.
	 * 
	 * @param x 4th component of the scaling factor applied to the matrix.
	 * 
	 * @param y 2D scaling factor for the x-axis of the matrix when initializing a new
	 * instance of the `Matrix4f` class.
	 * 
	 * @param z 2nd dimension of the scaling factor for the matrix, which is applied to
	 * the matrix's elements in the returned object.
	 * 
	 * @returns a reference to the original matrix object.
	 * 
	 * The returned object is an instance of the `Matrix4f` class, representing a 4x4
	 * homogeneous transformation matrix.
	 * 
	 * The elements of the matrix are initialized to the input values provided in the
	 * constructor. Specifically, the elements of the first column (data[0]) represent
	 * the x-scale factor, the second column (data[1]) represents the y-scale factor, and
	 * the third column (data[2]) represents the z-scale factor. The fourth column (data[3])
	 * represents the overall scale factor.
	 * 
	 * The `initScale` function returns a reference to the modified matrix object,
	 * indicating that the original matrix is unchanged.
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
	 * initializes a matrix for perspective projection, where fov is field of view,
	 * aspectRatio is aspect ratio, zNear is near clipping plane, and zFar is far clipping
	 * plane. It sets the elements of the matrix to achieve the desired perspective projection.
	 * 
	 * @param fov field of view (FOV) of the matrix, which determines the angle of the
	 * visual cone that the matrix projects.
	 * 
	 * @param aspectRatio 2D screen aspect ratio of the viewport, which is used to calculate
	 * the perspective projection matrix's front-to-back and left-to-right scaling factors.
	 * 
	 * @param zNear near plane of the perspective projection, which determines the position
	 * of objects in the near field of view.
	 * 
	 * @param zFar 3D distance from the camera to the near plane of the perspective
	 * projection, which determines the field of view and the aspect ratio of the resulting
	 * image.
	 * 
	 * @returns a matrix representing the viewprojection transformation for a perspective
	 * projection, with fields for the position, scale, and orientation of the camera.
	 * 
	 * 	- `data`: This is an array of 16 float values, representing the elements of a
	 * matrix. The dimensions of the matrix are not explicitly stated in the function
	 * signature, but it can be inferred that the matrix has four columns and twelve rows
	 * (representing a 3D transformation matrix).
	 * 	- `tanHalfFOV`: This is a value computed from the fov parameter passed to the
	 * function. It represents the tan of half of the field of view in radians.
	 * 	- `zNear`: This is a value passed to the function, representing the near plane
	 * of the perspective transformation.
	 * 	- `zFar`: This is also a value passed to the function, representing the far plane
	 * of the perspective transformation.
	 * 	- `aspectRatio`: This is a value passed to the function, representing the aspect
	 * ratio of the viewport.
	 * 
	 * The output of the `initPerspective` function can be used to initialize a Matrix4f
	 * object, which can then be used for various purposes such as 3D transformations,
	 * projections, and viewports.
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
	 * initializes an orthographic projection matrix with given dimensions and aspect
	 * ratio. It sets the appropriate elements of the matrix based on the input parameters,
	 * and returns the modified matrix.
	 * 
	 * @param left left value of the orthographic projection, which determines the scale
	 * factor for the x-axis in the returned matrix.
	 * 
	 * @param right right side of the orthographic projection and is used to calculate
	 * the width of the projection.
	 * 
	 * @param bottom 2D coordinate of the bottom-left corner of the orthographic projection,
	 * which is used to determine the scale and orientation of the projection.
	 * 
	 * @param top 2D coordinate of the top-left corner of the orthographic projection,
	 * which is used to determine the scale factor for the vertical dimension of the projection.
	 * 
	 * @param near 3D coordinate at which the orthographic projection is centered, and
	 * it determines the near plane of the projection.
	 * 
	 * @param far 4th coordinate of the resulting matrix, which is the distance from the
	 * origin along the Z-axis.
	 * 
	 * @returns a reference to the same matrix object.
	 * 
	 * 	- `data`: An array of 16 float values that represent the components of a 4x4
	 * orthographic transformation matrix.
	 * 	- Each element of `data` is computed based on the input parameters, including
	 * `left`, `right`, `bottom`, `top`, `near`, and `far`.
	 * 	- The matrix elements are normalized to ensure orthogonality and proper scaling.
	 * 	- The returned output is a reference to the same matrix object, allowing for
	 * efficient chaining of method calls.
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
	 * takes a forward and up vector as input, calculates the cross product of these
	 * vectors, and returns a rotation matrix based on those vectors.
	 * 
	 * @param forward 3D direction of rotation.
	 * 
	 * 	- `forward` is a `Vector3f` instance representing a vector in 3D space.
	 * 	- It has three components: x, y, and z, which represent the coordinate values of
	 * the forward direction.
	 * 	- The magnitude (or length) of `forward` is equal to 1, as it is the direction
	 * of the positive z-axis.
	 * 
	 * @param up 3D axis perpendicular to the forward direction and is used to calculate
	 * the rotation matrix.
	 * 
	 * 	- `up` is a vector representing the upward direction in a 3D space. It has three
	 * components, which are the x, y, and z coordinates of the upward direction.
	 * 	- The magnitude of `up` is always non-zero, as it represents a fixed direction
	 * in space.
	 * 	- `up` is a unit vector, meaning that its magnitude is equal to 1. This ensures
	 * that the rotation is properly defined and can be applied to any object in 3D space.
	 * 
	 * @returns a rotation matrix representing the rotation around the forward and up vectors.
	 * 
	 * 	- The output is a `Matrix4f` object, which represents a 4x4 homogeneous transformation
	 * matrix.
	 * 	- The elements of the matrix are determined by the inputs `forward`, `up`, and
	 * their dot product. Specifically, the elements in the first three rows (i.e., the
	 * rotation part) are proportional to `f`, while the elements in the third row (i.e.,
	 * the translation part) are proportional to `u`.
	 * 	- The rotation part of the matrix represents a counterclockwise rotation around
	 * the origin, as defined by the direction of `f`.
	 * 	- The translation part of the matrix represents the distance from the origin along
	 * the direction of `u`.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	/**
	 * sets the rotation matrix based on the provided forward, up, and right vectors,
	 * using the dot product of these vectors to compute the rotation axis and the sine
	 * of the angle between them as the rotation angle.
	 * 
	 * @param forward 3D direction of the rotation axis, which is used to initialize the
	 * rotation matrix.
	 * 
	 * 	- `Vector3f forward`: This represents a 3D vector that points in the direction
	 * of the desired rotation axis. The components of this vector represent the x, y,
	 * and z values of the rotation axis.
	 * 
	 * @param up 3D direction perpendicular to the rotation axis, which is used to define
	 * the orientation of the rotation matrix.
	 * 
	 * 	- `up`: A `Vector3f` object representing the up direction vector in 3D space. It
	 * has three components: `x`, `y`, and `z`, which represent the magnitude and direction
	 * of the upward vector.
	 * 
	 * @param right 3D right vector of the rotation axis, which is used to initialize the
	 * rotation matrix with the correct orientation.
	 * 
	 * 	- `right`: A vector representing the rightward direction in 3D space. It has three
	 * elements: `x`, `y`, and `z`, which represent the magnitude and direction of the
	 * rightward vector.
	 * 	- `up`: A vector representing the upward direction in 3D space. It has three
	 * elements: `x`, `y`, and `z`, which represent the magnitude and direction of the
	 * upward vector.
	 * 	- `forward`: A vector representing the forward direction in 3D space. It has three
	 * elements: `x`, `y`, and `z`, which represent the magnitude and direction of the
	 * forward vector.
	 * 
	 * @returns a matrix representing the rotation of a 3D object around a given axis.
	 * 
	 * 1/ The `data` array is a 4x4 matrix, where each element represents a vector in 3D
	 * space.
	 * 2/ The elements of the `data` array represent the rotation of the object around
	 * different axes (x, y, and z).
	 * 3/ Each element of the `data` array has a magnitude of 1, representing a unitary
	 * rotation.
	 * 4/ The last column of the `data` array represents the identity matrix, indicating
	 * that the rotation is around the origin.
	 * 5/ The function returns a reference to the same `Matrix4f` object, allowing for
	 * chaining of method calls.
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
	 * takes a `Vector3f` argument `r` and returns a new `Vector3f` object with the result
	 * of multiplying each component of the input vector by a set of constants and adding
	 * the resulting values.
	 * 
	 * @param r 4D vector that transforms the original 4D vector returned by the function.
	 * 
	 * 	- `r.getX()` returns the x-component of the input vector.
	 * 	- `r.getY()` returns the y-component of the input vector.
	 * 	- `r.getZ()` returns the z-component of the input vector.
	 * 	- `data[0][0]`, `data[1][0]`, and `data[2][0]` represent the x-components of three
	 * different vectors in the function.
	 * 	- `data[0][1]`, `data[1][1]`, and `data[2][1]` represent the y-components of these
	 * vectors.
	 * 	- `data[0][2]`, `data[1][2]`, and `data[2][2]` represent the z-components of these
	 * vectors.
	 * 	- `data[0][3]`, `data[1][3]`, and `data[2][3]` represent the w-components of these
	 * vectors.
	 * 
	 * @returns a new `Vector3f` instance representing the result of multiplying each
	 * component of the input `r` vector by the corresponding components of the input
	 * data array.
	 * 
	 * 	- The output is of type Vector3f, which represents a 3D vector in homogeneous coordinates.
	 * 	- The output's components are calculated by multiplying the input vector's elements
	 * with the corresponding elements of a set of data arrays, followed by addition and
	 * scaling of the resulting values.
	 * 	- The data arrays have four elements each, representing the scaled position,
	 * rotation, and translation components of a 3D transformation.
	 * 
	 * Overall, the `transform` function returns a transformed vector that represents the
	 * result of applying a 3D transformation to the input vector.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

	/**
	 * multiplies a matrix by another matrix, performing element-wise multiplication and
	 * storing the result in a new matrix.
	 * 
	 * @param r 4x4 homogeneous transformation matrix that, when multiplied with the
	 * current 4x4 matrix, results in the output matrix.
	 * 
	 * Matrix4f `r`: A 4x4 homogeneous transformation matrix with four elements (0, 1,
	 * 2, and 3) representing the matrix's components.
	 * 
	 * @returns a new matrix with the product of the input matrices.
	 * 
	 * The `res` variable is a `Matrix4f` object that represents the result of multiplying
	 * the input matrices `data` and `r`.
	 * 
	 * The method takes two matrices as input, and performs element-wise multiplication.
	 * That is, for each element in the first matrix (`data`), it multiplies the corresponding
	 * elements in the second matrix (`r`) and stores the result in the corresponding
	 * element of the output matrix `res`.
	 * 
	 * The output matrix `res` has the same dimensions as the input matrices, with each
	 * element containing the product of the corresponding elements in the input matrices.
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
	 * creates a 4x4 array of float values by replicating the input `data` array four
	 * times and returning it as a result.
	 * 
	 * @returns an array of arrays of float values.
	 */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
	/**
	 * returns an array of float values representing a linear dataset with 6 rows and 3
	 * columns. Each row represents a different data point, while each column corresponds
	 * to a specific feature or variable.
	 * 
	 * @returns an array of 12 floats representing the data points in a linear format.
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
	 * retrieves a value from a 2D array `data`. The value is located at position (x, y)
	 * and is returned as a float.
	 * 
	 * @param x 0-based index of a cell in the 2D array `data`.
	 * 
	 * @param y 2nd dimension of the data array that is being accessed by the function,
	 * and it is used to retrieve the value of the corresponding element in the data array.
	 * 
	 * @returns a floating-point value representing the data at the specified coordinates
	 * within an array.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}

	/**
	 * sets the value of a member field `data`.
	 * 
	 * @param data 2D array of float values that will be stored in the `data` field of
	 * the function's receiver object.
	 * 
	 * 	- `data` is a `float[][]` data structure.
	 * 	- It represents an array of arrays, where each inner array contains `float` values.
	 * 	- The length of the outer array (i.e., the size of the array of arrays) is
	 * determined by the input parameter.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}

	/**
	 * sets a value for a specific position in a two-dimensional array of integers and floats.
	 * 
	 * @param x 0-based index of the row in which the data element is being updated.
	 * 
	 * @param y 2nd dimension of the data array being manipulated and sets its corresponding
	 * element to the provided `float` value.
	 * 
	 * @param value 3D pixel value that is assigned to the corresponding position in the
	 * data array.
	 */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	/**
	 * transforms an array of arrays into a new array, transposing the elements within
	 * each sub-array. The original data is assigned to the new array.
	 */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
