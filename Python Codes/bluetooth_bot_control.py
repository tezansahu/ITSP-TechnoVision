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
servo_loc=0
def set_servo(servo_loc):
	servo_loc=7.5
	p.start(servo_loc)
	return servo_loc

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
def servo_left(servo_loc):
    print(servo_loc)
    if servo_loc==12.5:
        return servo_loc
    else:
        servo_loc=servo_loc+1
        p.ChangeDutyCycle(servo_loc)
        return servo_loc

def servo_right(servo_loc):
    print(servo_loc)
    if servo_loc==2.5:
        return servo_loc
    else:
        servo_loc=servo_loc-1
        p.ChangeDutyCycle(servo_loc)
        return servo_loc
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
                   profiles=[SERIAL_PORT_PROFILE]
                   )


stop()

# Ensure that the file containing exiting condition for test_recog script is empty in the beginning
if os.stat("/home/tezan/Tezan/ITSP-TechnoVision/Python Codes/exit_check.txt").st_size!=0:
    open("exit_check.txt","w").close()

# Main Bluetooth server loop
while True:

    print("Waiting for connection on RFCOMM channel %d" % port)
    
	# Open file to write IP Webcam server address for other script
    fout=open("ip_address.txt","w")
    try:
        client_sock = None

        # This will block until we get a new connection
        client_sock, client_info = server_sock.accept()
        print("Accepted connection from ", client_info)
        ip_addr = client_sock.recv(1024)
        print("Received ", ip_addr.decode("utf-8"))
        course = client_sock.recv(1024)
        print("Received ", course.decode("utf-8"))
        # Write IP address received to file
        fout.write(ip_addr.decode("utf-8"))
        fout.write(course.decode("utf-8"))
        
        fout.close()
        servo_loc=set_servo(servo_loc)
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

                servo_loc=servo_left(servo_loc)
            elif data[0]==6:

                servo_loc=servo_right(servo_loc)
            else:
                stop()
                time.sleep(1)
                if data[0]==8:# When Connection is lost

                    # Write the exiting text into a file for test_recog script to end
                    fout2=open("exit_check.txt","w")
                    fout2.write("EXIT FACE-RECOG SCRIPT")
                    fout2.close()    
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

