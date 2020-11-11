/*
 * Copyright (C) 2020. Niklas Linz - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.restfulapi.api.jsonapi.get.beta;

import de.linzn.calender.CalenderPlugin;
import de.linzn.calender.objects.ICalendarType;
import de.linzn.calender.objects.TrashType;
import de.linzn.restfulapi.api.jsonapi.get.IGetJSON;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class GET_TrashCalendar implements IGetJSON {
    @Override
    public Object getRequestData(List<String> inputList) {
        JSONObject jsonObject = new JSONObject();
        Format dateFormat = new SimpleDateFormat("EEEE d MMMMM yyyy", Locale.GERMANY);

        ICalendarType blackTrash = CalenderPlugin.calenderPlugin.getCalendarManager().getNextTrash(TrashType.BLACK);
        ICalendarType greenTrash = CalenderPlugin.calenderPlugin.getCalendarManager().getNextTrash(TrashType.GREEN);
        ICalendarType yellowTrash = CalenderPlugin.calenderPlugin.getCalendarManager().getNextTrash(TrashType.YELLOW);
        ICalendarType blueTrash = CalenderPlugin.calenderPlugin.getCalendarManager().getNextTrash(TrashType.BLUE);


        JSONObject blackTrashJson = new JSONObject();
        blackTrashJson.put("name", blackTrash.getName());
        blackTrashJson.put("date", dateFormat.format(blackTrash.getDate()));
        blackTrashJson.put("type", blackTrash.getType().name());
        jsonObject.put(blackTrash.getType().name(), blackTrashJson);

        JSONObject greenTrashJson = new JSONObject();
        greenTrashJson.put("name", greenTrash.getName());
        greenTrashJson.put("date", dateFormat.format(greenTrash.getDate()));
        greenTrashJson.put("type", greenTrash.getType().name());
        jsonObject.put(greenTrash.getType().name(), greenTrashJson);

        JSONObject yellowTrashJson = new JSONObject();
        yellowTrashJson.put("name", yellowTrash.getName());
        yellowTrashJson.put("date", dateFormat.format(yellowTrash.getDate()));
        yellowTrashJson.put("type", yellowTrash.getType().name());
        jsonObject.put(yellowTrash.getType().name(), yellowTrashJson);

        JSONObject blueTrashJson = new JSONObject();
        blueTrashJson.put("name", blueTrash.getName());
        blueTrashJson.put("date", dateFormat.format(blueTrash.getDate()));
        blueTrashJson.put("type", blueTrash.getType().name());
        jsonObject.put(blueTrash.getType().name(), blueTrashJson);
        return jsonObject;
    }

    @Override
    public Object getGenericData() {
        return getRequestData(null);
    }

    @Override
    public String name() {
        return "trash-calendar";
    }
}
