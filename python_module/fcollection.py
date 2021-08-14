# from collections import defaultdict
from flask import Flask, render_template, Response, request,redirect
import cv2
import numpy as np
import os
from os import walk
from os.path import sep
import time 
from flask.helpers import url_for

app = Flask(__name__)

# cap = cv2.VideoCapture('http://192.168.25.184:8080/video')  # use 0 for web camera
face_classifier=cv2.CascadeClassifier('haarcascade_frontalface_default.xml')
cameraLink = 'http://10.89.10.74:8080/video'


def face_detector(img, size=0.5):
    #gray=cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
    faces = face_classifier.detectMultiScale(img,1.3,5)
    if faces is ():
        return(img, [])
    for (x,y,w,h) in faces:
        cv2.rectangle(img, (x,y), (x+w, y+h), (0, 255, 255), 2)
        roi=img[y:y+h, x:x+w]
        roi=cv2.resize(roi, (200,200))
    return(img, roi)

def identify():
    mohit_model=cv2.face.LBPHFaceRecognizer_create()
    mohit_model.read('smart.yml')
    
    cap = cv2.VideoCapture(cameraLink)  # use 0 for web camera
    d=dict()
    global s
    s=set()
    # t= time.time() +60
    stime= time.time()
    duration = 100    #duration of time
    etime= stime + duration
    slot_time= duration/10

    while time.time() < etime:
        ret, frame = cap.read()
        if ret is None:
            break
        else:
            photo, face= face_detector(frame)
            
            try:
                face= cv2.cvtColor(face, cv2.COLOR_BGR2GRAY)
                
                #passing face to prediction model
                results= mohit_model.predict(face)
                # print(results[0])
                
                if results[1]<500:
                    confidence= int(100*(1-(results[1])/400))
                    display_string= str(confidence) + "% Confidence, It's a User"
                cv2.putText(photo, display_string, (100, 120), cv2.FONT_HERSHEY_COMPLEX, 1, (255, 0, 0), 2, cv2.LINE_AA)
                
                if confidence>=70:
                    cv2.putText(photo, str(results[0]), (250, 450), cv2.FONT_HERSHEY_COMPLEX, 1, (255, 0, 0), 2, cv2.LINE_AA)
                    # cv2.imshow('Face Recognotion', image)
                    r=results[0]
                    if r not in d.keys():
                        d[r] = [0]*10
                        temp=int( (time.time()-stime)/slot_time)
                        d[r][temp]= 1
                    else:
                        temp=int( (time.time()-stime)/slot_time)
                        d[r][temp]= 1
                    print(temp)
                    print(d)
                    # Before writing dict
                    # s.add(results[0])     
                    st, buffer = cv2.imencode('.jpg', photo)
                    # print(st, buffer)
                    photo = buffer.tobytes()
                    yield (b'--photo\r\n' b'Content-Type: image/jpeg\r\n\r\n' + photo + b'\r\n')  # concat photo one by one and show result
                    
                else:
                    cv2.putText(photo, "I don't Know", (250, 450), cv2.FONT_HERSHEY_COMPLEX, 1, (255, 0, 0), 2, cv2.LINE_AA)
                    # cv2.imshow("Face Recognition", photo)
                    st, buffer = cv2.imencode('.jpg', photo)
                    # print(st, buffer)
                    photo = buffer.tobytes()
                    yield (b'--photo\r\n' b'Content-Type: image/jpeg\r\n\r\n' + photo + b'\r\n')  # concat photo one by one and show result

                    
            except:
                cv2.putText(photo, "Face Not Found", (220, 120), cv2.FONT_HERSHEY_COMPLEX,1, (0,0,255), 2, cv2.LINE_AA)
                cv2.putText(photo, "Locked", (250, 450), cv2.FONT_HERSHEY_COMPLEX,1, (0,0,255), 2, cv2.LINE_AA)
                # cv2.imshow('Face Recognition', photo)
                st, buffer = cv2.imencode('.jpg', photo)
                # print(st, buffer)
                photo = buffer.tobytes()
                yield (b'--photo\r\n' b'Content-Type: image/jpeg\r\n\r\n' + photo + b'\r\n')  # concat photo one by one and show result
    print(d)
    cap.release()
    for i in d.keys():
        if d[i].count(1)>7:
            s.add(i)   
    print(s)

