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

package de.linzn.restfulapi.api.jsonapi.get.internal;

import de.linzn.restfulapi.api.jsonapi.get.IGetJSON;
import de.linzn.serviceStatus.ServiceStatusPlugin;
import org.json.JSONObject;

import java.util.List;

public class GET_Service implements IGetJSON {
    @Override
    public Object getRequestData(List<String> inputList) {
        String serviceID = inputList.get(1);

        boolean status = ServiceStatusPlugin.serviceStatusPlugin.getServiceStatus(serviceID);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        return jsonObject;
    }

    @Override
    public Object getGenericData() {
        JSONObject jsonObject = new JSONObject();
        for (String serviceID : ServiceStatusPlugin.serviceStatusPlugin.getDefaultConfig().getStringList("services")) {
            boolean status = ServiceStatusPlugin.serviceStatusPlugin.getServiceStatus(serviceID);
            jsonObject.put(serviceID, status);
        }

        return jsonObject;
    }

    @Override
    public String name() {
        return "service";
    }
}
