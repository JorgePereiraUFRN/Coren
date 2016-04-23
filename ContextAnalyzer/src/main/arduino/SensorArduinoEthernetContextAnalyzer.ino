#include <SPI.h>
#include <Ethernet.h>
#include <ArduinoJson.h>
#include <DHT.h>
#define DHTTYPE DHT11

#define DHTPIN 2 // pin digital temperature
int brightnessPin = A2; // pin analog brightness
int firePin = 3; // pin digital fire

const char* url = "/ContextAnalyzer/api/widget/updateAll/1"; // update many attributes in widget
const char* host = "192.168.1.131";
const int port = 8080;

byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
IPAddress ip(192, 168, 1, 130);
EthernetClient client;

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
  if (Ethernet.begin(mac) == 0) {
    Ethernet.begin(mac, ip);
  }
  delay(1000);
}

void loop() {
  if (client.connect(host, port)) {
    StaticJsonBuffer<500> jsonBuffer;
    JsonObject& jsonAttributes = buildAttributesJson(jsonBuffer);  
    
    client.println(String("PUT ") + url + " HTTP/1.1");
    client.println(String("Host: ") + host + ":" + port);
    client.println("Content-Type: application/json");
    client.println("Accept: */*");
    client.println(String("Content-Length: ") + jsonAttributes.measureLength());
    client.println("Connection: close");
    client.println();
    jsonAttributes.printTo(client);
    
    delay(10);
    
    client.stop();  
  }
  delay(3000);
}
