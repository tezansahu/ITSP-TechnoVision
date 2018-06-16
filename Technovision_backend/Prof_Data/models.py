from __future__ import unicode_literals

from django.db import models
from Student_Data.models import Course

class Prof(models.Model):
    Prof_Name = models.CharField(max_length=50)
    Prof_Course = models.ForeignKey(Course, on_delete=models.CASCADE)
    Password = models.CharField(max_length=20)

    def __str__(self):
        return self.Prof_Name + ' - ' + self.Prof_Course.Course_Code
    