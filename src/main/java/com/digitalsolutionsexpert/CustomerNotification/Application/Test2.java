package com.digitalsolutionsexpert.CustomerNotification.Application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.JsonNodeValueResolver;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.context.FieldValueResolver;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.context.MethodValueResolver;
import com.github.jknack.handlebars.helper.StringHelpers;

import java.io.IOException;

public class Test2 {
    public static void main(String args[]) throws IOException {
        System.out.println("test");

        ObjectMapper objectMapper = new ObjectMapper();
        Handlebars handlebars = new Handlebars();
        handlebars.registerHelpers(StringHelpers.class);
        String jsonString = "{\"bcDate\": \"2017-08-17T00:00:00.000+03:00\"}";
        String handlebarsTemplate = "{{dateFormat date bcDate format=\"DD-MM-YYYY\"}}";
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        Template template = handlebars.compileInline(handlebarsTemplate);
        Context context = Context
                .newBuilder(jsonNode)
                .resolver(JsonNodeValueResolver.INSTANCE,
                        JavaBeanValueResolver.INSTANCE,
                        FieldValueResolver.INSTANCE,
                        MapValueResolver.INSTANCE,
                        MethodValueResolver.INSTANCE).build();
        String formatString = template.apply(context);
        System.out.println(formatString);

    }
}
