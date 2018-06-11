from __future__ import unicode_literals

from django.db import models
from django_mysql.models import ListCharField

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
    Image = models.ImageField(upload_to='Student_Image', blank=True)

    def __str__(self):
        return self.Roll_No + " - " + self.Student_Name


class MA106(models.Model):
    student = models.ForeignKey(Student, on_delete=models.CASCADE)
    Dates=ListCharField(base_field=models.CharField(max_length=10), size=16, max_length=(16*11), default=[])
    Attendance=ListCharField(base_field=models.CharField(max_length=2), size=16, max_length=(16*3), default=[])

    def __str__(self):
        return self.student.Roll_No + ' - ' + self.student.Student_Name


class CS101(models.Model):
    student = models.ForeignKey(Student, on_delete=models.CASCADE)
    Dates = ListCharField(base_field=models.CharField(max_length=10), size=24, max_length=(24*11), default=[])
    Attendance = ListCharField(base_field=models.CharField(max_length=2), size=24, max_length=(24*3), default=[])

    def __str__(self):
        return self.student.Roll_No + ' - ' + self.student.Student_Name

class PH108(models.Model):
    student = models.ForeignKey(Student, on_delete=models.CASCADE)
    Dates = ListCharField(base_field=models.CharField(max_length=10), size=24, max_length=(24*11), default=[])
    Attendance = ListCharField(base_field=models.CharField(max_length=2), size=24, max_length=(24*3), default=[])

    def __str__(self):
        return self.student.Roll_No + ' - ' + self.student.Student_Name

class MA105(models.Model):
    student = models.ForeignKey(Student, on_delete=models.CASCADE)
    Dates = ListCharField(base_field=models.CharField(max_length=10), size=24, max_length=(24*11), default=[])
    Attendance = ListCharField(base_field=models.CharField(max_length=2), size=24, max_length=(24*3), default=[])

    def __str__(self):
        return self.student.Roll_No + ' - ' + self.student.Student_Name