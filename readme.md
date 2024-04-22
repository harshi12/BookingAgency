PostmanLink: https://api.postman.com/collections/18483763-b3b40514-2abb-44c0-b250-9c78bff9868c?access_key=PMAT-01HW34ZSD3CYK3XT3NZ523886J

Design Decisions:
1. A bookingID is tied to a slotID
2. Slot is an entity in database. A cron job can be created to add/delete slots data from db based on how far in the future bookings are accepted.
3. A booking can be rescheduled with any other available operator

Notes:
1. Update value of 'spring.sql.init.mode' to 'always' for data setup
2. A customer can book only 1 hour slot at a time