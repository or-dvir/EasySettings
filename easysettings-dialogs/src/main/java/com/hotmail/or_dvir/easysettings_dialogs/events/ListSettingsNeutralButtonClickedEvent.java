package com.hotmail.or_dvir.easysettings_dialogs.events;

import com.hotmail.or_dvir.easysettings_dialogs.pojos.ListSettingsObject;

/**
 * an event that is sent when the neutral button of a {@link ListSettingsObject}
 * is clicked
 */
public class ListSettingsNeutralButtonClickedEvent
{
	private ListSettingsObject listSettingsObj;

	/**
	 *
	 * @param listSettingsObj the {@link ListSettingsObject} whose neutral button was clicked
	 */
	public ListSettingsNeutralButtonClickedEvent(ListSettingsObject listSettingsObj)
	{
		this.listSettingsObj = listSettingsObj;
	}

	/**
	 *
	 * @return the {@link ListSettingsObject} whose neutral button was clicked
	 */
	public ListSettingsObject getListSettingsObj()
	{
		return listSettingsObj;
	}
}