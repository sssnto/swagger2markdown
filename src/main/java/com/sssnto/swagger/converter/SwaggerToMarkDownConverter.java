package com.sssnto.swagger.converter;

import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;

import java.net.URL;
import java.nio.file.Paths;

/**
 * 正常swagger2Markdown转换类
 */
public class SwaggerToMarkDownConverter {

    private Swagger2MarkupConfig swagger2MarkupConfig;

    public Swagger2MarkupConfig getConfig(){
        // 如果外部没有设置，初始化默认config
        if (swagger2MarkupConfig == null){
            this.swagger2MarkupConfig  = new Swagger2MarkupConfigBuilder()
                    .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                    .withGeneratedExamples()
                    .withInterDocumentCrossReferences()
                    .build();
        }
        return swagger2MarkupConfig;
    }

    //可自定义 Swagger2MarkupConfig ,不配置的话会有默认配置
    public SwaggerToMarkDownConverter config(Swagger2MarkupConfig swagger2MarkupConfig) {
        this.swagger2MarkupConfig = swagger2MarkupConfig;
        return this;
    }

    // swagger2markdown 转换方法
    public void converter(String url, String outputDir) throws Exception {
        Swagger2MarkupConverter.from(new URL(url))
                .withConfig(getConfig())
                .build()
                .toFolder(Paths.get(outputDir));
    }
}
