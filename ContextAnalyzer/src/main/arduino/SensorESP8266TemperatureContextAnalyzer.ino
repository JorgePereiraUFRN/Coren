#include <ESP8266WiFi.h>
#include <ArduinoJson.h>
#include <DHT.h>
#define DHTPIN 2
#define DHTTYPE DHT11

const char* ssid     = "SSID";
const char* password = "password";

const char* url = "/ContextAnalyzer/api/widget/update/1";
const char* host = "192.168.1.131";
const int port = 8080;

DHT dht(DHTPIN, DHTTYPE);

void connectWifi() {
  Serial.print("Connecting to ");
  Serial.println(ssid);
  
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("WiFi connected");  
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP()); 
}

JsonObject& prepareJson(JsonBuffer& jsonBuffer) {
  JsonObject& attr = jsonBuffer.createObject();
  float value = dht.readTemperature();
  while (isnan(value)) {
    value = dht.readTemperature();
  }
  attr["name"] = "temperature";
  attr["type"] = "int";
  attr["value"] = value;
  return attr;
}

void setup() {
  Serial.begin(115200);
  delay(10);
  connectWifi();
  dht.begin();
}

void loop() {
  WiFiClient client;
  if (!client.connect(host, port)) {
    Serial.println("connection failed");
    return;
  }
  
  StaticJsonBuffer<300> jsonBuffer;
  JsonObject& jsonObject = prepareJson(jsonBuffer);
  
  client.println(String("PUT ") + url + " HTTP/1.1");
  client.println(String("Host: ") + host + ":" + port);
  client.println("Content-Type: application/json");
  client.println("Accept: */*");
  client.println(String("Content-Length: ") + jsonObject.measureLength());
  client.println("Connection: close");
  client.println();
  jsonObject.printTo(client);
  
  delay(10);
  
  while(client.available()){
    String line = client.readStringUntil('\r');
    Serial.print(line);
  }
  
  client.stop();
  Serial.println("closing connection");
  delay(5000);
}
