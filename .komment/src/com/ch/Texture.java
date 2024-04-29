{"name":"Texture.java","path":"src/com/ch/Texture.java","content":{"structured":{"description":"A `Texture` class for managing textures in OpenGL. It provides methods for loading and binding textures, as well as getting the ID of a loaded texture. The loadTexture method loads a texture image from a file, converts it to an OpenGL texture, and returns the ID of the created texture.","diagram":"digraph G {\n    label=\"com.ch.Texture\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n    \n}\n","items":[{"id":"d19f7a00-c850-279c-2d49-42daea6a155b","ancestors":[],"type":"function","description":"is a Java class that provides functionality for managing textures in OpenGL. It offers various methods for binding and unbinding textures, as well as loading and storing textures. The class also provides a method for getting the ID of a loaded texture.","name":"Texture","code":"public class Texture {\n\n\tprivate int id;\n\tprivate String fileName;\n\n\tpublic Texture(String fileName) {\n\t\tthis.fileName = fileName;\n\t\tthis.id = loadTexture(fileName);\n\t}\n\n\n\t/**\n\t * 0 invokes the binding operation at index 0 of a data structure.\n\t */\n\tpublic void bind() {\n\t\tbind(0);\n\t}\n\n\t/**\n\t * sets the active texture slot to a specified index (samplerSlot) and binds a texture\n\t * to that slot using the `glBindTexture()` method. The function checks that the input\n\t * samplerSlot is within the valid range of 0 to 31 before executing the binding operation.\n\t * \n\t * @param samplerSlot 0-based index of a texture slot in the current active texture\n\t * unit, with values ranging from 0 to 31.\n\t */\n\tpublic void bind(int samplerSlot) {\n\t\tassert (samplerSlot >= 0 && samplerSlot <= 31);\n\t\tglActiveTexture(GL_TEXTURE0 + samplerSlot);\n\t\tglBindTexture(GL_TEXTURE_2D, id);\n\t}\n\n\t/**\n\t * returns the `id` field's value.\n\t * \n\t * @returns an integer value representing the ID.\n\t */\n\tpublic int getID() {\n\t\treturn id;\n\t}\n\n\t/**\n\t * loads a texture image from a file, converts it to an OpenGL texture, and returns\n\t * the ID of the created texture.\n\t * \n\t * @param fileName name of the texture file to be loaded and read.\n\t * \n\t * @returns an integer ID representing a loaded texture.\n\t */\n\tprivate static int loadTexture(String fileName) {\n\t\ttry {\n\t\t\tBufferedImage image = ImageIO.read(new File(fileName));\n\t\t\tint[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());\n\n\t\t\tByteBuffer buffer = Util.createByteBuffer(image.getHeight() * image.getWidth() * 4);\n\t\t\tboolean hasAlpha = image.getColorModel().hasAlpha();\n\n\t\t\tfor (int y = image.getHeight() - 1; y >= 0; y--) {\n\t\t\t\tfor (int x = 0; x < image.getWidth(); x++) {\n\t\t\t\t\tint pixel = pixels[y * image.getWidth() + x];\n\n\t\t\t\t\tbuffer.put((byte) ((pixel >> 16) & 0xFF));\n\t\t\t\t\tbuffer.put((byte) ((pixel >> 8) & 0xFF));\n\t\t\t\t\tbuffer.put((byte) ((pixel) & 0xFF));\n\t\t\t\t\tif (hasAlpha)\n\t\t\t\t\t\tbuffer.put((byte) ((pixel >> 24) & 0xFF));\n\t\t\t\t\telse\n\t\t\t\t\t\tbuffer.put((byte) (0xFF));\n\t\t\t\t}\n\t\t\t}\n\n\t\t\tbuffer.flip();\n\n\t\t\tint id = GL11.glGenTextures();\n\t\t\t\n\t\t\tglBindTexture(GL_TEXTURE_2D, id);\n\n\t\t\tglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);\n\t\t\tglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);\n\n\t\t\tglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);\n\t\t\tglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);\n\t\t\t\n\t\t\tglTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);\n\n\t\t\tGL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);\n\t\t\tGL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);\n\t\t\tGL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -.6f);\n\t\t\t\n\t\t\treturn id;\n\t\t} catch (Exception e) {\n\t\t\te.printStackTrace();\n\t\t\tSystem.exit(1);\n\t\t}\n\n\t\treturn 0;\n\t}\n\n}","location":{"start":35,"insert":29,"offset":" ","indent":0,"comment":{"start":28,"end":34}},"item_type":"class","length":99,"docLength":6},{"id":"d7b963c3-bd01-3792-0648-1cef8793cb2c","ancestors":["d19f7a00-c850-279c-2d49-42daea6a155b"],"type":"function","description":"0 invokes a method with the same name, passing the integer parameter 0 as an argument.","params":[],"usage":{"language":"java","code":"Texture tex = new Texture(\"texture_image.jpg\"); // create a new texture object\ntex.bind(0);                              // bind the texture to unit 0\n","description":""},"name":"bind","code":"public void bind() {\n\t\tbind(0);\n\t}","location":{"start":49,"insert":46,"offset":"\t","indent":1,"comment":{"start":45,"end":48}},"item_type":"method","length":3,"docLength":3},{"id":"0262d01e-f682-b680-3f4e-17f6e3876d43","ancestors":["d19f7a00-c850-279c-2d49-42daea6a155b"],"type":"function","description":"sets the current texture slot to a specific value (0-31) and binds a texture ID to it using the `glBindTexture()` method.","params":[{"name":"samplerSlot","type_name":"int","description":"0-based index of a texture unit to bind to the current program's input assembly, with valid values ranging from 0 to 31.","complex_type":false}],"usage":{"language":"java","code":"int samplerSlot = 0;\ntexture.bind(samplerSlot);\n","description":"\nThis will set the active texture unit to `GL_TEXTURE0 + samplerSlot`, and then bind the texture with ID `id` to that slot. Note that `samplerSlot` must be within the range of 0-31, inclusive."},"name":"bind","code":"public void bind(int samplerSlot) {\n\t\tassert (samplerSlot >= 0 && samplerSlot <= 31);\n\t\tglActiveTexture(GL_TEXTURE0 + samplerSlot);\n\t\tglBindTexture(GL_TEXTURE_2D, id);\n\t}","location":{"start":61,"insert":53,"offset":"\t","indent":1,"comment":{"start":52,"end":60}},"item_type":"method","length":5,"docLength":8},{"id":"fe80cdf4-cfd7-b788-4646-b289a610a6e0","ancestors":["d19f7a00-c850-279c-2d49-42daea6a155b"],"type":"function","description":"returns the value of the `id` field.","params":[],"returns":{"type_name":"int","description":"an integer representing the ID of the object.","complex_type":false},"usage":{"language":"java","code":"Texture myTexture = new Texture(\"texture.png\"); // Create a texture object. \nint textureId = myTexture.getID(); // Get the ID of the texture object. \nSystem.out.println(textureId); // Print the ID to the console.","description":""},"name":"getID","code":"public int getID() {\n\t\treturn id;\n\t}","location":{"start":72,"insert":67,"offset":"\t","indent":1,"comment":{"start":66,"end":71}},"item_type":"method","length":3,"docLength":5},{"id":"85b53699-d631-cfb7-da47-8772adea9684","ancestors":["d19f7a00-c850-279c-2d49-42daea6a155b"],"type":"function","description":"loads an image from a file and converts it into a texture in memory, which can be used in a graphics pipeline. It generates an ID for the texture and sets its parameters for linear filtering and repeat wrapping.","params":[{"name":"fileName","type_name":"String","description":"2D texture image file to be loaded and converted into a texture ID.","complex_type":false}],"returns":{"type_name":"int","description":"an OpenGL texture ID for a 2D texture loaded from a file.","complex_type":false},"name":"loadTexture","code":"private static int loadTexture(String fileName) {\n\t\ttry {\n\t\t\tBufferedImage image = ImageIO.read(new File(fileName));\n\t\t\tint[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());\n\n\t\t\tByteBuffer buffer = Util.createByteBuffer(image.getHeight() * image.getWidth() * 4);\n\t\t\tboolean hasAlpha = image.getColorModel().hasAlpha();\n\n\t\t\tfor (int y = image.getHeight() - 1; y >= 0; y--) {\n\t\t\t\tfor (int x = 0; x < image.getWidth(); x++) {\n\t\t\t\t\tint pixel = pixels[y * image.getWidth() + x];\n\n\t\t\t\t\tbuffer.put((byte) ((pixel >> 16) & 0xFF));\n\t\t\t\t\tbuffer.put((byte) ((pixel >> 8) & 0xFF));\n\t\t\t\t\tbuffer.put((byte) ((pixel) & 0xFF));\n\t\t\t\t\tif (hasAlpha)\n\t\t\t\t\t\tbuffer.put((byte) ((pixel >> 24) & 0xFF));\n\t\t\t\t\telse\n\t\t\t\t\t\tbuffer.put((byte) (0xFF));\n\t\t\t\t}\n\t\t\t}\n\n\t\t\tbuffer.flip();\n\n\t\t\tint id = GL11.glGenTextures();\n\t\t\t\n\t\t\tglBindTexture(GL_TEXTURE_2D, id);\n\n\t\t\tglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);\n\t\t\tglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);\n\n\t\t\tglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);\n\t\t\tglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);\n\t\t\t\n\t\t\tglTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);\n\n\t\t\tGL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);\n\t\t\tGL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);\n\t\t\tGL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -.6f);\n\t\t\t\n\t\t\treturn id;\n\t\t} catch (Exception e) {\n\t\t\te.printStackTrace();\n\t\t\tSystem.exit(1);\n\t\t}\n\n\t\treturn 0;\n\t}","location":{"start":84,"insert":76,"offset":"\t","indent":1,"comment":{"start":75,"end":83}},"item_type":"method","length":48,"docLength":8}]}}}