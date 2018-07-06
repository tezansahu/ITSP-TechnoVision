import urllib.request as requests
import json
from skimage import io
import cv2
import face_recognition
import datetime
import requests as req

print("packages imported")

# To fetch live streaming video of the class
# from the IP Webcam Server
def fetchLiveVideo(url):
	video_capture=cv2.VideoCapture(url)
	ret, frame = video_capture.read()
	if ret==True:
		return video_capture
	else:
		print("IP Webcam Server not responding")
		exit(0)


# Fetch list of Roll Numbers and their respective face-
# encodings lists from the deployed backend
def getDataFromBackend(course):
    stu_url="http://technovision.pythonanywhere.com/Student_Data/Student"
    course_url="http://technovision.pythonanywhere.com/Student_Data/"
    response=requests.urlopen(course_url+course)
    data=response.read().decode('utf-8')
    json_data=json.loads(data)
    
    if(len(json_data)>0):
        dates_str=json_data[0]["Dates"]
        dates_list=preProcessDates(dates_str)
    else:
        dates_list=[]
    roll_nos=[]
    attendance_list=[]
    for i in range(len(json_data)):
        roll_nos.append(json_data[i]["student"])
        attendance_list.append(json_data[i]["Attendance"])

    print("got data")

    response=requests.urlopen(stu_url)
    data=response.read().decode('utf-8')
    json_data=json.loads(data)

    known_face_encodings=[]

    for i in range(len(json_data)):
        if(roll_nos.count(json_data[i]["Roll_No"])>0):
            img_url=json_data[i]["Image"]
            known_face_encodings.append(face_recognition.face_encodings(io.imread(img_url))[0])

    print("got encodings")
    return dates_list, roll_nos, attendance_list, known_face_encodings;




# To return a list of dates of lectures from a string sent from backend
def preProcessDates(dates_str):
    format="%d/%m/%Y"
    dates_list=[]
    i=3
    while i<len(dates_str):
        dates_list.append(datetime.datetime.strptime(dates_str[i:i+10],format))
        i+=15
    return dates_list



# To look for today's date in the list of dates for a course.
def locateToday(dates_list):
    i=0
    for i in range(len(dates_list)):
        if dates_list[i].date()==datetime.datetime.now().date():
            return i

    print("This course is not scheduled for today.")
    exit(0)


# Mark the attendance of a person "Present" for the lecture
def markPresent(course, id, attendance, dates_list):
    attendance=attendance.replace(" ","").replace("u","").replace("'","").replace("[","").replace("]","")
    att_list=attendance.split(",")
    att_list[locateToday(dates_list)]="P"
    attendance=",".join(att_list)

    print(req.patch("http://technovision.pythonanywhere.com/Student_Data/"+course+"/"+str(id)+"/",data={"Attendance":attendance},auth=req.auth.HTTPBasicAuth('Tezan','technovision')))
    print("Update for id: "+str(id))


# Mark the attendance of a person "Absent" for the lecture
def markAbsent(course, id, attendance, dates_list):
    attendance=attendance.replace(" ","").replace("u","").replace("'","").replace("[","").replace("]","")
    att_list=attendance.split(",")
    att_list[locateToday(dates_list)]="A"
    attendance=",".join(att_list)

    req.patch("http://technovision.pythonanywhere.com/Student_Data/"+course+"/"+str(id)+"/",data={"Attendance":attendance},auth=req.auth.HTTPBasicAuth('Tezan','technovision'))
    print("Update Successful for id: "+str(id))    


# Recognize faces in the frame passed as arguement by comparing with the known-face-encodings.
def recognizeFaces(video_capture, process_this_frame, roll_nos, known_face_encodings, face_locations, face_encodings, face_rollNos, studPresent):
	# Grab a single frame of video
    ret, frame = video_capture.read()

    # Resize frame of video to 1/4 size for faster face recognition processing
    small_frame = cv2.resize(frame, (0, 0), fx=0.25, fy=0.25)

    # Convert the image from BGR color (which OpenCV uses) to RGB color (which face_recognition uses)
    rgb_small_frame = small_frame[:, :, ::-1]

    # Only process every other frame of video to save time
    if process_this_frame:
        # Find all the faces and face encodings in the current frame of video
        face_locations = face_recognition.face_locations(rgb_small_frame, model="cnn")
        face_encodings = face_recognition.face_encodings(rgb_small_frame, face_locations)

        face_rollNos = []
        for face_encoding in face_encodings:
            # See if the face is a match for the known face(s)
            matches = face_recognition.compare_faces(known_face_encodings, face_encoding, tolerance=0.5)
            rollNo = "Unknown"

            # If a match was found in known_face_encodings, just use the first one.
            if True in matches:
                first_match_index = matches.index(True)
                rollNo = roll_nos[first_match_index]

            face_rollNos.append(rollNo)
            if rollNo != "Unknown" and studPresent.count(rollNo)==0:
            	studPresent.append(rollNo)

    # Display the results
    for (top, right, bottom, left), rollNo in zip(face_locations, face_rollNos):
        # Scale back up face locations since the frame we detected in was scaled to 1/4 size
        top *= 4
        right *= 4
        bottom *= 4
        left *= 4

        # Draw a box around the face
        cv2.rectangle(frame, (left, top), (right, bottom), (0, 0, 255), 2)

        # Draw a label with a name below the face
        cv2.rectangle(frame, (left, bottom - 35), (right, bottom), (0, 0, 255), cv2.FILLED)
        font = cv2.FONT_HERSHEY_DUPLEX
        cv2.putText(frame, rollNo, (left + 6, bottom - 6), font, 1.0, (255, 255, 255), 1)

    # Display the resulting image
    cv2.imshow('Video', frame)
    return face_locations, face_encodings, face_rollNos, studPresent

# Close all OpenCV related video windows
def closeWindows(video_capture):
	video_capture.release()
	cv2.destroyAllWindows()

# Check if the quitting condition is matched
def checkQuit():
	if os.stat("/home/tezan/Tezan/ITSP-TechnoVision/Python Codes/exit_check.txt").st_size!=0:
        fin=open("exit_check.txt","r")
        if fin.read()[:-1]=="EXIT FACE-RECOG SCRIPT":
            return 1
    return 0