def train():
    print('Training Begins')
    data_path='./people/'

    Training_data, Labels = [], []
    i=0
    for subdir, dirs, files in walk(data_path):
        s=subdir[9:]
        for filename in files:
            filepath = subdir + sep + filename
            images=cv2.imread(filepath,cv2.IMREAD_GRAYSCALE)
            Training_data.append(np.asarray(images,dtype=np.uint8))
            Labels.append(int(s))
            i+=1
        

    #Creating Numpy array for both training data and Label
    Labels=np.asarray(Labels,dtype=np.int32)
    mohit_model=cv2.face.LBPHFaceRecognizer_create()


    #training Model
    mohit_model.train(np.asarray(Training_data),np.asarray(Labels))
    print("Model Train Successfully")
    mohit_model.write("smart.yml")
    print("Trained model is saved in system as 'smart.yml' ")


def face_extractor(img):
    grey=cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
    faces=face_classifier.detectMultiScale(grey)
    if len(faces)==0:
        return(None)
    else:
        for (x,y,w,h) in faces:
            croped_face=img[y:y+h,x:x+w]
        return(croped_face)


def collect():  # generate photo by photo from camera
    count=0
    if not os.path.exists('./people/'+name):
        os.makedirs('./people/'+name)
    
    cap = cv2.VideoCapture(cameraLink)  # use 0 for web camera

    while True:
        # Capture photo-by-photo
        ret, photo = cap.read()  # read the camera photo
        # print(ret)
        if not ret:
            # print('error')
            pass
        else:
            frame= face_extractor(photo)
            if frame is not None:
                count+=1
                face=cv2.resize(frame,(200,200))
                face=cv2.cvtColor(face,cv2.COLOR_BGR2GRAY)
                
                #saving file
                file_name_path='./people/'+name+"/face"+str(count)+'.jpg'
                cv2.imwrite(file_name_path,face)
                
                #putting counter on image
                cv2.putText(face, str(count), (50,50), cv2.FONT_HERSHEY_COMPLEX, 1, (255, 0, 0), 2, cv2.LINE_AA)
                # cv2.imshow('hi', photo)
                st, buffer = cv2.imencode('.jpg', face)
                # print(st, buffer)
                face = buffer.tobytes()
                if count==101:
                    break
                yield (b'--photo\r\n' b'Content-Type: image/jpeg\r\n\r\n' + face + b'\r\n')  # concat photo one by one and show result
            
            else:
                # return("Face not found")
                pass
    cap.release()
    #Training call
    train()
            

@app.route('/toJava')
def toJava():
    # ch='http://localhost/show_list?some_list='
    # while len(s)!=0:
    #     if len(s)==1:
    #         ch=ch+str(s.pop())
    #     ch=ch+','+str(s.pop())

    
    x=list(s)
    ch='http://localhost:8080/ssss/MarkAttendanceController?subject='+request.args.get('subject')+'&log='
    for i in range(len(x)):
        if i==0:
            ch=ch+str(x[i])
        else:
            ch=ch+','+str(x[i])
    return redirect(ch)

@app.route('/video_feed_2')
def video_feed_2():
    #Video streaming route. Put this in the src attribute of an img tag
    return Response(identify(), mimetype='multipart/x-mixed-replace; boundary=photo')
    
@app.route('/recog', methods=['GET'])
def face_recognition():
    # global name = request.args.get('id')
    # global name
    # name = str(1)
    """Video streaming home page."""
    # return redirect('http://wwww.google.com')
    return render_template('recog.html')


@app.route('/video_feed_1')
def video_feed_1():
    #Video streaming route. Put this in the src attribute of an img tag
    return Response(collect(), mimetype='multipart/x-mixed-replace; boundary=photo')


@app.route('/collection', methods=['GET'])
def face_collection():
    global name
    name = request.args.get('id')
    # name = str(4)
    """Video streaming home page."""
    return render_template('collect.html')


if __name__ == '__main__':
    app.run(debug=True)

