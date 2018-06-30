from bluetooth import *
import RPi.GPIO as GPIO
import time

#Set up the GPIO Pins to control the bot motors
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD)

motor_left_1=13
motor_left_2=15
motor_right_1=7
motor_right_2=11

servo_signal=12

GPIO.setup(motor_left_1, GPIO.OUT)
GPIO.setup(motor_left_2, GPIO.OUT)
GPIO.setup(motor_right_1, GPIO.OUT)
GPIO.setup(motor_right_2, GPIO.OUT)
GPIO.setup(servo_signal, GPIO.OUT)

#servo Initial Setup for PWM
p=GPIO.PWM(servo_signal, 50)
servo_posn=7.5
p.start(servo_posn)

####Set value of pins as per direction of movement###### 
def forward():
    GPIO.output(motor_left_1,1)
    GPIO.output(motor_left_2,0)
    GPIO.output(motor_right_1,0)
    GPIO.output(motor_right_2,1)

def backward():
    GPIO.output(motor_left_1,0)
    GPIO.output(motor_left_2,1)
    GPIO.output(motor_right_1,1)
    GPIO.output(motor_right_2,0)

def left():
    GPIO.output(motor_left_1,0)
    GPIO.output(motor_left_2,1)
    GPIO.output(motor_right_1,0)
    GPIO.output(motor_right_2,1)

def right():
    GPIO.output(motor_left_1,1)
    GPIO.output(motor_left_2,0)
    GPIO.output(motor_right_1,1)
    GPIO.output(motor_right_2,0)

def stop():
    GPIO.output(motor_left_1,0)
    GPIO.output(motor_left_2,0)
    GPIO.output(motor_right_1,0)
    GPIO.output(motor_right_2,0)

#######################################################



####Set Servo Position as per bluetooth signals########
def servo_left():
    if servo_posn==12.5:
        return
    else:
        servo_posn+=0.5
        p.ChangeDutyCycle(servo_posn)

def servo_right():
    if servo_posn==2.5:
        return
    else:
        servo_posn-=0.5
        p.ChangeDutyCycle(servo_posn)
#######################################################


# Create a new server socket using RFCOMM protocol
server_sock = BluetoothSocket(RFCOMM)
# Bind to any port
server_sock.bind(("", PORT_ANY))
# Start listening
server_sock.listen(1)
# Get the port the server socket is listening
port = server_sock.getsockname()[1]

# The service UUID to advertise
uuid = "00001101-0000-1000-8000-00805f9b34fb"
 
# Start advertising the service
advertise_service(server_sock, "RaspiBtSrv",
                   service_id=uuid,
                   service_classes=[uuid, SERIAL_PORT_CLASS],
                   profiles=[SERIAL_PORT_PROFILE])


stop()
# Main Bluetooth server loop
while True:

    print("Waiting for connection on RFCOMM channel %d" % port)

    try:
        client_sock = None

        # This will block until we get a new connection
        client_sock, client_info = server_sock.accept()
        print("Accepted connection from ", client_info)
	data = client_sock.recv(1024)
        print("Received ", data.decode("utf-8"))
        while True:
        # Read the data sent by the client
            data = client_sock.recv(1024)
            if len(data) == 0:
                stop()
                break

            print("Received ", data[0])
            if data[0]==1:
                forward()
            elif data[0]==2:
                left()
            elif data[0]==3:
                right()
            elif data[0]==4:
                backward()
            elif data[0]==5:
                servo_left()
            elif data[0]==6:
                servo_right()
            else:
                stop()
                time.sleep(1)    
    except IOError:
        pass

    except KeyboardInterrupt:

        if client_sock is not None:
            client_sock.close()

        server_sock.close()

        print("Server going down")
        p.stop()
        GPIO.cleanup()
        break
