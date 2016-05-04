package de.fzi.cep.sepa.actions.samples.areachart;

import de.fzi.cep.sepa.actions.samples.ActionParameters;

public class AreaChartParameters extends ActionParameters {

	private String keyName;
	private String valueName;
	private String listPropertyName;
	
	public AreaChartParameters(String topic, String url, String listPropertyName, String keyName, String valueName) {
		super(topic, url);
		this.keyName = keyName;
		this.valueName = valueName;
	}

	public String getKeyName() {
		return keyName;
	}

	public String getValueName() {
		return valueName;
	}
	
	public String getListPropertyName()
	{
		return listPropertyName;
	}
}