from django.contrib import admin
from .models import Students
from .models import Courses
# Register your models here.
admin.site.register(Students)
admin.site.register(Courses)