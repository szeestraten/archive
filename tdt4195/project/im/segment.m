function [ chiptype, xcoordinate, ycoordinate ] = segment( im, threshold, colorchannel )
%SEGMENT Block based processing of board image.
% Loops through each tile looking for red and white chips.

% Debug tile
debugi = -1;
debugj = -1;

[x,y] = size(im);

% Initialises result matrices
chiptype = zeros(5,8);
xcoordinate = zeros(5,8);
ycoordinate = zeros(5,8);

% Constants
red = 0;
blue = 1;
green = 2;

% Loops through tile by tile on the board for processing.
for i=0:4
  for j=0:7

    % Debug
    if debugi == i && debugj == j
      % Cleaning the figure
      clf
    end

    % Finds the color of the tile
    if bitget(i,1) == 0
        if bitget(j,1) == 0
            color = red;
        else
            color = blue;
        end
    else
        if bitget(j,1) == 1
            color = red;
        else
            color = blue;
        end
    end

    % Split the image in sub images for each tile.
    subim = im(round(1+(i*x/5)):round((i+1)*x/5),round(1+(j*y/8)):round((j+1)*y/8),1);

    % Cropping the sub image to compensate for bad angle when the picture was shot.
    subim = imcrop(subim,[10 10 90 90]);

    % Run histogram equalization for red channel. The is necessary to be able
    % to split red chips from red tiles.
    if colorchannel == red
        subim = histeq(subim);
    end

    % Debug
    if debugi == i && debugj == j
        % Plots the original tile with histogram
        subplot(3,2,1)
        imshow(subim)
        title('Orginal rute')
        subplot(3,2,2)
        imhist(subim)
        title('Histogram for rute')
    end

    % Dynamically calculate the threshold for binary segmentation.
    if colorchannel == red
        threshold = graythresh(subim);
    end
    bwsubim = im2bw(subim,threshold);

    % The sub image is inverted if it contains to much white. If not the
    % erosion fails in the next step.
    l = length(bwsubim(:));
    s = sum(bwsubim(:)==1);
    ratio = 2.5;

    % The green color channel needs a different ratio.
    if colorchannel == green
        ratio = 2.0;
    end

    % Debug code
    if debugi == i && debugj == j
        disp(l)
        disp(s)
        disp(ratio)
    end

    % Inverting the sub image.
    if l/ratio < s
        bwsubim = ~bwsubim;
        if debugi == i && debugj == j
            disp('Invert picture based on ratio.')
        end
    end

    % Debug
    if debugi == i && debugj == j
        % Plotting the binary image.
        subplot(3,2,3)
        imshow(bwsubim)
        title('Binary image')
    end

    % Runs erosion on the sub image.
    mask = strel('square', 42);
    bwsubim = imerode(bwsubim, mask);

    % Debug
    if debugi == i && debugj == j
        subplot(3,2,4)
        imshow(bwsubim)
        title('Erodert bilde')
    end

    % Calculating distance transform
    D = bwdist(~bwsubim,'chessboard');

    % Debug
    if debugi == i && debugj == j
        subplot(3,2,5)
        imshow(D,[0,max(D(:))])
        title('Distance transform')
    end

    % Crop image to ignore false positives on the boarders.
    Z = imcrop(D,[10 10 80 80]);

    % Debug
    if debugi == i && debugj == j
        subplot(3,2,6)
        imshow(Z,[0,max(D(:))])
    end

    % Debug code
    if debugi == i && debugj == j
        maxd = max(D(:))
        maxz = max(Z(:))
    end

    if max(D(:)) > 0 && max(D(:)) == max(Z(:))
        % Red channel
        if color==red && colorchannel==red
            [rows,cols] = find(D >= max(D(:)));
            % Selecting the "middle" pixel.
            r = ceil(length(rows)/2);
            c = ceil(length(cols)/2);
            % Update the result matrices.
            xcoordinate(i+1,j+1) = rows(r);
            ycoordinate(i+1,j+1) = cols(c);
            chiptype(i+1,j+1) = 1;
        end

        % Blue channel
        if color==blue && colorchannel==blue
            [rows,cols] = find(D >= max(D(:)));
            % Selecting the "middle" pixel.
            r = ceil(length(rows)/2);
            c = ceil(length(cols)/2);
            % Update the result matrices.
            xcoordinate(i+1,j+1) = rows(r);
            ycoordinate(i+1,j+1) = cols(c);
            chiptype(i+1,j+1) = 1;
        end

        % Green channel
        if colorchannel==green
            % White chip on red or blue tile
            [rows,cols] = find(D >= max(D(:)));
            % Selecting the "middle" pixel.
            r = ceil(length(rows)/2);
            c = ceil(length(cols)/2);
            % Update the result matrices.
            xcoordinate(i+1,j+1) = rows(r);
            ycoordinate(i+1,j+1) = cols(c);
            chiptype(i+1,j+1) = -1;
        end
    end

    % Stepping
    if debugi == i && debugj == j
        bleh = input('Waiting...');
    end

  end
end

end

