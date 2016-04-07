# dslr.skycam
dslr.skycam is a small project to make your regular DSLR camera into a Sky Cam.  
It utilizes the [gPhoto2](http://www.gphoto.org/) library to control the camera and [ImageMagick](www.imagemagick.org) for some simple editing.  

## Requirements
* gPhoto2
* ImageMagick

## Usage
### Camera configuration
Please visit [gphoto.org/doc/remote/](http://www.gphoto.org/doc/remote/) to check if your camera is supported.  
For an interactive setup of your camera, run `gphoto2 --config`.  

### Image capture
To capture an image, simply run `capture.sh`.  
After it has been captured, ImageMagick will resize it and stamp some information on the top.  
You are then free to view the image called `skycam.jpg` in the main folder.  

### Uploading with ftp
Update the index.html page with any news you might have and then just run `upload.sh`
