from django.shortcuts import render
#from django.http import HttpResponse
from rest_framework import viewsets
from .models import Student, Course
from .serializers import StudentSerializer, CourseSerializer 

class StudentView(viewsets.ModelViewSet):
    queryset = Student.objects.all()
    
    serializer_class = StudentSerializer

class CourseView(viewsets.ModelViewSet):
    queryset = Course.objects.all()
    
    serializer_class = CourseSerializer

'''
def index(request):
    return HttpResponse ("<h1>Student Data")
'''