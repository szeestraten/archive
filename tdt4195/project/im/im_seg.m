%% Main

% Loading images
im1 = im2double(imread('image1.png'));
im2 = im2double(imread('image2.png'));
im3 = im2double(imread('image3.png'));

im = im1;

if checkrotation(im) == 1
    im = imrotate(im,180);
end

R=im(:,:,1);
G=im(:,:,2);
B=im(:,:,3);

% 1 = red chip
% -1 = white chip

red = 0;
blue = 1;
green = 2;

% Processing blue channel
% Finds red chips on blue tile
disp('Processing blue channel');
im = B;
colorchannel = blue;
threshold = 0.2;
[chiptype1, xcoordinate1, ycoordinate1] = segment(im, threshold, colorchannel);

% Processing red channel
% Finds red chips on red tile.
disp('Processing red channel')
im = R;
colorchannel = red;
threshold = 0.5;
[chiptype2, xcoordinate2, ycoordinate2] = segment(im, threshold, colorchannel);

% Processing green channel
% Finds white chips on blue and red tile
disp('Processing green channel')
im = G;
colorchannel = green;
threshold = 0.5;
[chiptype3, xcoordinate3, ycoordinate3] = segment(im, threshold, colorchannel);

chiptype = chiptype1 + chiptype2 + chiptype3;
xcoordinate = xcoordinate1 + xcoordinate2 + xcoordinate3;
ycoordinate = ycoordinate1 + ycoordinate2 + ycoordinate3;

result(:,:,1) = chiptype;
result(:,:,2) = xcoordinate;
result(:,:,3) = ycoordinate;

% Writing result to cvs for export to OpenGL rendering.
csvwrite('output.csv',result)

%% Test results

im1result = [
    0 0 0 0 0 0 0 0;
    0 0 1 0 0 0 0 0;
    0 0 0 1 0 -1 0 0;
    0 -1 0 -1 0 0 0 0;
    0 0 0 0 0 0 0 0 ];

im2result = [
    0 0 0 0 0 0 0 0;
    0 0 1 0 0 0 0 0;
    0 0 0 0 1 -1 -1 0;
    0 0 -1 0 0 0 0 0;
    0 0 0 0 0 0 0 0 ];

im3result = [
    0 0 0 0 0 0 0 0;
    0 0 1 0 -1 0 0 0;
    0 0 0 1 0 -1 0 0;
    0 -1 0 0 0 0 0 0;
    0 0 0 0 0 0 0 0 ];

if im1result == chiptype
    display('Correct chips found in image 1')
end

if im2result == chiptype
    display('Correct chips found in image 2')
end

if im3result == chiptype
    display('Correct chips found in image 3')
end
