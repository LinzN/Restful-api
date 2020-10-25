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
import de.linzn.openJL.math.FloatingPoint;
import de.linzn.systemChain.callbacks.NetworkScheduler;
import org.json.JSONObject;

import java.util.List;

public class GenericDataJSON implements IResponseHandler {
    @Override
    public IHtmlTemplate buildResponse(List<String> inputList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("network", getNetworkData());
        jsonObject.put("stem", getStemData());

        JSONTemplate emptyPage = new JSONTemplate();
        emptyPage.setCode(jsonObject);
        return emptyPage;
    }

    private JSONObject getNetworkData() {
        JSONObject networkObject = new JSONObject();
        float ping = NetworkScheduler.getLastPing();
        networkObject.put("ping", ping);
        return networkObject;
    }

    private JSONObject getStemData() {
        JSONObject stemObject = new JSONObject();
        stemObject.put("status", "OK");
        return stemObject;
    }

}
