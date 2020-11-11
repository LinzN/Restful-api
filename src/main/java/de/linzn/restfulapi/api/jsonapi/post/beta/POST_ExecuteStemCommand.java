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

package de.linzn.restfulapi.api.jsonapi.post.beta;

import de.linzn.restfulapi.RestFulApiPlugin;
import de.linzn.restfulapi.api.jsonapi.post.IPostJSON;
import de.linzn.simplyLogger.Color;
import de.stem.stemSystem.AppLogger;
import de.stem.stemSystem.STEMSystemApp;
import de.stem.stemSystem.taskManagment.operations.defaultOperations.StemRestartOperation;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class POST_ExecuteStemCommand implements IPostJSON {
    @Override
    public Object postDataRequest(List<String> inputList) {
        JSONObject jsonObject = new JSONObject();

        String command = inputList.get(1);
        AppLogger.debug(Color.GREEN + "[API-SERVER] Post Request: StemCommand::" + command);

        boolean success = executeCommand(command);
        jsonObject.put("command", command);
        jsonObject.put("status", success);
        jsonObject.put("date", new Date().getTime());

        return jsonObject;
    }

    @Override
    public String name() {
        return "execute-stem-command";
    }

    private boolean executeCommand(String command) {
        if (command.equalsIgnoreCase("restart")) {
            restartCommand();
            return true;
        } else {
            return false;
        }
    }

    private void restartCommand() {
        StemRestartOperation stemRestartOperation = new StemRestartOperation();
        STEMSystemApp.getInstance().getScheduler().runTask(RestFulApiPlugin.restFulApiPlugin, stemRestartOperation);
    }
}
