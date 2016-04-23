#include <ESP8266WiFi.h>
#include <ArduinoJson.h>

const char* ssid     = "SSID";
const char* password = "password";

const char* url = "/ContextAnalyzer/api/widget/updateAll/1";
const char* host = "192.168.1.7";
const int port = 8080;

void setup() {
  Serial.begin(115200);
  delay(10);
  WiFi.begin(ssid, password);  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
  }
}

void loop() {
  while (Serial.available()) {
    String jsonString = Serial.readString();
      WiFiClient client;
      if (client.connect(host, port)) {
        client.println(String("PUT ") + url + " HTTP/1.1");
        client.println(String("Host: ") + host + ":" + port);
        client.println("Content-Type: application/json");
        client.println("Accept: */*");
        client.println(String("Content-Length: ") + jsonString.length());
        client.println("Connection: close");
        client.println();
        client.println(jsonString);
        client.stop();
      }
  }
}
