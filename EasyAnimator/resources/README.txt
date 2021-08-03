The two animations created for this assignment are "animation1.txt" and "animation2.txt"
- They are in the inputs directory.
- "animation1.txt" is a programmatically generated selection sort algorithm.
- "animation2.txt" is a manually written four player pong game.
Screenshot of Interactive View GUI is in resources directory
- It is a screenshot of the buildings.txt file paused at a random point during the animation
- "OOD_HW7_InteractiveViewScreenshot.png"
Jar file is in resources folder

CHANGES/ADDITIONS:
- Created CompositeView class to represent an interactive view with playback controls.  Implements a
GUI using Java Swing with a 'Start' button, which becomes a 'Restart' button, 'Resume' button,
'Pause' button, speed slider, toggle 'Loop' checkbox, a speed display, and a text display that shows
which button has just been pressed.
- Created the interface IAbstractDrawPanel and abstract class AbstractDrawPanel to abstract the
duplicated code within the draw panel classes for CompositeView and VisualViewI.  AbstractDrawPanel
is used to create the draw panels in views with visual displays (CompositeView and VisualViewI).
This is extended to the classes DrawPanelComposite and DrawPanelVisual classes for them to override
the actionPerformed method and implement the appropriate corresponding functionality for that view.
- Created abstract class AbstractViews, which implements IAnimation and is extended by views with
visual displays (CompositeView and VisualViewI).  Contains the default functionality of a non-
interactive visual view and is overwritten by CompositeView to implement the additional
functionality that comes along with the interactive GUI.  AbstractViews determines what type of draw
panel to use with the abstract createDrawPanel method.  This method is required to be overwritten by
extending classes and sets the AbstractDrawPanel used in the animation the proper view's draw panel.
- Adjusted the code in all classes that were switched to extend abstract classes accordingly by
removing the duplicated code that was abstracted, calling super in the constructors, and overriding
methods when necessary.
- Created the GenerateAnimation class, which programmatically generates an animation that sorts an
array of blocks (rectangles) in ascending height order using the selection sort algorithm.  The
entire animation is generated when the constructor of GenerateAnimation is called, so it contains a
static method, generateAnimation, It is currently set to write to a file called 'animation1.txt'
Additionally, it is currently implemented such that it can take in a size of the array to be
generated and the maximum value of an element in that array, or take in no arguments and use
arbitrarily decided default values as parameters for the array (size = 20, max value = 100).
- Created an animation file in the input directory called "animation2.txt" This animation was
manually written and displays a four player pong game.  Contains at least 5 shapes (4 paddles and
the pong ball).
- Added class Hw7Tests which contain unit tests on our controller and listeners.