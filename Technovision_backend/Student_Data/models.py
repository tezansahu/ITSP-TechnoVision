from __future__ import unicode_literals
import datetime
from django.db import models

class Course(models.Model):
    Course_Name = models.CharField(max_length=200)
    Course_Code = models.CharField(max_length=6, primary_key=True)
    Credits = models.IntegerField()
    No_of_Lectures = models.IntegerField()

    def __str__(self):
        return self.Course_Code + ' - ' +self.Course_Name

class Student(models.Model):
    Student_Name = models.CharField(max_length=50)
    Roll_No = models.CharField(max_length=10, primary_key=True)
    Branch = models.CharField(max_length=50)
    Reg_Course = models.ManyToManyField(Course)
    Password = models.CharField(max_length=10)

    def __str__(self):
        return self.Student_Name + ' - ' +self.Roll_No

class MA106_Date(models.Model):
    Date = models.DateField(primary_key=True, default="2018-06-07")
    Status = models.CharField(max_length=2, default="NA")

class MA106(models.Model):
    student = models.ForeignKey(Student, on_delete=models.CASCADE)
    ma106_dates = models.ManyToManyField(MA106_Date)
