# TDT4195 Project Report
#### Authors: Tom-Erik Røberg, Sandor Zeestraten

## Digital Image processing
The image processing is implemented in Matlab with use of functions mostly from the image toolbox. The output is a comma separated file.

### Description
Each color channel is processed separately which finds different chip and tile color combinations. For each color channel the image is split into sub images for each tile on the board.

Each sub image is then processed to find chips. The image is converted to a binary image with different threshold per color channel (for the red channel we calculate it dynamically for each tile). Based on a ratio we invert the binary image if necessary to ensure that the erosion step for removing noise preforms correctly.

To remove noise in the image and to not get false positives for other objects on the board we run erosion on the image with a large mask. This works because the chip is very large compared to the smaller noise objects.

After the doing the erosion the result image is a binary image with a withe circle in it. We calculate a distance transform on this image to find the center of the circle. This gives us the center pixel coordinates.

### How to run.

Run im_seq.m in Matlab. This will process image 1. Change line number 8 to process one of the other.

### Limitations
* Does not handle images rotated 90 degrees.
* Does not handle boards with different number of tiles.
* Does not handle boards with other or more colors on the tiles or chips.
* Very large noise objects could be detected as a chip.


## Graphics
For the graphics part of the project, we chose to use WebGL which is a JavaScript API based on OpenGL ES 2.0 which works in the browser. We've created a simple 3D scene with a 8x5 checker board as seen in our image sources with controllable checker pieces positioned as found by our image processing. Our implementation resulted in a HTML file with some extra external resources such as JavaScript libraries and textures.

### How to run
Just open the [index.html](./opengl/index.html) in a modern version of Firefox on computer that supports modern OpenGL. Then upload one of the outputs from the image processing part such as [output.csv](./output.csv).

### Implementation details 
##### Data upload
In order to use the data from the image processing part, the file must be uploaded via the browser. This is handled with [jQuery][3] and [PapaParse][4]. The data contains the position (including offsets from center) and the color of the checker pieces.

##### Checker board logic
We use a global 8x5 2D array called `grid` as our representation of the checker board. It gets initialized when new data files are uploaded with individual Javascript objects which contain the attributes such as *color, position, offset*. These objects are also in a regular 1D array which we iterate over in a round-robin fashion to determine which checker piece we currently control. When moving a piece, we check if the new location is empty and within the 8x5 boundaries.

##### 3D Models
For the 3D scene, we use two different models drawn with triangles. A cube which is transformed into a rectangle for the checker board grid. For the checker pieces we use spheres that are flattened. The cube has hardcoded vertices, while they are generated for the sphere. In order to draw the checkers board grid, we use a 256x256 pixel texture with red and blue colors that is repeated on the transformed cube so it matches the image source. For the checker pieces we instead use a simple 1x1 white texture. This is so we can manipulate the texture color to easily get solid colors without changing our shaders.

##### Limitations
* The movement of the individual checkers pieces are not animated smoothly.
* The correct centered position of the checker pieces are not very accurate due to limitations in our image processing part.
* We did not manage to use two different models for the different checker pieces. We tried to import a model from Sketchup and Blender, but ran into some issues.
* Tested only in Firefox version 42.0+

### Resources
##### External libraries
* [glMatrix - Javascript matrix and vector library for WebGL][1]
* [webgl-utils.js - Helper methods for setting up WebGL][2]
* [jQuery - For handling the uploading of the CVS file][3]
* [Papa Parse - For parsing the CVS file][4]

##### Learning material
* [Learning WebGL - A collection of WebGL lessons which we've used some code from][5]

[1]: http://glmatrix.net/
[2]: https://code.google.com/p/webglsamples/source/browse/book/webgl-utils.js?r=41401f8a69b1f8d32c6863ac8c1953c8e1e8eba0
[3]: https://jquery.com/
[4]: http://papaparse.com/
[5]: http://learningwebgl.com
