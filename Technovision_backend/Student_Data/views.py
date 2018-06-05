from django.shortcuts import render
#from django.http import HttpResponse
from rest_framework import viewsets
from .models import Student, Course, MA106
from .serializers import StudentSerializer, CourseSerializer, MA106Serializer

class StudentView(viewsets.ModelViewSet):
    queryset = Student.objects.all()
    
    serializer_class = StudentSerializer

class CourseView(viewsets.ModelViewSet):
    queryset = Course.objects.all()
    
    serializer_class = CourseSerializer

class MA106View(viewsets.ModelViewSet):
    queryset = MA106.objects.all()
    
    serializer_class = MA106Serializer


'''
def index(request):
    return HttpResponse ("<h1>Student Data")
'''
