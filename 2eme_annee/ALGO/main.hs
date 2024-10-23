factorial :: Integer -> Integer
factorial 0 = 1
factorial n = n * factorial (n-1)


neg x = x < 0

myAbs x 
   | x > 0     = x  
   | otherwise = x 

sumP [] = 0
sumP (x:xs)
 | x > 0 = 1 + sumP xs
 | otherwise = sumP xs