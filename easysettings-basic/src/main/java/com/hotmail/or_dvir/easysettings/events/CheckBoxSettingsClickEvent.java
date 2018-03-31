package com.hotmail.or_dvir.easysettings.events;

import com.hotmail.or_dvir.easysettings.pojos.CheckBoxSettingsObject;

/**
 * an event that is sent whenever a {@link CheckBoxSettingsObject}
 * is clicked
 */
public class CheckBoxSettingsClickEvent
{
	private CheckBoxSettingsObject clickedSettingsObj;

	/**
	 *
	 * @param clickedSettingsObj the {@link CheckBoxSettingsObject} that was clicked
	 */
	public CheckBoxSettingsClickEvent(CheckBoxSettingsObject clickedSettingsObj)
	{
		this.clickedSettingsObj = clickedSettingsObj;
	}

	/**
	 *
	 * @return the {@link CheckBoxSettingsObject} that was clicked
	 */
	public CheckBoxSettingsObject getClickedSettingsObj()
	{
		return clickedSettingsObj;
	}
}