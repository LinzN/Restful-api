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
import de.linzn.restfulapi.HomeWebAppPlugin;
import de.linzn.restfulapi.api.jsonapi.get.*;
import de.linzn.restfulapi.api.jsonapi.post.ChangeAutoModeJSON;
import de.linzn.restfulapi.api.jsonapi.post.ChangeDeviceJSON;
import de.linzn.restfulapi.api.jsonapi.post.ExecuteStemCommandJSON;
import de.linzn.restfulapi.core.IResponseHandler;
import de.linzn.restfulapi.core.htmlTemplates.IHtmlTemplate;
import de.linzn.restfulapi.core.htmlTemplates.JSONTemplate;
import de.linzn.openJL.network.IPAddressMatcher;
import de.stem.stemSystem.AppLogger;
import de.stem.stemSystem.utils.Color;
import org.json.JSONArray;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiHandler implements HttpHandler {

    private final Map<String, IResponseHandler> subHandlers;

    public ApiHandler() {
        this.subHandlers = new LinkedHashMap<>();
        this.registerSubHandlers();
    }


    @Override
    public void handle(HttpExchange he) throws IOException {
        handleRequests(he);
    }

    private void handleRequests(final HttpExchange he) throws IOException {
        List<String> whitelist = HomeWebAppPlugin.homeWebAppPlugin.getDefaultConfig().getStringList("apiServer.whitelist");
        String requestingAddress = he.getRemoteAddress().getAddress().getHostName();

        boolean matched = false;
        for (String ip : whitelist) {
            if (new IPAddressMatcher(ip).matches(requestingAddress)) {
                matched = true;
                break;
            }
        }

        if (!matched) {
            AppLogger.debug(Color.RED + "[WEBAPP_API-SERVER] Access deny for " + requestingAddress);
            he.close();
            return;
        }

        String url = he.getRequestURI().getRawPath();

        List<String> argsList = Arrays.stream(url.split("/")).filter(arg -> !arg.isEmpty()).collect(Collectors.toList());
        IHtmlTemplate iHtmlPage = new JSONTemplate();

        if (!argsList.isEmpty()) {
            String command = argsList.get(0);
            if (this.subHandlers.containsKey(command.toLowerCase())) {
                iHtmlPage = this.subHandlers.get(command.toLowerCase()).buildResponse(argsList);
            }
        } else {
            iHtmlPage = new JSONTemplate();

            JSONArray jsonArray = new JSONArray();
            for (String key : this.subHandlers.keySet()) {
                jsonArray.put(key);
            }

            ((JSONTemplate) iHtmlPage).setCode(jsonArray);
        }

        iHtmlPage.generate();

        Headers h = he.getResponseHeaders();

        for (String key : iHtmlPage.headerList().keySet()) {
            h.set(key, iHtmlPage.headerList().get(key));
        }

        he.sendResponseHeaders(200, iHtmlPage.length());
        OutputStream os = he.getResponseBody();
        os.write(iHtmlPage.getBytes());
        os.close();
    }

    private void registerSubHandlers() {
        /* JSON GET API */
        this.subHandlers.put("json_terminal", new TerminalJSON());
        this.subHandlers.put("json_weather", new WeatherJSON());
        this.subHandlers.put("json_resources", new ResourcesJSON());
        this.subHandlers.put("json_device-status", new DeviceStatusJSON());
        this.subHandlers.put("json_reminder", new ReminderJSON());
        this.subHandlers.put("json_notification", new NotificationJSON());
        this.subHandlers.put("json_trash-calendar", new TrashCalendarJSON());
        this.subHandlers.put("json_automode", new AutoModeJSON());
        this.subHandlers.put("json_generic", new GenericDataJSON());
        this.subHandlers.put("json_notification-archive", new NotificationArchiveJSON());

        /* JSON PUSH API */
        this.subHandlers.put("post_change-device-status", new ChangeDeviceJSON());
        this.subHandlers.put("post_change-automode", new ChangeAutoModeJSON());
        this.subHandlers.put("post_execute-stem-command", new ExecuteStemCommandJSON());
    }

}

