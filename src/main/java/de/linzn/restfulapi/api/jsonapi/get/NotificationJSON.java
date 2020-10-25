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

package de.linzn.restfulapi.api.jsonapi.get;

import de.linzn.restfulapi.core.IResponseHandler;
import de.linzn.restfulapi.core.htmlTemplates.IHtmlTemplate;
import de.linzn.restfulapi.core.htmlTemplates.JSONTemplate;
import org.json.JSONArray;

import java.util.List;

public class NotificationJSON implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {

        JSONArray jsonArray = new JSONArray();

        /* Todo more notifications */
        /*
        List<Notify> notifies = HeatingStatusPlugin.heatingStatusPlugin.heaterProcessor.getNotifiesList();

        for (Notify notify : notifies) {
            if (notify.isActive()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("source", "Heizung");
                jsonObject.put("notification", notify.getName());
                jsonArray.put(jsonObject);
            }
        }
*/

        JSONTemplate emptyPage = new JSONTemplate();

        emptyPage.setCode(jsonArray);
        return emptyPage;
    }
}
