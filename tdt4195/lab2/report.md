### Explain why the Scene 1,2,3 and 4 are displayed wrong after the user enables scene 5.
When rendering the scene 5, the shader pipeline is used which affects all scenes and the viewport is changed. After this, the view for the previous scenes will be different as can be seen in the teapot example.

### Change point and colour of triangle in Scene 5
I changed the *color* variable in [SimpleFragmentShader.fragmentshader](./bin/SimpleFragmentShader.fragmentshader) to blue.

To make sure one of the vertices of the new triangle is in the top left corner I edited *g_vertex_buffer_data* in [visuals.cpp](visuals.cpp).

### Add a new scene with 3 or more none overlapping triangles
Added the *RenderScene6* function in both [visuals.cpp](visuals.cpp) and [visuals.h](visuals.h). In order to draw more triangles, I just added 6 more vertices in *g_vertex_buffer_data* and changed the amount of indices in the *glDrawArrays* function so we draw 3 separate triangles.
