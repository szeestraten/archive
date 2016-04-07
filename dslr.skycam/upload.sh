#!/bin/bash

FTP_SERVER="ftp.polarlightcenter.com"
# FTP username
FTP_USER=""
# FTP password
FTP_PASS=""
FTP_DIR="/public/sites/www.polarlightcenter.com/allskycam"
PHOTO="skycam.jpg"
PAGE="index.html"

# Upload
ftp -inv $FTP_SERVER<<ENDFTP
user $FTP_USER $FTP_PASS
cd $FTP_DIR
put $PHOTO
put $PAGE
bye
ENDFTP
