import technovision_utils as utils

# Open the file where IP webcam server address and Course are stored 
fin=open("ip_address.txt","r")
url=fin.readline()[:-1]
course=fin.readline()[:-1]
fin.close()

# Start capturing live video from the URL
video_capture=utils.fetchLiveVideo(url)

# Get the data of registered students from the backend database 
dates_list, roll_nos, attendance_list, known_face_encodings=utils.getDataFromBackend(course)
#print(roll_nos)
#print(len(known_face_encodings))

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
			utils.markPresent(course, stud_id, attendance_list[stud_id-1])
		tot_stud_present.extend(studPresent)
		studPresent=[]
		#print(tot_stud_present)

	# If quitting condition is matched, set the attendance of all other studentsto "A"
	if utils.checkQuit():
		rem_rnos=list(set(roll_nos)-set(tot_stud_present))
		for rno in rem_rnos:
			stud_id=roll_nos.index(rno)+1
			utils.markAbsent(course, stud_id, attendance_list[stud_id-1])
		break

# Release handle to the IP Webcam Server
utils.closeWindows(video_capture)
