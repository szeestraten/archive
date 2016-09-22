function [ out ] = conv3( input, kernel )

for i = length(kernel) : length(input) - (length(kernel)-1)
    for j = (length(kernel)-1) : length(input) - (length(kernel)-1)
        submat = input(i:i + (length(kernel)-1), j:j + (length(kernel)-1));
        res = submat(:) .* kernel(:);
        out(i, j) = sum(res);
    end
end

end