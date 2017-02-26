This code is not 100/100

There are something wrong with the isFUll() method.

Because this method is using quick union UF
There are two side, one is connected with the top. The other is connected with bottom. So once the top and the bottom are connected, the others which are connected with the bottom but not the top is also be considered as Full. However, it should not be Full. This is like the water come back to the top. That is the problem That is the problem.

# this does not work
There is one way to do this.
set n bottoms so each bottom will have special bottom. So the water will not come back to the top.




