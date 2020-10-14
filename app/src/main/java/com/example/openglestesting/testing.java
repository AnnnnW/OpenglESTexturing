package com.example.openglestesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.net.ssl.HttpsURLConnection;

public class testing extends AppCompatActivity {
    private GLSurfaceView glView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_testing);

        glView = new MyGlSurfaceView(this);
        setContentView(glView);
    }

    private class MyGlSurfaceView extends GLSurfaceView {

        private final MyGLRenderer renderer;

        public MyGlSurfaceView(Context context) {
            super(context);

            // create an opengl es 2,0 context
            setEGLContextClientVersion(2);

            renderer = new MyGLRenderer();

            // set the renderer for drawing on the GLSurfaceView
            setRenderer(renderer);

            // renderer the view only when there is a change in the drawing data
//            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }

        private class MyGLRenderer implements GLSurfaceView.Renderer {
            ///////// change the background color of an empty activity //////////
//            @Override
//            public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
//                // set the background frame color
//                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//            }
//
//            @Override
//            public void onDrawFrame(GL10 gl) {
//                // redraw background color
//                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
//            }
//
//            public void onSurfaceChanged(GL10 gl, int width, int height) {
//                GLES20.glViewport(0, 0, width, height);
//            }

            ///////// change the background color of an empty activity //////////

            ///////// drawing a shape //////////
//            private Triangle mTriangle;
            private Square mSquare;
            private Context context;

            @Override
            public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
                // set the background frame color
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

                // initialize a trangle
//                mTriangle = new Triangle();
                // initialize a square
                mSquare = new Square();

                /////////////// texturing ///////////////
                mSquare.loadGLTexture(this.context);
                GLES20.glEnable(gl.GL_TEXTURE_2D);  // enable texture mapping
                /////////////// texturing ///////////////

            }

//            private float[] rotationMatrix = new float[16];
            @Override
            public void onDrawFrame(GL10 gl) {
//                float[] scratch = new float[16];

                // redraw background color
                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
                ////////////// only the shape without any camera and projection ////////////////
//                mTriangle.draw();
//                mSquare.draw();
                ////////////// only the shape without any camera and projection ////////////////

                ////////////// the shape with camera and projection ////////////////

                // Set the camera position (View matrix)
                Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

                // Calculate the projection and view transformation
                Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

                // Draw shape
//                mTriangle.draw(vPMatrix);
                mSquare.draw(vPMatrix);
                ////////////// the shape with camera and projection ////////////////

                ///////////// shape rotation //////////////
//                // Create a rotation transformation for the triangle
//                long time = SystemClock.uptimeMillis() % 4000L;
//                float angle = 0.090f * ((int) time);
//                Matrix.setRotateM(rotationMatrix, 0, angle, 0, 0, -1.0f);
//
//                // Combine the rotation matrix with the projection and camera view
//                // Note that the vPMatrix factor *must be first* in order
//                // for the matrix multiplication product to be correct.
//                Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0);
//
//                // Draw triangle
////                mTriangle.draw(scratch);
//                mSquare.draw(scratch);

                ///////////// shape rotation //////////////

            }

            // vPMatrix is an abbreviation for "Model View Projection Matrix"
            private final float[] vPMatrix = new float[16];
            private final float[] projectionMatrix = new float[16];
            private final float[] viewMatrix = new float[16];

            public void onSurfaceChanged(GL10 gl, int width, int height) {
                GLES20.glViewport(0, 0, width, height);
                float ratio = (float) width / height;

                // this projection matrix is applied to object coordinates
                // in the onDrawFrame() method
                Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
            }

            public int loadShader(int type, String shaderCode){
                // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
                // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
                int shader = GLES20.glCreateShader(type);

                // add the source code to the shader and compile it
                GLES20.glShaderSource(shader, shaderCode);
                GLES20.glCompileShader(shader);

                return shader;
            }
        }

//        public class Triangle {
//            private FloatBuffer vertextBuffer;
//
//            private FloatBuffer textureBuffer;
//            private int[] textures = new int[1]; // initialise the texture pointer
//
//            private final int mProgram;
//
//            // number of coordinates per vertex in this array
//            static final int COORDS_PER_VERTEX = 3;
////            static float triangleCoords[] = { // in counterclockwise order:
//            float triangleCoords[] = { // in counterclockwise order:
//                    0.0f,  0.622008459f, 0.0f, // top
//                    -0.5f, -0.311004243f, 0.0f, // bottom left
//                    0.5f, -0.311004243f, 0.0f  // bottom right
//            };
//
//            // set color with rgb and alpha (opacity) values
//            float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};
//
//            public Triangle() {
//                // initialize vertex byte buffer for shape coordinates
//                ByteBuffer bb = ByteBuffer.allocateDirect(
//                        // (number of coordinate values * 4 bytes per float)
//                        triangleCoords.length * 4);
//
//                // use the device hardware's native byte order
//                bb.order(ByteOrder.nativeOrder());
//
//                // create a floating point buffer from the ByteBuffer
//                vertextBuffer = bb.asFloatBuffer();
//                // add the coordinates to the FloatBuffer
//                vertextBuffer.put(triangleCoords);
//                // set the buffer tot read the first coordinate
//                vertextBuffer.position(0);
//
//                MyGLRenderer myGLRenderer = new MyGLRenderer();
//
//                int vertexShader = myGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
//                        vertexShaderCode);
//                int fragmentShader = myGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
//                        fragmentShaderCode);
//
//                // create empty OpenGL ES Program
//                mProgram = GLES20.glCreateProgram();
//
//                // add the vertex shader to program
//                GLES20.glAttachShader(mProgram, vertexShader);
//
//                // add the fragment shader to program
//                GLES20.glAttachShader(mProgram, fragmentShader);
//
//                // creates OpenGL ES program executables
//                GLES20.glLinkProgram(mProgram);
//
//            }
//
//            ////////////// only the shape without any camera and projection ////////////////
////            private final String vertexShaderCode =
////                    "attribute vec4 vPosition;" +
////                            "void main() {" +
////                            "  gl_Position = vPosition;" +
////                            "}";
//            ////////////// only the shape without any camera and projection ////////////////
//
//            ////////////// the shape with camera and projection ////////////////
//
//            private final String vertexShaderCode =
//                    // This matrix member variable provides a hook to manipulate
//                    // the coordinates of the objects that use this vertex shader
//                    "uniform mat4 uMVPMatrix;" +
//                            "attribute vec4 vPosition;" +
//                            "void main() {" +
//                            // the matrix must be included as a modifier of gl_Position
//                            // Note that the uMVPMatrix factor *must be first* in order
//                            // for the matrix multiplication product to be correct.
//                            "  gl_Position = uMVPMatrix * vPosition;" +
//                            "}";
//
//            // Use to access and set the view transformation
//            private int vPMatrixHandle;
//
//
//            private final String fragmentShaderCode =
//                    "precision mediump float;" +
//                            "uniform vec4 vColor;" +
//                            "void main() {" +
//                            "  gl_FragColor = vColor;" +
//                            "}";
//
//
//            private int positionHandle;
//            private int colorHandle;
//
//            private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
//            private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
//
//            ////////////// only the shape without any camera and projection ////////////////
//            public void draw() {
//                // Add program to OpenGL ES environment
//                GLES20.glUseProgram(mProgram);
//
//                // get handle to vertex shader's vPosition member
//                positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
//
//                // Enable a handle to the triangle vertices
//                GLES20.glEnableVertexAttribArray(positionHandle);
//
//                // Prepare the triangle coordinate data
//                GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
//                        GLES20.GL_FLOAT, false,
//                        vertexStride, vertextBuffer);
//
//                // get handle to fragment shader's vColor member
//                colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
//
//                // Set color for drawing the triangle
//                GLES20.glUniform4fv(colorHandle, 1, color, 0);
//
//                // Draw the triangle
//                GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
//
//                // Disable vertex array
//                GLES20.glDisableVertexAttribArray(positionHandle);
//            }
//            ////////////// only the shape without any camera and projection ////////////////
//
//            ////////////// the shape with camera and projection ////////////////
//            public void draw(float[] mvpMatrix) { // pass in the calculated transformation matrix
//                // Add program to OpenGL ES environment
//                GLES20.glUseProgram(mProgram);
//
//                // get handle to vertex shader's vPosition member
//                positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
//
//                // Enable a handle to the triangle vertices
//                GLES20.glEnableVertexAttribArray(positionHandle);
//
//                // Prepare the triangle coordinate data
//                GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
//                        GLES20.GL_FLOAT, false,
//                        vertexStride, vertextBuffer);
//
//                // get handle to fragment shader's vColor member
//                colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
//
//                // Set color for drawing the triangle
//                GLES20.glUniform4fv(colorHandle, 1, color, 0);
//
//                // get handle to shape's transformation matrix
//                vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
//
//                // Pass the projection and view transformation to the shader
//                GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0);
//
//                // Draw the triangle
//                GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
//
//                // Disable vertex array
//                GLES20.glDisableVertexAttribArray(positionHandle);
//            }
//            ////////////// the shape with camera and projection ////////////////
//
//        }

        public class Square {
            private final int mProgram;

            private FloatBuffer vertexBuffer;
            private ShortBuffer drawListBuffer;

            // number of coordinates per vertex in this array
            static final int COORDS_PER_VERTEX = 3;
            //            static float squareCoords[] = {
//            float squareCoords[] = {
//                    -0.5f, 0.5f, 0.0f,   // top left
//                    -0.5f, -0.5f, 0.0f,   // bottom left
//                    0.5f, -0.5f, 0.0f,   // bottom right
//                    0.5f, 0.5f, 0.0f}; // top right
//            private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices



            ////////////////////// texturing //////////////////////
            private FloatBuffer textureBuffer;
            private int[] textureIntCoords = new int[1];

            // texture coordinate is different
             float squareCoords[] = {
                -0.5f, 0.5f, 0.0f,   // top left
                0.5f, 0.5f, 0.0f,   // top right
                -0.5f, -0.5f, 0.0f,   // bottom left
                0.5f, -0.5f, 0.0f,   };// bottom right

            float textureCoords[] = {
                    1f, 0f, 0.0f, // top right
                    0f, 0f, 0.0f, // top left
                    1f, 1f, 0.0f, // bottom right
                    0f, 1f, 0.0f, };// bottom left

            private short drawOrder[] = { 0,2,3,0,3,1 }; // order to draw vertices

            ////////////////////// texturing //////////////////////

            float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};


            public Square() {
                // initialize vertex byte buffer for shape coordinates
                ByteBuffer bb = ByteBuffer.allocateDirect(
                        // (# of coordinate values * 4 bytes per float)
                        squareCoords.length * 4);
                bb.order(ByteOrder.nativeOrder());
                vertexBuffer = bb.asFloatBuffer();
                vertexBuffer.put(squareCoords);
                vertexBuffer.position(0);

                /////////////// texturing /////////////
                ByteBuffer tbb = ByteBuffer.allocateDirect(textureCoords.length * 4);
                tbb.order(ByteOrder.nativeOrder());
                textureBuffer = tbb.asFloatBuffer();
                textureBuffer.put(textureCoords);
                textureBuffer.position(0);
                /////////////// texturing /////////////

                // initialize byte buffer for the draw list
                ByteBuffer dlb = ByteBuffer.allocateDirect(
                        // (# of coordinate values * 2 bytes per short)
                        drawOrder.length * 4);
                dlb.order(ByteOrder.nativeOrder());
                drawListBuffer = dlb.asShortBuffer();
                drawListBuffer.put(drawOrder);
                drawListBuffer.position(0);

                MyGLRenderer myGLRenderer = new MyGLRenderer();

                int vertexShader = myGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                        vertexShaderCode);
                int fragmentShader = myGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                        fragmentShaderCode);

                // create empty OpenGL ES Program
                mProgram = GLES20.glCreateProgram();

                // add the vertex shader to program
                GLES20.glAttachShader(mProgram, vertexShader);
                // add the fragment shader to program
                GLES20.glAttachShader(mProgram, fragmentShader);

                // creates OpenGL ES program executables
                GLES20.glLinkProgram(mProgram);
            }

            ////////////// only the shape without any camera and projection ////////////////
