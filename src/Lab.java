
import java.awt.*;
import java.util.*;

public class Lab
{
  //Step 4,6
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int ALIVE = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  
  //do not add any more fields below
  private int[][] grid;
  private Display display;
  
  
  /**
   * Constructor for SandLab
   * @param numRows The number of rows to start with
   * @param numCols The number of columns to start with;
   */
  public Lab(int numRows, int numCols)
  {
    String[] names;
    // Change this value to add more buttons
    //Step 4,6
    names = new String[4];
    // Each value needs a name for the button
    names[EMPTY] = "Empty";
    names[ALIVE] = "Alive";
//    names[SAND] = "Sand";
//    names[WATER] = "Water";
    
    //1. Add code to initialize the data member grid with same dimensions
    grid = new int[numRows][numCols];
    display = new Display("Game of Life", numRows, numCols, names);
    
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
    //2. Assign the values associated with the parameters to the grid
   grid[row][col]= tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
	  int randomR = (int) (255*Math.random());
	  Color randomColor = new Color((int) (255*Math.random()), (int) (255*Math.random()), (int) (255*Math.random()));
      //Step 3
   //Hint - use a nested for loop
	  for(int i = 0; i < grid.length; i++)
	  {
		  for(int j = 0; j < grid[0].length; j++)
		  {
			  if(grid[i][j] == ALIVE)
			  {
				  display.setColor(i,j,randomColor);
			  }
			  else if(grid[i][j] == EMPTY)
			  {
				  display.setColor(i,j,Color.BLACK);
			  }
			  else if(grid[i][j] == SAND)
			  {
				  display.setColor(i,j,Color.YELLOW);
			  }
			  else if(grid[i][j] == WATER)
			  {
				  display.setColor(i,j,Color.BLUE);
			  }
		  }
	  }
  }

  //Step 5,7
  //called repeatedly.
  //causes one random particle in grid to maybe do something.
  public void step()
  {
    //Remember, you need to access both row and column to specify a spot in the array
    //The scalar refers to how big the value could be
    //int someRandom = (int) (Math.random() * scalar)
    //remember that you need to watch for the edges of the array
//	  int randRow = (int) (Math.random() * grid.length);
//	  int randCol = (int) (Math.random() * grid[0].length);
//	  int randDirection = (int) (Math.random() * 3);

	  for(int i = 0; i < grid.length; i++)
	  {
		  for(int j = 0; j < grid[i].length; j++)
		  {
			  //in the future I should make a method called getNumberOfNeighbors to call and simplify this method
			  int numberOfNeighbors = 0;
			  //I could probably substitute all of these if statements with a couple for loops
			  if(i+1 < grid.length && grid[i+1][j]==ALIVE)
			  {
				  numberOfNeighbors++;
			  }
			  if(i > 0 && grid[i-1][j]==ALIVE)
			  {
				  numberOfNeighbors++;
			  }
			  if(j+1 < grid[i].length && grid[i][j+1]==ALIVE)
			  {
				  numberOfNeighbors++;
			  }
			  if(j > 0 && grid[i][j-1]==ALIVE)
			  {
				  numberOfNeighbors++;
			  }
			  if(i+1 < grid.length && j+1 < grid[i].length && grid[i+1][j+1]==ALIVE)
			  {
				  numberOfNeighbors++;
			  }
			  if(j > 0 && i+1 < grid.length && grid[i+1][j-1]==ALIVE)
			  {
				  numberOfNeighbors++;
			  }
			  if(i > 0 && j+1 < grid[i-1].length && grid[i-1][j+1]==ALIVE)
			  {
				  numberOfNeighbors++;
			  }
			  if(i > 0 && j > 0 && grid[i-1][j-1]==ALIVE)
			  {
					  numberOfNeighbors++;
			  }
			  
			  if(grid[i][j] == ALIVE)
			  {
				  //Conway's first rule: "Any live cell with fewer than two live neighbors dies, as if by under population."
				  if(numberOfNeighbors < 2)
				  {
					  grid[i][j] = EMPTY;
				  }
				  //Conway's second rule: "Any live cell with two or three live neighbors lives on to the next generation."
				  if(numberOfNeighbors == 2 || numberOfNeighbors == 3)
				  {
					  //I don't think this step is necessary but it shows consistency of the rules
					  grid[i][j] = ALIVE;
				  }
				  //Conway's third rule: "Any live cell with more than three live neighbors dies, as if by overpopulation."
				  if(numberOfNeighbors > 3)
				  {
					  grid[i][j] = EMPTY;
				  }
			  }

			  //Conway's fourth rule: "Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction."
			  if(grid[i][j] == EMPTY && numberOfNeighbors == 3)
			  {
				  grid[i][j] = ALIVE;
			  }
		  }
	  }
  }
  
  public void run()
  {
	  int speed = display.getSpeed();
	  if(speed == 0)
	  {
		  while(true)
		  {
			  display.pause(1000);
		  }
	  }
	  while (true)
	  {
	      int[] mouseLoc = display.getMouseLocation();
	      if (mouseLoc != null)  //test if mouse clicked
	      {
	        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
			  updateDisplay();
			  display.repaint();
	      }
	      else
	      {
			  step();
			  updateDisplay();
			  display.repaint();
			  display.pause(250);
	      }
	  }
  }
  
  //do not modify this method!
  /*
  public void run()
  {
    while (true) // infinite loop
    {
      for (int i = 0; i < display.getSpeed(); i++)
      {
        step();
      }
      updateDisplay();
      display.repaint();
      display.pause(100);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
      {
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
      }
    }
  }
  */
}
