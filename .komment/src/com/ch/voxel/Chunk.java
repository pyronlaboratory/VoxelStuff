{"name":"Chunk.java","path":"src/com/ch/voxel/Chunk.java","content":{"structured":{"description":"","diagram":"digraph G {\n    label=\"com.ch.voxel.Chunk\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n    \n    subgraph cluster_0 {\n        label=\"voxel\"\n        color=\"#33363A\"\n        subgraph cluster_main {\n            // style=filled;\n            color=\"#00000000\"; \n            Chunk [style=filled,color=\"#26de81\",shape=rectangle, fontname=\"Courier New\"];\n            label = \"\"\n        }\n        Block\n    }\n    subgraph cluster_1 {\n        label=\"math\"\n        color=\"#33363A\"\n        Matrix4f\n    }\n    Model\n    Block -> Chunk \n    Chunk -> Model \n    Chunk -> Matrix4f \n}\n","items":[{"id":"6773cd94-c890-44a3-9f5d-7e3a7a931321","ancestors":[],"type":"function","name":"toGenModel","location":{"offset":"\t","indent":1,"insert":148,"start":148},"returns":false,"params":[{"name":"now","type":"boolean"}],"code":"public void toGenModel(boolean now) {\n\n\t\tint max_index = 0;\n//\t\tSystem.out.println(\"gen model\");\n\t\tfor (int i = 0; i < CHUNK_SIZE_CUBED; i++) {\n\t\t\tBlock b = blocks[i];\n\t\t\tif (b != null) {\n\t\t\t\tmax_index = gen(vertices, indices, b, max_index);\n\t\t\t\t\t\n\t\t\t}\n\t\t}\n//\t\tSystem.out.println(\"vertice   : \" + vertices.size() / 5 + \" -- floats : \" + vertices.size());\n//\t\tSystem.out.println(\"indices   : \" + indices.size());\n//\t\tSystem.out.println(\"triangles : \" + indices.size() / 3);\n//\t\tSystem.out.println(\"quads     : \" + indices.size() / 6);\n//\t\tSystem.out.println(\"---------------------------\\nloading model arrays\");\n\t\t\n\t\t// cant implement filtering and re-indexing for textured cubes\n//\t\t{\n//\t\t\t\n//\t\t\tArrayList<Integer>\n//\t\t\t\n//\t\t}\n\t\t\n//\t\treturn Model.load(Util.toFloatArray(new_vertices), Util.toIntArray(new_indices));\n\t\t\n\t\tif (now) {\n\t\t\tcreateModel();\n\t\t\tto_gen_model = false;\n\t\t} else {\n\t\t\tto_gen_model = true;\n\t\t}\n\t\t\n\t}","skip":false,"docLength":null,"length":34,"comment":{"description":"Generates a 3D model from a set of vertices, indices, and blocks. It determines the maximum index, generates new vertices and indices, and creates the model.","params":[{"name":"now","type":"boolean","description":"boolean flag to indicate whether the model should be re-generated or loaded from existing arrays, with a value of `true` for re-generation and `false` for loading."}],"returns":null}},{"id":"442e8c80-0f89-4ab3-8250-579646d9792a","ancestors":[],"type":"function","name":"gen","location":{"offset":"\t","indent":1,"insert":194,"start":194},"returns":"int","params":[{"name":"vertices","type":"List<Float>"},{"name":"indices","type":"List<Integer>"},{"name":"block","type":"Block"},{"name":"max_index","type":"int"}],"code":"private static int gen(List<Float> vertices, List<Integer> indices, Block block, int max_index) {\n\t\t\n\t\tfloat x = block.x;\n\t\tfloat y = block.y;\n\t\tfloat z = block.z;\n\t\t\n\t\tif (block.ft) {\n\t\t\tfloat[] tmp_v = { //\n\t\t\t\tx,   y,   z,   0, 0, //\n\t\t\t\tx+1, y,   z,   1, 0, //\n\t\t\t\tx+1, y+1, z,   1, 1, //\n\t\t\t\tx,   y+1, z,   0, 1, //\n\t\t\t}; //\n\t\t\tfor (float f : tmp_v) vertices.add(f);\n\t\t\tfor (int i : new int[] {0, 1, 2, 0, 2, 3}) indices.add(max_index + i);\n\t\t\tmax_index += 4;\n\t\t}\n\t\tif (block.bk) {\n\t\t\tfloat[] tmp_v = { //\n\t\t\t\tx,   y,   z+1,   1, 0, //\n\t\t\t\tx+1, y,   z+1,   0, 0, //\n\t\t\t\tx+1, y+1, z+1,   0, 1, //\n\t\t\t\tx,   y+1, z+1,   1, 1, //\n\t\t\t}; //\n\t\t\tfor (float f : tmp_v) vertices.add(f);\n\t\t\tfor (int i : new int[] {0, 3, 2, 0, 2, 1}) indices.add(max_index + i);\n\t\t\tmax_index += 4;\n\t\t}\n\t\tif (block.bt) {\n\t\t\tfloat[] tmp_v = { //\n\t\t\t\tx,   y,   z,     0, 0, //\n\t\t\t\tx+1, y,   z,   \t 1, 0, //\n\t\t\t\tx+1, y,   z+1,   1, 1, //\n\t\t\t\tx,   y,   z+1,   0, 1, //\n\t\t\t}; //\n\t\t\tfor (float f : tmp_v) vertices.add(f);\n\t\t\tfor (int i : new int[] {0, 3, 2, 0, 2, 1}) indices.add(max_index + i);\n\t\t\tmax_index += 4;\n\t\t}\n\t\tif (block.tp) {\n\t\t\tfloat[] tmp_v = { //\n\t\t\t\tx,   y+1, z,     0, 0, //\n\t\t\t\tx+1, y+1, z,     1, 0, //\n\t\t\t\tx+1, y+1, z+1,   1, 1, //\n\t\t\t\tx,   y+1, z+1,   0, 1, //\n\t\t\t}; //\n\t\t\tfor (float f : tmp_v) vertices.add(f);\n\t\t\tfor (int i : new int[] {0, 1, 2, 0, 2, 3}) indices.add(max_index + i);\n\t\t\tmax_index += 4;\n\t\t}\n\t\tif (block.lt) {\n\t\t\tfloat[] tmp_v = { //\n\t\t\t\tx,   y,   z,     1, 0, //\n\t\t\t\tx,   y+1, z,     1, 1, //\n\t\t\t\tx,   y+1, z+1,   0, 1, //\n\t\t\t\tx,   y,   z+1,   0, 0, //\n\t\t\t}; //\n\t\t\tfor (float f : tmp_v) vertices.add(f);\n\t\t\tfor (int i : new int[] {0, 1, 2, 0, 2, 3}) indices.add(max_index + i);\n\t\t\tmax_index += 4;\n\t\t}\n\t\tif (block.rt) {\n\t\t\tfloat[] tmp_v = { //\n\t\t\t\tx+1, y,   z,     0, 0, //\n\t\t\t\tx+1, y+1, z,     0, 1, //\n\t\t\t\tx+1, y+1, z+1,   1, 1, //\n\t\t\t\tx+1, y,   z+1,   1, 0, //\n\t\t\t}; //\n\t\t\tfor (float f : tmp_v) vertices.add(f);\n\t\t\tfor (int i : new int[] {0, 3, 2, 0, 2, 1}) indices.add(max_index + i);\n\t\t\tmax_index += 4;\n\t\t}\n\t\treturn max_index;\n\t}","skip":false,"docLength":null,"length":74,"comment":{"description":"Generates high-quality documentation for given code by adding vertices and indices to a list, based on the block type and its position in the space.","params":[{"name":"vertices","type":"List<Float>","description":"2D coordinates of the vertices that make up the 3D shape of the block, and is used to add those vertices to a list for further processing."},{"name":"indices","type":"List<Integer>","description":"3D indices of vertices in the mesh, and is used to update the array `vertices` by adding or removing indices based on the type of block being processed."},{"name":"block","type":"Block","description":"3D block being generated and determines which type of geometry is added to the vertices and indices lists."},{"name":"max_index","type":"int","description":"0-based index of the current block being processed, and is used to update the indices array with the new vertex positions and to increment the index for each new block."}],"returns":{"type":"int","description":"an integer representing the maximum index value added to the `indices` list for each block type."}}}]}}}