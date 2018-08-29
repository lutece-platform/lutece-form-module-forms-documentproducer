DROP TABLE IF EXISTS forms_config_producer;
DROP TABLE IF EXISTS forms_config_question;
DROP TABLE IF EXISTS forms_default_config;

/*==============================================================*/
/* Table structure for table form_config_producer				*/
/*==============================================================*/
CREATE TABLE forms_config_producer (
  id_config INT NOT NULL,
  name VARCHAR(255) DEFAULT NULL,
  id_question_name_file INT DEFAULT NULL,
  id_form INT DEFAULT NULL,
  config_type VARCHAR(255) DEFAULT NULL,
  text_file_name VARCHAR(255) DEFAULT NULL,
  type_config_file_name VARCHAR(255) DEFAULT NULL,
  extract_empty INT DEFAULT 0,
  
  PRIMARY KEY  (id_config)
  );
  
/*==============================================================*/
/* Table structure for table forms_config_question				*/
/*==============================================================*/
CREATE TABLE forms_config_question (
  id_config INT NOT NULL,
  id_question INT NOT NULL,
  PRIMARY KEY  (id_config,id_question)
  );
  
/*==============================================================*/
/* Table structure for table forms_default_config			*/
/*==============================================================*/
CREATE TABLE forms_default_config (
  id_config INT NOT NULL,
  id_form INT NOT NULL,
  config_type VARCHAR(255) NOT NULL,
  PRIMARY KEY  (id_config,id_form,config_type)
  );
