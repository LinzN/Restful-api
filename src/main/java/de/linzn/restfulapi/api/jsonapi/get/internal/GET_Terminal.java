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
import de.stem.stemSystem.AppLogger;
import org.json.JSONArray;

import java.util.List;

public class GET_Terminal implements IGetJSON {
    @Override
    public Object getRequestData(List<String> inputList) {
        JSONArray jsonArray = new JSONArray();
        for (String entry : AppLogger.getLastEntries(30)) {
            jsonArray.put(entry);
        }
        return jsonArray;
    }

    @Override
    public Object getGenericData() {
        return null;
    }

    @Override
    public String name() {
        return "terminal";
    }
}
