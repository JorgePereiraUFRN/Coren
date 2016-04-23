#include <ArduinoJson.h>
#include <DHT.h>
#define DHTTYPE DHT11

#define DHTPIN 2 // pin digital temperature
int brightnessPin = A2; // pin analog brightness
int firePin = 3; // pin digital fire

DHT dht(DHTPIN, DHTTYPE);

JsonObject& buildAttributesJson(JsonBuffer& jsonBuffer) {
  
  JsonObject& jsonObject = jsonBuffer.createObject();
  
  JsonArray& arrayJson = jsonBuffer.createArray();
  
  JsonObject& attrTemperature = jsonBuffer.createObject();
  int temperature = (int) dht.readTemperature();
  while (isnan(temperature)) {
    temperature = (int) dht.readTemperature();
  }
  attrTemperature["name"] = "temperature";
  attrTemperature["type"] = "int";
  attrTemperature["value"] = temperature;
  arrayJson.add(attrTemperature);
  
  JsonObject& attrBrightness = jsonBuffer.createObject();
  int brightness = (int) ((1024-analogRead(brightnessPin))/8);
  attrBrightness["name"] = "brightness";
  attrBrightness["type"] = "int";
  attrBrightness["value"] = brightness;
  arrayJson.add(attrBrightness);
  
  JsonObject& attrFire = jsonBuffer.createObject();

  attrFire["name"] = "fire";
  attrFire["type"] = "boolean";
  int fire = digitalRead(firePin);
  if (fire == HIGH) {
    attrFire["value"] = true;
  } else {
    attrFire["value"] = false;
  }
  arrayJson.add(attrFire);
  
  jsonObject["attributeEntity"] = arrayJson;

  return jsonObject;
}

void setup() {
  Serial.begin(115200);
  delay(10);
  dht.begin();
}

void loop() {
  StaticJsonBuffer<500> jsonBuffer;
  JsonObject& jsonAttributes = buildAttributesJson(jsonBuffer);  
  jsonAttributes.printTo(Serial);
  delay(3000);
}
