import technovision_utils as utils
import os

# Open the file where IP webcam server address and Course are stored
course=""
url=""
while True:

	# Check if the file contains data sent by bot controller app
	if os.stat("/home/tezan/Tezan/ITSP-TechnoVision/Python Codes/ip_address.txt").st_size!=0: # Change the file address to the place where your "ip_address.txt" is stored
		print("File not empty now") 
		fin=open("ip_address.txt","r")
		data=fin.read().split(":")
		url=data[0]
		print(url)
		url="http://"+url+":8080/video"

		course=data[1][:-1]
		print(course)
		fin.close()
		break

# Start capturing live video from the URL
video_capture=utils.fetchLiveVideo(url)

# Get the data of registered students from the backend database 
dates_list, roll_nos, attendance_list, known_face_encodings=utils.getDataFromBackend(course)
#print(roll_nos)
#print(len(known_face_encodings))
#print(attendance_list[0])
#print(dates_list)

# Declare some lists/variables that would be useful throughout the process
studPresent=[]
face_locations = []
face_encodings = []
face_rollNos = []
process_this_frame = True
frame_count=0;
tot_stud_present=[]

# Main loop for frame-by-frame recognition process
while True:
	frame_count+=1

	# Recognize the faces in the frame
	face_locations, face_encodings, face_rollNos, studPresent=utils.recognizeFaces(video_capture, process_this_frame, roll_nos, known_face_encodings, face_locations, face_encodings, face_rollNos, studPresent)
	
	# After every 30 frames processed, mark the attendance of students who are recognized as "P" 
	if frame_count%30==0:
		#print("Frame: "+str(frame_count))
		for rno in studPresent:
			stud_id=roll_nos.index(rno)+1
			utils.markPresent(course, stud_id, attendance_list[stud_id-1], dates_list)
		tot_stud_present.extend(studPresent)
		studPresent=[]
		#print(tot_stud_present)

	# If quitting condition is matched, set the attendance of all other students to "A"
	if utils.checkQuit():
		rem_rnos=list(set(roll_nos)-set(tot_stud_present))
		for rno in rem_rnos:
			stud_id=roll_nos.index(rno)+1
			utils.markAbsent(course, stud_id, attendance_list[stud_id-1], dates_list)
		break

# Release handle to the IP Webcam Server
utils.closeWindows(video_capture)
