function [ out ] = threshold( img, threshold )

% Initial threshold
T = mean(img(:));
g1 = img(img > T);
g2 = img(img <= T);
T_new = (mean(g1(:)) + mean(g2(:))) / 2;

while abs(T-T_new) > threshold
    T = T_new;
    g1 = img(img > T);
    g2 = img(img <= T);
    T_new = (mean(g1(:)) + mean(g2(:))) / 2;
end
out = img > T;
end

