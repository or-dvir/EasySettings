package com.hotmail.or_dvir.easysettings.events;

import com.hotmail.or_dvir.easysettings.pojos.SwitchSettingsObject;

/**
 * an event that is sent when a {@link SwitchSettingsObject} is clicked
 */
public class SwitchSettingsClickEvent
{
    private SwitchSettingsObject clickedSettingsObj;

    /**
     * @param clickedSettingsObj the {@link SwitchSettingsObject}
     *                           that was clicked
     */
    public SwitchSettingsClickEvent(SwitchSettingsObject clickedSettingsObj)
    {
        this.clickedSettingsObj = clickedSettingsObj;
    }

    /**
     * @return the {@link SwitchSettingsObject}
     * that was clicked
     */
    public SwitchSettingsObject getClickedSettingsObj()
    {
        return clickedSettingsObj;
    }
}