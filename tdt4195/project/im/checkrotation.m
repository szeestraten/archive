function [ result ] = checkrotation( im )
%CHECKROTATION Check the rotation of the input image
%   If the mean value for each corner tile is under 0.5 we report the image
%   as rotated 180 degrees. We do not check for the 90 degree case.

    R=im(:,:,1);
    G=im(:,:,2);
    B=im(:,:,3);

    im = R;
    [x,y] = size(im);

    i = 0;
    j = 0;
    subim = im(round(1+(i*x/5)):round((i+1)*x/5),round(1+(j*y/8)):round((j+1)*y/8),1);
    T1 = mean(subim(:));

    i = 4;
    j = 0;
    subim = im(round(1+(i*x/5)):round((i+1)*x/5),round(1+(j*y/8)):round((j+1)*y/8),1);
    T2 = mean(subim(:));

    im = B;

    i = 0;
    j = 7;
    subim = im(round(1+(i*x/5)):round((i+1)*x/5),round(1+(j*y/8)):round((j+1)*y/8),1);
    T3 = mean(subim(:));

    i = 4;
    j = 7;
    subim = im(round(1+(i*x/5)):round((i+1)*x/5),round(1+(j*y/8)):round((j+1)*y/8),1);
    T4 = mean(subim(:));

    if T1 < 0.5 && T2 < 0.5 && T3 < 0.5 && T4 < 0.5
        result = 1;
    else
        result = 0;
    end

end

