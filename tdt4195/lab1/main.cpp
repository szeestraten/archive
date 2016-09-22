#include <GL/glut.h>
#include <stdio.h>
#include "visuals.h"
 
//Main program
int main(int argc, char **argv) {
 
    glutInit(&argc, argv);

    glutInitDisplayMode(GLUT_RGBA|GLUT_DOUBLE);
     
    //Configure Window Postion
    glutInitWindowPosition(50, 25);
     
    //Configure Window Size
    glutInitWindowSize(480,480);

    //Create Window
    glutCreateWindow("Hello OpenGL");

    //Visuals Setup
    Setup();
    glutDisplayFunc(Render);
    glutReshapeFunc(Resize);
     
    //Loop require by OpenGL
    glutMainLoop();
    return 0;
}
