#!/bin/bash
TEXT="Polarlightcenter.com Sky Cam"
TIMESTAMP=$(date +"%Y-%m-%d-%H:%M")
VISUALTIMESTAMP=$(date +"%d/%m/%Y %H:%M %:z UTC")
FILENAME="skycam.jpg"

# Captures photo
TMPFILE="capture.jpg"
gphoto2 --filename $TMPFILE --force-overwrite --capture-image-and-download

# Resize and timestamp
convert $TMPFILE -scale 800x600 -font Arial -pointsize 12 -fill black -draw "rectangle 0,0 800,15" -draw "gravity northwest fill white text 3,3 '$TEXT $VISUALTIMESTAMP'" $FILENAME

# Clean up
rm $TMPFILE
