package com.vtes.model.navitime;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class FareSerializer extends JsonSerializer<Map<String, Integer>> {	

	@Override
	public void serialize(Map<String, Integer> value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		gen.writeStartObject();
		for (Map.Entry<String, Integer> entry : value.entrySet()) {
			String key = entry.getKey();
			if (key.equals("unit_0"))
				key = "cash";
			if (key.equals("unit_48"))
				key = "IC";
			gen.writeNumberField(key, entry.getValue());

		}
		gen.writeEndObject();

	}

}
