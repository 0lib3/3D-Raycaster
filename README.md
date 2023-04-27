# Java Built 3D-Raycaster

This 3D raycaster features a split-screen JPanel with a 2D projection of a field populated with collidable objects on one side, and the 3D representation of this on the
other. The 3D raycaster algorithm uses both 2D and 3D signed distance functions (SDFs) in order to calculate the distance from one point on the ray (a starting coordinate) 
to the nearest collidable object within the field.

Collision is viewable from the 2D representation, as the camera (user's cursor) fires ~640 rays to simulate an FOV of around 70 pixels.

## Demo
![raycasterDemo](https://user-images.githubusercontent.com/130253308/234984493-3036b660-f68b-4c56-a458-2ed2fd0167db.gif)

## Authors
* [@0lib3](https://github.com/0lib3)
* [@jetsonblack](https://github.com/jetsonblack)

## Features
* Real Time Calculations and Visualizations based on Mouse Movement
* Camera angle can be changed by both left and right arrow keys
* Random Generation of 15 polygons in the field


