ALTER TABLE yaos."user" ADD COLUMN salt character varying (20) COLLATE pg_catalog."default";
UPDATE yaos."user" SET salt='some random data';
ALTER TABLE yaos."user" ALTER COLUMN salt SET NOT NULL;