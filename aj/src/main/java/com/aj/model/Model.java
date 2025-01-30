package com.aj.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import com.aj.utility.Functions;


public class Model {
	protected Integer key;
	protected String nombreid;
	protected String nombresecuencia;

	public String getNombresecuencia() {
		return nombresecuencia;
	}

	public void setNombresecuencia(String nombresecuencia) {
		this.nombresecuencia = nombresecuencia;
	}

	public String save() {
		return makeQuerySave(this);
	}

	public String update(String customFields) {
		String tableName = this.getClass().getSimpleName().toLowerCase();
		String query = "";
		if(!customFields.isEmpty())
			query = "UPDATE " + tableName + " set " + makeCustomUpdate(this, customFields);
		else
			query = "UPDATE " + tableName + " set " + makeQueryUpdate(this);
		
		System.out.println(query);
		return query;
	}

	public String del() {
		String tableName = this.getClass().getSimpleName().toLowerCase();
		String query = "DELETE FROM " + tableName + " where  " + nombreid + " = " + key;
		System.out.println(query);
		return query;
	}

	protected String makeQueryUpdate(Object model) {
		Method metodo;
		String values = "";
		String where = "";
		for (Field f : model.getClass().getDeclaredFields()) {
			try {
				String nombreAtributo = f.getName();
				metodo = model.getClass().getMethod("get".concat(initCap(nombreAtributo)));
				Object val = metodo.invoke(model);

				if (nombreAtributo.equalsIgnoreCase(nombreid)) {
					where = " where " + nombreAtributo + " = " + "'" + toStr(val) + "'";
				} else {
					if (val != null) {
						values += nombreAtributo + " = '" + toStr(val) + "'" + ",";
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		if (!values.isEmpty()) {
			values = values.substring(0, values.length() - 1) + where;
		}
		return values;
	}
	
	protected String makeCustomUpdate(Object model, String fields) {
		String [] nameFieldsToUpdate = fields.split("\\,");
		Method metodo;
		String values = "";
		String where = "";
		try {
			for (Field f : model.getClass().getDeclaredFields()) {
				for(String nameField:nameFieldsToUpdate){					
					if(nameField.equalsIgnoreCase(f.getName()) && !nameField.equalsIgnoreCase(nombreid)){
						String nombreAtributo = f.getName();
						metodo = model.getClass().getMethod("get".concat(initCap(nombreAtributo)));
						Object val = metodo.invoke(model);
						if (val != null) {
							values += nombreAtributo + " = '" + toStr(val) + "'" + ",";
						}						
					}
				}
				if (f.getName().equalsIgnoreCase(nombreid)) {
					metodo = model.getClass().getMethod("get".concat(initCap(f.getName())));
					Object val = metodo.invoke(model);
					where = " where " + f.getName() + " = " + "'" + toStr(val) + "'";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!values.isEmpty()) {
			values = values.substring(0, values.length() - 1) + where;
		}
		return values;
	}

	protected String makeQuerySave(Object model) {
		String tableName = model.getClass().getSimpleName().toLowerCase();
		String values = this.getFieldValues(model);
		String columns = this.getFieldNames(model.getClass());
		String query = "INSERT INTO " + tableName + " (" + columns + " )" + " VALUES (" + values + ")";
		 System.out.println(query);
		return query;
	}

	protected String getFieldValues(Object model) {
		java.lang.reflect.Field[] fields;
		fields = model.getClass().getDeclaredFields();
		String values = "";
		for (java.lang.reflect.Field field : fields) {
			if (field.getModifiers() == Modifier.PUBLIC) {
				String claseAtributo = field.getType().getName();
				try {

					if (field.getName().equalsIgnoreCase(nombreid)) {
						values += "nextval('" + nombresecuencia + "')" + ",";
					} else {
						Object valor = field.get(model);
						if (valor != null) {
							if (Functions.toStr(claseAtributo).equalsIgnoreCase("java.lang.Double"))
								values += valor.toString() + ",";
							else if (Functions.toStr(claseAtributo).equalsIgnoreCase("java.lang.Integer"))
								values += valor.toString() + ",";
							else if (Functions.toStr(claseAtributo).equalsIgnoreCase("java.math.BigDecimal"))
								values += valor.toString() + ",";
							else
								values += "'" + valor.toString() + "'" + ",";
						} else {
							values += "null" + ",";
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (!values.isEmpty()) {
			values = values.substring(0, values.length() - 1);
		}
		return values;
	}

	protected String getFieldNames(Class modelClass) {
		java.lang.reflect.Field[] fields;
		fields = modelClass.getDeclaredFields();
		String values = "";
		for (java.lang.reflect.Field field : fields) {
			if (field.getModifiers() == Modifier.PUBLIC) {
				try {
					values += field.getName() + ",";
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (!values.isEmpty()) {
			values = values.substring(0, values.length() - 1);
		}
		return values;
	}

	private static String initCap(String s) {
		return (s != null) ? s.substring(0, 1).toUpperCase() + s.substring(1, s.length()).toLowerCase() : null;
	}

	public static String toStr(Object obj) {
		return obj == null ? "" : obj.toString().trim();
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getNombreid() {
		return nombreid;
	}

	public void setNombreid(String nombreid) {
		this.nombreid = nombreid;
	}

}
