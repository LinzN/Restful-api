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

package de.linzn.restfulapi.core;

import de.linzn.restfulapi.core.htmlTemplates.IHtmlTemplate;

import java.util.List;

public interface IResponseHandler {

    IHtmlTemplate buildResponse(List<String> inputList);
}
