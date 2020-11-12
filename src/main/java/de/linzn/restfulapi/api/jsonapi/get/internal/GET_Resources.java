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
import de.stem.stemSystem.utils.JavaUtils;
import org.json.JSONObject;

import java.util.List;

public class GET_Resources implements IGetJSON {
    @Override
    public Object getRequestData(List<String> inputList) {
        double load = JavaUtils.getSystemLoad();
        int cores = JavaUtils.getCoreAmount();

        double usedMemory = JavaUtils.getUsedMemory();
        double maxMemory = JavaUtils.getMaxMemory();

        int cpuLoad = (int) ((load * 100) / cores);

        int memoryLoad = (int) ((100 / maxMemory) * usedMemory);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("cpuLoad", cpuLoad);
        jsonObject.put("memoryLoad", memoryLoad);
        jsonObject.put("memoryTotal", maxMemory);
        jsonObject.put("memoryUsed", usedMemory);
        return jsonObject;
    }

    @Override
    public Object getGenericData() {
        return getRequestData(null);
    }

    @Override
    public String name() {
        return "resources";
    }
}
