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

package de.linzn.restfulapi.api.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.linzn.openJL.network.IPAddressMatcher;
import de.linzn.restfulapi.RestFulApiPlugin;
import de.linzn.restfulapi.api.jsonapi.get.IGetJSON;
import de.linzn.restfulapi.api.jsonapi.get.internal.*;
import de.linzn.restfulapi.api.jsonapi.post.IPostJSON;
import de.linzn.restfulapi.api.jsonapi.post.internal.POST_ExecuteStemCommand;
import de.linzn.restfulapi.core.JSONTemplate;
import de.stem.stemSystem.STEMSystemApp;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApiHandler implements HttpHandler {

    private List<IGetJSON> iGetJSONList;
    private List<IPostJSON> iPostJSONList;

    public ApiHandler() {
        this.iGetJSONList = new ArrayList<>();
        this.iPostJSONList = new ArrayList<>();
        this.registerInternalHandlers();
    }


    @Override
    public void handle(HttpExchange he) throws IOException {
        handleRequests(he);
    }

    private void handleRequests(final HttpExchange he) throws IOException {
        List<String> whitelist = RestFulApiPlugin.restFulApiPlugin.getDefaultConfig().getStringList("apiServer.whitelist");
        String requestingAddress = he.getRemoteAddress().getAddress().getHostName();

        boolean matched = false;
        for (String ip : whitelist) {
            if (new IPAddressMatcher(ip).matches(requestingAddress)) {
                matched = true;
                break;
            }
        }

        if (!matched) {
            STEMSystemApp.LOGGER.ERROR("[REST_API] Access deny for " + requestingAddress);
            he.close();
            return;
        }

        String url = he.getRequestURI().getRawPath();

        List<String> argsList = Arrays.stream(url.split("/")).filter(arg -> !arg.isEmpty()).collect(Collectors.toList());
        JSONTemplate jsonTemplate = new JSONTemplate();

        if (!argsList.isEmpty()) {
            String command = argsList.get(0);

            if (command.toLowerCase().startsWith("get_")) {
                String split_command = command.replace("get_", "");
                if (split_command.equalsIgnoreCase("generic")) {
                    jsonTemplate.setCode(buildGeneric());
                } else {
                    for (IGetJSON iGetJSON : this.iGetJSONList) {
                        if (iGetJSON.name().equalsIgnoreCase(split_command)) {
                            jsonTemplate.setCode(iGetJSON.getRequestData(argsList));
                        }
                    }
                }
            } else if (command.toLowerCase().startsWith("post_")) {
                String split_command = command.replace("post_", "");
                for (IPostJSON iPostJSON : this.iPostJSONList) {
                    if (iPostJSON.name().equalsIgnoreCase(split_command)) {
                        jsonTemplate.setCode(iPostJSON.postDataRequest(argsList));
                    }
                }
            }
        }

        Headers h = he.getResponseHeaders();

        for (String key : jsonTemplate.headerList().keySet()) {
            h.set(key, jsonTemplate.headerList().get(key));
        }

        he.sendResponseHeaders(200, jsonTemplate.length());
        OutputStream os = he.getResponseBody();
        os.write(jsonTemplate.getBytes());
        os.close();
    }

    public void addGetHandler(IGetJSON iGetJSON) {
        this.iGetJSONList.add(iGetJSON);
    }

    public void addPostHandler(IPostJSON iPostJSON) {
        this.iPostJSONList.add(iPostJSON);
    }

    private JSONObject buildGeneric() {
        JSONObject jsonObject = new JSONObject();

        for (IGetJSON iGetJSON : this.iGetJSONList) {
            Object data = iGetJSON.getGenericData();
            if (data != null) {
                jsonObject.put(iGetJSON.name(), data);
            }
        }
        return jsonObject;
    }


    private void registerInternalHandlers() {
        this.addGetHandler(new GET_Notification());
        this.addGetHandler(new GET_NotificationArchive());
        this.addGetHandler(new GET_Resources());
        this.addGetHandler(new GET_Terminal());
        this.addGetHandler(new GET_Stem());
        this.addGetHandler(new GET_Network());

        this.addPostHandler(new POST_ExecuteStemCommand());
    }
}

