function [ result ] = regiongrowing( im, seed, threshold )

[col, row] = size(im);
old_seed = zeros(col, row);

% 4-connected
neighbor = [-1 0; 1 0; 0 -1; 0 1];

% Stop when there's no difference
while(~isequal(seed, old_seed))
    old_seed = seed;
    
    for i = 1:col
        for j = 1:row
            if seed(i,j)
                % Go through 4-connected neighbors
                for k = 1:length(neighbor)
                    % Get neighbor coordinates
                    x = i + neighbor(k,1); y = j + neighbor(k,2);
                    % Check if in bounds
                    if (x >= 1 && y >= 1 && x <= col && y <= row)
                        % If difference is less than threshold, add it
                        if abs(seed(i,j) - im(x,y)) < threshold
                           seed(x,y) = 1;
                        end
                    end
                end
            end
        end
    end
    
end
result = seed;
end