//            private final String vertexShaderCode =
//                    "attribute vec4 vPosition;" +
//                            "void main() {" +
//                            "  gl_Position = vPosition;" +
//                            "}";
            ////////////// only the shape without any camera and projection ////////////////

            ////////////// the shape with camera and projection ////////////////

            //////////////////// shader and fragmentShader setting without texturing feature ///////////////////
//            private final String vertexShaderCode =
//                    // This matrix member variable provides a hook to manipulate
//                    // the coordinates of the objects that use this vertex shader
//                    "uniform mat4 uMVPMatrix;" +
//                            "attribute vec4 vPosition;" +
//                            "void main() {" +
//                            // the matrix must be included as a modifier of gl_Position
//                            // Note that the uMVPMatrix factor *must be first* in order
//                            // for the matrix multiplication product to be correct.
//                            "  gl_Position = uMVPMatrix * vPosition;" +
//                            "}";
//
//            private final String fragmentShaderCode =
//                    "precision mediump float;" +
//                            "uniform vec4 vColor;" +
//                            "void main() {" +
//                            "  gl_FragColor = vColor;" +
//                            "}";

            //////////////////// shader and fragmentShader setting without texturing feature ///////////////////


            // Use to access and set the view transformation
            private int vPMatrixHandle;

            ////////////// shader and fragmentShader setting with texturing ///////////////////
            private final String vertexShaderCode =
                    // This matrix member variable provides a hook to manipulate
                    // the coordinates of the objects that use this vertex shader
                    "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec2 vTexPosition;   //texture position \n" +
                    "varying vec2 v_texPo;  //texture position interacting with fragment_shader \n" +
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  v_texPo = vTexPosition;" +
                    "}";
            private final String fragmentShaderCode =
                    "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "uniform sampler2D sTexture;" +
                    "varying vec2 v_texPo;//texture position getting from the vertex_shader \n" +
                    "void main() {" +
                    "  // gl_FragColor = vColor; \n" +
                    "  gl_FragColor = texture2D(sTexture, v_texPo);" +
                    "}";
            ////////////// shader and fragmentShader setting with texturing ///////////////////

            private int positionHandle;
            private int texPositionHandle;
            private int colorHandle;

