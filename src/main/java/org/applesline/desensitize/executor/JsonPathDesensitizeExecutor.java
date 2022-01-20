package org.applesline.desensitize.executor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import org.applesline.desensitize.handle.DesensitizeHandlerSelector;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @author liuyaping
 * @date 2022/1/14
 */
public class JsonPathDesensitizeExecutor extends DesensitizeExecutorAdapter {

    public JsonPathDesensitizeExecutor(DesensitizeHandlerSelector desensitizeHandlerSelector) {
        super(desensitizeHandlerSelector);
    }

    @Override
    public Object executeMask(Object obj) {
        JsonElement jsonTree = gson.toJsonTree(obj);
        maskSensitiveWords(jsonTree,matchIgnoreFields(obj));
        return gson.fromJson(jsonTree.toString(),obj.getClass());
    }

    private void maskSensitiveWords(JsonElement originElement,Collection<String> ignoreMaskWords) {
        Set<String> needMaskWords = maskWordsMap.keySet();
        if (originElement.isJsonArray()) {
            JsonArray jsonArray = originElement.getAsJsonArray();
            for (JsonElement jsonElement : jsonArray) {
                maskSensitiveWords(jsonElement,ignoreMaskWords);
            }
        } else if (originElement.isJsonObject()) {
            JsonObject jsonObject = originElement.getAsJsonObject();
            for (Map.Entry<String,JsonElement> entry : originElement.getAsJsonObject().entrySet()) {
                JsonElement element = entry.getValue();
                if (element.isJsonArray()) {
                    maskSensitiveWords(element.getAsJsonArray(),ignoreMaskWords);
                } else if (element.isJsonObject()) {
                    maskSensitiveWords(element.getAsJsonObject(),ignoreMaskWords);
                } else {
                    if (needMaskWords.contains(entry.getKey()) && !ignoreMaskWords.contains(entry.getValue().getAsString())) {
                        if (element.getAsJsonPrimitive().isNumber()) {
                            jsonObject.addProperty(entry.getKey(), doMask(maskWordsMap.get(entry.getKey())));
                        } else {
                            jsonObject.addProperty(entry.getKey(), doMask(maskWordsMap.get(entry.getKey()),entry.getValue().getAsString()));
                        }
                    }
                }
            }
        }
    }

    private Collection<String> matchIgnoreFields(Object obj) {
        Collection<String> ignoreMaskFields = new HashSet<>();
        for (String ignoreJpe : ignoreJsonPathExpression) {
            Object field = JsonPath.read(gson.toJsonTree(obj).toString(),ignoreJpe);
            if (field instanceof Collection) {
                ignoreMaskFields.addAll((Collection<? extends String>) field);
            } else {
                ignoreMaskFields.add(String.valueOf(field));
            }
        }
        return ignoreMaskFields;
    }

}
