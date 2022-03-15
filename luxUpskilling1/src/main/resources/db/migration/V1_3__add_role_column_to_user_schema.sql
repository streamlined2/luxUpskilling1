ALTER TABLE yaos."user" ADD COLUMN role character varying (10) COLLATE pg_catalog."default";
UPDATE yaos."user" SET role='USER';
ALTER TABLE yaos."user" ALTER COLUMN role SET NOT NULL;