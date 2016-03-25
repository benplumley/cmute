# Integrated Project

TODO

Each ride needs a UUID assigned by MySQL, this will be how the UI refers to that pin internally.  
Do we need a location and location tolerance? The user sees a map of rides when they enter a time, might be more user friendly to let them decide per ride whether it's too far to walk rather than having to set a tolerance. Just show all rides within that time tolerance. Doesn't even clutter the map more because the area the user focusses on is the area near them, which will have the same number of rides in either way.
