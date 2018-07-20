package com.sssnto.swagger.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import io.swagger.parser.util.DeserializationUtils;
import io.swagger.parser.util.RemoteUrl;
import io.swagger.util.Json;

import java.nio.file.Paths;


public class JxiPlatformConverter extends SwaggerToMarkDownConverter{

    /**
     * 京喜平台swagger转换成markdown
     * @throws Exception
     */
    @Override
    public void converter(String url,String outputDir) throws Exception {

        // 1.获取远程数据
        String data = RemoteUrl.urlToString(url, null);

        //将数据组装成Json
        JsonNode rootNode;
        if (data.trim().startsWith("{")) {
            ObjectMapper mapper = Json.mapper();
            rootNode = mapper.readTree(data);
        } else {
            rootNode = DeserializationUtils.readYamlTree(data);
        }

        JsonNode jsonNode = rootNode.get("content").get("body");

        //构建swagger
        Swagger swagger = new SwaggerParser().read(jsonNode);

        //转换swagger到markdown
        Swagger2MarkupConverter.from(swagger)
                .withConfig(getConfig())
                .build()
                .toFolder(Paths.get(outputDir));

    }

}
