from django.db import models

class Student(models.Model):
    Student_Name = models.CharField(max_length=250)
    Roll_No = models.CharField(max_length=10)
    Branch = models.CharField(max_length=100)

    def __str__(self):
        return self.Student_Name + ' - ' +self.Roll_No
        

class Course(models.Model):
    student = models.ForeignKey(Student, on_delete=models.CASCADE)

