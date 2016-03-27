# Integrated Project

CHANGES

Location no longer used as part of the query because it clutters the UI, makes search slower and doesn't refine the results in a useful way.  
Decided to use MySQL so the server can be run on LCPU.  

TODO

Work out how repetition will work. If we just store a list of repeating days in the database, how do we store number of seats left because it will be different for each repetition (ie this Monday's ride has 3 seats left, but the same ride next monday has 5). Can't use multiple rows because each ride repeats an infinite number of times. Could use a worker in the server to check eg every hour and create a row for every repetition in the next month?
