TDT4195 Lab 4 
=============
**Author:** Sandor Zeestraten - zeestrat@stud.ntnu.no

Tasks
-----
#### 1: Rotate HIP joints so that they rotate like LEG 1 and LEG 2

In order to rotate the hip joints in unison with the legs we can just multiply the MVP of each hip with with the `MODEL_LEG` model. Note that I had to swap signs of the legs as to make sure it held the same convention as the hip joints where the first hip joint was nearest to the screen.

#### 2: Replace the LEGS by a new model

Here I defined a new set of points in the VBO `g_vertex_buffer_data` array so we would use something else than the cube.
I decided to create a prism shape defined by 10 triangles. We must then set the offset in the `glDrawArrays` function to use the new points such as below.

```
glDrawArrays(GL_TRIANGLES, 12*3, 10*3);
```

In order to make the prism look more like legs, it had to be translated, scaled and rotated like the original legs.
To make the feet, I used the same prism just scaled to be blockier and sit at the end of the legs.

#### 3: Change color of objects
In order to set the color of the different objects I created different color arrays such as `g_yellow_buffer_data` just like `g_color_buffer_data` with hardcoded RGB values. Then to use them on the objects, we just needed to bind to the approriate buffer such as below.

```
glEnableVertexAttribArray(1);
glBindBuffer(GL_ARRAY_BUFFER, greenBuffer);
```

#### 4: Create RenderScene6 where the camera must follow the moving legs
When the RenderScene6 is active, we follow the moving legs in the `Idle` function so the model does not run off screen by updating the position with the same direction that the counter which is used when moving/translating the model. It uses the same logic which moves the position based on the arrow keys on the keyboard earlier in the function.

```
position += glm::vec3(0, 0, 0.0013 * counter);
```
