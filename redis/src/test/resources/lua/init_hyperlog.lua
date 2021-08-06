local trueAndFalse = {true, false}

for j=1,5,1 do
    for i=1,1000000,1 do
        if trueAndFalse[math.random(1, 2)] then
            redis.call('PFADD',j,i)
        end
    end
end
