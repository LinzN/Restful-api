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

import de.linzn.restfulapi.api.jsonapi.RequestData;
import de.linzn.restfulapi.api.jsonapi.IRequest;
import de.stem.stemSystem.AppLogger;
import org.json.JSONArray;

public class GET_Terminal implements IRequest {
    @Override
    public Object proceedRequestData(RequestData requestData) {
        JSONArray jsonArray = new JSONArray();
        for (String entry : AppLogger.getLastEntries(30)) {
            jsonArray.put(entry);
        }
        return jsonArray;
    }

    @Override
    public String name() {
        return "terminal";
    }
}
