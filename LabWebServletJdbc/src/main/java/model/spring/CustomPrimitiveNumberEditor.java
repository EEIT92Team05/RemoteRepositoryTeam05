package model.spring;

import java.text.NumberFormat;

import org.springframework.beans.propertyeditors.CustomNumberEditor;

public class CustomPrimitiveNumberEditor extends CustomNumberEditor {
	private boolean allowEmpty;
	public CustomPrimitiveNumberEditor(Class<? extends Number> numberClass, boolean allowEmpty) {
		this(numberClass, null, allowEmpty);
	}
	public CustomPrimitiveNumberEditor(Class<? extends Number> numberClass, NumberFormat numberFormat, boolean allowEmpty) {
		super(numberClass, numberFormat, allowEmpty);
		this.allowEmpty = allowEmpty;
	}
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(allowEmpty==true && (text==null || text.length()==0)) {
			super.setAsText("0");
		} else {
			super.setAsText(text);
		}
	}
	
}
