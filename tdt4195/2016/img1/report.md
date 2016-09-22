TDT4195 Digital Image Processing Assignment 1
=============
**Author:** Sandor Zeestraten - zeestrat@stud.ntnu.no

Theory
-----
#### 1: How can you see that an image has low contrast when looking at its histogram?
The width of the histograms will correlate to the amount of contrast an image has. Broad histograms have higher contrast while narrow have lower contrast.

#### 2: Histogram equalization is an algorithm that creates a transformation Theq by exploiting an image histogram. What effect does Theq have when applied on an image? What happens when Theq is applied multiple times on the same image?
It will equalize the contrast levels of the image. Equalizing an already equalized image will have no effect.

#### 3: Perform histogram equalization by hand on the 3-bit (8 intensity levels) 3 � 5 image in Figure 1. Your report must include the transformed image and its histogram.

| Level                 | 0 | 1    | 2    | 3    | 4    | 5     | 6     | 7     |
|-----------------------|---|------|------|------|------|-------|-------|-------|
| No. pixels            | 0 | 1    | 0    | 1    | 5    | 6     | 2     | 0     |
| Cumulative            | 0 | 1    | 1    | 2    | 7    | 13    | 15    | 15    |
| Normalized            | 0 | 1/15 | 1/15 | 2/15 | 7/15 | 13/15 | 15/15 | 15/15 |
| Scaled by 7 (mapping) | 0 | 1    | 1    | 1    | 4    | 7     | 7     | 7     |

| New image |   |   |   |   |   |
|-----------|---|---|---|---|---|
|           | 4 | 1 | 7 | 7 | 1 |
|           | 4 | 7 | 4 | 7 | 4 |
|           | 7 | 7 | 7 | 4 | 7 |

#### 4: What is the main difference between convolution and correlation? Could you mention one or more convolution kernels that yield the same result for both convolution and correlation? What is common to these kernels?
Convolution is an linear operation whereas correlation measures the similarity of two signals. Symmetric kernels such as the Gaussian kernel will yield the same results for convolution and correlation because they are symmetric.
