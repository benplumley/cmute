-- new ride variables:
int x = ride.getLocation().getX();
int y = ride.getLocation().getY();
long dt = ride.getDateTime().getDateTime();
int sr = ride.getSeatsRemaining();
Boolean touni = ride.getIsToUni();
int rd = ride.getRepeatingDays();

-- new ride query:
INSERT INTO rides (map_point_x, map_point_y, date_and_time, region, seats_remaining, is_to_uni, repeating_days)
VALUES (x, y, dt, 1, sr, touni, rd);

----------------------------

-- book ride variables:
int sr = ride.getSeatsRemaining();
int id = ride.getUUID();

-- book ride query:
UPDATE rides
SET seats_remaining=(sr - 1)
WHERE UUID=id;

-- these won't compile in either language, the SQL parts must be turned into Java strings built around the variables.
