#$id = (int)$_GET['id'];
#3 param en entrée : path de l'img a comparer; l@ mail; et l'id de l'utilisateur
# pip install cmake, dlib, face_recognition, email, smtplib, mysql, numpy
import face_recognition
import numpy as np
import os
import argparse
import smtplib
from email.message import EmailMessage
#lien avec la bdd
import mysql.connector

#path de l'img en argument
parser = argparse.ArgumentParser()
parser.add_argument("path_image", help="path to input image ")
parser.add_argument("mail", help="mail si besoin de lui envoyer si besoin ")
parser.add_argument("id", help="id du user")
args = parser.parse_args()
#load et encoding de l'image a considerer
imgBase = face_recognition.load_image_file(args.path_image)
modi_encoding = face_recognition.face_encodings(imgBase)[0]
lien = "<a href=\"localhost/secuiot/tempo.php?id="+args.id+"\"\a>" #completer

def compare(modi_encoding,path_img):
    #load
    image = face_recognition.load_image_file(str(path_img))
    #encoding
    unknown_encoding = face_recognition.face_encodings(image)[0]

    #comparaison
    resultats = face_recognition.compare_faces ([modi_encoding], unknown_encoding)
    if resultats=="True":
        return 1
    else return 0

mydb = mysql.connector.connect(
  host="localhost",
  port="8889",
  user="root",
  password="root",
  database="Visage"
)
mycursor = mydb.cursor()
mycursor.execute("SELECT Image FROM photos") #recupere juste l'adresse de la photo (id pas interessant)
myresult = mycursor.fetchall()
present =0
for x in myresult:
  #print(x)
  if(compare(modi_encoding,x)==1):
      present =1
      break

present=1


if present ==1:
    print("mail")
    msg = EmailMessage()
    msg['Subject'] = 'Nouvelle demande de consentement'
    msg['From'] = 'GestionConsentement912@gmail.com'
    msg['To'] = args.mail
    s = "Bonjour,<br><br>Vous avez été detecté dans la banque d\'image utilisé.Votre consentement est requis pour l\'utilisation de celle-ci.<br>Cliquez "+lien+"1\>ici</a> pour donner votre consentement.<br>CLiquez "+lien+"0\>là</a> pour refuser son utilisation.<br><br>Bonne journée"
    msg.set_content(, subtype='html')
    


    with smtplib.SMTP_SSL('smtp.gmail.com', 465) as smtp:
            smtp.login('GestionConsentement912@gmail.com', "Bidon123")
            smtp.send_message(msg)
else print("rien")

#GestionConsentement912@gmail.com
#Bidon123

