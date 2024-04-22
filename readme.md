Design Decisions:
1. A bookingID is tied to a slotID
2. Slot is an entity in database. A cron job can be created to add/delete slots data from db based on how far in the future bookings are accepted.
3. A booking can be rescheduled with any other available operator

Notes:
1. Update value of 'spring.sql.init.mode' to 'always' for data setup
2. A customer can book only 1 hour slot at a time