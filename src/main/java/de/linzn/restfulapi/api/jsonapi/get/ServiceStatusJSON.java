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
import de.linzn.serviceStatus.ServiceStatusPlugin;
import org.json.JSONObject;

import java.util.List;

public class ServiceStatusJSON implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {

        String serviceID = inputList.get(1);

        boolean status = ServiceStatusPlugin.serviceStatusPlugin.getServiceStatus(serviceID);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);

        JSONTemplate emptyPage = new JSONTemplate();

        emptyPage.setCode(jsonObject);
        return emptyPage;
    }
}
