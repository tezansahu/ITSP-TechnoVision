import technovision_utils as utils

fin=open("ip_address.txt","r")
url=fin.readline()[:-1]
course=fin.readline()[:-1]
fin.close()

video_capture=utils.fetchLiveVideo(url)

dates_list, roll_nos, attendance_list, known_face_encodings=utils.getDataFromBackend(course)
print(roll_nos)
print(len(known_face_encodings))
studPresent=[]

face_locations = []
face_encodings = []
face_rollNos = []
process_this_frame = True

frame_count=0;

while True:
	frame_count+=1
	face_locations, face_encodings, face_rollNos, studPresent=utils.recognizeFaces(video_capture, process_this_frame, roll_nos, known_face_encodings, face_locations, face_encodings, face_rollNos, studPresent)
	if frame_count%20==0:
		print("Frame: "+str(frame_count))
		print(studPresent)

	if utils.checkQuit():
		break

# Release handle to the webcam
utils.closeWindows(video_capture)
