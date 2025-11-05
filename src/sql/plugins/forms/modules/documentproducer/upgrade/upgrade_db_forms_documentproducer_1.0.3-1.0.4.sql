-- liquibase formatted sql
-- changeset core:upgrade_db_forms_documentproducer_1.0.3-1.0.4.sql
-- preconditions onFail:MARK_RAN onError:WARN

ALTER TABLE forms_config_producer modify COLUMN id_config int AUTO_INCREMENT;