from django.shortcuts import render
#from django.http import HttpResponse
from rest_framework import viewsets
from .models import Student, Course, MA106, CS101, PH108, MA105
from .serializers import StudentSerializer, CourseSerializer, MA106Serializer, CS101Serializer, PH108Serializer, MA105Serializer

class StudentView(viewsets.ModelViewSet):
    queryset = Student.objects.all()
    
    serializer_class = StudentSerializer

class CourseView(viewsets.ModelViewSet):
    queryset = Course.objects.all()
    
    serializer_class = CourseSerializer

class MA106View(viewsets.ModelViewSet):
    queryset = MA106.objects.all()
    
    serializer_class = MA106Serializer

class CS101View(viewsets.ModelViewSet):
    queryset = CS101.objects.all()
    
    serializer_class = CS101Serializer

class PH108View(viewsets.ModelViewSet):
    queryset = PH108.objects.all()
    
    serializer_class = PH108Serializer

class MA105View(viewsets.ModelViewSet):
    queryset = MA105.objects.all()
    
    serializer_class = MA105Serializer        

'''
def index(request):
    return HttpResponse ("<h1>Student Data")
'''
