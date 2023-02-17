package com.example.weatherproject.UserLocations;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserLocationListDeserializer extends JsonDeserializer<List <UserLocation>> {
    @Override
    public List<UserLocation> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        ArrayNode arrayNode = jsonParser.getCodec().readTree(jsonParser);
        List<UserLocation> userLocations = new ArrayList<>();

        for(JsonNode node: arrayNode){

            UserLocation userLocation = new UserLocation(node.asText());

            userLocations.add(userLocation);
        }


        return userLocations;

    }
}
