from rest_framework import serializers
from .models import Student, Course, MA106, CS101, PH108, MA105

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


class CS101Serializer(serializers.ModelSerializer):
    class Meta:
        model = CS101
        fields = '__all__'


class PH108Serializer(serializers.ModelSerializer):
    class Meta:
        model = PH108
        fields = '__all__'


class MA105Serializer(serializers.ModelSerializer):
    class Meta:
        model = MA105
        fields = '__all__'
