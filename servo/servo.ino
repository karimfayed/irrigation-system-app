#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <Servo.h>

#ifdef ESP32
#pragma message(THIS EXAMPLE IS FOR ESP8266 ONLY!)
#error Select ESP8266 board.
#endif

ESP8266WebServer server(80);                        // 80 is the port number

const char* ssid = "local-network-name";            //wifi-name
const char* password = "local-network-password";    // wifi-password
Servo servo;

void Redon()                                        //Rotating the servo to 90 degrees to represent the valve opening
{
 servo.write(90);
  delay(1000);
}

void Redoff()                                       //Rotating the servo back to 0 degrees to represent the valve closing
{
 servo.write(0);
  delay(1000);
}


void setup() {

  Serial.begin(115200);
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED)delay(500);

  Serial.print(WiFi.localIP());

  server.on("/son", Redon);                         // setting the path with the corresponding function
  server.on("/soff", Redoff);                       // setting the path with the corresponding function


  server.begin();

  servo.attach(4);                                    //D2
  servo.write(0);
  delay(2000);

}

void loop()
{
  server.handleClient();
  delay(1);
}
