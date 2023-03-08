# How to run

if you're using the command line you can input this, assuming you have scala installed on your machine:
E.G:

    $ scala OrderAnalyzerApp 2022-01-01 2023-02-01 0-2 2-4 4-6 6-8 8-10 10-12 >13

the "0-2 2-4 4-6 6-8 8-10 10-12 >13" is the bonus requirement that lets you pass a custom list of intervals, this can be changed to whatever interval you want and can also be ommited, if you choose not to pass any it will use the default intervals:

    $ scala OrderAnalyzerApp 2022-01-01 2023-02-01

          List(
        (0, 3),
        (3, 6),
        (6, 9),
        (9, 12),
        (12, Int.MaxValue)
      )

You can alter the start and end date interval for testing as you wish, but i recommend using the ones in the example above because most of the hard coded mock examples fall into those time frames

If you're running on an IDE such as IntellJ, you must find the Run/Debug Configurations windows and input the program arguments
