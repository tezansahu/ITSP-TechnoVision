from rest_framework import serializers
from .models import Student, Course, MA106

class StudentSerializer(serializers.ModelSerializer):
    class Meta:
        model = Student
        fields = '__all__'


class CourseSerializer(serializers.ModelSerializer):
    class Meta:
        model = Course
        fields = '__all__'


class MA106Serializer(serializers.ModelSerializer):
    class Meta:
        model = MA106
        fields = '__all__'



