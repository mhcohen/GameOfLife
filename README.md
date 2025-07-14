A simple game of life implementation.
No graphical interface, sadly. Just the underlying logic.
It'll output 2D coordinates of all live cells each generation.

Usage: GameOfLife [-g=<generations>] [-h=<height>] [-w=<width>]
                  [-p=<initialPopulation>...]...
  -g, --generation=<generations>
                          Number of generations to run the game. Defaults to 100
  -h, --height=<height>   Height of the world in Cells. Defaults to 200
  -p, --population=<initialPopulation>...
                          List of initial population coordinates in x,y format
                            (e.g., 1,1 1,2 2,1)
  -w, --width=<width>     Width of the world in Cells. Defaults to 200
