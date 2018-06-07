from django.contrib import admin
from .models import Student, Course, MA106, MA106_Date

admin.site.register(MA106)
admin.site.register(Student)
admin.site.register(Course)
admin.site.register(MA106_Date)