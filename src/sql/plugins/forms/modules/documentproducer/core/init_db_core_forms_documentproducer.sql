--
-- Data for table core_admin_right
--
DELETE FROM core_admin_right WHERE id_right = 'CONFIG_DOCUMENT_PRODUCER_MANAGEMENT';
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url, id_order ) VALUES 
('CONFIG_DOCUMENT_PRODUCER_MANAGEMENT','module.forms.documentproducer.adminFeature.ManageConfigDocumentProducer.name',1,'jsp/admin/plugins/forms/modules/documentproducer/ManageConfigProducer.jsp?view=getSelectForm','module.forms.documentproducer.adminFeature.ManageConfigDocumentProducer.description',0,'forms-documentproducer',NULL,NULL,NULL,4);


--
-- Data for table core_user_right
--
DELETE FROM core_user_right WHERE id_right = 'CONFIG_DOCUMENT_PRODUCER_MANAGEMENT';
INSERT INTO core_user_right (id_right,id_user) VALUES ('CONFIG_DOCUMENT_PRODUCER_MANAGEMENT',1);