//            private final int vertexCount = squareCoords.length / COORDS_PER_VERTEX;
            private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

            ////////////// texturing ////////////////
            public void loadGLTexture(Context context) {
                Bitmap bmImg = null;
                URL url = null;
                InputStream is = null;

                /////////////////////// loading image url to the bitmap ///////////////////////////
                try {
//                    url = new URL ("https://vavideos.blob.core.windows.net/userads/cf221b7b-9ec7-40ec-ad78-7fc41d5819cd.png");
                    url = new URL ("http://adblock.eastasia.cloudapp.azure.com/upload/2.png");
                } catch (Exception e) {
                    Log.i("urlllllllllllll",e.getMessage());
                }

                // specify the url is using http or https proxy
                if (url.toString().startsWith("http:")) {
                    try {
                        HttpURLConnection http = (HttpURLConnection) url.openConnection();
                        http.setDoInput(true);
                        http.connect();
                        is = http.getInputStream();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (url.toString().startsWith("https:")) {
                    try {
                        HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
                        https.setDoInput(true);
                        https.connect();
                        is = https.getInputStream();
                    } catch (Exception e) {
                        e.printStackTrace();
                }

                }
                try {
                    bmImg = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // generate one texture pointer
                GLES20.glGenTextures(1, textureIntCoords, 0);
                // bind the texture pointer to the array
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIntCoords[0]);

                // create nearest filtered texture
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

                // different possible texture parameters
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

                // specify a 2d texture image from bitmap
                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmImg, 0);

                // clean up
                bmImg.recycle();
            }

            //////////////////////////// texturing /////////////////////////////

            ////////////// only the shape without any camera and projection ////////////////
            public void draw() {
                // Add program to OpenGL ES environment
                GLES20.glUseProgram(mProgram);

                // get handle to vertex shader's vPosition member
                positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

                // Enable a handle to the triangle vertices
                GLES20.glEnableVertexAttribArray(positionHandle);

                // Prepare the triangle coordinate data
                GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                        GLES20.GL_FLOAT, false,
                        vertexStride, vertexBuffer);

                // get handle to fragment shader's vColor member
                colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

                // Set color for drawing the square
                GLES20.glUniform4fv(colorHandle, 1, color, 0);

                // Draw the square
                GLES20.glDrawElements(GLES20.GL_TRIANGLES, squareCoords.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
                // Disable vertex array
                GLES20.glDisableVertexAttribArray(positionHandle);

            }
            ////////////// only the shape without any camera and projection ////////////////

            ////////////// the shape with camera and projection ////////////////
            public void draw(float[] mvpMatrix) {

                //////////////// texturing /////////////////
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIntCoords[0]);
                //////////////// texturing /////////////////

                // Add program to OpenGL ES environment
                GLES20.glUseProgram(mProgram);

                // get handle to vertex shader's vPosition member
                positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

                // get handle to vertex shader's calling id for texture position
                texPositionHandle = GLES20.glGetAttribLocation(mProgram, "vTexPosition");

                // Enable a handle to the square vertices
                GLES20.glEnableVertexAttribArray(positionHandle);
                // Prepare the square coordinate data
                GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                        GLES20.GL_FLOAT, false,
                        vertexStride, vertexBuffer);

                // Enable a handle to the square texturing vertices
                GLES20.glEnableVertexAttribArray(texPositionHandle);
                // Prepare the texture coordinate data
                GLES20.glVertexAttribPointer(texPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, textureBuffer);

                // get handle to fragment shader's vColor member
                colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

                // Set color for drawing the square
                GLES20.glUniform4fv(colorHandle, 1, color, 0);

                // get handle to shape's transformation matrix
                vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

                // Pass the projection and view transformation to the shader
                GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0);

                // Draw the square
//                GLES20.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,4);
                GLES20.glDrawElements(GLES20.GL_TRIANGLES, squareCoords.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

                // Disable vertex array
                GLES20.glDisableVertexAttribArray(positionHandle);

            }
            ////////////// the shape with camera and projection ////////////////

        }

    }
}

