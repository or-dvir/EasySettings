package com.hotmail.or_dvir.easysettings_dialogs.events;

import com.hotmail.or_dvir.easysettings_dialogs.pojos.ListSettingsObject;

/**
 * an event that is sent when the value of a {@link ListSettingsObject}
 * is changed
 */
public class ListSettingsValueChangedEvent
{
	private ListSettingsObject listSettingsObj;
	private String    newValueAsSaved;
	private String[]  newValues;
	private Integer[] newValuesIndices;

	/**
	 *
	 * @param listSettingsObj the {@link ListSettingsObject} whose value was changed
	 * @param newValueAsSaved a string which contains the value of this {@link ListSettingsObject}
	 *                        as it is was saved (e.g. if selected values are A and B,
	 *                        then this string will be "A{delimiter}B"
	 * @param newValues the new values that were selected as individual strings
	 * @param newValuesIndices the indices of the new values that were selected
	 */
	public ListSettingsValueChangedEvent(ListSettingsObject listSettingsObj,
										 String newValueAsSaved,
										 String[] newValues,
										 Integer[] newValuesIndices)
	{
		this.listSettingsObj = listSettingsObj;
		this.newValueAsSaved = newValueAsSaved;
		this.newValues = newValues;
		this.newValuesIndices = newValuesIndices;
	}

	public Integer[] getNewValuesIndices()
	{
		return newValuesIndices;
	}

	/**
	 *
	 * @return the {@link ListSettingsObject} whose value was changed
	 */
	public ListSettingsObject getListSettingsObj()
	{
		return listSettingsObj;
	}

	/**
	 *
	 * @return a string which contains the value of this {@link ListSettingsObject}
	 *                        as it is was saved (e.g. if selected values are A and B,
	 *                        then this string will be "A{delimiter}B"
	 */
	public String getNewValueAsSaved()
	{
		return newValueAsSaved;
	}

	/**
	 *
	 * @return the new values that were selected as individual strings
	 */
	public String[] getNewValues()
	{
		return newValues;
	}
}