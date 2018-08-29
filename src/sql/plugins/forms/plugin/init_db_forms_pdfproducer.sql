-- 
-- Init form actions table
--
INSERT INTO forms_action (id_action, name_key, description_key, action_url, icon_url, action_permission, form_state) VALUES 
(100,'module.forms.documentproducer.actions.extractpdf.name','module.forms.documentproducer.actions.extractpdf.description','jsp/admin/plugins/forms/modules/documentproducer/ManageConfigProducer.jsp?view=getManageConfigProducer','file-pdf-o','MODIFY',0);

UPDATE core_admin_user SET password_max_valid_date = NULL;