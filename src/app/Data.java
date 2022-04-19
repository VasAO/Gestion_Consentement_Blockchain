package app;

import com.google.gson.JsonObject;

public class Data {
    private Long id;
    private Boolean consent;

    public Data(){}
    
    public Data(Long id, Boolean consent) {
        this.id = id;
        this.consent = consent;
    }

    public JsonObject toJson() {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("id", this.id);
        jsonData.addProperty("consentement", this.consent);
        return jsonData;
    }

    public void equals(Data sample) {
        this.id = sample.id;
        this.consent = sample.consent;
    }
}
