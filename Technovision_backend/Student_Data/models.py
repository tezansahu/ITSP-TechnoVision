from django.db import models

class Student(models.Model):
    Student_Name = models.CharField(max_length=250)
    Roll_No = models.CharField(max_length=10)
    Branch = models.CharField(max_length=100)
    Password = models.CharField(max_length=100)

    def __str__(self):
        return self.Roll_No + ' - ' +self.Student_Name
        

class Course(models.Model):
  #  student = models.ForeignKey(Student, on_delete=models.CASCADE)
    Course_Name = models.CharField(max_length=100)
    Course_Code = models.CharField(max_length=10)
    Credits = models.CharField(max_length=5)
    No_of_Lectures = models.CharField(max_length=100)

class Attendence(models.Model):
    Date = models.CharField(max_length=20)
    Status = models.CharField(max_length=1)