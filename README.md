# geneticAlgorithm
A genetic algorithm resolving 3 different functions. Parameters for functions may be commented out, function 3 is currently set.
The functions are:

1 - 	f(x) = x2

2 -   f(x,y) = 0.26.( x2 + y2 ) – 0.48.x.y

3 - 	f(x) = 10n + ∑[ni](i=1)^(n[i])^2 – 10.cos(2pi.x[i])   a.k.a. The Rastrigin Function

Run from population.java as this contains main thread.
Please note there may be some strange standards with this repo, this was created as an assignment towards a degree module and therefore may have been created in alignment with the outline provided by the lecturer.
For example; each function solution should be 3 individual programs but is in 1 for ease of marking for lecturer, commenting was not required for this assignment as we had a verbal demonstration to prove knowledge of the code and so not included.

Also note that functions 1 and 2 are solvable within reasonable time by the GA however the third takes time and is random, hence setting the iterations to 50000. This can take 2000 or 200000 depending on which of the local minimums the GA falls into.
Once stuck the GA applies a larger than normal mutation value to jump from that local minimum.
