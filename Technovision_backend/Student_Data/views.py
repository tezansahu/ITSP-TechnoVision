from django.shortcuts import get_object_or_404
from .models import Student, Course
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .serializers import StudentSerializer

class StudentList(APIView):

    def get(self, request):
        students = Student.objects.all()
        serializer = StudentSerializer(students, many=True)
        return Response(serializer.data)
'''
class CourseList(APIView):

    def get(self, request):
        courses = Course.objects.all()
        C_serializer = CourseSerializer(courses, many=True)
        return Response(C_serializer.data)  '''      