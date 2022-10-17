CREATE USER zdata PASSWORD 'zdata12345';

DROP FUNCTION auto_update_time();
CREATE OR REPLACE FUNCTION auto_update_time()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.update_time = NOW();
    RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';
