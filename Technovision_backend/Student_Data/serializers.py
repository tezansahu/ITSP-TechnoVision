from rest_framework import serializers
from .models import Student

class StudentSerializer(serializers.ModelSerializer):

    class Meta:
        model = Student
        fields = 'Student_Name', 'Roll_No', 'Branch'

#class CourseSerializer(serializers.ModelSerializer):
