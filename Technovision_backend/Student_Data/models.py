from __future__ import unicode_literals
from django.db import models



        

class Course(models.Model):
#   student = models.ForeignKey(Student, on_delete=models.CASCADE)
    Course_Name = models.CharField(max_length=200)
    Course_Code = models.CharField(max_length=6, primary_key=True)
    Credits = models.IntegerField()
    No_of_Lectures = models.IntegerField()

class Student(models.Model):
    Student_Name = models.CharField(max_length=50)
    Roll_No = models.CharField(max_length=10, primary_key=True)
    Branch = models.CharField(max_length=50)
    Reg_Course = models.ManyToManyField(Course)
    Password = models.CharField(max_length=10)

    def __str__(self):
        return self.Student_Name + ' - ' +self.Roll_No