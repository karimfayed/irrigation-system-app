# **irrigation-system-app**
## **This application used to schedule and control when the valves of an irrigation system are opened and closed.**
## Importnat Notes:
### **This application uses firebase Realtime database so make sure you create one and connect to it as the database in the code is expired**
### **Make sure to change 'local-network-name' & 'local-network-password' in the arduino sketch with your local network name and password correspondingly**
### **The valves used are represented with a servo motor connected to an arduino with wifimodule and the functions of turning the servo motor are accessed using the path of the function and the IP address of the aurduino in the network**
### **The the arduino sketch is available in the file 'servo.ino' in the folder 'servo'**
---

## Classes Used:
**FullscreenActivity :** It manages the audio that is played as the application starts running.

**MainActivity :** It makes sure that the application is connected to the internet,
sets the connection between the application and the firebase Realtime database (is present in most classes),
it controls the gif and its translation across the screen and handles the process of signing up.

**login :** Handles the login process and provide validation and authentication to the entered credentials.

**MainActivity2 :** Home page of the user. It displays 2 buttons, one for viewing and setting up schedules and the other to turn the valve
on and off on command.

**Valves :** It handles the button for switching the state of the valve either on or off.

**Close & Open :** Connect to the arduino with its IP address in the local network and the path of the funtion to activate 
the corresponding function.

**TimePik :** It handles the timers used for adding another schedule.

**Users :** To provide 'user' object in MainActivity class.

**Schedule :** It shows the schedules set by the user, a button to add another schedule and another button to allow getting back to user's home.

**Scheduler :** It provide 'scheduler' object used for adding a schedule, used in TimePik class. 

**ProgramAdapter & ProgramViewHolder :** It handles viewing each schedule in the specified layout.