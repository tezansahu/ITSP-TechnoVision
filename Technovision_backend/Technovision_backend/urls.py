from django.contrib import admin
from django.conf.urls import url, include
from rest_framework.urlpatterns import format_suffix_patterns
from Student_Data import views

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^Student_Data/', views.StudentList.as_view()),
]

urlpatterns = format_suffix_patterns(urlpatterns)